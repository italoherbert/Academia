
create or replace function fib( int ) returns int as $$
begin
	if $1 = 1 or $1 = 2 then
		return 1;
	else return fib($1-1) + fib( $1-2 );
	end if;
end;
$$ language plpgsql;

create or replace function fibs( int ) returns setof int as $$
declare 
	ant1 int;
	ant2 int;
	soma int;
	i int;
begin
	ant1 := 0;
	ant2 := 1;
	for i in 1..$1 loop
		return next ant2;
		
		soma := ant1 + ant2;
		ant1 := ant2;
		ant2 := soma;		
	end loop;
	return;
end;
$$ language plpgsql;