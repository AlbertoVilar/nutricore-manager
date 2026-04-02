import { Link } from 'react-router-dom';
import { ArticleCard } from '../components/ArticleCard';
import { ErrorState } from '../components/ErrorState';
import { HeroSection } from '../components/HeroSection';
import { LoadingState } from '../components/LoadingState';
import { MetricCard } from '../components/MetricCard';
import { PlanCard } from '../components/PlanCard';
import { PostCard } from '../components/PostCard';
import { RecipeCard } from '../components/RecipeCard';
import { SectionHeading } from '../components/SectionHeading';
import { TestimonialCard } from '../components/TestimonialCard';
import { TrainingRoutineSpotlight } from '../components/TrainingRoutineSpotlight';
import { usePublicSiteData } from '../hooks/usePublicSiteData';
import { getGeneralPosts, getRoutineSpotlightPost } from '../utils/contentCollections';

export function HomePage() {
  const { articles, errors, isLoading, plans, posts, profile, recipes, refresh } = usePublicSiteData();
  const hasBlockingError = !isLoading && !profile;
  const featuredArticle = articles.find((article) => article.featured) ?? articles[0] ?? null;
  const trainingPost = getRoutineSpotlightPost(posts);
  const generalPosts = getGeneralPosts(posts);

  return (
    <>
      {isLoading && !profile ? <LoadingState message="Preparando a experiência pública..." /> : null}

      {profile ? <HeroSection profile={profile} /> : null}

      {hasBlockingError ? (
        <section className="section">
          <div className="container">
            <ErrorState
              description="Não foi possível carregar a apresentação principal do site agora."
              onRetry={() => {
                void refresh();
              }}
            />
          </div>
        </section>
      ) : null}

      {profile ? (
        <section className="section">
          <div className="container">
            <SectionHeading
              centered
              description={profile.howItWorksDescription}
              eyebrow="Como funciona"
              title={profile.howItWorksTitle}
            />

            <div className="metric-grid">
              {profile.siteMetrics.map((metric) => (
                <MetricCard key={`${metric.title}-${metric.value}`} metric={metric} />
              ))}
            </div>
          </div>
        </section>
      ) : null}

      {profile ? (
        <section className="section section-soft">
          <div className="container two-column-grid">
            <div>
              <SectionHeading
                description={profile.approachDescription}
                eyebrow="Abordagem"
                title={profile.approachTitle}
              />
              <div className="pillar-stack">
                {profile.servicePillars.map((pillar) => (
                  <article key={pillar.title} className="glass-card pillar-card">
                    <h3>{pillar.title}</h3>
                    <p>{pillar.description}</p>
                  </article>
                ))}
              </div>
            </div>

            <div className="glass-card callout-card">
              <span className="section-eyebrow">Sobre a nutricionista</span>
              <h3>{profile.aboutTitle}</h3>
              <p>{profile.biographySummary}</p>
              <Link className="button button-secondary" to="/sobre">
                Conhecer a profissional
              </Link>
            </div>
          </div>
        </section>
      ) : null}

      <section className="section plans-section">
        <div className="container">
          <SectionHeading
            description={profile?.plansDescription ?? 'Os planos de atendimento não foram carregados.'}
            eyebrow="Planos"
            title={profile?.plansTitle ?? 'Planos de acompanhamento'}
          />

          {errors.plans ? (
            <ErrorState description="Os planos de atendimento não foram carregados." />
          ) : (
            <div className="plan-grid">
              {plans.map((plan) => (
                <PlanCard key={plan.id} plan={plan} />
              ))}
            </div>
          )}

          <div className="section-actions">
            <Link className="button button-primary" to="/planos">
              Ver detalhes dos planos
            </Link>
            <Link className="button button-secondary" to="/contato">
              Tirar dúvidas sobre atendimento
            </Link>
          </div>
        </div>
      </section>

      {trainingPost ? <TrainingRoutineSpotlight post={trainingPost} /> : null}

      <section className="section">
        <div className="container">
          <SectionHeading
            description="Artigos aprofundam temas importantes. Posts curtos mantêm o contato frequente com treino, alimentação e vida real."
            eyebrow="Conteúdo"
            title="Biblioteca para orientar com clareza antes, durante e depois da consulta."
          />

          {errors.articles ? (
            <ErrorState description="Os artigos em destaque não puderam ser exibidos." />
          ) : featuredArticle || generalPosts.length > 0 ? (
            <div className="card-grid home-content-grid">
              {featuredArticle ? (
                <ArticleCard article={featuredArticle} />
              ) : (
                <ErrorState description="Nenhum artigo publicado foi encontrado no momento." />
              )}

              {errors.posts ? (
                <ErrorState description="Os posts publicados não puderam ser exibidos." />
              ) : generalPosts.length > 0 ? (
                generalPosts.slice(0, 2).map((post) => <PostCard key={post.id} post={post} />)
              ) : (
                <ErrorState description="Novos posts curtos e bastidores entram aqui assim que forem publicados." />
              )}
            </div>
          ) : (
            <ErrorState description="A biblioteca pública ainda não tem conteúdos suficientes para destaque nesta seção." />
          )}

          <div className="section-actions">
            <Link className="button button-secondary" to="/conteudos">
              Explorar biblioteca
            </Link>
          </div>
        </div>
      </section>

      <section className="section recipes-section">
        <div className="container">
          <SectionHeading
            description="Receitas publicadas ajudam a mostrar repertório, praticidade e escolhas que respeitam a rotina."
            eyebrow="Receitas"
            title="Sugestões saudáveis para manter consistência sem perder prazer em comer."
          />

          {errors.recipes ? (
            <ErrorState description="As receitas públicas não puderam ser exibidas." />
          ) : (
            <div className="recipe-grid">
              {recipes.slice(0, 3).map((recipe) => (
                <RecipeCard key={recipe.id} recipe={recipe} />
              ))}
            </div>
          )}

          <div className="section-actions">
            <Link className="button button-secondary" to="/receitas">
              Ver receitas
            </Link>
          </div>
        </div>
      </section>

      {profile ? (
        <section className="section section-soft">
          <div className="container">
            <SectionHeading
              centered
              description="A jornada nutricional ganha aderência quando existe acolhimento, clareza e ajuste fino ao longo do caminho."
              eyebrow="Relatos"
              title="Percepções de quem buscou mais estrutura para comer, treinar e sustentar resultado."
            />

            <div className="testimonial-grid">
              {profile.testimonials.map((testimonial) => (
                <TestimonialCard key={`${testimonial.name}-${testimonial.label}`} testimonial={testimonial} />
              ))}
            </div>
          </div>
        </section>
      ) : null}

      {profile ? (
        <section className="section cta-section">
          <div className="container cta-banner">
            <div>
              <span className="section-eyebrow">Contato</span>
              <h2>{profile.finalCtaTitle}</h2>
              <p>{profile.finalCtaDescription}</p>
            </div>

            <div className="cta-actions">
              <a className="button button-primary" href={profile.primaryCtaUrl} rel="noreferrer" target="_blank">
                {profile.primaryCtaLabel}
              </a>
              <Link className="button button-secondary" to="/planos">
                Conhecer os planos
              </Link>
            </div>
          </div>
        </section>
      ) : null}
    </>
  );
}
