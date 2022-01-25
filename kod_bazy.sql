#---------------------------
#-- Usuwanie tabel, widokow i trigerow
#---------------------------

SET foreign_key_checks = 0;

DROP PROCEDURE IF EXISTS aktualizacjaIlosci;
DROP PROCEDURE IF EXISTS aktualizacjaStatusu;
DROP PROCEDURE IF EXISTS dodajZamowienie;
DROP PROCEDURE IF EXISTS dodajProduktyDoZamowienia;
DROP PROCEDURE IF EXISTS wyswietlAdres;
DROP PROCEDURE IF EXISTS aktualizujAdres;
DROP PROCEDURE IF EXISTS dodajUzytkownika;

DROP VIEW IF EXISTS WIDOK_DANYCH_KLIENTA;
DROP VIEW IF EXISTS WIDOK_LISTY_ZAMOWIEN;
DROP VIEW IF EXISTS WIDOK_LISTY_PRODUKTOW;

DROP TRIGGER IF EXISTS log_dodania_produktu_magazyn;
DROP TRIGGER IF EXISTS log_dodania_produktu_sklep;
DROP TRIGGER IF EXISTS log_zmiany_statusu_trigger;

DROP TABLE PRACOWNICY;
DROP TABLE LISTA_PROD;
DROP TABLE PRODUKTY;
DROP TABLE KATEGORIE;
DROP TABLE PRODUCENCI;
DROP TABLE MAGAZYN;
DROP TABLE SKLEP;
DROP TABLE ZAMOWIENIA;
DROP TABLE KLIENCI;
DROP TABLE ADRES;
DROP TABLE LOG_DODANIA_PRODUKTU;
DROP TABLE LOG_ZMIANY_STATUSU;
DROP TABLE DANE_OSOBOWE;
DROP TABLE LISTA_ZAM;

#---------------------------
#-- Tworzenie tabel
#---------------------------

CREATE TABLE PRACOWNICY
(    
	ID_PRACOWNIKA INT AUTO_INCREMENT,
	ID_DANYCH INT NOT NULL,
	TYP CHAR(1) NOT NULL,
	WYNAGRODZENIE NUMERIC(10) NOT NULL,
	DATA_ZAT DATETIME NOT NULL,
	CONSTRAINT id_pracownika_PK PRIMARY KEY(ID_PRACOWNIKA)
);
 
CREATE TABLE LISTA_PROD
(    
	KOD_KRESKOWY NUMERIC(16,0),
	ID_PRODUKTU INT NOT NULL,
	ID_PRACOWNIKA INT NOT NULL,
	NAZWA_PRODUKTU VARCHAR(30) NOT NULL,
	DOSTEPNOSC VARCHAR(15) NOT NULL,
	CONSTRAINT kod_kreskowy_PK PRIMARY KEY(KOD_KRESKOWY)
);

CREATE TABLE PRODUKTY
(    
	ID_PRODUKTU INT AUTO_INCREMENT,
	ID_PRODUCENTA INT NOT NULL,
	ID_KATEGORII INT NOT NULL,
	CENA NUMERIC(10,0) NOT NULL,
	CONSTRAINT id_produktu_PK PRIMARY KEY(ID_PRODUKTU)
);

CREATE TABLE KATEGORIE
(    
	ID_KATEGORII INT AUTO_INCREMENT,
	OPIS VARCHAR(30) NOT NULL,
	KATEGORIA VARCHAR(30) UNIQUE NOT NULL,
	CONSTRAINT id_kategorii_PK PRIMARY KEY(ID_KATEGORII)
);

CREATE TABLE PRODUCENCI
(    
	ID_PRODUCENTA INT AUTO_INCREMENT,
	KRAJ VARCHAR(30) NOT NULL,
	PRODUCENT VARCHAR(30) NOT NULL,
	CONSTRAINT id_producenta_PK PRIMARY KEY(ID_PRODUCENTA)
);

