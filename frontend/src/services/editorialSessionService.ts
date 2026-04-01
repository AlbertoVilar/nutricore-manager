const EDITORIAL_TOKEN_STORAGE_KEY = 'nutricore.editorial.token';

export function getStoredEditorialToken() {
  return window.localStorage.getItem(EDITORIAL_TOKEN_STORAGE_KEY) ?? '';
}

export function storeEditorialToken(token: string) {
  window.localStorage.setItem(EDITORIAL_TOKEN_STORAGE_KEY, token);
}

export function clearStoredEditorialToken() {
  window.localStorage.removeItem(EDITORIAL_TOKEN_STORAGE_KEY);
}
