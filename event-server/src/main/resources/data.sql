-- 1. 이벤트 로그 테이블 (event)
CREATE TABLE event (
                       event_id BIGSERIAL PRIMARY KEY,                            -- 이벤트 식별자 (자동 증가)
                       event_type VARCHAR(100) NOT NULL,                           -- 이벤트 유형 (예: 'ORDER_CREATED', 'PRODUCT_UPDATED' 등)
                       aggregate_id BIGINT NOT NULL,                               -- 이벤트와 연관된 엔티티의 식별자 (예: 주문, 상품 등)
                       aggregate_type VARCHAR(100) NOT NULL,                       -- 연관된 엔티티의 타입 (예: 'Order', 'Product' 등)
                       payload TEXT,                                               -- 이벤트 상세 정보 (JSON 형식 등)
                       status VARCHAR(50) NOT NULL,                                -- 이벤트 처리 상태 (예: 'PENDING', 'PROCESSED', 'FAILED' 등)
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,    -- 이벤트 생성 시각
                       processed_at TIMESTAMP                                      -- 이벤트 처리 완료 시각 (처리 전에는 NULL)
);

COMMENT ON TABLE event IS '이벤트 서버에서 사용하는 이벤트 로그 테이블';
COMMENT ON COLUMN event.event_id IS '이벤트 식별자 (자동 증가)';
COMMENT ON COLUMN event.event_type IS '이벤트 유형 (예: ORDER_CREATED, PRODUCT_UPDATED 등)';
COMMENT ON COLUMN event.aggregate_id IS '이벤트와 관련된 엔티티의 식별자';
COMMENT ON COLUMN event.aggregate_type IS '연관된 엔티티의 타입 (예: Order, Product 등)';
COMMENT ON COLUMN event.payload IS '이벤트 상세 정보 (JSON 형식 등)';
COMMENT ON COLUMN event.status IS '이벤트 처리 상태 (PENDING, PROCESSED, FAILED 등)';
COMMENT ON COLUMN event.created_at IS '이벤트 생성 시각';
COMMENT ON COLUMN event.processed_at IS '이벤트 처리 완료 시각 (처리 전에는 NULL)';

-- 2. 이벤트 예산 테이블 (event_budget)
CREATE TABLE event_budget (
                              event_budget_id BIGSERIAL PRIMARY KEY,                     -- 이벤트 예산 식별자 (자동 증가)
                              event_id BIGINT NOT NULL,                                   -- 관련 이벤트 식별자 (event 테이블 참조)
                              discount_type VARCHAR(20) NOT NULL,                         -- 할인 유형: 'COUPON', 'PERCENTAGE', 'COMBINED'
                              coupon_amount NUMERIC(10,2),                                -- 쿠폰 할인 금액 (쿠폰 할인 또는 결합 할인 시 사용)
                              percentage_discount NUMERIC(5,2),                           -- 퍼센트 할인 값 (퍼센트 할인 또는 결합 할인 시 사용)
                              description TEXT,                                           -- 할인 설명 및 상세 내용 (예: '30% 할인 쿠폰')
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,    -- 레코드 생성 시각
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP     -- 레코드 마지막 수정 시각
);

ALTER TABLE event_budget
    ADD CONSTRAINT chk_discount_type CHECK (discount_type IN ('COUPON', 'PERCENTAGE', 'COMBINED'));

COMMENT ON TABLE event_budget IS '이벤트 예산 테이블: 쿠폰, % 할인 또는 결합 할인 유형 관리';
COMMENT ON COLUMN event_budget.event_budget_id IS '이벤트 예산 식별자 (자동 증가)';
COMMENT ON COLUMN event_budget.event_id IS '관련 이벤트 식별자 (event 테이블 참조)';
COMMENT ON COLUMN event_budget.discount_type IS '할인 유형 (COUPON, PERCENTAGE, COMBINED 중 하나)';
COMMENT ON COLUMN event_budget.coupon_amount IS '쿠폰 할인 금액 (쿠폰 할인 또는 결합 할인 시 사용)';
COMMENT ON COLUMN event_budget.percentage_discount IS '퍼센트 할인 값 (퍼센트 할인 또는 결합 할인 시 사용)';
COMMENT ON COLUMN event_budget.description IS '할인에 대한 설명 및 상세 내용';
COMMENT ON COLUMN event_budget.created_at IS '레코드 생성 시각';
COMMENT ON COLUMN event_budget.updated_at IS '레코드 마지막 수정 시각';

-- 3. 쿠폰 관리 테이블 (coupon)
CREATE TABLE coupon (
                        coupon_id BIGSERIAL PRIMARY KEY,                           -- 쿠폰 식별자 (자동 증가)
                        event_budget_id BIGINT NOT NULL,                            -- 연결된 이벤트 예산 식별자 (event_budget 테이블 참조)
                        coupon_code VARCHAR(50) NOT NULL UNIQUE,                    -- 고유 쿠폰 코드
                        issued_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,     -- 쿠폰 발급 시각
                        redeemed_at TIMESTAMP,                                      -- 쿠폰 사용 시각 (미사용 시 NULL)
                        expiration_date TIMESTAMP NOT NULL,                         -- 쿠폰 만료일
                        CONSTRAINT fk_event_budget FOREIGN KEY (event_budget_id)
                            REFERENCES event_budget(event_budget_id)
                            ON DELETE CASCADE
);

COMMENT ON TABLE coupon IS '개별 쿠폰 관리 테이블: 쿠폰 코드 및 발급/사용 정보를 관리';
COMMENT ON COLUMN coupon.coupon_id IS '쿠폰 식별자 (자동 증가)';
COMMENT ON COLUMN coupon.event_budget_id IS '연결된 이벤트 예산 식별자 (event_budget 테이블 참조)';
COMMENT ON COLUMN coupon.coupon_code IS '고유 쿠폰 코드';
COMMENT ON COLUMN coupon.issued_at IS '쿠폰 발급 시각';
COMMENT ON COLUMN coupon.redeemed_at IS '쿠폰 사용 시각 (미사용 시 NULL)';
COMMENT ON COLUMN coupon.expiration_date IS '쿠폰 만료일';
