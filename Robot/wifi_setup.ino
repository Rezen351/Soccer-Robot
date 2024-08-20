
const char* ap_ssid = "Player";
const char* ap_password = "12345678";

void setupAP() {
  WiFi.softAP(ap_ssid, ap_password);
  IPAddress APIP = WiFi.softAPIP();
  Serial.print("AP IP address: ");
  Serial.println(APIP);
}

