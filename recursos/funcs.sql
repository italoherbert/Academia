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