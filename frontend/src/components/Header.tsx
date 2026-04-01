import { Link, NavLink } from 'react-router-dom';

const navigationItems = [
  { to: '/', label: 'Inicio' },
  { to: '/sobre', label: 'Sobre' },
  { to: '/conteudos', label: 'Conteudo' },
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
            <span>Nutricao clinica e rotina real</span>
          </div>
        </Link>

        <nav className="main-nav" aria-label="Navegacao principal">
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

        <Link className="button button-primary header-cta" to="/contato">
          Agendar avaliacao
        </Link>
      </div>
    </header>
  );
}
