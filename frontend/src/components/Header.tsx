import { Link, NavLink } from 'react-router-dom';
import { usePublicSiteData } from '../hooks/usePublicSiteData';

const navigationItems = [
  { to: '/', label: 'Início' },
  { to: '/sobre', label: 'Sobre' },
  { to: '/conteudos', label: 'Conteúdo' },
  { to: '/receitas', label: 'Receitas' },
  { to: '/planos', label: 'Planos' },
  { to: '/contato', label: 'Contato' },
];

export function Header() {
  const { profile } = usePublicSiteData();

  return (
    <header className="site-header">
      <div className="container header-content">
        <Link className="brand" to="/">
          <span className="brand-mark">NC</span>
          <div>
            <strong>{profile?.fullName ?? 'NutriCore'}</strong>
            <span>{profile?.professionalSubtitle ?? 'Nutrição clínica e rotina real'}</span>
          </div>
        </Link>

        <nav aria-label="Navegação principal" className="main-nav">
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
          <a className="button button-primary header-cta" href={profile?.primaryCtaUrl ?? '/contato'} rel="noreferrer" target="_blank">
            {profile?.primaryCtaLabel ?? 'Agendar avaliação'}
          </a>
        </div>
      </div>
    </header>
  );
}
