import type { PublicPlan, PublicPost, PublicProfile, PublicRecipe } from '../types/public-content';
import { getJson } from './httpClient';

export function getPublicProfile() {
  return getJson<PublicProfile>('/v1/public/profile');
}

export function getPublicPlans() {
  return getJson<PublicPlan[]>('/v1/public/plans');
}

export function getPublicPosts() {
  return getJson<PublicPost[]>('/v1/public/posts');
}

export function getPublicRecipes() {
  return getJson<PublicRecipe[]>('/v1/public/recipes');
}
