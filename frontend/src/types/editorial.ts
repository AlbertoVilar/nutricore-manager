export type EditorialStatus = 'DRAFT' | 'PUBLISHED' | 'ARCHIVED';

export interface AdminPost {
  id: number;
  title: string;
  slug: string;
  summary: string | null;
  body: string;
  coverImageUrl: string | null;
  galleryImageUrls: string[];
  videoUrl: string | null;
  caption: string | null;
  category: string | null;
  featured: boolean;
  status: EditorialStatus;
  publishedAt: string | null;
  createdAt: string;
  updatedAt: string | null;
}

export interface AdminPostInput {
  title: string;
  slug: string;
  summary: string;
  body: string;
  coverImageUrl: string;
  galleryImageUrls: string[];
  videoUrl: string;
  caption: string;
  category: string;
  featured: boolean;
  status: EditorialStatus;
  publishedAt: string | null;
}

export interface AdminArticle {
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
  status: EditorialStatus;
  publishedAt: string | null;
  createdAt: string;
  updatedAt: string | null;
}

export interface AdminArticleInput {
  title: string;
  slug: string;
  summary: string;
  body: string;
  coverImageUrl: string;
  tags: string[];
  category: string;
  readTimeMinutes: number | null;
  featured: boolean;
  status: EditorialStatus;
  publishedAt: string | null;
}

export interface AdminRecipe {
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
  status: EditorialStatus;
  publishedAt: string | null;
  createdAt: string;
  updatedAt: string | null;
}

export interface AdminRecipeInput {
  title: string;
  slug: string;
  description: string;
  imageUrl: string;
  ingredients: string[];
  preparationSteps: string[];
  prepTimeMinutes: number;
  yieldInfo: string;
  category: string;
  featured: boolean;
  status: EditorialStatus;
  publishedAt: string | null;
}

export interface MediaUploadResponse {
  url: string;
  originalFilename: string;
  contentType: string;
  sizeBytes: number;
}
