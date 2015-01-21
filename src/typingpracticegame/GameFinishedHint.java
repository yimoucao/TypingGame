/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typingpracticegame;

import com.sun.deploy.util.StringUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.control.TextField;
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
public class GameFinishedHint extends Application {

    private int score;

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void start(Stage hintStage) throws Exception {
        init(hintStage);
        hintStage.show();
    }

    private void init(Stage hintStage) {
        VBox gameFinishedVbox = new VBox();
        gameFinishedVbox.setSpacing(10);
        gameFinishedVbox.setAlignment(Pos.CENTER);
        gameFinishedVbox.setPadding(new Insets(20, 20, 20, 20));
        gameFinishedVbox.getStyleClass().add("about_vbox");
        //Stage gameFinishedStage = new Stage();
        hintStage.initStyle(StageStyle.TRANSPARENT);
        hintStage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(gameFinishedVbox, 331, 500, Color.TRANSPARENT);
        scene.getStylesheets().add(TypingPracticeGame.class.getResource("options.css").toExternalForm());

        hintStage.setScene(scene);
        hintStage.centerOnScreen();

        //Group gameFinishedGroup = new Group();
        TextField nameInput = new TextField("---");
        Button doneBtn = new Button("Done");

        ArrayList<ScoreRecord> recordsList = getScoreRecords();
        Comparable min = Collections.min(recordsList);
        ScoreRecord minRecord = (ScoreRecord) min;
        if (score > minRecord.getScore()) {

            Text congra = new Text("Score " + score + "! Top 5!");
            congra.setFont(Font.loadFont(MyUtil.getMyFontStr(), 40));
            Text inputHint = new Text("Please input your name:");
            congra.setFont(Font.loadFont(MyUtil.getMyFontStr(), 40));
            //////
            doneBtn.setOnAction((ActionEvent event) -> {
                //Todo: getThe name info and score to store
                String recordName = nameInput.getText();
                if (recordName.isEmpty()) {
                    recordName="---";
                }
                ScoreRecord newRecord = new ScoreRecord(recordName, score);
                recordsList.remove(minRecord);
                recordsList.add(newRecord);
                Collections.sort(recordsList);
                store2File(recordsList);
//                for(ScoreRecord sr:recordsList){
//                    System.out.println(sr.toString());
//                }
                hintStage.close();

            });

            gameFinishedVbox.getChildren().addAll(congra, inputHint, nameInput, doneBtn);

        } else {
            Text congra = new Text("You Scored " + score + "!");
            congra.setFont(Font.loadFont(MyUtil.getMyFontStr(), 40));

            doneBtn.setOnAction((ActionEvent event) -> {
                hintStage.close();
            });

            gameFinishedVbox.getChildren().addAll(congra, doneBtn);

        }

        hintStage.show();
    }

    private ArrayList getScoreRecords() {
        ArrayList<ScoreRecord> recordsList = new ArrayList();

        Properties prop = new Properties();
        try {
            FileInputStream in = new FileInputStream("records.properties");
            prop.load(in);
        } catch (IOException ex) {
            Logger.getLogger(Bubble.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < 5; i++) {
            String aRecord = prop.getProperty("record" + i);
            String[] nameAndScore = aRecord.split(" : ");
            ScoreRecord aScoreRecord
                    = new ScoreRecord(nameAndScore[0], Integer.parseInt(nameAndScore[1]));
            recordsList.add(aScoreRecord);
        }

        return recordsList;
    }

    private void store2File(ArrayList<ScoreRecord> recordsList) {
        FileOutputStream out = null;
        try {
            Properties prop = new Properties();
            FileInputStream in = new FileInputStream("records.properties");
            prop.load(in);//there sometimes are some exceptions
            int i = 4;
            for (ScoreRecord sr : recordsList) {
                prop.setProperty("record" + (i--), sr.toString());
                if (i < 0) {
                    break;
                }
            }
            out = new FileOutputStream("records.properties");
            prop.store(out, "update high scores");
        } catch (Exception ex) {
            Logger.getLogger(GameFinishedHint.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(GameFinishedHint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
