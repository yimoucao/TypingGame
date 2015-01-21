/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typingpracticegame;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Simon
 */
public class HighScores extends Application {

    @Override
    public void start(Stage highscoreStage) throws Exception {
        init(highscoreStage);
        highscoreStage.show();
    }

    private void init(Stage highscoreStage) {
        highscoreStage.initStyle(StageStyle.TRANSPARENT);
        highscoreStage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getStyleClass().add("scores_vbox");

        Scene scene = new Scene(vbox, 331, 500, Color.TRANSPARENT);
        scene.getStylesheets().add(TypingPracticeGame.class.getResource("options.css").toExternalForm());

        highscoreStage.setScene(scene);
        highscoreStage.centerOnScreen();
        highscoreStage.show();

        Text scoresTitle = new Text("High Scores");
        scoresTitle.setFont(Font.loadFont(MyUtil.getMyFontStr(), 70));

        Group scoresPane = getScoresPane();

        Button toMain = new Button("Return");
        toMain.setOnAction((ActionEvent event) -> {
            highscoreStage.close();

        });

        vbox.getChildren().addAll(scoresTitle, scoresPane, toMain);
    }

    private Group getScoresPane() {
        Group scoresPane = new Group();
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        //vbox.setSpacing(0);

        Properties prop = new Properties();
        try {
            FileInputStream in = new FileInputStream("records.properties");
            prop.load(in);
        } catch (IOException ex) {
            Logger.getLogger(Bubble.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < 5; i++) {
            String aRecord = prop.getProperty("record" + i);
            Text aRecordShow = new Text(aRecord);
            aRecordShow.setFont(Font.loadFont(MyUtil.getMyFontStr(), 25));
            vbox.getChildren().add(aRecordShow);
        }
        scoresPane.getChildren().add(vbox);
        return scoresPane;
    }

}
