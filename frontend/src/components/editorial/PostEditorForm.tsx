import { useEffect, useState } from 'react';
import type { AdminPostInput, EditorialStatus } from '../../types/editorial';
import { toDateTimeLocalValue } from '../../utils/formatters';
import { EditorialImageField } from './EditorialImageField';
import { EditorialFormSection } from './EditorialFormSection';
import { TextListField } from './TextListField';

interface PostEditorFormProps {
  initialValue: AdminPostInput;
  isSubmitting: boolean;
  submitError: string | null;
  submitSuccess: string | null;
  onSubmit: (payload: AdminPostInput) => Promise<void>;
}

export function PostEditorForm({
  initialValue,
  isSubmitting,
  submitError,
  submitSuccess,
  onSubmit,
}: PostEditorFormProps) {
  const [form, setForm] = useState<AdminPostInput>(initialValue);

  useEffect(() => {
    setForm(initialValue);
  }, [initialValue]);

  function updateField<Key extends keyof AdminPostInput>(field: Key, value: AdminPostInput[Key]) {
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
      galleryImageUrls: form.galleryImageUrls,
      videoUrl: form.videoUrl.trim(),
      caption: form.caption.trim(),
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
            description="Defina o texto principal do post e deixe o slug livre se quiser geracao automatica."
            title="Conteudo principal"
          >
            <div className="editorial-form-grid">
              <div className="form-field">
                <label htmlFor="post-title">Titulo</label>
                <input id="post-title" onChange={(event) => updateField('title', event.target.value)} required type="text" value={form.title} />
              </div>

              <div className="form-field">
                <label htmlFor="post-slug">Slug manual opcional</label>
                <input
                  id="post-slug"
                  onChange={(event) => updateField('slug', event.target.value)}
                  placeholder="gerado-a-partir-do-titulo"
                  type="text"
                  value={form.slug}
                />
                <small className="form-hint">Se ficar vazio, o sistema gera automaticamente um slug unico.</small>
              </div>

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="post-summary">Resumo</label>
                <textarea id="post-summary" onChange={(event) => updateField('summary', event.target.value)} rows={4} value={form.summary} />
              </div>

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="post-body">Conteudo</label>
                <textarea id="post-body" onChange={(event) => updateField('body', event.target.value)} required rows={12} value={form.body} />
              </div>
            </div>
          </EditorialFormSection>

          <EditorialFormSection
            description="Adicione a capa, galeria e video quando isso ajudar a leitura e a narrativa do post."
            title="Midia e narrativa"
          >
            <div className="editorial-form-grid">
              <EditorialImageField
                className="editorial-form-grid-span-2"
                hint="Envie a capa do post e use a URL publica retornada no conteudo."
                label="Imagem de capa"
                onChange={(value) => updateField('coverImageUrl', value)}
                value={form.coverImageUrl}
              />

              <div className="form-field">
                <label htmlFor="post-video-url">Video por URL</label>
                <input
                  id="post-video-url"
                  onChange={(event) => updateField('videoUrl', event.target.value)}
                  placeholder="https://www.youtube.com/watch?v=..."
                  type="url"
                  value={form.videoUrl}
                />
                <small className="form-hint">Use links externos de video, como YouTube ou Vimeo.</small>
              </div>

              <div className="form-field">
                <label htmlFor="post-category">Categoria</label>
                <input id="post-category" onChange={(event) => updateField('category', event.target.value)} type="text" value={form.category} />
                <small className="form-hint">Exemplos: rotina, treino, bastidores ou dica rapida.</small>
              </div>

              <TextListField
                className="editorial-form-grid-span-2"
                hint="Galeria opcional por URL, uma imagem por linha."
                label="Galeria"
                onChange={(value) => updateField('galleryImageUrls', value)}
                placeholder="/api/media/images/arquivo.jpg"
                value={form.galleryImageUrls}
              />

              <div className="form-field editorial-form-grid-span-2">
                <label htmlFor="post-caption">Legenda</label>
                <textarea id="post-caption" onChange={(event) => updateField('caption', event.target.value)} rows={5} value={form.caption} />
              </div>
            </div>
          </EditorialFormSection>
        </div>

        <aside className="editorial-form-aside">
          <EditorialFormSection
            compact
            description="Controle quando o post pode ir ao ar e se ele deve receber destaque no site."
            title="Publicacao"
          >
            <div className="editorial-form-grid editorial-form-grid-single">
              <div className="form-field">
                <label htmlFor="post-status">Status</label>
                <select id="post-status" onChange={(event) => updateField('status', event.target.value as EditorialStatus)} value={form.status}>
                  <option value="DRAFT">Rascunho</option>
                  <option value="PUBLISHED">Publicado</option>
                  <option value="ARCHIVED">Arquivado</option>
                </select>
              </div>

              <div className="form-field">
                <label htmlFor="post-published-at">Data de publicacao</label>
                <input
                  id="post-published-at"
                  onChange={(event) => updateField('publishedAt', event.target.value || null)}
                  type="datetime-local"
                  value={toDateTimeLocalValue(form.publishedAt)}
                />
              </div>

              <label className="editorial-checkbox">
                <input checked={form.featured} onChange={(event) => updateField('featured', event.target.checked)} type="checkbox" />
                <span>Marcar como destaque no site publico</span>
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