CREATE TABLE MAGAZYN
(    
	ID_W_MAGAZYNIE INT AUTO_INCREMENT,
	KOD_KRESKOWY NUMERIC(16,0) NOT NULL,
	ILOSC NUMERIC(3,0) NOT NULL,
	CONSTRAINT id_magazynu_PK PRIMARY KEY(ID_W_MAGAZYNIE)
);

CREATE TABLE SKLEP
(    
	ID_W_SKLEPIE INT AUTO_INCREMENT,
	KOD_KRESKOWY NUMERIC(16,0) NOT NULL,
	ILOSC NUMERIC(3,0) NOT NULL,
	CONSTRAINT id_sklepu_PK PRIMARY KEY(ID_W_SKLEPIE)
);


CREATE TABLE  LOG_DODANIA_PRODUKTU
(    
	ID_LOGU_DODANIA_PROD INT AUTO_INCREMENT,
	KOD_KRESKOWY NUMERIC(16, 0) NOT NULL,
	LOKALIZACJA CHAR(1) NOT NULL,
	DATA_ZMIANY_ILOSCI DATETIME NOT NULL,
	STARA_ILOSC NUMERIC(4,0) NOT NULL,
	NOWA_ILOSC NUMERIC(4,0) NOT NULL,
	CONSTRAINT id_logu_dodania_prod_PK PRIMARY KEY (ID_LOGU_DODANIA_PROD)
);

CREATE TABLE LOG_ZMIANY_STATUSU
(    
	ID_LOGU_STATUSU INT AUTO_INCREMENT,
	ID_ZAMOWIENIA INT NOT NULL,
	DATA_ZMIANY_STATUSU DATETIME,
	STARY_STATUS VARCHAR(30) NOT NULL,
	NOWY_STATUS VARCHAR(30) NOT NULL,
	CONSTRAINT id_logu_statusu_PK PRIMARY KEY (ID_LOGU_STATUSU)
);

CREATE TABLE ZAMOWIENIA
(    
	ID_ZAMOWIENIA INT AUTO_INCREMENT,
	ID_KLIENTA INT NOT NULL,
	STATUS VARCHAR(30) NOT NULL,
	DATA DATETIME,
	WARTOSC NUMERIC(10,0) DEFAULT 0,
	CONSTRAINT id_zamowienia_PK PRIMARY KEY (ID_ZAMOWIENIA)
);

CREATE TABLE KLIENCI
(    
	ID_KLIENTA INT AUTO_INCREMENT,
	ID_DANYCH INT NOT NULL,
	ID_ADRESU INT NOT NULL,
	RABAT NUMERIC (4,2),
	CONSTRAINT id_klienta_PK PRIMARY KEY (ID_KLIENTA)
);

CREATE TABLE ADRES
(    
	ID_ADRESU INT AUTO_INCREMENT,
	MIASTO VARCHAR(20) NOT NULL,
	ULICA VARCHAR(20) NOT NULL,
	NUMER_MIESZKANIA NUMERIC(3,0) NOT NULL,
	KOD_POCZTOWY VARCHAR(6) NOT NULL,
	CONSTRAINT id_adresu_PK PRIMARY KEY (ID_ADRESU)
);

CREATE TABLE DANE_OSOBOWE
(
	ID_DANYCH  INT AUTO_INCREMENT,
	IMIE VARCHAR(20) NOT NULL,
	NAZWISKO VARCHAR(20) NOT NULL,
	EMAIL VARCHAR(20) NOT NULL,
	NUMER_TEL VARCHAR(20) NOT NULL,
	LOGIN VARCHAR(20) NOT NULL,
	HASLO VARCHAR(20) NOT NULL,
	CONSTRAINT id_danych_PK PRIMARY KEY (ID_DANYCH)
);

CREATE TABLE LISTA_ZAM
(
	ID_LISTAZAM INT AUTO_INCREMENT,
	ID_ZAMOWIENIA INT NOT NULL,
	KOD_KRESKOWY NUMERIC(16,0) NOT NULL,
	ILOSC INT NOT NULL,
	CONSTRAINT id_listzam_PK PRIMARY KEY (ID_LISTAZAM)
);

