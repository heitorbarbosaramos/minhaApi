create table TB_ENDERECO(

    id bigint not null auto_increment,
    cep varchar(9) not null,
    logradouro varchar(255) not null,
    complemento varchar(255),
    bairro varchar(255) not null,
    localidade varchar(255) not null,
    uf varchar(255) not null,
    ibge varchar(255) not null,
    ddd varchar(255) not null,
    gia varchar(255) not null,

    primary key(id)
);