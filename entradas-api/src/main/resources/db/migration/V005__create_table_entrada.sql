CREATE TABLE ENTRADA (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_metodo BIGINT NOT NULL,
    id_time_mandante BIGINT NOT NULL,
    id_time_visitante BIGINT NOT NULL,
    id_campeonato BIGINT NOT NULL,
    valor DECIMAL(19,2) NOT NULL,
    lucro_prejuizo DECIMAL(19,2) NOT NULL,
    data DATETIME NOT NULL,
    comentario VARCHAR(250),
    resultado_hora_entrada_enum ENUM ('EMPATE', 'SUPERFAVORITO_VENCENDO','FAVORITO_VENCENDO','NAO_FAVORITO_VENCENDO','PARELHO_VENCENDO','ZEBRA_VENCENDO') ,
    odd DECIMAL(6,2),
    
    FOREIGN KEY (id_metodo) REFERENCES METODO(id),
    FOREIGN KEY (id_time_mandante) REFERENCES TIME(id),
    FOREIGN KEY (id_time_visitante) REFERENCES TIME(id),
    FOREIGN KEY (id_campeonato) REFERENCES CAMPEONATO(id)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;