# --------------------------------
# -- Wprowadzanie kluczy obcych
# --------------------------------

ALTER TABLE PRACOWNICY ADD CONSTRAINT id_danych_pracownika_FK 
	FOREIGN KEY(ID_DANYCH) REFERENCES DANE_OSOBOWE(ID_DANYCH);

ALTER TABLE LISTA_PROD ADD CONSTRAINT id_produktu_lista_FK 
	FOREIGN KEY(ID_PRODUKTU) REFERENCES PRODUKTY(ID_PRODUKTU);
ALTER TABLE LISTA_PROD ADD CONSTRAINT id_pracownika_lista_FK 
	FOREIGN KEY(ID_PRACOWNIKA) REFERENCES PRACOWNICY(ID_PRACOWNIKA);

ALTER TABLE PRODUKTY ADD CONSTRAINT id_producenta_produkty_FK 
	FOREIGN KEY(ID_PRODUCENTA) REFERENCES PRODUCENCI(ID_PRODUCENTA);
ALTER TABLE PRODUKTY ADD CONSTRAINT id_kategorii_produkty_FK 
	FOREIGN KEY(ID_KATEGORII) REFERENCES KATEGORIE(ID_KATEGORII);

ALTER TABLE MAGAZYN ADD CONSTRAINT kod_kreskowy_magazyn_FK 
	FOREIGN KEY(KOD_KRESKOWY) REFERENCES LISTA_PROD(KOD_KRESKOWY);

ALTER TABLE SKLEP ADD CONSTRAINT kod_kreskowy_sklep_FK 
	FOREIGN KEY(KOD_KRESKOWY) REFERENCES LISTA_PROD(KOD_KRESKOWY);

ALTER TABLE LOG_ZMIANY_STATUSU ADD CONSTRAINT id_zamowienia_log_statusu_FK 
	FOREIGN KEY(ID_ZAMOWIENIA) REFERENCES ZAMOWIENIA(ID_ZAMOWIENIA);

ALTER TABLE ZAMOWIENIA ADD CONSTRAINT id_klienta_zamowienia_FK 
	FOREIGN KEY(ID_KLIENTA) REFERENCES KLIENCI(ID_KLIENTA);

ALTER TABLE KLIENCI ADD CONSTRAINT id_danych_FK 
	FOREIGN KEY(ID_DANYCH) REFERENCES DANE_OSOBOWE(ID_DANYCH);
ALTER TABLE KLIENCI ADD CONSTRAINT id_adresu_FK 
	FOREIGN KEY(ID_ADRESU) REFERENCES ADRES(ID_ADRESU);


ALTER TABLE LISTA_ZAM ADD CONSTRAINT id_zamowienia_lista_FK 
	FOREIGN KEY(ID_ZAMOWIENIA) REFERENCES ZAMOWIENIA(ID_ZAMOWIENIA);
ALTER TABLE LISTA_ZAM ADD CONSTRAINT id_kod_kreskowy_lista_FK 
	FOREIGN KEY(KOD_KRESKOWY) REFERENCES LISTA_PROD(KOD_KRESKOWY);

# --------------------------------
# -- Wprowadzanie danych do tabel
# --------------------------------

INSERT INTO DANE_OSOBOWE (IMIE ,NAZWISKO, EMAIL, NUMER_TEL, LOGIN,HASLO)
VALUES
('Adam', 'Grzyb', 'adm@gmail.com', '658342134', 'admin', 'admin'),
('Jan', 'Nowak', 'nowaljan@gmail.com', '523049123', 'jann13', 'password'),
('Michal', 'Krajenski', 'krajan@gmail.com', '523042340', 'gryll', 'gryll'),
('Klaudia', 'Banas', 'klaudia342@gmail.com', '655432123', 'xklara', '1234'),
('Joanna', 'GryN', 'jonnn@gmail.com', '6434324321', 'xjnn', 'pswrd'),
('Daniel', 'Nowak', '123@gmail.com', '567876123', '123', '321'),
('Robert', 'Maklowicz', 'maklo@gmail.com', '567876123', '123', '321'),
('Michal', 'Chudy', 'chudy@gmail.com', '567876123', '123', '321'),
('Mateusz', 'Bywalski', 'bywalski@gmail.com', '567876123', '123', '321'),
('Jaroslaw', 'Wierzgon', 'j.wierz@gmail.com', '567876123', '123', '321');

