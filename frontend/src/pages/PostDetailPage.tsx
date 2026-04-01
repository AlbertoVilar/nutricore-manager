import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { ErrorState } from '../components/ErrorState';
import { LoadingState } from '../components/LoadingState';
import { PageHero } from '../components/PageHero';
import { RichContent } from '../components/RichContent';
import { VideoEmbed } from '../components/VideoEmbed';
import { getPublicPostBySlug } from '../services/publicContentService';
import type { PublicPost } from '../types/public-content';
import { formatPublishedDate } from '../utils/formatters';
import { resolveAssetUrl } from '../utils/media';

export function PostDetailPage() {
  const { slug = '' } = useParams();
  const [post, setPost] = useState<PublicPost | null>(null);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    async function loadPost() {
      setIsLoading(true);
      setErrorMessage(null);

      try {
        setPost(await getPublicPostBySlug(slug));
      } catch (error) {
        setErrorMessage(error instanceof Error ? error.message : 'Nao foi possivel carregar o post.');
      } finally {
        setIsLoading(false);
      }
    }

    void loadPost();
  }, [slug]);

  if (isLoading) {
    return <LoadingState message="Carregando post..." />;
  }

  if (!post || errorMessage) {
    return <ErrorState description={errorMessage ?? 'Post nao encontrado.'} />;
  }

  const coverImageUrl = resolveAssetUrl(post.coverImageUrl ?? post.galleryImageUrls[0] ?? null);

  return (
    <>
      <PageHero
        actions={
          <div className="content-detail-actions">
            <span>{post.category ?? 'Post'}</span>
            <span>{formatPublishedDate(post.publishedAt)}</span>
          </div>
        }
        description={post.summary ?? post.caption ?? 'Post publicado pela nutricionista para a camada publica do site.'}
        eyebrow="Post"
        title={post.title}
      />

      <section className="section">
        <div className="container detail-layout">
          <article className="glass-card detail-card">
            {coverImageUrl ? <img alt={post.title} className="detail-cover-image" src={coverImageUrl} /> : null}
            <RichContent body={post.body} />
            {post.caption ? <p className="detail-caption">{post.caption}</p> : null}
            {post.videoUrl ? <VideoEmbed url={post.videoUrl} /> : null}

            {post.galleryImageUrls.length > 0 ? (
              <div className="detail-gallery">
                {post.galleryImageUrls.map((imageUrl) => (
                  <img alt={post.title} key={imageUrl} src={resolveAssetUrl(imageUrl)} />
                ))}
              </div>
            ) : null}
          </article>

          <aside className="glass-card detail-sidebar">
            <h3>Continuar navegando</h3>
            <p>Volte para a biblioteca editorial e acompanhe outros temas publicados.</p>
            <Link className="button button-secondary" to="/conteudos">
              Ver mais conteudos
            </Link>
          </aside>
        </div>
      </section>
    </>
  );
}
