#include <HX711.h>
HX711 scale;

#define DT 17
#define SCK 16

void setup() {
	
	Serial.begin(115200);
	scale.begin(DT, SCK);
	scale.set_scale(1.0);	
	scale.tare();
}

void loop() {

	float real_weight = 20.0;

	long rauw_weight = scale.get_value(10);
	float measured_weight = scale.get_units(10);

	float calibracion = measured_weight / real_weight;	
	
	Serial.print("Rauw value: ");
	Serial.println(rauw_weight);

	Serial.print("Measured value:");
	Serial.println(measured_weight);

	Serial.print("Factor calibracion: ");
	Serial.print(calibracion);

	delay(2000);
}
