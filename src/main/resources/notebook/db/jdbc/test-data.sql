INSERT INTO users (name, surname, username, email, password, enabled) VALUES ('Ivo', 'Ivić', 'iivic', 'email@tvz.hr', '$2a$10$NO8fr78xuYNksefvUXhb8edsglRjqZeIJ0ScISS49zvrh/iRiEoYy', true);
INSERT INTO users (name, surname, username, email, password, enabled) VALUES ('Marko', 'Markić', 'mmarkic', 'email@tvz.hr', '$2a$10$NO8fr78xuYNksefvUXhb8edsglRjqZeIJ0ScISS49zvrh/iRiEoYy', true);
INSERT INTO users (name, surname, username, email, password, enabled) VALUES ('Pero', 'Perić', 'pperic', 'email@tvz.hr', '$2a$10$NO8fr78xuYNksefvUXhb8edsglRjqZeIJ0ScISS49zvrh/iRiEoYy', true);
INSERT INTO users (name, surname, username, email, password, enabled) VALUES ('Adminko', 'Adminić', 'adminko', 'email@tvz.hr', '$2a$10$NO8fr78xuYNksefvUXhb8edsglRjqZeIJ0ScISS49zvrh/iRiEoYy', true);
INSERT INTO users (name, surname, username, email, password, enabled) VALUES ('Admin', 'Admin', 'admin', 'admin@tvz.hr', '$2a$10$g8ihG4QVH.pdzpAk0PuCLOiRaeCQus7bDPDtqcLIw3J87cUP5pBv2', true);
INSERT INTO users (name, surname, username, email, password, enabled) VALUES ('User', 'resU', 'user', 'user@tvz.hr', '$2a$10$zp6HrNn7okDuphq38pZJie1glfzjLfOvpkpirIVJzldWufVHNFh2y', true);

INSERT INTO user_roles (user, role) VALUES (1, 'ROLE_USER');
INSERT INTO user_roles (user, role) VALUES (2, 'ROLE_USER');
INSERT INTO user_roles (user, role) VALUES (3, 'ROLE_USER');
INSERT INTO user_roles (user, role) VALUES (4, 'ROLE_ADMIN');
INSERT INTO user_roles (user, role) VALUES (5, 'ROLE_USER');
INSERT INTO user_roles (user, role) VALUES (5, 'ROLE_ADMIN');
INSERT INTO user_roles (user, role) VALUES (6, 'ROLE_USER');

INSERT INTO notebooks (title, description) VALUES('Web aplikacije u Javi', 'Bilješke s predavanja iz kolegija web aplikacije u Javi');
INSERT INTO notebooks (title, description) VALUES('Razvoj aplikacija na Android platformi', 'Bilješke s predavanja kolegija razvoj aplikacija na Android platformi');
INSERT INTO notebooks (title, description) VALUES('Napredne teme računalnih mreža', 'Bilješke s predavanja napredne teme računalnih mreža'); 

INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Testni naslov', 'Testni tekst', 1, 1, 'NOT_IMPORTANT', null, 'ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Prva bilješka', 'Tekst prve bilješke', 6, 2, 'IMPORTANT', 'RED', 'NOT_ACTIVE'); 
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Druga bilješka', 'Tekst druge bilješke', 6, 1, 'IMPORTANT', 'GREEN', 'ACTIVE'); 
