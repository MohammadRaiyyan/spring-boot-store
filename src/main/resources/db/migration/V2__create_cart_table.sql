CREATE TABLE carts (
  id BINARY(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  date_created DATE NOT NULL DEFAULT (curdate()),
  PRIMARY KEY (id)
);

CREATE TABLE cart_items (
  id BINARY(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  cart_id BINARY(16) NOT NULL,
  product_id BIGINT NOT NULL,
  quantity INTEGER NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  CONSTRAINT cart_items_carts_id_fk FOREIGN KEY (cart_id) REFERENCES carts (id) ON DELETE CASCADE,
  CONSTRAINT cart_items_products_id_fk FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
  CONSTRAINT cart_items_cart_product_unique UNIQUE (cart_id, product_id)
);