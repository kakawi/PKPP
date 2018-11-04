package com.hlebon.gui;

import javafx.scene.control.Alert;

public class DialogManager {

    public static void showInfoDialog(String text) {
        showInfoDialog("Информация", text);
    }

    public static void showInfoDialog(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText("");
        alert.showAndWait();
    }

    public static void showErrorDialog(String text) {
        showErrorDialog("Ошибка", text);
    }

    public static void showErrorDialog(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText("");
        alert.showAndWait();
    }
}
