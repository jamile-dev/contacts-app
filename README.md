# Contacts App

## Overview
Contacts App is a Kotlin-based application designed to manage and organize your contacts efficiently. This app provides features to see, favorite, and search for contacts.

## Architecture
The architecture of the Contacts App is based on Clean Architecture principles, which separates the application into distinct layers, promoting scalability, maintainability, and testability.

### Layers
1. **Presentation Layer**: Manages the UI and user interactions using Jetpack Compose.
2. **Domain Layer**: Contains the business logic and application-specific rules.
3. **Data Layer**: Handles data operations, including API calls and database interactions.

## Module Division
The application is divided into the following modules:

1. **:app**: The main module that integrates all other modules.
2. **:data**: Manages data sources, such as network and database.
3. **:domain**: Contains the business logic and interacts with the data layer.
4. **:presentation**: Manages the UI and user interactions.

## Dependencies
The project utilizes several key dependencies:

- **Koin**: For dependency injection.
* **Kotlin:** Language, Coroutines + Flow for asynchronous operations.
* **Jetpack:**
   * Compose: Modern UI toolkit.
   * Lifecycle: Lifecycle-aware components.
   * ViewModel: UI-related data management.
   * Room: Local database persistence.
* **Retrofit2 & OkHttp3:** REST API communication.
* **Coil:** Image loading.
* **Material Design 3:** UI components and theming.
* **ksp:** Kotlin Symbol Processing API for code generation and analysis.
