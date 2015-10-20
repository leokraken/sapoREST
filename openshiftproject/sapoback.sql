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
    token character varying
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
    id varchar NOT NULL,
    id_usuario_duenio character varying,
    nombre character varying(60),
    descripcion character varying(255)
);


ALTER TABLE avs OWNER TO postgres;

--
-- Name: avs_categorias; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE avs_categorias (
    id_av varchar NOT NULL,
    id_categoria bigint NOT NULL
);


ALTER TABLE avs_categorias OWNER TO postgres;


--
-- Name: carrito_producto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE carrito_producto (
    id_av varchar NOT NULL,
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
-- Name: stock; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE stock (
    id_av varchar NOT NULL,
    id_producto bigint NOT NULL,
    cantidad integer
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
-- Name: usuarios; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuarios (
    id character varying NOT NULL,
    nombre character varying(60),
    apellido character varying(60),
    token character varying
);


ALTER TABLE usuarios OWNER TO postgres;

--
-- Name: usuarios_invitados; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuarios_invitados (
    id_usuario character varying NOT NULL,
    id_av varchar NOT NULL
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

ALTER TABLE ONLY productos ALTER COLUMN id SET DEFAULT nextval('productos_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY templates ALTER COLUMN id SET DEFAULT nextval('templates_id_seq'::regclass);


--
-- Data for Name: administradores; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY administradores (id, nombre, apellido, token) FROM stdin;
leo	leo	\N	\N
leo2modificado	leo	clavijomodificado	\N
leo2	leo	clavijomodificado	\N
admin	\N	\N	\N
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

COPY avs (id, id_usuario_duenio, nombre, descripcion) FROM stdin;
2	leo	almacendeleo	el super almacen de leo
4	alebarreiro@live.com	Almacen 1	Mi primera almacen desde la app angular
5	alebarreiro@live.com	Almacen 2	Descripcion almacen 2
6	alebarreiro@live.com	Almacen 3	Descripcion almacen 3
7	alebarreiro@live.com	Almacen 4	descripcion almacen 4
8	alebarreiro@live.com	almacen5	la desc 5
9	alebarreiro@live.com	almacen6	Y YA ME TIENE RE PODRIDO
10	alebarreiro@live.com	Almacen7	Ahora si?
11	leo	openshift redeploy	set descrition asd
12	alebarreiro@live.com	Almacen 8	Descripcion almacen 8
13	alebarreiro@live.com	almacen9	Esta va con categorias!
14	alebarreiro@live.com	almacen10	ahora si, con categorías! (esperemos)
15	alebarreiro@live.com	Almacen 11	Bueno, la tercera es la vencida. Ahora si va con categorias.
16	gmunua	www.nacional.com.uy	Plantel principal del Club Nacional de Football
17	alebarreiro@live.com	Prueba2	Esto es una prueba
18	alebarreiro@live.com	Prueba 3	prueba antes de la demo
19	alebarreiro@live.com	otra tienda	Descripcion de la tienda
20	alebarreiro@live.com	Demo prototipo	Una descripcion de la almacen
22	alejandroanonmallo@gmail.com	FC Barcelona	Barca
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
-- Name: avs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('avs_id_seq', 22, true);


--
-- Data for Name: carrito_producto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY carrito_producto (id_av, id_producto, cant_comprado, cant_total) FROM stdin;
16	6	1	2
\.


--
-- Data for Name: categorias; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY categorias (id, nombre, descripcion, generica) FROM stdin;
1	categoria1	\N	t
2	categoria2	desc2	t
3	categoria3	descripcion	f
4	Goleros	Goleros	f
5	Defensas	Zagueros y laterales	f
6	Mediocampistas	Mediocampistas ofensivos y defensivos	f
7	Delanteros	Todos los delanteros	f
\.


--
-- Name: categorias_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('categorias_id_seq', 4, true);


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
\.


--
-- Name: productos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('productos_id_seq', 30, true);


--
-- Data for Name: stock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY stock (id_av, id_producto, cantidad) FROM stdin;
16	7	1
16	8	1
16	9	1
16	10	1
16	11	1
16	12	1
16	13	1
16	14	1
16	15	1
16	16	1
16	17	1
16	6	0
\.


--
-- Data for Name: template_categoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY template_categoria (id_template, id_categoria) FROM stdin;
5	1
\.


--
-- Data for Name: templates; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY templates (id, nombre, descripcion) FROM stdin;
1	PeñarolManda	Si señor
2	ChinoHijo	Si señor
3	BolsoHijo	Si señor
4	PachecoClavaGalli	Si señor
5	template post	template post
6	Fútbol	Equipos de fútbol
\.


--
-- Name: templates_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('templates_id_seq', 5, true);


--
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuarios (id, nombre, apellido, token) FROM stdin;
leonardo	leonardin	clavijoupdated	\N
leo	leo	apellido	\N
god	tonton	tintin	\N
god2	asa	asaaa	\N
PEPITO	LOCO	PAJARITO	\N
alebarreiro@live.com	Alejandro	Barreiro	\N
gmunua	Gustavo	Munúa	\N
maildefb@domain.com	um certo	alguem	9283982sdbsdjhtokenfb
maildefbdelchino@domain.com	Vamoooo	alguem	9283982sdbsdjhtokenfb
alejandroanonmallo@gmail.com	Añón	Alejandro	10207577810028295
\.


--
-- Data for Name: usuarios_invitados; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuarios_invitados (id_usuario, id_av) FROM stdin;
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
-- Name: productos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY productos
    ADD CONSTRAINT productos_pkey PRIMARY KEY (id);


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
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

