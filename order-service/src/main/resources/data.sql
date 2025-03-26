-- order_info 테이블 생성
CREATE TABLE order_info (
                            order_info_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                            orderNumber VARCHAR(255) NOT NULL UNIQUE,
                            orderDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP

);

-- order_item 테이블 생성
CREATE TABLE order_item (
                            id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                            product_id BIGINT NOT NULL,
                            product_name VARCHAR(255) NOT NULL,
                            price DOUBLE NOT NULL,
                            quantity INT NOT NULL,
                            order_info_id BIGINT NOT NULL,
                            CONSTRAINT fk_order_info FOREIGN KEY (order_info_id)
                                REFERENCES order_info(order_info_id)
                                ON DELETE CASCADE
);
