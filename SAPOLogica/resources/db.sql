--global

--vistas dinamicas

--twitter y facebook
create table external_login_account(
	id smallint primary key,
	proveedor varchar(60)
);


create table administradores(
	id varchar primary key,
	nombre varchar(60),
	apellido varchar(60),
	account_type smallint references external_login_account(id)
);

create table usuarios(
	id varchar primary key,
	nombre varchar(60),
	apellido varchar(60),
	account_type smallint references external_login_account(id)
);

--global
create table avs(
	id bigserial primary key,
	id_usuario_duenio varchar references usuarios(id),
	url varchar(255),
	nombre varchar(60),
	descripcion varchar(255)
);

--many to many
create table usuarios_invitados(
	id_usuario varchar references usuarios(id),
	id_av bigint references avs(id),
	primary key(id_usuario, id_av)
);


--global para cada av
create table categorias(
	id bigserial primary key,
	nombre varchar(60),
	descripcion varchar(255),
	generica boolean default true
);

--mixto
create table productos(
	id bigserial primary key,
	nombre varchar(60),
	descripcion varchar(60),
	categoriaid bigint references categorias(id),
	generico boolean default true
);


--globales
create table atributos(
	id bigserial primary key,
	id_producto bigserial references productos(id) not null,
	nombre varchar(60),
	descripcion varchar(255)
);

--especifico av
create table carrito_producto(
	id_av bigint references avs(id),
	id_producto bigint references productos(id),
	cant_comprado smallint,
	cant_total smallint,
	primary key(id_av, id_producto)
);

--especifico av
create table stock(
	id_av bigint references avs(id),
	id_producto bigint references productos(id),
	cantidad integer,
	primary key(id_av, id_producto)
);


create table templates(
	id serial primary key,
	nombre varchar(60),
	descripcion varchar(255)
);

--relacion template categorias genericas.
create table template_categoria(
	id_template integer references templates(id),
	id_categoria bigint references categorias(id),
	primary key(id_template, id_categoria)
);

create table avs_categorias(
	id_av bigint references avs(id),
	id_categoria bigint references categorias(id),
	primary key (id_av, id_categoria)
);


--categorias de almacenes son las mismas que la de los productos?

