CREATE DATABASE empresa;
\c empresa;

CREATE TABLE empreses(
	idempresa SERIAL PRIMARY KEY,
	nomempresa text NOT NULL, 
	cif int,
	seus text[]
);

CREATE TABLE seus(
idseu SERIAL PRIMARY KEY,
nomseu text NOT NULL,
ciutat text,
telefon int,
id_empresa int,
FOREIGN KEY (id_empresa) REFERENCES empreses (idempresa)
);

ALTER SEQUENCE empreses_idempresa_seq RESTART WITH 1;

INSERT INTO empreses VALUES('1','empresa1', '342333', '{"e1_seu1","e1_seu2","e1_seu3"}');
INSERT INTO empreses VALUES('2','empresa2', '342311233','{"e2_seu1","e2_seu2","e2_seu3"}');
INSERT INTO empreses VALUES('3','empresa3', '33113','{"e3_seu1","e3_seu2","e3_seu3"}');

INSERT INTO seus VALUES ('1','e1_seu1','ciutat1', '676544','1');
INSERT INTO seus VALUES ('2','e1_seu2','ciutat2', '676544','1');
INSERT INTO seus VALUES ('3','e1_seu3','ciutat3', '676544','1');
INSERT INTO seus VALUES ('4','e2_seu1','ciutat1', '676544','2');
INSERT INTO seus VALUES ('5','e2_seu2','ciutat2', '676544','2');
INSERT INTO seus VALUES ('6','e2_seu3','ciutat3', '676544','2');
INSERT INTO seus VALUES ('7','e3_seu1','ciutat1', '676544','3');
INSERT INTO seus VALUES ('8','e3_seu2','ciutat2', '676544','3');
INSERT INTO seus VALUES ('9','e3_seu3','ciutat3', '676544','3');
