drop table if exists users;
drop table if exists user_roles;
drop table if exists notebooks;
drop table if exists notes;

CREATE  TABLE users (
	id INT(11) IDENTITY PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	surname VARCHAR(50) NOT NULL,
	username VARCHAR(45),
	password VARCHAR(100) NOT NULL,
	enabled TINYINT NOT NULL DEFAULT 1
);

CREATE TABLE user_roles (
	id int(11) IDENTITY PRIMARY KEY,
	username varchar(45) NOT NULL,
	role varchar(45) NOT NULL,
	FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE notebooks (
	id INT(11) AUTO_INCREMENT,
	title VARCHAR(50) PRIMARY KEY,
	description VARCHAR(100) NOT NULL
);


CREATE TABLE notes (
	id INT(11) IDENTITY PRIMARY KEY,
	header VARCHAR(100),
	text VARCHAR(1000),
	user VARCHAR(45) NOT NULL,
	notebook VARCHAR(50) NOT NULL,
	important TINYINT DEFAULT 0,
	mark VARCHAR(20) DEFAULT NULL,
	FOREIGN KEY (user) REFERENCES users (username),
	FOREIGN KEY (notebook) REFERENCES notebooks (title)
);
