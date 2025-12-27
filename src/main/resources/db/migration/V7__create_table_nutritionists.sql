CREATE TABLE tb_nutritionists (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    crn VARCHAR(20) NOT NULL,
    specialty VARCHAR(50),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    
    CONSTRAINT uk_nutritionist_email UNIQUE (email),
    CONSTRAINT uk_nutritionist_crn UNIQUE (crn)
);
