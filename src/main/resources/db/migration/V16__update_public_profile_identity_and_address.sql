ALTER TABLE tb_public_profiles
ADD COLUMN office_address VARCHAR(255) NOT NULL DEFAULT 'Endereço do consultório não informado.';

UPDATE tb_public_profiles
SET full_name = 'Ana Paula Vilar Alves',
    professional_title = 'Nutricionista clínica e esportiva',
    professional_subtitle = 'Formada pela Universidade Federal de Campina Grande',
    biography_summary = 'Atendimento com foco em vida real, performance e consistência, unindo escuta clínica, estratégia prática e acompanhamento próximo.',
    about_title = 'Sobre a profissional',
    about_description = 'Nutricionista formada pela Universidade Federal de Campina Grande, com atendimento voltado para reeducação alimentar, performance esportiva e bem-estar. O trabalho combina escuta clínica, estratégia prática e acompanhamento próximo para construir resultados sustentáveis sem fórmulas rígidas.',
    city = 'Cuité, PB',
    office_address = 'Rua Samaritana Maria Amália de Castilho, Cuité, PB, 58175-000, Brasil';
