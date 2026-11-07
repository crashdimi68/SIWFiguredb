-- =====================================================================
-- Dati di esempio per FigureDB (versione semplificata).
--
-- Gli INSERT sono idempotenti (WHERE NOT EXISTS): lo script gira a ogni
-- avvio (spring.sql.init.mode=always) senza duplicare nulla.
-- Le password sono cifrate con BCrypt: admin/admin, mario/user, giulia/user.
-- =====================================================================

-- ---------------------------------------------------------------------
-- UTENTI E CREDENZIALI
-- ---------------------------------------------------------------------
INSERT INTO users (id, nome, cognome, email)
SELECT nextval('users_seq'), 'Damiano', 'Nanni', 'admin@figuredb.it'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@figuredb.it');

INSERT INTO users (id, nome, cognome, email)
SELECT nextval('users_seq'), 'Mario', 'Rossi', 'mario.rossi@example.com'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'mario.rossi@example.com');

INSERT INTO users (id, nome, cognome, email)
SELECT nextval('users_seq'), 'Giulia', 'Bianchi', 'giulia.bianchi@example.com'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'giulia.bianchi@example.com');

INSERT INTO credentials (id, username, password, role, user_id)
SELECT nextval('credentials_seq'), 'admin',
       '$2a$10$pI75kGlOTCU5MnioO.honu.5y3Rzbt74vKI19AhF/XJHXVKZrBEEy', 'ADMIN', id
FROM users WHERE email = 'admin@figuredb.it'
AND NOT EXISTS (SELECT 1 FROM credentials WHERE username = 'admin');

INSERT INTO credentials (id, username, password, role, user_id)
SELECT nextval('credentials_seq'), 'mario',
       '$2a$10$YU5zqVmDBzb/o87vI9R2PuMaYitIegCY1vh/Ma6p9YPGWlvO0hZCG', 'DEFAULT', id
FROM users WHERE email = 'mario.rossi@example.com'
AND NOT EXISTS (SELECT 1 FROM credentials WHERE username = 'mario');

INSERT INTO credentials (id, username, password, role, user_id)
SELECT nextval('credentials_seq'), 'giulia',
       '$2a$10$YU5zqVmDBzb/o87vI9R2PuMaYitIegCY1vh/Ma6p9YPGWlvO0hZCG', 'DEFAULT', id
FROM users WHERE email = 'giulia.bianchi@example.com'
AND NOT EXISTS (SELECT 1 FROM credentials WHERE username = 'giulia');

-- ---------------------------------------------------------------------
-- SERIE E PERSONAGGI
-- ---------------------------------------------------------------------
INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'Spider-Man', 'FUMETTO',
       'Le storie dell''Uomo Ragno pubblicate da Marvel Comics a partire dal 1962.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'Spider-Man');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Peter Parker', 'Personaggio della serie Spider-Man', s.id
FROM serie s WHERE s.nome = 'Spider-Man'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Peter Parker' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Miles Morales', 'Personaggio della serie Spider-Man', s.id
FROM serie s WHERE s.nome = 'Spider-Man'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Miles Morales' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Gwen Stacy', 'Personaggio della serie Spider-Man', s.id
FROM serie s WHERE s.nome = 'Spider-Man'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Gwen Stacy' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Venom', 'Personaggio della serie Spider-Man', s.id
FROM serie s WHERE s.nome = 'Spider-Man'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Venom' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'Jujutsu Kaisen', 'MANGA',
       'Stregoni che combattono le maledizioni nate dalle emozioni negative degli esseri umani.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'Jujutsu Kaisen');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Yuji Itadori', 'Personaggio della serie Jujutsu Kaisen', s.id
FROM serie s WHERE s.nome = 'Jujutsu Kaisen'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Yuji Itadori' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Megumi Fushiguro', 'Personaggio della serie Jujutsu Kaisen', s.id
FROM serie s WHERE s.nome = 'Jujutsu Kaisen'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Megumi Fushiguro' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Nobara Kugisaki', 'Personaggio della serie Jujutsu Kaisen', s.id
FROM serie s WHERE s.nome = 'Jujutsu Kaisen'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Nobara Kugisaki' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Satoru Gojo', 'Personaggio della serie Jujutsu Kaisen', s.id
FROM serie s WHERE s.nome = 'Jujutsu Kaisen'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Satoru Gojo' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Ryomen Sukuna', 'Personaggio della serie Jujutsu Kaisen', s.id
FROM serie s WHERE s.nome = 'Jujutsu Kaisen'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Ryomen Sukuna' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'Hatsune Miku', 'VIDEOGIOCO',
       'La cantante virtuale Vocaloid protagonista della serie di giochi ritmici Project DIVA.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'Hatsune Miku');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Hatsune Miku', 'Personaggio della serie Hatsune Miku', s.id
FROM serie s WHERE s.nome = 'Hatsune Miku'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Hatsune Miku' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Kagamine Rin', 'Personaggio della serie Hatsune Miku', s.id
FROM serie s WHERE s.nome = 'Hatsune Miku'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Kagamine Rin' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Kagamine Len', 'Personaggio della serie Hatsune Miku', s.id
FROM serie s WHERE s.nome = 'Hatsune Miku'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Kagamine Len' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Megurine Luka', 'Personaggio della serie Hatsune Miku', s.id
FROM serie s WHERE s.nome = 'Hatsune Miku'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Megurine Luka' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'Super Mario Brothers', 'VIDEOGIOCO',
       'La serie di platform Nintendo ambientata nel Regno dei Funghi.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'Super Mario Brothers');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Mario', 'Personaggio della serie Super Mario Brothers', s.id
FROM serie s WHERE s.nome = 'Super Mario Brothers'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Mario' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Luigi', 'Personaggio della serie Super Mario Brothers', s.id
FROM serie s WHERE s.nome = 'Super Mario Brothers'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Luigi' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Principessa Peach', 'Personaggio della serie Super Mario Brothers', s.id
FROM serie s WHERE s.nome = 'Super Mario Brothers'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Principessa Peach' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Bowser', 'Personaggio della serie Super Mario Brothers', s.id
FROM serie s WHERE s.nome = 'Super Mario Brothers'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Bowser' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Yoshi', 'Personaggio della serie Super Mario Brothers', s.id
FROM serie s WHERE s.nome = 'Super Mario Brothers'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Yoshi' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'Cyberpunk Edge Runners', 'ANIME',
       'Serie animata ambientata a Night City tratta dal videogioco Cyberpunk 2077.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'Cyberpunk Edge Runners');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'David Martinez', 'Personaggio della serie Cyberpunk Edge Runners', s.id
FROM serie s WHERE s.nome = 'Cyberpunk Edge Runners'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'David Martinez' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Lucy', 'Personaggio della serie Cyberpunk Edge Runners', s.id
FROM serie s WHERE s.nome = 'Cyberpunk Edge Runners'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Lucy' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Rebecca', 'Personaggio della serie Cyberpunk Edge Runners', s.id
FROM serie s WHERE s.nome = 'Cyberpunk Edge Runners'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Rebecca' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Maine', 'Personaggio della serie Cyberpunk Edge Runners', s.id
FROM serie s WHERE s.nome = 'Cyberpunk Edge Runners'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Maine' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'Dragon Ball', 'MANGA',
       'Le avventure di Goku e dei guerrieri Saiyan alla ricerca delle sfere del drago.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'Dragon Ball');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Goku', 'Personaggio della serie Dragon Ball', s.id
