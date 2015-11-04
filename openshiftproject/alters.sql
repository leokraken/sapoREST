--alter table administradores add column password varchar;
--alter table administradores add column expires timestamp;
--update administradores set password = 'admin';
--create table tipo_notificaciones(
--	id serial primary key,
--	nombre varchar,
--	mensaje varchar
--);
--create table notificaciones(
--	id bigserial primary key,
--	usuarioid varchar references usuarios(id),
--	mensaje varchar,
--	tipo_notificacion int references tipo_notificaciones(id)
--);

--insert into tipo_notificaciones(nombre,mensaje) values('Limite cuenta', 'Has llegado al limite de la cuenta...');
--insert into tipo_notificaciones(nombre,mensaje) values('Stock productos', '');

--alter table notificaciones add column fecha timestamp;
--alter table notificaciones alter column fecha set default now();


--alter table tipo_cuenta add column tiempo int;
--update tipo_cuenta set tiempo=365;
--alter table usuarios add column expires timestamp;
--alter table avs add column visitas bigint;
--update avs set visitas=0;

create table reportes_movimiento_stock(
	id bigserial primary key,
	fecha timestamp,
	almacenid varchar,
	productoid bigint,
	stock int
);

--listo...

alter table reportes_movimiento_stock add column dif int;







