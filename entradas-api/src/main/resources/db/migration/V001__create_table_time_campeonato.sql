CREATE TABLE TIME (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	pais_enum ENUM ('AFEGANISTAO','AFRICA_DO_SUL','ALBANIA','ALEMANHA','ANDORRA','ANGOLA',
		'ANTIGA_E_BARBUDA','ARABIA_SAUDITA','ARGELIA','ARGENTINA','ARMENIA',
		'AUSTRALIA','AUSTRIA','AZERBAIJAO','BAHAMAS','BANGLADEXE','BARBADOS',
		'BAREM','BELGICA','BELIZE','BENIM','BIELORRUSSIA','BOLIVIA','BOSNIA_E_HERZEGOVINA',
		'BOTSUANA','BRASIL','BRUNEI','BULGARIA','BURQUINA_FASO','BURUNDI','BUTAO',
		'CABO_VERDE','CAMAROES','CAMBOJA','CANADA','CATAR','CAZAQUISTAO','CHADE',
		'CHILE','CHINA','CHIPRE','COLOMBIA','COMORES','CONGO_BRAZZAVILLE','COREIA_DO_NORTE',
		'COREIA_DO_SUL','COSOVO','COSTA_DO_MARFIM','COSTA_RICA','CROACIA','CUAITE','CUBA',
		'DINAMARCA','DOMINICA','EGITO','EMIRADOS_ARABES_UNIDOS','EQUADOR','ERITREIA',
		'ESLOVAQUIA','ESLOVENIA','ESPANHA','ESSUATINI','ESTADO_DA_PALESTINA','ESTADOS_UNIDOS','ESTONIA',
		'ETIOPIA','FIJI','FILIPINAS','FINLANDIA','FRANCA','GABAO','GAMBIA','GANA','GEORGIA',
		'GRANADA','GRECIA','GUATEMALA','GUIANA','GUINE','GUINE_EQUATORIAL','GUINE_BISSAU','HAITI',
		'HONDURAS','HUNGRIA','IEMEN','ILHAS_MARECHAL','INDIA','INDONESIA','IRAO','IRAQUE','IRLANDA',
		'ISLANDIA','ISRAEL','ITALIA','JAMAICA','JAPAO','JIBUTI','JORDANIA','LAUS','LESOTO','LETONIA',
		'LIBANO','LIBERIA','LIBIA','LISTENSTAINE','LITUANIA','LUXEMBURGO','MACEDONIA_DO_NORTE','MADAGASCAR',
		'MALASIA','MALAUI','MALDIVAS','MALI','MALTA','MARROCOS','MAURICIA','MAURITANIA','MEXICO',
		'MIANMAR','MICRONESIA','MOCAMBIQUE','MOLDAVIA','MONACO','MONGOLIA','MONTENEGRO','NAMIBIA',
		'NAURU','NEPAL','NICARAGUA','NIGER','NIGERIA','NORUEGA','NOVA_ZELANDIA','OMA','HOLANDA',
		'PALAU','PANAMA','PAPUA_NOVA_GUINE','PAQUISTAO','PARAGUAI','PERU','POLONIA','PORTUGAL','QUENIA',
		'QUIRGUISTAO','QUIRIBATI','INGLATERRA','IRLANDA_DO_NORTE','ESCOCIA','PAIS_GALES','REPUBLICA_CENTRO_AFRICANA','REPUBLICA_CHECA','REPUBLICA_DEMOCRATICA_DO_CONGO',
		'REPUBLICA_DOMINICANA','ROMENIA','RUANDA','RUSSIA','SALOMAO','SALVADOR','SAMOA','SANTA_LUCIA','SAO_CRISTOVAO_E_NEVES',
		'SAO_MARINHO','SAO_TOME_E_PRINCIPE','SAO_VICENTE_E_GRANADINAS','SEICHELES','SENEGAL','SERRA_LEOA','SERVIA',
		'SINGAPURA','SIRIA','SOMALIA','SRI_LANCA','SUDAO','SUDAO_DO_SUL','SUECIA','SUICA','SURINAME','TAILANDIA',
		'TAIUA','TAJIQUISTAO','TANZANIA','TIMOR_LESTE','TOGO','TONGA','TRINDADE_E_TOBAGO','TUNISIA','TURCOMENISTAO','TURQUIA',
		'TUVALU','UCRANIA','UGANDA','URUGUAI','USBEQUISTAO','VANUATU','VATICANO','VENEZUELA','VIETNAME','ZAMBIA','ZIMBABUE') NOT NULL,
	
	continente_enum ENUM ('ASIA',
		'AFRICA',
		'EUROPA',
		'AMERICA',
		'OCEANIA') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE CAMPEONATO (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	nacional BOOLEAN,
	pais_enum ENUM ('AFEGANISTAO','AFRICA_DO_SUL','ALBANIA','ALEMANHA','ANDORRA','ANGOLA',
		'ANTIGA_E_BARBUDA','ARABIA_SAUDITA','ARGELIA','ARGENTINA','ARMENIA',
		'AUSTRALIA','AUSTRIA','AZERBAIJAO','BAHAMAS','BANGLADEXE','BARBADOS',
		'BAREM','BELGICA','BELIZE','BENIM','BIELORRUSSIA','BOLIVIA','BOSNIA_E_HERZEGOVINA',
		'BOTSUANA','BRASIL','BRUNEI','BULGARIA','BURQUINA_FASO','BURUNDI','BUTAO',
		'CABO_VERDE','CAMAROES','CAMBOJA','CANADA','CATAR','CAZAQUISTAO','CHADE',
		'CHILE','CHINA','CHIPRE','COLOMBIA','COMORES','CONGO_BRAZZAVILLE','COREIA_DO_NORTE',
		'COREIA_DO_SUL','COSOVO','COSTA_DO_MARFIM','COSTA_RICA','CROACIA','CUAITE','CUBA',
		'DINAMARCA','DOMINICA','EGITO','EMIRADOS_ARABES_UNIDOS','EQUADOR','ERITREIA',
		'ESLOVAQUIA','ESLOVENIA','ESPANHA','ESSUATINI','ESTADO_DA_PALESTINA','ESTADOS_UNIDOS','ESTONIA',
		'ETIOPIA','FIJI','FILIPINAS','FINLANDIA','FRANCA','GABAO','GAMBIA','GANA','GEORGIA',
		'GRANADA','GRECIA','GUATEMALA','GUIANA','GUINE','GUINE_EQUATORIAL','GUINE_BISSAU','HAITI',
		'HONDURAS','HUNGRIA','IEMEN','ILHAS_MARECHAL','INDIA','INDONESIA','IRAO','IRAQUE','IRLANDA',
		'ISLANDIA','ISRAEL','ITALIA','JAMAICA','JAPAO','JIBUTI','JORDANIA','LAUS','LESOTO','LETONIA',
		'LIBANO','LIBERIA','LIBIA','LISTENSTAINE','LITUANIA','LUXEMBURGO','MACEDONIA_DO_NORTE','MADAGASCAR',
		'MALASIA','MALAUI','MALDIVAS','MALI','MALTA','MARROCOS','MAURICIA','MAURITANIA','MEXICO',
		'MIANMAR','MICRONESIA','MOCAMBIQUE','MOLDAVIA','MONACO','MONGOLIA','MONTENEGRO','NAMIBIA',
		'NAURU','NEPAL','NICARAGUA','NIGER','NIGERIA','NORUEGA','NOVA_ZELANDIA','OMA','HOLANDA',
		'PALAU','PANAMA','PAPUA_NOVA_GUINE','PAQUISTAO','PARAGUAI','PERU','POLONIA','PORTUGAL','QUENIA',
		'QUIRGUISTAO','QUIRIBATI','INGLATERRA','IRLANDA_DO_NORTE','ESCOCIA','PAIS_GALES','REPUBLICA_CENTRO_AFRICANA','REPUBLICA_CHECA','REPUBLICA_DEMOCRATICA_DO_CONGO',
		'REPUBLICA_DOMINICANA','ROMENIA','RUANDA','RUSSIA','SALOMAO','SALVADOR','SAMOA','SANTA_LUCIA','SAO_CRISTOVAO_E_NEVES',
		'SAO_MARINHO','SAO_TOME_E_PRINCIPE','SAO_VICENTE_E_GRANADINAS','SEICHELES','SENEGAL','SERRA_LEOA','SERVIA',
		'SINGAPURA','SIRIA','SOMALIA','SRI_LANCA','SUDAO','SUDAO_DO_SUL','SUECIA','SUICA','SURINAME','TAILANDIA',
		'TAIUA','TAJIQUISTAO','TANZANIA','TIMOR_LESTE','TOGO','TONGA','TRINDADE_E_TOBAGO','TUNISIA','TURCOMENISTAO','TURQUIA',
		'TUVALU','UCRANIA','UGANDA','URUGUAI','USBEQUISTAO','VANUATU','VATICANO','VENEZUELA','VIETNAME','ZAMBIA','ZIMBABUE'),
	
	continente_enum ENUM ('ASIA',
		'AFRICA',
		'EUROPA',
		'AMERICA',
		'OCEANIA') 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE TIME_CAMPEONATO (
	id_campeonato BIGINT NOT NULL,
	id_time BIGINT NOT NULL,
	PRIMARY KEY (id_campeonato, id_time),
	FOREIGN KEY (id_campeonato) REFERENCES CAMPEONATO(id),
	FOREIGN KEY (id_time) REFERENCES TIME(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;