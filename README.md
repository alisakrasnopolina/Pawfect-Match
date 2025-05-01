# 🐾 Pawfect Match

**Pawfect Match** — это Android-приложение, разработанное с использованием **Jetpack Compose**, **Kotlin** и **Firebase**, предназначенное для поиска домашних животных из приютов и упрощения процесса усыновления.

## 🚀 Особенности

- Регистрация и вход через Firebase Authentication
- Хранение профиля пользователя и списка избранных животных в Firestore
- Онбординг с пошаговым выбором предпочтений
- Просмотр животных из внешнего API (Petfinder)
- Фильтрация по типу, полу, возрасту, размеру
- Отображение подробной карточки животного
- Добавление/удаление из избранного
- Экран профиля с возможностью редактирования

## 🛠️ Используемые технологии

- **Kotlin** + **Jetpack Compose**
- **Navigation Compose**
- **Firebase Auth**
- **Firebase Firestore**
- **Retrofit + Gson** (для API-запросов)
- **Coil** для загрузки изображений
- **Coroutines + Flow** для асинхронной работы

## 📦 Зависимости (build.gradle)

```kotlin
// Firebase
implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
implementation("com.google.firebase:firebase-auth-ktx")
implementation("com.google.firebase:firebase-firestore-ktx")

// Compose
implementation("androidx.compose.ui:ui")
implementation("androidx.navigation:navigation-compose")
implementation("androidx.lifecycle:lifecycle-viewmodel-compose")

// Coil
implementation("io.coil-kt:coil-compose:2.4.0")

// Retrofit
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Kotlin coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
```

## 🧑‍💻 Запуск проекта

1. Склонируй репозиторий:
   ```bash
   git clone git@github.com:your-username/pawfect-match.git
   ```

2. Открой проект в **Android Studio**.

3. Убедись, что у тебя есть файл `google-services.json` в папке `app/` (скачай из Firebase Console).

4. Установи зависимости и нажми **"Sync Project with Gradle Files"**.

5. Запусти проект на эмуляторе или физическом устройстве.

---
