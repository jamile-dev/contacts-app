# Contacts App

## Overview
Contacts App is a Kotlin-based application designed to manage and organize your contacts efficiently. This app provides features to see, favorite, and search for contacts.

## Architecture
The architecture of the Contacts App is based on Clean Architecture principles, which separates the application into distinct layers, promoting scalability, maintainability, and testability.

![Untitled diagram-2025-02-19-104940](https://github.com/user-attachments/assets/9122fb0e-b18a-4819-b0ec-27c3c5da9150)


## Modularization
Contacts App have a modular architecture to enhance development and maintainability.

1. **:app**: The main module that integrates all other modules.
2. **:data**: Manages data sources, such as network and database.
3. **:domain**: Contains the business logic and interacts with the data layer.
4. **:presentation**: Manages the UI and user interactions.

### Key benefits include:
* **Improved Code Organization:**  Modules provide clear separation of concerns.
* **Enhanced Reusability:**  Reusable code components are isolated and easily shared.
* **Faster Build Times:** Parallel building of modules reduces overall build time.
* **Strict Visibility Control:**  Modules enforce access restrictions, preventing unintended dependencies.

## Data flow
<img width="600" alt="Arch_Diagram" src="https://github.com/user-attachments/assets/b17ad0cc-9a52-4611-8c2e-686b4f49f018" />

## Dependencies
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
