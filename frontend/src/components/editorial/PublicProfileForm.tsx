import { useEffect, useRef, useState } from 'react';
import type { AdminPublicProfileInput } from '../../types/admin-site';
import { EditorialFormSection } from './EditorialFormSection';
import { EditorialImageField } from './EditorialImageField';
import { StructuredItemsField } from './StructuredItemsField';

interface PublicProfileFormProps {
  initialValue: AdminPublicProfileInput;
  isSubmitting: boolean;
  submitError: string | null;
  submitSuccess: string | null;
  onSubmit: (payload: AdminPublicProfileInput) => Promise<void>;
}

export function PublicProfileForm({
  initialValue,
  isSubmitting,
  submitError,
  submitSuccess,
  onSubmit,
}: PublicProfileFormProps) {
  const [form, setForm] = useState<AdminPublicProfileInput>(initialValue);
  const formRef = useRef<HTMLFormElement | null>(null);

  useEffect(() => {
    setForm(initialValue);
  }, [initialValue]);

  function updateField<Key extends keyof AdminPublicProfileInput>(
    field: Key,
    value: AdminPublicProfileInput[Key],
  ) {
    setForm((current) => ({
      ...current,
      [field]: value,
    }));
  }

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    await onSubmit({
      ...form,
      fullName: form.fullName.trim(),
      professionalTitle: form.professionalTitle.trim(),
      professionalSubtitle: form.professionalSubtitle.trim(),
      biographySummary: form.biographySummary.trim(),
      heroBadge: form.heroBadge.trim(),
      heroTitle: form.heroTitle.trim(),
      heroDescription: form.heroDescription.trim(),
      heroCardTitle: form.heroCardTitle.trim(),
      heroCardDescription: form.heroCardDescription.trim(),
      aboutTitle: form.aboutTitle.trim(),
      aboutDescription: form.aboutDescription.trim(),
      heroImageUrl: form.heroImageUrl.trim(),
      aboutImageUrl: form.aboutImageUrl.trim(),
      primaryCtaLabel: form.primaryCtaLabel.trim(),
      primaryCtaUrl: form.primaryCtaUrl.trim(),
      secondaryCtaLabel: form.secondaryCtaLabel.trim(),
      secondaryCtaUrl: form.secondaryCtaUrl.trim(),
      contactEmail: form.contactEmail.trim(),
      contactPhone: form.contactPhone.trim(),
      whatsappNumber: form.whatsappNumber.trim(),
      instagramHandle: form.instagramHandle.trim(),
      linkedinUrl: form.linkedinUrl.trim(),
      youtubeUrl: form.youtubeUrl.trim(),
      city: form.city.trim(),
      footerDescription: form.footerDescription.trim(),
      howItWorksTitle: form.howItWorksTitle.trim(),
      howItWorksDescription: form.howItWorksDescription.trim(),
      approachTitle: form.approachTitle.trim(),
      approachDescription: form.approachDescription.trim(),
      plansTitle: form.plansTitle.trim(),
      plansDescription: form.plansDescription.trim(),
      contactTitle: form.contactTitle.trim(),
      contactDescription: form.contactDescription.trim(),
      finalCtaTitle: form.finalCtaTitle.trim(),
      finalCtaDescription: form.finalCtaDescription.trim(),
      siteMetrics: form.siteMetrics.map((item) => ({
        title: item.title.trim(),
        value: item.value.trim(),
        description: item.description.trim(),
      })),
      servicePillars: form.servicePillars.map((item) => ({
        title: item.title.trim(),
        description: item.description.trim(),
      })),
      testimonials: form.testimonials.map((item) => ({
        name: item.name.trim(),
        label: item.label.trim(),
        quote: item.quote.trim(),
        imageUrl: item.imageUrl.trim(),
      })),
    });
  }

  return (
    <form className="editorial-form-shell glass-card" onSubmit={handleSubmit} ref={formRef}>
      <div className="editorial-form-layout">
        <div className="editorial-form-main">
          <EditorialFormSection
            description="Edite a apresentação principal do site, com texto institucional e narrativa da hero."
            title="Identidade e hero"
          >
            <div className="editorial-form-grid">
              <div className="form-field">
                <label htmlFor="profile-full-name">Nome profissional</label>
                <input
                  id="profile-full-name"
                  onChange={(event) => updateField('fullName', event.target.value)}
                  required
                  type="text"
                  value={form.fullName}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-city">Cidade / localização</label>
                <input
                  id="profile-city"
                  onChange={(event) => updateField('city', event.target.value)}
                  required
                  type="text"
                  value={form.city}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-title">Título profissional</label>
                <input
                  id="profile-title"
                  onChange={(event) => updateField('professionalTitle', event.target.value)}
                  required
                  type="text"
                  value={form.professionalTitle}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-subtitle">Subtítulo profissional</label>
                <input
                  id="profile-subtitle"
                  onChange={(event) => updateField('professionalSubtitle', event.target.value)}
                  required
                  type="text"
                  value={form.professionalSubtitle}
                />
              </div>

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="profile-bio-summary">Resumo da bio</label>
                <textarea
                  id="profile-bio-summary"
                  onChange={(event) => updateField('biographySummary', event.target.value)}
                  required
                  rows={4}
                  value={form.biographySummary}
                />
              </div>

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="profile-hero-badge">Faixa superior da hero</label>
                <input
                  id="profile-hero-badge"
                  onChange={(event) => updateField('heroBadge', event.target.value)}
                  required
                  type="text"
                  value={form.heroBadge}
                />
              </div>

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="profile-hero-title">Título principal da hero</label>
                <textarea
                  id="profile-hero-title"
                  onChange={(event) => updateField('heroTitle', event.target.value)}
                  required
                  rows={4}
                  value={form.heroTitle}
                />
              </div>

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="profile-hero-description">Descrição da hero</label>
                <textarea
                  id="profile-hero-description"
                  onChange={(event) => updateField('heroDescription', event.target.value)}
                  required
                  rows={4}
                  value={form.heroDescription}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-hero-card-title">Título do card da hero</label>
                <input
                  id="profile-hero-card-title"
                  onChange={(event) => updateField('heroCardTitle', event.target.value)}
                  required
                  type="text"
                  value={form.heroCardTitle}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-hero-card-description">Descrição do card da hero</label>
                <textarea
                  id="profile-hero-card-description"
                  onChange={(event) => updateField('heroCardDescription', event.target.value)}
                  required
                  rows={4}
                  value={form.heroCardDescription}
                />
              </div>
            </div>
          </EditorialFormSection>

          <EditorialFormSection
            description="Esses blocos estruturados abastecem a home e a página Sobre sem depender de texto solto no frontend."
            title="Seções públicas"
          >
            <div className="editorial-form-grid">
              <div className="form-field">
                <label htmlFor="profile-about-title">Título da seção Sobre</label>
                <input
                  id="profile-about-title"
                  onChange={(event) => updateField('aboutTitle', event.target.value)}
                  required
                  type="text"
                  value={form.aboutTitle}
                />
              </div>

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="profile-about-description">Descrição institucional</label>
                <textarea
                  id="profile-about-description"
                  onChange={(event) => updateField('aboutDescription', event.target.value)}
                  required
                  rows={5}
                  value={form.aboutDescription}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-how-title">Título de “Como funciona”</label>
                <input
                  id="profile-how-title"
                  onChange={(event) => updateField('howItWorksTitle', event.target.value)}
                  required
                  type="text"
                  value={form.howItWorksTitle}
                />
              </div>

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="profile-how-description">Descrição de “Como funciona”</label>
                <textarea
                  id="profile-how-description"
                  onChange={(event) => updateField('howItWorksDescription', event.target.value)}
                  required
                  rows={4}
                  value={form.howItWorksDescription}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-approach-title">Título da abordagem</label>
                <input
                  id="profile-approach-title"
                  onChange={(event) => updateField('approachTitle', event.target.value)}
                  required
                  type="text"
                  value={form.approachTitle}
                />
              </div>

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="profile-approach-description">Descrição da abordagem</label>
                <textarea
                  id="profile-approach-description"
                  onChange={(event) => updateField('approachDescription', event.target.value)}
                  required
                  rows={4}
                  value={form.approachDescription}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-plans-title">Título da seção Planos</label>
                <input
                  id="profile-plans-title"
                  onChange={(event) => updateField('plansTitle', event.target.value)}
                  required
                  type="text"
                  value={form.plansTitle}
                />
              </div>

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="profile-plans-description">Descrição da seção Planos</label>
                <textarea
                  id="profile-plans-description"
                  onChange={(event) => updateField('plansDescription', event.target.value)}
                  required
                  rows={4}
                  value={form.plansDescription}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-contact-title">Título da seção Contato</label>
                <input
                  id="profile-contact-title"
                  onChange={(event) => updateField('contactTitle', event.target.value)}
                  required
                  type="text"
                  value={form.contactTitle}
                />
              </div>

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="profile-contact-description">Descrição da seção Contato</label>
                <textarea
                  id="profile-contact-description"
                  onChange={(event) => updateField('contactDescription', event.target.value)}
                  required
                  rows={4}
                  value={form.contactDescription}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-final-cta-title">Título do CTA final</label>
                <input
                  id="profile-final-cta-title"
                  onChange={(event) => updateField('finalCtaTitle', event.target.value)}
                  required
                  type="text"
                  value={form.finalCtaTitle}
                />
              </div>

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="profile-final-cta-description">Descrição do CTA final</label>
                <textarea
                  id="profile-final-cta-description"
                  onChange={(event) => updateField('finalCtaDescription', event.target.value)}
                  required
                  rows={4}
                  value={form.finalCtaDescription}
                />
              </div>
            </div>
          </EditorialFormSection>

          <EditorialFormSection
            description="Métricas, pilares e relatos abastecem os blocos de autoridade e percepção de valor."
            title="Conteúdo estruturado"
          >
            <StructuredItemsField
              createEmptyItem={() => ({
                title: '',
                value: '',
                description: '',
              })}
              description="Use números, promessas curtas ou sinais de posicionamento do atendimento."
              fields={[
                { key: 'title', label: 'Título' },
                { key: 'value', label: 'Valor em destaque' },
                { key: 'description', label: 'Descrição', multiline: true, rows: 3 },
              ]}
              items={form.siteMetrics}
              onChange={(value) => updateField('siteMetrics', value)}
              title="Métricas da home"
            />

            <StructuredItemsField
              createEmptyItem={() => ({
                title: '',
                description: '',
              })}
              description="Esses blocos descrevem o método e a forma de atendimento."
              fields={[
                { key: 'title', label: 'Título' },
                { key: 'description', label: 'Descrição', multiline: true, rows: 4 },
              ]}
              items={form.servicePillars}
              onChange={(value) => updateField('servicePillars', value)}
              title="Pilares do serviço"
            />

            <StructuredItemsField
              createEmptyItem={() => ({
                name: '',
                label: '',
                quote: '',
                imageUrl: '',
              })}
              description="Relatos curtos para reforçar autoridade e aderência à rotina."
              fields={[
                { key: 'name', label: 'Nome' },
                { key: 'label', label: 'Rótulo' },
                { key: 'quote', label: 'Relato', multiline: true, rows: 4 },
                { key: 'imageUrl', label: 'Imagem do relato' },
              ]}
              items={form.testimonials}
              onChange={(value) => updateField('testimonials', value)}
              title="Relatos"
            />
          </EditorialFormSection>
        </div>

        <aside className="editorial-form-aside">
          <EditorialFormSection
            compact
            description="Ajuste os canais públicos, redes sociais e chamadas principais do site."
            title="Contato e CTAs"
          >
            <div className="editorial-form-grid editorial-form-grid-single">
              <div className="form-field">
                <label htmlFor="profile-contact-email">E-mail</label>
                <input
                  id="profile-contact-email"
                  onChange={(event) => updateField('contactEmail', event.target.value)}
                  required
                  type="email"
                  value={form.contactEmail}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-contact-phone">Telefone</label>
                <input
                  id="profile-contact-phone"
                  onChange={(event) => updateField('contactPhone', event.target.value)}
                  required
                  type="text"
                  value={form.contactPhone}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-whatsapp">WhatsApp (número puro)</label>
                <input
                  id="profile-whatsapp"
                  onChange={(event) => updateField('whatsappNumber', event.target.value)}
                  required
                  type="text"
                  value={form.whatsappNumber}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-instagram">Instagram</label>
                <input
                  id="profile-instagram"
                  onChange={(event) => updateField('instagramHandle', event.target.value)}
                  required
                  type="text"
                  value={form.instagramHandle}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-linkedin">LinkedIn</label>
                <input
                  id="profile-linkedin"
                  onChange={(event) => updateField('linkedinUrl', event.target.value)}
                  type="url"
                  value={form.linkedinUrl}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-youtube">YouTube</label>
                <input
                  id="profile-youtube"
                  onChange={(event) => updateField('youtubeUrl', event.target.value)}
                  type="url"
                  value={form.youtubeUrl}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-primary-label">CTA principal</label>
                <input
                  id="profile-primary-label"
                  onChange={(event) => updateField('primaryCtaLabel', event.target.value)}
                  required
                  type="text"
                  value={form.primaryCtaLabel}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-primary-url">URL do CTA principal</label>
                <input
                  id="profile-primary-url"
                  onChange={(event) => updateField('primaryCtaUrl', event.target.value)}
                  required
                  type="text"
                  value={form.primaryCtaUrl}
                />
                <small className="form-hint">Use uma URL completa ou uma rota interna, como /contato.</small>
              </div>

              <div className="form-field">
                <label htmlFor="profile-secondary-label">CTA secundário</label>
                <input
                  id="profile-secondary-label"
                  onChange={(event) => updateField('secondaryCtaLabel', event.target.value)}
                  required
                  type="text"
                  value={form.secondaryCtaLabel}
                />
              </div>

              <div className="form-field">
                <label htmlFor="profile-secondary-url">URL do CTA secundário</label>
                <input
                  id="profile-secondary-url"
                  onChange={(event) => updateField('secondaryCtaUrl', event.target.value)}
                  required
                  type="text"
                  value={form.secondaryCtaUrl}
                />
                <small className="form-hint">Use uma URL completa ou uma rota interna, como /planos.</small>
              </div>

              <div className="form-field">
                <label htmlFor="profile-footer-description">Resumo do rodapé</label>
                <textarea
                  id="profile-footer-description"
                  onChange={(event) => updateField('footerDescription', event.target.value)}
                  required
                  rows={4}
                  value={form.footerDescription}
                />
              </div>
            </div>
          </EditorialFormSection>

          <EditorialFormSection
            compact
            description="Troque as imagens principais do site usando upload simples de mídia."
            title="Imagens do site"
          >
            <div className="editorial-form-grid editorial-form-grid-single">
              <EditorialImageField
                hint="Imagem principal da hero e da apresentação do topo."
                label="Imagem da hero"
                onChange={(value) => updateField('heroImageUrl', value)}
                value={form.heroImageUrl}
              />

              <EditorialImageField
                hint="Imagem institucional usada na página Sobre."
                label="Imagem da seção Sobre"
                onChange={(value) => updateField('aboutImageUrl', value)}
                value={form.aboutImageUrl}
              />
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
          {isSubmitting ? 'Salvando...' : 'Salvar perfil público'}
        </button>
      </div>
    </form>
  );
}
