import { ErrorState } from '../components/ErrorState';
import { ContactCtaForm } from '../components/ContactCtaForm';
import { LoadingState } from '../components/LoadingState';
import { PageHero } from '../components/PageHero';
import { SectionHeading } from '../components/SectionHeading';
import { usePublicSiteData } from '../hooks/usePublicSiteData';
import { sanitizeInstagramHandle } from '../utils/formatters';

export function ContactPage() {
  const { isLoading, profile, refresh } = usePublicSiteData();

  if (isLoading && !profile) {
    return <LoadingState message="Carregando canais de contato..." />;
  }

  if (!profile) {
    return (
      <section className="section">
        <div className="container">
          <ErrorState
            description="Sem perfil público não há dados suficientes para montar a página de contato."
            onRetry={() => {
              void refresh();
            }}
          />
        </div>
      </section>
    );
  }

  return (
    <>
      <PageHero
        description="Use os canais abaixo para tirar dúvidas, entender o atendimento e iniciar a conversa sobre sua rotina."
        eyebrow="Contato"
        title="Fale com a nutricionista e leve sua rotina para um plano viável."
      />

      <section className="section">
        <div className="container contact-grid">
          <div>
            <SectionHeading
              description="Os canais abaixo facilitam o primeiro contato. O formulário ao lado organiza uma mensagem inicial para continuar no WhatsApp."
              eyebrow="Canais ativos"
              title="Contato direto para avaliação, planos e orientações iniciais."
            />

            <div className="contact-card-grid">
              <article className="glass-card contact-card">
                <span className="section-eyebrow">Email</span>
                <h3>{profile.contactEmail}</h3>
                <a href={`mailto:${profile.contactEmail}`}>Enviar e-mail</a>
              </article>

              <article className="glass-card contact-card">
                <span className="section-eyebrow">WhatsApp</span>
                <h3>{profile.contactPhone}</h3>
                <a href={profile.primaryCtaUrl} rel="noreferrer" target="_blank">
                  Abrir conversa
                </a>
              </article>

              <article className="glass-card contact-card">
                <span className="section-eyebrow">Instagram</span>
                <h3>{profile.instagramHandle}</h3>
                <a
                  href={`https://instagram.com/${sanitizeInstagramHandle(profile.instagramHandle)}`}
                  rel="noreferrer"
                  target="_blank"
                >
                  Ver perfil
                </a>
              </article>
            </div>
          </div>

          <ContactCtaForm contactPhone={profile.contactPhone} fallbackUrl={profile.primaryCtaUrl} />
        </div>
      </section>
    </>
  );
}
