import { ErrorState } from '../components/ErrorState';
import { LoadingState } from '../components/LoadingState';
import { PageHero } from '../components/PageHero';
import { RecipeCard } from '../components/RecipeCard';
import { SectionHeading } from '../components/SectionHeading';
import { usePublicSiteData } from '../hooks/usePublicSiteData';

export function RecipesPage() {
  const { errors, isLoading, recipes, refresh } = usePublicSiteData();

  if (isLoading && recipes.length === 0) {
    return <LoadingState message="Carregando receitas..." />;
  }

  return (
    <>
      <PageHero
        description="Receitas pensadas para repertório, praticidade e aderência ao plano alimentar."
        eyebrow="Receitas"
        title="Preparos saudáveis para dias corridos e metas ambiciosas."
      />

      <section className="section recipes-section">
        <div className="container">
          <SectionHeading
            description="As receitas reforçam repertório alimentar, praticidade e uma relação mais leve com a rotina de preparo."
            eyebrow="Biblioteca"
            title="Sugestões com preparo simples, imagem clara e orientação objetiva."
          />

          {errors.recipes ? (
            <ErrorState
              description={errors.recipes}
              onRetry={() => {
                void refresh();
              }}
            />
          ) : (
            <div className="recipe-grid">
              {recipes.map((recipe) => (
                <RecipeCard key={recipe.id} recipe={recipe} />
              ))}
            </div>
          )}
        </div>
      </section>
    </>
  );
}
