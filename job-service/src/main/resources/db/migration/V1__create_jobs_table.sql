CREATE TABLE IF NOT EXISTS jobs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    company VARCHAR(150) NOT NULL,
    location VARCHAR(100) NOT NULL,
    skills TEXT,
    salary_min DECIMAL(10, 2),
    salary_max DECIMAL(10, 2),
    recruiter_id BIGINT NOT NULL,
    recruiter_email VARCHAR(150) NOT NULL,
    job_status VARCHAR(20) DEFAULT NOT NULL 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);