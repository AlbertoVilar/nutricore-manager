import type { ServicePillar, SiteMetric, Testimonial } from '../types/public-content';

export const siteMetrics: SiteMetric[] = [
  {
    title: 'Acompanhamento individual',
    value: '1:1',
    description: 'Plano prático, revisões estratégicas e orientação próxima para rotina real.',
  },
  {
    title: 'Método aplicável',
    value: '360',
    description: 'Visão integrada entre saúde, treino, adesão, comportamento e agenda.',
  },
  {
    title: 'Conteúdo útil',
    value: 'Sem atalhos',
    description: 'Educação nutricional clara para sustentar decisões melhores no dia a dia.',
  },
];

export const servicePillars: ServicePillar[] = [
  {
    title: 'Consulta com estratégia',
    description: 'Leitura do contexto, da rotina e do objetivo antes de montar qualquer plano alimentar.',
  },
  {
    title: 'Conduta sustentavel',
    description: 'Ajustes que respeitam adesão, energia, prazer em comer e consistência ao longo das semanas.',
  },
  {
    title: 'Acompanhamento inteligente',
    description: 'Revisões objetivas, feedback rápido e orientação para treino, refeições e recuperação.',
  },
];

export const testimonials: Testimonial[] = [
  {
    name: 'Marina S.',
    label: 'Rotina e emagrecimento',
    quote: 'Parecia impossível encaixar uma alimentação boa no meu trabalho. Com estratégia e ajuste fino, a rotina ficou leve e constante.',
    imageUrl: '/images/result-1.jpg',
  },
  {
    name: 'Felipe R.',
    label: 'Performance e composição corporal',
    quote: 'Passei a treinar com mais energia e parei de improvisar a dieta. O acompanhamento deixou o processo mensurável e consistente.',
    imageUrl: '/images/result-2.jpg',
  },
  {
    name: 'Amanda L.',
    label: 'Bem-estar e recuperação',
    quote: 'A orientação foi prática, sem rigidez inútil. Tive melhora de energia, fome mais estável e mais clareza nas escolhas.',
    imageUrl: '/images/result-3.jpg',
  },
];
