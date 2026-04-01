import { useState } from 'react';
import { uploadEditorialImage } from '../../services/editorialMediaService';
import { resolveAssetUrl } from '../../utils/media';

interface EditorialImageFieldProps {
  label: string;
  hint: string;
  value: string;
  onChange: (value: string) => void;
  className?: string;
}

export function EditorialImageField({ label, hint, value, onChange, className }: EditorialImageFieldProps) {
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [isUploading, setIsUploading] = useState(false);

  async function handleFileSelection(file: File | null) {
    if (!file) {
      return;
    }

    setIsUploading(true);
    setErrorMessage(null);

    try {
      const response = await uploadEditorialImage(file);
      onChange(response.url);
    } catch (error) {
      setErrorMessage(error instanceof Error ? error.message : 'Nao foi possivel enviar a imagem.');
    } finally {
      setIsUploading(false);
    }
  }

  return (
    <div className={`form-field${className ? ` ${className}` : ''}`}>
      <label>{label}</label>
      <input onChange={(event) => onChange(event.target.value)} placeholder="/api/media/images/..." type="text" value={value} />
      <div className="editorial-upload-row">
        <label className="button button-secondary editorial-upload-button">
          {isUploading ? 'Enviando imagem...' : 'Enviar imagem'}
          <input
            accept="image/png,image/jpeg,image/webp,image/gif"
            className="editorial-upload-input"
            onChange={(event) => {
              void handleFileSelection(event.target.files?.[0] ?? null);
              event.target.value = '';
            }}
            type="file"
          />
        </label>
        <small className="form-hint">{hint}</small>
      </div>
      {errorMessage ? <small className="form-error">{errorMessage}</small> : null}
      {value ? (
        <div className="editorial-image-preview">
          <img alt={label} src={resolveAssetUrl(value)} />
        </div>
      ) : null}
    </div>
  );
}
