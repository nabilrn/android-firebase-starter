# Firebase Starter - Android App

A modern Android application built with Jetpack Compose, Firebase Authentication, and Google Sign-In using the latest Credential Manager API.

## 🚀 Features

- **Google Sign-In Authentication** using modern Credential Manager API
- **Firebase Authentication** integration
- **MVVM Architecture** with Repository pattern
- **Jetpack Compose** UI with Material Design 3
- **Navigation Compose** for screen navigation
- **DataStore Preferences** for local data persistence
- **Network connectivity monitoring**
- **Clean, minimal UI design**

## 📋 Prerequisites

Before you begin, ensure you have:

- **Android Studio** (latest version recommended)
- **Android SDK** (API level 26 or higher)
- **Google account** for Firebase setup
- **Internet connection** for authentication

## 🔧 Setup Instructions

### 1. Firebase Project Setup

1. Go to the [Firebase Console](https://console.firebase.google.com/)
2. Click "Create a project" or select an existing project
3. Enter your project name (e.g., "Firebase Starter")
4. Enable Google Analytics (optional)
5. Click "Create project"

### 2. Enable Authentication

1. In your Firebase project, go to **Authentication** → **Sign-in method**
2. Click on **Google** provider
3. Toggle **Enable**
4. Add your **project support email**
5. Click **Save**

### 3. Register Android App

1. In Firebase Console, click **Project Settings** (gear icon)
2. Go to **Your apps** section
3. Click **Add app** → **Android**
4. Fill in the details:
   - **Android package name**: `com.example.androidfirebasestarter`
   - **App nickname**: Firebase Starter (optional)
   - **Debug signing certificate SHA-1**: Get this by running:
     ```bash
     keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
     ```
5. Click **Register app**

### 4. Download Configuration File

1. Download the `google-services.json` file
2. Place it in your `app/` directory:
   ```
   AndroidFirebaseStarter/
   └── app/
       └── google-services.json
   ```

### 5. Configure Web Client ID

1. In Firebase Console, go to **Project Settings** → **General**
2. Scroll down to **Your apps** section
3. Click on your Android app
4. In the **Web Client ID** section, copy the client ID
5. Open `app/build.gradle.kts` and replace the placeholder:
   ```kotlin
   buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", "\"YOUR_WEB_CLIENT_ID_HERE\"")
   ```

### 6. Alternative: Using Environment Variables (Recommended)

For better security, you can use `local.properties`:

1. Create/edit `local.properties` in the root directory:
   ```properties
   google.web.client.id=YOUR_WEB_CLIENT_ID_HERE
   ```

2. Update `app/build.gradle.kts`:
   ```kotlin
   android {
       defaultConfig {
           val localProperties = Properties()
           localProperties.load(project.rootProject.file("local.properties").inputStream())
           
           buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", 
               "\"${localProperties.getProperty("google.web.client.id")}\"")
       }
   }
   ```

## 🏃‍♂️ Running the App

1. **Clone the repository**:
   ```bash
   git clone <your-repo-url>
   cd AndroidFirebaseStarter
   ```

2. **Open in Android Studio**:
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project folder

3. **Sync dependencies**:
   - Click "Sync Now" when prompted
   - Wait for Gradle sync to complete

4. **Run the app**:
   - Connect an Android device or start an emulator
   - Click the "Run" button (▶️) or press `Ctrl+R`

## 📱 App Usage

### Login Screen
- Simple interface with Firebase Starter title
- Google Sign-In button with official Google logo
- Network connectivity status indicator
- Loading state during authentication

### Home Screen
- Welcome message with user's name and email
- Sign-out button
- Clean, minimal design

## 🏗️ Project Architecture

```
app/src/main/java/com/example/androidfirebasestarter/
├── data/
│   ├── preferences/
│   │   └── AuthPreferences.kt         # DataStore preferences
│   └── repository/
│       └── AuthRepository.kt          # Authentication logic
├── di/
│   └── ViewModelFactory.kt           # Dependency injection
├── navigation/
│   └── AppNavigation.kt              # Navigation setup
├── presentation/
│   ├── home/
│   │   ├── HomeScreen.kt             # Home UI
│   │   └── HomeViewModel.kt          # Home logic
│   └── login/
│       ├── LoginScreen.kt            # Login UI
│       └── LoginViewModel.kt         # Login logic
├── utils/
│   └── NetworkManager.kt             # Network monitoring
└── MainActivity.kt                   # Main entry point
```

## 🔐 Security Notes

- Never commit `google-services.json` to version control (already in `.gitignore`)
- Use `local.properties` for sensitive configuration
- The app uses modern Credential Manager API (no deprecated components)
- Web Client ID is stored in BuildConfig for compile-time safety

## 🐛 Troubleshooting

### Common Issues

1. **"Web client ID not found"**:
   - Ensure you've set the correct Web Client ID in `build.gradle.kts`
   - Check that `buildConfig = true` is enabled

2. **Google Sign-In not working**:
   - Verify SHA-1 certificate fingerprint in Firebase Console
   - Ensure `google-services.json` is in the correct location
   - Check internet connectivity

3. **Build errors**:
   - Clean and rebuild: **Build** → **Clean Project** → **Rebuild Project**
   - Invalidate caches: **File** → **Invalidate Caches and Restart**

4. **Authentication errors**:
   - Check Firebase Console for enabled providers
   - Verify package name matches exactly
   - Ensure Google Sign-In is enabled in Firebase Auth

## 📚 Dependencies

Key libraries used in this project:

- **Jetpack Compose** - Modern UI toolkit
- **Firebase Auth** - Authentication service
- **Credential Manager** - Modern authentication API
- **Navigation Compose** - Screen navigation
- **DataStore** - Local data storage
- **Material Design 3** - UI components
- **Lifecycle ViewModel** - State management

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

If you encounter any issues or have questions:

1. Check the troubleshooting section above
2. Review Firebase documentation
3. Create an issue in this repository
4. Check Android development best practices

---

**Happy coding! 🎉**
