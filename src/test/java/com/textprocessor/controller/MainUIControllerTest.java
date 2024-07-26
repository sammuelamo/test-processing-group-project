package com.textprocessor.controller;


import com.textprocessor.model.TextEntry;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class MainUIControllerTest extends ApplicationTest {

    private MainUIController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/textprocessor/utils/fxml/MainUI.fxml")) ;
        Parent root = loader.load();
        controller = loader.getController();

        stage.setScene(new Scene(root));
        stage.show();
        injectPrivateFields(controller);

    }

    private void injectPrivateFields(MainUIController controller) throws NoSuchFieldException, IllegalAccessException {
        Field regexField = MainUIController.class.getDeclaredField("regexField");
        regexField.setAccessible(true);
        regexField.set(controller, new TextField());

        Field textPool = MainUIController.class.getDeclaredField("textPool");
        textPool.setAccessible(true);
        textPool.set(controller, new TextArea());

        Field matchCount = MainUIController.class.getDeclaredField("matchCount");
        matchCount.setAccessible(true);
        matchCount.set(controller, new Label());

        Field replacementField = MainUIController.class.getDeclaredField("replacementField");
        replacementField.setAccessible(true);
        replacementField.set(controller, new TextField());

        Field resultText = MainUIController.class.getDeclaredField("resultText");
        resultText.setAccessible(true);
        resultText.set(controller, new TextFlow());

        Field findMatchesButton = MainUIController.class.getDeclaredField("findMatchesButton");
        findMatchesButton.setAccessible(true);
        Button button = new Button();
        button.setId("findMatchesButton");  // Set the ID
        findMatchesButton.set(controller, button);

        Field entriesListView = MainUIController.class.getDeclaredField("entriesListView");
        entriesListView.setAccessible(true);
        entriesListView.set(controller, new ListView<>());
    }

    @Test
    public void testFindMatches_validPattern() {
        TextField regexField = (TextField) lookup("#regexField").query();
        TextArea textPool = (TextArea) lookup("#textPool").query();
        clickOn(regexField).write("\\d+");
        clickOn(textPool).write("Here are some numbers: 123, 456, and 789.");

        WaitForAsyncUtils.waitForFxEvents();
        clickOn(lookup("#findMatches").queryButton());

        Label matchCount = (Label) lookup("#matchCount").query();
        assertEquals("3", matchCount.getText());
    }

    @Test
    public void testFindMatches_invalidPattern() {
        TextField regexField = (TextField) lookup("#regexField").query();
        TextArea textPool = (TextArea) lookup("#textPool").query();
        clickOn(regexField).write("[a-z]");
        clickOn(textPool).write("Here is some text.");
        clickOn(lookup("#findMatches").queryButton());

        Label matchCount = (Label) lookup("#matchCount").query();
        assertEquals("", matchCount.getText());
    }

    @Test
    public void testFindMatches_emptyInputs() {
        TextField regexField = (TextField) lookup("#regexField").query();
        TextArea textPool = (TextArea) lookup("#textPool").query();
        clickOn(regexField).write("");
        clickOn(textPool).write("");
        clickOn(lookup("#findMatches").queryButton());

        Label matchCount = (Label) lookup("#matchCount").query();
        assertEquals("", matchCount.getText());
    }

    @Test
    public void testReplaceMatches_validPattern() {
        TextField regexField = (TextField) lookup("#regexField").query();
        TextArea textPool = (TextArea) lookup("#textPool").query();
        TextField replacementField = (TextField) lookup("#replacementField").query();
        clickOn(regexField).write("\\d+");
        clickOn(textPool).write("Replace 123 with ABC.");
        clickOn(replacementField).write("ABC");
        clickOn(lookup("#replaceMatches").queryButton());

        TextFlow resultText = (TextFlow) lookup("#resultText").query();
        assertFalse(resultText.getChildren().isEmpty());
    }

    @Test
    public void testReplaceMatches_emptyInputs() {
        TextField regexField = (TextField) lookup("#regexField").query();
        TextArea textPool = (TextArea) lookup("#textPool").query();
        TextField replacementField = (TextField) lookup("#replacementField").query();
        clickOn(regexField).write("");
        clickOn(textPool).write("");
        clickOn(replacementField).write("");
        clickOn(lookup("#replaceMatches").queryButton());

        TextFlow resultText = (TextFlow) lookup("#resultText").query();
        assertTrue(resultText.getChildren().isEmpty());
    }

    @Test
    public void testAddEntry() {
        // Ensure we're on the JavaFX thread
        Platform.runLater(() -> {
            TextFlow resultText = lookup("#resultText").query();
            assertNotNull(resultText, "resultText should not be null");
            resultText.getChildren().clear();
            resultText.getChildren().add(new javafx.scene.text.Text("Sample text."));
        });

        // Wait for the UI update to complete
        WaitForAsyncUtils.waitForFxEvents();

        // Ensure the button exists and is clickable
        Button addEntryButton = lookup("#addEntry").queryButton();
        assertNotNull(addEntryButton, "addEntry button should not be null");
        clickOn(addEntryButton);

        // Wait for potential async operations
        WaitForAsyncUtils.waitForFxEvents();

        // Verify the ListView
        ListView<TextEntry> entriesListView = lookup("#entriesListView").query();
        assertNotNull(entriesListView, "entriesListView should not be null");

        // Wait a bit and keep checking (with a timeout)
        WaitForAsyncUtils.waitForAsync(5000, () -> entriesListView.getItems().size() == 1);

        assertEquals(1, entriesListView.getItems().size(), "entriesListView should contain one item");
    }

    @Test
    public void testDeleteEntry() {
        TextEntry entry = new TextEntry("1", "Sample text.");
        ListView<TextEntry> entriesListView = (ListView<TextEntry>) lookup("#entriesListView").query();
        entriesListView.getItems().add(entry);

        entriesListView.getSelectionModel().select(entry);
        clickOn(lookup("#deleteEntry").queryButton());

        assertTrue(entriesListView.getItems().isEmpty());
    }

    private void printNodeHierarchy(Node node, int level) {
        String indent = " ".repeat(level * 2);
        System.out.println(indent + node.getClass().getSimpleName() + " - " + node.getId());
        if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                printNodeHierarchy(child, level + 1);
            }
        }
    }
}