FROM serie s WHERE s.nome = 'Dragon Ball'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Goku' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Vegeta', 'Personaggio della serie Dragon Ball', s.id
FROM serie s WHERE s.nome = 'Dragon Ball'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Vegeta' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Gohan', 'Personaggio della serie Dragon Ball', s.id
FROM serie s WHERE s.nome = 'Dragon Ball'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Gohan' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Piccolo', 'Personaggio della serie Dragon Ball', s.id
FROM serie s WHERE s.nome = 'Dragon Ball'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Piccolo' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Freezer', 'Personaggio della serie Dragon Ball', s.id
FROM serie s WHERE s.nome = 'Dragon Ball'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Freezer' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'Street Fighter', 'VIDEOGIOCO',
       'Il picchiaduro Capcom che ha definito il genere dei giochi di combattimento.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'Street Fighter');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Ryu', 'Personaggio della serie Street Fighter', s.id
FROM serie s WHERE s.nome = 'Street Fighter'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Ryu' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Ken Masters', 'Personaggio della serie Street Fighter', s.id
FROM serie s WHERE s.nome = 'Street Fighter'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Ken Masters' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Chun-Li', 'Personaggio della serie Street Fighter', s.id
FROM serie s WHERE s.nome = 'Street Fighter'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Chun-Li' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Akuma', 'Personaggio della serie Street Fighter', s.id
FROM serie s WHERE s.nome = 'Street Fighter'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Akuma' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'Fate: Stay Night', 'ANIME',
       'Visual novel in cui sette maghi evocano eroi del passato per la Guerra del Sacro Graal.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'Fate: Stay Night');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Saber', 'Personaggio della serie Fate: Stay Night', s.id
FROM serie s WHERE s.nome = 'Fate: Stay Night'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Saber' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Shirou Emiya', 'Personaggio della serie Fate: Stay Night', s.id
FROM serie s WHERE s.nome = 'Fate: Stay Night'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Shirou Emiya' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Rin Tohsaka', 'Personaggio della serie Fate: Stay Night', s.id
FROM serie s WHERE s.nome = 'Fate: Stay Night'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Rin Tohsaka' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Archer', 'Personaggio della serie Fate: Stay Night', s.id
FROM serie s WHERE s.nome = 'Fate: Stay Night'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Archer' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'My Dress-Up-Darling', 'MANGA',
       'Un artigiano di bambole hina aiuta una compagna di classe a realizzare i suoi costumi cosplay.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'My Dress-Up-Darling');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Marin Kitagawa', 'Personaggio della serie My Dress-Up-Darling', s.id
FROM serie s WHERE s.nome = 'My Dress-Up-Darling'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Marin Kitagawa' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Wakana Gojo', 'Personaggio della serie My Dress-Up-Darling', s.id
FROM serie s WHERE s.nome = 'My Dress-Up-Darling'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Wakana Gojo' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Sajuna Inui', 'Personaggio della serie My Dress-Up-Darling', s.id
FROM serie s WHERE s.nome = 'My Dress-Up-Darling'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Sajuna Inui' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'Breaking Bad', 'SERIE_TV',
       'Un professore di chimica malato di cancro entra nel mercato della metanfetamina.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'Breaking Bad');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Walter White', 'Personaggio della serie Breaking Bad', s.id
FROM serie s WHERE s.nome = 'Breaking Bad'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Walter White' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Jesse Pinkman', 'Personaggio della serie Breaking Bad', s.id
FROM serie s WHERE s.nome = 'Breaking Bad'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Jesse Pinkman' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Saul Goodman', 'Personaggio della serie Breaking Bad', s.id
FROM serie s WHERE s.nome = 'Breaking Bad'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Saul Goodman' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Gus Fring', 'Personaggio della serie Breaking Bad', s.id
FROM serie s WHERE s.nome = 'Breaking Bad'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Gus Fring' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'NFL', 'SPORT',
       'La lega professionistica di football americano fondata nel 1920.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'NFL');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Patrick Mahomes', 'Personaggio della serie NFL', s.id
FROM serie s WHERE s.nome = 'NFL'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Patrick Mahomes' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Travis Kelce', 'Personaggio della serie NFL', s.id
FROM serie s WHERE s.nome = 'NFL'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Travis Kelce' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Josh Allen', 'Personaggio della serie NFL', s.id
FROM serie s WHERE s.nome = 'NFL'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Josh Allen' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Tom Brady', 'Personaggio della serie NFL', s.id
FROM serie s WHERE s.nome = 'NFL'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Tom Brady' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'Matrix', 'FILM',
       'Un programmatore scopre di vivere dentro una simulazione creata dalle macchine.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'Matrix');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Neo', 'Personaggio della serie Matrix', s.id
FROM serie s WHERE s.nome = 'Matrix'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Neo' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Trinity', 'Personaggio della serie Matrix', s.id
FROM serie s WHERE s.nome = 'Matrix'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Trinity' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Morpheus', 'Personaggio della serie Matrix', s.id
FROM serie s WHERE s.nome = 'Matrix'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Morpheus' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Agente Smith', 'Personaggio della serie Matrix', s.id
FROM serie s WHERE s.nome = 'Matrix'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Agente Smith' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'Il Monologo Della Speziale', 'ANIME',
       'Una giovane speziale risolve misteri all''interno del palazzo imperiale.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'Il Monologo Della Speziale');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Maomao', 'Personaggio della serie Il Monologo Della Speziale', s.id
FROM serie s WHERE s.nome = 'Il Monologo Della Speziale'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Maomao' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Jinshi', 'Personaggio della serie Il Monologo Della Speziale', s.id
FROM serie s WHERE s.nome = 'Il Monologo Della Speziale'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Jinshi' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Consorte Gyokuyou', 'Personaggio della serie Il Monologo Della Speziale', s.id
FROM serie s WHERE s.nome = 'Il Monologo Della Speziale'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Consorte Gyokuyou' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'Ufo Robot', 'ANIME',
       'Il robot Goldrake difende la Terra dagli attacchi dell''impero di Vega.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'Ufo Robot');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Actarus', 'Personaggio della serie Ufo Robot', s.id
FROM serie s WHERE s.nome = 'Ufo Robot'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Actarus' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Alcor', 'Personaggio della serie Ufo Robot', s.id
FROM serie s WHERE s.nome = 'Ufo Robot'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Alcor' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Venusia', 'Personaggio della serie Ufo Robot', s.id
FROM serie s WHERE s.nome = 'Ufo Robot'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Venusia' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Re Vega', 'Personaggio della serie Ufo Robot', s.id
FROM serie s WHERE s.nome = 'Ufo Robot'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Re Vega' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'One Piece', 'MANGA',
       'Le avventure della ciurma di Cappello di Paglia alla ricerca del One Piece.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'One Piece');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Monkey D. Rufy', 'Personaggio della serie One Piece', s.id
FROM serie s WHERE s.nome = 'One Piece'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Monkey D. Rufy' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Roronoa Zoro', 'Personaggio della serie One Piece', s.id
FROM serie s WHERE s.nome = 'One Piece'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Roronoa Zoro' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Nami', 'Personaggio della serie One Piece', s.id
FROM serie s WHERE s.nome = 'One Piece'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Nami' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Sanji', 'Personaggio della serie One Piece', s.id
FROM serie s WHERE s.nome = 'One Piece'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Sanji' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'L''Attacco dei Giganti', 'MANGA',
       'L''umanita'' sopravvive dentro mura altissime assediata da creature che divorano gli esseri umani.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'L''Attacco dei Giganti');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Eren Jaeger', 'Personaggio della serie L''Attacco dei Giganti', s.id
