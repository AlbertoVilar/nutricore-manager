ALTER TABLE tb_public_profiles ADD COLUMN professional_subtitle VARCHAR(160) NOT NULL DEFAULT 'Nutrição clínica e rotina real';
ALTER TABLE tb_public_profiles ADD COLUMN biography_summary VARCHAR(500) NOT NULL DEFAULT 'Atendimento com estratégia prática, acolhimento e consistência para transformar orientação em rotina possível.';
ALTER TABLE tb_public_profiles ADD COLUMN about_image_url VARCHAR(255) NOT NULL DEFAULT '/images/hero-healthy.jpg';
ALTER TABLE tb_public_profiles ADD COLUMN whatsapp_number VARCHAR(20) NOT NULL DEFAULT '+5511999999999';
ALTER TABLE tb_public_profiles ADD COLUMN linkedin_url VARCHAR(255);
ALTER TABLE tb_public_profiles ADD COLUMN youtube_url VARCHAR(255);
ALTER TABLE tb_public_profiles ADD COLUMN hero_card_title VARCHAR(120) NOT NULL DEFAULT 'Nutrição com estratégia e vida real';
ALTER TABLE tb_public_profiles ADD COLUMN hero_card_description VARCHAR(500) NOT NULL DEFAULT 'Consulta, conteúdo e acompanhamento organizados para transformar orientação em rotina possível.';
ALTER TABLE tb_public_profiles ADD COLUMN footer_description VARCHAR(255) NOT NULL DEFAULT 'Acompanhamento nutricional com linguagem clara, conteúdo útil e uma jornada pública organizada para quem quer mudar a rotina com estratégia.';
ALTER TABLE tb_public_profiles ADD COLUMN how_it_works_title VARCHAR(180) NOT NULL DEFAULT 'Uma jornada clara para orientar, acompanhar e sustentar resultado.';
ALTER TABLE tb_public_profiles ADD COLUMN how_it_works_description TEXT NOT NULL DEFAULT 'Cada etapa do atendimento nasce para funcionar na vida real: consulta com estratégia, conduta viável e acompanhamento que conversa com a rotina.';
ALTER TABLE tb_public_profiles ADD COLUMN approach_title VARCHAR(180) NOT NULL DEFAULT 'Atendimento pensado para caber na agenda, no treino e nas refeições do dia a dia.';
ALTER TABLE tb_public_profiles ADD COLUMN approach_description TEXT NOT NULL DEFAULT 'A consulta vai além do plano alimentar. O foco é combinar contexto, treino, aderência e acompanhamento em uma mesma linguagem.';
ALTER TABLE tb_public_profiles ADD COLUMN plans_title VARCHAR(180) NOT NULL DEFAULT 'Formas de acompanhamento para diferentes momentos da jornada.';
ALTER TABLE tb_public_profiles ADD COLUMN plans_description TEXT NOT NULL DEFAULT 'Os planos organizam o primeiro passo comercial do site com ofertas claras e encaminhamento objetivo para conversa.';
ALTER TABLE tb_public_profiles ADD COLUMN contact_title VARCHAR(180) NOT NULL DEFAULT 'Contato direto para avaliação, planos e orientações iniciais.';
ALTER TABLE tb_public_profiles ADD COLUMN contact_description TEXT NOT NULL DEFAULT 'Os canais abaixo facilitam o primeiro contato. O formulário ao lado organiza uma mensagem inicial para continuar no WhatsApp.';
ALTER TABLE tb_public_profiles ADD COLUMN final_cta_title VARCHAR(180) NOT NULL DEFAULT 'Pronta para organizar alimentação, treino e rotina com mais clareza?';
ALTER TABLE tb_public_profiles ADD COLUMN final_cta_description TEXT NOT NULL DEFAULT 'O próximo passo é conversar sobre objetivo, momento atual e o tipo de acompanhamento que faz sentido para sua rotina.';
ALTER TABLE tb_public_profiles ADD COLUMN site_metrics_json TEXT NOT NULL DEFAULT '[]';
ALTER TABLE tb_public_profiles ADD COLUMN service_pillars_json TEXT NOT NULL DEFAULT '[]';
ALTER TABLE tb_public_profiles ADD COLUMN testimonials_json TEXT NOT NULL DEFAULT '[]';

