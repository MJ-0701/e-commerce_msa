-- UUID 확장을 사용하여 전역 고유 실별자 생성 기능 활성화 --
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- CREATE OR REPLACE FUNCTION generate_user_id_trigger()
-- RETURNS TRIGGER AS $$
-- BEGIN
--   IF NEW.user_id IS NULL OR NEW.user_id = '' THEN
--     NEW.user_id := CONCAT(
--       'UID',
--       to_char(now(), 'YYYYMMDD'),
--       upper(replace(uuid_generate_v4()::text, '-', ''))
--     );
-- END IF;
-- RETURN NEW;
-- END;
-- $$ LANGUAGE plpgsql;
--
-- CREATE TRIGGER set_user_id
--     BEFORE INSERT ON users
--     FOR EACH ROW
--     EXECUTE PROCEDURE generate_user_id_trigger();

-- updated_at 컬럼 자동 갱신을 위한 트리거 함수 생성
-- CREATE OR REPLACE FUNCTION update_updated_at_column()
-- RETURNS TRIGGER AS $$
-- BEGIN
--     NEW.updated_at = now();
--     RETURN NEW;
-- END;
-- $$ LANGUAGE plpgsql;
--
--
-- -- users 테이블에 업데이트 시 update_at 자동 갱신 트리거 추가
-- CREATE TRIGGER update_updated_at
-- BEFORE UPDATE ON users
-- FOR EACH ROW
-- EXECUTE PROCEDURE update_updated_at_column();


-- 사용자 테이블 --
CREATE TABLE users (
                       user_id VARCHAR(255) PRIMARY KEY, -- 사용자 식별자
                       username VARCHAR(50) NOT NULL, -- 사용자명
                       email VARCHAR(100) NOT NULL UNIQUE, -- 사용자 이메일
                       password_hash VARCHAR(255) NOT NULL, -- 암호화된 비밀번호
                       email_verified BOOLEAN DEFAULT FALSE, -- 이메일 인증
                       phone VARCHAR(13), -- 휴대폰번호
                       ci VARCHAR(255) DEFAULT NULL, -- CI 값
                       is_active BOOLEAN DEFAULT TRUE, -- 게정 활성화 여부
                       last_login_at TIMESTAMP,
                       created_at TIMESTAMP DEFAULT now(),
                       updated_at TIMESTAMP DEFAULT now()
);