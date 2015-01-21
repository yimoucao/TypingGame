/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typingpracticegame;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Simon
 */
public class TypingPracticeGame extends Application {

    private void init(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        //create root node of scene, i.e. group
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //create scene with set width, height and color
        grid.getStyleClass().add("grid");
        Scene scene = new Scene(grid, 959, 569, Color.TRANSPARENT);
        //set scene to stage
        primaryStage.setScene(scene);
        scene.getStylesheets().add(TypingPracticeGame.class.getResource("mainPanel.css").toExternalForm());
        //center stage on screen
        primaryStage.centerOnScreen();
        //show the stage
        primaryStage.show();
        setDefaultDifficulty();//trans the default setting file to user's file system

        Button newGameBtn = new Button(" New Game ");
        newGameBtn.setId("new_game_button");
        newGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                try {
                    Gaming aNewGame = new Gaming();
                    aNewGame.start(new Stage());
                } catch (Exception ex) {
                    Logger.getLogger(TypingPracticeGame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        Button optionBtn = new Button("    Options    ");
        optionBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    new Options().start(new Stage());
                } catch (Exception ex) {
                    Logger.getLogger(TypingPracticeGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button scoresBtn = new Button("High Scores");
        scoresBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    new HighScores().start(new Stage());
                } catch (Exception ex) {
                    Logger.getLogger(TypingPracticeGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button aboutBtn = new Button("   About   ");
        aboutBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    new AboutInfo().start(new Stage());
                } catch (Exception ex) {
                    Logger.getLogger(TypingPracticeGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button exitBtn = new Button("      Exit       ");
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //in case we would like to close whole demo
                javafx.application.Platform.exit();
                //however we want to close only this instance of stage
                //primaryStage.close();
            }
        });

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.loadFont(MyUtil.getMyFontStr(), 80));
        //grid.add(scenetitle, 0, 0, 2, 1);
        // USE A LAYOUT VBOX FOR EASIER POSITIONING OF THE VISUAL NODES ON SCENE
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(0, 0, 0, 0));
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(scenetitle, newGameBtn, optionBtn, scoresBtn, aboutBtn, exitBtn);
        grid.getChildren().addAll(vBox);

        //HBox hbox = new HBox();
        //hbox.setSpacing(10);
        //hbox.setPadding(new Insets(60,0,0,20));
        //hbox.setAlignment(Pos.CENTER);
        //hbox.getChildren().add(exitBtn);
    }

    //start() in the entrance
    @Override
    public void start(Stage primaryStage) {
        init(primaryStage);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    //trans the default setting file to user's file system
    private void setDefaultDifficulty() {
        FileOutputStream out = null;
        try {
            //Todo: get info file if that file alreasy exists
            Properties prop = new Properties();
            InputStream in = getClass().getResourceAsStream("records.properties");
            prop.load(in);
            out = new FileOutputStream("records.properties");
            prop.store(out, "update difficulty");
        } catch (Exception ex) {
            Logger.getLogger(TypingPracticeGame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(TypingPracticeGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
