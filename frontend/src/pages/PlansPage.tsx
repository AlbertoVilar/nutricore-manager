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

  const planError = errors.find((item) => item.includes('planos'));

  return (
    <>
      <PageHero
        actions={
          <div className="page-hero-actions">
            <Link className="button button-primary" to="/contato">
              Quero uma avaliacao
            </Link>
          </div>
        }
        description="Planos claros para transformar interesse em conversa comercial e preparar a entrada do paciente na camada clinica."
        eyebrow="Planos"
        title="Opcoes de acompanhamento para diferentes fases da jornada."
      />

      <section className="section plans-section">
        <div className="container">
          <SectionHeading
            description="Conteudo vindo do backend, pronto para ser evoluido futuramente por uma area protegida."
            eyebrow="Oferta publica"
            title="Comparacao objetiva entre niveis de acompanhamento."
          />

          {planError ? (
            <ErrorState
              description={planError}
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
            <span className="section-eyebrow">Encaminhamento comercial</span>
            <h2>{profile?.primaryCtaLabel ?? 'Agendar avaliacao'}</h2>
            <p>
              O fluxo atual do MVP leva a conversa para WhatsApp e contato direto, sem mascarar
              funcionalidades administrativas que ainda nao entraram.
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