FROM serie s WHERE s.nome = 'L''Attacco dei Giganti'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Eren Jaeger' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Mikasa Ackerman', 'Personaggio della serie L''Attacco dei Giganti', s.id
FROM serie s WHERE s.nome = 'L''Attacco dei Giganti'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Mikasa Ackerman' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Armin Arlert', 'Personaggio della serie L''Attacco dei Giganti', s.id
FROM serie s WHERE s.nome = 'L''Attacco dei Giganti'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Armin Arlert' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Levi Ackerman', 'Personaggio della serie L''Attacco dei Giganti', s.id
FROM serie s WHERE s.nome = 'L''Attacco dei Giganti'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Levi Ackerman' AND p.serie_id = s.id);

INSERT INTO serie (id, nome, tipo, descrizione, logo)
SELECT nextval('serie_seq'), 'Topolino', 'FUMETTO',
       'Il settimanale a fumetti Disney pubblicato in Italia dal 1949 in formato libretto.', NULL
WHERE NOT EXISTS (SELECT 1 FROM serie WHERE nome = 'Topolino');

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Topolino', 'Personaggio della serie Topolino', s.id
FROM serie s WHERE s.nome = 'Topolino'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Topolino' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Minni', 'Personaggio della serie Topolino', s.id
FROM serie s WHERE s.nome = 'Topolino'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Minni' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Pippo', 'Personaggio della serie Topolino', s.id
FROM serie s WHERE s.nome = 'Topolino'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Pippo' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Paperino', 'Personaggio della serie Topolino', s.id
FROM serie s WHERE s.nome = 'Topolino'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Paperino' AND p.serie_id = s.id);

INSERT INTO personaggio (id, nome, descrizione, serie_id)
SELECT nextval('personaggio_seq'), 'Zio Paperone', 'Personaggio della serie Topolino', s.id
FROM serie s WHERE s.nome = 'Topolino'
AND NOT EXISTS (SELECT 1 FROM personaggio p WHERE p.nome = 'Zio Paperone' AND p.serie_id = s.id);

-- ---------------------------------------------------------------------
-- AZIENDE
-- ---------------------------------------------------------------------
INSERT INTO azienda (id, nome, regione, descrizione, logo)
SELECT nextval('azienda_seq'), 'Bandai', 'Giappone',
       'Storica azienda giapponese di giocattoli e model kit fondata nel 1950.', NULL
WHERE NOT EXISTS (SELECT 1 FROM azienda WHERE nome = 'Bandai');

INSERT INTO azienda (id, nome, regione, descrizione, logo)
SELECT nextval('azienda_seq'), 'Creators Collection Figure', 'Giappone',
       'Linea Banpresto che affida la scultura ad artisti selezionati per interpretazioni originali dei personaggi.', NULL
WHERE NOT EXISTS (SELECT 1 FROM azienda WHERE nome = 'Creators Collection Figure');

INSERT INTO azienda (id, nome, regione, descrizione, logo)
SELECT nextval('azienda_seq'), 'Figuarts ZERO', 'Giappone',
       'Linea Bandai Spirits dedicata alle statue in posa fissa con forte impatto scenico.', NULL
WHERE NOT EXISTS (SELECT 1 FROM azienda WHERE nome = 'Figuarts ZERO');

INSERT INTO azienda (id, nome, regione, descrizione, logo)
SELECT nextval('azienda_seq'), 'Funko', 'Stati Uniti',
       'Azienda statunitense nota per le figure in vinile della linea Pop!.', NULL
WHERE NOT EXISTS (SELECT 1 FROM azienda WHERE nome = 'Funko');

INSERT INTO azienda (id, nome, regione, descrizione, logo)
SELECT nextval('azienda_seq'), 'Hello! Big Smile', 'Giappone',
       'Linea di figure chibi di Good Smile Company con collo snodabile e design essenziale.', NULL
WHERE NOT EXISTS (SELECT 1 FROM azienda WHERE nome = 'Hello! Big Smile');

INSERT INTO azienda (id, nome, regione, descrizione, logo)
SELECT nextval('azienda_seq'), 'Hot Toys', 'Hong Kong',
       'Azienda specializzata in figure da collezione in scala 1/6 tratte dal cinema.', NULL
WHERE NOT EXISTS (SELECT 1 FROM azienda WHERE nome = 'Hot Toys');

INSERT INTO azienda (id, nome, regione, descrizione, logo)
SELECT nextval('azienda_seq'), 'Nendoroid', 'Giappone',
       'Linea Good Smile Company con figure deformed da 10 cm e parti intercambiabili.', NULL
WHERE NOT EXISTS (SELECT 1 FROM azienda WHERE nome = 'Nendoroid');

INSERT INTO azienda (id, nome, regione, descrizione, logo)
SELECT nextval('azienda_seq'), 'Pop Up Parade', 'Giappone',
       'Linea Good Smile Company con figure in scala fissa da 17-18 cm a prezzo contenuto.', NULL
WHERE NOT EXISTS (SELECT 1 FROM azienda WHERE nome = 'Pop Up Parade');

INSERT INTO azienda (id, nome, regione, descrizione, logo)
SELECT nextval('azienda_seq'), 'S.H. Figuarts', 'Giappone',
       'Linea di action figure snodabili in scala 1/12 di Bandai Spirits sotto il marchio Tamashii Nations.', NULL
WHERE NOT EXISTS (SELECT 1 FROM azienda WHERE nome = 'S.H. Figuarts');

-- ---------------------------------------------------------------------
-- EDITORI
-- ---------------------------------------------------------------------
INSERT INTO editore (id, nome, regione, descrizione, logo)
SELECT nextval('editore_seq'), 'Shueisha', 'Giappone',
       'Editore della rivista Weekly Shonen Jump.', NULL
WHERE NOT EXISTS (SELECT 1 FROM editore WHERE nome = 'Shueisha');

INSERT INTO editore (id, nome, regione, descrizione, logo)
SELECT nextval('editore_seq'), 'Panini Comics', 'Italia',
       'Editore italiano di manga e fumetti.', NULL
WHERE NOT EXISTS (SELECT 1 FROM editore WHERE nome = 'Panini Comics');

INSERT INTO editore (id, nome, regione, descrizione, logo)
SELECT nextval('editore_seq'), 'Mondadori', 'Italia',
       'Casa editrice italiana che pubblico'' Topolino in formato libretto dal 1949.', NULL
WHERE NOT EXISTS (SELECT 1 FROM editore WHERE nome = 'Mondadori');

INSERT INTO editore (id, nome, regione, descrizione, logo)
SELECT nextval('editore_seq'), 'Star Comics', 'Italia',
       'Editore italiano di manga fondato nel 1987.', NULL
WHERE NOT EXISTS (SELECT 1 FROM editore WHERE nome = 'Star Comics');

INSERT INTO editore (id, nome, regione, descrizione, logo)
SELECT nextval('editore_seq'), 'J-Pop', 'Italia',
       'Etichetta manga di Edizioni BD.', NULL
WHERE NOT EXISTS (SELECT 1 FROM editore WHERE nome = 'J-Pop');

INSERT INTO editore (id, nome, regione, descrizione, logo)
SELECT nextval('editore_seq'), 'Marvel Comics', 'Stati Uniti',
       'Casa editrice statunitense di fumetti di supereroi.', NULL
WHERE NOT EXISTS (SELECT 1 FROM editore WHERE nome = 'Marvel Comics');

-- ---------------------------------------------------------------------
-- SCRITTORI E DISEGNATORI
-- ---------------------------------------------------------------------
INSERT INTO scrittore (id, nome, cognome, data_nascita, paese)
SELECT nextval('scrittore_seq'), 'Eiichiro', 'Oda', DATE '1975-01-01', 'Giappone'
WHERE NOT EXISTS (SELECT 1 FROM scrittore WHERE nome = 'Eiichiro' AND cognome = 'Oda');

