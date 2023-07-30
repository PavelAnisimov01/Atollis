CREATE TABLE ADMINISTRATIVE_UNITS
(
    ID SERIAL PRIMARY KEY,
    NAME VARCHAR(50) NOT NULL,
    PARENT_ID INTEGER REFERENCES ADMINISTRATIVE_UNITS(ID),
    POPULATION INTEGER,
    AREA REAL
);
INSERT INTO ADMINISTRATIVE_UNITS (NAME,PARENT_ID,POPULATION,AREA)
VALUES ('RUSSIA',NULL,144500000,17098242),
       ('Moscow region',1,7500000,4432.2),
       ('Moscow',1,12615882,2561),
       ('Saint Petersburg',1,5351935,1439.4),
       ('Podolsk',2,187961,35.45),
       ('Kolomna',2,147698,49.12),
       ('Leningrad region',1,1826000,83944.2),
       ('Viborg',7,79827,100.01),
       ('Kirovsk',7,27866,54.51),
       ('Murino',7,6583,5.25),
       ('Voronezh region',1,2355000,52890.3),
       ('Voronezh',11,1054533,681.11),
       ('Kalach',11,23248,12.92),
       ('Liski',11,52698,15.95),
       ('New Usman',11,18731,14.58),
       ('Lipetsk region',1,1126263,24100),
       ('Lipetsk',16,490428,330),
       ('Elec',16,100000,71),
       ('Krasnodarskiy kray',1, 5819345, 76000),
       ('Krasnodar',19,974319,841)