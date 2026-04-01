import type { AdminPost, AdminPostInput } from '../types/editorial';
import { deleteRequest, getJson, patchJson, postJson, putJson } from './httpClient';
import { buildEditorialHeaders, buildStatusQuery } from './editorialRequest';

export function getAdminPosts(token: string, status: 'ALL' | 'DRAFT' | 'PUBLISHED' | 'ARCHIVED' = 'ALL') {
  return getJson<AdminPost[]>(`/v1/admin/posts${buildStatusQuery(status)}`, {
    headers: buildEditorialHeaders(token),
  });
}

export function getAdminPostById(token: string, id: number) {
  return getJson<AdminPost>(`/v1/admin/posts/${id}`, {
    headers: buildEditorialHeaders(token),
  });
}

export function createAdminPost(token: string, payload: AdminPostInput) {
  return postJson<AdminPost, AdminPostInput>('/v1/admin/posts', payload, {
    headers: buildEditorialHeaders(token),
  });
}

export function updateAdminPost(token: string, id: number, payload: AdminPostInput) {
  return putJson<AdminPost, AdminPostInput>(`/v1/admin/posts/${id}`, payload, {
    headers: buildEditorialHeaders(token),
  });
}

export function publishAdminPost(token: string, id: number) {
  return patchJson<AdminPost>(`/v1/admin/posts/${id}/publish`, {
    headers: buildEditorialHeaders(token),
  });
}

export function draftAdminPost(token: string, id: number) {
  return patchJson<AdminPost>(`/v1/admin/posts/${id}/draft`, {
    headers: buildEditorialHeaders(token),
  });
}

export function archiveAdminPost(token: string, id: number) {
  return patchJson<AdminPost>(`/v1/admin/posts/${id}/archive`, {
    headers: buildEditorialHeaders(token),
  });
}

export function deleteAdminPost(token: string, id: number) {
  return deleteRequest(`/v1/admin/posts/${id}`, {
    headers: buildEditorialHeaders(token),
  });
}
