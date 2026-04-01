import { Link } from 'react-router-dom';
import { PageHero } from '../components/PageHero';

export function NotFoundPage() {
  return (
    <>
      <PageHero
        description="A pagina que voce tentou acessar nao esta disponivel."
        eyebrow="404"
        title="Pagina nao encontrada."
      />

      <section className="section">
        <div className="container section-actions">
          <Link className="button button-primary" to="/">
            Voltar para a home
          </Link>
        </div>
      </section>
    </>
  );
}
