package com.pottersfieldap.biblememoryhangman;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
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
    // Add a stage to the stage map. The name must be an FMXL file.
    public void addToStageMap(String name, Stage stage) {
        stageMap.put(name, stage);
    }
    // get a stage from the stage map
    public Stage getFromStageMap(String name) {
        return stageMap.get(name);
    }
    /* Facilitates switching between scenes. First String parameter is the name of the FXML file of the current scene,
    and second String parameter is the name of the FXML file of the scene being transitioned to. Included are the
    dimensions for the window (only used if a new window is being made). Finally, a boolean is used to indicate whether
    a new window should be made or if the old stage should just be shown.
     */
    public void switchScene(String current_scene, String future_scene, int xDimension, int yDimension, Boolean new_scene) {
        // If a new scene should be made
        if (new_scene) {
            // Make new Stage
            newStage(future_scene, xDimension, yDimension);
            // Hide old stage
            getFromStageMap(current_scene).hide();
        } else {
            // Simply show the new stage and hide the old one.
            getFromStageMap(future_scene).show();
            getFromStageMap(current_scene).hide();
        }
    }
    // Make a new Stage without replacement
    public void newStage(String future_scene, int xDimension, int yDimension) {
        try {
            // Use the FXMLLoader to make a whole new stage
            Parent root = FXMLLoader.load(getClass().getResource(future_scene));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, xDimension, yDimension));
            addToStageMap(future_scene, newStage);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
