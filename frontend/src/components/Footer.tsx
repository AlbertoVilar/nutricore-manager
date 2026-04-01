import { Link } from 'react-router-dom';
import { usePublicSiteData } from '../hooks/usePublicSiteData';
import { sanitizeInstagramHandle } from '../utils/formatters';

export function Footer() {
  const { profile } = usePublicSiteData();

  return (
    <footer className="site-footer">
      <div className="container footer-grid">
        <div className="footer-brand">
          <div className="brand footer-brand-mark">
            <span className="brand-mark">NC</span>
            <div>
              <strong>NutriCore</strong>
              <span>Plataforma publica + clinica</span>
            </div>
          </div>
          <p>
            MVP publico construindo a camada comercial da nutricionista sobre uma base backend consistente e pronta
            para evolucao.
          </p>
        </div>

        <div>
          <h4>Rotas publicas</h4>
          <ul className="footer-links">
            <li>
              <Link to="/sobre">Sobre</Link>
            </li>
            <li>
              <Link to="/conteudos">Conteudos</Link>
            </li>
            <li>
              <Link to="/receitas">Receitas</Link>
            </li>
            <li>
              <Link to="/planos">Planos</Link>
            </li>
            <li>
              <Link to="/editor/acesso">Acesso editorial</Link>
            </li>
          </ul>
        </div>

        <div>
          <h4>Contato</h4>
          <ul className="footer-links">
            <li>{profile?.contactEmail ?? 'contato@nutricore.com.br'}</li>
            <li>{profile?.contactPhone ?? '+55 11 99999-9999'}</li>
            <li>{profile?.city ?? 'Sao Paulo, SP'}</li>
            <li>
              <a
                href={`https://instagram.com/${sanitizeInstagramHandle(profile?.instagramHandle ?? 'nutricore')}`}
                rel="noreferrer"
                target="_blank"
              >
                {profile?.instagramHandle ?? '@nutricore'}
              </a>
            </li>
          </ul>
        </div>
      </div>

      <div className="container footer-bottom">
        <span>NutriCore Manager MVP - Frontend publico integrado ao backend Spring.</span>
      </div>
    </footer>
  );
}
