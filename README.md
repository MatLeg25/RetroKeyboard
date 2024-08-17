# RetroKeyboard
![image](https://github.com/user-attachments/assets/66f88236-2adf-4b64-8d28-ee656bd65869)


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


## Tech stack:
- Android, JetpackCompose
- Kotlin, Coroutines, Flow

## Sources:
   1. Nokia 3310 user guide https://www.hmd.com/en_int/support/api/pdf/nokia-3310-user-guide
   2. App icon https://icons8.com/icon/41083/nokia-3310
   3. Font https://www.dafont.com/nokia-cellphone.font
   4. Video music: https://www.youtube.com/watch?v=CnR7hNs8XlQ