INSERT INTO disegnatore (id, nome, cognome, data_nascita, paese)
SELECT nextval('disegnatore_seq'), 'Eiichiro', 'Oda', DATE '1975-01-01', 'Giappone'
WHERE NOT EXISTS (SELECT 1 FROM disegnatore WHERE nome = 'Eiichiro' AND cognome = 'Oda');

INSERT INTO scrittore (id, nome, cognome, data_nascita, paese)
SELECT nextval('scrittore_seq'), 'Koyoharu', 'Gotouge', DATE '1989-05-05', 'Giappone'
WHERE NOT EXISTS (SELECT 1 FROM scrittore WHERE nome = 'Koyoharu' AND cognome = 'Gotouge');

INSERT INTO disegnatore (id, nome, cognome, data_nascita, paese)
SELECT nextval('disegnatore_seq'), 'Koyoharu', 'Gotouge', DATE '1989-05-05', 'Giappone'
WHERE NOT EXISTS (SELECT 1 FROM disegnatore WHERE nome = 'Koyoharu' AND cognome = 'Gotouge');

INSERT INTO disegnatore (id, nome, cognome, data_nascita, paese)
SELECT nextval('disegnatore_seq'), 'Tetsuya', 'Nomura', DATE '1970-10-08', 'Giappone'
WHERE NOT EXISTS (SELECT 1 FROM disegnatore WHERE nome = 'Tetsuya' AND cognome = 'Nomura');

INSERT INTO scrittore (id, nome, cognome, data_nascita, paese)
SELECT nextval('scrittore_seq'), 'Hajime', 'Isayama', DATE '1986-08-29', 'Giappone'
WHERE NOT EXISTS (SELECT 1 FROM scrittore WHERE nome = 'Hajime' AND cognome = 'Isayama');

INSERT INTO disegnatore (id, nome, cognome, data_nascita, paese)
SELECT nextval('disegnatore_seq'), 'Hajime', 'Isayama', DATE '1986-08-29', 'Giappone'
WHERE NOT EXISTS (SELECT 1 FROM disegnatore WHERE nome = 'Hajime' AND cognome = 'Isayama');

INSERT INTO scrittore (id, nome, cognome, data_nascita, paese)
SELECT nextval('scrittore_seq'), 'Gege', 'Akutami', DATE '1992-02-26', 'Giappone'
WHERE NOT EXISTS (SELECT 1 FROM scrittore WHERE nome = 'Gege' AND cognome = 'Akutami');

INSERT INTO disegnatore (id, nome, cognome, data_nascita, paese)
SELECT nextval('disegnatore_seq'), 'Gege', 'Akutami', DATE '1992-02-26', 'Giappone'
WHERE NOT EXISTS (SELECT 1 FROM disegnatore WHERE nome = 'Gege' AND cognome = 'Akutami');

INSERT INTO scrittore (id, nome, cognome, data_nascita, paese)
SELECT nextval('scrittore_seq'), 'Shinichi', 'Fukuda', DATE '1985-01-01', 'Giappone'
WHERE NOT EXISTS (SELECT 1 FROM scrittore WHERE nome = 'Shinichi' AND cognome = 'Fukuda');

INSERT INTO disegnatore (id, nome, cognome, data_nascita, paese)
SELECT nextval('disegnatore_seq'), 'Shinichi', 'Fukuda', DATE '1985-01-01', 'Giappone'
WHERE NOT EXISTS (SELECT 1 FROM disegnatore WHERE nome = 'Shinichi' AND cognome = 'Fukuda');

INSERT INTO scrittore (id, nome, cognome, data_nascita, paese)
SELECT nextval('scrittore_seq'), 'Guido', 'Martina', DATE '1906-02-09', 'Italia'
WHERE NOT EXISTS (SELECT 1 FROM scrittore WHERE nome = 'Guido' AND cognome = 'Martina');

INSERT INTO disegnatore (id, nome, cognome, data_nascita, paese)
SELECT nextval('disegnatore_seq'), 'Angelo', 'Bioletto', DATE '1906-01-01', 'Italia'
WHERE NOT EXISTS (SELECT 1 FROM disegnatore WHERE nome = 'Angelo' AND cognome = 'Bioletto');

INSERT INTO scrittore (id, nome, cognome, data_nascita, paese)
SELECT nextval('scrittore_seq'), 'Stan', 'Lee', DATE '1922-12-28', 'Stati Uniti'
WHERE NOT EXISTS (SELECT 1 FROM scrittore WHERE nome = 'Stan' AND cognome = 'Lee');

INSERT INTO disegnatore (id, nome, cognome, data_nascita, paese)
SELECT nextval('disegnatore_seq'), 'Steve', 'Ditko', DATE '1927-11-02', 'Stati Uniti'
WHERE NOT EXISTS (SELECT 1 FROM disegnatore WHERE nome = 'Steve' AND cognome = 'Ditko');

