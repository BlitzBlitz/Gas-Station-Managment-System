INSERT INTO public.users(
	id, enabled, password, username)
	VALUES (10001, 1, '$2a$10$nPK9ssKgEwSNfd7prmtm..Q5OoGwX8JLASDAN9V0L3dHjAxFxYdky', 'miri');
INSERT INTO public.admin(
	id, is_deleted, registered_at, update_at, username)
	VALUES (10001, false, now() , now(), 'miri');
INSERT INTO public.authorities(
	id, authority, username)
	VALUES (10001, 'ROLE_ADMIN', 'miri');


INSERT INTO public.users(
    id, enabled, password, username)
    VALUES (20001, 1, '$2a$10$nPK9ssKgEwSNfd7prmtm..Q5OoGwX8JLASDAN9V0L3dHjAxFxYdky', 'cimi');
INSERT INTO public.admin(
	id, is_deleted, registered_at, update_at, username)
	VALUES (20001, false, now() , now(), 'cimi');
INSERT INTO public.authorities(
	id, authority, username)
	VALUES (20001, 'ROLE_ADMIN', 'cimi');

insert into users (enabled, password, username, id)
values (1, '$2a$10$nPK9ssKgEwSNfd7prmtm..Q5OoGwX8JLASDAN9V0L3dHjAxFxYdky', 'beni',30001);
insert into authorities (authority, username, id)
values ('ROLE_FINANCIER', 'beni', 30001);
insert into financier (created_at, username, gas_station_balance, is_deleted, salary, updated_at, id)
values ( now(), 'beni', 100000.0, false, 3000.0, now(), 10001);

insert into users (enabled, password, username, id)
    values (1,  '$2a$10$nPK9ssKgEwSNfd7prmtm..Q5OoGwX8JLASDAN9V0L3dHjAxFxYdky', 'agimi', 40001);
insert into authorities (authority, username, id)
    values ('ROLE_WORKER', 'agimi', 40001);
insert into worker (created_at, is_deleted, salary, shift_balance, updated_at, username, id)
    values (now(), false, 2000.0, 1000.0, now(), 'agimi', 10001);

insert into users (enabled, password, username, id)
    values (1,  '$2a$10$nPK9ssKgEwSNfd7prmtm..Q5OoGwX8JLASDAN9V0L3dHjAxFxYdky', 'berti', 40002);
insert into authorities (authority, username, id)
    values ('ROLE_WORKER', 'berti', 50001);
insert into worker (created_at, is_deleted, salary, shift_balance, updated_at, username, id)
    values (now(), false, 2000.0, 5000.0, now(), 'berti', 20001);

insert into fuel_deposit (available_amount, id)
values (1000, 10001);
insert into fuel (current_price, fuel_deposit_id, is_deleted, type, id)
values (120, 10001, false, 'gas', 10001);

insert into price_data (changed_by_id, fuel_type_id, price, price_changed_date, id)
values (10001, 10001, 120, now(), 10001);
update fuel set current_price=122 where id=10001;

insert into price_data (changed_by_id, fuel_type_id, price, price_changed_date, id)
values (10001, 10001, 122, now(), 10002);
update fuel set current_price=124 where id=10001;

INSERT INTO orders(
	id, amount, order_date, total, fuel_type_id, processed_by_id)
	VALUES (1001, 10, '2021-05-04 10:31:16.777171', 1200, 10001, 10001);

INSERT INTO orders(
	id, amount, order_date, total, fuel_type_id, processed_by_id)
	VALUES (1002, 100, '2021-04-04 10:31:16.777171', 33200, 10001, 20001);


INSERT INTO orders(
	id, amount, order_date, total, fuel_type_id, processed_by_id)
	VALUES (1003, 10, '2021-04-04 11:00:16.777171', 12200, 10001, 10001);

INSERT INTO orders(
	id, amount, order_date, total, fuel_type_id, processed_by_id)
	VALUES (1004, 100, '2021-04-04 10:20:16.777171', 3200, 10001, 20001);