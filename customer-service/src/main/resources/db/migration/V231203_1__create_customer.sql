create schema if not exists app;

create table if not exists app.customer
(
    id        bigserial
        primary key,
    create_at timestamp(6),
    gender    varchar(255),
    last_name varchar(255),
    mail      varchar(255),
    name      varchar(255),
    update_ad timestamp(6)
);