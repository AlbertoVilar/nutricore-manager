import { useEffect, useState } from 'react';
import type { AdminArticleInput, EditorialStatus } from '../../types/editorial';
import { toDateTimeLocalValue } from '../../utils/formatters';
import { EditorialImageField } from './EditorialImageField';
import { TextListField } from './TextListField';

interface ArticleEditorFormProps {
  initialValue: AdminArticleInput;
  isSubmitting: boolean;
  submitError: string | null;
  submitSuccess: string | null;
  onSubmit: (payload: AdminArticleInput) => Promise<void>;
}

export function ArticleEditorForm({
  initialValue,
  isSubmitting,
  submitError,
  submitSuccess,
  onSubmit,
}: ArticleEditorFormProps) {
  const [form, setForm] = useState<AdminArticleInput>(initialValue);

  useEffect(() => {
    setForm(initialValue);
  }, [initialValue]);

  function updateField<Key extends keyof AdminArticleInput>(field: Key, value: AdminArticleInput[Key]) {
    setForm((current) => ({ ...current, [field]: value }));
  }

  async function submitWithStatus(statusOverride?: EditorialStatus) {
    const nextStatus = statusOverride ?? form.status;

    await onSubmit({
      ...form,
      title: form.title.trim(),
      slug: form.slug.trim(),
      summary: form.summary.trim(),
      body: form.body.trim(),
      coverImageUrl: form.coverImageUrl.trim(),
      tags: form.tags,
      category: form.category.trim(),
      readTimeMinutes: form.readTimeMinutes,
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
      <div className="editorial-form-grid">
        <div className="form-field">
          <label htmlFor="article-title">Titulo</label>
          <input id="article-title" onChange={(event) => updateField('title', event.target.value)} required type="text" value={form.title} />
        </div>

        <div className="form-field">
          <label htmlFor="article-slug">Slug manual opcional</label>
          <input
            id="article-slug"
            onChange={(event) => updateField('slug', event.target.value)}
            placeholder="artigo-sobre-habitos"
            type="text"
            value={form.slug}
          />
        </div>

        <div className="form-field editorial-form-grid-span-2">
          <label htmlFor="article-summary">Resumo</label>
          <textarea
            id="article-summary"
            onChange={(event) => updateField('summary', event.target.value)}
            required
            rows={3}
            value={form.summary}
          />
        </div>

        <div className="form-field editorial-form-grid-span-2">
          <label htmlFor="article-body">Conteudo completo</label>
          <textarea
            id="article-body"
            onChange={(event) => updateField('body', event.target.value)}
            required
            rows={12}
            value={form.body}
          />
        </div>

        <EditorialImageField
          hint="A imagem de capa aparece nas listagens publicas e no detalhe do artigo."
          label="Imagem de capa"
          onChange={(value) => updateField('coverImageUrl', value)}
          value={form.coverImageUrl}
        />

        <TextListField
          hint="Uma tag por linha."
          label="Tags"
          onChange={(value) => updateField('tags', value)}
          placeholder="adesao"
          value={form.tags}
        />

        <div className="form-field">
          <label htmlFor="article-category">Categoria</label>
          <input
            id="article-category"
            onChange={(event) => updateField('category', event.target.value)}
            type="text"
            value={form.category}
          />
        </div>

        <div className="form-field">
          <label htmlFor="article-read-time">Tempo de leitura</label>
          <input
            id="article-read-time"
            min={1}
            onChange={(event) =>
              updateField('readTimeMinutes', event.target.value ? Number(event.target.value) : null)
            }
            placeholder="Auto se vazio"
            type="number"
            value={form.readTimeMinutes ?? ''}
          />
        </div>

        <div className="form-field">
          <label htmlFor="article-status">Status</label>
          <select
            id="article-status"
            onChange={(event) => updateField('status', event.target.value as EditorialStatus)}
            value={form.status}
          >
            <option value="DRAFT">Rascunho</option>
            <option value="PUBLISHED">Publicado</option>
            <option value="ARCHIVED">Arquivado</option>
          </select>
        </div>

        <div className="form-field">
          <label htmlFor="article-published-at">Data de publicacao</label>
          <input
            id="article-published-at"
            onChange={(event) => updateField('publishedAt', event.target.value || null)}
            type="datetime-local"
            value={toDateTimeLocalValue(form.publishedAt)}
          />
        </div>

        <label className="editorial-checkbox">
          <input checked={form.featured} onChange={(event) => updateField('featured', event.target.checked)} type="checkbox" />
          <span>Exibir com destaque na home e na listagem</span>
        </label>
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
