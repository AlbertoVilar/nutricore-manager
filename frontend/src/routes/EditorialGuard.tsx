import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { LoadingState } from '../components/LoadingState';
import { useEditorialSession } from '../hooks/useEditorialSession';

export function EditorialGuard() {
  const location = useLocation();
  const { isAuthenticated, isReady } = useEditorialSession();

  if (!isReady) {
    return <LoadingState message="Validando sessao editorial..." />;
  }

  if (!isAuthenticated) {
    return <Navigate replace state={{ from: location }} to="/editor/acesso" />;
  }

  return <Outlet />;
}
