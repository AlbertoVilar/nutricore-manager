const defaultApiBaseUrl = 'http://localhost:8080/api';
const isVercelPreview =
  typeof window !== 'undefined' && window.location.hostname.endsWith('.vercel.app');
const configuredApiBaseUrl = import.meta.env.VITE_API_BASE_URL;

export const API_BASE_URL = (
  isVercelPreview && !configuredApiBaseUrl ? '/api' : configuredApiBaseUrl ?? defaultApiBaseUrl
).replace(/\/$/, '');
export const API_ORIGIN = API_BASE_URL.replace(/\/api$/, '');
