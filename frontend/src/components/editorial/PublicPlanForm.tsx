import { useEffect, useRef, useState } from 'react';
import type { AdminPublicPlanInput } from '../../types/admin-site';
import { EditorialFormSection } from './EditorialFormSection';
import { TextListField } from './TextListField';

interface PublicPlanFormProps {
  initialValue: AdminPublicPlanInput;
  isSubmitting: boolean;
  submitError: string | null;
  submitSuccess: string | null;
  onSubmit: (payload: AdminPublicPlanInput) => Promise<void>;
}

export function PublicPlanForm({
  initialValue,
  isSubmitting,
  submitError,
  submitSuccess,
  onSubmit,
}: PublicPlanFormProps) {
  const [form, setForm] = useState<AdminPublicPlanInput>(initialValue);
  const formRef = useRef<HTMLFormElement | null>(null);

  useEffect(() => {
    setForm(initialValue);
  }, [initialValue]);

  function updateField<Key extends keyof AdminPublicPlanInput>(field: Key, value: AdminPublicPlanInput[Key]) {
    setForm((current) => ({
      ...current,
      [field]: value,
    }));
  }

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    await onSubmit({
      ...form,
      title: form.title.trim(),
      subtitle: form.subtitle.trim(),
      priceLabel: form.priceLabel.trim(),
      summary: form.summary.trim(),
      features: form.features.map((item) => item.trim()).filter(Boolean),
      ctaLabel: form.ctaLabel.trim(),
      ctaUrl: form.ctaUrl.trim(),
    });
  }

  return (
    <form className="editorial-form-shell glass-card" onSubmit={handleSubmit} ref={formRef}>
      <div className="editorial-form-layout">
        <div className="editorial-form-main">
          <EditorialFormSection
            description="Organize a oferta comercial do site com título, resumo, benefícios e chamada para ação."
            title="Conteúdo do plano"
          >
            <div className="editorial-form-grid">
              <div className="form-field">
                <label htmlFor="plan-title">Nome do plano</label>
                <input
                  id="plan-title"
                  onChange={(event) => updateField('title', event.target.value)}
                  required
                  type="text"
                  value={form.title}
                />
              </div>

              <div className="form-field">
                <label htmlFor="plan-subtitle">Subtítulo</label>
                <input
                  id="plan-subtitle"
                  onChange={(event) => updateField('subtitle', event.target.value)}
                  required
                  type="text"
                  value={form.subtitle}
                />
              </div>

              <div className="form-field">
                <label htmlFor="plan-price">Preço / faixa comercial</label>
                <input
                  id="plan-price"
                  onChange={(event) => updateField('priceLabel', event.target.value)}
                  required
                  type="text"
                  value={form.priceLabel}
                />
              </div>

              <div className="form-field">
                <label htmlFor="plan-order">Ordem de exibição</label>
                <input
                  id="plan-order"
                  min={1}
                  onChange={(event) => updateField('displayOrder', Number(event.target.value) || 1)}
                  required
                  type="number"
                  value={form.displayOrder}
                />
              </div>

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="plan-summary">Resumo comercial</label>
                <textarea
                  id="plan-summary"
                  onChange={(event) => updateField('summary', event.target.value)}
                  required
                  rows={5}
                  value={form.summary}
                />
              </div>

              <TextListField
                className="editorial-form-grid-span-2"
                hint="Um benefício por linha para montar a lista pública do plano."
                label="Benefícios"
                onChange={(value) => updateField('features', value)}
                placeholder="Ex.: Consulta inicial estratégica"
                value={form.features}
              />
            </div>
          </EditorialFormSection>
        </div>

        <aside className="editorial-form-aside">
          <EditorialFormSection
            compact
            description="Controle o CTA e o status do plano no site público."
            title="Publicação e ação"
          >
            <div className="editorial-form-grid editorial-form-grid-single">
              <div className="form-field">
                <label htmlFor="plan-cta-label">Texto do CTA</label>
                <input
                  id="plan-cta-label"
                  onChange={(event) => updateField('ctaLabel', event.target.value)}
                  required
                  type="text"
                  value={form.ctaLabel}
                />
              </div>

              <div className="form-field">
                <label htmlFor="plan-cta-url">URL do CTA</label>
                <input
                  id="plan-cta-url"
                  onChange={(event) => updateField('ctaUrl', event.target.value)}
                  required
                  type="text"
                  value={form.ctaUrl}
                />
                <small className="form-hint">Use uma URL completa ou uma rota interna, como /contato.</small>
              </div>

              <label className="editorial-checkbox">
                <input
                  checked={form.featured}
                  onChange={(event) => updateField('featured', event.target.checked)}
                  type="checkbox"
                />
                <span>Destacar no site público</span>
              </label>

              <label className="editorial-checkbox">
                <input
                  checked={form.active}
                  onChange={(event) => updateField('active', event.target.checked)}
                  type="checkbox"
                />
                <span>Plano ativo no site público</span>
              </label>
            </div>
          </EditorialFormSection>
        </aside>
      </div>

      {submitError ? <p className="form-error">{submitError}</p> : null}
      {submitSuccess ? <p className="form-success">{submitSuccess}</p> : null}

      <div className="editorial-form-actions">
        <button
          className="button button-primary"
          disabled={isSubmitting}
          onClick={() => formRef.current?.requestSubmit()}
          type="button"
        >
          {isSubmitting ? 'Salvando...' : 'Salvar plano'}
        </button>
      </div>
    </form>
  );
}