-- ---------------------------------------------------------------------
-- FIGURE
-- ---------------------------------------------------------------------
INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'S.H. Figuarts Spider-Man', DATE '2026-03-14', 15.5, 'PVC',
       '/images/figure/SHFiguarts_Spider_Man_Snodabile/item_0000015491_dhdQz2FY_01_q.jpg', 49.0,
       'Spider-Man snodabile con possibilità di usare 4 paia di mani intercambiabili, testa intercambiabile, ragnatela per oscillare, ragnatela difensiva, adattatori per effetti senso di ragno',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'S.H. Figuarts' AND s.nome = 'Spider-Man'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'S.H. Figuarts Spider-Man' AND azienda_id = a.id AND data_uscita = DATE '2026-03-14');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'S.H. Figuarts Yuji Itadori', DATE '2026-03-14', 15.0, 'PVC',
       '/images/figure/SHFiguarts_Yuji_Itadori_Mani_Capelli_Scambiabili/item_0000015569_QekQO9xA_08_1.jpg', 40.0,
       'Yuji Itadori di Jujutsu Kaisen snodabile con 4 paia di mani intercambiabili, 4 differenti espressioni stile di capelli scambiabile e parti opzionali',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'S.H. Figuarts' AND s.nome = 'Jujutsu Kaisen'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'S.H. Figuarts Yuji Itadori' AND azienda_id = a.id AND data_uscita = DATE '2026-03-14');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'Hello! Big Smile Hatsune Miku Aurora Version', DATE '2026-02-27', 28.0, 'PVC',
       '/images/figure/HelloBigSmile_Hatsune_Miku_Aurora_Ver/Z47u1GSnzLjXsrcikyMQxmDabUWhBYVF.jpg', 113.0,
       'Hatsune Miku formato chibi versione aurora con la testa leggermente inclinabile. Tiratura limitata.',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'Hello! Big Smile' AND s.nome = 'Hatsune Miku'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'Hello! Big Smile Hatsune Miku Aurora Version' AND azienda_id = a.id AND data_uscita = DATE '2026-02-27');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'Hello! Big Smile Hatsune Miku', DATE '2026-02-08', 28.0, 'PVC',
       '/images/figure/HelloBigSmile_Hatsune_Miku/y8S4UxukD1BTf6Hi0dnaYPz3Z9bF5pE2_1.jpg', 97.0,
       'Hatsune Miku collo movibile chibi big smile',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'Hello! Big Smile' AND s.nome = 'Hatsune Miku'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'Hello! Big Smile Hatsune Miku' AND azienda_id = a.id AND data_uscita = DATE '2026-02-08');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'S.H. Figuarts Yoshi', DATE '2026-03-14', 11.0, 'PVC',
       '/images/figure/SHFiguarts_Yoshi/item_0000015466_t3uM9I9A_05_1.jpg', 32.0,
       'Yoshi (verde) della serie di super mario con la possibilità di essere cavalcato da mario e/o luigi. Mano destra intercambiabile, uovo di yoshi, lingua e occhi intercambiabili, guscio per essere cavalcato da Mario e guscio per essere cavalcato da Luigi, piedistallo dedicato',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'S.H. Figuarts' AND s.nome = 'Super Mario Brothers'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'S.H. Figuarts Yoshi' AND azienda_id = a.id AND data_uscita = DATE '2026-03-14');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'S.H. Figuarts Bowser', DATE '2026-03-14', 13.0, 'PVC',
       '/images/figure/SHFiguarts_Bowser/item_0000015467_gMKhkDFT_04_1.jpg', 38.0,
       'Bowser della serie super mario brothers snodabile con parti di mani intercambiabili, 2 parti mandibolari inferiori scambili e piedistallo dedicato. Possibilità di poter far sparare fiamma dalla bocca',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'S.H. Figuarts' AND s.nome = 'Super Mario Brothers'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'S.H. Figuarts Bowser' AND azienda_id = a.id AND data_uscita = DATE '2026-03-14');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'Pop Up Parade Lucyna Kushinada', DATE '2025-09-25', 22.5, 'PVC',
       '/images/figure/PopUpParade_Lucyna_Kushinada/vtSYBjKrChAJDg7X5NMQERWq2ZmHy0Fx_1.jpg', 53.0,
       'Action Figure rappresentante il personaggio Lucyna Kushinada di Cyberpunk Edge Runners',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'Pop Up Parade' AND s.nome = 'Cyberpunk Edge Runners'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'Pop Up Parade Lucyna Kushinada' AND azienda_id = a.id AND data_uscita = DATE '2025-09-25');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'Figuarts ZERO Son Goku (Super Sayan)', DATE '2026-03-14', 22.0, 'PVC',
       '/images/figure/FiguartsZERO_Son_Goku_Super_Sayan/item_0000015597_nNZosAiE_01_1.jpg', 54.0,
       'Goku trasformato in Super Sayan negli abiti classici che si lancia in avanti. Legato ad una tavoletta dove sono scolpiti Re Piccolo,Freezer nella quarta trasformazione e Cell perfetto.',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'Figuarts ZERO' AND s.nome = 'Dragon Ball'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'Figuarts ZERO Son Goku (Super Sayan)' AND azienda_id = a.id AND data_uscita = DATE '2026-03-14');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'S.H. Figuarts Ryu', DATE '2024-02-17', 15.0, 'PVC',
       '/images/figure/SHFiguarts_Ryu/item_0000014591_MKabUcY4_03_1.jpg', 43.0,
       'Ryu in outfit classico con colorazione tratta dal videogioco street fighter 6. 3 differenti espressioni facciali, 1 paio di braccia intercambiabili, effetto fisivo della tecnica Hadoken e sfondo personalizzato',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'S.H. Figuarts' AND s.nome = 'Street Fighter'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'S.H. Figuarts Ryu' AND azienda_id = a.id AND data_uscita = DATE '2024-02-17');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'S.H. Figuarts Chun-Li', DATE '2024-02-17', 14.5, 'PVC',
       '/images/figure/SHFiguarts_Chun_Li/item_0000014592_de5PMxXV_03.jpg', 43.0,
       'Chun-Li in outfit classico con colorazione tratta dal videogioco street fighter 6. 3 differenti espressioni facciali, 3 paia di mani intercambiabili, effetto visivo della tecnica Hyakuretsu Kyaku ( Hundred Lighting Kicks) e sfondo personalizzato',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'S.H. Figuarts' AND s.nome = 'Street Fighter'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'S.H. Figuarts Chun-Li' AND azienda_id = a.id AND data_uscita = DATE '2024-02-17');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'Nendoroid Artoria Pendragon (Saber)', DATE '2025-11-29', 10.0, 'PVC',
       '/images/figure/Nendoroid_Artoria_Pendragon_Saber/8mDSfcyXJhUG9i7zwtRNFCEnWujM2s4b.jpg', 81.0,
       'Action Figure rappresentante Saber della Saga Fate: Stay Night in formato Nendoroid Large',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'Nendoroid' AND s.nome = 'Fate: Stay Night'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'Nendoroid Artoria Pendragon (Saber)' AND azienda_id = a.id AND data_uscita = DATE '2025-11-29');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'Nendoroid Marin Kitagawa', DATE '2023-03-01', 10.0, 'PVC',
       '/images/figure/Nendoroid_Marin_Kitagawa/bae4c828952497e4c980d87d8f17d8da.jpg', 35.0,
       'Dall''anime televisivo "That Dress-Up Doll Is Falling in Love", la ragazza amante del cosplay "Kitagawa Kaiyume" è ora un Nendoroid! Sono disponibili tre tipi di espressioni facciali sostitutive: "smile", "wink face" e "big shunki too long face". Le parti opzionali includono "Nuru Woman2" e "Specchio di stampa Leopard", i personaggi del ketchup "Sorry" sono impressionanti "Sea Dream Special Omelet Rice" e "Hugging Pillow" di Shizukutan, in modo da poter godere della riproduzione della scena nella commedia e varie pose che sono come un sogno marino. Si prega di accogliere il simpatico piccolo "Nendoroid Kitagawa Kaimu" con tutti i mezzi ♪',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'Nendoroid' AND s.nome = 'My Dress-Up-Darling'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'Nendoroid Marin Kitagawa' AND azienda_id = a.id AND data_uscita = DATE '2023-03-01');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'Pop! Heisenberg (1934)', DATE '2026-11-01', 10.9, 'PVC',
       '/images/figure/FunkoPop_Heisenberg/heisenberg.jpg', 13.0,
       'POP! Heisenberg is here with his special bag of blue. When it comes to chemistry no one knows or does it better. When it comes to business, no one can cross him without severe consequences. Is the Breaking Bad lineup of your POP! Television collection ready for the head honcho to step on the scene? Vinyl figure is approximately 4.3 in (10.9 cm) tall.',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'Funko' AND s.nome = 'Breaking Bad'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'Pop! Heisenberg (1934)' AND azienda_id = a.id AND data_uscita = DATE '2026-11-01');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'Pop! Jaxon Smith-Njigba (316)', DATE '2025-08-01', 10.2, 'PVC',
       '/images/figure/FunkoPop_Jaxon_Smith_Njigba/89149_NFL_Seahawks_Smith-Njigba_POP_GLAM-WEB_1.jpg', 13.0,
       'Draft POP! Jaxon Smith-Njigba, wide receiver for the Seattle Seahawks, to your NFL collection in his blue and grey throwback uniform! Which team will POP! Jaxon Smith-Njigba and the Seahawks play next in your football lineup? Vinyl figure is approximately 4 in (10.2 cm) tall.',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'Funko' AND s.nome = 'NFL'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'Pop! Jaxon Smith-Njigba (316)' AND azienda_id = a.id AND data_uscita = DATE '2025-08-01');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'Figuarts ZERO Kaido Of The Beasts', DATE '2021-12-01', 32.2, 'RESINA',
       '/images/figure/FiguartsZERO_Kaido_Of_The_Beasts/bandai-figuarts-zero-one-piece-kaido-of-the-beasts_1.jpg', 435.0,
       'Kaido delle 100 Bestie tratto da One Piece entra nella line-up Bandai Figuarts Zero Extra-Battle, alto 32cm.',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'Figuarts ZERO' AND s.nome = 'One Piece'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'Figuarts ZERO Kaido Of The Beasts' AND azienda_id = a.id AND data_uscita = DATE '2021-12-01');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'Hot Toys Neo', DATE '2027-05-01', 31.0, 'ABS',
       '/images/figure/HotToys_Neo/x_hot915437_k_1.jpg', 379.0,
       'Hot Toys presenta una nuova figure in scala 1/6 dedicata a Neo, da The Matrix Reloaded e interpretato da Keanu Reeves. Alta circa 31 cm e dotata di 30 punti di articolazione, la figure presenta uno sculpt realistico con occhi mobili (rolling eyeballs), capelli scolpiti all’indietro e un’espressione fedele al personaggio. Il costume include il celebre lungo cappotto nero con fili metallici interni, oltre a pantaloni, calze e scarpe nere. Per le pose dinamiche sono incluse 11 mani intercambiabili, tra cui quella con il famoso gesto che ferma i proiettili. Tra gli accessori troviamo un ricco arsenale con spada, mazza chiodata, bastone, coppia di sai e lance spezzate, oltre agli iconici occhiali da sole. Completano il set una base diorama a tema scalinata dello Chateau con corrimano danneggiato e un backdrop scenografico dedicato alla celebre scena di combattimento del film.',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'Hot Toys' AND s.nome = 'Matrix'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'Hot Toys Neo' AND azienda_id = a.id AND data_uscita = DATE '2027-05-01');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'Nendoroid Maomao', DATE '2024-05-01', 10.0, 'PVC',
       '/images/figure/Nendoroid_Maomao/d969225b825892f64246e42876692d08.webp', 33.0,
       NULL,
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'Nendoroid' AND s.nome = 'Il Monologo Della Speziale'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'Nendoroid Maomao' AND azienda_id = a.id AND data_uscita = DATE '2024-05-01');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'Nendoroid Jinshi', DATE '2024-06-01', 10.0, 'PVC',
       '/images/figure/Nendoroid_Jinshi/eZcWwriRJAUSP2bCfHTkxNtDVKMGzX9y.jpg', 33.0,
       NULL,
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'Nendoroid' AND s.nome = 'Il Monologo Della Speziale'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'Nendoroid Jinshi' AND azienda_id = a.id AND data_uscita = DATE '2024-06-01');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'Creators Collection Hatsune Miku Rosuuri', DATE '2025-05-01', 21.4, 'PVC',
       '/images/figure/Creators_Collection_Figure_Hatsune_Miku_Rosuuri/FqjCGH9MaAbpvuPyYh04RmwtZTUzXdLr.jpg', 178.0,
       'HATSUNE MIKU EXPO 10th Anniversary Illustration Contest dei visual chiave di Rosuuri sono trasformati in una figura su una scala di 1/7! Una gemma che riproduce dettagli fini come vestiti e stelle sparse',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'Creators Collection Figure' AND s.nome = 'Hatsune Miku'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'Creators Collection Hatsune Miku Rosuuri' AND azienda_id = a.id AND data_uscita = DATE '2025-05-01');

