import { Navigate, Outlet } from 'react-router-dom';
import { useEditorialSession } from '../hooks/useEditorialSession';

export function AdminRoleGuard() {
  const { user } = useEditorialSession();

  if (!user || user.role !== 'ADMIN') {
    return <Navigate replace to="/editor" />;
  }

  return <Outlet />;
}
