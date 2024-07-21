# Android Person List App

## Overview

The Person List App showcases advanced Android development techniques, including MVVM clean architecture, coroutines, and modularization. The app interacts with a remote data source, implements caching, and features a well-designed user interface.

## Features

- **User Interfaces**: XML-based layouts for clear and user-friendly design.
- **Data Loading and Caching**: Fetches data from [Random User Generator](https://randomuser.me) and caches it with Room to reduce redundant network calls.
- **Detail Screen**: Provides comprehensive details about each person.
- **Swipe-to-Refresh**: Allows refreshing of data by swiping.
- **Pagination**: Loads more data as the user scrolls down.
- **Material Design**: Adheres to Material Design guidelines.
- **Modularization**: Uses a multi-modular approach for better project organization.

## Architecture

- **MVVM (Model-View-ViewModel)**: Separates concerns for cleaner code.
- **Clean Architecture**: Structured into Presentation, Domain, and Data layers.
- **Coroutines**: Handles asynchronous operations smoothly.
- **Modularization**: Divides the project into separate modules.

## Error Handling and Network Handling

- Implemented robust error handling for network and data operations.
- Network requests are efficiently managed to handle retries and errors.

## ProGuard and Build Flavors

- **ProGuard**: Enabled for code obfuscation and optimization.
- **Build Flavors**: Includes mock and prod flavors to cater to different environments.

## Setup and Configuration

1. **Clone the Repository**:
   ```bash
   git clone <GitHub Repository URL>
   cd <project-directory>