INSERT INTO Adres (MIASTO, ULICA, NUMER_MIESZKANIA, KOD_POCZTOWY)
VALUES
('Gdansk', 'Mickiewicza', 15, 89600),
('Szczecin', 'Rejmenta', 12, 50600),
('Malbork', 'Mickiewicza', 78, 10520),
('Wroclaw', 'Wyszynskiego', 23, 89600),
('Chodziez', 'Wroclawska', 150,50300);

INSERT INTO KLIENCI (ID_DANYCH, ID_ADRESU, RABAT)
VALUES
(6,  1, 5.34),
(7,  2, 2.54),
(8,  3, 6.22),
(9,  4, 2.53),  
(10, 5, 6.91);

INSERT INTO PRACOWNICY (ID_DANYCH, TYP, WYNAGRODZENIE, DATA_ZAT)
VALUES
(1,'S',5000,str_to_date('21,5,2013','%d,%m,%Y')),
(2,'M',13000,str_to_date('21,5,2013','%d,%m,%Y')),
(3,'M',14000,str_to_date('21,5,2013','%d,%m,%Y')),
(4,'S',8000,str_to_date('21,5,2013','%d,%m,%Y')),
(5,'S',5000,str_to_date('21,5,2013','%d,%m,%Y'));

INSERT INTO PRODUCENCI (KRAJ, PRODUCENT)
VALUES
('Japonia', 'Ibanez'),
('USA', 'Marcus Miller'),
('Austria', 'AKG'),
('Indonezja', 'Kala'),
('Japonia', 'Casio');

INSERT INTO KATEGORIE (OPIS, KATEGORIA)
VALUES
('Gitara elektryczna to...', 'Gitary elektryczne'),
('Gitary basowe sa...', 'Gitary basowe'),
('Ten rodzaj sluchawek jest...', 'Sluchawki studyjne'),
('Niewatpliwie jest to...', 'Keyboardy'),
('Jest to instrument...', 'Ukulele');

INSERT INTO PRODUKTY (ID_PRODUCENTA, ID_KATEGORII, CENA)
VALUES
(1, 1, 1230),
(1, 1, 1533),
(2, 2, 5345),
(2, 2, 7443),
(3, 3, 132),
(3, 3, 231),
(4, 4, 123),
(4, 4, 234),
(5, 5, 657),
(5, 5, 890);

INSERT INTO LISTA_PROD 
(KOD_KRESKOWY, ID_PRODUKTU, ID_PRACOWNIKA, NAZWA_PRODUKTU, DOSTEPNOSC)
VALUES
(124354634243,1,5,'Ibanez AF200 BS', 'Dostepny' ),
(867594736475,2,1,'Ibanez AG75G SCG', 'Niedostepny' ),
(958674637584,3,4,'Marcus Miller P7', 'Dostepny' ),
(165748375869,4,1,'Marcus Miller V7', 'Dostepny' ),
(596879402837,5,5,'AKG K240 MKII', 'Dostepny' ),
(586749304857,6,1,'AKG K240 Studio', 'Niedostepny' ),
(586749204574,7,4,'Kala KA SWF', 'Dostepny' ),
(106846736384,8,5,'Kala KA 15 S', 'Dostepny' ),
(505854978583,9,1,'Casio CT-S100', 'Niedostepny' ),
(607845794093,10,4,'Casio CT-S300', 'Dostepny' );


