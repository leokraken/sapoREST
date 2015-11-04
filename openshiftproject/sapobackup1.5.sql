--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: administradores; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE administradores (
    id character varying NOT NULL,
    nombre character varying(60),
    apellido character varying(60),
    token character varying,
    password character varying,
    expires timestamp without time zone
);


ALTER TABLE administradores OWNER TO postgres;

--
-- Name: atributos; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE atributos (
    id bigint NOT NULL,
    id_producto bigint NOT NULL,
    nombre character varying(60),
    descripcion character varying(255)
);


ALTER TABLE atributos OWNER TO postgres;

--
-- Name: atributos_id_producto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE atributos_id_producto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE atributos_id_producto_seq OWNER TO postgres;

--
-- Name: atributos_id_producto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE atributos_id_producto_seq OWNED BY atributos.id_producto;


--
-- Name: atributos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE atributos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE atributos_id_seq OWNER TO postgres;

--
-- Name: atributos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE atributos_id_seq OWNED BY atributos.id;


--
-- Name: avs; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE avs (
    id character varying NOT NULL,
    id_usuario_duenio character varying,
    nombre character varying(60),
    descripcion character varying(255),
    privada boolean DEFAULT false,
    visitas bigint
);


ALTER TABLE avs OWNER TO postgres;

--
-- Name: avs_categorias; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE avs_categorias (
    id_av character varying NOT NULL,
    id_categoria bigint NOT NULL
);


ALTER TABLE avs_categorias OWNER TO postgres;

--
-- Name: carrito_producto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE carrito_producto (
    id_av character varying NOT NULL,
    id_producto bigint NOT NULL,
    cant_comprado smallint,
    cant_total smallint
);


ALTER TABLE carrito_producto OWNER TO postgres;

--
-- Name: categorias; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE categorias (
    id bigint NOT NULL,
    nombre character varying(60),
    descripcion character varying(255),
    generica boolean DEFAULT true
);


ALTER TABLE categorias OWNER TO postgres;

--
-- Name: categorias_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE categorias_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE categorias_id_seq OWNER TO postgres;

--
-- Name: categorias_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE categorias_id_seq OWNED BY categorias.id;


--
-- Name: notificaciones; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE notificaciones (
    id bigint NOT NULL,
    usuarioid character varying,
    mensaje character varying,
    tipo_notificacion integer,
    fecha timestamp without time zone DEFAULT now()
);


ALTER TABLE notificaciones OWNER TO postgres;

--
-- Name: notificaciones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE notificaciones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE notificaciones_id_seq OWNER TO postgres;

--
-- Name: notificaciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE notificaciones_id_seq OWNED BY notificaciones.id;


--
-- Name: notificaciones_parametros; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE notificaciones_parametros (
    id bigint NOT NULL,
    avid character varying NOT NULL,
    productoid bigint NOT NULL,
    mensaje character varying(255)
);


ALTER TABLE notificaciones_parametros OWNER TO postgres;

--
-- Name: notificaciones_parametros_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE notificaciones_parametros_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE notificaciones_parametros_id_seq OWNER TO postgres;

--
-- Name: notificaciones_parametros_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE notificaciones_parametros_id_seq OWNED BY notificaciones_parametros.id;


--
-- Name: productos; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE productos (
    id bigint NOT NULL,
    nombre character varying(60),
    descripcion character varying(60),
    categoriaid bigint,
    generico boolean DEFAULT true
);


ALTER TABLE productos OWNER TO postgres;

--
-- Name: productos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE productos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE productos_id_seq OWNER TO postgres;

--
-- Name: productos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE productos_id_seq OWNED BY productos.id;


--
-- Name: reportes_movimiento_stock; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE reportes_movimiento_stock (
    id bigint NOT NULL,
    fecha timestamp without time zone,
    almacenid character varying,
    productoid bigint,
    stock integer,
    dif integer
);


ALTER TABLE reportes_movimiento_stock OWNER TO postgres;

--
-- Name: reportes_movimiento_stock_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE reportes_movimiento_stock_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reportes_movimiento_stock_id_seq OWNER TO postgres;

--
-- Name: reportes_movimiento_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE reportes_movimiento_stock_id_seq OWNED BY reportes_movimiento_stock.id;


--
-- Name: stock; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE stock (
    id_av character varying NOT NULL,
    id_producto bigint NOT NULL,
    cantidad integer,
    minimo integer,
    notifica boolean DEFAULT false
);


