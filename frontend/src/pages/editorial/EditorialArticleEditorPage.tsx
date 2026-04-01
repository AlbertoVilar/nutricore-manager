import { useEffect, useMemo, useState } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { ArticleEditorForm } from '../../components/editorial/ArticleEditorForm';
import { ErrorState } from '../../components/ErrorState';
import { LoadingState } from '../../components/LoadingState';
import { createAdminArticle, getAdminArticleById, updateAdminArticle } from '../../services/editorialArticleService';
import type { AdminArticleInput } from '../../types/editorial';

const emptyArticle: AdminArticleInput = {
  title: '',
  slug: '',
  summary: '',
  body: '',
  coverImageUrl: '',
  tags: [],
  category: '',
  readTimeMinutes: null,
  featured: false,
  status: 'DRAFT',
  publishedAt: null,
};

export function EditorialArticleEditorPage() {
  const { id } = useParams();
  const isEditMode = Boolean(id);
  const navigate = useNavigate();
  const location = useLocation();
  const [initialValue, setInitialValue] = useState<AdminArticleInput>(emptyArticle);
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

    async function loadArticle() {
      setIsLoading(true);
      setLoadError(null);

      try {
        const response = await getAdminArticleById(Number(id));
        setInitialValue({
          title: response.title,
          slug: response.slug,
          summary: response.summary,
          body: response.body,
          coverImageUrl: response.coverImageUrl ?? '',
          tags: response.tags,
          category: response.category ?? '',
          readTimeMinutes: response.readTimeMinutes,
          featured: response.featured,
          status: response.status,
          publishedAt: response.publishedAt,
        });
      } catch (error) {
        setLoadError(error instanceof Error ? error.message : 'Nao foi possivel carregar o artigo.');
      } finally {
        setIsLoading(false);
      }
    }

    void loadArticle();
  }, [id, isEditMode]);

  async function handleSubmit(payload: AdminArticleInput) {
    setIsSubmitting(true);
    setSubmitError(null);
    setSubmitSuccess(null);

    try {
      const response = isEditMode && id
        ? await updateAdminArticle(Number(id), payload)
        : await createAdminArticle(payload);

      if (!isEditMode) {
        navigate(`/editor/articles/${response.id}/editar`, {
          replace: true,
          state: { flash: 'Artigo salvo com sucesso.' },
        });
        return;
      }

      setSubmitSuccess('Artigo atualizado com sucesso.');
    } catch (error) {
      setSubmitError(error instanceof Error ? error.message : 'Nao foi possivel salvar o artigo.');
    } finally {
      setIsSubmitting(false);
    }
  }

  const title = useMemo(() => (isEditMode ? 'Editar artigo' : 'Criar artigo'), [isEditMode]);

  return (
    <section className="editorial-stack">
      <div className="editorial-toolbar">
        <div>
          <span className="section-eyebrow">Artigos</span>
          <h2>{title}</h2>
        </div>
        <Link className="button button-secondary" to="/editor/articles">
          Voltar para lista
        </Link>
      </div>

      {isLoading ? <LoadingState message="Carregando dados do artigo..." /> : null}
      {loadError && !isLoading ? <ErrorState description={loadError} /> : null}

      {!isLoading && !loadError ? (
        <ArticleEditorForm
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
