interface LoadingStateProps {
  message?: string;
}

export function LoadingState({ message = 'Carregando conteúdo...' }: LoadingStateProps) {
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
