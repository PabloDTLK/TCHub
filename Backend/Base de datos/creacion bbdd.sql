-- Crear secuencias para cada tabla
CREATE SEQUENCE coleccion_seq;
CREATE SEQUENCE usuario_seq;
CREATE SEQUENCE oferta_seq;
CREATE SEQUENCE inventario_seq;
CREATE SEQUENCE carta_seq;
CREATE SEQUENCE edicion_seq;
CREATE SEQUENCE version_seq;
CREATE SEQUENCE idioma_seq;
CREATE SEQUENCE expansion_seq;
CREATE SEQUENCE juego_seq;
CREATE SEQUENCE catalogo_seq;

-- Crear la tabla Colección
CREATE TABLE coleccion (
    id BIGINT DEFAULT nextval('coleccion_seq') PRIMARY KEY,
    nombre VARCHAR(255),
    descripcion TEXT,
    usuario_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Crear la tabla Usuario
CREATE TABLE usuario (
    id BIGINT DEFAULT nextval('usuario_seq') PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255)
);

-- Crear la tabla Oferta
CREATE TABLE oferta (
    id BIGINT DEFAULT nextval('oferta_seq') PRIMARY KEY,
    precio DOUBLE PRECISION,
    descripcion TEXT,
    activa BOOLEAN,
    fechaPublicacion DATE,
    usuario_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Crear la tabla Inventario
CREATE TABLE inventario (
    id BIGINT DEFAULT nextval('inventario_seq') PRIMARY KEY,
    coleccion_id BIGINT,
    version_id BIGINT,
    cantidad BIGINT,
    FOREIGN KEY (coleccion_id) REFERENCES coleccion(id),
    FOREIGN KEY (version_id) REFERENCES version(id)
);

-- Crear la tabla Carta
CREATE TABLE carta (
    id BIGINT DEFAULT nextval('carta_seq') PRIMARY KEY,
    id_coleccion BIGINT,
    FOREIGN KEY (id_coleccion) REFERENCES coleccion(id)
);

-- Crear la tabla Edición
CREATE TABLE edicion (
    id BIGINT DEFAULT nextval('edicion_seq') PRIMARY KEY,
    fecha_lanzamiento DATE,
    autor VARCHAR(255),
    carta_id BIGINT,
    FOREIGN KEY (carta_id) REFERENCES carta(id)
);

-- Crear la tabla Versión
CREATE TABLE version (
    id BIGINT DEFAULT nextval('version_seq') PRIMARY KEY,
    nombre VARCHAR(255),
    descripcion TEXT,
    imagenUrl VARCHAR(255),
    PrecioMedio DOUBLE PRECISION,
    edicion_id BIGINT,
    idioma_id BIGINT,
    FOREIGN KEY (edicion_id) REFERENCES edicion(id),
    FOREIGN KEY (idioma_id) REFERENCES idioma(id)
);

-- Crear la tabla Idioma
CREATE TABLE idioma (
    id BIGINT DEFAULT nextval('idioma_seq') PRIMARY KEY,
    valor VARCHAR(255),
    codigo VARCHAR(255)
);

-- Crear la tabla Expansión
CREATE TABLE expansion (
    id BIGINT DEFAULT nextval('expansion_seq') PRIMARY KEY,
    nombre VARCHAR(255),
    fecha_lanzamiento DATE,
    logoUrl VARCHAR(255),
    juego_id BIGINT,
    FOREIGN KEY (juego_id) REFERENCES juego(id)
);

-- Crear la tabla Juego
CREATE TABLE juego (
    id BIGINT DEFAULT nextval('juego_seq') PRIMARY KEY,
    nombre VARCHAR(255),
    codigo VARCHAR(255),
    logoUrl VARCHAR(255)
);

-- Crear la tabla Catálogo
CREATE TABLE catalogo (
    id BIGINT DEFAULT nextval('catalogo_seq') PRIMARY KEY,
    cantidad BIGINT,
    version_id BIGINT,
    FOREIGN KEY (version_id) REFERENCES version(id)
);

-- Crear la tabla Usuario_detalles
CREATE TABLE usuario_detalles (
    id BIGINT PRIMARY KEY,
    nombre VARCHAR(255),
    apellido VARCHAR(255),
    telefono VARCHAR(255),
    email VARCHAR(255)
);