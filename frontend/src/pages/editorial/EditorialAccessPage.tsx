import { useState } from 'react';
import { Link, Navigate, useLocation, useNavigate } from 'react-router-dom';
import { useEditorialSession } from '../../hooks/useEditorialSession';
import { LoadingState } from '../../components/LoadingState';
import type { LoginCredentials } from '../../types/auth';

interface AccessLocationState {
  from?: {
    pathname: string;
  };
}

export function EditorialAccessPage() {
  const location = useLocation();
  const navigate = useNavigate();
  const { clearError, errorMessage, isAuthenticated, isReady, signIn } = useEditorialSession();
  const [credentials, setCredentials] = useState<LoginCredentials>({
    email: '',
    password: '',
  });
  const [isSubmitting, setIsSubmitting] = useState(false);

  const state = location.state as AccessLocationState | null;
  const destination = state?.from?.pathname ?? '/editor';

  if (!isReady) {
    return <LoadingState message="Verificando sessao editorial..." />;
  }

  if (isAuthenticated) {
    return <Navigate replace to={destination} />;
  }

  async function handleSubmit() {
    setIsSubmitting(true);
    clearError();

    try {
      await signIn(credentials);
      navigate(destination, { replace: true });
    } catch {
      // handled by context error state
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <section className="section editorial-access-section">
      <div className="container editorial-access-grid">
        <div>
          <span className="section-eyebrow">Acesso editorial</span>
          <h1>Area privada para controlar o que vai ao ar no site.</h1>
          <p>
            O ambiente editorial fica separado da navegacao publica e agora exige autenticacao real para editar,
            publicar e arquivar conteudos do site.
          </p>
          <div className="cta-actions">
            <Link className="button button-secondary" to="/">
              Voltar ao site
            </Link>
          </div>
        </div>

        <form
          className="contact-form glass-card editorial-access-card"
          onSubmit={(event) => {
            event.preventDefault();
            void handleSubmit();
          }}
        >
          <div className="form-field">
            <label htmlFor="editorial-email">Email</label>
            <input
              autoComplete="username"
              id="editorial-email"
              onChange={(event) =>
                setCredentials((current) => ({
                  ...current,
                  email: event.target.value,
                }))
              }
              placeholder="nome@exemplo.com"
              required
              type="email"
              value={credentials.email}
            />
          </div>

          <div className="form-field">
            <label htmlFor="editorial-password">Senha</label>
            <input
              autoComplete="current-password"
              id="editorial-password"
              onChange={(event) =>
                setCredentials((current) => ({
                  ...current,
                  password: event.target.value,
                }))
              }
              placeholder="Informe sua senha"
              required
              type="password"
              value={credentials.password}
            />
          </div>

          <p className="form-hint">Use a conta editorial autorizada para entrar no CMS privado da nutricionista.</p>

          {errorMessage ? <p className="form-error">{errorMessage}</p> : null}

          <button className="button button-primary" disabled={isSubmitting} type="submit">
            {isSubmitting ? 'Entrando...' : 'Entrar no CMS'}
          </button>
        </form>
      </div>
    </section>
  );
}
