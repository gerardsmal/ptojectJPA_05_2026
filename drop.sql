
    set client_min_messages = WARNING;

    alter table if exists socio 
       drop constraint if exists fk_socio_certificato;

    drop table if exists certificato_medico cascade;

    drop table if exists socio cascade;
