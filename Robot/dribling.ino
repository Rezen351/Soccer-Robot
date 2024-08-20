

void movementControl() {
  // Convert power to speed

}

void dribling() {
  if (d == 1){
    moveMotorCForward();
    Serial.println("Motor C Run");
// moveMotorCBackward();
  }
  else {
    stopMotorC();
    Serial.println("Motor C Stop");
  }


}

void stopMotors() {
  stopMotorA();
  stopMotorB();
}

void movement(String direction, int speed) {
  if (direction == "R") {
    moveMotorAForward(speed);
    moveMotorBBackward(speed);
  } else if (direction == "UR") {
    moveMotorAForward(speed);
    moveMotorBForward(speed / 3);
  } else if (direction == "U") {
    moveMotorAForward(speed);
    moveMotorBForward(speed);
  } else if (direction == "UL") {
    moveMotorAForward(speed / 3);
    moveMotorBForward(speed);
  } else if (direction == "L") {
    moveMotorABackward(speed);
    moveMotorBForward(speed);
  } else if (direction == "BL") {
    moveMotorABackward(speed);
    moveMotorBBackward(speed / 3);
  } else if (direction == "B") {
    moveMotorABackward(speed);
    moveMotorBBackward(speed);
  } else if (direction == "BR") {
    moveMotorABackward(speed / 3);
    moveMotorBBackward(speed);
  } else {
    stopMotors();
  }
}

