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
    return <LoadingState message="Carregando publicacoes..." />;
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
        description="Aqui ficam os artigos de aprofundamento, a colecao Rotina da Nutri e os posts rapidos que acompanham o dia a dia da nutricionista."
        eyebrow="Conteudo"
        title="Uma biblioteca publica organizada por profundidade, contexto e rotina."
      />

      <section className="section section-anchor" id="artigos">
        <div className="container">
          <SectionHeading
            description="Artigos estruturados aprofundam temas relevantes de nutricao, adesao, saude e performance."
            eyebrow="Artigos"
            title="Leituras mais completas para quem quer contexto e direcao."
          />

          {errors.articles ? (
            <ErrorState
              description={errors.articles}
              onRetry={() => {
                void refresh();
              }}
            />
          ) : articles.length === 0 ? (
            <ErrorState description="Os artigos publicados vao aparecer aqui assim que entrarem no ar." />
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
            description="A Rotina da Nutri e uma colecao editorial dentro de posts, dedicada a treino, movimento e consistencia fora do consultorio."
            eyebrow="Rotina da Nutri"
            title="Treino, exemplo e disciplina aplicada a vida real."
          />

          {errors.posts ? (
            <ErrorState
              description={errors.posts}
              onRetry={() => {
                void refresh();
              }}
            />
          ) : routinePosts.length === 0 ? (
            <ErrorState description="Ainda nao ha posts de treino publicados nesta colecao." />
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
            description="Posts mais leves para bastidores, reflexoes, dicas rapidas e orientacoes pontuais."
            eyebrow="Posts"
            title="Atualizacoes curtas para manter presenca, clareza e frequencia."
          />

          {errors.posts ? (
            <ErrorState
              description={errors.posts}
              onRetry={() => {
                void refresh();
              }}
            />
          ) : generalPosts.length === 0 ? (
            <ErrorState description="Nao ha posts curtos publicados alem da colecao de treino neste momento." />
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
