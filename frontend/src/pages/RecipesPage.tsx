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
        description="Receitas pensadas para repertorio, praticidade e aderencia ao plano alimentar."
        eyebrow="Receitas"
        title="Preparos saudaveis para dias corridos e metas ambiciosas."
      />

      <section className="section recipes-section">
        <div className="container">
          <SectionHeading
            description="As receitas reforcam repertorio alimentar, praticidade e uma relacao mais leve com a rotina de preparo."
            eyebrow="Biblioteca"
            title="Sugestoes com preparo simples, imagem clara e orientacao objetiva."
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
