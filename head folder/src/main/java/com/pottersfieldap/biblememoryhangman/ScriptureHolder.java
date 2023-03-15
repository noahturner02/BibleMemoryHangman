package com.pottersfieldap.biblememoryhangman;

public class ScriptureHolder {
    private String scripture;
    private final static ScriptureHolder INSTANCE = new ScriptureHolder();

    private ScriptureHolder() {}

    public static ScriptureHolder getInstance() {
        return INSTANCE;
    }

    public void setScripture(String scripture) {
        this.scripture = scripture;
    }

    public String getScripture() {
        return scripture;
    }
}
