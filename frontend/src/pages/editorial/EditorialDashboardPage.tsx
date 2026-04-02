import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { EditorialDashboardCard } from '../../components/editorial/EditorialDashboardCard';
import { ErrorState } from '../../components/ErrorState';
import { LoadingState } from '../../components/LoadingState';
import { useEditorialSession } from '../../hooks/useEditorialSession';
import { getAdminArticles } from '../../services/editorialArticleService';
import { getAdminPosts } from '../../services/editorialPostService';
import { getAdminPublicPlans } from '../../services/adminPublicPlanService';
import { getAdminRecipes } from '../../services/editorialRecipeService';
import { getAdminUsers } from '../../services/adminUserService';
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
  const { user } = useEditorialSession();
  const [items, setItems] = useState<EditorialSummary[]>([]);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    async function loadDashboard() {
      setIsLoading(true);
      setErrorMessage(null);

      try {
        const [posts, articles, recipes, plans, users] = await Promise.all([
          getAdminPosts(),
          getAdminArticles(),
          getAdminRecipes(),
          getAdminPublicPlans(),
          user?.role === 'ADMIN' ? getAdminUsers() : Promise.resolve([]),
        ]);

        setItems([
          createSummary('Site público', '/editor/site', []),
          createSummary('Planos', '/editor/planos', plans.filter((item) => item.active).map(() => 'PUBLISHED')),
          createSummary('Posts', '/editor/posts', posts.map((item) => item.status)),
          createSummary('Artigos', '/editor/articles', articles.map((item) => item.status)),
          createSummary('Receitas', '/editor/recipes', recipes.map((item) => item.status)),
          ...(user?.role === 'ADMIN'
            ? [createSummary('Usuários', '/editor/usuarios', users.filter((item) => item.active).map(() => 'PUBLISHED'))]
            : []),
        ]);
      } catch (error) {
        setErrorMessage(error instanceof Error ? error.message : 'Não foi possível carregar o dashboard administrativo.');
      } finally {
        setIsLoading(false);
      }
    }

    void loadDashboard();
  }, [user?.role]);

  if (isLoading) {
    return <LoadingState message="Carregando indicadores administrativos..." />;
  }

  if (errorMessage) {
    return <ErrorState description={errorMessage} />;
  }

  return (
    <div className="editorial-stack">
      <section className="editorial-header-card glass-card">
        <div>
          <span className="section-eyebrow">Resumo</span>
          <h2>Site, planos, conteúdo editorial e acessos privados em um só painel.</h2>
          <p>
            O público vê apenas o que está configurado como ativo ou publicado. A área privada concentra operação
            comercial, institucional e editorial com autenticação real.
          </p>
          {user ? <p className="form-hint">Sessão ativa: {user.fullName} ({user.role})</p> : null}
          <div className="cta-actions">
            <Link className="button button-secondary" to="/editor/site">
              Configurar site
            </Link>
            <Link className="button button-secondary" to="/editor/planos">
              Gerenciar planos
            </Link>
            <Link className="button button-primary" to="/editor/posts/novo">
              Novo post
            </Link>
            <Link className="button button-secondary" to="/editor/articles/novo">
              Novo artigo
            </Link>
            <Link className="button button-secondary" to="/editor/recipes/novo">
              Nova receita
            </Link>
            {user?.role === 'ADMIN' ? (
              <Link className="button button-tertiary" to="/editor/usuarios">
                Gerenciar usuários
              </Link>
            ) : null}
            <Link className="button button-tertiary" to="/">
              Ver site público
            </Link>
          </div>
        </div>
      </section>

      <section className="editorial-dashboard-grid">
        {items.map((item) => (
          <div key={item.label} className="editorial-dashboard-stack">
            <EditorialDashboardCard
              description={
                item.label === 'Site público'
                  ? 'Hero, seções institucionais, contatos, imagens e conteúdo comercial.'
                  : `${item.published} ativos/publicados, ${item.draft} em rascunho e ${item.archived} arquivados/inativos.`
              }
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
