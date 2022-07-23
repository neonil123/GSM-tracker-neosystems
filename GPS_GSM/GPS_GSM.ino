

#include "SIM800L.h"

#define RXD2 16
#define TXD2 17
#define SIM800_RST_PIN 5

const char APN[] = "wap.orange.md";
const char URL[] = "http://test.neosystems.cc:8081/";
const char CONTENT_TYPE[] = "application/json";
const char PAYLOAD[] = "{\"latitude\": \"47.00556\", \"longitude\": \"28.8575\"}";

float latitude  = 47.00556;
float longitude = 28.8575;
float speedo  = 0;
float heading = 0;

SIM800L* sim800l;

/////////////////////////////////////


#include <TinyGPS++.h>
#include <SoftwareSerial.h>

// Choose two Arduino pins to use for software serial for GPS
int RXPin = 26;
int TXPin = 25;
int GPSBaud = 9600;
TinyGPSPlus gps;
SoftwareSerial gpsSerial(RXPin, TXPin);

void setup()
{
  pinMode(SIM800_RST_PIN,OUTPUT);
  digitalWrite(SIM800_RST_PIN,LOW);
  delay(1000);
  digitalWrite(SIM800_RST_PIN,HIGH);
  delay(3000);
  Serial.begin(115200);
  gpsSerial.begin(GPSBaud);

  Serial2.begin(9600, SERIAL_8N1, RXD2, TXD2);
  delay(1000);

  Serial2.println("AT"); //Once the handshake test is successful, it will back to OK
  updateSerial();
  Serial2.println("AT+CSQ"); //Signal quality test, value range is 0-31 , 31 is the best
  updateSerial();
  Serial2.println("AT+CCID"); //Read SIM information to confirm whether the SIM is plugged
  updateSerial();
  Serial2.println("AT+CREG?"); //Check whether it has registered in the network
  updateSerial();
  Serial2.println("AT+CBC"); //Check whether it has registered in the network
  updateSerial();

  sim800l = new SIM800L((Stream *)&Serial2, SIM800_RST_PIN, 200, 512);
  setupModule();





  
}

void loop()
{
  while (gpsSerial.available() > 0){
    if (gps.encode(gpsSerial.read())){
         test_Env();
         displayInfo();
         send_HTTP();}
  }  
}


void send_HTTP(){
  Serial.println();
  
  char buffer[120];
  sprintf(buffer, "{\"latitude\": \"%f\", \"longitude\": \"%f\",\"speed\": \"%f\" ,\"heading\": \"%f\" }", latitude, longitude,speedo, heading);
  Serial.println(buffer);
  latitude   += 0.0001;
  longitude  += 0.0001;

  // Establish GPRS connectivity (5 trials)
  bool connected = false;
  for(uint8_t i = 0; i < 5 && !connected; i++) {
    delay(1000);
    connected = sim800l->connectGPRS();
  }

  // Check if connected, if not reset the module and setup the config again
  if(connected) {
    Serial.print(F("GPRS connected with IP "));
    Serial.println(sim800l->getIP());
  } else {
    Serial.println(F("GPRS not connected !"));
    Serial.println(F("Reset the module."));
    sim800l->reset();
    setupModule();
    return;
  }

  Serial.println(F("Start HTTP POST..."));

  // Do HTTP POST communication with 10s for the timeout (read and write)
  uint16_t rc = sim800l->doPost(URL, CONTENT_TYPE, buffer, 10000, 10000);
   if(rc == 200) {
    // Success, output the data received on the serial
    Serial.print(F("HTTP POST successful ("));
    Serial.print(sim800l->getDataSizeReceived());
    Serial.println(F(" bytes)"));
    Serial.print(F("Received : "));
    Serial.println(sim800l->getDataReceived());
  } else {
    // Failed...
    Serial.print(F("HTTP POST error "));
    Serial.println(rc);
    Serial.println("Resetting");
    digitalWrite(SIM800_RST_PIN,LOW);
    delay(5000);
    digitalWrite(SIM800_RST_PIN,HIGH);
     delay(15000);
    setupModule();
  }

  // Close GPRS connectivity (5 trials)
  bool disconnected = sim800l->disconnectGPRS();
  for(uint8_t i = 0; i < 5 && !connected; i++) {
    delay(1000);
    disconnected = sim800l->disconnectGPRS();
  }
  
  if(disconnected) {
    Serial.println(F("GPRS disconnected !"));
  } else {
    Serial.println(F("GPRS still connected !"));
  }

delay(5000);
}









