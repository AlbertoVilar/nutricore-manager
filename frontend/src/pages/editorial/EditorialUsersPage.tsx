import { useCallback, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ErrorState } from '../../components/ErrorState';
import { LoadingState } from '../../components/LoadingState';
import { activateAdminUser, deactivateAdminUser, getAdminUsers } from '../../services/adminUserService';
import type { AdminEditorialUser } from '../../types/admin-site';
import { formatDateTime } from '../../utils/formatters';

export function EditorialUsersPage() {
  const [users, setUsers] = useState<AdminEditorialUser[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const loadUsers = useCallback(async () => {
    setIsLoading(true);
    setErrorMessage(null);

    try {
      setUsers(await getAdminUsers());
    } catch (error) {
      setErrorMessage(error instanceof Error ? error.message : 'Não foi possível carregar os usuários editoriais.');
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    void loadUsers();
  }, [loadUsers]);

  async function executeAction(callback: () => Promise<unknown>, confirmationMessage?: string) {
    if (confirmationMessage && !window.confirm(confirmationMessage)) {
      return;
    }

    try {
      await callback();
      await loadUsers();
    } catch (error) {
      setErrorMessage(error instanceof Error ? error.message : 'Não foi possível concluir a ação.');
    }
  }

  return (
    <section className="editorial-stack">
      <div className="editorial-toolbar">
        <div>
          <span className="section-eyebrow">Usuários editoriais</span>
          <h2>Contas privadas com acesso ao CMS</h2>
          <p>Gerencie quem entra na área editorial, o papel de cada conta e se o acesso está ativo.</p>
        </div>
        <Link className="button button-primary" to="/editor/usuarios/novo">
          Novo usuário
        </Link>
      </div>

      {isLoading ? <LoadingState message="Carregando usuários..." /> : null}
      {errorMessage && !isLoading ? <ErrorState description={errorMessage} /> : null}

      {!isLoading && !errorMessage ? (
        users.length > 0 ? (
          <div className="editorial-management-grid">
            {users.map((user) => (
              <article key={user.id} className={`glass-card editorial-management-card${user.active ? '' : ' is-muted'}`}>
                <div className="editorial-management-header">
                  <div>
                    <span className="section-eyebrow">{user.role}</span>
                    <h3>{user.fullName}</h3>
                    <p>{user.email}</p>
                  </div>
                  <span className={`editorial-status-badge ${user.active ? 'published' : 'archived'}`}>
                    {user.active ? 'Ativo' : 'Inativo'}
                  </span>
                </div>

                <div className="editorial-management-meta">
                  <span>Criado: {formatDateTime(user.createdAt)}</span>
                  <span>Atualizado: {formatDateTime(user.updatedAt)}</span>
                </div>

                <div className="editorial-management-actions">
                  <Link className="button button-secondary" to={`/editor/usuarios/${user.id}/editar`}>
                    Editar
                  </Link>
                  {user.active ? (
                    <button
                      className="button button-tertiary"
                      onClick={() => {
                        void executeAction(() => deactivateAdminUser(user.id), 'Desativar este usuário editorial?');
                      }}
                      type="button"
                    >
                      Desativar
                    </button>
                  ) : (
                    <button
                      className="button button-primary"
                      onClick={() => {
                        void executeAction(() => activateAdminUser(user.id));
                      }}
                      type="button"
                    >
                      Ativar
                    </button>
                  )}
                </div>
              </article>
            ))}
          </div>
        ) : (
          <div className="glass-card editorial-empty-state">
            <h3>Nenhum usuário editorial cadastrado.</h3>
            <p>Crie a primeira conta extra para dividir a manutenção do site com segurança.</p>
            <Link className="button button-primary" to="/editor/usuarios/novo">
              Criar usuário
            </Link>
          </div>
        )
      ) : null}
    </section>
  );
}
