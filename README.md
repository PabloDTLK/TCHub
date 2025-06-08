## Instalación

Sigue estos pasos para configurar y ejecutar el proyecto en tu entorno de desarrollo local:

### **Requisitos Previos:**

* **Node.js y npm:** Asegúrate de tener Node.js instalado (se recomienda una versión LTS reciente, ej: v18.x, v20.x o v22.x). npm viene incluido con Node.js.
  * Verifica con: `node -v` y `npm -v`
* **Angular CLI:** Instala Angular CLI globalmente si aún no lo has hecho:
  ```bash
  npm install -g @angular/cli

Verifica con: ng version

Java 17+ y Maven (para el backend):

Verifica con: java -version y mvn -version




---

Pasos de Instalación del Frontend (Angular):

1. Clonar el Repositorio (si aplica):
Si el código está en un repositorio Git:

git clone https://github.com/PabloDTLK/TCHub.git

cd TCHub

Si ya tienes los archivos del proyecto, navega a la carpeta raíz del proyecto en tu terminal.


2. Instalar Dependencias:
Desde la carpeta raíz del frontend, ejecuta el siguiente comando para instalar todas las librerías necesarias definidas en package.json:

npm install

npm install jwt-decode

3. Configurar el Entorno (Conexión con el Backend):
Asegúrate de que la propiedad baseUrl en src/environments/environment.ts apunte a la URL correcta del backend:

export const environment = {
  production: false,
  baseUrl: 'http://localhost:8080/api'
};


4. Ejecutar la Aplicación en Modo Desarrollo:
Una vez completados los pasos anteriores, puedes iniciar el servidor de desarrollo de Angular:

ng serve -o

---

Pasos de Instalación del Backend (Spring Boot):

1. Ubícate en la Carpeta del Backend:
En una terminal nueva, navega hasta la carpeta donde se encuentra el proyecto de Spring Boot

cd backend


2. Construir y Ejecutar la Aplicación:
Utiliza Maven para compilar y ejecutar el backend:

mvn spring-boot:run

Esto iniciará el servidor backend en http://localhost:8080 (puerto por defecto).


3. Verifica que el Backend esté Funcionando:
Puedes abrir en el navegador o usar herramientas como Postman para acceder a:

http://localhost:8080/api




---

Con ambos servidores (frontend y backend) en funcionamiento, ya puedes utilizar la aplicación completa en desarrollo. La aplicación Angular consumirá los datos del backend de Spring Boot a través de la URL definida en environment.ts.