void displayInfo()
{
  Serial.println();
  Serial.println();
  Serial.println();
  if (gps.location.isValid())
  {
    Serial.print("Latitude: ");
    Serial.println(gps.location.lat(), 6);
    latitude = gps.location.lat();
    Serial.print("Longitude: ");
    Serial.println(gps.location.lng(), 6);
    longitude = gps.location.lng();
    Serial.print("Altitude: ");
    Serial.println(gps.altitude.meters());
  }
  else
  {
    Serial.println("Location: Not Available");
  }
  
  Serial.print("Date: ");
  if (gps.date.isValid())
  {
    Serial.print(gps.date.month());
    Serial.print("/");
    Serial.print(gps.date.day());
    Serial.print("/");
    Serial.println(gps.date.year());
  }
  else
  {
    Serial.println("Not Available");
  }

  Serial.print("Time: ");
  if (gps.time.isValid())
  {
    if (gps.time.hour() < 10) Serial.print(F("0"));
    Serial.print(gps.time.hour());
    Serial.print(":");
    if (gps.time.minute() < 10) Serial.print(F("0"));
    Serial.print(gps.time.minute());
    Serial.print(":");
    if (gps.time.second() < 10) Serial.print(F("0"));
    Serial.println(gps.time.second());
  }
  else
  {
    Serial.println("Not Available");
  }


  Serial.print("Speed: ");
  if (gps.speed.isValid())
  { 
    Serial.print("knot/100: ");
    Serial.print(gps.speed.value());
    Serial.println("  km/h: ");
    Serial.println(gps.speed.value()*1.852);
    speedo = gps.speed.value()*1.852;
  }
  else
  {
    Serial.println("Not Available");
  }

  Serial.print("Heading: ");
  if (gps.course.isValid())
  { 
    Serial.print("deg/100: ");
    Serial.println(gps.course.value());
    heading =gps.course.value();
  }
  else
  {
    Serial.println("Not Available");
  }

  delay(1000);

  
}





void setupModule() {
  
    // Wait until the module is ready to accept AT commands
  while(!sim800l->isReady()) {
    Serial.println(F("Problem to initialize AT command, retry in 1 sec"));
    delay(1000);
  }
  Serial.println(F("Setup Complete!"));

  // Wait for the GSM signal
  uint8_t signal = sim800l->getSignal();
  while(signal <= 0) {
    delay(1000);
    signal = sim800l->getSignal();
    Serial.print(signal);
  }
  Serial.print(F("Signal OK (strenght: "));
  Serial.print(signal);
  Serial.println(F(")"));
  delay(1000);

  // Wait for operator network registration (national or roaming network)
  NetworkRegistration network = sim800l->getRegistrationStatus();
  while(network != REGISTERED_HOME && network != REGISTERED_ROAMING) {
    delay(1000);
    network = sim800l->getRegistrationStatus();
  }
  Serial.println(F("Network registration OK"));
  delay(1000);

  // Setup APN for GPRS configuration
  bool success = sim800l->setupGPRS(APN);
  while(!success) {
    success = sim800l->setupGPRS(APN);
    delay(5000);
  }
  Serial.println(F("GPRS config OK"));
}

void updateSerial()
{
  delay(500);
  while (Serial.available()) 
  {
    Serial2.write(Serial.read());//Forward what Serial received to Software Serial Port
  }
  while(Serial2.available()) 
  {
    Serial.write(Serial2.read());//Forward what Software Serial received to Serial Port
  }
}

void test_Env(){
latitude  += 0.0001;
longitude += 0.0001;
}
