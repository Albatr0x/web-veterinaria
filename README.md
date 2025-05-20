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
│   │   │           ├── Usuario/
│   │   │           │   ├── controllers/
│   │   │           │   │   ├── AuthController.java
│   │   │           │   │   └── UsuarioController.java
│   │   │           │   ├── services/
│   │   │           │   │   ├── AuthService.java
│   │   │           │   │   └── UsuarioService.java
│   │   │           │   ├── repositories/
│   │   │           │   │   └── UsuarioRepository.java
│   │   │           │   ├── models/
│   │   │           │   │   └── Usuario.java
│   │   │           │   └── dto/
│   │   │           │       ├── UsuarioDTO.java
│   │   │           │       └── LoginDTO.java
│   │   │           ├── Mascota/
│   │   │           │   ├── controllers/
│   │   │           │   │   └── MascotaController.java
│   │   │           │   ├── services/
│   │   │           │   │   └── MascotaService.java
│   │   │           │   ├── repositories/
│   │   │           │   │   └── MascotaRepository.java
│   │   │           │   ├── models/
│   │   │           │   │   └── Mascota.java
│   │   │           │   └── dto/
│   │   │           │       ├── MascotaDTO.java
│   │   │           │       └── MascotaResponseDTO.java
│   │   │           └── Cita/
│   │   │               ├── controllers/
│   │   │               │   └── CitaController.java
│   │   │               ├── services/
│   │   │               │   └── CitaService.java
│   │   │               ├── repositories/
│   │   │               │   └── CitaRepository.java
│   │   │               ├── models/
│   │   │               │   └── Cita.java
│   │   │               └── dto/
│   │   │                   ├── CitaDTO.java
│   │   │                   └── CitaResponseDTO.java
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
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
- **AuthController**: Maneja la autenticación y autorización
  - `login()`: Procesa el inicio de sesión
  - `register()`: Registra nuevos usuarios
  - `verify2FA()`: Verifica el código 2FA
  - `setup2FA()`: Configura la autenticación de dos factores

- **UsuarioController**: Gestiona las operaciones de usuarios
  - `getAll()`: Obtiene todos los usuarios
  - `getById()`: Obtiene un usuario por ID
  - `create()`: Crea un nuevo usuario
  - `update()`: Actualiza un usuario existente
  - `delete()`: Elimina un usuario

- **MascotaController**: Gestiona las operaciones de mascotas
  - `getAll()`: Obtiene todas las mascotas
  - `getById()`: Obtiene una mascota por ID
  - `getByUsuario()`: Obtiene mascotas por usuario
  - `create()`: Crea una nueva mascota
  - `update()`: Actualiza una mascota existente
  - `delete()`: Elimina una mascota
  - `getHistorialMedico()`: Obtiene el historial médico de una mascota

- **CitaController**: Gestiona las operaciones de citas
  - `getAll()`: Obtiene todas las citas
  - `getById()`: Obtiene una cita por ID
  - `getByMascota()`: Obtiene citas por mascota
  - `getByVeterinario()`: Obtiene citas por veterinario
  - `create()`: Crea una nueva cita
  - `update()`: Actualiza una cita existente
  - `delete()`: Elimina una cita
  - `confirmarCita()`: Confirma una cita
  - `cancelarCita()`: Cancela una cita

### 2. Capa de Servicio (Services)
- **AuthService**: Implementa la lógica de autenticación
  - `authenticate()`: Autentica usuarios
  - `generate2FASecret()`: Genera secreto para 2FA
  - `verify2FACode()`: Verifica códigos 2FA
  - `generateQRCode()`: Genera código QR para 2FA

- **UsuarioService**: Implementa la lógica de negocio de usuarios
  - `findAll()`: Obtiene todos los usuarios
  - `findById()`: Busca usuario por ID
  - `save()`: Guarda/actualiza usuario
  - `delete()`: Elimina usuario

- **MascotaService**: Implementa la lógica de negocio de mascotas
  - `findAll()`: Obtiene todas las mascotas
  - `findById()`: Busca mascota por ID
  - `findByUsuario()`: Busca mascotas por usuario
  - `save()`: Guarda/actualiza mascota
  - `delete()`: Elimina mascota
  - `getHistorialMedico()`: Obtiene historial médico
  - `validarDatosMascota()`: Valida datos de mascota

- **CitaService**: Implementa la lógica de negocio de citas
  - `findAll()`: Obtiene todas las citas
  - `findById()`: Busca cita por ID
  - `findByMascota()`: Busca citas por mascota
  - `findByVeterinario()`: Busca citas por veterinario
  - `save()`: Guarda/actualiza cita
  - `delete()`: Elimina cita
  - `confirmarCita()`: Confirma cita
  - `cancelarCita()`: Cancela cita
  - `validarDisponibilidad()`: Valida disponibilidad de horario

### 3. Capa de Repositorio (Repositories)
- **UsuarioRepository**: Gestiona el acceso a datos de usuarios
  - `findByEmail()`: Busca usuario por email
  - `findByUsername()`: Busca usuario por nombre de usuario
  - Métodos CRUD heredados de JpaRepository

