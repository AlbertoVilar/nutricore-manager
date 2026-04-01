import { ErrorState } from '../components/ErrorState';
import { LoadingState } from '../components/LoadingState';
import { PageHero } from '../components/PageHero';
import { SectionHeading } from '../components/SectionHeading';
import { TestimonialCard } from '../components/TestimonialCard';
import { servicePillars, testimonials } from '../data/site-content';
import { usePublicSiteData } from '../hooks/usePublicSiteData';
import { resolveAssetUrl } from '../utils/media';

export function AboutPage() {
  const { errors, isLoading, profile, refresh } = usePublicSiteData();

  if (isLoading && !profile) {
    return <LoadingState message="Carregando apresentacao profissional..." />;
  }

  if (!profile) {
    return (
      <section className="section">
        <div className="container">
          <ErrorState
            description="O perfil publico ainda nao esta disponivel para montar a pagina Sobre."
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
        description={`${profile.professionalTitle} em ${profile.city}. Esta pagina usa o perfil institucional vindo do backend e complementa com secoes editoriais locais para sustentar o MVP.`}
        eyebrow="Sobre"
        title={profile.fullName}
      />

      {errors.length > 0 ? (
        <section className="section">
          <div className="container">
            <ErrorState description={errors.join(' ')} />
          </div>
        </section>
      ) : null}

      <section className="section">
        <div className="container two-column-grid">
          <div className="glass-card about-card">
            <SectionHeading
              description="Conteudo institucional vindo diretamente do endpoint de perfil publico."
              eyebrow="Posicionamento"
              title={profile.aboutTitle}
            />
            <p>{profile.aboutDescription}</p>
          </div>

          <div className="hero-image-frame about-image-frame">
            <img alt={profile.fullName} src={resolveAssetUrl(profile.heroImageUrl)} />
          </div>
        </div>
      </section>

      <section className="section section-soft">
        <div className="container">
          <SectionHeading
            centered
            description="Os pilares abaixo seguem locais por enquanto, mas ja encaixados em estrutura pronta para futura administracao via painel."
            eyebrow="Como trabalha"
            title="Uma abordagem pensada para vida real, adesao e resultado mensuravel."
          />

          <div className="card-grid">
            {servicePillars.map((pillar) => (
              <article key={pillar.title} className="glass-card pillar-card">
                <h3>{pillar.title}</h3>
                <p>{pillar.description}</p>
              </article>
            ))}
          </div>
        </div>
      </section>

      <section className="section">
        <div className="container">
          <SectionHeading
            centered
            description="Resultados ilustrativos com os assets da landing original reaproveitados para a camada publica do MVP."
            eyebrow="Percepcao de valor"
            title="Relatos que conectam autoridade, acolhimento e consistencia."
          />

          <div className="testimonial-grid">
            {testimonials.map((testimonial) => (
              <TestimonialCard key={testimonial.name} testimonial={testimonial} />
            ))}
          </div>
        </div>
      </section>
    </>
  );
}
