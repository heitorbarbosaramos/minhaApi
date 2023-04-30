CREATE TABLE usuario_telefone (
	usuario_id int8 NOT NULL,
	fone varchar(255) NULL,
	CONSTRAINT fk_usuario_telefone FOREIGN KEY (usuario_id) REFERENCES tb_usuario(id)
);