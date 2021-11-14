package com.main.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PopupHandler {
    public static void ShowError(String errorText){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(errorText);
        alert.showAndWait();
    }

    public static void ShowWarning(String warningText){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setContentText(warningText);
        alert.showAndWait();
    }

    public static void ShowInfo(String infoText){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(infoText);
        alert.showAndWait();
    }
}
