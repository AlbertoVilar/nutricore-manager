import { Navigate, Route, Routes } from 'react-router-dom';
import { PublicLayout } from '../layouts/PublicLayout';
import { EditorialLayout } from '../layouts/EditorialLayout';
import { AboutPage } from '../pages/AboutPage';
import { ArticleDetailPage } from '../pages/ArticleDetailPage';
import { ContactPage } from '../pages/ContactPage';
import { ContentPage } from '../pages/ContentPage';
import { HomePage } from '../pages/HomePage';
import { NotFoundPage } from '../pages/NotFoundPage';
import { PlansPage } from '../pages/PlansPage';
import { PostDetailPage } from '../pages/PostDetailPage';
import { RecipeDetailPage } from '../pages/RecipeDetailPage';
import { RecipesPage } from '../pages/RecipesPage';
import { EditorialAccessPage } from '../pages/editorial/EditorialAccessPage';
import { EditorialArticleEditorPage } from '../pages/editorial/EditorialArticleEditorPage';
import { EditorialArticlesPage } from '../pages/editorial/EditorialArticlesPage';
import { EditorialDashboardPage } from '../pages/editorial/EditorialDashboardPage';
import { EditorialPostEditorPage } from '../pages/editorial/EditorialPostEditorPage';
import { EditorialPostsPage } from '../pages/editorial/EditorialPostsPage';
import { EditorialRecipeEditorPage } from '../pages/editorial/EditorialRecipeEditorPage';
import { EditorialRecipesPage } from '../pages/editorial/EditorialRecipesPage';
import { EditorialGuard } from './EditorialGuard';

export function AppRoutes() {
  return (
    <Routes>
      <Route path="/editor/acesso" element={<EditorialAccessPage />} />

      <Route element={<PublicLayout />}>
        <Route index element={<HomePage />} />
        <Route path="/sobre" element={<AboutPage />} />
        <Route path="/conteudos" element={<ContentPage />} />
        <Route path="/conteudos/posts/:slug" element={<PostDetailPage />} />
        <Route path="/conteudos/artigos/:slug" element={<ArticleDetailPage />} />
        <Route path="/receitas" element={<RecipesPage />} />
        <Route path="/receitas/:slug" element={<RecipeDetailPage />} />
        <Route path="/planos" element={<PlansPage />} />
        <Route path="/contato" element={<ContactPage />} />
        <Route path="/404" element={<NotFoundPage />} />
      </Route>

      <Route element={<EditorialGuard />}>
        <Route element={<EditorialLayout />}>
          <Route path="/editor" element={<EditorialDashboardPage />} />
          <Route path="/editor/posts" element={<EditorialPostsPage />} />
          <Route path="/editor/posts/novo" element={<EditorialPostEditorPage />} />
          <Route path="/editor/posts/:id/editar" element={<EditorialPostEditorPage />} />
          <Route path="/editor/articles" element={<EditorialArticlesPage />} />
          <Route path="/editor/articles/novo" element={<EditorialArticleEditorPage />} />
          <Route path="/editor/articles/:id/editar" element={<EditorialArticleEditorPage />} />
          <Route path="/editor/recipes" element={<EditorialRecipesPage />} />
          <Route path="/editor/recipes/novo" element={<EditorialRecipeEditorPage />} />
          <Route path="/editor/recipes/:id/editar" element={<EditorialRecipeEditorPage />} />
        </Route>
      </Route>

      <Route path="*" element={<Navigate replace to="/404" />} />
    </Routes>
  );
}
