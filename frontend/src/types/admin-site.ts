import type { EditorialRole } from './auth';
import type { ServicePillar, SiteMetric, Testimonial } from './public-content';

export interface AdminPublicProfile {
  id: number;
  fullName: string;
  professionalTitle: string;
  professionalSubtitle: string;
  biographySummary: string;
  heroBadge: string;
  heroTitle: string;
  heroDescription: string;
  heroCardTitle: string;
  heroCardDescription: string;
  aboutTitle: string;
  aboutDescription: string;
  heroImageUrl: string;
  aboutImageUrl: string;
  primaryCtaLabel: string;
  primaryCtaUrl: string;
  secondaryCtaLabel: string;
  secondaryCtaUrl: string;
  contactEmail: string;
  contactPhone: string;
  whatsappNumber: string;
  instagramHandle: string;
  linkedinUrl: string | null;
  youtubeUrl: string | null;
  city: string;
  officeAddress: string;
  footerDescription: string;
  howItWorksTitle: string;
  howItWorksDescription: string;
  approachTitle: string;
  approachDescription: string;
  plansTitle: string;
  plansDescription: string;
  contactTitle: string;
  contactDescription: string;
  finalCtaTitle: string;
  finalCtaDescription: string;
  siteMetrics: SiteMetric[];
  servicePillars: ServicePillar[];
  testimonials: Testimonial[];
  createdAt: string | null;
  updatedAt: string | null;
}

export interface AdminPublicProfileInput {
  fullName: string;
  professionalTitle: string;
  professionalSubtitle: string;
  biographySummary: string;
  heroBadge: string;
  heroTitle: string;
  heroDescription: string;
  heroCardTitle: string;
  heroCardDescription: string;
  aboutTitle: string;
  aboutDescription: string;
  heroImageUrl: string;
  aboutImageUrl: string;
  primaryCtaLabel: string;
  primaryCtaUrl: string;
  secondaryCtaLabel: string;
  secondaryCtaUrl: string;
  contactEmail: string;
  contactPhone: string;
  whatsappNumber: string;
  instagramHandle: string;
  linkedinUrl: string;
  youtubeUrl: string;
  city: string;
  officeAddress: string;
  footerDescription: string;
  howItWorksTitle: string;
  howItWorksDescription: string;
  approachTitle: string;
  approachDescription: string;
  plansTitle: string;
  plansDescription: string;
  contactTitle: string;
  contactDescription: string;
  finalCtaTitle: string;
  finalCtaDescription: string;
  siteMetrics: SiteMetric[];
  servicePillars: ServicePillar[];
  testimonials: Testimonial[];
}

export interface AdminPublicPlan {
  id: number;
  title: string;
  subtitle: string;
  priceLabel: string;
  summary: string;
  features: string[];
  featured: boolean;
  active: boolean;
  ctaLabel: string;
  ctaUrl: string;
  displayOrder: number;
  createdAt: string | null;
  updatedAt: string | null;
}

export interface AdminPublicPlanInput {
  title: string;
  subtitle: string;
  priceLabel: string;
  summary: string;
  features: string[];
  featured: boolean;
  active: boolean;
  ctaLabel: string;
  ctaUrl: string;
  displayOrder: number;
}

export interface AdminEditorialUser {
  id: number;
  fullName: string;
  email: string;
  role: EditorialRole;
  active: boolean;
  createdAt: string | null;
  updatedAt: string | null;
}

export interface AdminEditorialUserInput {
  fullName: string;
  email: string;
  role: EditorialRole;
  active: boolean;
}

export interface AdminEditorialUserCreateInput extends AdminEditorialUserInput {
  password: string;
}

export interface AdminEditorialUserPasswordInput {
  newPassword: string;
}
