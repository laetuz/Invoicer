# Invoicer 🧾

Invoicer is a desktop application built with Compose Multiplatform. It allows users to draft, edit, and manage invoices with a refined, keyboard-centric user interface designed for productivity.

<img width="787" height="420" alt="image" src="https://github.com/user-attachments/assets/0be62b96-8b92-4ed4-aa3c-4cf71ef4533e" />


## Features
* Desktop-First UX: Optimized for mouse and keyboard usage on Windows, macOS, and Linux.
* Keyboard Navigation: Full support for Tab and Enter keys to navigate cells and add new line items rapidly without lifting hands from the keyboard.
* Dynamic Data Grid:
  * Real-time calculations (Quantity × Rate = Amount).
  * Automatic row addition.
  * Inline deletion.

## Tech Stack
* Language: The one and only ✨Kotlin
* UI Framework: Compose Multiplatform (targeting Desktop/JVM)
* Build System: Gradle Kotlin DSL
* PdfBox for Pdf Rendering

---
To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run Desktop (JVM) Application

To build and run the development version of the desktop app, use the run configuration from the run widget
in your IDE’s toolbar or run it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:run
  ```
---

[Neotica](https://neotica.id)
