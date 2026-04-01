import { Navigate, Route, Routes } from 'react-router-dom';
import { PublicLayout } from '../layouts/PublicLayout';
import { AboutPage } from '../pages/AboutPage';
import { ContactPage } from '../pages/ContactPage';
import { ContentPage } from '../pages/ContentPage';
import { HomePage } from '../pages/HomePage';
import { NotFoundPage } from '../pages/NotFoundPage';
import { PlansPage } from '../pages/PlansPage';
import { RecipesPage } from '../pages/RecipesPage';

export function AppRoutes() {
  return (
    <Routes>
      <Route element={<PublicLayout />}>
        <Route index element={<HomePage />} />
        <Route path="/sobre" element={<AboutPage />} />
        <Route path="/conteudos" element={<ContentPage />} />
        <Route path="/receitas" element={<RecipesPage />} />
        <Route path="/planos" element={<PlansPage />} />
        <Route path="/contato" element={<ContactPage />} />
        <Route path="/404" element={<NotFoundPage />} />
      </Route>
      <Route path="*" element={<Navigate replace to="/404" />} />
    </Routes>
  );
}
