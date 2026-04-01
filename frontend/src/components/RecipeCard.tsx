import { Link } from 'react-router-dom';
import type { PublicRecipe } from '../types/public-content';
import { resolveAssetUrl } from '../utils/media';

interface RecipeCardProps {
  recipe: PublicRecipe;
}

export function RecipeCard({ recipe }: RecipeCardProps) {
  return (
    <article className="recipe-card">
      {recipe.imageUrl ? <img alt={recipe.title} src={resolveAssetUrl(recipe.imageUrl)} /> : null}
      <div className="recipe-overlay">
        <span className="recipe-meta">
          {recipe.prepTimeMinutes} min - {recipe.yieldInfo}
        </span>
        <h3>{recipe.title}</h3>
        <p>{recipe.description}</p>
        <Link className="content-card-link recipe-link" to={`/receitas/${recipe.slug}`}>
          Ver receita
        </Link>
      </div>
    </article>
  );
}
