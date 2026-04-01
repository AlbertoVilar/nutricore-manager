import { API_BASE_URL } from './config';
import { getStoredEditorialSession } from './editorialSessionService';

export class ApiError extends Error {
  public readonly status: number;

  constructor(message: string, status: number) {
    super(message);
    this.name = 'ApiError';
    this.status = status;
  }
}

interface RequestOptions extends RequestInit {
  auth?: boolean;
  skipAuthFailureHandling?: boolean;
}

let authFailureHandler: ((error: ApiError) => void) | null = null;

export function registerAuthFailureHandler(handler: ((error: ApiError) => void) | null) {
  authFailureHandler = handler;
}

async function parseErrorMessage(response: Response): Promise<string> {
  try {
    const contentType = response.headers.get('content-type') ?? '';

    if (contentType.includes('application/json')) {
      const payload = (await response.json()) as { message?: string };

      if (payload.message) {
        return payload.message;
      }
    }
  } catch {
    return `Erro HTTP ${response.status}`;
  }

  return `Erro HTTP ${response.status}`;
}

async function request(path: string, init?: RequestOptions): Promise<Response> {
  const session = init?.auth ? getStoredEditorialSession() : null;

  return fetch(`${API_BASE_URL}${path}`, {
    ...init,
    headers: {
      Accept: 'application/json',
      ...(session?.accessToken
        ? {
            Authorization: `Bearer ${session.accessToken}`,
          }
        : {}),
      ...(init?.headers ?? {}),
    },
  });
}

async function parseJsonOrNull<T>(response: Response): Promise<T> {
  if (response.status === 204) {
    return null as T;
  }

  return (await response.json()) as T;
}

async function handleResponse<T>(response: Response, init?: RequestOptions): Promise<T> {
  if (!response.ok) {
    const error = new ApiError(await parseErrorMessage(response), response.status);

    if (response.status === 401 && !init?.skipAuthFailureHandling) {
      authFailureHandler?.(error);
    }

    throw error;
  }

  return parseJsonOrNull<T>(response);
}

export async function getJson<T>(path: string, init?: RequestOptions): Promise<T> {
  return handleResponse<T>(await request(path, init), init);
}

export async function postJson<TResponse, TRequest>(
  path: string,
  body: TRequest,
  init?: RequestOptions,
): Promise<TResponse> {
  return handleResponse<TResponse>(
    await request(path, {
      method: 'POST',
      body: JSON.stringify(body),
      ...init,
      headers: {
        'Content-Type': 'application/json',
        ...(init?.headers ?? {}),
      },
    }),
    init,
  );
}

export async function putJson<TResponse, TRequest>(
  path: string,
  body: TRequest,
  init?: RequestOptions,
): Promise<TResponse> {
  return handleResponse<TResponse>(
    await request(path, {
      method: 'PUT',
      body: JSON.stringify(body),
      ...init,
      headers: {
        'Content-Type': 'application/json',
        ...(init?.headers ?? {}),
      },
    }),
    init,
  );
}

export async function patchJson<TResponse>(path: string, init?: RequestOptions): Promise<TResponse> {
  return handleResponse<TResponse>(
    await request(path, {
      method: 'PATCH',
      ...init,
    }),
    init,
  );
}

export async function deleteRequest(path: string, init?: RequestOptions): Promise<void> {
  await handleResponse<void>(
    await request(path, {
      method: 'DELETE',
      ...init,
    }),
    init,
  );
}

export async function uploadFile<TResponse>(
  path: string,
  file: File,
  fieldName = 'file',
  init?: RequestOptions,
): Promise<TResponse> {
  const formData = new FormData();
  formData.append(fieldName, file);

  return handleResponse<TResponse>(
    await request(path, {
      method: 'POST',
      body: formData,
      ...init,
    }),
    init,
  );
}
