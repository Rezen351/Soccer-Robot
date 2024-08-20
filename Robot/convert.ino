#include <ESP32Servo.h>

#define SERVO_PIN 18


Servo myServo;

void setupServoControl() {
  myServo.attach(SERVO_PIN);
}


void convertControl() {
  if (inputType == "Joystick") {
    float value1convert = value1.toFloat();
    power = value2.toFloat();
    direction = convertAngleToDirection(value1convert);
    Serial.print("Direction: ");
    Serial.println(direction);
    Serial.print("Power: ");
    Serial.println(power);
  } else if (inputType == "Button") {
    checkButton();
  }
}

void checkButton() {
  Serial.println("Cek Tombol Uhuuy");
  Serial.print(value1);
  Serial.println(value2);
  
  if (value2 == " Pressed"){
    Serial.print("Tombol ditekan Nih");
    if (value1 == " L"){
      Serial.println("Move servo to the left");
      myServo.write(0); // Adjust the angle as needed
      delay(50);

    }else if (value1 == " R") {
      Serial.println("Move servo to the right");
      myServo.write(180); // Adjust the angle as needed
      delay(50);

    }else if (value1 == " A") {

    
    }else if (value1 == " B") {
      if (d == 0){
        d = 1;
        Serial.println("Motor C on");
      }
      else{
        d = 0;
        Serial.println("Motor C off");
      }
    
    }else if (value1 == " X") {
      digitalWrite(SOLENOID_PIN, HIGH);
      Serial.println("SOLENOID HIGH");
    }else if (value1 == " Y") {
    
    }

  }else if (value2 == " Release") {
    Serial.print("Tombol dilepas Nih");
    if (value1 == " L"){
      Serial.println("Move servo to the left");
      myServo.write(90); // Adjust the angle as needed
      delay(500);

    }else if (value1 == " R") {
      Serial.println("Move servo to the right");
      myServo.write(90); // Adjust the angle as needed
      delay(500);

    }
    else if (value1 == " A") {
    
    }else if (value1 == " B") {
    
    
    }else if (value1 == " X") {
      digitalWrite(SOLENOID_PIN, LOW);
      Serial.println("SOLENOID LOW");
    }else if (value1 == " Y") {
    
    }

    }
  
  else {
    
  }
}



void parseInput(String input) {
  int firstComma = input.indexOf(',');
  int secondComma = input.indexOf(',', firstComma + 1);

  if (firstComma != -1 && secondComma != -1) {
    inputType = input.substring(0, firstComma);
    value1 = input.substring(firstComma + 1, secondComma);
    value2 = input.substring(secondComma + 1);

    Serial.print("Input Type: ");
    Serial.println(inputType);
    Serial.print("Value1: ");
    Serial.println(value1);
    Serial.print("Value2: ");
    Serial.println(value2);
  } else {
    Serial.println("Invalid input format");
  }
}

String convertAngleToDirection(float value1convert) {
  if ((value1convert >= 337.5 && value1convert <= 360) || (value1convert >= 0 && value1convert < 22.5)) {
    return "R"; // Right
  } else if (value1convert >= 22.5 && value1convert < 67.5) {
    return "UR"; // UpRight
  } else if (value1convert >= 67.5 && value1convert < 112.5) {
    return "U"; // Upward
  } else if (value1convert >= 112.5 && value1convert < 157.5) {
    return "UL"; // UpLeft
  } else if (value1convert >= 157.5 && value1convert < 202.5) {
    return "L"; // Left
  } else if (value1convert >= 202.5 && value1convert < 247.5) {
    return "BL"; // BackLeft
  } else if (value1convert >= 247.5 && value1convert < 292.5) {
    return "B"; // Backward
  } else if (value1convert >= 292.5 && value1convert < 337.5) {
    return "BR"; // BackRight
  } else {
    return ""; // Undefined
  }
}
