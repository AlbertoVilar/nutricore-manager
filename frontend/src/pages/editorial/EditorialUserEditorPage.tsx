import { useEffect, useMemo, useState } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { AdminUserForm } from '../../components/editorial/AdminUserForm';
import { EditorialFormSection } from '../../components/editorial/EditorialFormSection';
import { ErrorState } from '../../components/ErrorState';
import { LoadingState } from '../../components/LoadingState';
import {
  createAdminUser,
  getAdminUserById,
  resetAdminUserPassword,
  updateAdminUser,
} from '../../services/adminUserService';
import type {
  AdminEditorialUser,
  AdminEditorialUserCreateInput,
  AdminEditorialUserInput,
} from '../../types/admin-site';

const emptyCreateValue: AdminEditorialUserCreateInput = {
  fullName: '',
  email: '',
  password: '',
  role: 'EDITOR',
  active: true,
};

function toFormValue(user: AdminEditorialUser): AdminEditorialUserInput {
  return {
    fullName: user.fullName,
    email: user.email,
    role: user.role,
    active: user.active,
  };
}

export function EditorialUserEditorPage() {
  const { id } = useParams();
  const isEditMode = Boolean(id);
  const navigate = useNavigate();
  const location = useLocation();
  const [initialValue, setInitialValue] = useState<AdminEditorialUserCreateInput | AdminEditorialUserInput>(
    emptyCreateValue,
  );
  const [isLoading, setIsLoading] = useState(isEditMode);
  const [loadError, setLoadError] = useState<string | null>(null);
  const [submitError, setSubmitError] = useState<string | null>(null);
  const [submitSuccess, setSubmitSuccess] = useState<string | null>(
    (location.state as { flash?: string } | null)?.flash ?? null,
  );
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [passwordValue, setPasswordValue] = useState('');
  const [passwordError, setPasswordError] = useState<string | null>(null);
  const [passwordSuccess, setPasswordSuccess] = useState<string | null>(null);
  const [isResettingPassword, setIsResettingPassword] = useState(false);

  useEffect(() => {
    if (!isEditMode || !id) {
      return;
    }

    async function loadUser() {
      setIsLoading(true);
      setLoadError(null);

      try {
        const response = await getAdminUserById(Number(id));
        setInitialValue(toFormValue(response));
      } catch (error) {
        setLoadError(error instanceof Error ? error.message : 'Não foi possível carregar o usuário editorial.');
      } finally {
        setIsLoading(false);
      }
    }

    void loadUser();
  }, [id, isEditMode]);

  async function handleSubmit(payload: AdminEditorialUserCreateInput | AdminEditorialUserInput) {
    setIsSubmitting(true);
    setSubmitError(null);
    setSubmitSuccess(null);

    try {
      const response = isEditMode && id
        ? await updateAdminUser(Number(id), payload as AdminEditorialUserInput)
        : await createAdminUser(payload as AdminEditorialUserCreateInput);

      if (!isEditMode) {
        navigate(`/editor/usuarios/${response.id}/editar`, {
          replace: true,
          state: { flash: 'Usuário editorial salvo com sucesso.' },
        });
        return;
      }

      setInitialValue(toFormValue(response));
      setSubmitSuccess('Usuário editorial atualizado com sucesso.');
    } catch (error) {
      setSubmitError(error instanceof Error ? error.message : 'Não foi possível salvar o usuário editorial.');
    } finally {
      setIsSubmitting(false);
    }
  }

  async function handlePasswordReset(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    if (!id) {
      return;
    }

    setIsResettingPassword(true);
    setPasswordError(null);
    setPasswordSuccess(null);

    try {
      await resetAdminUserPassword(Number(id), {
        newPassword: passwordValue,
      });
      setPasswordValue('');
      setPasswordSuccess('Senha redefinida com sucesso.');
    } catch (error) {
      setPasswordError(error instanceof Error ? error.message : 'Não foi possível redefinir a senha.');
    } finally {
      setIsResettingPassword(false);
    }
  }

  const title = useMemo(() => (isEditMode ? 'Editar usuário editorial' : 'Criar usuário editorial'), [isEditMode]);

  return (
    <section className="editorial-stack">
      <div className="editorial-toolbar">
        <div>
          <span className="section-eyebrow">Usuários editoriais</span>
          <h2>{title}</h2>
        </div>
        <Link className="button button-secondary" to="/editor/usuarios">
          Voltar para lista
        </Link>
      </div>

      {isLoading ? <LoadingState message="Carregando dados do usuário..." /> : null}
      {loadError && !isLoading ? <ErrorState description={loadError} /> : null}

      {!isLoading && !loadError ? (
        <>
          <AdminUserForm
            includePasswordField={!isEditMode}
            initialValue={initialValue}
            isSubmitting={isSubmitting}
            onSubmit={handleSubmit}
            submitError={submitError}
            submitSuccess={submitSuccess}
          />

          {isEditMode ? (
            <form className="glass-card editorial-password-card" onSubmit={handlePasswordReset}>
              <EditorialFormSection
                compact
                description="Use esta ação para definir uma nova senha sem precisar editar os demais dados da conta."
                title="Redefinir senha"
              >
                <div className="editorial-form-grid editorial-form-grid-single">
                  <div className="form-field">
                    <label htmlFor="reset-password">Nova senha</label>
                    <input
                      id="reset-password"
                      minLength={6}
                      onChange={(event) => setPasswordValue(event.target.value)}
                      required
                      type="password"
                      value={passwordValue}
                    />
                  </div>
                </div>
              </EditorialFormSection>

              {passwordError ? <p className="form-error">{passwordError}</p> : null}
              {passwordSuccess ? <p className="form-success">{passwordSuccess}</p> : null}

              <div className="editorial-form-actions">
                <button className="button button-primary" disabled={isResettingPassword} type="submit">
                  {isResettingPassword ? 'Redefinindo...' : 'Redefinir senha'}
                </button>
              </div>
            </form>
          ) : null}
        </>
      ) : null}
    </section>
  );
}
