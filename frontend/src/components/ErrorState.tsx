interface ErrorStateProps {
  title?: string;
  description: string;
  onRetry?: () => void;
}

export function ErrorState({
  title = 'Nao foi possivel carregar esta secao.',
  description,
  onRetry,
}: ErrorStateProps) {
  return (
    <div className="state-card error">
      <h3>{title}</h3>
      <p>{description}</p>
      {onRetry ? (
        <button className="button button-primary" onClick={onRetry} type="button">
          Tentar novamente
        </button>
      ) : null}
    </div>
  );
}
