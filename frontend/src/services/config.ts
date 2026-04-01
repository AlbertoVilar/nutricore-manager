const defaultApiBaseUrl = 'http://localhost:8080/api';

export const API_BASE_URL = (import.meta.env.VITE_API_BASE_URL ?? defaultApiBaseUrl).replace(/\/$/, '');
