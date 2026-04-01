import { ArticleCard } from '../components/ArticleCard';
import { ErrorState } from '../components/ErrorState';
import { LoadingState } from '../components/LoadingState';
import { PageHero } from '../components/PageHero';
import { PostCard } from '../components/PostCard';
import { SectionHeading } from '../components/SectionHeading';
import { usePublicSiteData } from '../hooks/usePublicSiteData';

export function ContentPage() {
  const { articles, errors, isLoading, posts, refresh } = usePublicSiteData();

  if (isLoading && posts.length === 0 && articles.length === 0) {
    return <LoadingState message="Carregando publicacoes..." />;
  }

  const postError = errors.find((item) => item.includes('conteudos'));
  const articleError = errors.find((item) => item.includes('artigos'));

  return (
    <>
      <PageHero
        description="Area publica para artigos, orientacoes, bastidores, videos e temas ligados a dieta, treino, bem-estar e rotina."
        eyebrow="Conteudos"
        title="Biblioteca editorial para educar, converter e manter presenca profissional."
      />

      <section className="section">
        <div className="container">
          <SectionHeading
            description="Artigos estruturados reforcam autoridade e profundidade de tema."
            eyebrow="Artigos"
            title="Conteudos longos para orientar e posicionar a nutricionista."
          />

          {articleError ? (
            <ErrorState
              description={articleError}
              onRetry={() => {
                void refresh();
              }}
            />
          ) : (
            <div className="card-grid">
              {articles.map((article) => (
                <ArticleCard article={article} key={article.id} />
              ))}
            </div>
          )}
        </div>
      </section>

      <section className="section section-soft">
        <div className="container">
          <SectionHeading
            description="Posts curtos e dinamicos para rotina profissional, dicas rapidas, treino e bastidores."
            eyebrow="Posts"
            title="Atualizacoes mais leves, com opcao de video e linguagem mais direta."
          />

          {postError ? (
            <ErrorState
              description={postError}
              onRetry={() => {
                void refresh();
              }}
            />
          ) : (
            <div className="card-grid">
              {posts.map((post) => (
                <PostCard key={post.id} post={post} />
              ))}
            </div>
          )}
        </div>
      </section>
    </>
  );
}
