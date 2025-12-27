CREATE TABLE tb_nutrition_goals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,

    goal_type VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL,

    target_weight NUMERIC(10,2),
    target_body_fat_percentage NUMERIC(10,2),
    target_lean_mass NUMERIC(10,2),

    start_date DATE NOT NULL,
    expected_end_date DATE,

    notes TEXT,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_nutrition_goal_patient
        FOREIGN KEY (patient_id)
        REFERENCES tb_patients(id)
);
