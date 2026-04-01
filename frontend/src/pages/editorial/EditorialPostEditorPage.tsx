import { useEffect, useMemo, useState } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { PostEditorForm } from '../../components/editorial/PostEditorForm';
import { ErrorState } from '../../components/ErrorState';
import { LoadingState } from '../../components/LoadingState';
import { createAdminPost, getAdminPostById, updateAdminPost } from '../../services/editorialPostService';
import type { AdminPostInput } from '../../types/editorial';

const emptyPost: AdminPostInput = {
  title: '',
  slug: '',
  summary: '',
  body: '',
  coverImageUrl: '',
  galleryImageUrls: [],
  videoUrl: '',
  caption: '',
  category: '',
  featured: false,
  status: 'DRAFT',
  publishedAt: null,
};

export function EditorialPostEditorPage() {
  const { id } = useParams();
  const isEditMode = Boolean(id);
  const navigate = useNavigate();
  const location = useLocation();
  const [initialValue, setInitialValue] = useState<AdminPostInput>(emptyPost);
  const [isLoading, setIsLoading] = useState(isEditMode);
  const [loadError, setLoadError] = useState<string | null>(null);
  const [submitError, setSubmitError] = useState<string | null>(null);
  const [submitSuccess, setSubmitSuccess] = useState<string | null>(
    (location.state as { flash?: string } | null)?.flash ?? null,
  );
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    if (!isEditMode || !id) {
      return;
    }

    async function loadPost() {
      setIsLoading(true);
      setLoadError(null);

      try {
        const response = await getAdminPostById(Number(id));
        setInitialValue({
          title: response.title,
          slug: response.slug,
          summary: response.summary ?? '',
          body: response.body,
          coverImageUrl: response.coverImageUrl ?? '',
          galleryImageUrls: response.galleryImageUrls,
          videoUrl: response.videoUrl ?? '',
          caption: response.caption ?? '',
          category: response.category ?? '',
          featured: response.featured,
          status: response.status,
          publishedAt: response.publishedAt,
        });
      } catch (error) {
        setLoadError(error instanceof Error ? error.message : 'Nao foi possivel carregar o post.');
      } finally {
        setIsLoading(false);
      }
    }

    void loadPost();
  }, [id, isEditMode]);

  async function handleSubmit(payload: AdminPostInput) {
    setIsSubmitting(true);
    setSubmitError(null);
    setSubmitSuccess(null);

    try {
      const response = isEditMode && id
        ? await updateAdminPost(Number(id), payload)
        : await createAdminPost(payload);

      if (!isEditMode) {
        navigate(`/editor/posts/${response.id}/editar`, {
          replace: true,
          state: { flash: 'Post salvo com sucesso.' },
        });
        return;
      }

      setSubmitSuccess('Post atualizado com sucesso.');
    } catch (error) {
      setSubmitError(error instanceof Error ? error.message : 'Nao foi possivel salvar o post.');
    } finally {
      setIsSubmitting(false);
    }
  }

  const title = useMemo(() => (isEditMode ? 'Editar post' : 'Criar post'), [isEditMode]);

  return (
    <section className="editorial-stack">
      <div className="editorial-toolbar">
        <div>
          <span className="section-eyebrow">Posts</span>
          <h2>{title}</h2>
        </div>
        <Link className="button button-secondary" to="/editor/posts">
          Voltar para lista
        </Link>
      </div>

      {isLoading ? <LoadingState message="Carregando dados do post..." /> : null}
      {loadError && !isLoading ? <ErrorState description={loadError} /> : null}

      {!isLoading && !loadError ? (
        <PostEditorForm
          initialValue={initialValue}
          isSubmitting={isSubmitting}
          onSubmit={handleSubmit}
          submitError={submitError}
          submitSuccess={submitSuccess}
        />
      ) : null}
    </section>
  );
}