INSERT INTO SKLEP (KOD_KRESKOWY, ILOSC)
VALUES
(124354634243, 4),
(867594736475, 0),
(958674637584, 10),
(165748375869, 42),
(596879402837, 3),
(586749304857, 0),
(586749204574, 12),
(106846736384, 3),
(505854978583, 0),
(607845794093, 4);

INSERT INTO MAGAZYN (KOD_KRESKOWY, ILOSC)  
VALUES
(124354634243, 1),
(867594736475, 0),
(958674637584, 4),
(165748375869, 65),
(596879402837, 6),
(586749304857, 0),
(586749204574, 43),
(106846736384, 2),
(505854978583, 0),
(607845794093, 7);


# ----------------------------------------
# -- Tworzenie triggerow do tablic logow
# ----------------------------------------

DELIMITER //
CREATE TRIGGER log_dodania_produktu_magazyn
AFTER UPDATE ON MAGAZYN FOR EACH ROW
	BEGIN
    	IF NEW.ILOSC != OLD.ILOSC THEN
        	INSERT INTO LOG_DODANIA_PRODUKTU 
				(KOD_KRESKOWY, LOKALIZACJA, DATA_ZMIANY_ILOSCI,
					STARA_ILOSC, NOWA_ILOSC)
			VALUES (OLD.KOD_KRESKOWY, 'M', SYSDATE(), OLD.ILOSC, NEW.ILOSC);
			UPDATE LISTA_PROD L SET DOSTEPNOSC = 'Dostepny'
            	WHERE OLD.KOD_KRESKOWY = L.KOD_KRESKOWY
            	AND
                	(SELECT NOWA_ILOSC
                    	FROM LOG_DODANIA_PRODUKTU
                    	WHERE ID_LOGU_DODANIA_PROD = (SELECT MAX(ID_LOGU_DODANIA_PROD)
                                                    	FROM LOG_DODANIA_PRODUKTU)) 	> 0;
        	UPDATE LISTA_PROD L SET DOSTEPNOSC = 'Niedostepny'
            	WHERE OLD.KOD_KRESKOWY = L.KOD_KRESKOWY
            	AND
                	(SELECT NOWA_ILOSC
                    	FROM LOG_DODANIA_PRODUKTU
                    	WHERE ID_LOGU_DODANIA_PROD = (SELECT MAX(ID_LOGU_DODANIA_PROD)
                                                    	FROM LOG_DODANIA_PRODUKTU)) = 0;
    	END IF;
	END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER log_dodania_produktu_sklep
AFTER UPDATE ON SKLEP FOR EACH ROW
	BEGIN
    	IF NEW.ILOSC != OLD.ILOSC THEN
        	INSERT INTO LOG_DODANIA_PRODUKTU 
				(KOD_KRESKOWY, LOKALIZACJA, DATA_ZMIANY_ILOSCI,
					STARA_ILOSC, NOWA_ILOSC)
			VALUES(OLD.KOD_KRESKOWY, 'S', SYSDATE(), OLD.ILOSC, NEW.ILOSC);
        	
			UPDATE LISTA_PROD L SET DOSTEPNOSC = 'Dostepny'
            	WHERE OLD.KOD_KRESKOWY = L.KOD_KRESKOWY
            	AND
                	(SELECT NOWA_ILOSC
                    	FROM LOG_DODANIA_PRODUKTU
                    	WHERE ID_LOGU_DODANIA_PROD = (SELECT MAX(ID_LOGU_DODANIA_PROD)
                                                    	FROM LOG_DODANIA_PRODUKTU)) > 0;
        	UPDATE LISTA_PROD L SET DOSTEPNOSC = 'Niedostepny'
            	WHERE OLD.KOD_KRESKOWY = L.KOD_KRESKOWY
            	AND
                	(SELECT NOWA_ILOSC
                    	FROM LOG_DODANIA_PRODUKTU
                    	WHERE ID_LOGU_DODANIA_PROD = (SELECT MAX(ID_LOGU_DODANIA_PROD)
                                                    	FROM LOG_DODANIA_PRODUKTU)) = 0;
    	END IF;
	END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER log_zmiany_statusu_trigger
