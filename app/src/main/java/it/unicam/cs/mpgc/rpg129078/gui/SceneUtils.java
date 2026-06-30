package it.unicam.cs.mpgc.rpg129078.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;


public final class SceneUtils {

    private SceneUtils() {}


    public static <T> T cambiaScena(Node nodoCorrente, String fxmlResourcePath) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneUtils.class.getResource(fxmlResourcePath));
        Parent nuovaRoot = loader.load();
        Stage stage = (Stage) nodoCorrente.getScene().getWindow();
        stage.getScene().setRoot(nuovaRoot);
        return loader.getController();
    }
}