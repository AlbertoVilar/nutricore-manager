INSERT INTO tb_public_profiles (
    full_name,
    professional_title,
    hero_badge,
    hero_title,
    hero_description,
    about_title,
    about_description,
    primary_cta_label,
    primary_cta_url,
    secondary_cta_label,
    secondary_cta_url,
    contact_email,
    contact_phone,
    instagram_handle,
    city,
    hero_image_url,
    created_at,
    updated_at
) VALUES (
    'Dra. Carolina Barreto',
    'Nutricionista Clinica e Esportiva',
    'Acompanhamento nutricional para rotina real, treino e performance',
    'Nutricao baseada em ciencia para transformar saude, consistencia e resultados.',
    'Um plano alimentar bom precisa caber na vida real. O MVP publico do NutriCore apresenta a nutricionista, seus planos, conteudos e receitas com linguagem clara, acolhimento e orientacao profissional.',
    'Sobre a profissional',
    'Atendimento com foco em reeducacao alimentar, performance esportiva e bem-estar. O trabalho combina escuta clinica, estrategia pratica e acompanhamento proximo para construir resultados sustentaveis sem formulas rigidas.',
    'Agendar avaliacao',
    'https://wa.me/5511999999999?text=Ola%2C%20quero%20agendar%20uma%20avaliacao%20nutricional.',
    'Ver planos',
    '/planos',
    'contato@nutricore.com.br',
    '+55 11 99999-9999',
    '@nutricore',
    'Sao Paulo, SP',
    '/images/hero-healthy.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO tb_public_plans (
    title,
    subtitle,
    price_label,
    summary,
    features,
    featured,
    cta_label,
    display_order
) VALUES
(
    'Plano Essencial',
    'Base para reorganizar a alimentacao',
    'R$ 297',
    'Ideal para quem quer sair do improviso, ajustar a rotina e criar consistencia alimentar com acompanhamento profissional.',
    'Consulta inicial completa
Plano alimentar personalizado
Checklist de rotina e hidratacao
Suporte via WhatsApp em horario comercial',
    FALSE,
    'Quero começar',
    1
),
(
    'Plano Evolucao',
    'Acompanhamento para resultado mensuravel',
    'R$ 447',
    'Pensado para quem busca performance, evolucao corporal e revisoes frequentes com mais proximidade da nutricionista.',
    'Tudo do Plano Essencial
Retorno quinzenal
Ajuste de macros e estrategia de treino
Biblioteca de receitas e guias praticos',
    TRUE,
    'Escolher evolucao',
    2
),
(
    'Plano Performance',
    'Estrutura premium para atletas e rotina intensa',
    'R$ 697',
    'Pacote com acompanhamento intensivo para metas de performance, composicao corporal e periodizacao nutricional.',
    'Tudo do Plano Evolucao
Analise de exames e suplementacao
Planejamento para competicoes ou provas
Contato prioritario',
    FALSE,
    'Falar com a nutri',
    3
);

INSERT INTO tb_public_posts (
    title,
    slug,
    category,
    excerpt,
    read_time_minutes,
    image_url,
    published_at
) VALUES
(
    'Como organizar uma rotina alimentar mesmo com agenda corrida',
    'rotina-alimentar-agenda-corrida',
    'Rotina',
    'Tres ajustes simples para manter estrategia nutricional no trabalho, no treino e nos dias em que a agenda aperta.',
    6,
    '/images/blog-1.jpg',
    DATE '2026-03-10'
),
(
    'O que observar antes de cortar carboidrato no processo de emagrecimento',
    'cortar-carboidrato-emagrecimento',
    'Dieta',
    'Nem todo corte agressivo melhora adesao ou composicao corporal. Entenda quando ajustar e quando preservar energia.',
    5,
    '/images/blog-2.jpg',
    DATE '2026-03-17'
),
(
    'Treino, fome e recuperacao: por que o pos-treino interfere no resto do dia',
    'pos-treino-recuperacao-energia',
    'Treino',
    'Recuperar bem apos o treino reduz compulsao, melhora rendimento e ajuda a sustentar a estrategia nutricional da semana.',
    7,
    '/images/blog-3.jpg',
    DATE '2026-03-24'
);

INSERT INTO tb_public_recipes (
    title,
    slug,
    summary,
    image_url,
    prep_time_minutes,
    calories_label,
    ingredients,
    preparation_steps,
    display_order
) VALUES
(
    'Bowl mediterraneo proteico',
    'bowl-mediterraneo-proteico',
    'Uma refeicao pratica para almoco ou jantar com fibra, proteina e saciedade.',
    '/images/recipe-1.jpg',
    20,
    '520 kcal',
    '120 g de frango grelhado
1 xicara de arroz integral
Pepino em cubos
Tomate cereja
Grao-de-bico assado
Molho de iogurte com limao',
    'Grelhe o frango e reserve.
Cozinhe o arroz integral.
Monte a base com arroz, vegetais e grao-de-bico.
Finalize com o frango e o molho de iogurte.',
    1
),
(
    'Smoothie proteico de frutas vermelhas',
    'smoothie-proteico-frutas-vermelhas',
    'Boa opcao para cafe da manha corrido ou pos-treino leve.',
    '/images/recipe-2.jpg',
    10,
    '280 kcal',
    '200 ml de leite ou bebida vegetal
1 scoop de whey
1 banana congelada
1/2 xicara de frutas vermelhas
1 colher de aveia',
    'Bata todos os ingredientes no liquidificador.
Ajuste a textura com gelo ou mais liquido.
Sirva logo em seguida.',
    2
),
(
    'Salmao ao pesto com legumes',
    'salmao-pesto-legumes',
    'Refeicao rica em proteina e gorduras boas para dias de foco em recuperacao.',
    '/images/recipe-3.jpg',
    30,
    '610 kcal',
    '150 g de salmao
Legumes assados
1 colher de pesto
Batatas pequenas cozidas
Sal e pimenta a gosto',
    'Asse os legumes e cozinhe as batatas.
Grelhe o salmao ate o ponto desejado.
Sirva com pesto por cima e acompanhe com os legumes.',
    3
),
(
    'Salada refrescante com molho citrico',
    'salada-refrescante-molho-citrico',
    'Opcao leve para complementar a rotina sem perder volume e sabor.',
    '/images/recipe-4.jpg',
    15,
    '190 kcal',
    'Folhas variadas
Manga em cubos
Pepino fatiado
Castanhas
Molho de limao com mostarda',
    'Higienize as folhas e organize na tigela.
Adicione manga, pepino e castanhas.
Misture o molho e finalize na hora de servir.',
    4
);