AFTER UPDATE ON ZAMOWIENIA FOR EACH ROW
	BEGIN
    	IF NEW.STATUS != OLD.STATUS THEN
        	INSERT INTO LOG_ZMIANY_STATUSU 
				(ID_ZAMOWIENIA, DATA_ZMIANY_STATUSU,
					STARY_STATUS, NOWY_STATUS)
					VALUES(OLD.ID_ZAMOWIENIA, SYSDATE(),
						OLD.STATUS, NEW.STATUS);
    	END IF;
	END;
//
DELIMITER ;

# ----------------------------------------
# -- Tworzenie widokow
# ----------------------------------------

DELIMITER //
CREATE VIEW widok_danych_klienta AS
SELECT d.IMIE, d.NAZWISKO, d.EMAIL, d.NUMER_TEL, 
	a.MIASTO, a.ULICA, a.NUMER_MIESZKANIA
FROM DANE_OSOBOWE d
JOIN KLIENCI k
ON k.ID_DANYCH = d.ID_DANYCH
JOIN ADRES a
ON k.ID_ADRESU = a.ID_ADRESU;
//
DELIMITER ;

DELIMITER //
CREATE VIEW widok_listy_zamowien AS
SELECT d.IMIE, d.NAZWISKO, d.EMAIL, d.NUMER_TEL, a.MIASTO, 
a.ULICA, a.NUMER_MIESZKANIA, z.WARTOSC, z.STATUS, z.DATA
FROM DANE_OSOBOWE d
JOIN KLIENCI k
ON k.ID_DANYCH = d.ID_DANYCH
JOIN ADRES a
ON k.ID_ADRESU = a.ID_ADRESU
JOIN ZAMOWIENIA z
ON z.ID_KLIENTA = k.ID_KLIENTA;
//
DELIMITER ;

DELIMITER //
CREATE VIEW widok_listy_produktow AS
SELECT L.NAZWA_PRODUKTU, PR.PRODUCENT, K.KATEGORIA, P.CENA,
S.ILOSC as LSklep, M.ILOSC as LMagazyn, L.DOSTEPNOSC, L.ID_PRACOWNIKA, L.KOD_KRESKOWY
FROM LISTA_PROD L
JOIN PRODUKTY P
ON L.ID_PRODUKTU = P.ID_PRODUKTU
JOIN PRODUCENCI PR
ON PR.ID_PRODUCENTA = P.ID_PRODUCENTA
JOIN KATEGORIE K
ON K.ID_KATEGORII = P.ID_KATEGORII
JOIN SKLEP S
ON L.KOD_KRESKOWY = S.KOD_KRESKOWY
JOIN MAGAZYN M
ON L.KOD_KRESKOWY = M.KOD_KRESKOWY;
//
DELIMITER ;

# ----------------------------------------
# -- Testowanie triggerow
# ----------------------------------------

UPDATE MAGAZYN SET ILOSC = 2 WHERE ID_W_MAGAZYNIE = 1;
UPDATE MAGAZYN SET ILOSC = 255 WHERE ID_W_MAGAZYNIE = 2;
UPDATE MAGAZYN SET ILOSC = 427 WHERE ID_W_MAGAZYNIE = 3;
UPDATE SKLEP SET ILOSC = 99 WHERE ID_W_SKLEPIE = 5;
UPDATE SKLEP SET ILOSC = 853 WHERE ID_W_SKLEPIE = 2;
UPDATE SKLEP SET ILOSC = 52 WHERE ID_W_SKLEPIE = 1;

