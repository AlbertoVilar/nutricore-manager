import type { AdminPublicPlan, AdminPublicPlanInput } from '../types/admin-site';
import { deleteRequest, getJson, patchJson, postJson, putJson } from './httpClient';

export function getAdminPublicPlans() {
  return getJson<AdminPublicPlan[]>('/v1/admin/public-plans', {
    auth: true,
  });
}

export function getAdminPublicPlanById(id: number) {
  return getJson<AdminPublicPlan>(`/v1/admin/public-plans/${id}`, {
    auth: true,
  });
}

export function createAdminPublicPlan(payload: AdminPublicPlanInput) {
  return postJson<AdminPublicPlan, AdminPublicPlanInput>('/v1/admin/public-plans', payload, {
    auth: true,
  });
}

export function updateAdminPublicPlan(id: number, payload: AdminPublicPlanInput) {
  return putJson<AdminPublicPlan, AdminPublicPlanInput>(`/v1/admin/public-plans/${id}`, payload, {
    auth: true,
  });
}

export function activateAdminPublicPlan(id: number) {
  return patchJson<AdminPublicPlan>(`/v1/admin/public-plans/${id}/activate`, {
    auth: true,
  });
}

export function deactivateAdminPublicPlan(id: number) {
  return patchJson<AdminPublicPlan>(`/v1/admin/public-plans/${id}/deactivate`, {
    auth: true,
  });
}

export function deleteAdminPublicPlan(id: number) {
  return deleteRequest(`/v1/admin/public-plans/${id}`, {
    auth: true,
  });
}
