export function formatPublishedDate(value: string) {
  return new Intl.DateTimeFormat('pt-BR', {
    day: '2-digit',
    month: 'short',
    year: 'numeric',
  }).format(new Date(`${value}T00:00:00`));
}

export function sanitizeInstagramHandle(handle: string) {
  return handle.startsWith('@') ? handle.slice(1) : handle;
}
