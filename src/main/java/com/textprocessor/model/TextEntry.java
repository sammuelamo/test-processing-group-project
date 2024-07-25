package com.textprocessor.model;

import java.util.Objects;
import java.util.regex.Pattern;

public class TextEntry {
    private String id;
    private String content;

    public TextEntry(String id, String content) {
        this.id = id;
        this.content = content;
    }

    
    /** 
     * @return String
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextEntry textEntry = (TextEntry) o;
        return Objects.equals(id, textEntry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TextEntry{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getNumericId() {
        
        throw new UnsupportedOperationException("'getNumericId'");
    }

    public Pattern getActualText() {
        throw new UnsupportedOperationException("'getActualText'");
    }
}