INSERT INTO figure (id, nome, data_uscita, altezza, materiale, immagine, prezzo, descrizione, azienda_id, serie_id)
SELECT nextval('figure_seq'), 'Bandai Ufo Robot Grendizer', DATE '2026-04-22', 16.0, 'ABS',
       '/images/figure/Bandai_Ufo_Robot_Grendizer/bandai-the-robot-spirits-ufo-robot-grendizer_01.jpg', 80.0,
       'UFO Robot Grendizer entra nella line-up Bandai The Robot Spirits, azione completa alto 16cm. Realizzato in ABS e PVC. Viene fornito con 4 mani intercambiabili, Magli Perforanti e Alabarda Spaziale',
       a.id, s.id
FROM azienda a, serie s WHERE a.nome = 'Bandai' AND s.nome = 'Ufo Robot'
AND NOT EXISTS (SELECT 1 FROM figure WHERE nome = 'Bandai Ufo Robot Grendizer' AND azienda_id = a.id AND data_uscita = DATE '2026-04-22');

-- ---------------------------------------------------------------------
-- FUMETTI
-- ---------------------------------------------------------------------
INSERT INTO fumetto (id, titolo, variant, anno, isbn, prezzo, genere, immagine, serie_id, scrittore_id, editore_id)
SELECT nextval('fumetto_seq'), 'L''Attacco dei Giganti vol. 1', FALSE, 2012, '9788899000011', 5.2, 'Shonen',
       '/images/fumetti/AttaccoGiganti_vol1/960px-L''attacco_dei_giganti_copertina.jpeg',
       s.id, sc.id, e.id
FROM serie s, scrittore sc, editore e
WHERE s.nome = 'L''Attacco dei Giganti' AND sc.nome = 'Hajime' AND sc.cognome = 'Isayama' AND e.nome = 'Panini Comics'
AND NOT EXISTS (SELECT 1 FROM fumetto WHERE isbn = '9788899000011');

INSERT INTO fumetto (id, titolo, variant, anno, isbn, prezzo, genere, immagine, serie_id, scrittore_id, editore_id)
SELECT nextval('fumetto_seq'), 'L''Attacco dei Giganti vol. 2', FALSE, 2012, '9788899000028', 5.2, 'Shonen',
       '/images/fumetti/AttaccoGiganti_vol2/9788828790549_0_0_536_0_75.jpg',
       s.id, sc.id, e.id
FROM serie s, scrittore sc, editore e
WHERE s.nome = 'L''Attacco dei Giganti' AND sc.nome = 'Hajime' AND sc.cognome = 'Isayama' AND e.nome = 'Panini Comics'
AND NOT EXISTS (SELECT 1 FROM fumetto WHERE isbn = '9788899000028');

INSERT INTO fumetto (id, titolo, variant, anno, isbn, prezzo, genere, immagine, serie_id, scrittore_id, editore_id)
SELECT nextval('fumetto_seq'), 'L''Attacco dei Giganti vol. 3', FALSE, 2012, '9788899000035', 5.2, 'Shonen',
       '/images/fumetti/AttaccoGiganti_vol3/71-45UxHOGL._AC_UF1000,1000_QL80_.jpg',
       s.id, sc.id, e.id
FROM serie s, scrittore sc, editore e
WHERE s.nome = 'L''Attacco dei Giganti' AND sc.nome = 'Hajime' AND sc.cognome = 'Isayama' AND e.nome = 'Panini Comics'
AND NOT EXISTS (SELECT 1 FROM fumetto WHERE isbn = '9788899000035');

INSERT INTO fumetto (id, titolo, variant, anno, isbn, prezzo, genere, immagine, serie_id, scrittore_id, editore_id)
SELECT nextval('fumetto_seq'), 'Jujutsu Kaisen vol. 1', FALSE, 2020, '9788899000042', 4.9, 'Shonen',
       '/images/fumetti/JujutsuKaisen_vol1/81TmHlRleJL._AC_UF1000,1000_QL80_.jpg',
       s.id, sc.id, e.id
FROM serie s, scrittore sc, editore e
WHERE s.nome = 'Jujutsu Kaisen' AND sc.nome = 'Gege' AND sc.cognome = 'Akutami' AND e.nome = 'Panini Comics'
AND NOT EXISTS (SELECT 1 FROM fumetto WHERE isbn = '9788899000042');

INSERT INTO fumetto (id, titolo, variant, anno, isbn, prezzo, genere, immagine, serie_id, scrittore_id, editore_id)
SELECT nextval('fumetto_seq'), 'Jujutsu Kaisen vol. 2', FALSE, 2020, '9788899000059', 4.9, 'Shonen',
       '/images/fumetti/JujutsuKaisen_vol2/91ZnHPJy2lL._UF1000,1000_QL80_.jpg',
       s.id, sc.id, e.id
FROM serie s, scrittore sc, editore e
WHERE s.nome = 'Jujutsu Kaisen' AND sc.nome = 'Gege' AND sc.cognome = 'Akutami' AND e.nome = 'Panini Comics'
AND NOT EXISTS (SELECT 1 FROM fumetto WHERE isbn = '9788899000059');

