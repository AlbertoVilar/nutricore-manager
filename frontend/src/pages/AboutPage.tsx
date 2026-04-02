import { ErrorState } from '../components/ErrorState';
import { LoadingState } from '../components/LoadingState';
import { PageHero } from '../components/PageHero';
import { SectionHeading } from '../components/SectionHeading';
import { TestimonialCard } from '../components/TestimonialCard';
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
        description={profile.biographySummary}
        eyebrow="Sobre"
        title={profile.fullName}
      />

      <section className="section">
        <div className="container two-column-grid">
          <div className="glass-card about-card">
            <SectionHeading
              description={profile.professionalSubtitle}
              eyebrow="Posicionamento"
              title={profile.aboutTitle}
            />
            <p>{profile.aboutDescription}</p>
          </div>

          <div className="hero-image-frame about-image-frame">
            <img alt={profile.fullName} src={resolveAssetUrl(profile.aboutImageUrl)} />
          </div>
        </div>
      </section>

      <section className="section section-soft">
        <div className="container">
          <SectionHeading
            centered
            description={profile.approachDescription}
            eyebrow="Como funciona"
            title={profile.approachTitle}
          />

          <div className="card-grid">
            {profile.servicePillars.map((pillar) => (
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
            {profile.testimonials.map((testimonial) => (
              <TestimonialCard key={`${testimonial.name}-${testimonial.label}`} testimonial={testimonial} />
            ))}
          </div>
        </div>
      </section>
    </>
  );
}
