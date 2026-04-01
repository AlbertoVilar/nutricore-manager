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
            <Link className="button button-primary" to="/contato">
              Quero uma avaliação
            </Link>
          </div>
        }
        description="Planos pensados para diferentes momentos da jornada, com clareza sobre apoio, acompanhamento e ritmo de evolução."
        eyebrow="Planos"
        title="Opções de acompanhamento para diferentes fases da jornada."
      />

      <section className="section plans-section">
        <div className="container">
          <SectionHeading
            description="Cada plano organiza o nível de proximidade, revisão e suporte para ajudar a transformar orientação em aderência."
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
            <h2>{profile?.primaryCtaLabel ?? 'Agendar avaliação'}</h2>
            <p>
              Se você quiser entender qual formato faz mais sentido para o seu momento, a conversa inicial segue por
              canais diretos com a nutricionista.
            </p>
          </div>
          <div className="cta-actions">
            <a
              className="button button-primary"
              href={profile?.primaryCtaUrl ?? '/contato'}
              rel="noreferrer"
              target="_blank"
            >
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
