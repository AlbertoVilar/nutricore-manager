UPDATE tb_public_profiles
SET
    professional_title = 'Nutricionista Clínica e Esportiva',
    hero_title = 'Nutrição baseada em ciência para transformar saúde, consistência e resultados.',
    hero_description = 'Um plano alimentar bom precisa caber na vida real. O site público do NutriCore apresenta a nutricionista, seus planos, conteúdos e receitas com linguagem clara, acolhimento e orientação profissional.',
    about_description = 'Atendimento com foco em reeducação alimentar, performance esportiva e bem-estar. O trabalho combina escuta clínica, estratégia prática e acompanhamento próximo para construir resultados sustentáveis sem fórmulas rígidas.',
    primary_cta_label = 'Agendar avaliação',
    city = 'São Paulo, SP'
WHERE full_name = 'Dra. Carolina Barreto';

UPDATE tb_public_plans
SET
    features = 'Consulta inicial completa
Plano alimentar personalizado
Checklist de rotina e hidratação
Suporte via WhatsApp em horário comercial',
    cta_label = 'Quero começar'
WHERE title = 'Plano Essencial';

UPDATE tb_public_plans
SET
    title = 'Plano Evolução',
    summary = 'Pensado para quem busca performance, evolução corporal e revisões frequentes com mais proximidade da nutricionista.',
    features = 'Tudo do Plano Essencial
Retorno quinzenal
Ajuste de macros e estratégia de treino
Biblioteca de receitas e guias práticos',
    cta_label = 'Escolher evolução'
WHERE title = 'Plano Evolucao';

UPDATE tb_public_posts
SET excerpt = 'Três ajustes simples para manter a estratégia nutricional no trabalho, no treino e nos dias em que a agenda aperta.'
WHERE slug = 'rotina-alimentar-agenda-corrida';

UPDATE tb_public_posts
SET excerpt = 'Nem todo corte agressivo melhora adesão ou composição corporal. Entenda quando ajustar e quando preservar energia.'
WHERE slug = 'cortar-carboidrato-emagrecimento';

UPDATE tb_public_posts
SET
    title = 'Treino, fome e recuperação: por que o pós-treino interfere no resto do dia',
    excerpt = 'Recuperar bem após o treino reduz compulsão, melhora rendimento e ajuda a sustentar a estratégia nutricional da semana.'
WHERE slug = 'pos-treino-recuperacao-energia';

UPDATE tb_public_recipes
SET
    summary = 'Uma refeição prática para almoço ou jantar com fibra, proteína e saciedade.',
    ingredients = '120 g de frango grelhado
1 xícara de arroz integral
Pepino em cubos
Tomate cereja
Grão-de-bico assado
Molho de iogurte com limão',
    preparation_steps = 'Grelhe o frango e reserve.
Cozinhe o arroz integral.
Monte a base com arroz, vegetais e grão-de-bico.
Finalize com o frango e o molho de iogurte.'
WHERE slug = 'bowl-mediterraneo-proteico';

UPDATE tb_public_recipes
SET
    summary = 'Boa opção para café da manhã corrido ou pós-treino leve.',
    ingredients = '200 ml de leite ou bebida vegetal
1 scoop de whey
1 banana congelada
1/2 xícara de frutas vermelhas
1 colher de aveia',
    preparation_steps = 'Bata todos os ingredientes no liquidificador.
Ajuste a textura com gelo ou mais líquido.
Sirva logo em seguida.'
WHERE slug = 'smoothie-proteico-frutas-vermelhas';

UPDATE tb_public_recipes
SET
    title = 'Salmão ao pesto com legumes',
    summary = 'Refeição rica em proteína e gorduras boas para dias de foco em recuperação.',
    ingredients = '150 g de salmão
Legumes assados
1 colher de pesto
Batatas pequenas cozidas
Sal e pimenta a gosto',
    preparation_steps = 'Asse os legumes e cozinhe as batatas.
Grelhe o salmão até o ponto desejado.
Sirva com pesto por cima e acompanhe com os legumes.'
WHERE slug = 'salmao-pesto-legumes';

UPDATE tb_public_recipes
SET
    summary = 'Opção leve para complementar a rotina sem perder volume e sabor.',
    ingredients = 'Folhas variadas
Manga em cubos
Pepino fatiado
Castanhas
Molho de limão com mostarda',
    preparation_steps = 'Higienize as folhas e organize na tigela.
Adicione manga, pepino e castanhas.
Misture o molho e finalize na hora de servir.'
WHERE slug = 'salada-refrescante-molho-citrico';

UPDATE tb_posts
SET
    title = 'Treino forte sem bagunçar a alimentação da semana',
    summary = 'Como encaixar treino intenso sem abrir espaço para improviso alimentar no resto do dia.',
    body = 'Treinar forte e manter a estratégia alimentar exige previsão. O post mostra como distribuir refeições, organizar lanches e reduzir o efeito sanfona da fome pós-treino.',
    caption = 'Bastidores de rotina real com orientação prática.'
WHERE slug = 'treino-forte-sem-baguncar-a-alimentacao';