ALTER TABLE tb_public_plans ADD COLUMN cta_url VARCHAR(255) NOT NULL DEFAULT '/contato';
ALTER TABLE tb_public_plans ADD COLUMN active BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE tb_public_plans ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE tb_public_plans ADD COLUMN updated_at TIMESTAMP;

UPDATE tb_public_profiles
SET professional_subtitle = 'Nutrição clínica e rotina real',
    biography_summary = 'Atendimento com foco em vida real, performance e consistência, unindo escuta clínica, estratégia prática e acompanhamento próximo.',
    about_image_url = hero_image_url,
    whatsapp_number = '+5511999999999',
    hero_card_title = 'Nutrição com estratégia e vida real',
    hero_card_description = 'Consulta, conteúdo e acompanhamento organizados para transformar orientação em rotina possível.',
    footer_description = 'Acompanhamento nutricional com linguagem clara, conteúdo útil e uma jornada pública organizada para quem quer mudar a rotina com estratégia.',
    how_it_works_title = 'Uma jornada clara para orientar, acompanhar e sustentar resultado.',
    how_it_works_description = 'Cada etapa do atendimento nasce para funcionar na vida real: consulta com estratégia, conduta viável e acompanhamento que conversa com a rotina.',
    approach_title = 'Atendimento pensado para caber na agenda, no treino e nas refeições do dia a dia.',
    approach_description = 'A consulta vai além do plano alimentar. O foco é combinar contexto, treino, aderência e acompanhamento em uma mesma linguagem.',
    plans_title = 'Formas de acompanhamento para diferentes momentos da jornada.',
    plans_description = 'Os planos organizam o primeiro passo comercial do site com ofertas claras e encaminhamento objetivo para conversa.',
    contact_title = 'Contato direto para avaliação, planos e orientações iniciais.',
    contact_description = 'Os canais abaixo facilitam o primeiro contato. O formulário ao lado organiza uma mensagem inicial para continuar no WhatsApp.',
    final_cta_title = 'Pronta para organizar alimentação, treino e rotina com mais clareza?',
    final_cta_description = 'O próximo passo é conversar sobre objetivo, momento atual e o tipo de acompanhamento que faz sentido para sua rotina.',
    site_metrics_json = '[{"title":"Acompanhamento individual","value":"1:1","description":"Plano prático, revisões estratégicas e orientação próxima para rotina real."},{"title":"Método aplicável","value":"360","description":"Visão integrada entre saúde, treino, adesão, comportamento e agenda."},{"title":"Conteúdo útil","value":"Sem atalhos","description":"Educação nutricional clara para sustentar decisões melhores no dia a dia."}]',
    service_pillars_json = '[{"title":"Consulta com estratégia","description":"Leitura do contexto, da rotina e do objetivo antes de montar qualquer plano alimentar."},{"title":"Conduta sustentável","description":"Ajustes que respeitam adesão, energia, prazer em comer e consistência ao longo das semanas."},{"title":"Acompanhamento inteligente","description":"Revisões objetivas, feedback rápido e orientação para treino, refeições e recuperação."}]',
    testimonials_json = '[{"name":"Marina S.","label":"Rotina e emagrecimento","quote":"Parecia impossível encaixar uma alimentação boa no meu trabalho. Com estratégia e ajuste fino, a rotina ficou leve e constante.","imageUrl":"/images/result-1.jpg"},{"name":"Felipe R.","label":"Performance e composição corporal","quote":"Passei a treinar com mais energia e parei de improvisar a dieta. O acompanhamento deixou o processo mensurável e consistente.","imageUrl":"/images/result-2.jpg"},{"name":"Amanda L.","label":"Bem-estar e recuperação","quote":"A orientação foi prática, sem rigidez inútil. Tive melhora de energia, fome mais estável e mais clareza nas escolhas.","imageUrl":"/images/result-3.jpg"}]';

UPDATE tb_public_plans
SET cta_url = '/contato',
    active = TRUE,
    created_at = CURRENT_TIMESTAMP
WHERE created_at IS NULL;
