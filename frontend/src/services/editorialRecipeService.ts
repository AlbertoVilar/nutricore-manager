import type { AdminRecipe, AdminRecipeInput } from '../types/editorial';
import { deleteRequest, getJson, patchJson, postJson, putJson } from './httpClient';
import { buildStatusQuery } from './editorialRequest';

export function getAdminRecipes(status: 'ALL' | 'DRAFT' | 'PUBLISHED' | 'ARCHIVED' = 'ALL') {
  return getJson<AdminRecipe[]>(`/v1/admin/recipes${buildStatusQuery(status)}`, {
    auth: true,
  });
}

export function getAdminRecipeById(id: number) {
  return getJson<AdminRecipe>(`/v1/admin/recipes/${id}`, {
    auth: true,
  });
}

export function createAdminRecipe(payload: AdminRecipeInput) {
  return postJson<AdminRecipe, AdminRecipeInput>('/v1/admin/recipes', payload, {
    auth: true,
  });
}

export function updateAdminRecipe(id: number, payload: AdminRecipeInput) {
  return putJson<AdminRecipe, AdminRecipeInput>(`/v1/admin/recipes/${id}`, payload, {
    auth: true,
  });
}

export function publishAdminRecipe(id: number) {
  return patchJson<AdminRecipe>(`/v1/admin/recipes/${id}/publish`, {
    auth: true,
  });
}

export function draftAdminRecipe(id: number) {
  return patchJson<AdminRecipe>(`/v1/admin/recipes/${id}/draft`, {
    auth: true,
  });
}

export function archiveAdminRecipe(id: number) {
  return patchJson<AdminRecipe>(`/v1/admin/recipes/${id}/archive`, {
    auth: true,
  });
}

export function deleteAdminRecipe(id: number) {
  return deleteRequest(`/v1/admin/recipes/${id}`, {
    auth: true,
  });
}