- **MascotaRepository**: Gestiona el acceso a datos de mascotas
  - `findByUsuarioId()`: Busca mascotas por ID de usuario
  - `findByEspecie()`: Busca mascotas por especie
  - Métodos CRUD heredados de JpaRepository

- **CitaRepository**: Gestiona el acceso a datos de citas
  - `findByMascotaId()`: Busca citas por ID de mascota
  - `findByVeterinarioId()`: Busca citas por ID de veterinario
  - `findByFechaBetween()`: Busca citas en un rango de fechas
  - Métodos CRUD heredados de JpaRepository

### 4. Capa de Modelo (Models)
- **Usuario**: Entidad principal de usuarios
  - Atributos: id, username, email, password, enabled, secret2FA
  - Métodos: getters, setters, equals, hashCode

- **Mascota**: Entidad principal de mascotas
  - Atributos: id, nombre, especie, raza, edad, peso, usuario
  - Métodos: getters, setters, equals, hashCode

- **Cita**: Entidad principal de citas
  - Atributos: id, fecha, hora, estado, mascota, veterinario, motivo
  - Métodos: getters, setters, equals, hashCode

### 5. Capa de DTO (Data Transfer Objects)
- **UsuarioDTO**: Para transferencia de datos de usuario
  - Atributos: id, username, email, enabled

- **LoginDTO**: Para transferencia de datos de login
  - Atributos: username, password, code2FA

- **MascotaDTO**: Para transferencia de datos de mascota
  - Atributos: id, nombre, especie, raza, edad, peso, usuarioId

- **MascotaResponseDTO**: Para respuesta de datos de mascota
  - Atributos: id, nombre, especie, raza, edad, peso, usuario

- **CitaDTO**: Para transferencia de datos de cita
  - Atributos: id, fecha, hora, estado, mascotaId, veterinarioId, motivo

- **CitaResponseDTO**: Para respuesta de datos de cita
  - Atributos: id, fecha, hora, estado, mascota, veterinario, motivo

## Configuración del application.properties

El archivo `src/main/resources/application.properties` debe ser configurado con los siguientes valores:

```properties
# Configuración del servidor
server.port=9300

# Configuración de logging
logging.level.org.springframework.security=DEBUG
logging.level.com.example=DEBUG

# Configuración de APIs externas
# API de Usuarios - Debe estar disponible en el puerto 9100
api.usuarios.base-url=http://localhost:9100/api/usuarios
api.usuarios.timeout=5000
api.usuarios.retry-attempts=3

# API de Citas - Debe estar disponible en el puerto 8081
api.citas.base-url=http://localhost:8081/api/citas
api.citas.timeout=5000
api.citas.retry-attempts=3

# Configuración de CORS
spring.web.cors.allowed-origins=http://localhost:8080,http://localhost:8081
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE
spring.web.cors.allowed-headers=*
```

### Requisitos de APIs Externas

El sistema requiere que las siguientes APIs estén disponibles y funcionando antes de iniciar la aplicación:

1. **API de Usuarios** (Puerto 8080)
   - Debe estar ejecutándose en `http://localhost:8080`
   - Endpoints requeridos:
     - `/api/usuarios/register` - Registro de usuarios
     - `/api/usuarios/login` - Autenticación
     - `/api/usuarios/verify-2fa` - Verificación 2FA
     - `/api/usuarios/profile` - Perfil de usuario

2. **API de Citas** (Puerto 8081)
   - Debe estar ejecutándose en `http://localhost:8081`
   - Endpoints requeridos:
     - `/api/citas/create` - Creación de citas
     - `/api/citas/update` - Actualización de citas
     - `/api/citas/list` - Listado de citas
     - `/api/citas/cancel` - Cancelación de citas

### Verificación de APIs

Antes de iniciar la aplicación, verifica que las APIs estén disponibles ejecutando:

```bash
# Verificar API de Usuarios
curl http://localhost:8080/api/usuarios/health

# Verificar API de Citas
curl http://localhost:8081/api/citas/health
```

Si alguna de las APIs no está disponible, la aplicación no podrá funcionar correctamente.

## Tecnologías Utilizadas

- Java 21
- Spring Boot 3.4.3
- Spring Security
- Spring Data JPA
- Thymeleaf
- TOTP (Two-Factor Authentication)
- ZXing (para códigos QR)
- Docker y Docker Compose
- MySQL 8.0

## Requisitos Previos

- Java 21 o superior
- Maven
- MySQL 8.0
- Docker y Docker Compose (opcional)

## Configuración y Ejecución

### 1. Configuración del Entorno

1. Clonar el repositorio:
```bash
git clone [URL_DEL_REPOSITORIO]
cd sist-veterinarias
```

2. Configurar la base de datos:
- Crear una base de datos MySQL llamada `veterinaria_db`
- Actualizar las credenciales en `application.properties`

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
- Base de datos MySQL
- Logging detallado

## Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.
