--------------------------------------------------
-- 1. 결제 트랜잭션 테이블 (payment)
--------------------------------------------------
CREATE TABLE payment (
                         payment_id BIGSERIAL PRIMARY KEY,                         -- 결제 식별자 (자동 증가)
                         order_info_id BIGINT NOT NULL,                             -- 주문 식별자 (order-service의 주문 정보와 연계)
                         payment_type VARCHAR(50) NOT NULL,                         -- 결제 유형 (예: 'CARD', 'BANK_TRANSFER' 등)
                         payment_method VARCHAR(50) NOT NULL,                       -- 결제 방법 (예: 'CREDIT_CARD', 'REALTIME_TRANSFER' 등)
                         payment_details TEXT,                                      -- 결제 관련 상세 정보 (예: 승인번호, 트랜잭션 ID 등)
                         amount DECIMAL(10,2) NOT NULL,                             -- 결제 금액
                         status VARCHAR(50) NOT NULL,                               -- 결제 상태 (예: 'SUCCESS', 'FAILED', 'PENDING' 등)
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,   -- 결제 생성 시각
                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP    -- 결제 정보 수정 시각
);

COMMENT ON TABLE payment IS '결제 트랜잭션 관리 테이블';
COMMENT ON COLUMN payment.payment_id IS '결제 식별자 (자동 증가)';
COMMENT ON COLUMN payment.order_info_id IS '주문 식별자 (order-service와 연계)';
COMMENT ON COLUMN payment.payment_type IS '결제 유형 (예: CARD, BANK_TRANSFER 등)';
COMMENT ON COLUMN payment.payment_method IS '결제 방법 (예: CREDIT_CARD, REALTIME_TRANSFER 등)';
COMMENT ON COLUMN payment.payment_details IS '결제 관련 상세 정보 (승인번호, 트랜잭션 ID 등)';
COMMENT ON COLUMN payment.amount IS '결제 금액';
COMMENT ON COLUMN payment.status IS '결제 상태 (SUCCESS, FAILED, PENDING 등)';
COMMENT ON COLUMN payment.created_at IS '결제 생성 시각';
COMMENT ON COLUMN payment.updated_at IS '결제 정보 수정 시각';


--------------------------------------------------
-- 2. 결제 실패 사유 테이블 (payment_failure)
--------------------------------------------------
CREATE TABLE payment_failure (
                                 failure_id BIGSERIAL PRIMARY KEY,                         -- 실패 기록 식별자 (자동 증가)
                                 payment_id BIGINT NOT NULL,                                -- 실패한 결제의 식별자 (payment 테이블 참조)
                                 failure_reason TEXT NOT NULL,                              -- 결제 실패 원인에 대한 설명
                                 failure_code VARCHAR(50),                                  -- 선택적 실패 코드 (예: API 에러 코드 등)
                                 additional_details TEXT,                                   -- 추가 실패 정보나 로그
                                 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP    -- 실패 기록 생성 시각
);

ALTER TABLE payment_failure
    ADD CONSTRAINT fk_payment_failure_payment
        FOREIGN KEY (payment_id)
            REFERENCES payment(payment_id)
            ON DELETE CASCADE;

COMMENT ON TABLE payment_failure IS '결제 실패 사유를 관리하는 테이블';
COMMENT ON COLUMN payment_failure.failure_id IS '실패 기록 식별자 (자동 증가)';
COMMENT ON COLUMN payment_failure.payment_id IS '실패한 결제의 식별자 (payment 테이블 참조)';
COMMENT ON COLUMN payment_failure.failure_reason IS '결제 실패 원인에 대한 설명';
COMMENT ON COLUMN payment_failure.failure_code IS '실패 코드 (예: API 에러 코드 등)';
COMMENT ON COLUMN payment_failure.additional_details IS '추가 실패 정보나 로그';
COMMENT ON COLUMN payment_failure.created_at IS '실패 기록 생성 시각';


--------------------------------------------------
-- 3. 환불 관리 테이블 (refund)
--------------------------------------------------
CREATE TABLE refund (
                        refund_id BIGSERIAL PRIMARY KEY,                          -- 환불 식별자 (자동 증가)
                        payment_id BIGINT NOT NULL,                                -- 원 결제 식별자 (payment 테이블 참조)
                        refund_amount DECIMAL(10,2) NOT NULL,                      -- 환불 금액
                        refund_method VARCHAR(50) NOT NULL,                        -- 환불 방식 (예: 'ORIGINAL_PAYMENT_METHOD', 'BANK_TRANSFER' 등)
                        refund_status VARCHAR(50) NOT NULL,                        -- 환불 상태 (예: 'PENDING', 'COMPLETED' 등)
                        refund_details TEXT,                                       -- 환불 관련 추가 정보 (예: 환불 승인번호 등)
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,   -- 환불 생성 시각
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP    -- 환불 정보 수정 시각
);

