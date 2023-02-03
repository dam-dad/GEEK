CREATE TABLE Usuarios (
	ID SERIAL,
    nombre VARCHAR(40),
    nombreUsuario VARCHAR(40) NOT NULL,
    password VARCHAR(100) NOT NULL,
    imagen TEXT,
    CONSTRAINT PK_Usuarios PRIMARY KEY (ID)
);

CREATE TABLE Filtros (
	ID SERIAL,
    nombre VARCHAR(50),
    shortname VARCHAR(10),
    descripcion TEXT,
    CONSTRAINT PK_Filtros PRIMARY KEY (ID)    
);

CREATE TABLE FiltrosUsuario (
	ID_Usuario INT NOT NULL,
    ID_Filtro INT NOT NULL,
    CONSTRAINT PK_FiltrosUsuaio PRIMARY KEY (ID_Usuario, ID_Filtro),
    CONSTRAINT FK_FiltrosUsuarios_Usuarios FOREIGN KEY (ID_Usuario) REFERENCES Usuarios(ID),
    CONSTRAINT FK_FiltrosUsuarios_Filtros FOREIGN KEY (ID_Filtro) REFERENCES Filtros(ID)
);

CREATE TABLE Posts(
	ID SERIAL,
    ID_Usuario INT NOT NULL,
    titulo VARCHAR(50),
    contenido TEXT,
    imagen TEXT,
    CONSTRAINT PK_Posts PRIMARY KEY (ID),
    CONSTRAINT FK_Posts_Usuarios FOREIGN KEY (ID_Usuario) REFERENCES Usuarios(ID)
);

CREATE TABLE FiltrosPost (
	ID_Post INT NOT NULL,
    ID_Filtro INT NOT NULL,
    CONSTRAINT PK_FiltrosPosts PRIMARY KEY (ID_Post, ID_Filtro),
    CONSTRAINT FK_FiltrosPost_Posts FOREIGN KEY (ID_Post) REFERENCES Posts(ID),
    CONSTRAINT FK_FiltrosPost_Filtros FOREIGN KEY (ID_Filtro) REFERENCES Filtros(ID)
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