# Smart Greenhouse Cursor Context

The project already has:
- Auth with JWT partially implemented.
- MongoDB connection working.
- PostgreSQL connection existing but MongoDB is currently prioritized.
- MQTT connection and publisher working.
- A working base slice for AI prediction automation.

The working base slice is:

POST /api/predictions

Request:
{
"device_id": "nodo1",
"procesado": false,
"actuador_id": "24SKF28",
"timeAction": "10"
}

Flow:
1. PredictionController receives request.
2. PredictionDtoMapper converts request to ProcessAiPredictionCommand.
3. ProcessAiPredictionUseCase validates and saves Prediction.
4. It reads GreenhouseConfig.
5. If automaticMode is false, it only stores the prediction.
6. If automaticMode is true, it executes TimedActuatorExecutorPort.
7. ExecuteTimedActuatorUseCase resolves actuatorId.
8. It publishes MQTT ON.
9. It saves actuator event ON.
10. It schedules MQTT OFF after timeAction seconds.
11. It saves actuator event OFF.

Important:
- InMemoryActuatorResolverAdapter is temporary.
- Do not replace it yet unless explicitly requested.
- The current slice is the architecture pattern for future modules.

Next modules needed:
1. Sensors MQTT receiving and REST queries.
2. Devices.
3. Greenhouse config REST endpoints.
4. Inventory.
5. Manual actuator execution endpoint.