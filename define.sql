
    create table certificato_medico (
        data_certificato date,
        id integer not null auto_increment,
        tipo_certificato bit,
        primary key (id)
    ) engine=InnoDB;

    create table socio (
        certificato_id integer,
        id integer not null auto_increment,
        codice_fiscale varchar(16) not null,
        cognome varchar(100) not null,
        nome varchar(100) not null,
        email varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table socio 
       add constraint uk_codice_fiscale unique (codice_fiscale);

    alter table socio 
       add constraint UK9hpjov7lek6gxy0827x0uq3ys unique (certificato_id);

    alter table socio 
       add constraint fk_socio_certificato 
       foreign key (certificato_id) 
       references certificato_medico (id);
