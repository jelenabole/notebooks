drop table if exists users;
drop table if exists user_roles;
drop table if exists notebooks;
drop table if exists notes;

CREATE  TABLE users (
	id INT(11) IDENTITY PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	surname VARCHAR(50) NOT NULL,
	username VARCHAR(50),
	email VARCHAR(50),
	password VARCHAR(100) NOT NULL,
	enabled TINYINT NOT NULL DEFAULT 1
);

CREATE TABLE user_roles (
	id int(11) IDENTITY PRIMARY KEY,
	user INT NOT NULL,
	role varchar(50) NOT NULL,
	FOREIGN KEY (user) REFERENCES users (id)
);

CREATE TABLE notebooks (
	id INT(11) IDENTITY PRIMARY KEY,
	title VARCHAR(50),
	description VARCHAR(100) NOT NULL
);


CREATE TABLE notes (
	id INT(11) IDENTITY PRIMARY KEY,
	header VARCHAR(100),
	text VARCHAR(1000),
	user INT NOT NULL,
	notebook INT NOT NULL,
	importance VARCHAR(20) NOT NULL DEFAULT 'NOT_IMPORTANT',
	mark VARCHAR(20) DEFAULT NULL,
	status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
	FOREIGN KEY (user) REFERENCES users (id),
	FOREIGN KEY (notebook) REFERENCES notebooks (id)
);
