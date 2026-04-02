const defaultApiBaseUrl = 'http://localhost:8080/api';
const isVercelPreview =
  typeof window !== 'undefined' && window.location.hostname.endsWith('.vercel.app');

export const API_BASE_URL = (
  isVercelPreview ? '/api' : import.meta.env.VITE_API_BASE_URL ?? defaultApiBaseUrl
).replace(/\/$/, '');
export const API_ORIGIN = API_BASE_URL.replace(/\/api$/, '');
