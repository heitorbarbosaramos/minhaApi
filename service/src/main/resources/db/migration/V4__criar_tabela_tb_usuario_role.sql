CREATE TABLE tb_usuario_roles (
	id_usuario int8 NOT NULL,
	id_perfil int8 NOT NULL,
	CONSTRAINT tb_usuario_roles_pkey PRIMARY KEY (id_usuario, id_perfil),
	CONSTRAINT fkbrusuariorolesidperfil FOREIGN KEY (id_perfil) REFERENCES tb_usuario_perfil(id),
	CONSTRAINT fkkkusuariorolesidusuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id)
);