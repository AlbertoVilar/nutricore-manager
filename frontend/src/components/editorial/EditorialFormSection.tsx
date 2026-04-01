import type { ReactNode } from 'react';

interface EditorialFormSectionProps {
  title: string;
  description: string;
  children: ReactNode;
  compact?: boolean;
}

export function EditorialFormSection({
  title,
  description,
  children,
  compact = false,
}: EditorialFormSectionProps) {
  return (
    <section className={`editorial-form-section${compact ? ' editorial-form-section-compact' : ''}`}>
      <header className="editorial-form-section-header">
        <h3>{title}</h3>
        <p>{description}</p>
      </header>
      {children}
    </section>
  );
}
