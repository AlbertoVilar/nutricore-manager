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
  archiveAdminPost,
  deleteAdminPost,
  draftAdminPost,
  getAdminPosts,
  publishAdminPost,
} from '../../services/editorialPostService';
import type { AdminPost } from '../../types/editorial';

export function EditorialPostsPage() {
  const [posts, setPosts] = useState<AdminPost[]>([]);
  const [filter, setFilter] = useState<EditorialStatusFilterValue>('ALL');
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const loadPosts = useCallback(async () => {
    setIsLoading(true);
    setErrorMessage(null);

    try {
      setPosts(await getAdminPosts(filter));
    } catch (error) {
      setErrorMessage(error instanceof Error ? error.message : 'Não foi possível carregar os posts editoriais.');
    } finally {
      setIsLoading(false);
    }
  }, [filter]);

  useEffect(() => {
    void loadPosts();
  }, [loadPosts]);

  async function executeAction(callback: () => Promise<unknown>, confirmationMessage?: string) {
    if (confirmationMessage && !window.confirm(confirmationMessage)) {
      return;
    }

    try {
      await callback();
      await loadPosts();
    } catch (error) {
      setErrorMessage(error instanceof Error ? error.message : 'Não foi possível concluir a ação.');
    }
  }

  return (
    <section className="editorial-stack">
      <div className="editorial-toolbar">
        <div>
          <span className="section-eyebrow">Posts</span>
          <h2>Mensagens rápidas, rotina profissional e conteúdo com vídeo.</h2>
        </div>
        <Link className="button button-primary" to="/editor/posts/novo">
          Novo post
        </Link>
      </div>

      <EditorialStatusFilter onChange={setFilter} value={filter} />

      {isLoading ? <LoadingState message="Carregando posts..." /> : null}
      {errorMessage && !isLoading ? <ErrorState description={errorMessage} /> : null}

      {!isLoading && !errorMessage ? (
        <EditorialContentTable
          createHref="/editor/posts/novo"
          editHrefBase="/editor/posts"
          emptyDescription="Comece por um rascunho e publique quando o material estiver pronto."
          emptyTitle="Nenhum post encontrado para o filtro atual."
          entityLabel="post"
          onArchive={(id) => {
            void executeAction(() => archiveAdminPost(id), 'Arquivar este post?');
          }}
          onDelete={(id) => {
            void executeAction(() => deleteAdminPost(id), 'Excluir este post permanentemente?');
          }}
          onDraft={(id) => {
            void executeAction(() => draftAdminPost(id), 'Mover este post de volta para rascunho?');
          }}
          onPublish={(id) => {
            void executeAction(() => publishAdminPost(id));
          }}
          rows={posts.map((post) => ({
            id: post.id,
            title: post.title,
            slug: post.slug,
            summary: post.summary ?? post.body.slice(0, 120),
            category: post.category,
            featured: post.featured,
            status: post.status,
            updatedAt: post.updatedAt ?? post.createdAt,
            publishedAt: post.publishedAt,
            imageUrl: post.coverImageUrl ?? post.galleryImageUrls[0] ?? null,
          }))}
        />
      ) : null}
    </section>
  );
}
