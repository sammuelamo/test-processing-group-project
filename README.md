# Text Processing Tool

## Overview

The Text Processing Tool is a JavaFX application designed for advanced text manipulation using regular expressions. It provides a user-friendly interface for finding and replacing text patterns, managing text entries, and exporting results.

## Features

- Regular expression pattern matching
- Text replacement with highlighting
- File upload for text input
- Entry management (add, delete, export)
- Real-time match counting
- Cross-platform compatibility

## Technology Stack

- Java
- JavaFX
- JUnit 5 for testing
- CSS for styling

## Project Structure

````
com.textprocessor/
│
├── controller/
│   ├── MainUIController.java
│   └── MainUIControllerTest.java
│
├── model/
│   ├── DataManager.java
│   └── TextEntry.java
│
├── utils/
│   └── fxml/
│       └── MainUI.fxml
│
├── css/
│   └── styles.css
│
└── Main.java
````
## Setup and Installation

### Prerequisites

- Java Development Kit (JDK) 11 or later
- JavaFX SDK 11 or later
- Maven (for dependency management and building)

### Building the Project

1. Clone the repository:git clone https://github.com/yourusername/text-processing-tool.git
   cd text-processing-tool

2. Build the project using Maven:
   This will create a JAR file in the `target` directory.

## Running the Application

To run the application from the JAR file:Note: Ensure that the JavaFX runtime is in your module path or classpath.

## Usage Guide

1. **Finding Matches**:
    - Enter a regex pattern in the "Regex Pattern" field.
    - Input or upload text in the "Text Pool" area.
    - Click "Find Matches" to see the number of matches.

2. **Replacing Text**:
    - After finding matches, enter replacement text in the "Replacement Text" field.
    - Click "Replace Matches" to apply the replacement.
    - The result will be displayed in the "Result" area with blue highlighting.

3. **Managing Entries**:
    - Click "Add Entry" to save the current result.
    - Select an entry in the list and click "Delete Entry" to remove it.
    - Use "Export Entry" to save the selected entry as a text file.

4. **File Operations**:
    - Click "Upload File" to load text from a file into the Text Pool.
    - Use "Export Entry" to save processed text to a file.

## Testing

The project includes JUnit tests for the `MainUIController`. To run the tests: mvn test

## Styling

The application uses a custom CSS file (`styles.css`) for consistent styling across the UI. Key style features include:

- A clean, modern interface with a light background
- Distinctive primary and secondary button styles
- Responsive text inputs and result displays
- Custom styling for the list view and split pane

## Deployment

To deploy the application:

1. Ensure all dependencies are correctly set up in the `pom.xml` file.
2. Build the project using Maven:mvn clean package

3. Locate the generated JAR file in the `target` directory.
4. Distribute this JAR file along with any necessary runtime dependencies.

Users can then run the application using the command provided in the "Running the Application" section.

## Contributing

Contributions to the Text Processing Tool are welcome. Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/0006-user-notification`)
3. Commit your changes (`git commit -m 'feat: added user notification'`)
4. Push to the branch (`git push origin feature/0006-user-notification`)
5. Open a Pull Request

## License

MIT License



Project Link: https://github.com/samuelnapasco/test-processing-group-project.git