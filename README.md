# RetroKeyboard
![image](https://github.com/user-attachments/assets/c1e4d8f3-01f8-40c5-b07a-57addf0a6fa3)

The aim of the project was to build a keyboard using JetpackCompose, based on the Nokia 3310 writing system.

## Video:
[retro_keyboard_demo.webm](https://github.com/user-attachments/assets/aeed2d38-4122-4270-b748-dfceeecb4e9b)

## Getting Started
1. Clone repository.
2. Open project in Android Studio.
3. Build with Gradle.
4. As the official documentation says: <i>"Writing with the keypad is easy and fun"</i>, so have fun!

## How to use [1.]

### Writing: 

>Writing with the keypad is easy and fun. <br />
>Press a key repeatedly until the letter is shown. <br />
>To type in a space press 0 . <br />
>To type in a special character or punctuation mark, press * . <br />
>To switch between character cases, press # repeatedly. <br />
>To type in a number, press and hold a number key. <br />

### Support action buttons: (from left to right):
    1. Clear text
    2. Backspace (remove char before cursor)
    3. Move cursor left
    4. Move cursor right
    5. Copy text to clipboard
   ![image](https://github.com/user-attachments/assets/faef32f3-51ac-47bc-96b1-0b5f9b4f1a71)

 + Last two buttons are related with simple Text to number sequence converter:
   >     6. Convert text to num - convert given text to number sequence, corresponding to keypad numbers clicked by user when typing text
   >     7. Convert num to text - convert back given number sequence to text (lowercase)

### Text to number sequence converter:
![image](https://github.com/user-attachments/assets/f233a076-f153-4bd0-81fa-ba9db8b78212)
*Converter works only with base chars, special characters are not converted. Letter case is not respected - output is always in lowercase.


## Check it out!
The app is not officially published in Google Play Store. If you want to check it out, you can do it using one of the following ways:

1) Download directly on Android device from: <b>[RetroKeyboard v1.0](https://github.com/MatLeg25/RetroKeyboard/releases/download/v1.0/RetroKeyboard_v1.0.apk)</b> <br />
<i>You may need to enable Unknown Sources in your device settings to install apps outside of the Google Play Store.</i>  [[Allow Unknown Sources on Android](https://www.wikihow.com/Allow-Apps-from-Unknown-Sources-on-Android)]

2) Build the app on your computer:
   - a)  Clone repository.
   - b)  Open project in Android Studio.
   - c)  Build with Gradle.
   - d)  Run on your device or emulator.


## Tech stack:
- Android, JetpackCompose
- Kotlin, Coroutines, Flow

## Sources:
   1. Nokia 3310 user guide https://www.hmd.com/en_int/support/api/pdf/nokia-3310-user-guide
   2. App icon https://icons8.com/icon/41083/nokia-3310
   3. Font https://www.dafont.com/nokia-cellphone.font
   4. Music used in the demo video: https://www.youtube.com/watch?v=CnR7hNs8XlQ
