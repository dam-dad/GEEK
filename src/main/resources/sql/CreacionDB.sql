CREATE TABLE Usuarios (
	ID SERIAL,
    nombre VARCHAR(40),
    nombreUsuario VARCHAR(40) NOT NULL,
    password VARCHAR(100) NOT NULL,
    imagen bytea,
    CONSTRAINT PK_Usuarios PRIMARY KEY (ID)
);

CREATE TABLE Filtros (
	ID SERIAL,
    nombre VARCHAR(50),
    shortname VARCHAR(10),
    descripcion TEXT,
    CONSTRAINT PK_Filtros PRIMARY KEY (ID)    
);

CREATE TABLE Posts(
	ID SERIAL,
    ID_Usuario INT8 NOT NULL,
    titulo VARCHAR(50),
    contenido TEXT,
    imagen bytea,
    filtros TEXT,
    CONSTRAINT PK_Posts PRIMARY KEY (ID),
    CONSTRAINT FK_Posts_Usuarios FOREIGN KEY (ID_Usuario) REFERENCES Usuarios(ID)
);


INSERT INTO Usuarios (nombre, nombreUsuario, password)
VALUES ("User_01", "User_01", "User_01_password");

INSERT INTO Usuarios (nombre, nombreUsuario, password)
VALUES ("User_02", "User_02", "User_02_password");

INSERT INTO Usuarios (nombre, nombreUsuario, password)
VALUES ("User_03", "User_03", "User_03_password");

INSERT INTO Usuarios (nombre, nombreUsuario, password)
VALUES ("User_04", "User_04", "User_04_password");

INSERT INTO Filtros (nombre, shortname, descripcion)
VALUES ("Filtro_01", "Filt_01", "Descripcion_01_01");

INSERT INTO Filtros (nombre, shortname, descripcion)
VALUES ("Filtro_02", "Filt_02", "Descripcion_02_02");

INSERT INTO Filtros (nombre, shortname, descripcion)
VALUES ("Filtro_03", "Filt_03", "Descripcion_03_03");

INSERT INTO Filtros (nombre, shortname, descripcion)
VALUES ("Filtro_04", "Filt_04", "Descripcion_04_04");

INSERT INTO Posts (ID_Usuario, titulo, contenido)
VALUES (1, "POST_01", "Prueba Post_01");

INSERT INTO Posts (ID_Usuario, titulo, contenido)
VALUES (2, "POST_02", "Prueba Post_02");

INSERT INTO Posts (ID_Usuario, titulo, contenido)
VALUES (3, "POST_03", "Prueba Post_03");

INSERT INTO Posts (ID_Usuario, titulo, contenido)
VALUES (4, "POST_04", "Prueba Post_04");