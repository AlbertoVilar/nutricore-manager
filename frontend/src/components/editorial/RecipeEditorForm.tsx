import { useEffect, useState } from 'react';
import type { AdminRecipeInput, EditorialStatus } from '../../types/editorial';
import { toDateTimeLocalValue } from '../../utils/formatters';
import { EditorialFormSection } from './EditorialFormSection';
import { EditorialImageField } from './EditorialImageField';
import { TextListField } from './TextListField';

interface RecipeEditorFormProps {
  initialValue: AdminRecipeInput;
  isSubmitting: boolean;
  submitError: string | null;
  submitSuccess: string | null;
  onSubmit: (payload: AdminRecipeInput) => Promise<void>;
}

export function RecipeEditorForm({
  initialValue,
  isSubmitting,
  submitError,
  submitSuccess,
  onSubmit,
}: RecipeEditorFormProps) {
  const [form, setForm] = useState<AdminRecipeInput>(initialValue);

  useEffect(() => {
    setForm(initialValue);
  }, [initialValue]);

  function updateField<Key extends keyof AdminRecipeInput>(field: Key, value: AdminRecipeInput[Key]) {
    setForm((current) => ({ ...current, [field]: value }));
  }

  async function submitWithStatus(statusOverride?: EditorialStatus) {
    const nextStatus = statusOverride ?? form.status;

    await onSubmit({
      ...form,
      title: form.title.trim(),
      slug: form.slug.trim(),
      description: form.description.trim(),
      imageUrl: form.imageUrl.trim(),
      ingredients: form.ingredients,
      preparationSteps: form.preparationSteps,
      prepTimeMinutes: form.prepTimeMinutes,
      yieldInfo: form.yieldInfo.trim(),
      category: form.category.trim(),
      featured: form.featured,
      status: nextStatus,
      publishedAt: nextStatus === 'PUBLISHED' ? form.publishedAt : null,
    });
  }

  return (
    <form
      className="editorial-form-shell glass-card"
      onSubmit={(event) => {
        event.preventDefault();
        void submitWithStatus();
      }}
    >
      <div className="editorial-form-layout">
        <div className="editorial-form-main">
          <EditorialFormSection
            description="Defina o nome, a descricao e o passo a passo da receita com clareza para leitura publica."
            title="Conteudo principal"
          >
            <div className="editorial-form-grid">
              <div className="form-field">
                <label htmlFor="recipe-title">Titulo</label>
                <input id="recipe-title" onChange={(event) => updateField('title', event.target.value)} required type="text" value={form.title} />
              </div>

              <div className="form-field">
                <label htmlFor="recipe-slug">Slug manual opcional</label>
                <input id="recipe-slug" onChange={(event) => updateField('slug', event.target.value)} type="text" value={form.slug} />
                <small className="form-hint">Se ficar vazio, o sistema gera automaticamente um slug unico.</small>
              </div>

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="recipe-description">Descricao</label>
                <textarea
                  id="recipe-description"
                  onChange={(event) => updateField('description', event.target.value)}
                  required
                  rows={4}
                  value={form.description}
                />
              </div>

              <TextListField
                className="editorial-form-grid-span-2"
                hint="Um ingrediente por linha."
                label="Ingredientes"
                onChange={(value) => updateField('ingredients', value)}
                placeholder="2 ovos"
                value={form.ingredients}
              />

              <TextListField
                className="editorial-form-grid-span-2"
                hint="Um passo por linha."
                label="Modo de preparo"
                onChange={(value) => updateField('preparationSteps', value)}
                placeholder="Misture todos os ingredientes."
                value={form.preparationSteps}
              />
            </div>
          </EditorialFormSection>

          <EditorialFormSection
            description="Use uma capa forte e complete os dados praticos que ajudam o visitante a executar a receita."
            title="Midia e dados da receita"
          >
            <div className="editorial-form-grid">
              <EditorialImageField
                className="editorial-form-grid-span-2"
                hint="Imagem principal da receita exibida no site publico."
                label="Imagem"
                onChange={(value) => updateField('imageUrl', value)}
                value={form.imageUrl}
              />

              <div className="form-field">
                <label htmlFor="recipe-prep-time">Tempo de preparo</label>
                <input
                  id="recipe-prep-time"
                  min={1}
                  onChange={(event) => updateField('prepTimeMinutes', Number(event.target.value))}
                  required
                  type="number"
                  value={form.prepTimeMinutes}
                />
              </div>

              <div className="form-field">
                <label htmlFor="recipe-yield">Rendimento</label>
                <input
                  id="recipe-yield"
                  onChange={(event) => updateField('yieldInfo', event.target.value)}
                  required
                  type="text"
                  value={form.yieldInfo}
                />
              </div>
            </div>
          </EditorialFormSection>
        </div>

        <aside className="editorial-form-aside">
          <EditorialFormSection
            compact
            description="Classifique a receita para facilitar a organizacao editorial e a navegacao publica."
            title="Classificacao"
          >
            <div className="editorial-form-grid editorial-form-grid-single">
              <div className="form-field">
                <label htmlFor="recipe-category">Categoria</label>
                <input
                  id="recipe-category"
                  onChange={(event) => updateField('category', event.target.value)}
                  type="text"
                  value={form.category}
                />
              </div>
            </div>
          </EditorialFormSection>

          <EditorialFormSection
            compact
            description="Controle quando a receita vai ao ar e se ela deve aparecer como destaque."
            title="Publicacao"
          >
            <div className="editorial-form-grid editorial-form-grid-single">
              <div className="form-field">
                <label htmlFor="recipe-status">Status</label>
                <select id="recipe-status" onChange={(event) => updateField('status', event.target.value as EditorialStatus)} value={form.status}>
                  <option value="DRAFT">Rascunho</option>
                  <option value="PUBLISHED">Publicado</option>
                  <option value="ARCHIVED">Arquivado</option>
                </select>
              </div>

              <div className="form-field">
                <label htmlFor="recipe-published-at">Data de publicacao</label>
                <input
                  id="recipe-published-at"
                  onChange={(event) => updateField('publishedAt', event.target.value || null)}
                  type="datetime-local"
                  value={toDateTimeLocalValue(form.publishedAt)}
                />
              </div>

              <label className="editorial-checkbox">
                <input checked={form.featured} onChange={(event) => updateField('featured', event.target.checked)} type="checkbox" />
                <span>Exibir como receita em destaque</span>
              </label>
            </div>
          </EditorialFormSection>
        </aside>
      </div>

      {submitError ? <p className="form-error">{submitError}</p> : null}
      {submitSuccess ? <p className="form-success">{submitSuccess}</p> : null}

      <div className="editorial-form-actions">
        <button className="button button-primary" disabled={isSubmitting} type="submit">
          {isSubmitting ? 'Salvando...' : 'Salvar'}
        </button>
        <button
          className="button button-secondary"
          disabled={isSubmitting}
          onClick={() => {
            void submitWithStatus('DRAFT');
          }}
          type="button"
        >
          Salvar rascunho
        </button>
        <button
          className="button button-tertiary"
          disabled={isSubmitting}
          onClick={() => {
            void submitWithStatus('PUBLISHED');
          }}
          type="button"
        >
          Salvar e publicar
        </button>
      </div>
    </form>
  );
}
