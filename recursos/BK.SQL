--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.1
-- Dumped by pg_dump version 9.6.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: restaura_senha(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION restaura_senha(character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$
declare 
	pw varchar;
	i boolean;
begin
	select senha into pw from config limit 1;
	
	select id into i from usuario where nome_usuario=$1;
	if i IS NULL then
		raise warning 'NAO EXISTE USUARIO CADASTRADO COM NOME DE USUARIO "%"', $1;
	else 		
		update usuario set senha=pw where nome_usuario=$1;	
	
		raise info 'SENHA RESTAURADA PARA O USUARIO "%"', $1;
	end if;
	
	return;
end;
$_$;


ALTER FUNCTION public.restaura_senha(character varying) OWNER TO postgres;

--
-- Name: salva_altera_senha(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION salva_altera_senha(character varying, character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$
declare
	pw varchar;
begin
	select senha into pw from usuario where nome_usuario=$1;
	
	if pw IS NULL then
		raise warning 'NAO EXISTE USUARIO CADASTRADO COM NOME DE USUARIO "%"', $1;
	else		
		update config set senha=pw;	
		update usuario set senha=md5($2) where nome_usuario=$1;
		
		raise info 'SENHA SALVA E ALTERADA PARA O USUARIO "%"', $1;
	end if;
	
	return;
end;
$_$;


ALTER FUNCTION public.salva_altera_senha(character varying, character varying) OWNER TO postgres;

--
-- Name: aluno_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE aluno_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE aluno_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: aluno; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE aluno (
    id integer DEFAULT nextval('aluno_seq'::regclass) NOT NULL,
    nome character varying(256) NOT NULL,
    data_nasc timestamp without time zone,
    obs character varying(256),
    matricula_corrente integer DEFAULT '-1'::integer
);


ALTER TABLE aluno OWNER TO postgres;

--
-- Name: config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE config (
    pg_tolerancia integer DEFAULT 0,
    auto_carregar_usuarios boolean DEFAULT true,
    auto_carregar_modalidades boolean DEFAULT true,
    admin_usuario_tipo_id integer DEFAULT 1 NOT NULL,
    func_usuario_tipo_id integer DEFAULT 2 NOT NULL,
    diaria_aluno_id integer DEFAULT 1 NOT NULL,
    senha character varying
);


ALTER TABLE config OWNER TO postgres;

--
-- Name: desconto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE desconto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE desconto_seq OWNER TO postgres;

--
-- Name: desconto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE desconto (
    id integer DEFAULT nextval('desconto_seq'::regclass) NOT NULL,
    mat_id integer NOT NULL,
    porcentagem numeric(10,2) NOT NULL,
    data_alter timestamp without time zone DEFAULT now()
);


ALTER TABLE desconto OWNER TO postgres;

--
-- Name: matmod_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE matmod_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE matmod_seq OWNER TO postgres;

--
-- Name: matmod; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE matmod (
    id integer DEFAULT nextval('matmod_seq'::regclass) NOT NULL,
    mat_id integer NOT NULL,
    mod_id integer NOT NULL,
    data_contrato timestamp without time zone DEFAULT now(),
    data_encerramento timestamp without time zone
);


ALTER TABLE matmod OWNER TO postgres;

--
-- Name: matricula_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE matricula_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE matricula_seq OWNER TO postgres;

--
-- Name: matricula; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE matricula (
    id integer DEFAULT nextval('matricula_seq'::regclass) NOT NULL,
    aluno_id integer,
    data_inicio timestamp without time zone DEFAULT now(),
    data_fim timestamp without time zone,
    data_dia_pag timestamp without time zone NOT NULL
);


ALTER TABLE matricula OWNER TO postgres;

--
-- Name: medidas_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE medidas_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE medidas_seq OWNER TO postgres;

--
-- Name: medidas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE medidas (
    id integer DEFAULT nextval('medidas_seq'::regclass) NOT NULL,
    mat_id integer NOT NULL,
    peso integer,
    altura numeric(10,2),
    braco_esquerdo integer,
    antebraco_esquerdo integer,
    braco_direito integer,
    antebraco_direito integer,
    torax integer,
    cintura integer,
    bumbum integer,
    coxa_esquerda integer,
    coxa_direita integer,
    panturrilha_esquerda integer,
    panturrilha_direita integer,
    data_reg timestamp without time zone DEFAULT now()
);


ALTER TABLE medidas OWNER TO postgres;

--
-- Name: mensalidade_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE mensalidade_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mensalidade_seq OWNER TO postgres;

--
-- Name: mensalidade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE mensalidade (
    id integer DEFAULT nextval('mensalidade_seq'::regclass) NOT NULL,
    mod_id integer NOT NULL,
    valor numeric(10,2) NOT NULL,
    data_alter timestamp without time zone DEFAULT now()
);


ALTER TABLE mensalidade OWNER TO postgres;

--
-- Name: modalidade_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE modalidade_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE modalidade_seq OWNER TO postgres;

--
-- Name: modalidade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE modalidade (
    id integer DEFAULT nextval('modalidade_seq'::regclass) NOT NULL,
    descricao character varying(256) NOT NULL,
    data_inicio timestamp without time zone DEFAULT now(),
    data_fim timestamp without time zone,
    valor_inicial numeric(10,2) NOT NULL,
    valor_diaria numeric(10,2) DEFAULT 0
);


ALTER TABLE modalidade OWNER TO postgres;

--
-- Name: pagamento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pagamento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pagamento_seq OWNER TO postgres;

--
-- Name: pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pagamento (
    id integer DEFAULT nextval('pagamento_seq'::regclass) NOT NULL,
    mat_id integer NOT NULL,
    usuario_id integer NOT NULL,
    valor numeric(10,2) NOT NULL,
    desconto numeric(10,2) DEFAULT 0,
    data_pag timestamp without time zone DEFAULT now()
);


ALTER TABLE pagamento OWNER TO postgres;

--
-- Name: usuario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usuario_seq OWNER TO postgres;

--
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE usuario (
    id integer DEFAULT nextval('usuario_seq'::regclass) NOT NULL,
    tipo_id integer NOT NULL,
    nome character varying(256) NOT NULL,
    nome_usuario character varying(256) NOT NULL,
    senha character varying(256) NOT NULL,
    ativo boolean DEFAULT true
);


ALTER TABLE usuario OWNER TO postgres;

--
-- Name: usuario_tipo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE usuario_tipo (
    id integer NOT NULL,
    tipo character varying(100) NOT NULL
);


ALTER TABLE usuario_tipo OWNER TO postgres;

--
-- Data for Name: aluno; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY aluno (id, nome, data_nasc, obs, matricula_corrente) FROM stdin;
4	JOANA DARC MARTINS SOUZA	1977-02-15 00:00:00		4
5	JOANE MARTINS DE SOUZA LIRA	1997-11-13 00:00:00		5
6	MARCIO ROCHA 	1979-09-26 00:00:00		6
7	IARA PEREIRA DA PAIXÃO	1994-12-24 00:00:00		7
8	ITALA GABRIELA PEREIRA DA SILVA PAIXAO	2000-09-04 00:00:00		8
9	MONIQUE SOUZA E SILVA	1987-10-04 00:00:00		9
10	JAMILA SOUZA DOS SANTOS	1993-11-26 00:00:00		10
11	JOSE LOPES DE LIMA FILHO	1994-08-03 00:00:00		11
12	MERCIA MARIA	1962-11-12 00:00:00		12
13	MATHEUS DOS SANTOS BRITO FERREIRA	1998-12-18 00:00:00		13
14	JULIO CESAR JOSÉ ALVES DE SOUZA	1999-03-22 00:00:00		14
16	MOZART RAMON DE ANDRADE TEIXEIRA	1991-02-08 00:00:00		16
17	LUSANA DOS SANTOS MENEZES	1988-09-30 00:00:00		17
18	JOSE CELIO ERNESTO JUNIOR	1996-04-16 00:00:00		18
20	VITÓRIA FRANCISCA PASSOS ALVES	2002-05-03 00:00:00		20
21	RAYSSA SIQUEIRA GABRIEL	1999-01-05 00:00:00		21
23	VITOR ALVES RAMOS	1999-05-13 00:00:00		24
24	LUAN MANOEL BATISTA SIMOES	1990-08-14 00:00:00		25
25	PAULO JOSÉ DE SIQUEIRA SILVA	1981-07-22 00:00:00		26
26	LAIRE GABRIELA LOPES DA SILVA	1994-11-27 00:00:00		27
29	EDIVAN GOMES DE BRITO	1977-11-22 00:00:00		30
30	DANILO JOSE DE BRITO VIANA	1994-09-09 00:00:00		31
31	ISABELLA MARIANA DOS SANTOS SOARES	1994-03-15 00:00:00		32
32	Gildeci Mandú	1987-06-04 00:00:00		33
34	RAFAELA RODRIGUES DA SILVA	1993-04-22 00:00:00		35
35	LUIZ HENRIQUE DA SILVA OLIVEIRA	2000-10-18 00:00:00		36
37	DURCINETE LOPES DA SILVA BARBOSA	1963-04-16 00:00:00		38
38	MARISA BEZERRA CCARDEAL	1954-06-26 00:00:00		39
39	CARLOS VINICIUS SIQUEIRA DE MACEDO	1998-12-28 00:00:00		40
40	ARIELE LIRA NUNES	2000-11-17 00:00:00		41
41	ROSIMEIRE MACENA GONÇALVES	1986-04-01 00:00:00		42
44	Diária	\N	\N	45
45	Luciara	\N		46
59	Marcos	2001-12-25 00:00:00		60
60	Teste	2001-12-26 00:00:00		61
27	ANALIA RAFAELA DE SOUZA QUEIROZ	1990-12-04 00:00:00		-1
19	AMANDA SILVA DOS SANTOS	1987-11-23 00:00:00		-1
42	ALISSON GABRIEL MACIEL D SILVA	1998-01-25 00:00:00		72
43	Camila Sofia	1997-07-25 00:00:00		78
28	CARLA CIBELIA DE LIRA GOMES	1977-11-22 00:00:00		79
15	BRUNO DE SOUZA SIQUEIRA	1999-03-22 00:00:00		80
36	ANDREY BERNARDES DE SIQUEIRA LOPES	1992-10-09 00:00:00		81
\.


--
-- Name: aluno_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('aluno_seq', 60, true);


--
-- Data for Name: config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY config (pg_tolerancia, auto_carregar_usuarios, auto_carregar_modalidades, admin_usuario_tipo_id, func_usuario_tipo_id, diaria_aluno_id, senha) FROM stdin;
10	t	t	1	2	44	1b150854805cbe12194c8dbc55c900cd
\.


--
-- Data for Name: desconto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY desconto (id, mat_id, porcentagem, data_alter) FROM stdin;
4	4	0.00	2017-12-11 00:00:00
5	5	0.00	2017-12-11 00:00:00
6	6	0.00	2017-12-11 00:00:00
7	7	0.00	2017-12-11 00:00:00
8	8	0.00	2017-12-11 00:00:00
9	9	0.00	2017-12-11 00:00:00
10	10	0.00	2017-12-11 00:00:00
11	11	0.00	2017-12-11 00:00:00
12	12	0.00	2017-12-11 00:00:00
13	13	0.00	2017-12-11 00:00:00
14	14	0.00	2017-12-11 00:00:00
15	15	0.00	2017-12-11 00:00:00
16	16	0.00	2017-12-11 00:00:00
17	17	0.00	2017-12-11 00:00:00
18	18	0.00	2017-12-12 00:00:00
19	19	0.00	2017-12-12 00:00:00
20	20	0.00	2017-12-12 00:00:00
21	21	0.00	2017-12-12 00:00:00
23	24	0.00	2017-12-12 00:00:00
24	25	0.00	2017-12-12 00:00:00
25	26	0.00	2017-12-12 00:00:00
26	27	0.00	2017-12-12 00:00:00
27	28	0.00	2017-12-12 00:00:00
28	29	0.00	2017-12-12 00:00:00
29	30	0.00	2017-12-12 00:00:00
30	31	0.00	2017-12-14 00:00:00
31	32	0.00	2017-12-14 00:00:00
32	33	0.00	2017-12-16 00:00:00
35	35	0.00	2017-12-18 00:00:00
36	36	0.00	2017-12-18 00:00:00
37	37	0.00	2017-12-18 00:00:00
38	38	0.00	2017-12-18 00:00:00
39	39	0.00	2017-12-18 00:00:00
40	40	0.00	2017-12-18 00:00:00
41	41	0.00	2017-12-18 00:00:00
42	42	0.00	2017-12-18 00:00:00
44	44	0.00	2017-12-18 00:00:00
45	46	0.00	2017-12-19 00:00:00
59	60	0.00	2017-12-25 00:00:00
60	61	0.00	2017-12-26 00:00:00
62	62	10.00	2018-03-07 22:04:15.769
63	62	0.00	2018-03-07 22:05:27.529
64	62	10.00	2018-03-07 22:05:58.885
\.


--
-- Name: desconto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('desconto_seq', 64, true);


--
-- Data for Name: matmod; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY matmod (id, mat_id, mod_id, data_contrato, data_encerramento) FROM stdin;
4	4	1	2017-12-11 00:00:00	\N
5	5	1	2017-12-11 00:00:00	\N
6	6	7	2017-12-11 00:00:00	\N
7	7	1	2017-12-11 00:00:00	\N
8	8	1	2017-12-11 00:00:00	\N
9	9	1	2017-12-11 00:00:00	\N
10	10	1	2017-12-11 00:00:00	\N
11	11	1	2017-12-11 00:00:00	\N
12	12	7	2017-12-11 00:00:00	\N
13	13	1	2017-12-11 00:00:00	\N
14	14	1	2017-12-11 00:00:00	\N
15	15	1	2017-12-11 00:00:00	\N
16	16	1	2017-12-11 00:00:00	\N
17	17	1	2017-12-11 00:00:00	\N
18	18	1	2017-12-12 00:00:00	\N
19	19	1	2017-12-12 00:00:00	\N
20	20	1	2017-12-12 00:00:00	\N
21	21	1	2017-12-12 00:00:00	\N
23	24	1	2017-12-12 00:00:00	\N
24	24	2	2017-12-12 00:00:00	\N
25	25	1	2017-12-12 00:00:00	\N
26	26	4	2017-12-12 00:00:00	\N
27	27	4	2017-12-12 00:00:00	\N
28	28	1	2017-12-12 00:00:00	\N
29	29	1	2017-12-12 00:00:00	\N
30	30	1	2017-12-12 00:00:00	\N
31	31	3	2017-12-14 00:00:00	\N
32	32	1	2017-12-14 00:00:00	\N
33	33	7	2017-12-16 00:00:00	\N
35	35	1	2017-12-18 00:00:00	\N
36	36	1	2017-12-18 00:00:00	\N
37	37	1	2017-12-18 00:00:00	\N
38	38	4	2017-12-18 00:00:00	\N
39	39	1	2017-12-18 00:00:00	\N
40	40	1	2017-12-18 00:00:00	\N
41	41	1	2017-12-18 00:00:00	\N
42	42	1	2017-12-18 00:00:00	\N
44	44	4	2017-12-18 00:00:00	2017-12-18 19:51:38.288601
46	46	1	2017-12-19 00:00:00	\N
45	44	4	2017-12-18 19:51:45.636214	2017-12-20 20:30:10.350427
47	44	3	2017-12-20 20:30:11.910429	2017-12-20 20:30:17.183239
48	44	2	2017-12-20 20:30:13.158431	2017-12-20 20:30:17.183239
49	44	4	2017-12-20 20:30:23.86005	\N
65	60	1	2017-12-25 00:00:00	\N
66	61	1	2017-12-26 00:00:00	\N
67	62	4	2018-03-07 22:05:47.450717	2018-03-07 22:06:15.421567
68	62	1	2018-03-07 22:06:17.18437	\N
69	62	3	2018-03-08 11:59:55.436378	\N
75	72	1	2018-04-11 14:43:43.493968	\N
77	76	1	2018-04-11 15:58:35.762866	\N
78	78	4	2018-04-20 00:00:00	\N
79	79	1	2018-04-20 00:00:00	\N
80	80	1	2018-04-30 00:00:00	\N
81	70	1	2018-04-24 18:27:34.239404	\N
82	81	1	2018-04-29 00:00:00	\N
\.


--
-- Name: matmod_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('matmod_seq', 82, true);


--
-- Data for Name: matricula; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY matricula (id, aluno_id, data_inicio, data_fim, data_dia_pag) FROM stdin;
4	4	2017-12-11 00:00:00	\N	2017-12-11 00:00:00
5	5	2017-12-11 00:00:00	\N	2017-12-11 00:00:00
6	6	2017-12-11 00:00:00	\N	2017-12-11 00:00:00
7	7	2017-12-11 00:00:00	\N	2017-12-11 00:00:00
8	8	2017-12-11 00:00:00	\N	2017-12-11 00:00:00
9	9	2017-12-11 00:00:00	\N	2017-12-11 00:00:00
10	10	2017-12-11 00:00:00	\N	2017-12-11 00:00:00
11	11	2017-12-11 00:00:00	\N	2017-12-11 00:00:00
12	12	2017-12-11 00:00:00	\N	2017-12-11 00:00:00
13	13	2017-12-11 00:00:00	\N	2017-12-11 00:00:00
14	14	2017-12-11 00:00:00	\N	2017-12-11 00:00:00
16	16	2017-12-11 00:00:00	\N	2017-12-11 00:00:00
17	17	2017-12-11 00:00:00	\N	2017-12-11 00:00:00
18	18	2017-12-12 00:00:00	\N	2017-12-12 00:00:00
20	20	2017-12-12 00:00:00	\N	2017-12-12 00:00:00
21	21	2017-12-12 00:00:00	\N	2017-12-12 00:00:00
24	23	2017-12-12 00:00:00	\N	2017-12-12 00:00:00
25	24	2017-12-12 00:00:00	\N	2017-12-12 00:00:00
26	25	2017-12-12 00:00:00	\N	2017-12-12 00:00:00
27	26	2017-12-12 00:00:00	\N	2017-12-12 00:00:00
30	29	2017-12-12 00:00:00	\N	2017-12-12 00:00:00
31	30	2017-12-14 00:00:00	\N	2017-12-14 00:00:00
32	31	2017-12-14 00:00:00	\N	2017-12-14 00:00:00
33	32	2017-12-16 00:00:00	\N	2017-12-16 00:00:00
35	34	2017-12-18 00:00:00	\N	2017-12-18 00:00:00
36	35	2017-12-18 00:00:00	\N	2017-12-18 00:00:00
38	37	2017-12-18 00:00:00	\N	2017-12-18 00:00:00
39	38	2017-12-18 00:00:00	\N	2017-12-18 00:00:00
40	39	2017-12-18 00:00:00	\N	2017-12-18 00:00:00
41	40	2017-12-18 00:00:00	\N	2017-12-18 00:00:00
42	41	2017-12-18 00:00:00	\N	2017-12-18 00:00:00
45	44	2017-12-19 16:26:32.168672	\N	2017-12-19 16:26:32.168672
46	45	2017-12-19 00:00:00	\N	2017-12-19 00:00:00
60	59	2017-12-25 00:00:00	\N	2017-12-25 00:00:00
61	60	2017-12-26 00:00:00	\N	2017-12-26 00:00:00
62	42	2018-02-18 16:08:34.497	2018-03-24 11:17:22.42584	2018-02-18 16:08:34.497
28	27	2017-12-12 00:00:00	2018-03-24 12:32:57.441	2017-12-12 00:00:00
19	19	2017-12-12 00:00:00	2018-04-11 13:03:06.579115	2017-12-12 00:00:00
37	36	2017-12-18 00:00:00	2018-04-11 13:06:31.203084	2017-12-18 00:00:00
72	42	2018-04-11 00:00:00	\N	2018-04-11 00:00:00
15	15	2017-12-11 00:00:00	2018-04-11 15:58:35.762866	2017-12-11 00:00:00
44	43	2017-12-18 00:00:00	2018-04-11 16:07:56.12917	2017-12-18 00:00:00
78	43	2018-04-20 00:00:00	\N	2018-04-20 00:00:00
29	28	2017-12-12 00:00:00	2018-04-11 17:55:31.776406	2017-12-12 00:00:00
79	28	2018-04-20 00:00:00	\N	2018-04-20 00:00:00
76	15	2018-04-20 00:00:00	2018-04-24 17:09:29.153125	2018-04-20 00:00:00
80	15	2018-04-30 00:00:00	\N	2018-05-01 00:00:00
70	36	2018-04-15 00:00:00	2018-04-24 18:28:17.872604	2018-04-15 00:00:00
81	36	2018-04-29 00:00:00	\N	2018-05-01 00:00:00
\.


--
-- Name: matricula_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('matricula_seq', 81, true);


--
-- Data for Name: medidas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY medidas (id, mat_id, peso, altura, braco_esquerdo, antebraco_esquerdo, braco_direito, antebraco_direito, torax, cintura, bumbum, coxa_esquerda, coxa_direita, panturrilha_esquerda, panturrilha_direita, data_reg) FROM stdin;
\.


--
-- Name: medidas_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('medidas_seq', 1, false);


--
-- Data for Name: mensalidade; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY mensalidade (id, mod_id, valor, data_alter) FROM stdin;
1	1	55.00	2017-12-09 00:00:00
2	2	30.00	2017-12-09 00:00:00
3	3	30.00	2017-12-09 00:00:00
4	4	60.00	2017-12-09 00:00:00
5	5	75.00	2017-12-09 00:00:00
6	6	50.00	2017-12-09 00:00:00
7	7	5.00	2017-12-11 00:00:00
8	8	70.00	2017-12-11 00:00:00
\.


--
-- Name: mensalidade_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('mensalidade_seq', 8, true);


--
-- Data for Name: modalidade; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY modalidade (id, descricao, data_inicio, data_fim, valor_inicial, valor_diaria) FROM stdin;
1	Musculação	2017-12-09 00:00:00	\N	55.00	0.00
2	Treinamento Funcional	2017-12-09 00:00:00	\N	30.00	0.00
3	Fitdance	2017-12-09 00:00:00	\N	30.00	0.00
4	Musculação e Treinamento Funcional	2017-12-09 00:00:00	\N	60.00	0.00
6	Pacote Aeróbio	2017-12-09 00:00:00	\N	50.00	0.00
7	Diária	2017-12-11 00:00:00	\N	5.00	0.00
8	Musculação + Fitdance	2017-12-11 00:00:00	\N	70.00	0.00
5	Pacote Ouro ( Musculação + T. Funcional + Fitdance)	2017-12-09 00:00:00	\N	75.00	5.00
\.


--
-- Name: modalidade_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('modalidade_seq', 8, true);


--
-- Data for Name: pagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pagamento (id, mat_id, usuario_id, valor, desconto, data_pag) FROM stdin;
2	4	1	55.00	0.00	2017-12-11 05:59:42.307766
3	5	1	55.00	0.00	2017-12-11 06:01:36.128765
4	6	1	5.00	0.00	2017-12-11 06:13:25.165601
5	6	1	5.00	0.00	2017-12-11 06:13:25.181201
6	7	1	55.00	0.00	2017-12-11 07:57:46.824148
7	8	1	55.00	18.18	2017-12-11 08:01:21.263322
8	10	1	55.00	9.00	2017-12-11 09:01:57.172362
9	9	1	55.00	9.09	2017-12-11 09:03:12.941694
10	6	1	5.00	0.00	2017-12-11 10:00:34.512498
11	6	1	5.00	0.00	2017-12-11 10:00:34.559298
12	11	4	55.00	9.09	2017-12-11 18:36:55.686594
13	12	4	5.00	0.00	2017-12-11 18:43:32.234704
14	12	4	5.00	0.00	2017-12-11 18:43:32.250304
15	12	4	5.00	0.00	2017-12-11 18:43:32.265904
16	13	4	55.00	9.09	2017-12-11 19:00:55.775536
17	14	4	55.00	0.00	2017-12-11 19:05:52.457863
18	15	4	55.00	9.09	2017-12-11 19:11:44.364489
19	16	4	55.00	0.00	2017-12-11 19:13:21.049058
20	12	4	5.00	0.00	2017-12-11 19:34:38.529575
21	12	4	5.00	0.00	2017-12-11 19:34:38.545175
22	12	4	5.00	20.00	2017-12-11 19:35:55.067309
23	12	4	5.00	0.00	2017-12-11 19:37:44.844901
24	12	4	5.00	0.00	2017-12-11 19:37:44.860501
25	12	4	5.00	0.00	2017-12-11 19:37:45.125701
26	17	4	55.00	0.00	2017-12-11 20:06:53.352299
28	18	4	55.00	0.00	2017-12-12 17:29:34.210155
29	19	4	55.00	0.00	2017-12-12 17:34:02.386232
30	12	4	5.00	0.00	2017-12-12 17:36:11.18666
31	20	4	55.00	0.00	2017-12-12 17:42:22.112537
32	12	4	5.00	0.00	2017-12-12 18:51:13.477245
33	21	4	55.00	0.00	2017-12-12 18:53:20.696668
34	24	4	85.00	29.40	2017-12-12 19:43:58.976185
35	25	4	55.00	0.00	2017-12-12 19:45:16.304319
36	26	4	60.00	0.00	2017-12-12 19:46:46.193677
37	27	4	60.00	0.00	2017-12-12 19:48:43.277683
38	28	4	55.00	0.00	2017-12-12 19:51:04.81673
39	29	4	55.00	9.09	2017-12-12 19:53:40.912602
40	30	4	55.00	9.09	2017-12-12 19:54:53.031528
41	31	4	30.00	0.00	2017-12-14 18:48:18.266422
42	32	4	55.00	0.00	2017-12-14 18:50:33.378258
43	33	1	5.00	0.00	2017-12-16 18:02:04.419856
44	33	1	5.00	0.00	2017-12-16 18:02:04.482256
45	33	1	5.00	0.00	2017-12-16 18:02:04.513456
46	33	1	5.00	0.00	2017-12-16 18:02:04.544656
47	33	1	5.00	0.00	2017-12-16 18:02:04.560256
49	35	4	55.00	0.00	2017-12-18 18:53:47.65195
50	36	4	55.00	0.00	2017-12-18 19:04:41.885891
51	37	4	55.00	0.00	2017-12-18 19:08:02.829841
52	38	4	60.00	0.00	2017-12-18 19:09:47.646424
53	39	4	55.00	0.00	2017-12-18 19:14:38.08773
54	40	4	55.00	9.09	2017-12-18 19:19:46.188267
55	41	4	55.00	9.09	2017-12-18 19:27:24.688667
56	42	4	55.00	9.09	2017-12-18 19:33:42.053325
58	46	1	55.00	9.09	2017-12-19 20:28:02.913903
59	45	1	5.00	0.00	2017-12-19 20:32:38.317383
71	45	1	5.00	0.00	2018-02-15 15:40:15.731
73	19	1	55.00	9.90	2018-03-07 22:08:39.565
74	4	1	55.00	0.00	2018-03-08 19:19:24.843
75	4	1	55.00	0.00	2018-03-08 19:19:25.171
76	61	1	55.00	0.00	2018-03-24 12:25:15.544
77	61	1	55.00	0.00	2018-03-24 12:25:15.654
78	61	1	55.00	0.00	2018-03-24 12:25:15.654
79	41	1	55.00	0.00	2018-03-24 12:31:06.166
80	41	1	55.00	0.00	2018-03-24 12:31:06.166
81	41	1	55.00	0.00	2018-03-24 12:31:06.166
84	60	1	55.00	0.00	2018-04-24 16:02:19.592
85	60	1	55.00	0.00	2018-04-24 16:02:19.78
86	60	1	55.00	0.00	2018-04-24 16:02:19.78
87	60	1	55.00	0.00	2018-04-24 16:02:19.78
\.


--
-- Name: pagamento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pagamento_seq', 87, true);


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuario (id, tipo_id, nome, nome_usuario, senha, ativo) FROM stdin;
3	1	Linda Rocha	Linda	eaf450085c15c3b880c66d0b78f2c041	t
4	1	Mércia 	mercia	be5c1413a48353e02fcda3e2424ed902	t
2	2	Gil	gil	0d7d3a24242c6d235735b98149c6b35b	t
5	1	Manutenção	manut	e48379e1edcf90e9cfc6bcac30d84151	t
1	1	Marcio Rocha	marcio	1b150854805cbe12194c8dbc55c900cd	t
\.


--
-- Name: usuario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuario_seq', 5, true);


--
-- Data for Name: usuario_tipo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuario_tipo (id, tipo) FROM stdin;
1	administrador
2	funcionario
\.


--
-- Name: aluno aluno_nome_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY aluno
    ADD CONSTRAINT aluno_nome_key UNIQUE (nome);


--
-- Name: aluno aluno_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY aluno
    ADD CONSTRAINT aluno_pkey PRIMARY KEY (id);


--
-- Name: desconto desconto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY desconto
    ADD CONSTRAINT desconto_pkey PRIMARY KEY (id);


--
-- Name: matmod matmod_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY matmod
    ADD CONSTRAINT matmod_pkey PRIMARY KEY (id);


--
-- Name: matricula matricula_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY matricula
    ADD CONSTRAINT matricula_pkey PRIMARY KEY (id);


--
-- Name: medidas medidas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY medidas
    ADD CONSTRAINT medidas_pkey PRIMARY KEY (id);


--
-- Name: mensalidade mensalidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mensalidade
    ADD CONSTRAINT mensalidade_pkey PRIMARY KEY (id);


--
-- Name: modalidade modalidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY modalidade
    ADD CONSTRAINT modalidade_pkey PRIMARY KEY (id);


--
-- Name: pagamento pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pagamento
    ADD CONSTRAINT pagamento_pkey PRIMARY KEY (id);


--
-- Name: usuario usuario_nome_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_nome_usuario_key UNIQUE (nome_usuario);


--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- Name: usuario_tipo usuario_tipo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario_tipo
    ADD CONSTRAINT usuario_tipo_pkey PRIMARY KEY (id);


--
-- Name: desconto desconto_mat_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY desconto
    ADD CONSTRAINT desconto_mat_id_fkey FOREIGN KEY (mat_id) REFERENCES matricula(id) ON DELETE CASCADE;


--
-- Name: matmod matmod_mat_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY matmod
    ADD CONSTRAINT matmod_mat_id_fkey FOREIGN KEY (mat_id) REFERENCES matricula(id) ON DELETE CASCADE;


--
-- Name: matmod matmod_mod_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY matmod
    ADD CONSTRAINT matmod_mod_id_fkey FOREIGN KEY (mod_id) REFERENCES modalidade(id) ON DELETE CASCADE;


--
-- Name: matricula matricula_aluno_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY matricula
    ADD CONSTRAINT matricula_aluno_id_fkey FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE;


--
-- Name: medidas medidas_mat_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY medidas
    ADD CONSTRAINT medidas_mat_id_fkey FOREIGN KEY (mat_id) REFERENCES matricula(id) ON DELETE CASCADE;


--
-- Name: mensalidade mensalidade_mod_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mensalidade
    ADD CONSTRAINT mensalidade_mod_id_fkey FOREIGN KEY (mod_id) REFERENCES modalidade(id) ON DELETE CASCADE;


--
-- Name: pagamento pagamento_mat_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pagamento
    ADD CONSTRAINT pagamento_mat_id_fkey FOREIGN KEY (mat_id) REFERENCES matricula(id) ON DELETE CASCADE;


--
-- Name: usuario usuario_tipo_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_tipo_id_fkey FOREIGN KEY (tipo_id) REFERENCES usuario_tipo(id);


--
-- PostgreSQL database dump complete
--

