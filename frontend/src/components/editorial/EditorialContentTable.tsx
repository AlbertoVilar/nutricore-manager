import { Link } from 'react-router-dom';
import type { EditorialStatus } from '../../types/editorial';
import { formatDateTime } from '../../utils/formatters';
import { resolveAssetUrl } from '../../utils/media';
import { EditorialStatusBadge } from './EditorialStatusBadge';

interface EditorialContentRow {
  id: number;
  title: string;
  slug: string;
  summary: string;
  category: string | null;
  featured: boolean;
  status: EditorialStatus;
  updatedAt: string;
  publishedAt: string | null;
  imageUrl: string | null;
}

interface EditorialContentTableProps {
  rows: EditorialContentRow[];
  createHref: string;
  editHrefBase: string;
  emptyTitle: string;
  emptyDescription: string;
  entityLabel: string;
  onArchive: (id: number) => void;
  onDelete: (id: number) => void;
  onDraft: (id: number) => void;
  onPublish: (id: number) => void;
}

export function EditorialContentTable({
  rows,
  createHref,
  editHrefBase,
  emptyTitle,
  emptyDescription,
  entityLabel,
  onArchive,
  onDelete,
  onDraft,
  onPublish,
}: EditorialContentTableProps) {
  if (rows.length === 0) {
    return (
      <div className="glass-card editorial-empty-state">
        <h3>{emptyTitle}</h3>
        <p>{emptyDescription}</p>
        <Link className="button button-primary" to={createHref}>
          Criar {entityLabel}
        </Link>
      </div>
    );
  }

  return (
    <div className="editorial-table-wrapper glass-card">
      <div className="editorial-table">
        {rows.map((row) => (
          <article className="editorial-row" key={row.id}>
            <div className="editorial-row-main">
              <div className="editorial-row-thumb">
                {row.imageUrl ? <img alt={row.title} src={resolveAssetUrl(row.imageUrl)} /> : <span>{row.title.slice(0, 2)}</span>}
              </div>
              <div>
                <div className="editorial-row-title">
                  <h3>{row.title}</h3>
                  {row.featured ? <span className="editorial-featured-pill">Destaque</span> : null}
                </div>
                <p>{row.summary}</p>
                <div className="editorial-row-meta">
                  <span>Slug: /{row.slug}</span>
                  {row.category ? <span>Categoria: {row.category}</span> : null}
                  <span>Atualizado: {formatDateTime(row.updatedAt)}</span>
                  <span>Publicado: {formatDateTime(row.publishedAt)}</span>
                </div>
              </div>
            </div>

            <div className="editorial-row-side">
              <EditorialStatusBadge status={row.status} />
              <div className="editorial-row-actions">
                <Link className="button button-secondary" to={`${editHrefBase}/${row.id}/editar`}>
                  Editar
                </Link>
                {row.status === 'PUBLISHED' ? (
                  <button className="button button-tertiary" onClick={() => onDraft(row.id)} type="button">
                    Despublicar
                  </button>
                ) : (
                  <button className="button button-primary" onClick={() => onPublish(row.id)} type="button">
                    Publicar
                  </button>
                )}
                <button className="button button-tertiary" onClick={() => onArchive(row.id)} type="button">
                  Arquivar
                </button>
                <button className="button button-tertiary button-danger" onClick={() => onDelete(row.id)} type="button">
                  Excluir
                </button>
              </div>
            </div>
          </article>
        ))}
      </div>
    </div>
  );
}