UPDATE tb_posts
SET
    summary = 'Bastidores de treino e atividade física para inspirar constância fora do consultório.',
    body = 'A nutricionista também sustenta a própria rotina com movimento, planejamento e consistência. Este post mostra um recorte simples de treino, organização e vida real para inspirar quem acompanha o processo.'
WHERE slug = 'rotina-da-nutri-dando-exemplo';

UPDATE tb_posts
SET
    title = 'Dicas rápidas para montar marmitas com mais adesão',
    summary = 'Pequenas decisões que deixam a marmita mais prática e mais fácil de repetir.',
    caption = 'Organização simples para dias corridos.'
WHERE slug = 'dicas-rapidas-para-marmitas-com-mais-adesao';

UPDATE tb_posts
SET
    title = 'Reflexão da semana: consistência vale mais que perfeição',
    body = 'A perfeição trava. A consistência constrói resultado. O objetivo deste post é lembrar que um plano alimentar bom precisa sobreviver a semanas comuns, não apenas a semanas ideais.'
WHERE slug = 'reflexao-da-semana-consistencia-vale-mais-que-perfeicao';

UPDATE tb_posts
SET
    summary = 'Rascunho interno para publicação posterior.',
    body = 'Conteúdo ainda em desenvolvimento sobre rotina de atendimento e educação alimentar.'
WHERE slug = 'bastidor-de-consultorio-para-pauta-futura';

UPDATE tb_articles
SET
    title = 'Por que restringir demais a dieta costuma falhar na prática',
    summary = 'Um artigo sobre adesão, fome, comportamento e sustentabilidade alimentar.',
    body = 'Dietas agressivas prometem rapidez, mas frequentemente quebram a adesão. Neste artigo, a nutricionista explica como fome, contexto social, treino e rotina profissional interferem diretamente no resultado. O foco sai do extremismo e vai para estratégia sustentável.'
WHERE slug = 'por-que-restringir-demais-a-dieta-costuma-falhar';

UPDATE tb_articles
SET
    body = 'Quando a agenda aperta, comer bem exige menos perfeição e mais pontos de apoio. O artigo detalha uma estrutura semanal com refeições-base, lanches de contingência e compras inteligentes para reduzir atrito.'
WHERE slug = 'como-organizar-a-semana-alimentar-quando-o-trabalho-aperta';

UPDATE tb_articles
SET
    summary = 'Conteúdo interno ainda não publicado.',
    body = 'Rascunho para evolução futura com referências, imagem e revisão editorial.'
WHERE slug = 'estrutura-de-artigo-em-rascunho-sobre-suplementacao';

UPDATE tb_recipes
SET
    title = 'Bowl mediterrâneo proteico',
    description = 'Uma refeição prática para almoço ou jantar com fibra, proteína e saciedade.',
    ingredients = '120 g de frango grelhado
1 xícara de arroz integral
Pepino em cubos
Tomate cereja
Grão de bico assado
Molho de iogurte com limão',
    preparation_steps = 'Grelhe o frango e reserve.
Cozinhe o arroz integral.
Monte a base com arroz, vegetais e grão de bico.
Finalize com o frango e o molho de iogurte.',
    yield_info = '2 porções',
    category = 'Almoço'
WHERE slug = 'bowl-mediterraneo-proteico';

UPDATE tb_recipes
SET
    description = 'Boa opção para café da manhã corrido ou pós-treino leve.',
    ingredients = '200 ml de leite ou bebida vegetal
1 scoop de whey
1 banana congelada
1/2 xícara de frutas vermelhas
1 colher de aveia',
    preparation_steps = 'Bata todos os ingredientes no liquidificador.
Ajuste a textura com gelo ou mais líquido.
Sirva logo em seguida.',
    yield_info = '1 porção',
    category = 'Café da manhã'
WHERE slug = 'smoothie-proteico-de-frutas-vermelhas';

UPDATE tb_recipes
SET
    title = 'Salmão ao pesto com legumes',
    description = 'Refeição rica em proteína e gorduras boas para dias de foco em recuperação.',
    ingredients = '150 g de salmão
Legumes assados
1 colher de pesto
Batatas pequenas cozidas
Sal e pimenta a gosto',
    preparation_steps = 'Asse os legumes e cozinhe as batatas.
Grelhe o salmão até o ponto desejado.
Sirva com pesto por cima e acompanhe com os legumes.',
    yield_info = '2 porções'
WHERE slug = 'salmao-ao-pesto-com-legumes';

UPDATE tb_recipes
SET
    description = 'Opção leve para complementar a rotina sem perder volume e sabor.',
    ingredients = 'Folhas variadas
Manga em cubos
Pepino fatiado
Castanhas
Molho de limão com mostarda',
    preparation_steps = 'Higienize as folhas e organize na tigela.
Adicione manga, pepino e castanhas.
Misture o molho e finalize na hora de servir.',
    yield_info = '2 porções'
WHERE slug = 'salada-refrescante-com-molho-citrico';

UPDATE tb_recipes
SET
    title = 'Receita em rascunho para publicação futura',
    description = 'Receita interna ainda não publicada.',
    yield_info = '1 porção'
WHERE slug = 'receita-em-rascunho-para-publicacao-futura';
