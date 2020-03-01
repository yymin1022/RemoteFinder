# 리모컨 파인더 || RemoteControl Finder

## Android 애플리케이션과 아두이노 기기간의 Bluetooth 통신을 활용한 리모컨 되찾기 프로젝트

## RemoteControl finding project with Bluetooth communication between Android Application and Arduino device

## 초기 구상도 || First Plan

<div>
  <img width="400" src="/Images/img_idea.jpg"/>
</div>

## 동작 구조 || How does it work?

<div style="position:absoulte;width:100%;height:100%;">
  <img width="600" style="position:absoulte;width:100%;height:100%;" src="/Images/img_how_works.png"/>
</div>

## 완성 예시 || Complete example
<div style="position:absoulte;width:100%;height:100%;">
  <img width="600" style="position:absoulte;width:100%;height:100%;" src="/Images/img_code_example.png"/>
</div>

## 준비물 || Needed

> Android 스마트폰 || Android Smartphone

> Arduino UNO 또는 Mega 등 Arduino 보드 || Arduino boards like UNO, Mega, etc.

> HC-06 Bluetooth, LED, 진동모터 || HC-06 Bluetooth, LED, Vibration Motor

## 방법 || HOWTO

<div style="position:absoulte;width:100%;height:100%;">
  <img width="600" style="position:absoulte;width:100%;height:100%;" src="/Images/img_making.jpg"/>
</div>

* Arduino 기기 || Arduino Device

  * 다음과 같이 Arduino 기기를 구성합니다. LED와 진동모터의 경우, 사용자의 취향에 맞게 스피커 등으로 변환하여 연결할 수 있습니다.

  * Make Arduino device like this image. You can change LED and Vibration Motor to everything like speaker, if you want.

<div style="position:absoulte;width:100%;height:100%;">
  <img width="600" style="position:absoulte;width:100%;height:100%;" src="/Arduino Device/HowToConnect.png"/>
</div>

  * 구성된 Arduino 기기에 소스코드를 업로드합니다. 소스코드는 `/ArduinoDevice/Main_Sketch.ino` 파일입니다.

  * Upload source code to Arduino device. You can get `/ArduinoDevice/Main_Sketch.ino` file and use it.

* Android 애플리케이션 || Android Application

  * APK 다운로드 || Download APK

    * `/AndroidApplication/app/build/bin/app.apk` 파일을 다운로드하여 스마트폰에 설치합니다.

    * Download `/AndroidApplication/app/build/bin/app.apk`

  * 직접 빌드하는 경우 || Build self

    * `/AndroidApplication` 폴더를 zip으로 다운로드하거나 `git clone https://github.com/yymin1022/RemoteFinder` 명령어를 이용하여 PC에 저장합니다.

    * Save `/AndroidApplication` forlder to your PC with zip archieved file or using `git clone https://github.com/yymin1022/RemoteFinder` command.

    * Android Studio에서 Project Import를 이용해 애플리케이션을 빌드합니다.

    * Build application with Android Studio Project Import.
