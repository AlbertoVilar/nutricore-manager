import { useEffect, useRef, useState } from 'react';
import type { EditorialRole } from '../../types/auth';
import type { AdminEditorialUserCreateInput, AdminEditorialUserInput } from '../../types/admin-site';
import { EditorialFormSection } from './EditorialFormSection';

interface AdminUserFormProps {
  initialValue: AdminEditorialUserCreateInput | AdminEditorialUserInput;
  includePasswordField: boolean;
  isSubmitting: boolean;
  submitError: string | null;
  submitSuccess: string | null;
  onSubmit: (payload: AdminEditorialUserCreateInput | AdminEditorialUserInput) => Promise<void>;
}

export function AdminUserForm({
  initialValue,
  includePasswordField,
  isSubmitting,
  submitError,
  submitSuccess,
  onSubmit,
}: AdminUserFormProps) {
  const [form, setForm] = useState(initialValue);
  const createForm = includePasswordField ? (form as AdminEditorialUserCreateInput) : null;
  const formRef = useRef<HTMLFormElement | null>(null);

  useEffect(() => {
    setForm(initialValue);
  }, [initialValue]);

  function updateField<Key extends keyof typeof form>(field: Key, value: (typeof form)[Key]) {
    setForm((current) => ({
      ...current,
      [field]: value,
    }));
  }

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    await onSubmit({
      ...form,
      fullName: form.fullName.trim(),
      email: form.email.trim(),
      ...(includePasswordField && 'password' in form ? { password: form.password.trim() } : {}),
    });
  }

  return (
    <form className="editorial-form-shell glass-card" onSubmit={handleSubmit} ref={formRef}>
      <div className="editorial-form-layout">
        <div className="editorial-form-main">
          <EditorialFormSection
            description="Crie ou atualize usuários editoriais com papel e status bem definidos."
            title="Dados do usuário"
          >
            <div className="editorial-form-grid">
              <div className="form-field">
                <label htmlFor="user-full-name">Nome completo</label>
                <input
                  id="user-full-name"
                  onChange={(event) => updateField('fullName', event.target.value)}
                  required
                  type="text"
                  value={form.fullName}
                />
              </div>

              <div className="form-field">
                <label htmlFor="user-email">E-mail</label>
                <input
                  id="user-email"
                  onChange={(event) => updateField('email', event.target.value)}
                  required
                  type="email"
                  value={form.email}
                />
              </div>

              {includePasswordField && 'password' in form ? (
                <div className="form-field">
                  <label htmlFor="user-password">Senha inicial</label>
                  <input
                    id="user-password"
                    minLength={6}
                    onChange={(event) => setForm((current) => ({ ...current, password: event.target.value }))}
                    required
                    type="password"
                    value={createForm?.password ?? ''}
                  />
                </div>
              ) : null}
            </div>
          </EditorialFormSection>
        </div>

        <aside className="editorial-form-aside">
          <EditorialFormSection
            compact
            description="Defina o papel e se o acesso fica ativo para login."
            title="Permissões"
          >
            <div className="editorial-form-grid editorial-form-grid-single">
              <div className="form-field">
                <label htmlFor="user-role">Papel</label>
                <select
                  id="user-role"
                  onChange={(event) => updateField('role', event.target.value as EditorialRole)}
                  value={form.role}
                >
                  <option value="ADMIN">Admin</option>
                  <option value="EDITOR">Editor</option>
                </select>
              </div>

              <label className="editorial-checkbox">
                <input
                  checked={form.active}
                  onChange={(event) => updateField('active', event.target.checked)}
                  type="checkbox"
                />
                <span>Usuário ativo para login</span>
              </label>
            </div>
          </EditorialFormSection>
        </aside>
      </div>

      {submitError ? <p className="form-error">{submitError}</p> : null}
      {submitSuccess ? <p className="form-success">{submitSuccess}</p> : null}

      <div className="editorial-form-actions">
        <button
          className="button button-primary"
          disabled={isSubmitting}
          onClick={() => formRef.current?.requestSubmit()}
          type="button"
        >
          {isSubmitting ? 'Salvando...' : 'Salvar usuário'}
        </button>
      </div>
    </form>
  );
}
