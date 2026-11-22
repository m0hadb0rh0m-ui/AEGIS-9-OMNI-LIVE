# رَصَد // AEGIS-9 OMNI LIVE — Android

Android application shell for the AEGIS-9 monitoring platform. This repository builds an **APK** with GitHub Actions. It does **not** host the data backend, and it does not invent live data.

## Download an APK on your phone
1. Open the repository's **Actions** tab.
2. Select **Build Android APK**, then **Run workflow**.
3. Wait for a successful run; open it and download **AEGIS-9-OMNI-LIVE-debug-APK** under Artifacts.
4. Extract the ZIP artifact and install `app-debug.apk` on Android.
5. Launch the app and enter the HTTPS URL of your deployed AEGIS-9 platform.

## Requirements
The app is an Android WebView shell, requiring a deployed HTTPS web frontend and backend. Live feeds may require API keys and licenses. Never commit secrets to this public repository.

## Build details
Android Gradle Plugin 8.7.3; compile SDK 35; Java 17; min SDK 26. The workflow uses the Gradle executable installed on GitHub's runner. If no suitable Gradle version is present, install or configure a Gradle wrapper before building.

## Security
Debug APKs are for testing, not Play Store release. The app only accepts HTTPS URLs.
