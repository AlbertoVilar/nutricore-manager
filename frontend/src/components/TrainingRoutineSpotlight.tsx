import { Link } from 'react-router-dom';
import type { PublicPost } from '../types/public-content';
import { formatPublishedDate } from '../utils/formatters';
import { resolveAssetUrl } from '../utils/media';

interface TrainingRoutineSpotlightProps {
  post: PublicPost;
}

export function TrainingRoutineSpotlight({ post }: TrainingRoutineSpotlightProps) {
  const spotlightImages = [post.coverImageUrl, ...post.galleryImageUrls]
    .filter((value): value is string => Boolean(value))
    .slice(0, 3);

  return (
    <section className="section">
      <div className="container">
        <div className="routine-spotlight glass-card">
          <div className="routine-spotlight-copy">
            <span className="section-eyebrow">Rotina em movimento</span>
            <h2>{post.title}</h2>
            <p>{post.summary ?? post.caption ?? 'Conteudo editorial pensado para inspirar consistencia fora do consultorio.'}</p>

            <div className="routine-spotlight-meta">
              <span>{post.category ?? 'Treino'}</span>
              <span>{post.featured ? 'Destaque' : 'Rotina'}</span>
              <span>{formatPublishedDate(post.publishedAt)}</span>
              {post.videoUrl ? <span>Video externo</span> : null}
            </div>

            {post.caption ? <p className="routine-spotlight-caption">{post.caption}</p> : null}

            <div className="routine-spotlight-actions">
              <Link className="button button-primary" to={`/conteudos/posts/${post.slug}`}>
                Ver rotina completa
              </Link>
              <Link className="button button-secondary" to="/conteudos">
                Mais publicacoes
              </Link>
            </div>
          </div>

          <div className="routine-spotlight-gallery">
            {spotlightImages.map((imageUrl, index) => (
              <figure className="routine-spotlight-card" key={imageUrl}>
                <img alt={`${post.title} ${index + 1}`} src={resolveAssetUrl(imageUrl)} />
              </figure>
            ))}
          </div>
        </div>
      </div>
    </section>
  );
}
