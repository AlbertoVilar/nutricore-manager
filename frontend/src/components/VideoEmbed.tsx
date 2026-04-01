import { getVideoEmbedUrl } from '../utils/media';

interface VideoEmbedProps {
  url: string;
}

export function VideoEmbed({ url }: VideoEmbedProps) {
  const embedUrl = getVideoEmbedUrl(url);

  if (!embedUrl) {
    return (
      <a className="button button-secondary" href={url} rel="noreferrer" target="_blank">
        Abrir vídeo externo
      </a>
    );
  }

  return (
    <div className="video-embed-frame">
      <iframe
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
        allowFullScreen
        src={embedUrl}
        title="Vídeo relacionado ao conteúdo"
      />
    </div>
  );
}
