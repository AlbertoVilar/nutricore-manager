import { API_BASE_URL } from './config';

export class ApiError extends Error {
  public readonly status: number;

  constructor(
    message: string,
    status: number,
  ) {
    super(message);
    this.name = 'ApiError';
    this.status = status;
  }
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

async function request(path: string, init?: RequestInit): Promise<Response> {
  return fetch(`${API_BASE_URL}${path}`, {
    ...init,
    headers: {
      Accept: 'application/json',
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

async function handleResponse<T>(response: Response): Promise<T> {
  if (!response.ok) {
    throw new ApiError(await parseErrorMessage(response), response.status);
  }

  return parseJsonOrNull<T>(response);
}

export async function getJson<T>(path: string, init?: RequestInit): Promise<T> {
  return handleResponse<T>(await request(path, init));
}

export async function postJson<TResponse, TRequest>(
  path: string,
  body: TRequest,
  init?: RequestInit,
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
  );
}

export async function putJson<TResponse, TRequest>(
  path: string,
  body: TRequest,
  init?: RequestInit,
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
  );
}

export async function patchJson<TResponse>(
  path: string,
  init?: RequestInit,
): Promise<TResponse> {
  return handleResponse<TResponse>(
    await request(path, {
      method: 'PATCH',
      ...init,
    }),
  );
}

export async function deleteRequest(path: string, init?: RequestInit): Promise<void> {
  await handleResponse<void>(
    await request(path, {
      method: 'DELETE',
      ...init,
    }),
  );
}

export async function uploadFile<TResponse>(
  path: string,
  file: File,
  fieldName = 'file',
  init?: RequestInit,
): Promise<TResponse> {
  const formData = new FormData();
  formData.append(fieldName, file);

  return handleResponse<TResponse>(
    await request(path, {
      method: 'POST',
      body: formData,
      ...init,
    }),
  );
}
