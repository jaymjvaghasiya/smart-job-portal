-- Drop foreign key
ALTER TABLE user_skills
DROP FOREIGN KEY user_skills_ibfk_1;

-- Modify parent table column
ALTER TABLE users
MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;

-- Modify child table column
ALTER TABLE user_skills
MODIFY COLUMN user_id BIGINT NOT NULL;

-- Add foreign key again
ALTER TABLE user_skills
ADD CONSTRAINT user_skills_ibfk_1
FOREIGN KEY (user_id) REFERENCES users(id);