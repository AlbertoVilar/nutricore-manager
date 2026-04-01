import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { ErrorState } from '../components/ErrorState';
import { LoadingState } from '../components/LoadingState';
import { PageHero } from '../components/PageHero';
import { RichContent } from '../components/RichContent';
import { getPublicArticleBySlug } from '../services/publicContentService';
import type { PublicArticle } from '../types/public-content';
import { formatPublishedDate } from '../utils/formatters';
import { resolveAssetUrl } from '../utils/media';

export function ArticleDetailPage() {
  const { slug = '' } = useParams();
  const [article, setArticle] = useState<PublicArticle | null>(null);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    async function loadArticle() {
      setIsLoading(true);
      setErrorMessage(null);

      try {
        setArticle(await getPublicArticleBySlug(slug));
      } catch (error) {
        setErrorMessage(error instanceof Error ? error.message : 'Nao foi possivel carregar o artigo.');
      } finally {
        setIsLoading(false);
      }
    }

    void loadArticle();
  }, [slug]);

  if (isLoading) {
    return <LoadingState message="Carregando artigo..." />;
  }

  if (!article || errorMessage) {
    return <ErrorState description={errorMessage ?? 'Artigo nao encontrado.'} />;
  }

  return (
    <>
      <PageHero
        actions={
          <div className="content-detail-actions">
            <span>{article.category ?? 'Artigo'}</span>
            <span>{article.readTimeMinutes} min de leitura</span>
            <span>{formatPublishedDate(article.publishedAt)}</span>
          </div>
        }
        description={article.summary}
        eyebrow="Artigo"
        title={article.title}
      />

      <section className="section">
        <div className="container detail-layout">
          <article className="glass-card detail-card">
            {article.coverImageUrl ? (
              <img alt={article.title} className="detail-cover-image" src={resolveAssetUrl(article.coverImageUrl)} />
            ) : null}
            {article.tags.length > 0 ? (
              <div className="detail-tag-row">
                {article.tags.map((tag) => (
                  <span className="detail-tag" key={tag}>
                    #{tag}
                  </span>
                ))}
              </div>
            ) : null}
            <RichContent body={article.body} />
          </article>

          <aside className="glass-card detail-sidebar">
            <h3>Biblioteca de artigos</h3>
            <p>Volte para a selecao de leituras e continue explorando temas publicados pela nutricionista.</p>
            <div className="detail-sidebar-actions">
              <Link className="button button-secondary" to="/conteudos#artigos">
                Voltar para artigos
              </Link>
              <Link className="button button-tertiary" to="/planos">
                Conhecer planos
              </Link>
            </div>
          </aside>
        </div>
      </section>
    </>
  );
}
