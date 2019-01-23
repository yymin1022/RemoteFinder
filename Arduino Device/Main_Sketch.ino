#include <SoftwareSerial.h>
#include <avr/wdt.h>

int btTX = 8;
int btRX = 9;
int LED = 2;
int VIB = 3;

SoftwareSerial  btSerial(btTX, btRX);
byte buffer[1024];
int bufferPosition;

unsigned char ledChar = 'LED';
unsigned char vibChar = 'VIB';
unsigned char exitChar = 'EXIT';

void startRing(int);

void setup(){
  Serial.begin(9600);
  btSerial.begin(9600);
  bufferPosition = 0;
  //LED와 진동모터를 출력기기로 설정
  //Set LED and VIB Motor to OUTPUT device
  pinMode(LED, OUTPUT);
  pinMode(VIB, OUTPUT);
}

void loop(){
  //애플리케이션으로부터 수신된 메시지 처리
  //Read received message from application
  if(btSerial.available()){
    byte data = btSerial.read();
    Serial.write(data);
    //LED 실헹 명령이 전달된 경우
    //LED command  received
    if(data == ledChar){
      startRing(1);
    }
    //진동모터 실행 명령이 전달된 경우
    //VIB Motor command received
    if(data == vibChar){
      startRing(2);
    }
    //연결 해제 명령이 전달된 경우
    //Disconnection command received
		if(data == exitChar){
      Serial.write("Hello, World!");
      btSerial.print("AT+RESET");
    }
    //데이터 수신 변수를 처음상태로 초기화
    //Reset data receiving variable
    buffer[bufferPosition++] = data;
  }
}

//경우에 따라 LED를 깜빡이거나 진동모터를 울리도록 하는 함수
//Blink LED or enable vibration case by case
void startRing(int input){
  switch(input){
    case 1:
      digitalWrite(LED, HIGH);
      delay(1000);
      digitalWrite(LED, LOW);
      delay(1000);
      digitalWrite(LED, HIGH);
      delay(1000);
      digitalWrite(LED, LOW);
      break;
    case 2:
      digitalWrite(VIB, HIGH);
      delay(1000);
      digitalWrite(VIB, LOW);
      delay(1000);
      digitalWrite(VIB, HIGH);
      delay(1000);
      digitalWrite(VIB, LOW);
  }
}

