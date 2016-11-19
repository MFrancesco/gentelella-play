# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  surname                       varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  type                          varchar(5),
  created_at                    timestamp not null,
  constraint ck_user_type check (type in ('ADMIN','USER')),
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id)
);


# --- !Downs

drop table if exists user;

