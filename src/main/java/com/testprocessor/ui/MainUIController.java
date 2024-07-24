package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import model.DataManager;
import model.TextEntry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller class for the main UI functionality.
 */
public class MainUIController {

    @FXML
    private TextField regexField;

    @FXML
    private TextArea textPool;

    @FXML
    private Label matchCount;

    @FXML
    private TextField replacementField;

    @FXML
    private TextFlow resultText;

    @FXML
    private ListView<TextEntry> entriesListView;

    private DataManager dataManager;
    private int currentId = 1;

    /**
     * Initializes the controller, setting up data manager and list view.
     */
    @FXML
    public void initialize() {
        dataManager = new DataManager();
        entriesListView.setItems(dataManager.getEntriesList());
    }


    
    /**
     * Finds and counts regex matches in the text pool.
     */
    @FXML
    public void findMatches() {
        String regex = regexField.getText();
        String text = textPool.getText();

        if (regex.isEmpty() || text.isEmpty()) {
            showAlert("Error", "Regex pattern and text pool cannot be empty.");
            return;
        }

        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            int count = 0;
            while (matcher.find()) {
                count++;
            }

            matchCount.setText(String.valueOf(count));
        } catch (Exception e) {
            showAlert("Error", "Invalid regex pattern.");
        }
    }




    /**
     * Replaces regex matches in the text pool with specified replacement text.
     */
    @FXML
    public void replaceMatches() {
        String regex = regexField.getText();
        String text = textPool.getText();
        String replacement = replacementField.getText();

        if (regex.isEmpty() || text.isEmpty() || replacement.isEmpty()) {
            showAlert("Error", "Regex pattern, text pool, and replacement text cannot be empty.");
            return;
        }

        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            resultText.getChildren().clear();

            int lastEnd = 0;
            while (matcher.find()) {
                Text beforeMatch = new Text(text.substring(lastEnd, matcher.start()));
                resultText.getChildren().add(beforeMatch);

                Text highlightedReplacement = new Text(replacement);
                highlightedReplacement.setStyle("-fx-fill: blue;");
                resultText.getChildren().add(highlightedReplacement);

                lastEnd = matcher.end();
            }

            Text remainingText = new Text(text.substring(lastEnd));
            resultText.getChildren().add(remainingText);

            showAlert("Replacement Complete", "Regex replacements have been applied.");

        } catch (Exception e) {
            showAlert("Error", "Invalid regex pattern: " + e.getMessage());
        }
    }




    /**
     * Adds the current result as an entry to the data manager.
     */
    @FXML
    public void addEntry() {
        StringBuilder contentBuilder = new StringBuilder();
        for (javafx.scene.Node node : resultText.getChildren()) {
            if (node instanceof Text) {
                contentBuilder.append(((Text) node).getText());
            }
        }

        String content = contentBuilder.toString();
        if (content.isEmpty()) {
            showAlert("Error", "Result text cannot be empty.");
            return;
        }

        String numericId = String.valueOf(currentId++);
        TextEntry entry = new TextEntry(numericId, content);
        dataManager.addToList(entry);
        dataManager.addToSet(entry);
        dataManager.addToMap(entry);
        showAlert("Info", "Entry added to collections.");
    }




    /**
     * Loads content from a selected file into the text pool.
     */
    @FXML
    public void updateEntry() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                String content = new String(java.nio.file.Files.readAllBytes(selectedFile.toPath()));
                textPool.setText(content);
                showAlert("File Upload", "File content loaded into text pool.");
            } catch (IOException e) {
                showAlert("Error", "Failed to read file: " + e.getMessage());
            }
        } else {
            showAlert("File Upload", "No file selected.");
        }
    }




    /**
     * Deletes the selected entry from the collections.
     */
    @FXML
    public void deleteEntry() {
        TextEntry entry = entriesListView.getSelectionModel().getSelectedItem();

        if (entry != null) {
            dataManager.removeFromList(entry);
            dataManager.removeFromSet(entry);
            dataManager.removeFromMap(entry.getId());

            entriesListView.getItems().remove(entry);

            showAlert("Info", "Entry removed from collections.");
        }

        resultText.getChildren().clear();
    }



    /**
     * Shows all entries in the data manager.
     */
    @FXML
    public void showAllEntries() {
        StringBuilder sb = new StringBuilder();
        List<TextEntry> allEntries = dataManager.getEntriesList();
        for (TextEntry entry : allEntries) {
            sb.append(entry.getId()).append(": ").append(entry.getContent()).append("\n");
        }
        showAlert("All Entries", sb.toString());
    }


    /**
     * Exports the current result as a text file.
     */
    @FXML
    public void exportEntry() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            StringBuilder contentBuilder = new StringBuilder();
            for (javafx.scene.Node node : resultText.getChildren()) {
                if (node instanceof Text) {
                    contentBuilder.append(((Text) node).getText());
                }
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(contentBuilder.toString());
                showAlert("Export Complete", "Entry exported to " + file.getAbsolutePath());
            } catch (IOException e) {
                showAlert("Error", "Failed to export entry: " + e.getMessage());
            }
        } else {
            showAlert("Export Cancelled", "No file selected for export.");
        }
    }


    /**
     * Shows an alert dialog with the given title and message.
     *
     * @param title   Title of the alert
     * @param message Message content of the alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}