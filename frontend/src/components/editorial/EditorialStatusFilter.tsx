import type { EditorialStatus } from '../../types/editorial';

export type EditorialStatusFilterValue = 'ALL' | EditorialStatus;

interface EditorialStatusFilterProps {
  value: EditorialStatusFilterValue;
  onChange: (value: EditorialStatusFilterValue) => void;
}

const filters: Array<{ label: string; value: EditorialStatusFilterValue }> = [
  { label: 'Todos', value: 'ALL' },
  { label: 'Rascunhos', value: 'DRAFT' },
  { label: 'Publicados', value: 'PUBLISHED' },
  { label: 'Arquivados', value: 'ARCHIVED' },
];

export function EditorialStatusFilter({ value, onChange }: EditorialStatusFilterProps) {
  return (
    <div className="editorial-filter-row" role="tablist" aria-label="Filtrar por status editorial">
      {filters.map((filter) => (
        <button
          key={filter.value}
          className={`editorial-filter-chip${value === filter.value ? ' active' : ''}`}
          onClick={() => {
            onChange(filter.value);
          }}
          type="button"
        >
          {filter.label}
        </button>
      ))}
    </div>
  );
}
