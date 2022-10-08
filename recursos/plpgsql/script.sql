
-- SALVA ALUNO

create or replace function salva_aluno( varchar, int, int, float, varchar ) returns void as $$
declare
	aid int;
begin	
	select id into aid from aluno where lower(nome)=lower($1) limit 1;
	if found then 		
		update aluno set idade=$2, peso=$3, altura=$4, obs=$5 where id=aid;	
	else
		insert into aluno ( nome, idade, peso, altura, obs ) values ( $1, $2, $3, $4, $5 );		
	end if;	

	return;
end;
$$ language plpgsql;



-- BUSCA TODOS OS ALUNOS

--create or replace function busca_todos_alunos() returns refcursor

create or replace function busca_todos_alunos2() returns setof aluno as $$
declare a aluno;
begin
	for a in select * from aluno loop
		return next a;
	end loop;
	
	return;
end;
$$ language plpgsql;

-- BUSCA ALUNO POR ID

create or replace function busca_aluno( in pid int, out pa aluno ) returns aluno as $$
begin
	select * into pa from aluno where id=pid limit 1;	
	
	return;
end;
$$ language plpgsql;



create or replace function busca_nomes() returns setof varchar as $$
declare 
	na varchar;
	c1 refcursor;
begin
	open c1 for select nome from aluno;

	loop
		fetch c1 into na;
		exit when not found;
		
		return next na;
	end loop;
	
	close c1;
		
	return;
end;
$$ language plpgsql;














-- TESTE COM CURSOR
/*
drop table if exists teste;

create table teste( col text );
insert into teste values ( 'abc' );

create or replace function func( refcursor ) returns refcursor as $$
begin
	open $1 for select col from teste;
	return $1;
end;
$$ language plpgsql;


begin;
	select func( 'rc' );
	
	fetch all from rc;		
commit;
*/