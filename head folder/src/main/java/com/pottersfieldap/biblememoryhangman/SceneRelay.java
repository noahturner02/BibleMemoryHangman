package com.pottersfieldap.biblememoryhangman;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

public class SceneRelay {
    private HashMap<String, Stage> stageMap = new HashMap<>();
    private Stage stage;
    private String scripture;
    private boolean win;
    private final static SceneRelay INSTANCE = new SceneRelay();

    private SceneRelay() {}

    public static SceneRelay getInstance() {
        return INSTANCE;
    }

    public void setScripture(String scripture) {
        this.scripture = scripture;
    }

    public String getScripture() {
        return scripture;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    public Boolean getWin() {
        return win;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void addToStageMap(String name, Stage stage) {
        stageMap.put(name, stage);
    }

    public Stage getFromStageMap(String name) {
        return stageMap.get(name);
    }
}