INSERT INTO fumetto (id, titolo, variant, anno, isbn, prezzo, genere, immagine, serie_id, scrittore_id, editore_id)
SELECT nextval('fumetto_seq'), 'Jujutsu Kaisen vol. 3', FALSE, 2021, '9788899000066', 4.9, 'Shonen',
       '/images/fumetti/JujutsuKaisen_vol3/MAHER037SBNR2_0.webp',
       s.id, sc.id, e.id
FROM serie s, scrittore sc, editore e
WHERE s.nome = 'Jujutsu Kaisen' AND sc.nome = 'Gege' AND sc.cognome = 'Akutami' AND e.nome = 'Panini Comics'
AND NOT EXISTS (SELECT 1 FROM fumetto WHERE isbn = '9788899000066');

INSERT INTO fumetto (id, titolo, variant, anno, isbn, prezzo, genere, immagine, serie_id, scrittore_id, editore_id)
SELECT nextval('fumetto_seq'), 'Topolino n. 1', FALSE, 1949, '9788899000073', 3.5, 'Umoristico',
       '/images/fumetti/Topolino_vol1/images.jpg',
       s.id, sc.id, e.id
FROM serie s, scrittore sc, editore e
WHERE s.nome = 'Topolino' AND sc.nome = 'Guido' AND sc.cognome = 'Martina' AND e.nome = 'Mondadori'
AND NOT EXISTS (SELECT 1 FROM fumetto WHERE isbn = '9788899000073');

INSERT INTO fumetto (id, titolo, variant, anno, isbn, prezzo, genere, immagine, serie_id, scrittore_id, editore_id)
SELECT nextval('fumetto_seq'), 'Topolino n. 2', FALSE, 1949, '9788899000080', 3.5, 'Umoristico',
       '/images/fumetti/Topolino_vol2/copia.jpg',
       s.id, sc.id, e.id
FROM serie s, scrittore sc, editore e
WHERE s.nome = 'Topolino' AND sc.nome = 'Guido' AND sc.cognome = 'Martina' AND e.nome = 'Mondadori'
AND NOT EXISTS (SELECT 1 FROM fumetto WHERE isbn = '9788899000080');

INSERT INTO fumetto (id, titolo, variant, anno, isbn, prezzo, genere, immagine, serie_id, scrittore_id, editore_id)
SELECT nextval('fumetto_seq'), 'Topolino n. 3', FALSE, 1949, '9788899000097', 3.5, 'Umoristico',
       '/images/fumetti/Topolino_vol3/9788883959486_0_0_424_0_75_1.jpg',
       s.id, sc.id, e.id
FROM serie s, scrittore sc, editore e
WHERE s.nome = 'Topolino' AND sc.nome = 'Guido' AND sc.cognome = 'Martina' AND e.nome = 'Mondadori'
AND NOT EXISTS (SELECT 1 FROM fumetto WHERE isbn = '9788899000097');

INSERT INTO fumetto (id, titolo, variant, anno, isbn, prezzo, genere, immagine, serie_id, scrittore_id, editore_id)
SELECT nextval('fumetto_seq'), 'One Piece vol. 1', FALSE, 2001, '9788899000103', 4.3, 'Shonen',
       '/images/fumetti/OnePiece_vol1/images_a.jpg',
       s.id, sc.id, e.id
FROM serie s, scrittore sc, editore e
WHERE s.nome = 'One Piece' AND sc.nome = 'Eiichiro' AND sc.cognome = 'Oda' AND e.nome = 'Star Comics'
AND NOT EXISTS (SELECT 1 FROM fumetto WHERE isbn = '9788899000103');

INSERT INTO fumetto (id, titolo, variant, anno, isbn, prezzo, genere, immagine, serie_id, scrittore_id, editore_id)
SELECT nextval('fumetto_seq'), 'One Piece vol. 2', FALSE, 2001, '9788899000110', 4.3, 'Shonen',
       '/images/fumetti/OnePiece_vol2/one-piece-new-edition-2-1200px.jpg',
       s.id, sc.id, e.id
FROM serie s, scrittore sc, editore e
WHERE s.nome = 'One Piece' AND sc.nome = 'Eiichiro' AND sc.cognome = 'Oda' AND e.nome = 'Star Comics'
AND NOT EXISTS (SELECT 1 FROM fumetto WHERE isbn = '9788899000110');

INSERT INTO fumetto (id, titolo, variant, anno, isbn, prezzo, genere, immagine, serie_id, scrittore_id, editore_id)
SELECT nextval('fumetto_seq'), 'Spider-Man vol. 1', FALSE, 1963, '9788899000127', 12.0, 'Supereroi',
       '/images/fumetti/SpiderMan_vol1/MOMNI007ISBN_0.webp',
       s.id, sc.id, e.id
FROM serie s, scrittore sc, editore e
WHERE s.nome = 'Spider-Man' AND sc.nome = 'Stan' AND sc.cognome = 'Lee' AND e.nome = 'Marvel Comics'
AND NOT EXISTS (SELECT 1 FROM fumetto WHERE isbn = '9788899000127');

INSERT INTO fumetto (id, titolo, variant, anno, isbn, prezzo, genere, immagine, serie_id, scrittore_id, editore_id)
SELECT nextval('fumetto_seq'), 'Spider-Man vol. 2', FALSE, 1963, '9788899000134', 12.0, 'Supereroi',
       '/images/fumetti/SpiderMan_vol2/91HgSVpRGgL._AC_UF1000,1000_QL80_.jpg',
       s.id, sc.id, e.id
FROM serie s, scrittore sc, editore e
WHERE s.nome = 'Spider-Man' AND sc.nome = 'Stan' AND sc.cognome = 'Lee' AND e.nome = 'Marvel Comics'
AND NOT EXISTS (SELECT 1 FROM fumetto WHERE isbn = '9788899000134');

INSERT INTO fumetto (id, titolo, variant, anno, isbn, prezzo, genere, immagine, serie_id, scrittore_id, editore_id)
SELECT nextval('fumetto_seq'), 'My Dress-Up-Darling vol. 1', FALSE, 2022, '9788899000141', 6.5, 'Seinen',
       '/images/fumetti/MyDressUpDarling_vol1/91kUbCiJMDL._AC_UF1000,1000_QL80_AIweblab1381794,T1_.jpg',
       s.id, sc.id, e.id
FROM serie s, scrittore sc, editore e
WHERE s.nome = 'My Dress-Up-Darling' AND sc.nome = 'Shinichi' AND sc.cognome = 'Fukuda' AND e.nome = 'J-Pop'
AND NOT EXISTS (SELECT 1 FROM fumetto WHERE isbn = '9788899000141');

-- ---------------------------------------------------------------------
-- ASSOCIAZIONE FUMETTO - DISEGNATORE (molti-a-molti)
-- ---------------------------------------------------------------------
INSERT INTO fumetto_disegnatore (fumetto_id, disegnatore_id)
SELECT f.id, d.id
FROM fumetto f, disegnatore d
WHERE f.isbn = '9788899000011' AND d.nome = 'Hajime' AND d.cognome = 'Isayama'
  AND NOT EXISTS (SELECT 1 FROM fumetto_disegnatore fd WHERE fd.fumetto_id = f.id AND fd.disegnatore_id = d.id);

INSERT INTO fumetto_disegnatore (fumetto_id, disegnatore_id)
SELECT f.id, d.id
FROM fumetto f, disegnatore d
WHERE f.isbn = '9788899000028' AND d.nome = 'Hajime' AND d.cognome = 'Isayama'
  AND NOT EXISTS (SELECT 1 FROM fumetto_disegnatore fd WHERE fd.fumetto_id = f.id AND fd.disegnatore_id = d.id);

