import type { Testimonial } from '../types/public-content';

interface TestimonialCardProps {
  testimonial: Testimonial;
}

export function TestimonialCard({ testimonial }: TestimonialCardProps) {
  return (
    <article className="testimonial-card">
      <img alt={testimonial.name} src={testimonial.imageUrl} />
      <div className="testimonial-content">
        <span className="testimonial-label">{testimonial.label}</span>
        <p>{testimonial.quote}</p>
        <strong>{testimonial.name}</strong>
      </div>
    </article>
  );
}
