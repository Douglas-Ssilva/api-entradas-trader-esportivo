set foreign_key_checks = 0;

lock tables CAMPEONATO write, TIME write, TIME_CAMPEONATO write, USER write, BANCA write, METODO write, ENTRADA write, APORTE_SAQUE write; 

delete from CAMPEONATO;
delete from TIME;
delete from TIME_CAMPEONATO;
delete from USER;
delete from BANCA;
delete from METODO;
delete from ENTRADA;
delete from APORTE_SAQUE;

set foreign_key_checks = 1;

alter table CAMPEONATO auto_increment = 1;
alter table TIME auto_increment = 1;
alter table USER auto_increment = 1;
alter table BANCA auto_increment = 1;
alter table METODO auto_increment = 1;
alter table ENTRADA auto_increment = 1;
alter table APORTE_SAQUE auto_increment = 1;


insert into CAMPEONATO (id, nome, pais_enum, continente_enum, total_times, data_atualizacao, ativo)  values (1, 'Premier League', 'INGLATERRA', 'EUROPA', 20, utc_timestamp, 1);
insert into CAMPEONATO (id, nome, pais_enum, continente_enum, total_times, data_atualizacao, ativo)  values (2, 'Bundesliga', 'ALEMANHA', 'EUROPA', 18, utc_timestamp, 0);
insert into CAMPEONATO (id, nome, pais_enum, continente_enum, total_times, data_atualizacao, ativo)  values (3, 'Eredivisie', 'PAISES_BAIXOS', 'EUROPA', 18, utc_timestamp, 0);
insert into CAMPEONATO (id, nome, data_atualizacao)  values (4, 'Amistoso', utc_timestamp);
insert into CAMPEONATO (id, nome, data_atualizacao, pais_enum, continente_enum)  values (5, 'Brasileirão Série A', utc_timestamp, 'BRASIL', 'AMERICA');
insert into CAMPEONATO (id, nome, data_atualizacao, pais_enum, continente_enum)  values (6, 'La Liga', utc_timestamp, 'ESPANHA', 'EUROPA');
insert into CAMPEONATO (id, nome, data_atualizacao, pais_enum, continente_enum)  values (7, 'Série A', utc_timestamp, 'ITALIA', 'EUROPA');
insert into CAMPEONATO (id, nome, data_atualizacao, pais_enum, continente_enum)  values (8, 'Primeira Liga', utc_timestamp, 'PORTUGAL', 'EUROPA');
insert into CAMPEONATO (id, nome, data_atualizacao, pais_enum, continente_enum)  values (9, 'Pro League', utc_timestamp, 'BELGICA', 'EUROPA');
insert into CAMPEONATO (id, nome, data_atualizacao, continente_enum)  values (10, 'Europa League', utc_timestamp, 'EUROPA');
insert into CAMPEONATO (id, nome, data_atualizacao, continente_enum)  values (11, 'Conferece League', utc_timestamp, 'EUROPA');
insert into CAMPEONATO (id, nome, data_atualizacao, pais_enum, continente_enum)  values (12, 'Brasileirão Série B', utc_timestamp, 'BRASIL', 'AMERICA');
insert into CAMPEONATO (id, nome, pais_enum, continente_enum, total_times, data_atualizacao, ativo)  values (13, 'Bundesliga 2', 'ALEMANHA', 'EUROPA', 18, utc_timestamp, 0);
insert into CAMPEONATO (id, nome, data_atualizacao, pais_enum, continente_enum)  values (14, 'La Liga 2', utc_timestamp, 'ESPANHA', 'EUROPA');
insert into CAMPEONATO (id, nome, data_atualizacao, pais_enum, continente_enum)  values (15, 'Super Lig', utc_timestamp, 'TURQUIA', 'EUROPA');
insert into CAMPEONATO (id, nome, data_atualizacao)  values (16, 'Mundial de Clubes', utc_timestamp);
insert into CAMPEONATO (id, nome, data_atualizacao, pais_enum, continente_enum)  values (17, 'Copa do Brasil', utc_timestamp, 'BRASIL', 'AMERICA');
insert into CAMPEONATO (id, nome, data_atualizacao, pais_enum, continente_enum)  values (18, 'Pokal', utc_timestamp, 'ALEMANHA', 'EUROPA');
insert into CAMPEONATO (id, nome, data_atualizacao, pais_enum)  values (19, 'FA Cup', utc_timestamp, 'INGLATERRA');

