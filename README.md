# Sistema de Veterinarias

Este proyecto es un sistema de gestión para veterinarias desarrollado con Spring Boot, implementando una arquitectura en capas y siguiendo los principios de Clean Architecture.

## Estructura del Proyecto

```
sist-veterinarias/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── Usuario/
│   │   └── resources/
│   └── test/
├── target/
├── .mvn/
├── .git/
├── pom.xml
├── Dockerfile
└── docker-compose.yml
```

## Arquitectura del Proyecto

El proyecto sigue una arquitectura en capas, separando las responsabilidades en diferentes niveles:

### 1. Capa de Presentación (Controllers)
- Maneja las peticiones HTTP
- Implementa la lógica de autenticación y autorización
- Gestiona la interacción con el usuario

### 2. Capa de Servicio (Services)
- Implementa la lógica de negocio
- Coordina las operaciones entre diferentes componentes
- Maneja la autenticación de dos factores (2FA)

### 3. Capa de Dto ()
- Implementa las entidades para consumir las APIs.

### 4. Capa de Modelo (Models)
- Define las entidades del sistema
- Contiene los DTOs (Data Transfer Objects)
- Implementa la lógica de validación

## Tecnologías Utilizadas

- Java 21
- Spring Boot 3.4.3
- Spring Security
- Spring Data JPA
- Thymeleaf
- TOTP (Two-Factor Authentication)
- ZXing (para códigos QR)
- Docker y Docker Compose

## Requisitos Previos

- Java 21 o superior
- Maven
- Docker y Docker Compose (opcional)

## Configuración y Ejecución

### 1. Configuración del Entorno

1. Clonar el repositorio:
```bash
git clone [URL_DEL_REPOSITORIO]
cd sist-veterinarias
```

2. Configurar las variables de entorno (si es necesario):
- Crear un archivo `.env` en la raíz del proyecto
- Configurar las variables necesarias (base de datos, puertos, etc.)

### 2. Ejecución Local

1. Compilar el proyecto:
```bash
mvn clean install
```

2. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:9300`

### 3. Ejecución con Docker

1. Construir la imagen:
```bash
docker-compose build
```

2. Iniciar los contenedores:
```bash
docker-compose up -d
```

## Características Principales

- Autenticación de usuarios
- Autenticación de dos factores (2FA)
- Gestión de usuarios
- Interfaz web con Thymeleaf
- API RESTful
- Seguridad con Spring Security

## Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.
