import type { PublicPost } from '../types/public-content';
import { formatPublishedDate } from '../utils/formatters';

interface PostCardProps {
  post: PublicPost;
}

export function PostCard({ post }: PostCardProps) {
  return (
    <article className="content-card glass-card">
      <img alt={post.title} className="content-card-image" src={post.imageUrl} />
      <div className="content-card-meta">
        <span>{post.category}</span>
        <span>{post.readTimeMinutes} min</span>
      </div>
      <h3>{post.title}</h3>
      <p>{post.excerpt}</p>
      <div className="content-card-footer">
        <span>{formatPublishedDate(post.publishedAt)}</span>
        <span className="content-card-link">Leitura orientada</span>
      </div>
    </article>
  );
}
