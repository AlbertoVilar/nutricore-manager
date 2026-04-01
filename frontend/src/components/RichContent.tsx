interface RichContentProps {
  body: string;
}

export function RichContent({ body }: RichContentProps) {
  return (
    <div className="rich-content">
      {body
        .split(/\n{2,}/)
        .map((paragraph) => paragraph.trim())
        .filter(Boolean)
        .map((paragraph) => (
          <p key={paragraph.slice(0, 48)}>{paragraph}</p>
        ))}
    </div>
  );
}
