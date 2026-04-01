import type { PublicRecipe } from '../types/public-content';

interface RecipeCardProps {
  recipe: PublicRecipe;
}

export function RecipeCard({ recipe }: RecipeCardProps) {
  return (
    <article className="recipe-card">
      <img alt={recipe.title} src={recipe.imageUrl} />
      <div className="recipe-overlay">
        <span className="recipe-meta">
          {recipe.prepTimeMinutes} min · {recipe.caloriesLabel}
        </span>
        <h3>{recipe.title}</h3>
        <p>{recipe.summary}</p>
      </div>
    </article>
  );
}
