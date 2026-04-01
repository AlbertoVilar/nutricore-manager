import { Link } from 'react-router-dom';
import type { PublicArticle } from '../types/public-content';
import { formatPublishedDate } from '../utils/formatters';
import { resolveAssetUrl } from '../utils/media';

interface ArticleCardProps {
  article: PublicArticle;
}

export function ArticleCard({ article }: ArticleCardProps) {
  return (
    <article className="content-card glass-card">
      {article.coverImageUrl ? (
        <img alt={article.title} className="content-card-image" src={resolveAssetUrl(article.coverImageUrl)} />
      ) : null}
      <div className="content-card-meta">
        <span>{article.category ?? 'Artigo'}</span>
        <span>{article.readTimeMinutes} min</span>
      </div>
      <h3>{article.title}</h3>
      <p>{article.summary}</p>
      <div className="content-card-footer">
        <span>{formatPublishedDate(article.publishedAt)}</span>
        <Link className="content-card-link" to={`/conteudos/artigos/${article.slug}`}>
          Ler artigo
        </Link>
      </div>
    </article>
  );
}