INSERT INTO fumetto_disegnatore (fumetto_id, disegnatore_id)
SELECT f.id, d.id
FROM fumetto f, disegnatore d
WHERE f.isbn = '9788899000035' AND d.nome = 'Hajime' AND d.cognome = 'Isayama'
  AND NOT EXISTS (SELECT 1 FROM fumetto_disegnatore fd WHERE fd.fumetto_id = f.id AND fd.disegnatore_id = d.id);

INSERT INTO fumetto_disegnatore (fumetto_id, disegnatore_id)
SELECT f.id, d.id
FROM fumetto f, disegnatore d
WHERE f.isbn = '9788899000042' AND d.nome = 'Gege' AND d.cognome = 'Akutami'
  AND NOT EXISTS (SELECT 1 FROM fumetto_disegnatore fd WHERE fd.fumetto_id = f.id AND fd.disegnatore_id = d.id);

INSERT INTO fumetto_disegnatore (fumetto_id, disegnatore_id)
SELECT f.id, d.id
FROM fumetto f, disegnatore d
WHERE f.isbn = '9788899000059' AND d.nome = 'Gege' AND d.cognome = 'Akutami'
  AND NOT EXISTS (SELECT 1 FROM fumetto_disegnatore fd WHERE fd.fumetto_id = f.id AND fd.disegnatore_id = d.id);

INSERT INTO fumetto_disegnatore (fumetto_id, disegnatore_id)
SELECT f.id, d.id
FROM fumetto f, disegnatore d
WHERE f.isbn = '9788899000066' AND d.nome = 'Gege' AND d.cognome = 'Akutami'
  AND NOT EXISTS (SELECT 1 FROM fumetto_disegnatore fd WHERE fd.fumetto_id = f.id AND fd.disegnatore_id = d.id);

INSERT INTO fumetto_disegnatore (fumetto_id, disegnatore_id)
SELECT f.id, d.id
FROM fumetto f, disegnatore d
WHERE f.isbn = '9788899000073' AND d.nome = 'Angelo' AND d.cognome = 'Bioletto'
  AND NOT EXISTS (SELECT 1 FROM fumetto_disegnatore fd WHERE fd.fumetto_id = f.id AND fd.disegnatore_id = d.id);

INSERT INTO fumetto_disegnatore (fumetto_id, disegnatore_id)
SELECT f.id, d.id
FROM fumetto f, disegnatore d
WHERE f.isbn = '9788899000080' AND d.nome = 'Angelo' AND d.cognome = 'Bioletto'
  AND NOT EXISTS (SELECT 1 FROM fumetto_disegnatore fd WHERE fd.fumetto_id = f.id AND fd.disegnatore_id = d.id);

INSERT INTO fumetto_disegnatore (fumetto_id, disegnatore_id)
SELECT f.id, d.id
FROM fumetto f, disegnatore d
WHERE f.isbn = '9788899000097' AND d.nome = 'Angelo' AND d.cognome = 'Bioletto'
  AND NOT EXISTS (SELECT 1 FROM fumetto_disegnatore fd WHERE fd.fumetto_id = f.id AND fd.disegnatore_id = d.id);

INSERT INTO fumetto_disegnatore (fumetto_id, disegnatore_id)
SELECT f.id, d.id
FROM fumetto f, disegnatore d
WHERE f.isbn = '9788899000103' AND d.nome = 'Eiichiro' AND d.cognome = 'Oda'
  AND NOT EXISTS (SELECT 1 FROM fumetto_disegnatore fd WHERE fd.fumetto_id = f.id AND fd.disegnatore_id = d.id);

INSERT INTO fumetto_disegnatore (fumetto_id, disegnatore_id)
SELECT f.id, d.id
FROM fumetto f, disegnatore d
WHERE f.isbn = '9788899000110' AND d.nome = 'Eiichiro' AND d.cognome = 'Oda'
  AND NOT EXISTS (SELECT 1 FROM fumetto_disegnatore fd WHERE fd.fumetto_id = f.id AND fd.disegnatore_id = d.id);

INSERT INTO fumetto_disegnatore (fumetto_id, disegnatore_id)
SELECT f.id, d.id
FROM fumetto f, disegnatore d
WHERE f.isbn = '9788899000127' AND d.nome = 'Steve' AND d.cognome = 'Ditko'
  AND NOT EXISTS (SELECT 1 FROM fumetto_disegnatore fd WHERE fd.fumetto_id = f.id AND fd.disegnatore_id = d.id);

INSERT INTO fumetto_disegnatore (fumetto_id, disegnatore_id)
SELECT f.id, d.id
FROM fumetto f, disegnatore d
WHERE f.isbn = '9788899000134' AND d.nome = 'Steve' AND d.cognome = 'Ditko'
  AND NOT EXISTS (SELECT 1 FROM fumetto_disegnatore fd WHERE fd.fumetto_id = f.id AND fd.disegnatore_id = d.id);

INSERT INTO fumetto_disegnatore (fumetto_id, disegnatore_id)
SELECT f.id, d.id
FROM fumetto f, disegnatore d
WHERE f.isbn = '9788899000141' AND d.nome = 'Shinichi' AND d.cognome = 'Fukuda'
  AND NOT EXISTS (SELECT 1 FROM fumetto_disegnatore fd WHERE fd.fumetto_id = f.id AND fd.disegnatore_id = d.id);

-- ---------------------------------------------------------------------
-- RECENSIONI DI ESEMPIO
-- ---------------------------------------------------------------------
INSERT INTO recensione (id, titolo, testo, voto, data, autore_id, figure_id)
SELECT nextval('recensione_seq'), 'Scultura impeccabile', 'I dettagli del costume e le mani intercambiabili sono davvero notevoli per questa fascia di prezzo.', 5, DATE '2026-10-20', c.user_id, f.id
FROM credentials c, figure f WHERE c.username = 'mario' AND f.nome = 'S.H. Figuarts Spider-Man'
AND NOT EXISTS (SELECT 1 FROM recensione r WHERE r.autore_id = c.user_id AND r.figure_id = f.id);

INSERT INTO recensione (id, titolo, testo, voto, data, autore_id, figure_id)
SELECT nextval('recensione_seq'), 'Bella ma fragile', 'Ottima resa cromatica, pero'' gli accessori piu'' sottili vanno maneggiati con attenzione.', 4, DATE '2026-10-18', c.user_id, f.id
FROM credentials c, figure f WHERE c.username = 'giulia' AND f.nome = 'S.H. Figuarts Spider-Man'
AND NOT EXISTS (SELECT 1 FROM recensione r WHERE r.autore_id = c.user_id AND r.figure_id = f.id);

INSERT INTO recensione (id, titolo, testo, voto, data, autore_id, figure_id)
SELECT nextval('recensione_seq'), 'Vale il prezzo', 'Confezione curata, base solida e nessuna sbavatura di vernice. Consigliata a chi colleziona la serie.', 5, DATE '2026-10-15', c.user_id, f.id
FROM credentials c, figure f WHERE c.username = 'mario' AND f.nome = 'Nendoroid Marin Kitagawa'
AND NOT EXISTS (SELECT 1 FROM recensione r WHERE r.autore_id = c.user_id AND r.figure_id = f.id);

INSERT INTO recensione (id, titolo, testo, voto, data, autore_id, figure_id)
SELECT nextval('recensione_seq'), 'Sotto le aspettative', 'La posa e'' dinamica ma la verniciatura del mio esemplare presenta qualche imprecisione.', 3, DATE '2026-10-12', c.user_id, f.id
FROM credentials c, figure f WHERE c.username = 'giulia' AND f.nome = 'Hot Toys Neo'
AND NOT EXISTS (SELECT 1 FROM recensione r WHERE r.autore_id = c.user_id AND r.figure_id = f.id);
