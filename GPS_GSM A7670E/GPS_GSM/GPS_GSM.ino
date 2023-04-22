
#include "BluetoothSerial.h" //Header File for Serial Bluetooth, will be added by default into Arduino

BluetoothSerial ESP_BT; //Object for Bluetooth
int incoming;
int LED_BUILTIN = 5;



#define RXD2 16
#define TXD2 17
#define SIM7670_PWR_PIN 32

const char APN[] = "wap.orange.md";
const char URL[] = "http://test.neosystems.cc:8081/";
const char CONTENT_TYPE[] = "application/json";
const char PAYLOAD[] = "{\"latitude\": \"47.02573\", \"longitude\": \"28.845774\"}";
const int HardwareId = 1;

float latitude = 0;
float longitude = 0;
float speedo = 0;
float heading = 0;

bool active = true;
/////////////////////////////////////


#include <TinyGPS++.h>
#include <SoftwareSerial.h>

// Choose two Arduino pins to use for software serial for GPS
int RXPin = 26;
int TXPin = 25;
int GPSBaud = 9600;
TinyGPSPlus gps;
SoftwareSerial gpsSerial(RXPin, TXPin);

#define wirelessPWR 33

void setup() {

  Serial.begin(115200);
  gpsSerial.begin(GPSBaud);
  Serial2.begin(115200, SERIAL_8N1, RXD2, TXD2);

  ESP_BT.begin("ESP32_GPS_Tracker"); //Name of your Bluetooth Signal
  Serial.println("Bluetooth Device is Ready to Pair");
  pinMode (LED_BUILTIN, OUTPUT);//Specify that LED pin is output

  pinMode(5, OUTPUT);
  digitalWrite(5, HIGH);
  delay(100);



  pinMode(wirelessPWR, OUTPUT);
  digitalWrite(wirelessPWR, HIGH);

  pinMode(SIM7670_PWR_PIN, OUTPUT);
  digitalWrite(SIM7670_PWR_PIN, LOW);
  delay(3000);



  delay(1000);

  Serial2.println("AT");  //Once the handshake test is successful, it will back to OK
  updateSerial();
  delay(2000);
  Serial2.println("AT+CSQ");  //Signal quality test, value range is 0-31 , 31 is the best
  updateSerial();
  delay(2000);
  Serial2.println("AT+CREG?");  //Read SIM information to confirm whether the SIM is plugged
  updateSerial();
  delay(2000);
  Serial2.println("AT+CGREG?");  //Check whether it has registered in the network
  updateSerial();
  delay(2000);
  Serial2.println("AT+CPSI?");  //Check whether it has registered in the network
  updateSerial();
  delay(2000);
}

void loop() {
  updateSerial();

  while (gpsSerial.available() > 0) {
    if (gps.encode(gpsSerial.read())) {
      // test_Env();
      displayInfo();
      if ((int)gps.satellites.value() > 0 && latitude != 0 && longitude != 0 && speedo > 2) {
        send_HTTP();
      }
    }
  }

}


