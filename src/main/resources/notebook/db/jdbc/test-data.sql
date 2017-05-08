INSERT INTO users (name, surname, username, password, enabled) VALUES ('Ivo', 'Ivić', 'iivic', '$2a$10$NO8fr78xuYNksefvUXhb8edsglRjqZeIJ0ScISS49zvrh/iRiEoYy', true);
INSERT INTO users (name, surname, username, password, enabled) VALUES ('Marko', 'Markić', 'mmarkic', '$2a$10$NO8fr78xuYNksefvUXhb8edsglRjqZeIJ0ScISS49zvrh/iRiEoYy', true);
INSERT INTO users (name, surname, username, password, enabled) VALUES ('Pero', 'Perić', 'pperic', '$2a$10$NO8fr78xuYNksefvUXhb8edsglRjqZeIJ0ScISS49zvrh/iRiEoYy', true);
INSERT INTO users (name, surname, username, password, enabled) VALUES ('Adminko', 'Adminić', 'adminko','$2a$10$NO8fr78xuYNksefvUXhb8edsglRjqZeIJ0ScISS49zvrh/iRiEoYy', true);
INSERT INTO users (name, surname, username, password, enabled) VALUES ('Admin', 'Admin', 'admin', '$2a$10$g8ihG4QVH.pdzpAk0PuCLOiRaeCQus7bDPDtqcLIw3J87cUP5pBv2', true);
INSERT INTO users (name, surname, username, password, enabled) VALUES ('User', 'resU', 'user', '$2a$10$zp6HrNn7okDuphq38pZJie1glfzjLfOvpkpirIVJzldWufVHNFh2y', true);

INSERT INTO user_roles (username, role) VALUES ('iivic', 'ROLE_USER');
INSERT INTO user_roles (username, role) VALUES ('mmarkic', 'ROLE_USER');
INSERT INTO user_roles (username, role) VALUES ('pperic', 'ROLE_USER');
INSERT INTO user_roles (username, role) VALUES ('adminko', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles (username, role) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role) VALUES ('user', 'ROLE_USER');

INSERT INTO notebooks (title, description) VALUES('Web aplikacije u Javi', 'Bilješke s predavanja iz kolegija web aplikacije u Javi');
INSERT INTO notebooks (title, description) VALUES('Razvoj aplikacija na Android platformi', 'Bilješke s predavanja kolegija razvoj aplikacija na Android platformi');
INSERT INTO notebooks (title, description) VALUES('Napredne teme računalnih mreža', 'Bilješke s predavanja napredne teme računalnih mreža'); 

INSERT INTO notes (header, text, user, notebook, important, mark) VALUES('Testni naslov', 'Testni tekst', 'iivic', 'Web aplikacije u Javi', false, null);
INSERT INTO notes (header, text, user, notebook, important, mark) VALUES('Prva bilješka', 'Tekst prve bilješke', 'user', 'Razvoj aplikacija na Android platformi', true, 'red'); 
INSERT INTO notes (header, text, user, notebook, important, mark) VALUES('Druga bilješka', 'Tekst druge bilješke', 'user', 'Web aplikacije u Javi', true, 'green'); 
