import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { EditorialDashboardCard } from '../../components/editorial/EditorialDashboardCard';
import { ErrorState } from '../../components/ErrorState';
import { LoadingState } from '../../components/LoadingState';
import { useEditorialSession } from '../../hooks/useEditorialSession';
import { getAdminArticles } from '../../services/editorialArticleService';
import { getAdminPosts } from '../../services/editorialPostService';
import { getAdminRecipes } from '../../services/editorialRecipeService';
import type { EditorialStatus } from '../../types/editorial';

interface EditorialSummary {
  label: string;
  total: number;
  draft: number;
  published: number;
  archived: number;
  href: string;
}

export function EditorialDashboardPage() {
  const { token } = useEditorialSession();
  const [items, setItems] = useState<EditorialSummary[]>([]);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    async function loadDashboard() {
      setIsLoading(true);
      setErrorMessage(null);

      try {
        const [posts, articles, recipes] = await Promise.all([
          getAdminPosts(token),
          getAdminArticles(token),
          getAdminRecipes(token),
        ]);

        setItems([
          createSummary('Posts', '/editor/posts', posts.map((item) => item.status)),
          createSummary('Artigos', '/editor/articles', articles.map((item) => item.status)),
          createSummary('Receitas', '/editor/recipes', recipes.map((item) => item.status)),
        ]);
      } catch (error) {
        setErrorMessage(error instanceof Error ? error.message : 'Nao foi possivel carregar o dashboard editorial.');
      } finally {
        setIsLoading(false);
      }
    }

    void loadDashboard();
  }, [token]);

  if (isLoading) {
    return <LoadingState message="Carregando indicadores editoriais..." />;
  }

  if (errorMessage) {
    return <ErrorState description={errorMessage} />;
  }

  return (
    <div className="editorial-stack">
      <section className="editorial-header-card glass-card">
        <div>
          <span className="section-eyebrow">Resumo</span>
          <h2>O que esta publicado, o que segue em rascunho e o que saiu do ar.</h2>
          <p>
            O CMS organiza tudo em torno de rascunho, publicacao e arquivamento. O publico so enxerga o que esta em{' '}
            <strong>PUBLISHED</strong>.
          </p>
          <div className="cta-actions">
            <Link className="button button-primary" to="/editor/posts/novo">
              Novo post
            </Link>
            <Link className="button button-secondary" to="/editor/articles/novo">
              Novo artigo
            </Link>
            <Link className="button button-secondary" to="/editor/recipes/novo">
              Nova receita
            </Link>
            <Link className="button button-tertiary" to="/">
              Ver site publico
            </Link>
          </div>
        </div>
      </section>

      <section className="editorial-dashboard-grid">
        {items.map((item) => (
          <div key={item.label} className="editorial-dashboard-stack">
            <EditorialDashboardCard
              description={`${item.published} publicados, ${item.draft} rascunhos e ${item.archived} arquivados.`}
              title={item.label}
              value={String(item.total)}
            />
            <Link className="button button-secondary" to={item.href}>
              Gerenciar {item.label.toLowerCase()}
            </Link>
          </div>
        ))}
      </section>
    </div>
  );
}

function createSummary(label: string, href: string, statuses: EditorialStatus[]): EditorialSummary {
  const countByStatus = statuses.reduce<Record<EditorialStatus, number>>(
    (accumulator, status) => ({
      ...accumulator,
      [status]: accumulator[status] + 1,
    }),
    {
      DRAFT: 0,
      PUBLISHED: 0,
      ARCHIVED: 0,
    },
  );

  return {
    label,
    href,
    total: statuses.length,
    draft: countByStatus.DRAFT,
    published: countByStatus.PUBLISHED,
    archived: countByStatus.ARCHIVED,
  };
}
