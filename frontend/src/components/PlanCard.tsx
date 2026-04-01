import { Link } from 'react-router-dom';
import type { PublicPlan } from '../types/public-content';

interface PlanCardProps {
  plan: PublicPlan;
}

export function PlanCard({ plan }: PlanCardProps) {
  return (
    <article className={`plan-card${plan.featured ? ' featured' : ''}`}>
      {plan.featured ? <span className="plan-badge">Mais procurado</span> : null}
      <h3>{plan.title}</h3>
      <p className="plan-subtitle">{plan.subtitle}</p>
      <strong className="plan-price">{plan.priceLabel}</strong>
      <p className="plan-summary">{plan.summary}</p>
      <ul className="plan-features">
        {plan.features.map((feature) => (
          <li key={feature}>{feature}</li>
        ))}
      </ul>
      <Link className="button button-primary" to="/contato">
        {plan.ctaLabel}
      </Link>
    </article>
  );
}
