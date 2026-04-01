import { Link, NavLink } from 'react-router-dom';

const navigationItems = [
  { to: '/', label: 'Início' },
  { to: '/sobre', label: 'Sobre' },
  { to: '/conteudos', label: 'Conteúdo' },
  { to: '/receitas', label: 'Receitas' },
  { to: '/planos', label: 'Planos' },
  { to: '/contato', label: 'Contato' },
];

export function Header() {
  return (
    <header className="site-header">
      <div className="container header-content">
        <Link className="brand" to="/">
          <span className="brand-mark">NC</span>
          <div>
            <strong>NutriCore</strong>
            <span>Nutrição clínica e rotina real</span>
          </div>
        </Link>

        <nav className="main-nav" aria-label="Navegação principal">
          {navigationItems.map((item) => (
            <NavLink
              key={item.to}
              className={({ isActive }) => `nav-link${isActive ? ' active' : ''}`}
              to={item.to}
            >
              {item.label}
            </NavLink>
          ))}
        </nav>

        <div className="header-actions">
          <Link className="button button-secondary" to="/acessos">
            Acessos
          </Link>
          <Link className="button button-primary header-cta" to="/contato">
            Agendar avaliação
          </Link>
        </div>
      </div>
    </header>
  );
}
