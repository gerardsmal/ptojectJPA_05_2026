
    set client_min_messages = WARNING;

    alter table if exists abbonamento_attivita 
       drop constraint if exists FK853iwjge5sco7nac3v8pvs72v;

    alter table if exists abbonamento_attivita 
       drop constraint if exists FK7slwjgyb7wchv5gnm07g9m6v1;

    alter table if exists abbonamento_socio 
       drop constraint if exists fk_abbonamento_socio;

    alter table if exists socio 
       drop constraint if exists fk_socio_certificato;

    drop table if exists abbonamento_attivita cascade;

    drop table if exists abbonamento_socio cascade;

    drop table if exists attivita cascade;

    drop table if exists certificato_medico cascade;

    drop table if exists socio cascade;
