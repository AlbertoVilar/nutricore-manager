interface TextListFieldProps {
  label: string;
  hint: string;
  value: string[];
  onChange: (nextValue: string[]) => void;
  placeholder: string;
  className?: string;
}

export function TextListField({ label, hint, value, onChange, placeholder, className }: TextListFieldProps) {
  return (
    <div className={`form-field${className ? ` ${className}` : ''}`}>
      <label>{label}</label>
      <textarea
        onChange={(event) => {
          onChange(
            event.target.value
              .split('\n')
              .map((item) => item.trim())
              .filter(Boolean),
          );
        }}
        placeholder={placeholder}
        rows={6}
        value={value.join('\n')}
      />
      <small className="form-hint">{hint}</small>
    </div>
  );
}
