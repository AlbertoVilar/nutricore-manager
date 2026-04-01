import { ErrorState } from '../components/ErrorState';
import { LoadingState } from '../components/LoadingState';
import { PageHero } from '../components/PageHero';
import { PostCard } from '../components/PostCard';
import { SectionHeading } from '../components/SectionHeading';
import { usePublicSiteData } from '../hooks/usePublicSiteData';

export function ContentPage() {
  const { errors, isLoading, posts, refresh } = usePublicSiteData();

  if (isLoading && posts.length === 0) {
    return <LoadingState message="Carregando publicacoes..." />;
  }

  const contentError = errors.find((item) => item.includes('conteudos'));

  return (
    <>
      <PageHero
        description="Area publica para artigos, orientacoes e temas ligados a dieta, treino, bem-estar e rotina."
        eyebrow="Conteudos"
        title="Publicacoes organizadas para educar e converter."
      />

      <section className="section">
        <div className="container">
          <SectionHeading
            description="Os cards abaixo sao consumidos do backend e validam a camada editorial do MVP."
            eyebrow="Editorial"
            title="Posts prontos para crescer em uma esteira real de conteudo."
          />

          {contentError ? (
            <ErrorState
              description={contentError}
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
