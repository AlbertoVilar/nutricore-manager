import type { AdminArticle, AdminArticleInput } from '../types/editorial';
import { deleteRequest, getJson, patchJson, postJson, putJson } from './httpClient';
import { buildEditorialHeaders, buildStatusQuery } from './editorialRequest';

export function getAdminArticles(token: string, status: 'ALL' | 'DRAFT' | 'PUBLISHED' | 'ARCHIVED' = 'ALL') {
  return getJson<AdminArticle[]>(`/v1/admin/articles${buildStatusQuery(status)}`, {
    headers: buildEditorialHeaders(token),
  });
}

export function getAdminArticleById(token: string, id: number) {
  return getJson<AdminArticle>(`/v1/admin/articles/${id}`, {
    headers: buildEditorialHeaders(token),
  });
}

export function createAdminArticle(token: string, payload: AdminArticleInput) {
  return postJson<AdminArticle, AdminArticleInput>('/v1/admin/articles', payload, {
    headers: buildEditorialHeaders(token),
  });
}

export function updateAdminArticle(token: string, id: number, payload: AdminArticleInput) {
  return putJson<AdminArticle, AdminArticleInput>(`/v1/admin/articles/${id}`, payload, {
    headers: buildEditorialHeaders(token),
  });
}

export function publishAdminArticle(token: string, id: number) {
  return patchJson<AdminArticle>(`/v1/admin/articles/${id}/publish`, {
    headers: buildEditorialHeaders(token),
  });
}

export function draftAdminArticle(token: string, id: number) {
  return patchJson<AdminArticle>(`/v1/admin/articles/${id}/draft`, {
    headers: buildEditorialHeaders(token),
  });
}

export function archiveAdminArticle(token: string, id: number) {
  return patchJson<AdminArticle>(`/v1/admin/articles/${id}/archive`, {
    headers: buildEditorialHeaders(token),
  });
}

export function deleteAdminArticle(token: string, id: number) {
  return deleteRequest(`/v1/admin/articles/${id}`, {
    headers: buildEditorialHeaders(token),
  });
}
