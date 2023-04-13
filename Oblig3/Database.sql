DROP SCHEMA IF EXISTS ansattO1 CASCADE;
CREATE SCHEMA ansattO1;
SET search_path TO ansattO1;

CREATE TABLE Ansatt (
  AnsattID   SERIAL,
  Brukernavn VARCHAR(4) UNIQUE,
  Fornavn    VARCHAR(30) NOT NULL,
  Etternavn  VARCHAR(30) NOT NULL,
  AnsattDato DATE NOT NULL,
  Stilling   VARCHAR(30),
  Maanedslon INTEGER,
  Avdeling   Integer NOT NULL,
  Constraint AnsattID Primary Key (AnsattID)
);

CREATE TABLE Avdeling (
	AvdelingID SERIAL,
	Navn	   VARCHAR(30),
	Sjef_ID    Integer NOT NULL,
	Constraint Sjef_ID Foreign Key (Sjef_ID) References Ansatt(AnsattID), 
	Constraint AvdelingID Primary Key (AvdelingID)
);

CREATE TABLE Prosjekt (
	ProsjektID  SERIAL,
	Navn        VARCHAR(60),
	Beskrivelse VARCHAR(255),
	Constraint ProsjektID Primary Key (ProsjektID)
);

CREATE TABLE ProsjektDeltagelse (
	DeltagelseID SERIAL,
	StartDato  	 DATE,
	AntallTimer  Integer,
	Rolle 		 VARCHAR(30),
	Ansatt 		 Integer NOT NULL,
	Prosjekt     Integer NOT NULL,
	Constraint Ansatt Foreign Key (Ansatt) References Ansatt(AnsattID),
	Constraint Prosjekt Foreign Key (Prosjekt) References Prosjekt(ProsjektID),
	Constraint DeltagelseID Primary Key (DeltagelseID)
);

INSERT INTO Ansatt(Brukernavn, Fornavn, Etternavn, AnsattDato, Stilling, Maanedslon, Avdeling) VALUES
    ('PeAr', 'Per', 'Arneson', '2018-04-04', 'IndustriMekaniker', 47000, 1),
    ('KaBu', 'Kari', 'Bunnpris', '2013-04-04', 'Resepsjonist', 29000, 2),
	('ViMo', 'Villiam', 'Monclair', '2016-04-04', 'IKT-suport', 70000, 1),
	('ArSe', 'Arne', 'Selnes', '2020-04-04', 'KariereRådgiver', 59000, 3),
	('BeRo', 'Benjmin', 'Røiland', '2023-04-04', 'Intern', 500, 2),
	('StDa', 'Stefan', 'Dalland', '2018-04-04', 'Økonom', 100000, 2),
	('PeBu','Per', 'Bunnpris', '2013-04-04', 'Assistent', 8000, 3),
	('PåHy', 'Pål', 'Hytte', '2020-04-04', 'Mekaniker', 30000, 1),
	('WiRa', 'Will', 'Ramos', '2014-04-04', 'PersonalRådgiver', 70000, 3),
	('OlNo', 'Ola', 'Norman', '2017-04-04', 'Sveiser', 40000, 1);

INSERT INTO Avdeling(Navn, Sjef_ID) Values
	('Verksted', 3),
	('Økonomi', 6),
	('HR', 4);
	
INSERT INTO Prosjekt(Navn, Beskrivelse) Values
	('P1_April2020', 'Overhaling av Utstyr'),
	('P1_Oktober2017', 'Lage Netside For Bedriften'),
	('P2_April2020', 'Reklamering av bedrift');
	
INSERT INTO ProsjektDeltagelse(StartDato, AntallTimer, Rolle, Ansatt, Prosjekt) Values
	('05-10-2017', 100, 'ProsjektLeder', 3, 2),
	('10-04-2020', 60, 'ProsjektDeltager', 1, 1),
	('12-04-2020', 80, 'ProsjektLeder', 4, 1),
	('05-04-2020', 120, 'ProsjektLeder', 9, 3);

Alter Table Ansatt Add 
Constraint Avdeling Foreign Key (Avdeling) References Avdeling(AvdelingID);

Select * from ProsjektDeltagelse, Ansatt, Prosjekt, Avdeling Where 
ProsjektDeltagelse.Ansatt = Ansatt.AnsattID AND
ProsjektDeltagelse.Prosjekt = Prosjekt.ProsjektID AND
Ansatt.Avdeling = Avdeling.AvdelingID;