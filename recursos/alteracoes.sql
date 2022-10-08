

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




alter table config add admin_usuario_tipo_id int;
alter table config add func_usuario_tipo_id int;
alter table config add diaria_aluno_id int;

alter table config alter admin_usuario_tipo_id set default 1;
alter table config alter func_usuario_tipo_id set default 2;
alter table config alter diaria_aluno_id set default 1;

update config set admin_usuario_tipo_id=1;
update config set func_usuario_tipo_id=2;
update config set diaria_aluno_id=44;

alter table config alter admin_usuario_tipo_id set not null;
alter table config alter func_usuario_tipo_id set not null;
alter table config alter diaria_aluno_id set not null;

alter table config add senha varchar;