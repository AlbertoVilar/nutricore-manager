import type {
  AdminEditorialUser,
  AdminEditorialUserCreateInput,
  AdminEditorialUserInput,
  AdminEditorialUserPasswordInput,
} from '../types/admin-site';
import { getJson, patchJson, postJson, putJson } from './httpClient';

export function getAdminUsers() {
  return getJson<AdminEditorialUser[]>('/v1/admin/users', {
    auth: true,
  });
}

export function getAdminUserById(id: number) {
  return getJson<AdminEditorialUser>(`/v1/admin/users/${id}`, {
    auth: true,
  });
}

export function createAdminUser(payload: AdminEditorialUserCreateInput) {
  return postJson<AdminEditorialUser, AdminEditorialUserCreateInput>('/v1/admin/users', payload, {
    auth: true,
  });
}

export function updateAdminUser(id: number, payload: AdminEditorialUserInput) {
  return putJson<AdminEditorialUser, AdminEditorialUserInput>(`/v1/admin/users/${id}`, payload, {
    auth: true,
  });
}

export function activateAdminUser(id: number) {
  return patchJson<AdminEditorialUser>(`/v1/admin/users/${id}/activate`, {
    auth: true,
  });
}

export function deactivateAdminUser(id: number) {
  return patchJson<AdminEditorialUser>(`/v1/admin/users/${id}/deactivate`, {
    auth: true,
  });
}

export function resetAdminUserPassword(id: number, payload: AdminEditorialUserPasswordInput) {
  return patchJson<void>(`/v1/admin/users/${id}/password`, {
    auth: true,
    body: JSON.stringify(payload),
    headers: {
      'Content-Type': 'application/json',
    },
  });
}
