-- Inserindo histórico para Ana Oliveira (Foco: Evolução em 3 meses)
INSERT INTO tb_clinical_anamnesis
(patient_id, date, clinical_history, family_history, medication_use, sleep_quality, bowel_function, water_intake_goal, created_at)
VALUES
((SELECT id FROM tb_patients WHERE email = 'ana.oliveira@email.com'), '2025-10-10', 'Relata cansaço extremo e dores de cabeça frequentes.', 'Pai diabético', 'Nenhum', 'POOR', 'CONSTIPATED', 2.0, NOW()),
((SELECT id FROM tb_patients WHERE email = 'ana.oliveira@email.com'), '2025-11-15', 'Melhora nas dores após aumento da hidratação.', 'Pai diabético', 'Nenhum', 'FAIR', 'REGULAR', 2.5, NOW()),
((SELECT id FROM tb_patients WHERE email = 'ana.oliveira@email.com'), '2025-12-20', 'Paciente relata disposição e sono regulado.', 'Pai diabético', 'Nenhum', 'GOOD', 'REGULAR', 3.0, NOW());

-- Inserindo histórico para Bruno Santos (Foco: Doenças Crônicas)
INSERT INTO tb_clinical_anamnesis
(patient_id, date, clinical_history, family_history, medication_use, sleep_quality, bowel_function, water_intake_goal, created_at)
VALUES
((SELECT id FROM tb_patients WHERE email = 'bruno.santos@email.com'), '2025-09-01', 'Sedentarismo e dieta rica em ultraprocessados.', 'Hipertensão na família', 'Atenolol', 'FAIR', 'REGULAR', 1.5, NOW()),
((SELECT id FROM tb_patients WHERE email = 'bruno.santos@email.com'), '2025-11-20', 'Iniciou caminhadas 3x por semana. Redução de sódio.', 'Hipertensão na família', 'Atenolol', 'GOOD', 'REGULAR', 2.0, NOW());

-- Inserindo histórico para Carla Mendes (Foco: Performance)
INSERT INTO tb_clinical_anamnesis
(patient_id, date, clinical_history, family_history, medication_use, sleep_quality, bowel_function, water_intake_goal, created_at)
VALUES
((SELECT id FROM tb_patients WHERE email = 'carla.mendes@email.com'), '2025-12-01', 'Busca hipertrofia e redução de gordura corporal.', 'Nada relevante', 'Nenhum', 'EXCELLENT', 'REGULAR', 3.5, NOW());