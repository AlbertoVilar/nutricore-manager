import { useCallback, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { EditorialContentTable } from '../../components/editorial/EditorialContentTable';
import {
  EditorialStatusFilter,
  type EditorialStatusFilterValue,
} from '../../components/editorial/EditorialStatusFilter';
import { ErrorState } from '../../components/ErrorState';
import { LoadingState } from '../../components/LoadingState';
import {
  archiveAdminArticle,
  deleteAdminArticle,
  draftAdminArticle,
  getAdminArticles,
  publishAdminArticle,
} from '../../services/editorialArticleService';
import type { AdminArticle } from '../../types/editorial';

export function EditorialArticlesPage() {
  const [articles, setArticles] = useState<AdminArticle[]>([]);
  const [filter, setFilter] = useState<EditorialStatusFilterValue>('ALL');
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const loadArticles = useCallback(async () => {
    setIsLoading(true);
    setErrorMessage(null);

    try {
      setArticles(await getAdminArticles(filter));
    } catch (error) {
      setErrorMessage(error instanceof Error ? error.message : 'Não foi possível carregar os artigos.');
    } finally {
      setIsLoading(false);
    }
  }, [filter]);

  useEffect(() => {
    void loadArticles();
  }, [loadArticles]);

  async function executeAction(callback: () => Promise<unknown>, confirmationMessage?: string) {
    if (confirmationMessage && !window.confirm(confirmationMessage)) {
      return;
    }

    try {
      await callback();
      await loadArticles();
    } catch (error) {
      setErrorMessage(error instanceof Error ? error.message : 'Não foi possível concluir a ação.');
    }
  }

  return (
    <section className="editorial-stack">
      <div className="editorial-toolbar">
        <div>
          <span className="section-eyebrow">Artigos</span>
          <h2>Conteúdos densos com resumo, tags e leitura estimada.</h2>
        </div>
        <Link className="button button-primary" to="/editor/articles/novo">
          Novo artigo
        </Link>
      </div>

      <EditorialStatusFilter onChange={setFilter} value={filter} />

      {isLoading ? <LoadingState message="Carregando artigos..." /> : null}
      {errorMessage && !isLoading ? <ErrorState description={errorMessage} /> : null}

      {!isLoading && !errorMessage ? (
        <EditorialContentTable
          createHref="/editor/articles/novo"
          editHrefBase="/editor/articles"
          emptyDescription="Os artigos estruturam a autoridade da nutricionista e a biblioteca publica."
          emptyTitle="Nenhum artigo encontrado para o filtro atual."
          entityLabel="artigo"
          onArchive={(id) => {
            void executeAction(() => archiveAdminArticle(id), 'Arquivar este artigo?');
          }}
          onDelete={(id) => {
            void executeAction(() => deleteAdminArticle(id), 'Excluir este artigo permanentemente?');
          }}
          onDraft={(id) => {
            void executeAction(() => draftAdminArticle(id), 'Mover este artigo de volta para rascunho?');
          }}
          onPublish={(id) => {
            void executeAction(() => publishAdminArticle(id));
          }}
          rows={articles.map((article) => ({
            id: article.id,
            title: article.title,
            slug: article.slug,
            summary: article.summary,
            category: article.category,
            featured: article.featured,
            status: article.status,
            updatedAt: article.updatedAt ?? article.createdAt,
            publishedAt: article.publishedAt,
            imageUrl: article.coverImageUrl,
          }))}
        />
      ) : null}
    </section>
  );
}
