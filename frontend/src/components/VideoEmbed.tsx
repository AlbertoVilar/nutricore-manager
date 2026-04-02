import { getVideoEmbedUrl } from '../utils/media';

interface VideoEmbedProps {
  url: string;
  posterUrl?: string | null;
}

function isDirectVideoUrl(url: string) {
  return /^\/videos\//.test(url) || /\.(mp4|webm|ogg|mov)$/i.test(url);
}

export function VideoEmbed({ url, posterUrl }: VideoEmbedProps) {
  if (isDirectVideoUrl(url)) {
    return (
      <div className="video-embed-frame video-embed-frame-portrait">
        <video controls playsInline poster={posterUrl ?? undefined} preload="metadata">
          <source src={url} type="video/mp4" />
          Seu navegador não conseguiu carregar este vídeo.
        </video>
      </div>
    );
  }

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
