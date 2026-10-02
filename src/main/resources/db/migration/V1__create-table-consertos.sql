create table consertos (
    id bigint not null auto_increment,
    data_entrada varchar(10) not null,
    data_saida varchar(10),
    nome varchar(100) not null,
    anos_experiencia integer,
    marca varchar(100) not null,
    modelo varchar(100) not null,
    ano char(4) not null,
    primary key (id)
);