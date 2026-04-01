import { useState } from 'react';
import { buildWhatsAppLink } from '../utils/whatsapp';

interface ContactCtaFormProps {
  contactPhone: string;
  fallbackUrl: string;
}

export function ContactCtaForm({ contactPhone, fallbackUrl }: ContactCtaFormProps) {
  const [name, setName] = useState('');
  const [objective, setObjective] = useState('');
  const [routine, setRoutine] = useState('');

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const message = [
      'Ola, vim pelo site do NutriCore.',
      `Nome: ${name || 'Nao informado'}.`,
      `Objetivo: ${objective || 'Nao informado'}.`,
      `Rotina/treino: ${routine || 'Nao informado'}.`,
      'Quero agendar uma avaliacao.',
    ].join(' ');

    const targetUrl = contactPhone.trim() ? buildWhatsAppLink(contactPhone, message) : fallbackUrl;

    window.open(targetUrl, '_blank', 'noopener,noreferrer');
  }

  return (
    <form className="contact-form glass-card" onSubmit={handleSubmit}>
      <div className="form-field">
        <label htmlFor="name">Nome</label>
        <input
          id="name"
          name="name"
          onChange={(event) => setName(event.target.value)}
          placeholder="Como voce prefere ser chamado?"
          type="text"
          value={name}
        />
      </div>

      <div className="form-field">
        <label htmlFor="objective">Objetivo principal</label>
        <input
          id="objective"
          name="objective"
          onChange={(event) => setObjective(event.target.value)}
          placeholder="Emagrecimento, performance, rotina..."
          type="text"
          value={objective}
        />
      </div>

      <div className="form-field">
        <label htmlFor="routine">Rotina e treino</label>
        <textarea
          id="routine"
          name="routine"
          onChange={(event) => setRoutine(event.target.value)}
          placeholder="Conte um pouco da sua agenda, treinos e desafios atuais."
          rows={4}
          value={routine}
        />
      </div>

      <button className="button button-primary" type="submit">
        Enviar no WhatsApp
      </button>
    </form>
  );
}
