import { useEffect } from 'react';
import { NavLink, Outlet, useLocation } from 'react-router-dom';
import { useEditorialSession } from '../hooks/useEditorialSession';

const editorialNavigation = [
  { to: '/editor', label: 'Dashboard' },
  { to: '/editor/posts', label: 'Posts' },
  { to: '/editor/articles', label: 'Artigos' },
  { to: '/editor/recipes', label: 'Receitas' },
];

export function EditorialLayout() {
  const location = useLocation();
  const { signOut } = useEditorialSession();

  useEffect(() => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }, [location.pathname]);

  return (
    <div className="editorial-shell">
      <aside className="editorial-sidebar">
        <div className="editorial-sidebar-panel">
          <div className="editorial-brand">
            <span className="brand-mark">NC</span>
            <div>
              <strong>NutriCore CMS</strong>
              <span>Gestao editorial provisoria</span>
            </div>
          </div>

          <nav aria-label="Navegacao editorial" className="editorial-nav">
            {editorialNavigation.map((item) => (
              <NavLink
                key={item.to}
                className={({ isActive }) => `editorial-nav-link${isActive ? ' active' : ''}`}
                end={item.to === '/editor'}
                to={item.to}
              >
                {item.label}
              </NavLink>
            ))}
          </nav>

          <div className="editorial-sidebar-footer">
            <p>
              Esta camada privada ainda usa token provisorio em vez de autenticacao real. A substituicao por JWT vira
              na proxima etapa.
            </p>
            <button className="button button-secondary" onClick={signOut} type="button">
              Encerrar acesso
            </button>
          </div>
        </div>
      </aside>

      <div className="editorial-main-shell">
        <header className="editorial-topbar">
          <div>
            <span className="section-eyebrow">Area privada</span>
            <h1>Conteudo publico controlado pela nutricionista</h1>
          </div>
        </header>

        <main className="editorial-main">
          <Outlet />
        </main>
      </div>
    </div>
  );
}
