import type { AdminPost, AdminPostInput } from '../types/editorial';
import { deleteRequest, getJson, patchJson, postJson, putJson } from './httpClient';
import { buildStatusQuery } from './editorialRequest';

export function getAdminPosts(status: 'ALL' | 'DRAFT' | 'PUBLISHED' | 'ARCHIVED' = 'ALL') {
  return getJson<AdminPost[]>(`/v1/admin/posts${buildStatusQuery(status)}`, {
    auth: true,
  });
}

export function getAdminPostById(id: number) {
  return getJson<AdminPost>(`/v1/admin/posts/${id}`, {
    auth: true,
  });
}

export function createAdminPost(payload: AdminPostInput) {
  return postJson<AdminPost, AdminPostInput>('/v1/admin/posts', payload, {
    auth: true,
  });
}

export function updateAdminPost(id: number, payload: AdminPostInput) {
  return putJson<AdminPost, AdminPostInput>(`/v1/admin/posts/${id}`, payload, {
    auth: true,
  });
}

export function publishAdminPost(id: number) {
  return patchJson<AdminPost>(`/v1/admin/posts/${id}/publish`, {
    auth: true,
  });
}

export function draftAdminPost(id: number) {
  return patchJson<AdminPost>(`/v1/admin/posts/${id}/draft`, {
    auth: true,
  });
}

export function archiveAdminPost(id: number) {
  return patchJson<AdminPost>(`/v1/admin/posts/${id}/archive`, {
    auth: true,
  });
}

export function deleteAdminPost(id: number) {
  return deleteRequest(`/v1/admin/posts/${id}`, {
    auth: true,
  });
}
