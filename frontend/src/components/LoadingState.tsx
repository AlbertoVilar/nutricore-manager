interface LoadingStateProps {
  message?: string;
}

export function LoadingState({ message = 'Carregando conteudo...' }: LoadingStateProps) {
  return (
    <div className="state-card">
      <div className="loading-dot-row" aria-hidden="true">
        <span />
        <span />
        <span />
      </div>
      <p>{message}</p>
    </div>
  );
}
