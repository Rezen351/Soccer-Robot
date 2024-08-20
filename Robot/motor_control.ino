
#include <Arduino.h>

// Define motor control pins
#define A1A_PIN 25  // Adjust pin number as per your wiring
#define A1B_PIN 26  // Adjust pin number as per your wiring
#define B1A_PIN 32 // Adjust pin number as per your wiring
#define B1B_PIN 33 // Adjust pin number as per your wiring

#define C1A_PIN 27 // Adjust pin number as per your wiring
#define C1B_PIN 14 // Adjust pin number as per your wiring

// Define LEDC channels for each motor pin
#define CHANNEL_A1A 8
#define CHANNEL_A1B 9
#define CHANNEL_B1A 4
#define CHANNEL_B1B 5

#define CHANNEL_C1A 7
#define CHANNEL_C1B 6


void setupMotorControl() {
  // Set pin modes

  // Configure LEDC channels
  ledcSetup(CHANNEL_A1A, 1000, 8); // Channel, frequency, resolution
  ledcSetup(CHANNEL_A1B, 1000, 8);
  ledcSetup(CHANNEL_B1A, 1000, 8);
  ledcSetup(CHANNEL_B1B, 1000, 8);

  ledcSetup(CHANNEL_C1A, 1000, 8);
  ledcSetup(CHANNEL_C1B, 1000, 8);

  // Attach pins to LEDC channels
  ledcAttachPin(A1A_PIN, CHANNEL_A1A);
  ledcAttachPin(A1B_PIN, CHANNEL_A1B);
  ledcAttachPin(B1A_PIN, CHANNEL_B1A);
  ledcAttachPin(B1B_PIN, CHANNEL_B1B);
  
  ledcAttachPin(C1A_PIN, CHANNEL_C1A);
  ledcAttachPin(C1B_PIN, CHANNEL_C1B);
}

void moveMotorAForward(int speed) {
  ledcWrite(CHANNEL_A1A, speed);
  ledcWrite(CHANNEL_A1B, 0);
}

void moveMotorABackward(int speed) {
  ledcWrite(CHANNEL_A1A, 0);
  ledcWrite(CHANNEL_A1B, speed);
}

void stopMotorA() {
  ledcWrite(CHANNEL_A1A, 0);
  ledcWrite(CHANNEL_A1B, 0);
}

void moveMotorBForward(int speed) {
  ledcWrite(CHANNEL_B1A, speed);
  ledcWrite(CHANNEL_B1B, 0);
}

void moveMotorBBackward(int speed) {
  ledcWrite(CHANNEL_B1A, 0);
  ledcWrite(CHANNEL_B1B, speed);
}

void stopMotorB() {
  ledcWrite(CHANNEL_B1A, 0);
  ledcWrite(CHANNEL_B1B, 0);
}


void moveMotorCForward() {
  ledcWrite(CHANNEL_C1A, 50);
  ledcWrite(CHANNEL_C1B, 0);
}

void moveMotorCBackward() {
  ledcWrite(CHANNEL_C1A, 0);
  ledcWrite(CHANNEL_C1B, 50);
}
void stopMotorC() {
  ledcWrite(CHANNEL_C1A, 0);
  ledcWrite(CHANNEL_C1B, 0);
}

