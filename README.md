# Get Aweigh: Navy Packing List App

## Purpose

This Android application is designed to help Navy sailors (and others) create and manage packing lists for various types of trips (work, leisure, combined). The app allows users to:

*   Select a date range for their trip.
*   View a dynamically generated packing list based on trip type and duration. (Currently, trip type is a UI element but does not yet filter the database.)
*   Mark items as packed (with visual feedback).
*   Add custom items to their packing list.
*   Select a category from a dropdown list (currently pre-populated).

## Current Status (as of March 8, 2024)

The application is in active development. The core functionality for creating a single packing list is implemented, including:

*   Basic UI using XML layouts (with some initial steps towards Jetpack Compose).
*   Date range selection using `MaterialDatePicker`.
*   Database persistence using Room (SQLite).
*   RecyclerView to display packing list items.
*   Checkbox functionality to mark items as packed.
*   Ability to add custom items (basic implementation).
*   Category selection using a Spinner (populated from the database).

## Technology Stack

*   **Language:** Kotlin
*   **UI:** Primarily XML layouts, with a plan to migrate to Jetpack Compose.
*   **Database:** Room (SQLite)
*   **Date Picker:** MaterialDatePicker
*   **Asynchronous Operations:** Kotlin Coroutines
*   **Dependency Management:** Gradle (with version catalog)

## Completed Tasks (Summary from CHANGELOG)
- Initial project setup with basic UI
- Room database integration
- RecyclerView display list
- Date Range Selection
- Add custom items
- Checkbox with strikethrough
- Delete item functionality
- MaterialDatePicker Theming
- Category Selection

(See `CHANGELOG.md` for a detailed history of changes.)

## TODO (Future Enhancements)

(See `TODO.md` for a detailed list of planned features and tasks.)

*   **User Authentication:** Implement user registration/login/logout using Firebase Authentication.
*   **Multiple Packing Lists:** Allow users to create and manage multiple packing lists.
*   **Packing List Screen Enhancements:** Improve the UI for editing item details (name, category, quantity), deleting items, and adding new categories.
*   **UI Improvements:** Refine the layout, implement a custom top app bar, and consider alternative UI elements.  Migrate to Jetpack Compose.
*   **Pre-defined Lists:**  Add Navy-specific pre-defined packing lists.
*   **Weather Integration:** Integrate a Weather API for location-based packing suggestions.
*   **(And many more features listed in TODO.md)**

## How to Build and Run

1.  Clone the repository: `git clone https://github.com/jtcovington/PackingApp.git`
2.  Open the project in Android Studio.
3.  Build the project: `Build` > `Make Project` (or use the keyboard shortcut).
4.  Run the app on an emulator or connected Android device.

## Contributing

(Add guidelines for contributing to your project here, if you plan to accept contributions.)

## License

(Add license information here.  If you're not sure, consider MIT or Apache 2.0 for open-source projects.)
