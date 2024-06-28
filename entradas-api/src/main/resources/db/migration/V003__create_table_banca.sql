CREATE TABLE BANCA (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100)  NOT NULL,
    id_user BIGINT  NOT NULL,
    valor DECIMAL(19,2) NOT NULL,
    
    FOREIGN KEY (id_user) REFERENCES USER(id)    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;