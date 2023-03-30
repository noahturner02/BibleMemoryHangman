package com.pottersfieldap.biblememoryhangman;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

/* This class is responsible for communication that happens between different scenes of the program. Since this class
has a SceneRelay parameter which is static, the state of this class is the same between all references. There are two
main roles of this class:
1) This class facilitates transitions between scenes and stages. This happens with the stageMap. stageMap is a Hashmap
that holds the different stages that are used. Since stages normally cannot be referenced outside of their respective
controller, this provides a way for one controller to influence another's stage.

2) This class facilitates data transfer between different scenes. Since one screen normally can't see the state of
another's controller, this class has fields with setters and getters. The setters are called by one class and read by
another. There may be a better way of sending data than this, but if there is, I don't know it.

If you want to call SceneRelay, do it like this:

SceneRelay
 */
public class SceneRelay {
    // Holds stage references with corresponding String key
    private HashMap<String, Stage> stageMap = new HashMap<>();
    // Holds a passage of scripture. Passed from setup to hangman game
    private String scripture;
    // Tells whether the game was a win or a loss. Passed from hangman game to result screen
    private boolean win;
    // Allows the same instance to be accessed anywhere
    private final static SceneRelay INSTANCE = new SceneRelay();

    private SceneRelay() {}
    // Access our instance of SceneRelay
    public static SceneRelay getInstance() {
        return INSTANCE;
    }
    // Set the scripture to be passed
    public void setScripture(String scripture) {
        this.scripture = scripture;
    }
    // Retrieve scripture
    public String getScripture() {
        return scripture;
    }
    // Set win status
    public void setWin(Boolean win) {
        this.win = win;
    }
    // get win status
    public Boolean getWin() {
        return win;
    }
    // Add a stage to the stage map.
    public void addToStageMap(String name, Stage stage) {
        stageMap.put(name, stage);
    }
    // get a stage from the stage map
    public Stage getFromStageMap(String name) {
        return stageMap.get(name);
    }
}
