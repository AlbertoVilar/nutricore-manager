import { useEffect, useMemo, useState } from 'react';
import { PublicProfileForm } from '../../components/editorial/PublicProfileForm';
import { ErrorState } from '../../components/ErrorState';
import { LoadingState } from '../../components/LoadingState';
import { usePublicSiteData } from '../../hooks/usePublicSiteData';
import { getAdminPublicProfile, updateAdminPublicProfile } from '../../services/adminPublicProfileService';
import type { AdminPublicProfile, AdminPublicProfileInput } from '../../types/admin-site';

function toFormValue(profile: AdminPublicProfile): AdminPublicProfileInput {
  return {
    fullName: profile.fullName,
    professionalTitle: profile.professionalTitle,
    professionalSubtitle: profile.professionalSubtitle,
    biographySummary: profile.biographySummary,
    heroBadge: profile.heroBadge,
    heroTitle: profile.heroTitle,
    heroDescription: profile.heroDescription,
    heroCardTitle: profile.heroCardTitle,
    heroCardDescription: profile.heroCardDescription,
    aboutTitle: profile.aboutTitle,
    aboutDescription: profile.aboutDescription,
    heroImageUrl: profile.heroImageUrl,
    aboutImageUrl: profile.aboutImageUrl,
    primaryCtaLabel: profile.primaryCtaLabel,
    primaryCtaUrl: profile.primaryCtaUrl,
    secondaryCtaLabel: profile.secondaryCtaLabel,
    secondaryCtaUrl: profile.secondaryCtaUrl,
    contactEmail: profile.contactEmail,
    contactPhone: profile.contactPhone,
    whatsappNumber: profile.whatsappNumber,
    instagramHandle: profile.instagramHandle,
    linkedinUrl: profile.linkedinUrl ?? '',
    youtubeUrl: profile.youtubeUrl ?? '',
    city: profile.city,
    officeAddress: profile.officeAddress,
    footerDescription: profile.footerDescription,
    howItWorksTitle: profile.howItWorksTitle,
    howItWorksDescription: profile.howItWorksDescription,
    approachTitle: profile.approachTitle,
    approachDescription: profile.approachDescription,
    plansTitle: profile.plansTitle,
    plansDescription: profile.plansDescription,
    contactTitle: profile.contactTitle,
    contactDescription: profile.contactDescription,
    finalCtaTitle: profile.finalCtaTitle,
    finalCtaDescription: profile.finalCtaDescription,
    siteMetrics: profile.siteMetrics,
    servicePillars: profile.servicePillars,
    testimonials: profile.testimonials,
  };
}

export function EditorialPublicProfilePage() {
  const { refresh } = usePublicSiteData();
  const [profile, setProfile] = useState<AdminPublicProfile | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [loadError, setLoadError] = useState<string | null>(null);
  const [submitError, setSubmitError] = useState<string | null>(null);
  const [submitSuccess, setSubmitSuccess] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    async function loadProfile() {
      setIsLoading(true);
      setLoadError(null);

      try {
        setProfile(await getAdminPublicProfile());
      } catch (error) {
        setLoadError(error instanceof Error ? error.message : 'Não foi possível carregar a configuração pública.');
      } finally {
        setIsLoading(false);
      }
    }

    void loadProfile();
  }, []);

  async function handleSubmit(payload: AdminPublicProfileInput) {
    setIsSubmitting(true);
    setSubmitError(null);
    setSubmitSuccess(null);

    try {
      const response = await updateAdminPublicProfile(payload);
      setProfile(response);
      await refresh();
      setSubmitSuccess('Perfil público atualizado com sucesso.');
    } catch (error) {
      setSubmitError(error instanceof Error ? error.message : 'Não foi possível salvar o perfil público.');
    } finally {
      setIsSubmitting(false);
    }
  }

  const initialValue = useMemo<AdminPublicProfileInput | null>(
    () => (profile ? toFormValue(profile) : null),
    [profile],
  );

  return (
    <section className="editorial-stack">
      <div className="editorial-toolbar">
        <div>
          <span className="section-eyebrow">Site público</span>
          <h2>Perfil institucional e conteúdo comercial</h2>
          <p>Edite textos, imagens, CTAs e blocos estruturados que aparecem na home, Sobre, Planos e Contato.</p>
        </div>
      </div>

      {isLoading ? <LoadingState message="Carregando configuração pública..." /> : null}
      {loadError && !isLoading ? <ErrorState description={loadError} /> : null}

      {!isLoading && !loadError && initialValue ? (
        <PublicProfileForm
          initialValue={initialValue}
          isSubmitting={isSubmitting}
          onSubmit={handleSubmit}
          submitError={submitError}
          submitSuccess={submitSuccess}
        />
      ) : null}
    </section>
  );
}
