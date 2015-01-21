/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typingpracticegame;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
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
public class Options extends Application {

    private int levelNum;
    private Text diffDigit = new Text();

    @Override
    public void start(Stage optionsStage) throws Exception {
        init(optionsStage);
        optionsStage.show();
    }

    private void init(Stage optionsStage) {
        optionsStage.initStyle(StageStyle.TRANSPARENT);
        optionsStage.initModality(Modality.WINDOW_MODAL);
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getStyleClass().add("options_vbox");

        Scene scene = new Scene(vbox, 331, 500, Color.TRANSPARENT);
        scene.getStylesheets().add(TypingPracticeGame.class.getResource("options.css").toExternalForm());

        optionsStage.setScene(scene);
        optionsStage.centerOnScreen();
        optionsStage.show();

        Text optionsTitle = new Text("Options");
        optionsTitle.setFont(Font.loadFont(MyUtil.getMyFontStr(), 70));
        Text difficultyTitle = new Text("Difficulty:");
        difficultyTitle.setFont(Font.loadFont(MyUtil.getMyFontStr(), 40));

        levelNum = getLevelNum();

        Button leftArrowBtn = new Button("  <  ");
        leftArrowBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (levelNum > 1) {
                    levelNum--;
                }
                diffDigit.setText("" + (levelNum));
                setDifficulty2File(levelNum);
            }
        });
        Button rightArrowBtn = new Button("  >  ");
        rightArrowBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (levelNum < 5) {
                    levelNum++;
                }
                diffDigit.setText("" + (levelNum));
                setDifficulty2File(levelNum);
            }
        });

        diffDigit.setText("" + (levelNum));
        HBox lrChooseHbox = new HBox();
        lrChooseHbox.setSpacing(40);
        lrChooseHbox.setAlignment(Pos.CENTER);
        lrChooseHbox.getChildren().addAll(leftArrowBtn, diffDigit, rightArrowBtn);

        Button toMain = new Button("Return");
        toMain.setOnAction((ActionEvent event) -> {
            //in case we would like to close whole demo
            //javafx.application.Platform.exit();
            //however we want to close only this instance of stage
            optionsStage.close();

        });

        vbox.getChildren().addAll(optionsTitle, difficultyTitle, lrChooseHbox, toMain);
    }

    private void setDifficulty2File(int diffc) {
        try {
            Properties prop = new Properties();
            FileInputStream in = new FileInputStream("records.properties");
            prop.load(in);//there sometimes are some exceptions
            prop.setProperty("levelDifficulty", "" + diffc);

            //System.out.println(getClass().getResource("records.properties").toString());
            //System.out.println(getClass().getResource("records.properties").toExternalForm());
            //System.out.println(getClass().getResource("records.properties").getPath());
            //System.out.println(getClass().getResource("records.properties").getFile());
            FileOutputStream out = new FileOutputStream("records.properties");
            prop.store(out, "update difficulty");

        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int getLevelNum() {
        Properties prop = new Properties();

        try {
            FileInputStream in = new FileInputStream("records.properties");
            prop.load(in);
        } catch (IOException ex) {
            Logger.getLogger(Bubble.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Integer.parseInt(prop.getProperty("levelDifficulty"));

    }
}
