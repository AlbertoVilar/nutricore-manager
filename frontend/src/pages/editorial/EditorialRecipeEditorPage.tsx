import { useEffect, useMemo, useState } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { RecipeEditorForm } from '../../components/editorial/RecipeEditorForm';
import { ErrorState } from '../../components/ErrorState';
import { LoadingState } from '../../components/LoadingState';
import { useEditorialSession } from '../../hooks/useEditorialSession';
import { createAdminRecipe, getAdminRecipeById, updateAdminRecipe } from '../../services/editorialRecipeService';
import type { AdminRecipeInput } from '../../types/editorial';

const emptyRecipe: AdminRecipeInput = {
  title: '',
  slug: '',
  description: '',
  imageUrl: '',
  ingredients: [],
  preparationSteps: [],
  prepTimeMinutes: 20,
  yieldInfo: '',
  category: '',
  featured: false,
  status: 'DRAFT',
  publishedAt: null,
};

export function EditorialRecipeEditorPage() {
  const { id } = useParams();
  const isEditMode = Boolean(id);
  const { token } = useEditorialSession();
  const navigate = useNavigate();
  const location = useLocation();
  const [initialValue, setInitialValue] = useState<AdminRecipeInput>(emptyRecipe);
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

    async function loadRecipe() {
      setIsLoading(true);
      setLoadError(null);

      try {
        const response = await getAdminRecipeById(token, Number(id));
        setInitialValue({
          title: response.title,
          slug: response.slug,
          description: response.description,
          imageUrl: response.imageUrl ?? '',
          ingredients: response.ingredients,
          preparationSteps: response.preparationSteps,
          prepTimeMinutes: response.prepTimeMinutes,
          yieldInfo: response.yieldInfo,
          category: response.category ?? '',
          featured: response.featured,
          status: response.status,
          publishedAt: response.publishedAt,
        });
      } catch (error) {
        setLoadError(error instanceof Error ? error.message : 'Nao foi possivel carregar a receita.');
      } finally {
        setIsLoading(false);
      }
    }

    void loadRecipe();
  }, [id, isEditMode, token]);

  async function handleSubmit(payload: AdminRecipeInput) {
    setIsSubmitting(true);
    setSubmitError(null);
    setSubmitSuccess(null);

    try {
      const response = isEditMode && id
        ? await updateAdminRecipe(token, Number(id), payload)
        : await createAdminRecipe(token, payload);

      if (!isEditMode) {
        navigate(`/editor/recipes/${response.id}/editar`, {
          replace: true,
          state: { flash: 'Receita salva com sucesso.' },
        });
        return;
      }

      setSubmitSuccess('Receita atualizada com sucesso.');
    } catch (error) {
      setSubmitError(error instanceof Error ? error.message : 'Nao foi possivel salvar a receita.');
    } finally {
      setIsSubmitting(false);
    }
  }

  const title = useMemo(() => (isEditMode ? 'Editar receita' : 'Criar receita'), [isEditMode]);

  return (
    <section className="editorial-stack">
      <div className="editorial-toolbar">
        <div>
          <span className="section-eyebrow">Receitas</span>
          <h2>{title}</h2>
        </div>
        <Link className="button button-secondary" to="/editor/recipes">
          Voltar para lista
        </Link>
      </div>

      {isLoading ? <LoadingState message="Carregando dados da receita..." /> : null}
      {loadError && !isLoading ? <ErrorState description={loadError} /> : null}

      {!isLoading && !loadError ? (
        <RecipeEditorForm
          initialValue={initialValue}
          isSubmitting={isSubmitting}
          onSubmit={handleSubmit}
          submitError={submitError}
          submitSuccess={submitSuccess}
          token={token}
        />
      ) : null}
    </section>
  );
}
