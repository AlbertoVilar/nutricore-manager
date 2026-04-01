import type { AuthSession } from '../types/auth';

const EDITORIAL_SESSION_STORAGE_KEY = 'nutricore.editorial.session';

function getStorage() {
  if (typeof window === 'undefined') {
    return null;
  }

  return window.sessionStorage;
}

export function getStoredEditorialSession(): AuthSession | null {
  const storage = getStorage();

  if (!storage) {
    return null;
  }

  const rawValue = storage.getItem(EDITORIAL_SESSION_STORAGE_KEY);

  if (!rawValue) {
    return null;
  }

  try {
    return JSON.parse(rawValue) as AuthSession;
  } catch {
    storage.removeItem(EDITORIAL_SESSION_STORAGE_KEY);
    return null;
  }
}

export function storeEditorialSession(session: AuthSession) {
  getStorage()?.setItem(EDITORIAL_SESSION_STORAGE_KEY, JSON.stringify(session));
}

export function clearStoredEditorialSession() {
  getStorage()?.removeItem(EDITORIAL_SESSION_STORAGE_KEY);
}

export function isEditorialSessionExpired(session: AuthSession) {
  return Number.isNaN(Date.parse(session.expiresAt)) || Date.parse(session.expiresAt) <= Date.now();
}
