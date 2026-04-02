import { useEffect, useMemo, useState } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { PublicPlanForm } from '../../components/editorial/PublicPlanForm';
import { ErrorState } from '../../components/ErrorState';
import { LoadingState } from '../../components/LoadingState';
import { usePublicSiteData } from '../../hooks/usePublicSiteData';
import {
  createAdminPublicPlan,
  getAdminPublicPlanById,
  updateAdminPublicPlan,
} from '../../services/adminPublicPlanService';
import type { AdminPublicPlan, AdminPublicPlanInput } from '../../types/admin-site';

const emptyPlan: AdminPublicPlanInput = {
  title: '',
  subtitle: '',
  priceLabel: '',
  summary: '',
  features: [],
  featured: false,
  active: true,
  ctaLabel: '',
  ctaUrl: '',
  displayOrder: 1,
};

function toFormValue(plan: AdminPublicPlan): AdminPublicPlanInput {
  return {
    title: plan.title,
    subtitle: plan.subtitle,
    priceLabel: plan.priceLabel,
    summary: plan.summary,
    features: plan.features,
    featured: plan.featured,
    active: plan.active,
    ctaLabel: plan.ctaLabel,
    ctaUrl: plan.ctaUrl,
    displayOrder: plan.displayOrder,
  };
}

export function EditorialPublicPlanEditorPage() {
  const { refresh } = usePublicSiteData();
  const { id } = useParams();
  const isEditMode = Boolean(id);
  const navigate = useNavigate();
  const location = useLocation();
  const [initialValue, setInitialValue] = useState<AdminPublicPlanInput>(emptyPlan);
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

    async function loadPlan() {
      setIsLoading(true);
      setLoadError(null);

      try {
        const response = await getAdminPublicPlanById(Number(id));
        setInitialValue(toFormValue(response));
      } catch (error) {
        setLoadError(error instanceof Error ? error.message : 'Não foi possível carregar o plano público.');
      } finally {
        setIsLoading(false);
      }
    }

    void loadPlan();
  }, [id, isEditMode]);

  async function handleSubmit(payload: AdminPublicPlanInput) {
    setIsSubmitting(true);
    setSubmitError(null);
    setSubmitSuccess(null);

    try {
      const response = isEditMode && id
        ? await updateAdminPublicPlan(Number(id), payload)
        : await createAdminPublicPlan(payload);
      await refresh();

      if (!isEditMode) {
        navigate(`/editor/planos/${response.id}/editar`, {
          replace: true,
          state: { flash: 'Plano público salvo com sucesso.' },
        });
        return;
      }

      setSubmitSuccess('Plano público atualizado com sucesso.');
    } catch (error) {
      setSubmitError(error instanceof Error ? error.message : 'Não foi possível salvar o plano público.');
    } finally {
      setIsSubmitting(false);
    }
  }

  const title = useMemo(() => (isEditMode ? 'Editar plano' : 'Criar plano'), [isEditMode]);

  return (
    <section className="editorial-stack">
      <div className="editorial-toolbar">
        <div>
          <span className="section-eyebrow">Planos públicos</span>
          <h2>{title}</h2>
        </div>
        <Link className="button button-secondary" to="/editor/planos">
          Voltar para lista
        </Link>
      </div>

      {isLoading ? <LoadingState message="Carregando dados do plano..." /> : null}
      {loadError && !isLoading ? <ErrorState description={loadError} /> : null}

      {!isLoading && !loadError ? (
        <PublicPlanForm
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
