import { Link } from 'react-router-dom';
import type { PublicPost } from '../types/public-content';
import { formatPublishedDate } from '../utils/formatters';
import { resolveAssetUrl } from '../utils/media';

interface PostCardProps {
  post: PublicPost;
}

export function PostCard({ post }: PostCardProps) {
  const imageUrl = resolveAssetUrl(post.coverImageUrl ?? post.galleryImageUrls[0] ?? null);
  const summary = post.summary ?? `${post.body.slice(0, 140)}...`;

  return (
    <article className="content-card glass-card">
      {imageUrl ? <img alt={post.title} className="content-card-image" src={imageUrl} /> : null}
      <div className="content-card-meta">
        <span>{post.category ?? 'Rotina clínica'}</span>
        <span>{post.videoUrl ? 'Com vídeo' : 'Post rápido'}</span>
      </div>
      <h3>{post.title}</h3>
      <p>{summary}</p>
      <div className="content-card-footer">
        <span>{formatPublishedDate(post.publishedAt)}</span>
        <Link className="content-card-link" to={`/conteudos/posts/${post.slug}`}>
          Ler post
        </Link>
      </div>
    </article>
  );
}
