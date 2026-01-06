/************************************************************
 * PLANTILLA: ESP32  → Adafruit IO
 ************************************************************/

#include <WiFi.h>                  // Librería WiFi para ESP32
#include "Adafruit_MQTT.h"         // Librería MQTT
#include "Adafruit_MQTT_Client.h"  // Cliente MQTT para Adafruit IO
#include <Wire.h>
#include <LiquidCrystal_I2C.h>     // Libreria para manejo de pantalla
#include <HX711.h>                 // Libreria para control de celda de carga
#include <ESP32Servo.h>            // Libreria para control de servomotor

/*
  IMPORTANTE

  Por motivos de seguridad y de funcionamiento, hay algunos valores
  que no se dejarán explícitos en el código, como serían el SSID de la red
  La contraseña de la red, el username de adafruit ni la llave.

  Finalmente, el valor de la variable scale_weight y weight_param, 
  debido a que dependen directamente de la calibración de la celda de carga, 
  podrán variar al momento de la presentación.

*/


/************** CONFIGURACIÓN Wi-Fi **************/

#define WLAN_SSID ""     // Nombre de tu red WiFi
#define WLAN_PASS ""  // Contraseña de tu red WiFi

/************** CONFIGURACIÓN Adafruit IO **************/
#define AIO_SERVER "io.adafruit.com"
#define AIO_SERVERPORT 1883  // Usa 8883 para SSL
#define AIO_USERNAME ""
#define AIO_KEY ""

/************** VARIABLES**************/
HX711 scale;
Servo servo;

int relay = 14;
int IR = 2;
int DT = 17;
int SCK_pin = 16;
int servo_pin = 13;

int scale_weight = -346300.0; // Puede variar

int initial_servo = 0;
int first_category = 60;
int second_category = 90;

float weight_param = 0.05; //50 gramos, puede variar

LiquidCrystal_I2C lcd(0x27, 16, 2);

/************** CLIENTE MQTT **************/
WiFiClient client;
Adafruit_MQTT_Client mqtt(&client, AIO_SERVER, AIO_SERVERPORT, AIO_USERNAME, AIO_KEY);

/************** FEED DE PUBLICACIÓN **************/
Adafruit_MQTT_Publish pubWht = Adafruit_MQTT_Publish(&mqtt, AIO_USERNAME "/feeds/peso");

/************** FUNCIÓN: Conexión a MQTT **************/
void MQTT_connect() {
  if (mqtt.connected()) return;
  Serial.print("Conectando a Adafruit IO... ");
  int8_t ret;
  uint8_t retries = 3;
  while ((ret = mqtt.connect()) != 0) {
    Serial.println(mqtt.connectErrorString(ret));
    Serial.println("Reintento en 10 s...");
    mqtt.disconnect();
    delay(10000);
    if (--retries == 0)
      while (1) delay(1);  // Queda esperando reset
  }
  Serial.println("¡Conectado!");
}


/************** SETUP **************/
void setup() {
  Serial.begin(115200);

  lcd.init();
  lcd.backlight();
  lcd.clear();

  pinMode(relay, OUTPUT);
  digitalWrite(relay, LOW);

  pinMode(IR, INPUT_PULLUP);

  scale.begin(DT, SCK_pin);
  scale.set_scale(scale_weight);
  scale.tare();

  servo.attach(servo_pin);
  servo.write(initial_servo);

  Serial.print("Conectando a WiFi...");
  WiFi.begin(WLAN_SSID, WLAN_PASS);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("\nWiFi conectado");
}

/************** LOOP **************/
void loop() {
  // Mantener conexión MQTT
  MQTT_connect();
  mqtt.processPackets(100);  // Reducido para mejor respuesta
  if (!mqtt.ping()) mqtt.disconnect();

  // Lectura de sensores cada INTERVALO_SENSORES
  int value_ir = digitalRead(IR);

  Serial.print("IR: ");
  Serial.println(value_ir);

  if (value_ir == LOW) {

    digitalWrite(relay, HIGH);

    double weight = obtenerPesoValido();

    delay(3000);

    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Peso: ");
    lcd.setCursor(0, 1);
    lcd.print(String(weight) + " kg.");
    if (!pubWht.publish(weight)) {
      Serial.println("Error al publicar el peso");
    } else {
      Serial.println("Éxito al publicar el peso");
    }

    if (weight < weight_param)
      servo.write(first_category);
    else
      servo.write(second_category);

    digitalWrite(relay, LOW);  // Mantenemos detenido
    delay(3000);
  }
  // Actualizar tiempo de última publicación
}


double obtenerPesoValido() {
  double w = scale.get_units(10);

  // Reintentar hasta que NO sea NaN
  while (isnan(w)) {
    Serial.println("Peso invalido, reintentando...");
    w = scale.get_units(10);
    delay(200);
  }

  return w;
}
