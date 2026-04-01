export type EditorialRole = 'ADMIN' | 'EDITOR';

export interface AuthenticatedUser {
  id: number;
  fullName: string;
  email: string;
  role: EditorialRole;
}

export interface AuthSession {
  accessToken: string;
  tokenType: string;
  expiresAt: string;
  user: AuthenticatedUser;
}

export interface LoginCredentials {
  email: string;
  password: string;
}
