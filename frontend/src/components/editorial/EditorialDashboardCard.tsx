interface EditorialDashboardCardProps {
  title: string;
  value: string;
  description: string;
}

export function EditorialDashboardCard({ title, value, description }: EditorialDashboardCardProps) {
  return (
    <article className="glass-card editorial-dashboard-card">
      <span className="editorial-dashboard-value">{value}</span>
      <h3>{title}</h3>
      <p>{description}</p>
    </article>
  );
}