#Holanda
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (1, 'Ajax', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (2, 'Feyenoord', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (3, 'AZ Alkmaar', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (4, 'PSV Eindhoven', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (5, 'FC Twente', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (6, 'Sparta Rotterdam', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (7, 'FC Utrecht', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (8, 'RKC Waalwijk', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (9, 'NEC Nijmegen', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (10, 'Heerenveen', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (11, 'Fortuna Sittard', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (12, 'Go Ahead Eagles', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (13, 'Vitesse', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (14, 'FC Volendam', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (15, 'FC Emmen', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (16, 'Excelsior', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (17, 'FC Groningen', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (18, 'SC Cambuur', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);

#Reino Unido
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (19, 'Arsenal', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (20, 'Manchester City', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (21, 'Manchester United', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (22, 'Tottenham', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (23, 'Newcastle', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (24, 'Liverpool', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (25, 'Brighton & Hove Albion', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (26, 'Fulham', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (27, 'Brentford', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (28, 'Chelsea', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (29, 'Aston Villa', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (30, 'Crystal Palace', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (31, 'Wolverhampton', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (32, 'Nottingham Forest', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (33, 'Everton', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (34, 'Leicester City', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (35, 'West Ham', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (36, 'Bournemouth', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (37, 'Leeds United', 'INGLATERRA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (38, 'Southampton', 'INGLATERRA', 'EUROPA', utc_timestamp);
                                                      
#Alemanha                                             
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (39, 'Augsburg', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (40, 'Bayer Leverkusen', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (41, 'Bayern de Munique', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (42, 'Bochum', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (43, 'Borussia Dortmund', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (44, 'Borussia M\'gladbach', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (45, 'Eintracht Frankfurt', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (46, 'Freiburg', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (47, 'Hertha Berlin', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (48, 'Hoffenheim', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (49, 'Köln', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (50, 'Mainz 05', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (51, 'RB Leipzig', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (52, 'Schalke 04', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (53, 'Stuttgart', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (54, 'Union Berlin', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (55, 'Werder Bremen', 'ALEMANHA', 'EUROPA', utc_timestamp);
insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (56, 'Wolfsburg', 'ALEMANHA', 'EUROPA', utc_timestamp);

insert into TIME (id, nome, pais_enum, continente_enum, data_atualizacao)  values (57, 'Zwolle', 'PAISES_BAIXOS', 'EUROPA', utc_timestamp);

insert into  TIME_CAMPEONATO (id_campeonato, id_time) VALUES (1, 19), (1, 20), (1, 21), (1, 22), (1, 23), (1, 24), (1, 25), (1, 26), (1, 27), (1, 28), (1, 29), (1, 30), (1, 31), (1, 32), (1, 33), (1, 34), (1, 35), (1, 36), (1, 37), (1, 38);
insert into  TIME_CAMPEONATO (id_campeonato, id_time) VALUES (2, 39), (2, 40), (2, 41), (2, 42), (2, 43), (2, 44), (2, 45), (2, 46), (2, 47), (2, 48), (2, 49), (2, 50), (2, 51), (2, 52), (2, 53), (2, 54), (2, 55), (2, 56);
insert into  TIME_CAMPEONATO (id_campeonato, id_time) VALUES (3, 1), (3, 2), (3, 3), (3, 4), (3, 5), (3, 6), (3, 7), (3, 8), (3, 9), (3, 10), (3, 11), (3, 12), (3, 13), (3, 14), (3, 15), (3, 16), (3, 17), (3, 18);


#https://bcrypt-generator.com/
insert into USER (id, nome, email, senha) values 
	(1, 'Douglas', 'douglas_d1994@hotmail.com', '$2a$12$9Kbsflc0AFEVY31FE/XYBOJ2I076zBehg96aeg9dOIEqOtfMDnDZ.'), 
	(2, 'Inez', 'iaugustbarros@gmail.com', '$2a$12$9Kbsflc0AFEVY31FE/XYBOJ2I076zBehg96aeg9dOIEqOtfMDnDZ.'), 
	(3, 'Geraldo', 'geraldo@gmail.com', '$2a$12$9Kbsflc0AFEVY31FE/XYBOJ2I076zBehg96aeg9dOIEqOtfMDnDZ.'), 
	(4, 'Nath', 'nathaliaamador27@gmail.com', '$2a$12$9Kbsflc0AFEVY31FE/XYBOJ2I076zBehg96aeg9dOIEqOtfMDnDZ.'),
	(5, 'Igor', 'igor-dono-restaurante@gmail.com', '$2a$12$9Kbsflc0AFEVY31FE/XYBOJ2I076zBehg96aeg9dOIEqOtfMDnDZ.');
	
insert into BANCA (id, nome, id_user, valor, principal) values
	(1, 'Betfair', 2, 10000.0, 0), 
	(2, 'Principal', 2, 100.0, 1), 
	(3, 'Principal', 1, 10000.0, 1), 
	(4, 'Método ciclos', 1, 500.0, 0);
	
insert into METODO (id, nome, stake_default, id_banca, exigir_preenchimento_flag_mandante_visitante) values
	(1, 'Back Favorito', 1000, 3, 1), 
	(2, 'Lay pior em campo', 200, 3, 0), 
	(3, 'Vovô', 100, 1, 1), 
	(4, 'Over limite', 50, 1, 0);
	
insert into ENTRADA (id, id_metodo, id_time_mandante, id_time_visitante, id_campeonato, valor, lucro_prejuizo, data, aposta_favor_mandante, aposta_favor_visitante) values
	(1, 1, 56, 44, 2, 100, -200, '2024-03-01 00:00:00', 1, NULL),
	(2, 2, 54, 45, 2, 200, -50, '2024-03-05 00:00:00', 1, NULL),
	(3, 1, 53, 49, 2, 100, 70, '2024-03-10 00:00:00', NULL, 1),
	(4, 2, 52, 55, 2, 300, -50, '2024-02-20 00:00:00', NULL, 1),
	(5, 1, 41, 44, 2, 100, 300, '2024-02-01 00:00:00', 1, NULL),
	(6, 1, 44, 53, 2, 100, -40, '2024-02-01 00:00:00', 1, NULL),
	(7, 1, 43, 44, 2, 100, 200, '2024-03-20 00:00:00', 1, NULL),
	(8, 1, 19, 20, 1, 100, 170, '2024-04-20 00:00:00', 1, NULL);

unlock tables;