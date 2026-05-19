# Cambios API REST - Backend

Fecha: 2026-05-18
Rama: `feature/backend-3-4-api-rest`

Este documento resume los cambios hechos en el backend para que el equipo pueda retomar el trabajo, entender el contrato actual y diagnosticar problemas de despliegue o validacion.

## Resumen general

- Se completo la capa REST para la configuracion general del invernadero.
- Se implemento el centro de notificaciones in-app.
- Las alertas por umbral ahora generan notificaciones persistidas.
- Se mantuvieron las slices previas de auth, telemetria, dispositivos, actuadores, inventario, umbrales, alertas y preferencias de notificacion.

## Endpoints REST relevantes

### Autenticacion

- `POST /auth/login`
- `POST /auth/register`

### Telemetria y dispositivos

- `GET /api/sensors/latest`
- `GET /api/sensors/history/{device_id}`
- `GET /api/devices`

### Actuadores e inventario

- `POST /api/actuators/execute`
- `POST /api/inventory/consumption`

### Alertas y umbrales

- `GET /api/v1/alerts`
- `POST /api/v1/alerts/threshold`
- `POST /api/v1/threshold-rules`

### Preferencias y configuracion

- `PATCH /api/v1/users/{user_id}/notification-preferences`
- `GET /api/config`
- `PATCH /api/config`

### Centro de notificaciones

- `GET /api/v1/notifications`
- `PATCH /api/v1/notifications/{notification_id}`
- `DELETE /api/v1/notifications/{notification_id}`

## Contratos nuevos o ajustados

### Configuracion general

`GET /api/config` y `PATCH /api/config` trabajan como singleton global con estos campos:

```json
{
  "automatic_mode": false,
  "inactivity_threshold_minutes": 10,
  "manual_override_duration_minutes": 10,
  "report_timezone": "UTC",
  "updated_at": "2026-05-18T10:00:00Z"
}
```

El `PATCH` acepta actualizacion parcial. La zona horaria se valida con `ZoneId`.

### Centro de notificaciones

`GET /api/v1/notifications` devuelve la lista ordenada por fecha descendente.

```json
{
  "notifications": [
    {
      "notification_id": "string",
      "alert_id": "string",
      "title": "string",
      "message": "string",
      "severity": "warning | critical",
      "status": "unread | read | archived",
      "suggested_action": "string",
      "created_at": "ISO 8601"
    }
  ]
}
```

- `PATCH /api/v1/notifications/{notification_id}` actualiza `status` a `read` o `archived`.
- `DELETE /api/v1/notifications/{notification_id}` elimina la notificacion de forma permanente.

### Generacion de notificaciones desde alertas

La slice de alertas ahora persiste una notificacion cuando se crea una alerta de umbral, usando `NotificationRepositoryPort`.

## Archivos tocados

### Wiring y configuracion

- `src/main/java/com/proyectosu/invernadero/shared/config/ApplicationConfig.java`

### Configuracion general

- `src/main/java/com/proyectosu/invernadero/greenhouseconfig/inbound/controller/GreenhouseConfigController.java`
- `src/main/java/com/proyectosu/invernadero/greenhouseconfig/application/usecase/GetGreenhouseConfigUseCase.java`
- `src/main/java/com/proyectosu/invernadero/greenhouseconfig/application/usecase/UpdateGreenhouseConfigUseCase.java`
- `src/main/java/com/proyectosu/invernadero/greenhouseconfig/domain/model/GreenhouseConfig.java`
- `src/main/java/com/proyectosu/invernadero/greenhouseconfig/domain/ports/GreenhouseConfigRepositoryPort.java`
- `src/main/java/com/proyectosu/invernadero/greenhouseconfig/dto/request/GreenhouseConfigRequest.java`
- `src/main/java/com/proyectosu/invernadero/greenhouseconfig/dto/response/GreenhouseConfigResponse.java`
- `src/main/java/com/proyectosu/invernadero/greenhouseconfig/infrastructure/persistence/adapter/GreenhouseConfigRepositoryAdapter.java`
- `src/main/java/com/proyectosu/invernadero/greenhouseconfig/infrastructure/persistence/document/GreenhouseConfigDocument.java`
- `src/main/java/com/proyectosu/invernadero/greenhouseconfig/infrastructure/persistence/repository/GreenhouseConfigMongoRepository.java`
- `src/test/java/com/proyectosu/invernadero/greenhouseconfig/...`

### Centro de notificaciones

