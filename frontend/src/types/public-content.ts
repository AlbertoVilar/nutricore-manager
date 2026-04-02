export interface PublicProfile {
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
  heroImageUrl: string;
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

export interface PublicPlan {
  id: number;
  title: string;
  subtitle: string;
  priceLabel: string;
  summary: string;
  features: string[];
  featured: boolean;
  ctaLabel: string;
  ctaUrl: string;
}

export interface PublicPost {
  id: number;
  title: string;
  slug: string;
  summary: string | null;
  body: string;
  coverImageUrl: string | null;
  galleryImageUrls: string[];
  videoUrl: string | null;
  caption: string | null;
  category: string;
  featured: boolean;
  publishedAt: string;
}

export interface PublicArticle {
  id: number;
  title: string;
  slug: string;
  summary: string;
  body: string;
  coverImageUrl: string | null;
  tags: string[];
  category: string | null;
  readTimeMinutes: number;
  featured: boolean;
  publishedAt: string;
}

export interface PublicRecipe {
  id: number;
  title: string;
  slug: string;
  description: string;
  imageUrl: string | null;
  ingredients: string[];
  preparationSteps: string[];
  prepTimeMinutes: number;
  yieldInfo: string;
  category: string | null;
  featured: boolean;
  publishedAt: string;
}

export interface SiteMetric {
  title: string;
  value: string;
  description: string;
}

export interface ServicePillar {
  title: string;
  description: string;
}

export interface Testimonial {
  name: string;
  label: string;
  quote: string;
  imageUrl: string;
}
