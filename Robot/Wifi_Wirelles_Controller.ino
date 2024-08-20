#include <WiFi.h> // WiFi library
#include "ESPAsyncWebServer.h"

#define SOLENOID_PIN 12


// Standard Value
String sudut;
String inputType;
String value1;
String value2;

int d = 0;

// After Conversion
String direction;
float power;
int speed; // Speed to control motor

void setup() {
  Serial.begin(115200);

  // Initialize WiFi and connect to the network
  setupAP();
  
  // Initialize the OLED display
  setupOLED();

  // Set up the access point
  setupAP();

  // Initialize web server
  setupWebServer();

  // Initialize motor control
  setupMotorControl();

  // Initialize servo control
  setupServoControl();

  // Setup Solenoid
  pinMode(SOLENOID_PIN, OUTPUT);
}

void loop() {
  // Print the angle value to OLED
  updateOLED(sudut);

  // Parse Input
  parseInput(sudut);

  // Convert Control and Perform Dribbling if applicable
  convertControl();
  
  speed = map(power, 0, 100, 0, 255);

  // Perform dribbling if power is greater than 30
  if (power > 30) {
    movement(direction, speed);
    Serial.print("Dribbling");
  } else {
    stopMotors();
    Serial.print("Stop Motor");
  }
  dribling();

  delay(100);
}


