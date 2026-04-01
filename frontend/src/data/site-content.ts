import type { ServicePillar, SiteMetric, Testimonial } from '../types/public-content';

export const siteMetrics: SiteMetric[] = [
  {
    title: 'Acompanhamento individual',
    value: '1:1',
    description: 'Plano pratico, revisoes estrategicas e orientacao proxima para rotina real.',
  },
  {
    title: 'Metodo aplicavel',
    value: '360',
    description: 'Visao integrada entre saude, treino, adesao, comportamento e agenda.',
  },
  {
    title: 'Conteudo util',
    value: 'Sem atalhos',
    description: 'Educacao nutricional clara para sustentar decisoes melhores no dia a dia.',
  },
];

export const servicePillars: ServicePillar[] = [
  {
    title: 'Consulta com estrategia',
    description: 'Leitura do contexto, da rotina e do objetivo antes de montar qualquer plano alimentar.',
  },
  {
    title: 'Conduta sustentavel',
    description: 'Ajustes que respeitam adesao, energia, prazer em comer e consistencia ao longo das semanas.',
  },
  {
    title: 'Acompanhamento inteligente',
    description: 'Revisoes objetivas, feedback rapido e orientacao para treino, refeicoes e recuperacao.',
  },
];

export const testimonials: Testimonial[] = [
  {
    name: 'Marina S.',
    label: 'Rotina e emagrecimento',
    quote: 'Parecia impossivel encaixar alimentacao boa no meu trabalho. Com estrategia e ajuste fino, a rotina ficou leve e constante.',
    imageUrl: '/images/result-1.jpg',
  },
  {
    name: 'Felipe R.',
    label: 'Performance e composicao corporal',
    quote: 'Passei a treinar com mais energia e parei de improvisar a dieta. O acompanhamento deixou o processo medivel e consistente.',
    imageUrl: '/images/result-2.jpg',
  },
  {
    name: 'Amanda L.',
    label: 'Bem-estar e recuperacao',
    quote: 'A orientacao foi pratica, sem rigidez inutil. Tive melhora de energia, fome mais estavel e mais clareza nas escolhas.',
    imageUrl: '/images/result-3.jpg',
  },
];