ALTER TABLE stock OWNER TO postgres;

--
-- Name: template_categoria; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE template_categoria (
    id_template integer NOT NULL,
    id_categoria bigint NOT NULL
);


ALTER TABLE template_categoria OWNER TO postgres;

--
-- Name: templates; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE templates (
    id integer NOT NULL,
    nombre character varying(60),
    descripcion character varying(255)
);


ALTER TABLE templates OWNER TO postgres;

--
-- Name: templates_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE templates_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE templates_id_seq OWNER TO postgres;

--
-- Name: templates_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE templates_id_seq OWNED BY templates.id;


--
-- Name: tipo_cuenta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tipo_cuenta (
    id integer NOT NULL,
    nombre character varying(60),
    descripcion character varying(255),
    precio numeric,
    tiempo integer
);


ALTER TABLE tipo_cuenta OWNER TO postgres;

--
-- Name: tipo_cuentas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tipo_cuentas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tipo_cuentas_id_seq OWNER TO postgres;

--
-- Name: tipo_cuentas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tipo_cuentas_id_seq OWNED BY tipo_cuenta.id;


--
-- Name: tipo_notificaciones; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tipo_notificaciones (
    id integer NOT NULL,
    nombre character varying,
    mensaje character varying
);


ALTER TABLE tipo_notificaciones OWNER TO postgres;

--
-- Name: tipo_notificaciones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tipo_notificaciones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tipo_notificaciones_id_seq OWNER TO postgres;

--
-- Name: tipo_notificaciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tipo_notificaciones_id_seq OWNED BY tipo_notificaciones.id;


--
-- Name: usuarios; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuarios (
    id character varying NOT NULL,
    nombre character varying(60),
    apellido character varying(60),
    token character varying,
    tipo_cuenta integer,
    expires timestamp without time zone
);


ALTER TABLE usuarios OWNER TO postgres;

--
-- Name: usuarios_invitados; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuarios_invitados (
    id_usuario character varying NOT NULL,
    id_av character varying NOT NULL
);


ALTER TABLE usuarios_invitados OWNER TO postgres;

--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY atributos ALTER COLUMN id SET DEFAULT nextval('atributos_id_seq'::regclass);


