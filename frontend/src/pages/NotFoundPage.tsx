import { Link } from 'react-router-dom';
import { PageHero } from '../components/PageHero';

export function NotFoundPage() {
  return (
    <>
      <PageHero
        description="A página que você tentou acessar não está disponível."
        eyebrow="404"
        title="Página não encontrada."
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
