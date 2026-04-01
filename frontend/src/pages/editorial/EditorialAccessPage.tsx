import { useState } from 'react';
import { Navigate, useLocation, useNavigate } from 'react-router-dom';
import { useEditorialSession } from '../../hooks/useEditorialSession';

interface AccessLocationState {
  from?: {
    pathname: string;
  };
}

export function EditorialAccessPage() {
  const location = useLocation();
  const navigate = useNavigate();
  const { clearError, errorMessage, isAuthenticated, signIn } = useEditorialSession();
  const [tokenInput, setTokenInput] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const state = location.state as AccessLocationState | null;
  const destination = state?.from?.pathname ?? '/editor';

  if (isAuthenticated) {
    return <Navigate replace to={destination} />;
  }

  async function handleSubmit() {
    setIsSubmitting(true);
    clearError();

    try {
      await signIn(tokenInput);
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
          <h1>Camada privada para controlar posts, artigos e receitas.</h1>
          <p>
            Esta etapa usa um token provisorio de administracao para separar a area editorial da area publica. O
            desenho ja deixa um ponto claro para substituir isso por autenticacao real da nutricionista depois.
          </p>
        </div>

        <form
          className="contact-form glass-card editorial-access-card"
          onSubmit={(event) => {
            event.preventDefault();
            void handleSubmit();
          }}
        >
          <div className="form-field">
            <label htmlFor="editorial-token">Token editorial provisorio</label>
            <input
              id="editorial-token"
              onChange={(event) => setTokenInput(event.target.value)}
              placeholder="Informe o token do ambiente"
              required
              type="password"
              value={tokenInput}
            />
          </div>

          <p className="form-hint">
            O backend exige o header <code>X-Editorial-Token</code> para todas as rotas <code>/api/v1/admin/*</code>.
          </p>

          {errorMessage ? <p className="form-error">{errorMessage}</p> : null}

          <button className="button button-primary" disabled={isSubmitting} type="submit">
            {isSubmitting ? 'Validando...' : 'Entrar no CMS'}
          </button>
        </form>
      </div>
    </section>
  );
}
