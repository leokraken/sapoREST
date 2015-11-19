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

drop table reportes_movimiento_stock;
create table reportes_movimiento_stock(
	id bigserial primary key,
	fecha timestamp default NOW(),
	almacenid varchar references avs(id),
	productoid bigint references productos(id),
	stock int,
	dif int
);


CREATE OR REPLACE FUNCTION trigger_func_stock() returns TRIGGER AS $$
begin
	insert into reportes_movimiento_stock(almacenid,productoid,stock,dif)values(NEW.id_av,NEW.id_producto, NEW.cantidad,NEW.cantidad-OLD.cantidad);
    	return NEW;
END
$$ language plpgsql;

CREATE TRIGGER trigger_stock 
before UPDATE on stock 
for each row execute procedure trigger_func_stock();

--list 4/11 11:53


alter table productos add column tags varchar;

CREATE TABLE comentarios(
	id bigserial primary key,
	almacenid varchar references avs(id),
	usuarioid varchar references usuarios(id),
	comentario varchar,
	fecha timestamp default now()
);
update productos set tags=null;

--listo 5/11 2:14
alter table productos add column imagenes varchar;
update productos set imagenes=null;
--listo..

create table notificaciones_personalizadas(
	id bigserial primary key,
	usuarioid varchar references usuarios(id),
	productoid bigint references productos(id),
	avid varchar references avs(id),
	mensaje varchar,
	tipo_notificacion integer references tipo_notificaciones(id),
	fecha timestamp default now()
);

create table producto_usuario_tienda_notificacion(
	productoid bigint references productos(id),
	usuarioid varchar references usuarios(id),
	tiendaid varchar references avs(id),
	minimo int,
	primary key(productoid,usuarioid,tiendaid)
);

--listo


alter table avs add column css varchar;
--listo


create table reporte_ganancias(
id serial,
usuarioid varchar references usuarios(id),
fecha timestamp default now(),
tipo_cuenta integer references tipo_cuenta(id),
monto numeric);

insert into reporte_ganancias(usuarioid, tipo_cuenta, monto) values('leo', 2, 50);
insert into reporte_ganancias(usuarioid, tipo_cuenta, monto) values('leo', 3, 100);
insert into reporte_ganancias(usuarioid, tipo_cuenta, monto, fecha) values('leo', 3, 100, now()- '1 day'::interval);
insert into reporte_ganancias(usuarioid, tipo_cuenta, monto, fecha) values('leo', 2, 50, now()- '1 day'::interval);

CREATE OR REPLACE FUNCTION trigger_ganancias() returns TRIGGER AS $$
DECLARE
	monto integer;
begin
	select precio into monto from tipo_cuenta where id=new.tipo_cuenta;
	IF(old.tipo_cuenta <> new.tipo_cuenta) THEN
		insert into reporte_ganancias(usuarioid, tipo_cuenta, monto) values(old.id, new.tipo_cuenta, monto);
    END IF;
	return NEW;
END
$$ language plpgsql;

CREATE TRIGGER trigger_ganancias 
before UPDATE on usuarios 
for each row execute procedure trigger_ganancias();


