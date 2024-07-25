package com.textprocessor.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

/**
 * DataManager manages collections of TextEntry objects using JavaFX Observable collections.
 */
public class DataManager {

    // Observable collections to store TextEntry objects
    private ObservableList<TextEntry> entriesList;
    private ObservableSet<TextEntry> entriesSet;
    private ObservableMap<String, TextEntry> entriesMap;

    /**
     * Constructor initializes the Observable collections.
     */
    public DataManager() {
        entriesList = FXCollections.observableArrayList();
        entriesSet = FXCollections.observableSet();
        entriesMap = FXCollections.observableHashMap();
    }

    /**
     * Retrieves the ObservableList of TextEntry objects.
     *
     * @return ObservableList<TextEntry>
     */
    public ObservableList<TextEntry> getEntriesList() {
        return entriesList;
    }

    /**
     * Retrieves the ObservableSet of TextEntry objects.
     *
     * @return ObservableSet<TextEntry>
     */
    public ObservableSet<TextEntry> getEntriesSet() {
        return entriesSet;
    }

    /**
     * Retrieves the ObservableMap of TextEntry objects, keyed by entry ID.
     *
     * @return ObservableMap<String, TextEntry>
     */
    public ObservableMap<String, TextEntry> getEntriesMap() {
        return entriesMap;
    }

    /**
     * Adds a TextEntry object to the ObservableList.
     *
     * @param entry TextEntry to add
     */
    public void addToList(TextEntry entry) {
        entriesList.add(entry);
    }

    /**
     * Adds a TextEntry object to the ObservableSet.
     *
     * @param entry TextEntry to add
     */
    public void addToSet(TextEntry entry) {
        entriesSet.add(entry);
    }

    /**
     * Adds a TextEntry object to the ObservableMap.
     *
     * @param entry TextEntry to add
     */
    public void addToMap(TextEntry entry) {
        entriesMap.put(entry.getId(), entry);
    }

    /**
     * Updates the content of a TextEntry in the ObservableList.
     *
     * @param id         ID of the TextEntry to update
     * @param newContent New content to set
     */
    public void updateListEntry(String id, String newContent) {
        for (TextEntry entry : entriesList) {
            if (entry.getId().equals(id)) {
                entry.setContent(newContent);
                break;
            }
        }
    }

    /**
     * Updates the content of a TextEntry in the ObservableSet.
     *
     * @param id         ID of the TextEntry to update
     * @param newContent New content to set
     */
    public void updateSetEntry(String id, String newContent) {
        for (TextEntry entry : entriesSet) {
            if (entry.getId().equals(id)) {
                entry.setContent(newContent);
                break;
            }
        }
    }

    /**
     * Updates the content of a TextEntry in the ObservableMap.
     *
     * @param id         ID of the TextEntry to update
     * @param newContent New content to set
     */
    public void updateMapEntry(String id, String newContent) {
        if (entriesMap.containsKey(id)) {
            TextEntry entry = entriesMap.get(id);
            entry.setContent(newContent);
        }
    }

    /**
     * Removes a TextEntry from the ObservableList.
     *
     * @param entry TextEntry to remove
     */
    public void removeFromList(TextEntry entry) {
        entriesList.remove(entry);
    }

    /**
     * Removes a TextEntry from the ObservableSet.
     *
     * @param entry TextEntry to remove
     */
    public void removeFromSet(TextEntry entry) {
        entriesSet.remove(entry);
    }

    /**
     * Removes a TextEntry from the ObservableMap.
     *
     * @param id ID of the TextEntry to remove
     */
    public void removeFromMap(String id) {
        entriesMap.remove(id);
    }
}