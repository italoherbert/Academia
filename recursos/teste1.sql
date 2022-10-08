
create or replace function teste1() returns void as $$
declare data_mascara varchar := 'yyyy-mm-dd';
		
		aluno_id int;
		mat_id int;

		nome varchar := 'Italo Herbert';		
		obs varchar := 'Eu!';			
		data_nasc timestamp := date '1988-10-22';
		data_mat timestamp := date '2017-07-25';				
		data_dia_pag timestamp := date '2017-07-25';
		
		descontos float[] := '{ 10, 5, 15, 0, 3 }';
		descontos_dinicio varchar[] := '{ 2017-07-01, 2017-07-15, 2017-08-08, 2017-09-10, 2017-10-10 }';
		descontos_len int := 5;
		
		modalidades varchar[] := '{ Musculacao, Pilates, Aerobica, Piscina }';
		modalidades_dinicio varchar[] := '{ 2017-01-03, 2017-01-03, 2017-01-02, 2017-02-04 }';
		modalidades_dfim varchar[] := '{ 2017-11-10, null, null, null }'; 
		modalidades_len	int := 4;			
		
		mensalidade_mod_ids int[] := '{ 1, 2, 3, 4, 1, 2, 1, 3 }';
		mensalidade_valores float[] := '{ 20, 10, 15, 30, 40, 20, 50, 30 }';
		mensalidade_data_alters varchar[] := '{ 2017-04-01, 2017-04-01, 2017-04-01, 2017-04-01,
												2017-08-02, 2017-08-02, 
												2017-09-20, 2017-09-20 }';
		mensalidade_mod_len int := 8;
		
		matmod_ids int[] := '{1, 3, 3}';
		matmod_contrato_datas varchar[] := '{ 2017-07-25, 2017-08-03, 2017-09-30 }';
		matmod_encerramento_datas varchar[] := '{ 2017-11-10, 2017-09-18, null }';
		matmod_len int := 3;				
				
		tolerancia_pag int := 10;

		i int;
begin				
	-- INSERE CONFIG E MODALIDADES
	
	insert into config ( pg_tolerancia ) values ( tolerancia_pag );
	
	for i in 1..modalidades_len loop		
		insert into modalidade ( descricao, data_inicio, data_fim, valor_inicial ) values ( 
			modalidades[ i ], 
			to_timestamp( modalidades_dinicio[ i ], data_mascara ),
			to_timestamp( modalidades_dfim[ i ], data_mascara ),
			50
		);
	end loop;
	
	-- INSERE MENSALIDADE PARA MODALIDADADE
	
	for i in 1..mensalidade_mod_len loop
		insert into mensalidade ( mod_id, valor, data_alter ) values ( 
			mensalidade_mod_ids[i],
			mensalidade_valores[i],
			to_timestamp( mensalidade_data_alters[i], data_mascara )		
		);
	end loop;
	
			
	-- INSERE ALUNO E MATRICULA
				
	aluno_id := nextval( 'aluno_seq' );
	mat_id := nextval( 'matricula_seq' );
	
	insert into aluno ( id, matricula_corrente, nome, data_nasc ,obs ) values ( 
		aluno_id, mat_id, nome, data_nasc, obs 
	);
			
	insert into matricula ( id, aluno_id, data_inicio, data_dia_pag ) values ( mat_id, aluno_id, data_mat, data_dia_pag );

	for i in 1..descontos_len loop
		insert into desconto ( mat_id, porcentagem, data_alter ) values (
			mat_id, 
			descontos[ i ], 
			to_timestamp( descontos_dinicio[ i ], data_mascara )
		);
	end loop;
	
	-- ASSOCIA MODALIDADES AO ALUNO
		
	for i in 1..matmod_len loop 	
		insert into matmod ( mat_id, mod_id, data_contrato, data_encerramento ) values ( 
			mat_id, 
			matmod_ids[i], 
			to_timestamp( matmod_contrato_datas[i], data_mascara ),
			to_timestamp( matmod_encerramento_datas[i], data_mascara )
		);		
	end loop;				
	
	-- INSERE MEDIDAS DO ALUNO
	
	insert into medidas ( mat_id, data_reg, peso, altura, 
			braco_esquerdo, braco_direito, 
			antebraco_esquerdo, antebraco_direito,
			torax, cintura, bumbum, 
			coxa_esquerda, coxa_direita, 
			panturrilha_esquerda, panturrilha_direita ) values 
		( mat_id, date '2017-06-10', 72, 1.75, 28, 29, 26, 25, 80, 70, 90, 53, 53, 32, 32 ), 
		( mat_id, date '2017,09-11', 70, 1.75, 30, 30, 27, 27, 85, 68, 92, 54, 54, 33, 33 );

	
end; 

$$ language plpgsql;

\i script.sql
select teste1();