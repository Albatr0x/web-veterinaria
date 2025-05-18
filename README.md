# Demo de Autenticación Multifactor

Este proyecto es una aplicación Spring Boot que implementa un sistema de autenticación con soporte para autenticación de dos factores (2FA) utilizando TOTP (Time-based One-Time Password).

## Estructura del Proyecto

```
src/main/java/com/example/seguridad/
├── configuracion/
│   └── SecurityConfig.java         # Configuración de seguridad de Spring
├── controller/
│   ├── AuthController.java         # Controlador para autenticación básica
│   ├── MFAController.java          # Controlador para autenticación 2FA
│   └── TwoFactorAuthController.java # Controlador para configuración 2FA
├── filtro/
│   ├── FilterConfig.java           # Configuración de filtros
│   └── TwoFactorAuthFilter.java    # Filtro para verificación 2FA
├── model/
│   └── Usuario.java               # Modelo de usuario
├── repository/
│   └── UsuarioRepository.java     # Repositorio para acceso a datos
├── service/
│   ├── CustomUserDetailsService.java    # Servicio de detalles de usuario
│   ├── TwoFactorAuthenticationService.java # Servicio para 2FA
│   └── UsuarioService.java        # Servicio de gestión de usuarios
└── DemoApplication.java           # Clase principal de la aplicación
```

## Componentes Principales

### Modelo de Usuario (`Usuario.java`)
- Implementa `UserDetails` de Spring Security
- Almacena información del usuario incluyendo credenciales y configuración 2FA
- Campos principales:
  - `username`: Nombre de usuario único
  - `password`: Contraseña encriptada
  - `secretKey`: Clave secreta para 2FA
  - `using2fa`: Indicador de si 2FA está activo

### Controladores

#### `AuthController.java`
- Maneja el registro y login básico
- Endpoints:
  - `/login`: Página de inicio de sesión
  - `/registro`: Registro de nuevos usuarios
  - `/dashboard`: Panel principal después del login

#### `MFAController.java`
- Gestiona la autenticación de dos factores
- Endpoints:
  - `/mfa`: Configuración y verificación 2FA
  - Genera códigos QR para configuración

#### `TwoFactorAuthController.java`
- Maneja la configuración inicial de 2FA
- Endpoints:
  - `/2fa/setup`: Configuración inicial de 2FA
  - `/2fa/verify`: Verificación de códigos 2FA

### Servicios

#### `UsuarioService.java`
- Gestiona operaciones de usuario
- Métodos principales:
  - `registrarUsuario`: Registra nuevos usuarios
  - `generarSecreto2FA`: Genera clave secreta para 2FA
  - `generarQRCode`: Genera código QR para configuración
  - `verificarCodigo2FA`: Verifica códigos 2FA

#### `TwoFactorAuthenticationService.java`
- Implementa la lógica de autenticación 2FA
- Métodos principales:
  - `generateSecretKey`: Genera clave secreta
  - `generateQrCode`: Genera código QR
  - `verifyCode`: Verifica códigos TOTP

### Filtros

#### `TwoFactorAuthFilter.java`
- Filtro que verifica si se requiere autenticación 2FA
- Redirige a la página de verificación 2FA cuando es necesario

## Cómo Probar la Aplicación

### 1. Registrar un Nuevo Usuario

1. Accede a la aplicación en `http://localhost:8080/registro`
2. Completa el formulario con:
   - Nombre de usuario
   - Contraseña
3. Haz clic en "Registrarse"

### 2. Iniciar Sesión

1. Ve a `http://localhost:8080/login`
2. Ingresa tus credenciales:
   - Usuario
   - Contraseña
3. Haz clic en "Ingresar"

### 3. Configurar 2FA (Opcional)

1. Después del login, serás redirigido a la página de configuración 2FA
2. Escanea el código QR con una aplicación de autenticación (Google Authenticator, Authy, etc.)
3. Ingresa el código de verificación generado por la aplicación
4. Haz clic en "Verificar"

### 4. Acceder al Dashboard

1. Después de la autenticación exitosa, serás redirigido al dashboard
2. Si 2FA está activo, deberás ingresar el código de verificación en cada inicio de sesión

## Requisitos Técnicos

- Java 17 o superior
- Maven
- Base de datos H2 (configurada por defecto)
- Navegador web moderno
- Aplicación de autenticación 2FA (Google Authenticator, Authy, etc.)

## Dependencias Principales

- Spring Boot Starter Web
- Spring Security
- Spring Data JPA
- Thymeleaf
- TOTP (Time-based One-Time Password)
- ZXing (para generación de códigos QR)
- H2 Database

## Notas de Seguridad

- Las contraseñas se almacenan encriptadas usando BCrypt
- La autenticación 2FA utiliza el estándar TOTP
- Las sesiones se invalidan al cerrar sesión
- Se implementan medidas de protección contra CSRF 