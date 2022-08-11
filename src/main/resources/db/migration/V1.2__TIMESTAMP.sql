ALTER TABLE user_authority
    ADD created_at TIMESTAMP;

ALTER TABLE user_authority
    ALTER COLUMN created_at SET NOT NULL;

ALTER TABLE user_authentication
    ALTER COLUMN last_password_changed TIMESTAMP;