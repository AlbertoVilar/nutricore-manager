import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { ErrorState } from '../components/ErrorState';
import { LoadingState } from '../components/LoadingState';
import { PageHero } from '../components/PageHero';
import { getPublicRecipeBySlug } from '../services/publicContentService';
import type { PublicRecipe } from '../types/public-content';
import { formatPublishedDate } from '../utils/formatters';
import { resolveAssetUrl } from '../utils/media';

export function RecipeDetailPage() {
  const { slug = '' } = useParams();
  const [recipe, setRecipe] = useState<PublicRecipe | null>(null);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    async function loadRecipe() {
      setIsLoading(true);
      setErrorMessage(null);

      try {
        setRecipe(await getPublicRecipeBySlug(slug));
      } catch (error) {
        setErrorMessage(error instanceof Error ? error.message : 'Nao foi possivel carregar a receita.');
      } finally {
        setIsLoading(false);
      }
    }

    void loadRecipe();
  }, [slug]);

  if (isLoading) {
    return <LoadingState message="Carregando receita..." />;
  }

  if (!recipe || errorMessage) {
    return <ErrorState description={errorMessage ?? 'Receita nao encontrada.'} />;
  }

  return (
    <>
      <PageHero
        actions={
          <div className="content-detail-actions">
            <span>{recipe.category ?? 'Receita'}</span>
            <span>{recipe.prepTimeMinutes} min</span>
            <span>{formatPublishedDate(recipe.publishedAt)}</span>
          </div>
        }
        description={recipe.description}
        eyebrow="Receita"
        title={recipe.title}
      />

      <section className="section">
        <div className="container detail-layout">
          <article className="glass-card detail-card">
            {recipe.imageUrl ? <img alt={recipe.title} className="detail-cover-image" src={resolveAssetUrl(recipe.imageUrl)} /> : null}

            <div className="recipe-detail-grid">
              <div>
                <h3>Ingredientes</h3>
                <ul className="detail-list">
                  {recipe.ingredients.map((ingredient) => (
                    <li key={ingredient}>{ingredient}</li>
                  ))}
                </ul>
              </div>

              <div>
                <h3>Modo de preparo</h3>
                <ol className="detail-list ordered">
                  {recipe.preparationSteps.map((step) => (
                    <li key={step}>{step}</li>
                  ))}
                </ol>
              </div>
            </div>
          </article>

          <aside className="glass-card detail-sidebar">
            <h3>Rendimento</h3>
            <p>{recipe.yieldInfo}</p>
            <div className="detail-sidebar-actions">
              <Link className="button button-secondary" to="/receitas">
                Ver mais receitas
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
