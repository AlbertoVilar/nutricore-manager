import { API_ORIGIN } from '../services/config';

export function resolveAssetUrl(url: string | null | undefined) {
  if (!url) {
    return '';
  }

  if (/^https?:\/\//i.test(url)) {
    return url;
  }

  if (url.startsWith('/api/')) {
    return `${API_ORIGIN}${url}`;
  }

  return url;
}

export function getVideoEmbedUrl(url: string | null | undefined) {
  if (!url) {
    return null;
  }

  try {
    const parsedUrl = new URL(url);

    if (parsedUrl.hostname.includes('youtube.com')) {
      const videoId = parsedUrl.searchParams.get('v');
      return videoId ? `https://www.youtube.com/embed/${videoId}` : null;
    }

    if (parsedUrl.hostname === 'youtu.be') {
      const videoId = parsedUrl.pathname.replace('/', '');
      return videoId ? `https://www.youtube.com/embed/${videoId}` : null;
    }

    if (parsedUrl.hostname.includes('vimeo.com')) {
      const videoId = parsedUrl.pathname.replace('/', '');
      return videoId ? `https://player.vimeo.com/video/${videoId}` : null;
    }

    if (parsedUrl.hostname.includes('instagram.com')) {
      const parts = parsedUrl.pathname.split('/').filter(Boolean);
      const type = parts[0];
      const shortcode = parts[1];

      if ((type === 'reel' || type === 'p' || type === 'tv') && shortcode) {
        return `https://www.instagram.com/${type}/${shortcode}/embed`;
      }
    }
  } catch {
    return null;
  }

  return null;
}
