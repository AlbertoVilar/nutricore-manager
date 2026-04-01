import {
  createContext,
  useContext,
  useEffect,
  useMemo,
  useState,
  type ReactNode,
} from 'react';
import { ApiError } from '../services/httpClient';
import { getAdminPosts } from '../services/editorialPostService';
import {
  clearStoredEditorialToken,
  getStoredEditorialToken,
  storeEditorialToken,
} from '../services/editorialSessionService';

interface EditorialSessionContextValue {
  token: string;
  isAuthenticated: boolean;
  isReady: boolean;
  errorMessage: string | null;
  signIn: (token: string) => Promise<void>;
  signOut: () => void;
  clearError: () => void;
}

const EditorialSessionContext = createContext<EditorialSessionContextValue | undefined>(undefined);

interface EditorialSessionProviderProps {
  children: ReactNode;
}

export function EditorialSessionProvider({ children }: EditorialSessionProviderProps) {
  const [token, setToken] = useState('');
  const [isReady, setIsReady] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  useEffect(() => {
    setToken(getStoredEditorialToken());
    setIsReady(true);
  }, []);

  async function signIn(nextToken: string) {
    const trimmedToken = nextToken.trim();

    if (!trimmedToken) {
      throw new Error('Informe o token editorial provisorio.');
    }

    try {
      await getAdminPosts(trimmedToken, 'ALL');
      storeEditorialToken(trimmedToken);
      setToken(trimmedToken);
      setErrorMessage(null);
    } catch (error) {
      clearStoredEditorialToken();
      setToken('');

      if (error instanceof ApiError) {
        setErrorMessage(error.message);
        throw error;
      }

      setErrorMessage('Nao foi possivel validar o acesso editorial agora.');
      throw error;
    }
  }

  function signOut() {
    clearStoredEditorialToken();
    setToken('');
    setErrorMessage(null);
  }

  function clearError() {
    setErrorMessage(null);
  }

  const value = useMemo<EditorialSessionContextValue>(
    () => ({
      token,
      isAuthenticated: token.trim().length > 0,
      isReady,
      errorMessage,
      signIn,
      signOut,
      clearError,
    }),
    [errorMessage, isReady, token],
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
