import { Link } from 'react-router-dom';
import { ErrorState } from '../components/ErrorState';
import { HeroSection } from '../components/HeroSection';
import { LoadingState } from '../components/LoadingState';
import { MetricCard } from '../components/MetricCard';
import { PlanCard } from '../components/PlanCard';
import { PostCard } from '../components/PostCard';
import { RecipeCard } from '../components/RecipeCard';
import { SectionHeading } from '../components/SectionHeading';
import { TestimonialCard } from '../components/TestimonialCard';
import { servicePillars, siteMetrics, testimonials } from '../data/site-content';
import { usePublicSiteData } from '../hooks/usePublicSiteData';

export function HomePage() {
  const { errors, isLoading, plans, posts, profile, recipes, refresh } = usePublicSiteData();
  const hasBlockingError = !isLoading && !profile;

  return (
    <>
      {isLoading && !profile ? <LoadingState message="Preparando a experiencia publica do NutriCore..." /> : null}

      {profile ? <HeroSection profile={profile} /> : null}

      {hasBlockingError ? (
        <section className="section">
          <div className="container">
            <ErrorState
              description="O backend nao respondeu com o perfil publico principal. Verifique a API e tente novamente."
              onRetry={() => {
                void refresh();
              }}
            />
          </div>
        </section>
      ) : null}

      <section className="section">
        <div className="container">
          <SectionHeading
            centered
            description="Os cards abaixo reforcam a proposta de valor da camada publica e complementam o conteudo vindo do backend."
            eyebrow="Valor de negocio"
            title="Presenca profissional para converter, educar e preparar a gestao clinica."
          />

          <div className="metric-grid">
            {siteMetrics.map((metric) => (
              <MetricCard key={metric.title} metric={metric} />
            ))}
          </div>
        </div>
      </section>

      <section className="section section-soft">
        <div className="container two-column-grid">
          <div>
            <SectionHeading
              description="A experiencia publica nao e uma vitrine solta. Ela prepara o paciente para uma jornada clinica mais madura e orientada."
              eyebrow="Metodo"
              title="Conteudo, atendimento e acompanhamento conversando na mesma linguagem."
            />
            <div className="pillar-stack">
              {servicePillars.map((pillar) => (
                <article key={pillar.title} className="glass-card pillar-card">
                  <h3>{pillar.title}</h3>
                  <p>{pillar.description}</p>
                </article>
              ))}
            </div>
          </div>

          <div className="glass-card callout-card">
            <span className="section-eyebrow">Sobre a profissional</span>
            <h3>{profile?.aboutTitle ?? 'Atendimento com direcao clinica e comercial.'}</h3>
            <p>
              {profile?.aboutDescription ??
                'A camada publica continua dependente da API para o conteudo principal. Quando o backend responde, esta area mostra o posicionamento oficial da nutricionista.'}
            </p>
            <Link className="button button-secondary" to="/sobre">
              Ver apresentacao completa
            </Link>
          </div>
        </div>
      </section>

      <section className="section">
        <div className="container">
          <SectionHeading
            description="Posts reais vindos do backend para validar a integracao e a estrutura editorial do site."
            eyebrow="Conteudo"
            title="Publicacoes para orientar rotina, treino e estrategia alimentar."
          />

          {errors.includes('Nao foi possivel carregar os conteudos publicados.') ? (
            <ErrorState description="A lista de posts publicos nao foi carregada." />
          ) : (
            <div className="card-grid">
              {posts.slice(0, 3).map((post) => (
                <PostCard key={post.id} post={post} />
              ))}
            </div>
          )}

          <div className="section-actions">
            <Link className="button button-secondary" to="/conteudos">
              Ver todos os conteudos
            </Link>
          </div>
        </div>
      </section>

      <section className="section recipes-section">
        <div className="container">
          <SectionHeading
            description="Receitas publicas para mostrar utilidade pratica, repertorio alimentar e consistencia de marca."
            eyebrow="Receitas"
            title="Preparos saudaveis para manter estrategia e adesao."
          />

          {errors.includes('Nao foi possivel carregar as receitas.') ? (
            <ErrorState description="A lista de receitas publicas nao foi carregada." />
          ) : (
            <div className="recipe-grid">
              {recipes.slice(0, 3).map((recipe) => (
                <RecipeCard key={recipe.id} recipe={recipe} />
              ))}
            </div>
          )}

          <div className="section-actions">
            <Link className="button button-secondary" to="/receitas">
              Explorar receitas
            </Link>
          </div>
        </div>
      </section>

      <section className="section section-soft">
        <div className="container">
          <SectionHeading
            centered
            description="Secao visual reaproveitando os assets da landing original para reforcar prova social e credibilidade."
            eyebrow="Resultados percebidos"
            title="Relatos e contextos que traduzem o impacto do acompanhamento."
          />

          <div className="testimonial-grid">
            {testimonials.map((testimonial) => (
              <TestimonialCard key={testimonial.name} testimonial={testimonial} />
            ))}
          </div>
        </div>
      </section>

      <section className="section plans-section">
        <div className="container">
          <SectionHeading
            description="Planos publicos vindos do backend para o fluxo comercial inicial do site."
            eyebrow="Planos"
            title="Ofertas claras para converter interesse em conversa."
          />

          {errors.includes('Nao foi possivel carregar os planos de atendimento.') ? (
            <ErrorState description="Os planos publicos nao foram carregados." />
          ) : (
            <div className="plan-grid">
              {plans.map((plan) => (
                <PlanCard key={plan.id} plan={plan} />
              ))}
            </div>
          )}
        </div>
      </section>

      <section className="section cta-section">
        <div className="container cta-banner">
          <div>
            <span className="section-eyebrow">Proxima etapa</span>
            <h2>Backend estavel. Frontend publico integrado. Base pronta para crescer.</h2>
            <p>
              O MVP atual fecha a camada publica/comercial com contrato real de API e estrutura
              preparada para futuras rotas protegidas.
            </p>
          </div>

          <div className="cta-actions">
            <Link className="button button-secondary" to="/contato">
              Falar com a nutricionista
            </Link>
            <Link className="button button-tertiary" to="/planos">
              Comparar planos
            </Link>
          </div>
        </div>
      </section>
    </>
  );
}
