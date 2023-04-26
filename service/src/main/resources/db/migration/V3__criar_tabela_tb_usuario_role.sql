create table TB_USUARIO_ROLES(

    ID_USUARIO bigint not null,
    ID_PERFIL bigint not null,

    primary key(ID_USUARIO, ID_PERFIL)

);