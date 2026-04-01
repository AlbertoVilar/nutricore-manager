import { Link } from 'react-router-dom';
import type { PublicProfile } from '../types/public-content';
import { resolveAssetUrl } from '../utils/media';

interface HeroSectionProps {
  profile: PublicProfile;
}

export function HeroSection({ profile }: HeroSectionProps) {
  return (
    <section className="hero-section">
      <div className="container hero-grid">
        <div className="hero-copy">
          <span className="hero-badge">{profile.heroBadge}</span>
          <h1>{profile.heroTitle}</h1>
          <p>{profile.heroDescription}</p>
          <div className="hero-actions">
            <a className="button button-primary" href={profile.primaryCtaUrl} rel="noreferrer" target="_blank">
              {profile.primaryCtaLabel}
            </a>
            <Link className="button button-secondary" to="/planos">
              {profile.secondaryCtaLabel}
            </Link>
          </div>
          <div className="hero-footnote">
            <span>{profile.professionalTitle}</span>
            <span>{profile.city}</span>
          </div>
        </div>

        <div className="hero-visual">
          <div className="hero-image-frame">
            <img alt={profile.fullName} src={resolveAssetUrl(profile.heroImageUrl)} />
          </div>
          <div className="hero-floating-card glass-card">
            <strong>Nutrição com estratégia e vida real</strong>
            <p>Consulta, conteúdo e acompanhamento organizados para transformar orientação em rotina possível.</p>
          </div>
        </div>
      </div>
    </section>
  );
}
