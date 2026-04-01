import { ErrorState } from '../components/ErrorState';
import { LoadingState } from '../components/LoadingState';
import { PageHero } from '../components/PageHero';
import { SectionHeading } from '../components/SectionHeading';
import { TestimonialCard } from '../components/TestimonialCard';
import { servicePillars, testimonials } from '../data/site-content';
import { usePublicSiteData } from '../hooks/usePublicSiteData';
import { resolveAssetUrl } from '../utils/media';

export function AboutPage() {
  const { isLoading, profile, refresh } = usePublicSiteData();

  if (isLoading && !profile) {
    return <LoadingState message="Carregando apresentação profissional..." />;
  }

  if (!profile) {
    return (
      <section className="section">
        <div className="container">
          <ErrorState
            description="Não foi possível carregar a apresentação da nutricionista neste momento."
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
        description={`${profile.professionalTitle} em ${profile.city}, com uma abordagem que combina estratégia, rotina real e acompanhamento próximo.`}
        eyebrow="Sobre"
        title={profile.fullName}
      />

      <section className="section">
        <div className="container two-column-grid">
          <div className="glass-card about-card">
            <SectionHeading
              description="Uma leitura profissional que considera contexto, adesão e consistência antes de qualquer ajuste."
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
            description="O trabalho combina leitura técnica, planejamento aplicável e acompanhamento que respeita a vida como ela é."
            eyebrow="Como funciona"
            title="Uma abordagem pensada para vida real, adesão e resultado mensurável."
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
            description="Relatos que ajudam a traduzir a experiência de um acompanhamento claro, sustentável e orientado à rotina."
            eyebrow="Percepção de valor"
            title="Relatos que conectam autoridade, acolhimento e consistência."
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
