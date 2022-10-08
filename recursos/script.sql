
\encoding UTF8

drop table if exists config;

drop table if exists pagamento;
drop table if exists medidas;

drop table if exists matmod;

drop table if exists mensalidade;
drop table if exists modalidade;

drop table if exists desconto;

drop table if exists matricula;
drop table if exists aluno;

drop table if exists usuario;
drop table if exists usuario_tipo;

drop sequence if exists aluno_seq;
drop sequence if exists matricula_seq;
drop sequence if exists modalidade_seq;
drop sequence if exists desconto_seq;
drop sequence if exists mensalidade_seq;
drop sequence if exists matmod_seq;
drop sequence if exists pagamento_seq;
drop sequence if exists medidas_seq;
drop sequence if exists usuario_seq;

create sequence aluno_seq start 1;
create sequence matricula_seq start 1;
create sequence desconto_seq start 1;
create sequence modalidade_seq start 1;
create sequence mensalidade_seq start 1;
create sequence matmod_seq start 1;
create sequence pagamento_seq start 1;
create sequence medidas_seq start 1;
create sequence usuario_seq start 1;

create table usuario_tipo (
	id int not null,
	tipo varchar( 100 ) not null,
	primary key( id )
);

create table usuario (
	id int default nextval( 'usuario_seq' ),
	tipo_id int not null,
	nome varchar( 256 ) not null,
	nome_usuario varchar( 256 ) not null unique,
	senha varchar( 256 ) not null,	
	ativo boolean default true,
	primary key( id ),
	foreign key( tipo_id ) references usuario_tipo( id )
);

create table aluno (
    id int default nextval( 'aluno_seq' ),
    nome varchar( 256 ) unique not null,
	data_nasc timestamp,
    obs varchar( 256 ),
    matricula_corrente int default -1,
    primary key ( id )
);

create table matricula (
    id int default nextval( 'matricula_seq' ),
    aluno_id int,
    data_inicio timestamp default current_timestamp,
    data_fim timestamp,
	data_dia_pag timestamp not null,
    primary key( id ),
    foreign key ( aluno_id ) references aluno( id ) on delete cascade
);

create table desconto (
	id int default nextval( 'desconto_seq' ),
	mat_id int not null,
	porcentagem decimal( 10, 2 ) not null,
	data_alter timestamp default current_timestamp,
	primary key( id ),
	foreign key( mat_id ) references matricula( id ) on delete cascade
);

create table modalidade (
    id int default nextval( 'modalidade_seq' ),
    descricao varchar( 256 ) not null,
	data_inicio timestamp default current_timestamp,
	data_fim timestamp default null,
	valor_inicial decimal( 10, 2 ) not null,
	valor_diaria decimal( 10, 2 ) default 0,
    primary key( id )
);

create table mensalidade (
    id int default nextval( 'mensalidade_seq' ),
    mod_id int not null,
    valor decimal( 10, 2 ) not null,
    data_alter timestamp default current_timestamp,	
    primary key( id ),
    foreign key( mod_id ) references modalidade( id ) on delete cascade
);

create table matmod (
    id int default nextval( 'matmod_seq' ),
    mat_id int not null,
    mod_id int not null,
    data_contrato timestamp default current_timestamp,
	data_encerramento timestamp default null,
    primary key( id ),
    foreign key( mat_id ) references matricula( id ) on delete cascade,
    foreign key( mod_id ) references modalidade( id ) on delete cascade
);

create table pagamento (
    id int default nextval( 'pagamento_seq' ),
    mat_id int not null,
	usuario_id int not null,
    valor decimal( 10, 2 ) not null,
	desconto decimal( 10, 2 ) default 0,
    data_pag timestamp default current_timestamp,
    primary key( id ),
    foreign key( mat_id ) references matricula( id ) on delete cascade
);

create table medidas (
	id int default nextval( 'medidas_seq' ),
	mat_id int not null,
	peso int,
	altura decimal( 10, 2 ),
	braco_esquerdo int,
	antebraco_esquerdo int,
	braco_direito int,
	antebraco_direito int,
	torax int,
	cintura int,
	bumbum int,
	coxa_esquerda int,
	coxa_direita int,
	panturrilha_esquerda int,
	panturrilha_direita int,
	data_reg timestamp default current_timestamp,
	primary key( id ),
	foreign key( mat_id ) references matricula( id ) on delete cascade
);

create table config (
    pg_tolerancia int default 0,
	auto_carregar_usuarios boolean default true,	
	auto_carregar_modalidades boolean default true,
	admin_usuario_tipo_id int default 1,
	func_usuario_tipo_id int default 2,
	diaria_aluno_id int default 1,
	senha varchar
);






/* PARAMETROS - NOME DE USUARIO E SENHA*/
create or replace function salva_altera_senha( varchar, varchar ) returns void as $$
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
$$ language plpgsql;


/* PARAMETROS - NOME DE USUARIO*/
create or replace function restaura_senha( varchar ) returns void as $$
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
$$ language plpgsql;





insert into config values ( 10 );

insert into usuario_tipo( id, tipo ) values ( 1, 'administrador' );
insert into usuario_tipo( id, tipo ) values ( 2, 'funcionario' );
update config set admin_usuario_tipo_id=1;
update config set func_usuario_tipo_id=2;

insert into usuario ( tipo_id, nome, nome_usuario, senha ) values ( 1, 'Admin', 'admin', md5( 'admin' ) ); 

insert into aluno ( nome ) values ( 'Diária' );
insert into matricula ( aluno_id, data_dia_pag ) values ( currval( 'aluno_seq' ), current_timestamp );
update aluno set matricula_corrente=currval( 'matricula_seq' ) where id=currval( 'aluno_seq' );
update config set diaria_aluno_id=currval( 'aluno_seq' );