UPDATE ZAMOWIENIA SET STATUS = 'Do wysylki' WHERE id_zamowienia = 1;
UPDATE ZAMOWIENIA SET STATUS = 'Zrealizowane' WHERE id_zamowienia = 2;
UPDATE ZAMOWIENIA SET STATUS = 'Do wysylki' WHERE id_zamowienia = 3;
UPDATE ZAMOWIENIA SET STATUS = 'Anulowano' WHERE id_zamowienia = 4;
UPDATE ZAMOWIENIA SET STATUS = 'Oczekwianie na wplate' WHERE id_zamowienia = 5;

# ----------------------------------------
# -- Procedury
# ----------------------------------------

DELIMITER //
create procedure aktualizacjaIlosci (IN kod decimal(16,0), IN ilosc decimal(3,0), IN name varchar(100))
               begin
                       IF name = 'SKLEP' THEN
                        UPDATE SKLEP SET ILOSC = ilosc WHERE kod_kreskowy = kod;
                    ELSEIF name = 'MAGAZYN' THEN
                        UPDATE MAGAZYN SET ILOSC = ilosc WHERE kod_kreskowy = kod;
                    END IF;
               end //
			   
			   
DELIMITER //
create procedure aktualizacjaStatusu (IN kod decimal(1,0), IN zamowienie decimal(3,0))
               begin
                       IF kod = 0 THEN
                        UPDATE ZAMOWIENIA SET STATUS = 'Do wysylki' WHERE id_zamowienia = zamowienie;
					ELSEIF kod = 1 THEN
                        UPDATE ZAMOWIENIA SET STATUS = 'Zrealizowano' WHERE id_zamowienia = zamowienie;
					ELSEIF kod = 2 THEN	
						UPDATE ZAMOWIENIA SET STATUS = 'Anulowano' WHERE id_zamowienia = zamowienie;
					ELSEIF kod = 3 THEN
                        UPDATE ZAMOWIENIA SET STATUS = 'Oczekiwanie na wplate' WHERE id_zamowienia = zamowienie;
					ELSEIF kod = 4 THEN
                        UPDATE ZAMOWIENIA SET STATUS = 'Zarezerwowano' WHERE id_zamowienia = zamowienie;		
                    END IF;
               end //
			   
DELIMITER //
create procedure dodajZamowienie (IN idKlienta int(11), IN typZamowienia  decimal(1,0))
               begin
               	INSERT INTO zamowienia (ID_KLIENTA, DATA) VALUES (idKlienta, SYSDATE());  
               	CAll aktualizacjaStatusu(typZamowienia, 
                                        (SELECT MAX(ID_ZAMOWIENIA) 
                                         FROM zamowienia 
                                         WHERE ID_KLIENTA = idKlienta));       
               end //

DELIMITER //
create procedure dodajProduktyDoZamowienia (IN idKlienta int(11), IN kodKreskowyProduktu   NUMERIC(16,0), IN liczbaProduktow int(11), IN typZamowienia  decimal(1,0) )
               begin
                    INSERT INTO LISTA_ZAM (ID_ZAMOWIENIA,  KOD_KRESKOWY, ILOSC)
                   	VALUES
                    ((SELECT MAX(ID_ZAMOWIENIA) 
                     FROM zamowienia 
                     WHERE ID_KLIENTA = idKlienta), kodKreskowyProduktu, liczbaProduktow);
					 
					 IF typZamowienia = 4 THEN
						CALL aktualizacjaIlosci(kodKreskowyProduktu, (SELECT ILOSC 
																		FROM SKLEP
																		WHERE kod_kreskowy = kodKreskowyProduktu) - liczbaProduktow, 'SKLEP');
					 ELSEIF typZamowienia = 3 THEN
						CALL aktualizacjaIlosci(kodKreskowyProduktu, (SELECT ILOSC 
																		FROM MAGAZYN
																		WHERE kod_kreskowy = kodKreskowyProduktu) - liczbaProduktow, 'MAGAZYN');
					 END IF;
					 
					 UPDATE ZAMOWIENIA SET WARTOSC =
					(SELECT WARTOSC FROM ZAMOWIENIA WHERE ID_ZAMOWIENIA =
					(SELECT ID_ZAMOWIENIA FROM LISTA_ZAM WHERE ID_LISTAZAM =
					(SELECT MAX(ID_LISTAZAM) FROM LISTA_ZAM)))
					+   	 
					((SELECT CENA FROM PRODUKTY WHERE ID_PRODUKTU =
					(SELECT ID_PRODUKTU FROM lista_prod WHERE KOD_KRESKOWY =
					(SELECT KOD_KRESKOWY FROM lista_zam WHERE ID_LISTAZAM =  
					(SELECT MAX(ID_LISTAZAM) FROM LISTA_ZAM)))) 
					 *(SELECT ILOSC FROM LISTA_ZAM WHERE ID_LISTAZAM =  
					(SELECT MAX(ID_LISTAZAM) FROM LISTA_ZAM)))	 
					WHERE ID_ZAMOWIENIA =
					(SELECT ID_ZAMOWIENIA FROM LISTA_ZAM WHERE ID_LISTAZAM =
					(SELECT MAX(ID_LISTAZAM) FROM LISTA_ZAM));
               end //

