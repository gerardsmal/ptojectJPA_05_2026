
    set client_min_messages = WARNING;

    alter table if exists abbonamento_socio 
       drop constraint if exists fk_abbonamento_socio;

    alter table if exists socio 
       drop constraint if exists fk_socio_certificato;

    drop table if exists abbonamento_socio cascade;

    drop table if exists certificato_medico cascade;

    drop table if exists socio cascade;
