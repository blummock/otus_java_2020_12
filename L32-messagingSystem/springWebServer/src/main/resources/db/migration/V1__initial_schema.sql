CREATE SEQUENCE client_id_seq;
create table client
(
    id BIGINT not null primary key DEFAULT nextval('client_id_seq'),
    name varchar(50) not null,
    login varchar(50) not null,
    address varchar(50)
);

INSERT INTO client values (nextval('client_id_seq'), 'Test Name1', 'test1@mail.com', 'Test Address1');
INSERT INTO client values (nextval('client_id_seq'), 'Test Name2', 'test2@mail.com', 'Test Address2');

