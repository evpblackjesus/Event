package io.blackjesus.calendario;

import java.awt.*;
import java.awt.event.*;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;
import java.time.LocalDate;
import javafx.geometry.Pos;
import javafx.geometry.Insets;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javafx.animation.PauseTransition;




import java.io.IOException;

public class Controller {
    @FXML
    private StackPane root;

    private void switchContent(Node node) {
        if(!root.getChildren().isEmpty()) {
            root.getChildren().clear();
        }
        root.getChildren().add(node);
    }

    @FXML
    private void onDayViewClick() throws IOException {
        Label label = new Label("day view");
        switchContent(label);
    }

    @FXML
    private void onWeekViewClick() {
        switchContent(new Label("weekView"));
    }

    @FXML
    private void onMonthViewClick() {
        switchContent(new Label("monthView"));
    }

    @FXML
    private VBox eventsBox;

    @FXML
    private TextField eventstext;

    @FXML
    private Button addEventButton;

    @FXML
    private VBox eventsbox;

    @FXML
    private CheckBox checkBox;


    //Hozzáadás funkció
    @FXML
    private void onEventAddClick() {
        String eventName = eventstext.getText();
        LocalDate eventDate = LocalDate.now();
        if (!eventName.isEmpty()) {
            CheckBox checkBox = new CheckBox();
            Label eventLabel = new Label(eventName);
            Label eventDateLabel = new Label(eventDate.toString());


            HBox eventHBox = new HBox(checkBox, eventLabel);
            eventHBox.setStyle("-fx-background-color: lightblue; -fx-border-color: black;");
            eventDateLabel.setPadding(new Insets(0, 0, 9, 0));



            //eseményfigyelő a CheckBox-hoz
            checkBox.setOnAction(event -> {
                // CheckBox ki van-e pipálva
                if (checkBox.isSelected()) {

                    //notification cuccos, ezt majd át akarom rakni az eventNotification() metódusba.
                    try{
                        //Obtain only one instance of the SystemTray object
                        SystemTray tray = SystemTray.getSystemTray();

                        Image image = Toolkit.getDefaultToolkit().createImage("some-icon.png");
                        //Alternative (if the icon is on the classpath):
                        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

                        TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
                        //Let the system resize the image if needed
                        trayIcon.setImageAutoSize(true);
                        //Set tooltip text for the tray icon
                        trayIcon.setToolTip("System tray icon demo");
                        tray.add(trayIcon);

                        // Display info notification:
                        trayIcon.displayMessage("Esemény elvégezve!", "Sikeresen elvégeztél egy eseményt!", MessageType.INFO);
                        // Error:
                        // trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.ERROR);
                        // Warning:
                        // trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.WARNING);
                    }catch(Exception ex){
                        System.err.print(ex);
                    }




                    //strikethrough stílus
                    eventLabel.setStyle("-fx-strikethrough: true;");

                    //PauseTransition-t a várakozáshoz
                    PauseTransition pause = new PauseTransition(Duration.seconds(2)); // A 2 másodperces késleltetés

                    // eseménykezelő
                    pause.setOnFinished(e -> {
                        // HBox eltüntetése
                        eventsbox.getChildren().remove(eventHBox);
                        eventsbox.getChildren().remove(eventDateLabel);
                    });

                    pause.play();
                }
            });

            // Hbox hozzáadása eventsbox-hoz
            eventsbox.getChildren().add(eventHBox);
            eventsbox.getChildren().add(eventDateLabel);


            // szöveg törlése
            eventstext.clear();
        }
    }

    //majd külön notfication funkció lesz dátum szerint
    @FXML
    private void eventNotification(){

    }


}
