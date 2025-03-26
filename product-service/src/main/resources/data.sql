-- 1. 원산지 정보를 관리하는 테이블 (origin)
CREATE TABLE origin (
                        origin_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '원산지 식별자',
                        country VARCHAR(100) NOT NULL COMMENT '원산지 국가명',
                        region VARCHAR(100) COMMENT '원산지 지역',
                        description TEXT COMMENT '원산지에 대한 추가 설명',
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '레코드 생성 시각',
                        PRIMARY KEY (origin_id)
) ENGINE=InnoDB COMMENT='상품의 원산지 정보를 관리하는 테이블';

-- 2. 상품 기본 정보를 관리하는 테이블 (product)
CREATE TABLE product (
                         product_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '상품 식별자',
                         product_name VARCHAR(255) NOT NULL COMMENT '상품 이름',
                         product_description TEXT COMMENT '상품 상세 설명',
                         price DECIMAL(10,2) NOT NULL COMMENT '상품 가격',
                         stock_quantity INT NOT NULL DEFAULT 0 COMMENT '재고 수량',
                         origin_id BIGINT COMMENT '원산지 정보',
                         created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시각',
                         updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 시각',
                         PRIMARY KEY (product_id),
                         CONSTRAINT fk_origin FOREIGN KEY (origin_id) REFERENCES origin(origin_id) ON DELETE SET NULL
) ENGINE=InnoDB COMMENT='상품 기본 정보를 관리하는 테이블 (원산지 정보 및 재고 수량 포함)';

-- 상점 정보 테이블 (store)
CREATE TABLE store (
                       store_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '상점 식별자 (자동 증가)',
                       store_name VARCHAR(255) NOT NULL COMMENT '상점명',
                       description TEXT COMMENT '상점에 대한 설명 (선택 사항)',
                       contact_email VARCHAR(255) COMMENT '상점 연락 이메일',
                       phone_number VARCHAR(50) COMMENT '상점 연락처 번호',
                       address VARCHAR(255) COMMENT '상점 주소',
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '상점 등록 시각',
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '상점 정보 수정 시각',
                       PRIMARY KEY (store_id)
) ENGINE=InnoDB COMMENT='상품을 관리하는 상점 정보 테이블';
