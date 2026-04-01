import type { AuthSession, AuthenticatedUser, LoginCredentials } from '../types/auth';
import { getJson, postJson } from './httpClient';

export function loginEditorialUser(credentials: LoginCredentials) {
  return postJson<AuthSession, LoginCredentials>('/v1/auth/login', credentials, {
    skipAuthFailureHandling: true,
  });
}

export function getAuthenticatedEditorialUser() {
  return getJson<AuthenticatedUser>('/v1/auth/me', {
    auth: true,
  });
}
