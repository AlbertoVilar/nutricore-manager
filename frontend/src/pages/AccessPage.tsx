import { Link } from 'react-router-dom';
import { PageHero } from '../components/PageHero';

export function AccessPage() {
  return (
    <>
      <PageHero
        description="Escolha o tipo de acesso disponivel. A area administrativa ja esta pronta para login. A area do aluno fica reservada aqui para a proxima etapa do produto."
        eyebrow="Acessos"
        title="Entradas privadas do NutriCore."
      />

      <section className="section">
        <div className="container access-grid">
          <article className="glass-card access-card">
            <span className="section-eyebrow">Admin</span>
            <h2>Area da nutricionista</h2>
            <p>
              Login para gerenciar posts, artigos, receitas, publicacao e midia do site publico com autenticacao
              segura.
            </p>
            <div className="cta-actions">
              <Link className="button button-primary" to="/editor/acesso">
                Entrar como admin
              </Link>
            </div>
          </article>

          <article className="glass-card access-card access-card-disabled">
            <span className="section-eyebrow">Aluno</span>
            <h2>Area do aluno</h2>
            <p>
              Este espaco vai concentrar acompanhamento, materiais e acesso privado do aluno quando essa camada entrar
              na proxima fase do produto.
            </p>
            <span className="access-status">Em breve</span>
          </article>
        </div>
      </section>
    </>
  );
}
