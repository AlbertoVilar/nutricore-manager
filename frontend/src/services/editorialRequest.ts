import type { EditorialStatus } from '../types/editorial';

export const EDITORIAL_TOKEN_HEADER = 'X-Editorial-Token';

export function buildEditorialHeaders(token: string) {
  return {
    [EDITORIAL_TOKEN_HEADER]: token,
  };
}

export function buildStatusQuery(status: EditorialStatus | 'ALL') {
  return status === 'ALL' ? '' : `?status=${status}`;
}
