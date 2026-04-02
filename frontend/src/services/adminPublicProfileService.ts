import type { AdminPublicProfile, AdminPublicProfileInput } from '../types/admin-site';
import { getJson, putJson } from './httpClient';

export function getAdminPublicProfile() {
  return getJson<AdminPublicProfile>('/v1/admin/public-profile', {
    auth: true,
  });
}

export function updateAdminPublicProfile(payload: AdminPublicProfileInput) {
  return putJson<AdminPublicProfile, AdminPublicProfileInput>('/v1/admin/public-profile', payload, {
    auth: true,
  });
}
