import { useEffect } from 'react';
import { Link, NavLink, Outlet, useLocation } from 'react-router-dom';
import { useEditorialSession } from '../hooks/useEditorialSession';

export function EditorialLayout() {
  const location = useLocation();
  const { signOut, user } = useEditorialSession();

  const editorialNavigation = [
    { to: '/editor', label: 'Dashboard' },
    { to: '/editor/site', label: 'Site público' },
    { to: '/editor/planos', label: 'Planos' },
    { to: '/editor/posts', label: 'Posts' },
    { to: '/editor/articles', label: 'Artigos' },
    { to: '/editor/recipes', label: 'Receitas' },
    ...(user?.role === 'ADMIN' ? [{ to: '/editor/usuarios', label: 'Usuários' }] : []),
  ];

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
              <span>Área privada da nutricionista</span>
            </div>
          </div>

          {user ? (
            <div className="editorial-user-card">
              <strong>{user.fullName}</strong>
              <span>{user.email}</span>
              <small>{user.role}</small>
            </div>
          ) : null}

          <nav aria-label="Navegação editorial" className="editorial-nav">
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
            <p>Gerencie o site público, os planos comerciais e o conteúdo editorial sem depender de código.</p>
            <div className="editorial-sidebar-actions">
              <Link className="button button-secondary" to="/">
                Ver site público
              </Link>
              <button className="button button-tertiary" onClick={() => signOut()} type="button">
                Sair da sessão
              </button>
            </div>
          </div>
        </div>
      </aside>

      <div className="editorial-main-shell">
        <header className="editorial-topbar">
          <div>
            <span className="section-eyebrow">Área privada</span>
            <h1>Central administrativa</h1>
            <p>Configuração pública, planos, conteúdo editorial e acesso do time em uma jornada consistente.</p>
          </div>
        </header>

        <main className="editorial-main">
          <Outlet />
        </main>
      </div>
    </div>
  );
}