- `src/main/java/com/proyectosu/invernadero/notifications/center/inbound/controller/NotificationsController.java`
- `src/main/java/com/proyectosu/invernadero/notifications/center/application/usecase/ListNotificationsUseCase.java`
- `src/main/java/com/proyectosu/invernadero/notifications/center/application/usecase/UpdateNotificationStatusUseCase.java`
- `src/main/java/com/proyectosu/invernadero/notifications/center/application/usecase/DeleteNotificationUseCase.java`
- `src/main/java/com/proyectosu/invernadero/notifications/center/domain/model/Notification.java`
- `src/main/java/com/proyectosu/invernadero/notifications/center/domain/ports/NotificationRepositoryPort.java`
- `src/main/java/com/proyectosu/invernadero/notifications/center/dto/request/UpdateNotificationStatusRequest.java`
- `src/main/java/com/proyectosu/invernadero/notifications/center/dto/response/NotificationResponse.java`
- `src/main/java/com/proyectosu/invernadero/notifications/center/dto/response/NotificationsListResponse.java`
- `src/main/java/com/proyectosu/invernadero/notifications/center/dto/response/NotificationActionResponse.java`
- `src/main/java/com/proyectosu/invernadero/notifications/center/infrastructure/persistence/adapter/NotificationRepositoryAdapter.java`
- `src/main/java/com/proyectosu/invernadero/notifications/center/infrastructure/persistence/document/NotificationDocument.java`
- `src/main/java/com/proyectosu/invernadero/notifications/center/infrastructure/persistence/repository/NotificationMongoRepository.java`
- `src/test/java/com/proyectosu/invernadero/notifications/center/...`

### Alertas

- `src/main/java/com/proyectosu/invernadero/alerts/infrastructure/notification/NoOpAlertNotificationAdapter.java`

## Validacion realizada

- La suite del backend paso con `./gradlew.bat test`.
- El backend pudo levantar con `bootRun` una vez creada la base `invernadero` en PostgreSQL local.
- Se valido login manual con un usuario semilla para obtener JWT.

## Dependencias y observaciones

- El backend necesita PostgreSQL local con la base `invernadero` creada.
- El backend necesita MongoDB disponible en `localhost:27017` para que `GET /api/config` y `GET /api/v1/notifications` funcionen.
- En la validacion local, `POST /auth/register` presento un error de persistencia en el entorno de prueba; el login se valido con un usuario semilla directo en PostgreSQL.
- Si Mongo no esta disponible, las rutas de config y notificaciones responden con timeout de lectura de base de datos.

---

# Cambios necesarios en Frontend

## Nuevos endpoints - Bloque 4 (Inventario, Predicciones, Config)

### Inventario
Estos endpoints están listos en backend para ser consumidos por el equipo de frontend.

**GET /api/inventory**
- Retorna: Lista de items de inventario ordenados por nombre
- Headers: `Authorization: Bearer {token}`
- Response:
```json
[
  {
    "id": "uuid",
    "nombre": "Fertilizante A",
    "cantidad": 50,
    "unidad": "kg",
    "threshold_minimo": 10,
    "created_at": "2026-05-18T10:00:00Z",
    "updated_at": "2026-05-18T10:00:00Z"
  }
]
```

**POST /api/inventory**
- Crear nuevo item
- Headers: `Authorization: Bearer {token}`, `Content-Type: application/json`
- Body:
```json
{
  "nombre": "Fertilizante A",
  "cantidad": 50,
  "unidad": "kg",
  "threshold_minimo": 10
}
```
- Response: `{message: "Item creado exitosamente"}`

**PATCH /api/inventory/{id}**
- Actualizar item (campos opcionales)
- Headers: `Authorization: Bearer {token}`, `Content-Type: application/json`
- Body:
```json
{
  "cantidad": 45,
  "threshold_minimo": 15
}
```
- Response: `{message: "Item actualizado exitosamente"}`

**Implementar en Frontend:**
- Módulo `src/lib/inventoryApi.js` con funciones:
  - `fetchInventory(token)` → GET /api/inventory
  - `createInventoryItem(body, token)` → POST /api/inventory
  - `updateInventoryItem(id, body, token)` → PATCH /api/inventory/{id}
- Página `src/modules/inventory/pages/InventoryList.jsx` que:
  - Liste items con búsqueda y filtrado
  - Muestre indicador de bajo stock (cantidad <= threshold_minimo)
  - Permita crear y editar items
  - Maneje estados de carga, error, y éxito

---

### Predicciones
Endpoint read-only para consultar predicciones generadas por módulo de IA.

**GET /api/predictions**
- Retorna: Lista de predicciones ordenadas por fecha descendente
- Headers: `Authorization: Bearer {token}`
- Response:
```json
[
  {
    "id": "uuid",
    "tipo": "riego",
    "mensaje": "Se recomienda riego en próximas 2 horas",
    "accion_recomendada": "Activar sistema de riego",
    "device_id": "sensor-123",
    "procesado": false,
    "created_at": "2026-05-18T10:00:00Z"
  }
]
```

