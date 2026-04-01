export interface PublicProfile {
  id: number;
  fullName: string;
  professionalTitle: string;
  heroBadge: string;
  heroTitle: string;
  heroDescription: string;
  aboutTitle: string;
  aboutDescription: string;
  primaryCtaLabel: string;
  primaryCtaUrl: string;
  secondaryCtaLabel: string;
  secondaryCtaUrl: string;
  contactEmail: string;
  contactPhone: string;
  instagramHandle: string;
  city: string;
  heroImageUrl: string;
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
