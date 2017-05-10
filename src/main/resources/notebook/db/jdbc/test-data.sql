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

INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Nullam libero', 'Sed eu iaculis nulla.', 1, 1, 'NOT_IMPORTANT', null, 'ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Fusce vel molestie', 'Mauris ante ex, condimentum at sem.', 1, 3, 'IMPORTANT', 'PURPLE', 'ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Aliquam tempor', 'Fusce vel molestie lorem.', 2, 1, 'IMPORTANT', 'ORANGE', 'NOT_ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Morbi bibendum', 'Etiam ut urna ultricies.', 2, 2, 'NOT_IMPORTANT', null, 'ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Donec maximus', 'Orci varius natoque et magnis.', 3, 3, 'NOT_IMPORTANT', null, 'NOT_ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Cras interdum', 'Nam sed elementum nunc.', 3, 3, 'IMPORTANT', 'DEFAULT', 'NOT_ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Donec facilisis', 'Tortor ut diam auctor.', 3, 3, 'NOT_IMPORTANT', null, 'ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Class aptent', 'Felis ac tellus euismod.', 4, 2, 'IMPORTANT', 'PURPLE', 'ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Vestibulum luctus', 'Nam vitae urna ut lacus rhoncus.', 4, 2, 'NOT_IMPORTANT', null, 'ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Praesent commodo', 'Fusce id felis finibus.', 4, 3, 'IMPORTANT', 'BLUE', 'NOT_ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Pellentesque habitant', 'Cras dictum felis a justo interdum.', 5, 1, 'IMPORTANT', 'ORANGE', 'ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Quisque malesuada', 'Praesent volutpat mollis semper.', 5, 2, 'NOT_IMPORTANT', null, 'NOT_ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Vivamus elementum', 'Libero non neque laoreet blandit.', 5, 3, 'IMPORTANT', 'RED', 'ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Aliquam cursus', 'Donec lorem ex, luctus nec erat quis.', 5, 3, 'IMPORTANT', 'BLUE', 'ACTIVE');
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Vivamus fringilla', 'Pellentesque nisi at elementum.', 6, 2, 'IMPORTANT', 'RED', 'NOT_ACTIVE'); 
INSERT INTO notes (header, text, user, notebook, importance, mark, status) VALUES('Suspendisse lobortis', 'Ut miscelerisque quis bibendum.', 6, 1, 'IMPORTANT', 'GREEN', 'ACTIVE'); 
