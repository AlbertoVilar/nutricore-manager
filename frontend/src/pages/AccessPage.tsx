import { Link } from 'react-router-dom';
import { PageHero } from '../components/PageHero';

export function AccessPage() {
  return (
    <>
      <PageHero
        description="Escolha o tipo de acesso disponível. A área administrativa já está pronta para login. A área do aluno fica reservada aqui para a próxima etapa do produto."
        eyebrow="Acessos"
        title="Entradas privadas do NutriCore."
      />

      <section className="section">
        <div className="container access-grid">
          <article className="glass-card access-card">
            <span className="section-eyebrow">Admin</span>
            <h2>Área da nutricionista</h2>
            <p>
              Login para gerenciar posts, artigos, receitas, publicação e mídia do site público com autenticação
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
            <h2>Área do aluno</h2>
            <p>
              Este espaço vai concentrar acompanhamento, materiais e acesso privado do aluno quando essa camada entrar
              na próxima fase do produto.
            </p>
            <span className="access-status">Em breve</span>
          </article>
        </div>
      </section>
    </>
  );
}
