INSERT INTO tb_posts (
    title,
    slug,
    summary,
    body,
    cover_image_url,
    gallery_image_urls,
    video_url,
    caption,
    category,
    featured,
    status,
    published_at,
    created_at,
    updated_at
) VALUES
(
    'Treino forte sem baguncar a alimentacao da semana',
    'treino-forte-sem-baguncar-a-alimentacao',
    'Como encaixar treino intenso sem abrir espaco para improviso alimentar no resto do dia.',
    'Treinar forte e manter a estrategia alimentar exige previsao. O post mostra como distribuir refeicoes, organizar lanches e reduzir o efeito sanfona da fome pos treino.',
    '/images/blog-1.jpg',
    '/images/result-1.jpg
/images/result-2.jpg',
    'https://www.youtube.com/watch?v=9No-FiEInLA',
    'Bastidores de rotina real com orientacao pratica.',
    'Treino',
    TRUE,
    'PUBLISHED',
    TIMESTAMP '2026-03-25 09:00:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'Rotina da Nutri - Dando exemplo',
    'rotina-da-nutri-dando-exemplo',
    'Bastidores de treino e atividade fisica para inspirar constancia fora do consultorio.',
    'A nutricionista tambem sustenta a propria rotina com movimento, planejamento e consistencia. Este post mostra um recorte simples de treino, organizacao e vida real para inspirar quem acompanha o processo.',
    '/images/routine-1.jpg',
    '/images/routine-2.jpg
/images/routine-3.jpg
/images/routine-4.jpg',
    'https://www.youtube.com/watch?v=9No-FiEInLA',
    'Treino, postura e exemplo para a rotina editorial.',
    'Treino',
    TRUE,
    'PUBLISHED',
    TIMESTAMP '2026-04-01 07:00:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'Dicas rapidas para montar marmitas com mais adesao',
    'dicas-rapidas-para-marmitas-com-mais-adesao',
    'Pequenas decisoes que deixam a marmita mais pratica e mais facil de repetir.',
    'A montagem da marmita precisa combinar saciedade, textura, praticidade e tempo real de preparo. Quando isso acontece, a adesao melhora muito.',
    '/images/blog-2.jpg',
    NULL,
    NULL,
    'Organizacao simples para dias corridos.',
    'Rotina',
    FALSE,
    'PUBLISHED',
    TIMESTAMP '2026-03-21 08:30:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'Reflexao da semana: consistencia vale mais que perfeicao',
    'reflexao-da-semana-consistencia-vale-mais-que-perfeicao',
    'Ajustar expectativa e manter repeticao inteligente traz mais resultado que entrar em ciclos radicais.',
    'A perfeicao trava. A consistencia constroi resultado. O objetivo deste post e lembrar que um plano alimentar bom precisa sobreviver a semanas comuns, nao apenas a semanas ideais.',
    '/images/blog-3.jpg',
    NULL,
    NULL,
    'Mensagem curta para quem esta retomando a rotina.',
    'Bem-estar',
    FALSE,
    'PUBLISHED',
    TIMESTAMP '2026-03-18 18:15:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'Bastidor de consultorio para pauta futura',
    'bastidor-de-consultorio-para-pauta-futura',
    'Rascunho interno para publicacao posterior.',
    'Conteudo ainda em desenvolvimento sobre rotina de atendimento e educacao alimentar.',
    NULL,
    NULL,
    NULL,
    NULL,
    'Bastidores',
    FALSE,
    'DRAFT',
    NULL,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO tb_articles (
    title,
    slug,
    summary,
    body,
    cover_image_url,
    tags,
    category,
    read_time_minutes,
    featured,
    status,
    published_at,
    created_at,
    updated_at
) VALUES
(
    'Por que restringir demais a dieta costuma falhar na pratica',
    'por-que-restringir-demais-a-dieta-costuma-falhar',
    'Um artigo sobre adesao, fome, comportamento e sustentabilidade alimentar.',
    'Dietas agressivas prometem rapidez, mas frequentemente quebram a adesao. Neste artigo, a nutricionista explica como fome, contexto social, treino e rotina profissional interferem diretamente no resultado. O foco sai do extremismo e vai para estrategia sustentavel.',
    '/images/blog-1.jpg',
    'adesao
comportamento
emagrecimento',
    'Dieta',
    5,
    TRUE,
    'PUBLISHED',
    TIMESTAMP '2026-03-15 10:00:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'Como organizar a semana alimentar quando o trabalho aperta',
    'como-organizar-a-semana-alimentar-quando-o-trabalho-aperta',
    'Planejamento simples para quem precisa de previsibilidade sem burocracia.',
    'Quando a agenda aperta, comer bem exige menos perfeicao e mais pontos de apoio. O artigo detalha uma estrutura semanal com refeicoes base, lanches de contingencia e compras inteligentes para reduzir atrito.',
    '/images/blog-2.jpg',
    'rotina
planejamento
consistencia',
    'Rotina',
    4,
    FALSE,
    'PUBLISHED',
    TIMESTAMP '2026-03-12 07:45:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'Estrutura de artigo em rascunho sobre suplementacao',
    'estrutura-de-artigo-em-rascunho-sobre-suplementacao',
    'Conteudo interno ainda nao publicado.',
    'Rascunho para evolucao futura com referencias, imagem e revisao editorial.',
    NULL,
    'suplementacao
performance',
    'Performance',
    3,
    FALSE,
    'DRAFT',
    NULL,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO tb_recipes (
    title,
    slug,
    description,
    image_url,
    ingredients,
    preparation_steps,
    prep_time_minutes,
    yield_info,
    category,
    featured,
    status,
    published_at,
    created_at,
    updated_at
) VALUES
(
    'Bowl mediterraneo proteico',
    'bowl-mediterraneo-proteico',
    'Uma refeicao pratica para almoco ou jantar com fibra, proteina e saciedade.',
    '/images/recipe-1.jpg',
    '120 g de frango grelhado
1 xicara de arroz integral
Pepino em cubos
Tomate cereja
Grao de bico assado
Molho de iogurte com limao',
    'Grelhe o frango e reserve.
Cozinhe o arroz integral.
Monte a base com arroz, vegetais e grao de bico.
Finalize com o frango e o molho de iogurte.',
    20,
    '2 porcoes',
    'Almoco',
    TRUE,
    'PUBLISHED',
    TIMESTAMP '2026-03-24 12:00:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'Smoothie proteico de frutas vermelhas',
    'smoothie-proteico-de-frutas-vermelhas',
    'Boa opcao para cafe da manha corrido ou pos treino leve.',
    '/images/recipe-2.jpg',
    '200 ml de leite ou bebida vegetal
1 scoop de whey
1 banana congelada
1/2 xicara de frutas vermelhas
1 colher de aveia',
    'Bata todos os ingredientes no liquidificador.
Ajuste a textura com gelo ou mais liquido.
Sirva logo em seguida.',
    10,
    '1 porcao',
    'Cafe da manha',
    FALSE,
    'PUBLISHED',
    TIMESTAMP '2026-03-19 08:00:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'Salmao ao pesto com legumes',
    'salmao-ao-pesto-com-legumes',
    'Refeicao rica em proteina e gorduras boas para dias de foco em recuperacao.',
    '/images/recipe-3.jpg',
    '150 g de salmao
Legumes assados
1 colher de pesto
Batatas pequenas cozidas
Sal e pimenta a gosto',
    'Asse os legumes e cozinhe as batatas.
Grelhe o salmao ate o ponto desejado.
Sirva com pesto por cima e acompanhe com os legumes.',
    30,
    '2 porcoes',
    'Jantar',
    FALSE,
    'PUBLISHED',
    TIMESTAMP '2026-03-14 19:30:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'Salada refrescante com molho citrico',
    'salada-refrescante-com-molho-citrico',
    'Opcao leve para complementar a rotina sem perder volume e sabor.',
    '/images/recipe-4.jpg',
    'Folhas variadas
Manga em cubos
Pepino fatiado
Castanhas
Molho de limao com mostarda',
    'Higienize as folhas e organize na tigela.
Adicione manga, pepino e castanhas.
Misture o molho e finalize na hora de servir.',
    15,
    '2 porcoes',
    'Acompanhamento',
    FALSE,
    'PUBLISHED',
    TIMESTAMP '2026-03-10 11:20:00',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'Receita em rascunho para publicacao futura',
    'receita-em-rascunho-para-publicacao-futura',
    'Receita interna ainda nao publicada.',
    NULL,
    'Ingrediente teste 1
Ingrediente teste 2',
    'Passo 1.
Passo 2.',
    12,
    '1 porcao',
    'Rascunho',
    FALSE,
    'DRAFT',
    NULL,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
