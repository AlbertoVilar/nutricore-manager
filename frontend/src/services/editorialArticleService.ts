import type { AdminArticle, AdminArticleInput } from '../types/editorial';
import { deleteRequest, getJson, patchJson, postJson, putJson } from './httpClient';
import { buildStatusQuery } from './editorialRequest';

export function getAdminArticles(status: 'ALL' | 'DRAFT' | 'PUBLISHED' | 'ARCHIVED' = 'ALL') {
  return getJson<AdminArticle[]>(`/v1/admin/articles${buildStatusQuery(status)}`, {
    auth: true,
  });
}

export function getAdminArticleById(id: number) {
  return getJson<AdminArticle>(`/v1/admin/articles/${id}`, {
    auth: true,
  });
}

export function createAdminArticle(payload: AdminArticleInput) {
  return postJson<AdminArticle, AdminArticleInput>('/v1/admin/articles', payload, {
    auth: true,
  });
}

export function updateAdminArticle(id: number, payload: AdminArticleInput) {
  return putJson<AdminArticle, AdminArticleInput>(`/v1/admin/articles/${id}`, payload, {
    auth: true,
  });
}

export function publishAdminArticle(id: number) {
  return patchJson<AdminArticle>(`/v1/admin/articles/${id}/publish`, {
    auth: true,
  });
}

export function draftAdminArticle(id: number) {
  return patchJson<AdminArticle>(`/v1/admin/articles/${id}/draft`, {
    auth: true,
  });
}

export function archiveAdminArticle(id: number) {
  return patchJson<AdminArticle>(`/v1/admin/articles/${id}/archive`, {
    auth: true,
  });
}

export function deleteAdminArticle(id: number) {
  return deleteRequest(`/v1/admin/articles/${id}`, {
    auth: true,
  });
}
