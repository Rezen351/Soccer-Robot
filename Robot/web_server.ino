
#include "ESPAsyncWebServer.h"

extern String sudut;
AsyncWebServer server(80);

void setupWebServer() {
  server.on("/", HTTP_GET, [](AsyncWebServerRequest *request){
    if (request->hasParam("angle")) {
      String angleString = request->getParam("angle")->value();
      request->send(200, "text/plain", "Input angle received: " + angleString);
      sudut = angleString;
    } else {
      request->send(400, "text/plain", "Bad Request: No message content provided");
    }
  });
  server.begin();
}

