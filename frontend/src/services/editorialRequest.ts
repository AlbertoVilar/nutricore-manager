import type { EditorialStatus } from '../types/editorial';

export function buildStatusQuery(status: EditorialStatus | 'ALL') {
  return status === 'ALL' ? '' : `?status=${status}`;
}
