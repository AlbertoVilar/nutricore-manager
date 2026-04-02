import { Link } from 'react-router-dom';
import { ErrorState } from '../components/ErrorState';
import { LoadingState } from '../components/LoadingState';
import { PageHero } from '../components/PageHero';
import { PlanCard } from '../components/PlanCard';
import { SectionHeading } from '../components/SectionHeading';
import { usePublicSiteData } from '../hooks/usePublicSiteData';

export function PlansPage() {
  const { errors, isLoading, plans, profile, refresh } = usePublicSiteData();

  if (isLoading && plans.length === 0) {
    return <LoadingState message="Carregando planos de atendimento..." />;
  }

  return (
    <>
      <PageHero
        actions={
          <div className="page-hero-actions">
            <a className="button button-primary" href={profile?.primaryCtaUrl ?? '/contato'} rel="noreferrer" target="_blank">
              {profile?.primaryCtaLabel ?? 'Quero uma avaliação'}
            </a>
          </div>
        }
        description={profile?.plansDescription ?? 'Planos pensados para diferentes momentos da jornada.'}
        eyebrow="Planos"
        title={profile?.plansTitle ?? 'Opções de acompanhamento'}
      />

      <section className="section plans-section">
        <div className="container">
          <SectionHeading
            description={profile?.plansDescription ?? 'Cada plano organiza o nível de proximidade, revisão e suporte.'}
            eyebrow="Atendimento"
            title="Comparação objetiva entre níveis de acompanhamento."
          />

          {errors.plans ? (
            <ErrorState
              description={errors.plans}
              onRetry={() => {
                void refresh();
              }}
            />
          ) : (
            <div className="plan-grid">
              {plans.map((plan) => (
                <PlanCard key={plan.id} plan={plan} />
              ))}
            </div>
          )}
        </div>
      </section>

      <section className="section section-soft">
        <div className="container cta-banner">
          <div>
            <span className="section-eyebrow">Próximo passo</span>
            <h2>{profile?.finalCtaTitle ?? 'Agendar avaliação'}</h2>
            <p>{profile?.finalCtaDescription ?? 'Converse com a nutricionista para entender o formato ideal.'}</p>
          </div>
          <div className="cta-actions">
            <a className="button button-primary" href={profile?.primaryCtaUrl ?? '/contato'} rel="noreferrer" target="_blank">
              Abrir conversa
            </a>
            <Link className="button button-tertiary" to="/contato">
              Ver canais de contato
            </Link>
          </div>
        </div>
      </section>
    </>
  );
}
