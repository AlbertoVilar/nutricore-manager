import type { EditorialStatus } from '../../types/editorial';

interface EditorialStatusBadgeProps {
  status: EditorialStatus;
}

const labels: Record<EditorialStatus, string> = {
  DRAFT: 'Rascunho',
  PUBLISHED: 'Publicado',
  ARCHIVED: 'Arquivado',
};

export function EditorialStatusBadge({ status }: EditorialStatusBadgeProps) {
  return <span className={`editorial-status-badge ${status.toLowerCase()}`}>{labels[status]}</span>;
}
