import type { SiteMetric } from '../types/public-content';

interface MetricCardProps {
  metric: SiteMetric;
}

export function MetricCard({ metric }: MetricCardProps) {
  return (
    <article className="glass-card metric-card">
      <span className="metric-value">{metric.value}</span>
      <h3>{metric.title}</h3>
      <p>{metric.description}</p>
    </article>
  );
}
