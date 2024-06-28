ALTER TABLE ENTRADA ADD COLUMN time_mandante VARCHAR(250);
ALTER TABLE ENTRADA ADD COLUMN time_visitante VARCHAR(250);

update ENTRADA e set e.time_mandante = (select t.nome from TIME t where e.id_time_mandante = t.id);
update ENTRADA e set e.time_visitante = (select t.nome from TIME t where e.id_time_visitante = t.id);

ALTER TABLE ENTRADA DROP FOREIGN KEY ENTRADA_ibfk_2;
ALTER TABLE ENTRADA DROP FOREIGN KEY ENTRADA_ibfk_3;

ALTER TABLE ENTRADA MODIFY COLUMN time_mandante VARCHAR(250) NOT NULL;
ALTER TABLE ENTRADA MODIFY COLUMN time_visitante VARCHAR(250) NOT NULL;

