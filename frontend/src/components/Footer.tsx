import type { ReactNode } from 'react';
import { Link } from 'react-router-dom';
import { usePublicSiteData } from '../hooks/usePublicSiteData';
import { sanitizeInstagramHandle } from '../utils/formatters';

function FooterIcon({ children }: { children: ReactNode }) {
  return (
    <span aria-hidden="true" className="footer-contact-icon">
      {children}
    </span>
  );
}

export function Footer() {
  const { profile } = usePublicSiteData();
  const contactEmail = profile?.contactEmail ?? 'contato@nutricore.com.br';
  const contactPhone = profile?.contactPhone ?? '+55 11 99999-9999';
  const instagramHandle = profile?.instagramHandle ?? '@nutricore';
  const instagramUrl = `https://instagram.com/${sanitizeInstagramHandle(instagramHandle)}`;
  const phoneHref = `tel:${contactPhone.replace(/[^\d+]/g, '')}`;

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
            <li>
              <a className="footer-contact-link" href={`mailto:${contactEmail}`}>
                <FooterIcon>
                  <svg fill="none" height="18" viewBox="0 0 24 24" width="18">
                    <path
                      d="M4 7.5 11.214 12.55a1.35 1.35 0 0 0 1.572 0L20 7.5M5.6 19h12.8A1.6 1.6 0 0 0 20 17.4V6.6A1.6 1.6 0 0 0 18.4 5H5.6A1.6 1.6 0 0 0 4 6.6v10.8A1.6 1.6 0 0 0 5.6 19Z"
                      stroke="currentColor"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="1.8"
                    />
                  </svg>
                </FooterIcon>
                <span>{contactEmail}</span>
              </a>
            </li>
            <li>
              <a className="footer-contact-link" href={phoneHref}>
                <FooterIcon>
                  <svg fill="none" height="18" viewBox="0 0 24 24" width="18">
                    <path
                      d="M8.6 4.8h1.7c.36 0 .68.24.78.58l.9 3.16a.8.8 0 0 1-.21.8l-1.25 1.26a12.8 12.8 0 0 0 3.61 3.61l1.26-1.25a.8.8 0 0 1 .8-.21l3.16.9c.34.1.58.42.58.78v1.7A1.8 1.8 0 0 1 18.1 20 14.1 14.1 0 0 1 4 5.9 1.8 1.8 0 0 1 5.9 4h2.7Z"
                      stroke="currentColor"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="1.8"
                    />
                  </svg>
                </FooterIcon>
                <span>{contactPhone}</span>
              </a>
            </li>
            <li>{profile?.city ?? 'São Paulo, SP'}</li>
            <li>{profile?.officeAddress ?? 'Endereço do consultório indisponível.'}</li>
            <li>
              <a className="footer-contact-link" href={instagramUrl} rel="noreferrer" target="_blank">
                <FooterIcon>
                  <svg fill="none" height="18" viewBox="0 0 24 24" width="18">
                    <rect
                      height="14"
                      rx="4.2"
                      stroke="currentColor"
                      strokeWidth="1.8"
                      width="14"
                      x="5"
                      y="5"
                    />
                    <circle cx="12" cy="12" r="3.2" stroke="currentColor" strokeWidth="1.8" />
                    <circle cx="16.8" cy="7.2" fill="currentColor" r="1" />
                  </svg>
                </FooterIcon>
                <span>{instagramHandle}</span>
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
