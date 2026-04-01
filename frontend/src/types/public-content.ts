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
  category: string;
  excerpt: string;
  readTimeMinutes: number;
  imageUrl: string;
  publishedAt: string;
}

export interface PublicRecipe {
  id: number;
  title: string;
  slug: string;
  summary: string;
  imageUrl: string;
  prepTimeMinutes: number;
  caloriesLabel: string;
  ingredients: string[];
  preparationSteps: string[];
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
