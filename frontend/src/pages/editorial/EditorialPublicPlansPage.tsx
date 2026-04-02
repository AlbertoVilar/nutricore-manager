import { useCallback, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ErrorState } from '../../components/ErrorState';
import { LoadingState } from '../../components/LoadingState';
import { usePublicSiteData } from '../../hooks/usePublicSiteData';
import {
  activateAdminPublicPlan,
  deactivateAdminPublicPlan,
  deleteAdminPublicPlan,
  getAdminPublicPlans,
} from '../../services/adminPublicPlanService';
import type { AdminPublicPlan } from '../../types/admin-site';
import { formatDateTime } from '../../utils/formatters';

export function EditorialPublicPlansPage() {
  const { refresh } = usePublicSiteData();
  const [plans, setPlans] = useState<AdminPublicPlan[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const loadPlans = useCallback(async () => {
    setIsLoading(true);
    setErrorMessage(null);

    try {
      setPlans(await getAdminPublicPlans());
    } catch (error) {
      setErrorMessage(error instanceof Error ? error.message : 'Não foi possível carregar os planos públicos.');
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    void loadPlans();
  }, [loadPlans]);

  async function executeAction(callback: () => Promise<unknown>, confirmationMessage?: string) {
    if (confirmationMessage && !window.confirm(confirmationMessage)) {
      return;
    }

    try {
      await callback();
      await loadPlans();
      await refresh();
    } catch (error) {
      setErrorMessage(error instanceof Error ? error.message : 'Não foi possível concluir a ação.');
    }
  }

  return (
    <section className="editorial-stack">
      <div className="editorial-toolbar">
        <div>
          <span className="section-eyebrow">Planos públicos</span>
          <h2>Ofertas comerciais exibidas no site</h2>
          <p>Crie, reorganize, ative ou retire planos do ar sem depender de código.</p>
        </div>
        <Link className="button button-primary" to="/editor/planos/novo">
          Novo plano
        </Link>
      </div>

      {isLoading ? <LoadingState message="Carregando planos..." /> : null}
      {errorMessage && !isLoading ? <ErrorState description={errorMessage} /> : null}

      {!isLoading && !errorMessage ? (
        plans.length > 0 ? (
          <div className="editorial-management-grid">
            {plans.map((plan) => (
              <article key={plan.id} className={`glass-card editorial-management-card${plan.active ? '' : ' is-muted'}`}>
                <div className="editorial-management-header">
                  <div>
                    <span className="section-eyebrow">Ordem {plan.displayOrder}</span>
                    <h3>{plan.title}</h3>
                    <p>{plan.subtitle}</p>
                  </div>
                  <div className="editorial-management-badges">
                    <span className={`editorial-status-badge ${plan.active ? 'published' : 'archived'}`}>
                      {plan.active ? 'Ativo' : 'Inativo'}
                    </span>
                    {plan.featured ? <span className="editorial-featured-pill">Destaque</span> : null}
                  </div>
                </div>

                <strong className="plan-price">{plan.priceLabel}</strong>
                <p className="editorial-management-summary">{plan.summary}</p>

                <ul className="plan-features">
                  {plan.features.map((feature) => (
                    <li key={feature}>{feature}</li>
                  ))}
                </ul>

                <div className="editorial-management-meta">
                  <span>CTA: {plan.ctaLabel}</span>
                  <span>Atualizado: {formatDateTime(plan.updatedAt ?? plan.createdAt)}</span>
                </div>

                <div className="editorial-management-actions">
                  <Link className="button button-secondary" to={`/editor/planos/${plan.id}/editar`}>
                    Editar
                  </Link>
                  {plan.active ? (
                    <button
                      className="button button-tertiary"
                      onClick={() => {
                        void executeAction(() => deactivateAdminPublicPlan(plan.id), 'Desativar este plano no site público?');
                      }}
                      type="button"
                    >
                      Desativar
                    </button>
                  ) : (
                    <button
                      className="button button-primary"
                      onClick={() => {
                        void executeAction(() => activateAdminPublicPlan(plan.id));
                      }}
                      type="button"
                    >
                      Ativar
                    </button>
                  )}
                  <button
                    className="button button-tertiary button-danger"
                    onClick={() => {
                      void executeAction(() => deleteAdminPublicPlan(plan.id), 'Excluir este plano permanentemente?');
                    }}
                    type="button"
                  >
                    Excluir
                  </button>
                </div>
              </article>
            ))}
          </div>
        ) : (
          <div className="glass-card editorial-empty-state">
            <h3>Nenhum plano cadastrado.</h3>
            <p>Crie o primeiro plano comercial para começar a alimentar a página pública.</p>
            <Link className="button button-primary" to="/editor/planos/novo">
              Criar plano
            </Link>
          </div>
        )
      ) : null}
    </section>
  );
}
