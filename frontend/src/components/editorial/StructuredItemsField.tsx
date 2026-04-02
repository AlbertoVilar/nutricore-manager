interface StructuredFieldDefinition<T extends Record<string, string>> {
  key: keyof T;
  label: string;
  placeholder?: string;
  multiline?: boolean;
  rows?: number;
}

interface StructuredItemsFieldProps<T extends Record<string, string>> {
  title: string;
  description: string;
  items: T[];
  fields: StructuredFieldDefinition<T>[];
  createEmptyItem: () => T;
  onChange: (items: T[]) => void;
}

export function StructuredItemsField<T extends Record<string, string>>({
  title,
  description,
  items,
  fields,
  createEmptyItem,
  onChange,
}: StructuredItemsFieldProps<T>) {
  function updateItem(index: number, key: keyof T, value: string) {
    const nextItems = [...items];
    nextItems[index] = {
      ...nextItems[index],
      [key]: value,
    };
    onChange(nextItems);
  }

  function addItem() {
    onChange([...items, createEmptyItem()]);
  }

  function removeItem(index: number) {
    onChange(items.filter((_, currentIndex) => currentIndex !== index));
  }

  return (
    <div className="structured-items-field">
      <div className="structured-items-header">
        <div>
          <h4>{title}</h4>
          <p>{description}</p>
        </div>
        <button className="button button-secondary" onClick={addItem} type="button">
          Adicionar item
        </button>
      </div>

      <div className="structured-items-stack">
        {items.map((item, index) => (
          <article key={`${title}-${index}`} className="structured-item-card glass-card">
            <div className="structured-item-grid">
              {fields.map((field) => (
                <div className="form-field" key={String(field.key)}>
                  <label htmlFor={`${title}-${index}-${String(field.key)}`}>{field.label}</label>
                  {field.multiline ? (
                    <textarea
                      id={`${title}-${index}-${String(field.key)}`}
                      onChange={(event) => updateItem(index, field.key, event.target.value)}
                      placeholder={field.placeholder}
                      rows={field.rows ?? 4}
                      value={item[field.key] ?? ''}
                    />
                  ) : (
                    <input
                      id={`${title}-${index}-${String(field.key)}`}
                      onChange={(event) => updateItem(index, field.key, event.target.value)}
                      placeholder={field.placeholder}
                      type="text"
                      value={item[field.key] ?? ''}
                    />
                  )}
                </div>
              ))}
            </div>

            <div className="structured-item-actions">
              <button
                className="button button-tertiary button-danger"
                disabled={items.length <= 1}
                onClick={() => removeItem(index)}
                type="button"
              >
                Remover item
              </button>
            </div>
          </article>
        ))}
      </div>
    </div>
  );
}
