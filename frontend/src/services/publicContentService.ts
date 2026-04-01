import type { PublicArticle, PublicPlan, PublicPost, PublicProfile, PublicRecipe } from '../types/public-content';
import { getJson } from './httpClient';

export function getPublicProfile() {
  return getJson<PublicProfile>('/v1/public/profile');
}

export function getPublicPlans() {
  return getJson<PublicPlan[]>('/v1/public/plans');
}

export function getPublicArticles() {
  return getJson<PublicArticle[]>('/v1/public/articles');
}

export function getPublicArticleBySlug(slug: string) {
  return getJson<PublicArticle>(`/v1/public/articles/${slug}`);
}

export function getPublicPosts() {
  return getJson<PublicPost[]>('/v1/public/posts');
}

export function getPublicPostBySlug(slug: string) {
  return getJson<PublicPost>(`/v1/public/posts/${slug}`);
}

export function getPublicRecipes() {
  return getJson<PublicRecipe[]>('/v1/public/recipes');
}

export function getPublicRecipeBySlug(slug: string) {
  return getJson<PublicRecipe>(`/v1/public/recipes/${slug}`);
}
