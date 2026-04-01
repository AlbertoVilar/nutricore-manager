import type { AdminRecipe, AdminRecipeInput } from '../types/editorial';
import { deleteRequest, getJson, patchJson, postJson, putJson } from './httpClient';
import { buildEditorialHeaders, buildStatusQuery } from './editorialRequest';

export function getAdminRecipes(token: string, status: 'ALL' | 'DRAFT' | 'PUBLISHED' | 'ARCHIVED' = 'ALL') {
  return getJson<AdminRecipe[]>(`/v1/admin/recipes${buildStatusQuery(status)}`, {
    headers: buildEditorialHeaders(token),
  });
}

export function getAdminRecipeById(token: string, id: number) {
  return getJson<AdminRecipe>(`/v1/admin/recipes/${id}`, {
    headers: buildEditorialHeaders(token),
  });
}

export function createAdminRecipe(token: string, payload: AdminRecipeInput) {
  return postJson<AdminRecipe, AdminRecipeInput>('/v1/admin/recipes', payload, {
    headers: buildEditorialHeaders(token),
  });
}

export function updateAdminRecipe(token: string, id: number, payload: AdminRecipeInput) {
  return putJson<AdminRecipe, AdminRecipeInput>(`/v1/admin/recipes/${id}`, payload, {
    headers: buildEditorialHeaders(token),
  });
}

export function publishAdminRecipe(token: string, id: number) {
  return patchJson<AdminRecipe>(`/v1/admin/recipes/${id}/publish`, {
    headers: buildEditorialHeaders(token),
  });
}

export function draftAdminRecipe(token: string, id: number) {
  return patchJson<AdminRecipe>(`/v1/admin/recipes/${id}/draft`, {
    headers: buildEditorialHeaders(token),
  });
}

export function archiveAdminRecipe(token: string, id: number) {
  return patchJson<AdminRecipe>(`/v1/admin/recipes/${id}/archive`, {
    headers: buildEditorialHeaders(token),
  });
}

export function deleteAdminRecipe(token: string, id: number) {
  return deleteRequest(`/v1/admin/recipes/${id}`, {
    headers: buildEditorialHeaders(token),
  });
}
