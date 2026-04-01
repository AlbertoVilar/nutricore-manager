import {
  createContext,
  useContext,
  useEffect,
  useMemo,
  useState,
  type ReactNode,
} from 'react';
import type { AuthSession, AuthenticatedUser, LoginCredentials } from '../types/auth';
import { loginEditorialUser, getAuthenticatedEditorialUser } from '../services/authService';
import {
  clearStoredEditorialSession,
  getStoredEditorialSession,
  isEditorialSessionExpired,
  storeEditorialSession,
} from '../services/editorialSessionService';
import { ApiError, registerAuthFailureHandler } from '../services/httpClient';

interface EditorialSessionContextValue {
  session: AuthSession | null;
  user: AuthenticatedUser | null;
  isAuthenticated: boolean;
  isReady: boolean;
  errorMessage: string | null;
  signIn: (credentials: LoginCredentials) => Promise<void>;
  signOut: (reason?: string) => void;
  clearError: () => void;
}

const EditorialSessionContext = createContext<EditorialSessionContextValue | undefined>(undefined);

interface EditorialSessionProviderProps {
  children: ReactNode;
}

export function EditorialSessionProvider({ children }: EditorialSessionProviderProps) {
  const [session, setSession] = useState<AuthSession | null>(null);
  const [isReady, setIsReady] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  useEffect(() => {
    let isMounted = true;

    async function restoreSession() {
      const storedSession = getStoredEditorialSession();

      if (!storedSession) {
        if (isMounted) {
          setIsReady(true);
        }
        return;
      }

      if (isEditorialSessionExpired(storedSession)) {
        clearStoredEditorialSession();

        if (isMounted) {
          setSession(null);
          setErrorMessage('Sua sessao expirou. Faca login novamente.');
          setIsReady(true);
        }
        return;
      }

      setSession(storedSession);

      try {
        const authenticatedUser = await getAuthenticatedEditorialUser();
        const refreshedSession = {
          ...storedSession,
          user: authenticatedUser,
        };

        storeEditorialSession(refreshedSession);

        if (isMounted) {
          setSession(refreshedSession);
          setErrorMessage(null);
        }
      } catch (error) {
        clearStoredEditorialSession();

        if (isMounted) {
          setSession(null);
          setErrorMessage(
            error instanceof ApiError && error.status === 401
              ? 'Sua sessao expirou. Faca login novamente.'
              : 'Nao foi possivel restaurar a sessao editorial.',
          );
        }
      } finally {
        if (isMounted) {
          setIsReady(true);
        }
      }
    }

    void restoreSession();

    return () => {
      isMounted = false;
    };
  }, []);

  useEffect(() => {
    registerAuthFailureHandler((error) => {
      if (!getStoredEditorialSession()) {
        return;
      }

      clearStoredEditorialSession();
      setSession(null);
      setErrorMessage(
        error.status === 401
          ? 'Sua sessao expirou ou ficou invalida. Faca login novamente.'
          : 'Seu acesso editorial foi encerrado.',
      );
    });

    return () => {
      registerAuthFailureHandler(null);
    };
  }, []);

  async function signIn(credentials: LoginCredentials) {
    const email = credentials.email.trim();

    if (!email || !credentials.password.trim()) {
      throw new Error('Informe email e senha para acessar o CMS.');
    }

    try {
      const authenticatedSession = await loginEditorialUser({
        email,
        password: credentials.password,
      });

      storeEditorialSession(authenticatedSession);
      setSession(authenticatedSession);
      setErrorMessage(null);
    } catch (error) {
      clearStoredEditorialSession();
      setSession(null);

      if (error instanceof ApiError) {
        setErrorMessage(error.message);
        throw error;
      }

      setErrorMessage('Nao foi possivel autenticar o acesso editorial agora.');
      throw error;
    }
  }

  function signOut(reason?: string) {
    clearStoredEditorialSession();
    setSession(null);
    setErrorMessage(reason ?? null);
  }

  function clearError() {
    setErrorMessage(null);
  }

  const value = useMemo<EditorialSessionContextValue>(
    () => ({
      session,
      user: session?.user ?? null,
      isAuthenticated: session !== null,
      isReady,
      errorMessage,
      signIn,
      signOut,
      clearError,
    }),
    [errorMessage, isReady, session],
  );

  return <EditorialSessionContext.Provider value={value}>{children}</EditorialSessionContext.Provider>;
}

export function useEditorialSession() {
  const context = useContext(EditorialSessionContext);

  if (!context) {
    throw new Error('useEditorialSession must be used within EditorialSessionProvider');
  }

  return context;
}
