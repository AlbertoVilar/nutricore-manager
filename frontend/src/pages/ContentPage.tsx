import { ArticleCard } from '../components/ArticleCard';
import { ErrorState } from '../components/ErrorState';
import { LoadingState } from '../components/LoadingState';
import { PageHero } from '../components/PageHero';
import { PostCard } from '../components/PostCard';
import { SectionHeading } from '../components/SectionHeading';
import { usePublicSiteData } from '../hooks/usePublicSiteData';
import { getGeneralPosts, getRoutinePosts } from '../utils/contentCollections';

export function ContentPage() {
  const { articles, errors, isLoading, posts, refresh } = usePublicSiteData();
  const routinePosts = getRoutinePosts(posts);
  const generalPosts = getGeneralPosts(posts);

  if (isLoading && posts.length === 0 && articles.length === 0) {
    return <LoadingState message="Carregando publicações..." />;
  }

  return (
    <>
      <PageHero
        actions={
          <div className="section-link-row">
            <a className="button button-secondary" href="#artigos">
              Artigos
            </a>
            <a className="button button-secondary" href="#rotina-da-nutri">
              Rotina da Nutri
            </a>
            <a className="button button-secondary" href="#posts">
              Posts e bastidores
            </a>
          </div>
        }
        description="Aqui ficam os artigos de aprofundamento, a coleção Rotina da Nutri e os posts rápidos que acompanham o dia a dia da nutricionista."
        eyebrow="Conteúdo"
        title="Uma biblioteca pública organizada por profundidade, contexto e rotina."
      />

      <section className="section section-anchor" id="artigos">
        <div className="container">
          <SectionHeading
            description="Artigos estruturados aprofundam temas relevantes de nutrição, adesão, saúde e performance."
            eyebrow="Artigos"
            title="Leituras mais completas para quem quer contexto e direção."
          />

          {errors.articles ? (
            <ErrorState
              description={errors.articles}
              onRetry={() => {
                void refresh();
              }}
            />
          ) : articles.length === 0 ? (
            <ErrorState description="Os artigos publicados vão aparecer aqui assim que entrarem no ar." />
          ) : (
            <div className="card-grid">
              {articles.map((article) => (
                <ArticleCard article={article} key={article.id} />
              ))}
            </div>
          )}
        </div>
      </section>

      <section className="section section-soft section-anchor" id="rotina-da-nutri">
        <div className="container">
          <SectionHeading
            description="A Rotina da Nutri é uma coleção editorial dentro de posts, dedicada a treino, movimento e consistência fora do consultório."
            eyebrow="Rotina da Nutri"
            title="Treino, exemplo e disciplina aplicada à vida real."
          />

          {errors.posts ? (
            <ErrorState
              description={errors.posts}
              onRetry={() => {
                void refresh();
              }}
            />
          ) : routinePosts.length === 0 ? (
            <ErrorState description="Ainda não há posts de treino publicados nesta coleção." />
          ) : (
            <div className="card-grid">
              {routinePosts.map((post) => (
                <PostCard key={post.id} post={post} />
              ))}
            </div>
          )}
        </div>
      </section>

      <section className="section section-anchor" id="posts">
        <div className="container">
          <SectionHeading
            description="Posts mais leves para bastidores, reflexões, dicas rápidas e orientações pontuais."
            eyebrow="Posts"
            title="Atualizações curtas para manter presença, clareza e frequência."
          />

          {errors.posts ? (
            <ErrorState
              description={errors.posts}
              onRetry={() => {
                void refresh();
              }}
            />
          ) : generalPosts.length === 0 ? (
            <ErrorState description="Não há posts curtos publicados além da coleção de treino neste momento." />
          ) : (
            <div className="card-grid">
              {generalPosts.map((post) => (
                <PostCard key={post.id} post={post} />
              ))}
            </div>
          )}
        </div>
      </section>
    </>
  );
}
