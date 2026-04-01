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

  const recipeError = errors.find((item) => item.includes('receitas'));

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
            description="As receitas abaixo vem da API publica e ajudam a mostrar utilidade pratica do site desde o MVP."
            eyebrow="Biblioteca inicial"
            title="Sugestoes visuais com preparo simples e comunicacao clara."
          />

          {recipeError ? (
            <ErrorState
              description={recipeError}
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