void send_HTTP() {
  Serial.println();

  size_t needed = snprintf(NULL, 0, "{\"latitude\": \"%f\", \"longitude\": \"%f\",\"speed\": \"%.2f\" ,\"heading\": \"%.2f\",\"trackerId\": \"%d\" }", latitude, longitude, speedo, heading, HardwareId) + 1;
  char *buffer = (char*)malloc(needed);
  sprintf(buffer, "{\"latitude\": \"%f\", \"longitude\": \"%f\",\"speed\": \"%.2f\" ,\"heading\": \"%.2f\",\"trackerId\": \"%d\" }", latitude, longitude, speedo, heading, HardwareId);
  Serial.println(buffer);
  Serial.println(needed);
  Serial.println(F("Start HTTP POST..."));
  ESP_BT.println("Start HTTP POST...");

  Serial2.println("AT+HTTPINIT");
  updateSerial();
  //delay(20);

  Serial2.println("AT+HTTPPARA=\"CONTENT\",\"application/json\"");
  updateSerial();
  //delay(20);

  Serial2.println("AT+HTTPPARA=\"URL\",\"http://test.neosystems.cc:8081\"");
  updateSerial();
  //delay(20);

  char buffer1[30];
  sprintf(buffer1, "AT+HTTPDATA=%d,1000", needed);
  Serial2.println(buffer1);
  updateSerial();
  //delay(20);

  Serial2.println(buffer);
  updateSerial();
  //delay(20);

  Serial2.println("AT+HTTPACTION=1");
  updateSerial();
  for (int i = 0; i < 5; i++) {
    updateSerial();
    delay(2);
  }

  Serial2.println("AT+HTTPREAD=4");
  updateSerial();
  //delay(20);

  Serial2.println("AT+HTTPTERM");
  updateSerial();
  ESP_BT.println("End Post");
  ESP_BT.println();
  ESP_BT.println();
  Serial.println("======================================================================================================");
  Serial.println();
  Serial.println();
  delay(100);
}



void displayInfo() {
  Serial.println();
  Serial.println();
  Serial.println();
  if (gps.location.isValid()) {
    Serial.print("Latitude: ");
    Serial.println(gps.location.lat(), 6);
    latitude = gps.location.lat();
    Serial.print("Longitude: ");
    Serial.println(gps.location.lng(), 6);
    longitude = gps.location.lng();
    Serial.print("Altitude: ");
    Serial.println(gps.altitude.meters());

    ESP_BT.print("Latitude: ");
    ESP_BT.println( latitude);
    ESP_BT.print("Longitude: ");
    ESP_BT.println(longitude);


  } else {
    Serial.println("Location: Not Available");
    ESP_BT.println("Location: Not Available");
  }

  Serial.print("Date: ");
  if (gps.date.isValid()) {
    Serial.print(gps.date.month());
    Serial.print("/");
    Serial.print(gps.date.day());
    Serial.print("/");
    Serial.println(gps.date.year());
  } else {
    Serial.println("Not Available");
  }

  Serial.print("Time: ");
  if (gps.time.isValid()) {
    if (gps.time.hour() < 10) Serial.print(F("0"));
    Serial.print(gps.time.hour());
    Serial.print(":");
    if (gps.time.minute() < 10) Serial.print(F("0"));
    Serial.print(gps.time.minute());
    Serial.print(":");
    if (gps.time.second() < 10) Serial.print(F("0"));
    Serial.println(gps.time.second());
  } else {
    Serial.println("Not Available");
  }


  Serial.print("Speed: ");
  if (gps.speed.isValid()) {
    Serial.print("  km/h: ");
    Serial.println(gps.speed.kmph());
    speedo = gps.speed.kmph();

    ESP_BT.print("Speed km/h: ");
    ESP_BT.println(speedo);
  } else {
    Serial.println("Not Available");
    ESP_BT.println("Speed Not Available");
  }

  Serial.print("Heading: ");
  if (gps.course.isValid()) {
    Serial.print("deg");
    Serial.println(gps.course.deg());
    heading = gps.course.deg();
  } else {
    Serial.println("Not Available");
  }



  Serial.print("Satelites: ");
  if ((int)gps.satellites.value() > 0) {
    Serial.println((int)gps.satellites.value());
    ESP_BT.print("Satelites: ");
    ESP_BT.println((int)gps.satellites.value());
  } else {
    Serial.println("Not Available");
    ESP_BT.println("Satelites Not Available");
  }

}





void setupModule() {


}

void updateSerial() {
  delay(100);
  while (Serial.available()) {
    Serial2.write(Serial.read());  //Forward what Serial received to Software Serial Port
  }
  while (Serial2.available()) {
    //Serial.write(Serial2.read());  //Forward what Software Serial received to Serial Port
    ESP_BT.write(Serial2.read());
  }
}

void test_Env() {
  latitude += 0.0001;
  longitude += 0.0001;
}
