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
              <strong>{profile?.fullName ?? 'NutriCore'}</strong>
              <span>{profile?.professionalTitle ?? 'Nutrição clínica'}</span>
            </div>
          </div>
          <p>
            {profile?.footerDescription ??
              'Acompanhamento nutricional com linguagem clara, conteúdo útil e uma jornada pública organizada para quem quer mudar a rotina com estratégia.'}
          </p>
        </div>

        <div>
          <h4>Navegação</h4>
          <ul className="footer-links">
            <li>
              <Link to="/sobre">Sobre</Link>
            </li>
            <li>
              <Link to="/conteudos">Conteúdo</Link>
            </li>
            <li>
              <Link to="/receitas">Receitas</Link>
            </li>
            <li>
              <Link to="/planos">Planos</Link>
            </li>
            <li>
              <Link to="/contato">Contato</Link>
            </li>
            <li>
              <Link to="/acessos">Acessos</Link>
            </li>
          </ul>
        </div>

        <div>
          <h4>Contato</h4>
          <ul className="footer-links">
            <li>{profile?.contactEmail ?? 'contato@nutricore.com.br'}</li>
            <li>{profile?.contactPhone ?? '+55 11 99999-9999'}</li>
            <li>{profile?.city ?? 'São Paulo, SP'}</li>
            <li>{profile?.officeAddress ?? 'Endereço do consultório indisponível.'}</li>
            <li>
              <a
                href={`https://instagram.com/${sanitizeInstagramHandle(profile?.instagramHandle ?? 'nutricore')}`}
                rel="noreferrer"
                target="_blank"
              >
                {profile?.instagramHandle ?? '@nutricore'}
              </a>
            </li>
            {profile?.linkedinUrl ? (
              <li>
                <a href={profile.linkedinUrl} rel="noreferrer" target="_blank">
                  LinkedIn
                </a>
              </li>
            ) : null}
            {profile?.youtubeUrl ? (
              <li>
                <a href={profile.youtubeUrl} rel="noreferrer" target="_blank">
                  YouTube
                </a>
              </li>
            ) : null}
          </ul>
        </div>
      </div>

      <div className="container footer-bottom">
        <span>NutriCore Manager | Site institucional, conteúdo editorial e conversão comercial.</span>
      </div>
    </footer>
  );
}