DELIMITER //

create procedure wyswietlAdres(IN dane int(11))
            begin
               SELECT a.MIASTO, a.ULICA, a.NUMER_MIESZKANIA, a.KOD_POCZTOWY 
               FROM adres a 
                JOIN KLIENCI k
                ON a.ID_ADRESU = k.ID_ADRESU
                WHERE ID_KLIENTA = dane; 
               end //
			   
DELIMITER //
CREATE PROCEDURE aktualizujAdres(IN k varchar(20), IN m varchar(20), IN n decimal(3,0),  IN u varchar(20), in idklienta int(11)) 
    BEGIN
        UPDATE ADRES SET kod_pocztowy = k, miasto = m, numer_mieszkania = n,  ulica = u 
            WHERE ID_ADRESU = (SELECT ID_ADRESU FROM KLIENCI WHERE ID_KLIENTA = idklienta);
    END//   
	
DELIMITER //
create procedure dodajUzytkownika (IN im varchar(20), IN nazw varchar(20), IN em varchar(20), IN num varchar(20), IN log varchar(20), IN haslo varchar(20), 
                                  IN mias varchar(20), IN ul varchar(20), IN num_miesz decimal(3,0), IN kod_p varchar(6))
               begin
               	INSERT INTO dane_osobowe (IMIE, NAZWISKO, EMAIL, NUMER_TEL, LOGIN, HASLO) VALUES (im, nazw, em, num, log, haslo);
                INSERT INTO adres (MIASTO, ULICA, NUMER_MIESZKANIA, KOD_POCZTOWY) VALUES (mias, ul, num_miesz, kod_p);
               	INSERT INTO klienci (ID_DANYCH, ID_ADRESU) VALUES ((SELECT MAX(ID_DANYCH) FROM dane_osobowe), (SELECT MAX(ID_ADRESU) FROM adres));  
               
               end //

			   
			   
# -----------------------------------------			   
# -- Uzupelnienie zamowien			   
# -----------------------------------------
//
CALL dodajZamowienie(1, 3);
//
CALL dodajProduktyDoZamowienia(1, 124354634243, 5, 3);
//
CALL dodajProduktyDoZamowienia(1, 958674637584, 2, 3);
//
CALL dodajZamowienie(2, 3);
//
CALL dodajProduktyDoZamowienia(2, 958674637584, 2, 3);
//
CALL dodajZamowienie(3, 3);
//
CALL dodajProduktyDoZamowienia(3, 607845794093, 1, 3);
//
CALL dodajZamowienie(4, 4);
//
CALL dodajProduktyDoZamowienia(4, 586749204574, 6, 4);
//
CALL dodajZamowienie(5, 4);
//
CALL dodajProduktyDoZamowienia(5, 165748375869, 2, 4);
//
CALL dodajProduktyDoZamowienia(5, 586749204574, 1, 4);
	   

