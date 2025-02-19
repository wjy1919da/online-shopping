
-- 用户表 (对应user表)
CREATE TABLE user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, -- 存储加密后的密码
    role INT NOT NULL DEFAULT 0 -- 0=普通用户, 1=管理员
) ENGINE=InnoDB;

-- 商品表 (对应product表)
CREATE TABLE product (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    quantity INT NOT NULL DEFAULT 0,
    retail_price DOUBLE NOT NULL,
    wholesale_price DOUBLE NOT NULL
) ENGINE=InnoDB;

-- 订单表 (对应order表)
CREATE TABLE orders ( -- 注意：order是保留字，用orders命名
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_placed DATETIME(6) NOT NULL,
    order_status VARCHAR(255) NOT NULL DEFAULT 'PROCESSING', -- PROCESSING/COMPLETED/CANCELED
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
) ENGINE=InnoDB;

-- 订单项表 (对应order_item表)
CREATE TABLE order_item (
    item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    purchased_price DOUBLE NOT NULL, -- 下单时的零售价
    quantity INT NOT NULL,
    wholesale_price DOUBLE NOT NULL, -- 下单时的批发价（用于利润计算）
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
) ENGINE=InnoDB;

-- 关注列表表（对应watchlist功能）
CREATE TABLE watchlist (
    watch_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    UNIQUE (user_id, product_id) -- 防止重复添加
) ENGINE=InnoDB;

-- 索引示例
CREATE INDEX idx_user_email ON user(email);
CREATE INDEX idx_order_status ON orders(order_status);