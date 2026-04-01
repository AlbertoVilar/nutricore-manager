import type { PublicPost } from '../types/public-content';

function normalizeCategory(category: string | null | undefined) {
  return category?.trim().toLowerCase() ?? '';
}

export function isRoutinePost(post: PublicPost) {
  return normalizeCategory(post.category) === 'treino';
}

export function getRoutinePosts(posts: PublicPost[]) {
  return posts.filter(isRoutinePost);
}

export function getGeneralPosts(posts: PublicPost[]) {
  return posts.filter((post) => !isRoutinePost(post));
}

export function getRoutineSpotlightPost(posts: PublicPost[]) {
  return posts.find((post) => post.featured && isRoutinePost(post)) ?? posts.find(isRoutinePost) ?? null;
}