ALTER TABLE refund
    ADD CONSTRAINT fk_refund_payment
        FOREIGN KEY (payment_id)
            REFERENCES payment(payment_id)
            ON DELETE CASCADE;

COMMENT ON TABLE refund IS '환불 관리 테이블';
COMMENT ON COLUMN refund.refund_id IS '환불 식별자 (자동 증가)';
COMMENT ON COLUMN refund.payment_id IS '원 결제 식별자 (payment 테이블 참조)';
COMMENT ON COLUMN refund.refund_amount IS '환불 금액';
COMMENT ON COLUMN refund.refund_method IS '환불 방식 (예: ORIGINAL_PAYMENT_METHOD, BANK_TRANSFER 등)';
COMMENT ON COLUMN refund.refund_status IS '환불 상태 (PENDING, COMPLETED 등)';
COMMENT ON COLUMN refund.refund_details IS '환불 관련 추가 정보 (환불 승인번호 등)';
COMMENT ON COLUMN refund.created_at IS '환불 생성 시각';
COMMENT ON COLUMN refund.updated_at IS '환불 정보 수정 시각';


--------------------------------------------------
-- 4. 정산 관리 테이블 (settlement)
--------------------------------------------------
CREATE TABLE settlement (
                            settlement_id BIGSERIAL PRIMARY KEY,                      -- 정산 식별자 (자동 증가)
                            payment_id BIGINT NOT NULL,                                -- 정산 대상 결제 식별자 (payment 테이블 참조)
                            store_id BIGINT NOT NULL,                                  -- 정산 대상 상점 식별자
                            product_id BIGINT,                                         -- 관련 상품 식별자 (선택적, product-service의 product 테이블 참조)
                            settlement_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 정산 일자 (실제 정산 날짜)
                            settled_amount DECIMAL(10,2) NOT NULL,                     -- 정산된 총 금액
                            commission_fee DECIMAL(10,2) DEFAULT 0,                    -- 정산 시 공제된 수수료 (있을 경우)
                            net_amount DECIMAL(10,2) NOT NULL,                         -- 수수료 공제 후 정산 금액
                            status VARCHAR(50) NOT NULL,                               -- 정산 상태 (예: 'COMPLETED', 'PENDING' 등)
                            details TEXT,                                              -- 정산 관련 추가 정보
                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP     -- 정산 기록 생성 시각
);

ALTER TABLE settlement
    ADD CONSTRAINT fk_settlement_payment
        FOREIGN KEY (payment_id)
            REFERENCES payment(payment_id)
            ON DELETE CASCADE;

-- 상점(store) 및 상품(product) 테이블과의 외래키 제약 조건은 해당 테이블이 존재할 경우 추가할 수 있음.
-- 예: store 테이블이 존재한다면 아래와 같이 추가할 수 있습니다.
-- ALTER TABLE settlement
-- ADD CONSTRAINT fk_settlement_store
-- FOREIGN KEY (store_id)
-- REFERENCES store(store_id)
-- ON DELETE CASCADE;

COMMENT ON TABLE settlement IS '정산 관리 테이블: 결제 건에 대해 상점 및 관련 상품 정보를 포함하여 정산 내역 관리';
COMMENT ON COLUMN settlement.settlement_id IS '정산 식별자 (자동 증가)';
COMMENT ON COLUMN settlement.payment_id IS '정산 대상 결제 식별자 (payment 테이블 참조)';
COMMENT ON COLUMN settlement.store_id IS '정산 대상 상점 식별자';
COMMENT ON COLUMN settlement.product_id IS '관련 상품 식별자 (선택적, product-service 참조)';
COMMENT ON COLUMN settlement.settlement_date IS '정산 일자 (실제 정산 날짜)';
COMMENT ON COLUMN settlement.settled_amount IS '정산된 총 금액';
COMMENT ON COLUMN settlement.commission_fee IS '공제된 수수료 (있을 경우)';
COMMENT ON COLUMN settlement.net_amount IS '수수료 공제 후 정산 금액';
COMMENT ON COLUMN settlement.status IS '정산 상태 (COMPLETED, PENDING 등)';
COMMENT ON COLUMN settlement.details IS '정산 관련 추가 정보';
COMMENT ON COLUMN settlement.created_at IS '정산 기록 생성 시각';