--
-- Name: id_producto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY atributos ALTER COLUMN id_producto SET DEFAULT nextval('atributos_id_producto_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY categorias ALTER COLUMN id SET DEFAULT nextval('categorias_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificaciones ALTER COLUMN id SET DEFAULT nextval('notificaciones_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificaciones_parametros ALTER COLUMN id SET DEFAULT nextval('notificaciones_parametros_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY productos ALTER COLUMN id SET DEFAULT nextval('productos_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reportes_movimiento_stock ALTER COLUMN id SET DEFAULT nextval('reportes_movimiento_stock_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY templates ALTER COLUMN id SET DEFAULT nextval('templates_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipo_cuenta ALTER COLUMN id SET DEFAULT nextval('tipo_cuentas_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipo_notificaciones ALTER COLUMN id SET DEFAULT nextval('tipo_notificaciones_id_seq'::regclass);


--
-- Data for Name: administradores; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY administradores (id, nombre, apellido, token, password, expires) FROM stdin;
leo2modificado	leo	clavijomodificado	\N	admin	\N
leo2	leo	clavijomodificado	\N	admin	\N
admin	\N	\N	\N	admin	\N
leo	leo	\N	0496acea-5420-4a8c-ba3b-27817cc28c54	admin	2015-11-02 18:57:09.736
\.


--
-- Data for Name: atributos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY atributos (id, id_producto, nombre, descripcion) FROM stdin;
\.


--
-- Name: atributos_id_producto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('atributos_id_producto_seq', 1, false);


--
-- Name: atributos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('atributos_id_seq', 1, false);


--
-- Data for Name: avs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY avs (id, id_usuario_duenio, nombre, descripcion, privada, visitas) FROM stdin;
aaaaaaaaa	leo	mytienda	set descrition here	\N	0
aaaaaaaaa2	leo	mytienda	set descrition here	f	0
2	leo	NUEVO NOMBRE	NUEVA DESC	t	0
4	alebarreiro@live.com	Almacen 1	Mi primera almacen desde la app angular	f	0
5	alebarreiro@live.com	Almacen 2	Descripcion almacen 2	f	0
6	alebarreiro@live.com	Almacen 3	Descripcion almacen 3	f	0
7	alebarreiro@live.com	Almacen 4	descripcion almacen 4	f	0
8	alebarreiro@live.com	almacen5	la desc 5	f	0
9	alebarreiro@live.com	almacen6	Y YA ME TIENE RE PODRIDO	f	0
10	alebarreiro@live.com	Almacen7	Ahora si?	f	0
11	leo	openshift redeploy	set descrition asd	f	0
12	alebarreiro@live.com	Almacen 8	Descripcion almacen 8	f	0
13	alebarreiro@live.com	almacen9	Esta va con categorias!	f	0
14	alebarreiro@live.com	almacen10	ahora si, con categorías! (esperemos)	f	0
15	alebarreiro@live.com	Almacen 11	Bueno, la tercera es la vencida. Ahora si va con categorias.	f	0
17	alebarreiro@live.com	Prueba2	Esto es una prueba	f	0
18	alebarreiro@live.com	Prueba 3	prueba antes de la demo	f	0
19	alebarreiro@live.com	otra tienda	Descripcion de la tienda	f	0
20	alebarreiro@live.com	Demo prototipo	Una descripcion de la almacen	f	0
22	alejandroanonmallo@gmail.com	FC Barcelona	Barca	f	0
zt5324	leo	mytienda	set descrition here	f	0
zt5325	leo	mytienda	set descrition here	f	0
16	gmunua	www.nacional.com.uy	Plantel principal del Club Nacional de Football	f	1
\.


--
-- Data for Name: avs_categorias; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY avs_categorias (id_av, id_categoria) FROM stdin;
2	1
15	1
15	2
16	4
16	5
16	6
16	7
17	1
17	2
18	2
18	1
19	2
20	1
20	2
\.


--
-- Data for Name: carrito_producto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY carrito_producto (id_av, id_producto, cant_comprado, cant_total) FROM stdin;
16	6	1	5
\.


--
-- Data for Name: categorias; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY categorias (id, nombre, descripcion, generica) FROM stdin;
1	categoria1	\N	t
2	categoria2	desc2	t
3	categoria3	descripcion	t
4	Goleros	Goleros	t
5	Defensas	Zagueros y laterales	t
6	Mediocampistas	Mediocampistas ofensivos y defensivos	t
7	Delanteros	Todos los delanteros	t
\.


--
-- Name: categorias_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('categorias_id_seq', 4, true);


--
-- Data for Name: notificaciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY notificaciones (id, usuarioid, mensaje, tipo_notificacion, fecha) FROM stdin;
3	gmunua	Únicamente cuentas con 4 unidades de Esteban Conde!	2	2015-11-02 20:21:02.347
4	gmunua	\N	2	2015-11-02 19:33:47.357741
\.


--
-- Name: notificaciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('notificaciones_id_seq', 5, true);


--
-- Data for Name: notificaciones_parametros; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY notificaciones_parametros (id, avid, productoid, mensaje) FROM stdin;
\.


--
-- Name: notificaciones_parametros_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('notificaciones_parametros_id_seq', 1, false);


--
-- Data for Name: productos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY productos (id, nombre, descripcion, categoriaid, generico) FROM stdin;
6	Esteban Conde	Esteban Conde	4	f
7	Diego Polenta	Diego Polenta	5	f
8	Sebastián Gorga	Sebastián Gorga	5	f
9	Alfonso Espino	Alfonso Espino	5	f
10	Jorge Fucile	Jorge Fucile	5	f
11	José Aja	José Aja	5	f
12	Santiago Romero	Santiago Romero	6	f
13	Gonzalo Porras	Gonzalo Porras	6	f
14	Ignacio González	Ignacio González	6	f
15	Iván Alonso	Iván Alonso	7	f
16	Sebastián Abreu	Sebastián Abreu	7	f
17	Sebastián Fernández	Sebastián Fernández	7	f
28	prueba1510	o	1	f
31	test	\N	1	t
\.


--
-- Name: productos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('productos_id_seq', 31, true);


--
-- Data for Name: reportes_movimiento_stock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY reportes_movimiento_stock (id, fecha, almacenid, productoid, stock, dif) FROM stdin;
1	2015-11-03 02:52:59.525	2	7	0	\N
2	2015-11-03 02:52:59.53	2	8	0	\N
3	2015-11-03 02:52:59.534	2	10	0	\N
4	2015-11-03 02:54:11.902	2	7	10	\N
5	2015-11-03 02:57:05.855	2	31	33	\N
6	2015-11-03 02:57:20.241	2	31	38	\N
\.


--
-- Name: reportes_movimiento_stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('reportes_movimiento_stock_id_seq', 6, true);


--
-- Data for Name: stock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY stock (id_av, id_producto, cantidad, minimo, notifica) FROM stdin;
16	7	1	\N	f
16	8	1	\N	f
16	9	1	\N	f
16	10	1	\N	f
16	11	1	\N	f
16	12	1	\N	f
16	13	1	\N	f
16	14	1	\N	f
16	15	1	\N	f
16	16	1	\N	f
16	17	1	\N	f
16	6	4	5	t
2	8	0	\N	f
2	9	0	\N	f
2	10	0	\N	f
2	7	10	\N	f
2	31	38	\N	f
\.


--
-- Data for Name: template_categoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY template_categoria (id_template, id_categoria) FROM stdin;
5	2
5	3
\.


--
-- Data for Name: templates; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY templates (id, nombre, descripcion) FROM stdin;
1	PeñarolManda	Si señor
2	ChinoHijo	Si señor
3	BolsoHijo	Si señor
4	PachecoClavaGalli	Si señor
6	Fútbol	Equipos de fútbol
5	template5	template5modificado
\.


--
-- Name: templates_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('templates_id_seq', 5, true);


--
-- Data for Name: tipo_cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tipo_cuenta (id, nombre, descripcion, precio, tiempo) FROM stdin;
1	FREE	cuenta gold	2.0	12
2	SILVER	taasssssssssssssc	10000	12
\.


--
-- Name: tipo_cuentas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tipo_cuentas_id_seq', 2, true);


--
-- Data for Name: tipo_notificaciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tipo_notificaciones (id, nombre, mensaje) FROM stdin;
1	Limite cuenta	Has llegado al limite de la cuenta...
2	Stock productos	
\.


--
-- Name: tipo_notificaciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tipo_notificaciones_id_seq', 2, true);


--
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuarios (id, nombre, apellido, token, tipo_cuenta, expires) FROM stdin;
leonardo	leonardin	clavijoupdated	\N	1	\N
god	tonton	tintin	\N	1	\N
god2	asa	asaaa	\N	1	\N
PEPITO	LOCO	PAJARITO	\N	1	\N
alebarreiro@live.com	Alejandro	Barreiro	\N	1	\N
gmunua	Gustavo	Munúa	\N	1	\N
maildefb@domain.com	um certo	alguem	9283982sdbsdjhtokenfb	1	\N
maildefbdelchino@domain.com	Vamoooo	alguem	9283982sdbsdjhtokenfb	1	\N
alejandroanonmallo@gmail.com	Añón	Alejandro	10207577810028295	1	\N
leo	leo	apellido	\N	2	2015-11-02 21:59:23.018943
\.


--
-- Data for Name: usuarios_invitados; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuarios_invitados (id_usuario, id_av) FROM stdin;
god	2
god2	2
\.


--
-- Name: administradores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY administradores
    ADD CONSTRAINT administradores_pkey PRIMARY KEY (id);


--
-- Name: atributos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY atributos
    ADD CONSTRAINT atributos_pkey PRIMARY KEY (id);


--
-- Name: avs_categorias_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY avs_categorias
    ADD CONSTRAINT avs_categorias_pkey PRIMARY KEY (id_av, id_categoria);


--
-- Name: avs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY avs
    ADD CONSTRAINT avs_pkey PRIMARY KEY (id);


--
-- Name: carrito_producto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY carrito_producto
    ADD CONSTRAINT carrito_producto_pkey PRIMARY KEY (id_av, id_producto);


--
-- Name: categorias_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY categorias
    ADD CONSTRAINT categorias_pkey PRIMARY KEY (id);


--
-- Name: notificaciones_parametros_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY notificaciones_parametros
    ADD CONSTRAINT notificaciones_parametros_pkey PRIMARY KEY (id);


--
-- Name: notificaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY notificaciones
    ADD CONSTRAINT notificaciones_pkey PRIMARY KEY (id);


--
-- Name: productos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY productos
    ADD CONSTRAINT productos_pkey PRIMARY KEY (id);


--
-- Name: reportes_movimiento_stock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY reportes_movimiento_stock
    ADD CONSTRAINT reportes_movimiento_stock_pkey PRIMARY KEY (id);


--
-- Name: stock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY stock
    ADD CONSTRAINT stock_pkey PRIMARY KEY (id_av, id_producto);


--
-- Name: template_categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY template_categoria
    ADD CONSTRAINT template_categoria_pkey PRIMARY KEY (id_template, id_categoria);


--
-- Name: templates_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY templates
    ADD CONSTRAINT templates_pkey PRIMARY KEY (id);


--
-- Name: tipo_cuentas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tipo_cuenta
    ADD CONSTRAINT tipo_cuentas_pkey PRIMARY KEY (id);


--
-- Name: tipo_notificaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tipo_notificaciones
    ADD CONSTRAINT tipo_notificaciones_pkey PRIMARY KEY (id);


--
-- Name: usuarios_invitados_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuarios_invitados
    ADD CONSTRAINT usuarios_invitados_pkey PRIMARY KEY (id_usuario, id_av);


--
-- Name: usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);


--
-- Name: atributos_id_producto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY atributos
    ADD CONSTRAINT atributos_id_producto_fkey FOREIGN KEY (id_producto) REFERENCES productos(id);


--
-- Name: avs_categorias_id_av_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY avs_categorias
    ADD CONSTRAINT avs_categorias_id_av_fkey FOREIGN KEY (id_av) REFERENCES avs(id);


--
-- Name: avs_categorias_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY avs_categorias
    ADD CONSTRAINT avs_categorias_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES categorias(id);


--
-- Name: avs_id_usuario_duenio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY avs
    ADD CONSTRAINT avs_id_usuario_duenio_fkey FOREIGN KEY (id_usuario_duenio) REFERENCES usuarios(id);


--
-- Name: carrito_producto_id_av_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY carrito_producto
    ADD CONSTRAINT carrito_producto_id_av_fkey FOREIGN KEY (id_av) REFERENCES avs(id);


--
-- Name: carrito_producto_id_producto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY carrito_producto
    ADD CONSTRAINT carrito_producto_id_producto_fkey FOREIGN KEY (id_producto) REFERENCES productos(id);


--
-- Name: notificaciones_parametros_avid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificaciones_parametros
    ADD CONSTRAINT notificaciones_parametros_avid_fkey FOREIGN KEY (avid) REFERENCES avs(id);


--
-- Name: notificaciones_parametros_productoid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificaciones_parametros
    ADD CONSTRAINT notificaciones_parametros_productoid_fkey FOREIGN KEY (productoid) REFERENCES productos(id);


--
-- Name: notificaciones_tipo_notificacion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificaciones
    ADD CONSTRAINT notificaciones_tipo_notificacion_fkey FOREIGN KEY (tipo_notificacion) REFERENCES tipo_notificaciones(id);


--
-- Name: notificaciones_usuarioid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notificaciones
    ADD CONSTRAINT notificaciones_usuarioid_fkey FOREIGN KEY (usuarioid) REFERENCES usuarios(id);


--
-- Name: productos_categoriaid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY productos
    ADD CONSTRAINT productos_categoriaid_fkey FOREIGN KEY (categoriaid) REFERENCES categorias(id);


--
-- Name: stock_id_av_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY stock
    ADD CONSTRAINT stock_id_av_fkey FOREIGN KEY (id_av) REFERENCES avs(id);


--
-- Name: stock_id_producto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY stock
    ADD CONSTRAINT stock_id_producto_fkey FOREIGN KEY (id_producto) REFERENCES productos(id);


--
-- Name: template_categoria_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY template_categoria
    ADD CONSTRAINT template_categoria_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES categorias(id);


--
-- Name: template_categoria_id_template_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY template_categoria
    ADD CONSTRAINT template_categoria_id_template_fkey FOREIGN KEY (id_template) REFERENCES templates(id);


--
-- Name: usuarios_invitados_id_av_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuarios_invitados
    ADD CONSTRAINT usuarios_invitados_id_av_fkey FOREIGN KEY (id_av) REFERENCES avs(id);


--
-- Name: usuarios_invitados_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuarios_invitados
    ADD CONSTRAINT usuarios_invitados_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES usuarios(id);


--
-- Name: usuarios_tipo_cuenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT usuarios_tipo_cuenta_fkey FOREIGN KEY (tipo_cuenta) REFERENCES tipo_cuenta(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

