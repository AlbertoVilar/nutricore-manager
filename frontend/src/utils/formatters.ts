export function formatPublishedDate(value: string | null | undefined) {
  if (!value) {
    return 'Não publicado';
  }

  const normalizedValue = value.includes('T') ? value : `${value}T00:00:00`;

  return new Intl.DateTimeFormat('pt-BR', {
    day: '2-digit',
    month: 'short',
    year: 'numeric',
  }).format(new Date(normalizedValue));
}

export function formatDateTime(value: string | null | undefined) {
  if (!value) {
    return 'Não definido';
  }

  return new Intl.DateTimeFormat('pt-BR', {
    day: '2-digit',
    month: 'short',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  }).format(new Date(value));
}

export function toDateTimeLocalValue(value: string | null | undefined) {
  if (!value) {
    return '';
  }

  const date = new Date(value);
  const pad = (input: number) => String(input).padStart(2, '0');

  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T${pad(date.getHours())}:${pad(
    date.getMinutes(),
  )}`;
}

export function sanitizeInstagramHandle(handle: string) {
  return handle.startsWith('@') ? handle.slice(1) : handle;
}
