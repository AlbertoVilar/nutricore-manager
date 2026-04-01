import { useCallback, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { EditorialContentTable } from '../../components/editorial/EditorialContentTable';
import {
  EditorialStatusFilter,
  type EditorialStatusFilterValue,
} from '../../components/editorial/EditorialStatusFilter';
import { ErrorState } from '../../components/ErrorState';
import { LoadingState } from '../../components/LoadingState';
import { useEditorialSession } from '../../hooks/useEditorialSession';
import {
  archiveAdminRecipe,
  deleteAdminRecipe,
  draftAdminRecipe,
  getAdminRecipes,
  publishAdminRecipe,
} from '../../services/editorialRecipeService';
import type { AdminRecipe } from '../../types/editorial';

export function EditorialRecipesPage() {
  const { token } = useEditorialSession();
  const [recipes, setRecipes] = useState<AdminRecipe[]>([]);
  const [filter, setFilter] = useState<EditorialStatusFilterValue>('ALL');
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const loadRecipes = useCallback(async () => {
    setIsLoading(true);
    setErrorMessage(null);

    try {
      setRecipes(await getAdminRecipes(token, filter));
    } catch (error) {
      setErrorMessage(error instanceof Error ? error.message : 'Nao foi possivel carregar as receitas.');
    } finally {
      setIsLoading(false);
    }
  }, [filter, token]);

  useEffect(() => {
    void loadRecipes();
  }, [loadRecipes]);

  async function executeAction(callback: () => Promise<unknown>, confirmationMessage?: string) {
    if (confirmationMessage && !window.confirm(confirmationMessage)) {
      return;
    }

    try {
      await callback();
      await loadRecipes();
    } catch (error) {
      setErrorMessage(error instanceof Error ? error.message : 'Nao foi possivel concluir a acao.');
    }
  }

  return (
    <section className="editorial-stack">
      <div className="editorial-toolbar">
        <div>
          <span className="section-eyebrow">Receitas</span>
          <h2>Biblioteca publica com ingredientes, modo de preparo e imagem.</h2>
        </div>
        <Link className="button button-primary" to="/editor/recipes/novo">
          Nova receita
        </Link>
      </div>

      <EditorialStatusFilter onChange={setFilter} value={filter} />

      {isLoading ? <LoadingState message="Carregando receitas..." /> : null}
      {errorMessage && !isLoading ? <ErrorState description={errorMessage} /> : null}

      {!isLoading && !errorMessage ? (
        <EditorialContentTable
          createHref="/editor/recipes/novo"
          editHrefBase="/editor/recipes"
          emptyDescription="Receitas publicadas ajudam a demonstrar utilidade pratica do acompanhamento."
          emptyTitle="Nenhuma receita encontrada para o filtro atual."
          entityLabel="receita"
          onArchive={(id) => {
            void executeAction(() => archiveAdminRecipe(token, id), 'Arquivar esta receita?');
          }}
          onDelete={(id) => {
            void executeAction(() => deleteAdminRecipe(token, id), 'Excluir esta receita permanentemente?');
          }}
          onDraft={(id) => {
            void executeAction(() => draftAdminRecipe(token, id), 'Mover esta receita de volta para rascunho?');
          }}
          onPublish={(id) => {
            void executeAction(() => publishAdminRecipe(token, id));
          }}
          rows={recipes.map((recipe) => ({
            id: recipe.id,
            title: recipe.title,
            slug: recipe.slug,
            summary: recipe.description,
            category: recipe.category,
            featured: recipe.featured,
            status: recipe.status,
            updatedAt: recipe.updatedAt ?? recipe.createdAt,
            publishedAt: recipe.publishedAt,
            imageUrl: recipe.imageUrl,
          }))}
        />
      ) : null}
    </section>
  );
}