**Implementar en Frontend:**
- Módulo `src/lib/predictionsApi.js` con función:
  - `fetchPredictions(token)` → GET /api/predictions
- Componente opcional para mostrar predicciones pendientes o alertas basadas en las predicciones
- Considerar integración en dashboard o panel separado

---

### Configuración general del invernadero
Endpoint existente; actualmente operativo en backend pero puede requerir ajustes en frontend.

**GET /api/config**
- Retorna: Configuración global del invernadero (singleton)
- Headers: `Authorization: Bearer {token}`
- Response:
```json
{
  "automatic_mode": false,
  "inactivity_threshold_minutes": 10,
  "manual_override_duration_minutes": 10,
  "report_timezone": "UTC",
  "updated_at": "2026-05-18T10:00:00Z"
}
```

**PATCH /api/config**
- Actualizar configuración (campos opcionales)
- Headers: `Authorization: Bearer {token}`, `Content-Type: application/json`
- Body:
```json
{
  "automatic_mode": true,
  "inactivity_threshold_minutes": 15,
  "report_timezone": "America/Bogota"
}
```
- Response: El objeto actualizado con los campos modificados

**Implementar/Revisar en Frontend:**
- Módulo `src/lib/configApi.js` con funciones:
  - `fetchConfig(token)` → GET /api/config
  - `updateConfig(body, token)` → PATCH /api/config
- Página de configuración que permita editar estos campos
- Considerar validación local de zona horaria (valores válidos: valores de `Olson/IANA`)

---

### Centro de notificaciones
Sistema de notificaciones in-app persistidas desde alertas y eventos del sistema.

**GET /api/v1/notifications**
- Retorna: Lista de notificaciones ordenadas por fecha descendente
- Headers: `Authorization: Bearer {token}`
- Response:
```json
{
  "notifications": [
    {
      "notification_id": "uuid",
      "alert_id": "uuid",
      "title": "Alerta de temperatura",
      "message": "Temperatura crítica detectada",
      "severity": "critical",
      "status": "unread",
      "suggested_action": "Revisar sistema de refrigeración",
      "created_at": "2026-05-18T10:00:00Z"
    }
  ]
}
```

**PATCH /api/v1/notifications/{notification_id}**
- Actualizar estado de notificación
- Headers: `Authorization: Bearer {token}`, `Content-Type: application/json`
- Body:
```json
{
  "status": "read"
}
```
- Estados válidos: `unread`, `read`, `archived`

**DELETE /api/v1/notifications/{notification_id}**
- Eliminar notificación permanentemente
- Headers: `Authorization: Bearer {token}`
- Response: `{message: "Notificación eliminada exitosamente"}`

**Implementar en Frontend:**
- Módulo `src/lib/notificationsApi.js` con funciones:
  - `fetchNotifications(token)` → GET /api/v1/notifications
  - `updateNotificationStatus(notificationId, status, token)` → PATCH /api/v1/notifications/{id}
  - `deleteNotification(notificationId, token)` → DELETE /api/v1/notifications/{id}
- Centro de notificaciones in-app que:
  - Muestre notificaciones con badge de "sin leer"
  - Permita marcar como leída o archivar
  - Permita eliminar notificaciones
  - Diferencie visualmente por severity (warning vs critical)

---

## Notas técnicas para el equipo de frontend

1. **Autenticación**: Todos los endpoints requieren JWT en header `Authorization: Bearer {token}`
   - El token se obtiene de `POST /auth/login`
   - Se almacena típicamente en `localStorage` bajo la clave `auth_token`

2. **Errores**: Las respuestas de error siguen este formato:
   ```json
   {
     "code": "BAD_REQUEST",
     "message": "Descripción del error",
     "body": { "campo": "detalles adicionales" }
   }
   ```
   - Códigos de status HTTP: 400 (bad request), 401 (no autenticado), 404 (no encontrado), 500 (error servidor)

3. **Base URL**: Configurar en variable de entorno (ej: `VITE_API_URL=http://localhost:8080`)
   - En desarrollo: `http://localhost:8080`
   - En producción: URL del servidor

4. **Persistencia**: MongoDB está en `localhost:27017`; asegurar que esté corriendo durante desarrollo
   - Colecciones: `inventory`, `predictions`, `greenhouse_config`, `notifications`

5. **Validaciones frontend recomendadas**:
   - Cantidad y threshold_minimo: no negativos, números enteros
   - Nombre y unidad: requeridos, sin espacios al inicio/final
   - Zona horaria: validar contra lista de IANA timezones válidas
