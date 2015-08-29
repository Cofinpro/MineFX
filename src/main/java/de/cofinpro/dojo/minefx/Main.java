package de.cofinpro.dojo.minefx;

import de.cofinpro.dojo.minefx.model.ConfigFx;
import de.cofinpro.dojo.minefx.multiplayer.MulticastTransmitter;
import de.cofinpro.dojo.minefx.view.ConfigController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.text.DecimalFormat;

public class Main extends Application {

    private static final DecimalFormat DF = new DecimalFormat("00");
    private GamePanel gamePanel;
    private long startMillis;
    private Timeline timerTimeline;
    private Window parentWindow;
    private ConfigFx configFx;
    private Stage primaryStage;
    private MulticastTransmitter transmitter;
    private ObservableList<UserScoreEntry> userScoreData = FXCollections.observableArrayList();

    public Main() throws IOException {
        transmitter = MulticastTransmitter.getInstance();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        configFx = new ConfigFx(10, 10, 5, false);
        createPlayground();
    }

    public void createPlayground() throws Exception {
        parentWindow = primaryStage;


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(createMenu());
        borderPane.setBottom(createStatusBar());

        gamePanel = new GamePanel(configFx, timerTimeline, primaryStage, userScoreData);
        borderPane.setCenter(new ScrollPane(gamePanel));
        borderPane.setRight(createUserScoreTable());

        primaryStage.setTitle("Shitsweeper");
        final Scene scene = new Scene(borderPane);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();

        primaryStage.setMaxWidth(Screen.getPrimary().getBounds().getWidth());
        primaryStage.setMaxHeight(Screen.getPrimary().getBounds().getHeight());

        primaryStage.show();

        initializeMultiplayer(primaryStage);
    }

    private TableView<UserScoreEntry> createUserScoreTable() {
        TableView<UserScoreEntry> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<UserScoreEntry, String> gamerNameColumn = new TableColumn<>("Gamer");
        gamerNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<UserScoreEntry, Integer> pointColumn = new TableColumn<>("Points");
        pointColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        userScoreData.addListener((ListChangeListener<UserScoreEntry>) c -> {
            tableView.getItems().clear();
            tableView.getItems().addAll(userScoreData);
        });
        tableView.getItems().addAll(userScoreData);
        tableView.getColumns().addAll(gamerNameColumn, pointColumn);
        return tableView;
    }

    private void initializeMultiplayer(Stage primaryStage) {
        transmitter.setGamePanel(gamePanel);
        transmitter.listen();
        primaryStage.setOnCloseRequest(e -> {
            try {
                transmitter.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }


    private MenuBar createMenu() {
        Menu menu = new Menu();
        menu.setText("Shit");

        Menu menu1 = new Menu();
        menu1.setText("Types of Shit");
        MenuItem showShitConditionsMenuItem = new MenuItem("Shit conditions");

        showShitConditionsMenuItem.setOnAction(typesOfShitActionEvent -> this.showConfigEditDialog(configFx));
        menu1.getItems().add(showShitConditionsMenuItem);

        MenuItem menuItem = new MenuItem("Shit again");
        menuItem.setOnAction(event -> this.restart());
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Transfer shit");
        menuItem.setOnAction(event -> broadcastGameboard());
        menu.getItems().add(menuItem);

        MenuBar bar = new MenuBar();
        bar.getMenus().add(menu);
        bar.getMenus().add(menu1);

        return bar;
    }

    private GridPane createStatusBar() {

        startMillis = System.currentTimeMillis();

        Label time = new Label();
        GridPane pane = new GridPane();
        pane.add(time, 0, 0);
        pane.setMaxHeight(20);
        pane.setPrefHeight(20);

        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            Duration duration = new Duration(System.currentTimeMillis() - startMillis);
            String text = DF.format(duration.toHours()) + ":" + DF.format(duration.toMinutes()) + ":" + DF.format(duration.toSeconds());
            time.setText(text);
        }));

        timerTimeline.setCycleCount(Animation.INDEFINITE);
        timerTimeline.play();

        return pane;
    }

    public void restart() {
        startMillis = System.currentTimeMillis();
        timerTimeline.play();
        gamePanel.start();
        primaryStage.sizeToScene();
    }

    public void broadcastGameboard() {
        try {
            gamePanel.broadcastGameboard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showConfigEditDialog(ConfigFx configFx) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("view/ConfigMenu.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Shit Conditions");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(parentWindow);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ConfigController controller = loader.getController();
            controller.setConfigDialogStage(dialogStage);
            controller.setConfigFx(configFx);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Main.launch(args);
    }
}
