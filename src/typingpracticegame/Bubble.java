/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typingpracticegame;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Simon
 */
public class Bubble extends Pane {

    private Timeline timeline;
    private String aChar;
    private static Gaming parentGame;
    private static int initLifeCount = Gaming.getInitLifeCount();
    private Text text2show;
    private static Pane pane4bubble;
    private double layoutX4afterHit;
    private double layoutY4afterHit;

    private int setAbubble() {
        int abc = new Double(Math.round(Math.random() * 100)).intValue() % 26 + 65;
        Character char2show = (char) (abc);
        aChar = char2show.toString();
        text2show = new Text(aChar);

        int pathX = 100 + 60 * (new Double(Math.round(Math.random() * 10)).intValue());
        text2show.setLayoutX(pathX);//path: 100,160,220,280,340,400,460,520,580,640,700
        text2show.setLayoutY(40);
        text2show.setScaleX(1.5);
        text2show.setScaleY(1.5);
        pane4bubble.getChildren().add(text2show);

        timeline = new Timeline();
        int durationSec = getSpeedRate();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(durationSec), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //To do :
                /*
                 1, change the color of grass
                 2, record the count that the specific key isn't pressed
                 */
                pane4bubble.getChildren().remove(text2show);
                aChar = "";
                timeline.stop();
                showLifeHint();
            }

        }, new KeyValue(text2show.layoutYProperty(), 520));
        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);
        //System.out.println(aChar);
        timeline.play();
        return -1;
    }

    public void gotHit() {
        //this.
        layoutX4afterHit = text2show.getLayoutX();
        layoutY4afterHit = text2show.getLayoutY();
        pane4bubble.getChildren().remove(text2show);
        timeline.stop();
        afterHitEffect();
    }

    public Bubble(Pane pane4bubble, Gaming parentGame) {
        Bubble.pane4bubble = pane4bubble;
        Bubble.parentGame = parentGame;
        setAbubble();

    }

    public String getTheChar() {
        return aChar;
    }

    public void afterHitEffect() {
        Group afterHitGroup = new Group();
        StackPane stackPane4Hit = new StackPane();
        Circle circle = new Circle(6);
        Circle circle1 = new Circle(Math.random() + 3.5);
        Circle circle2 = new Circle(Math.random() + 2.5);
        Circle circle3 = new Circle(Math.random() + 1.5);
        Circle circle4 = new Circle(Math.random() + 0.5);
        Circle circle5 = new Circle(Math.random() / 2);
        stackPane4Hit.getChildren().
                addAll(circle, circle1, circle2, circle3, circle4, circle5);
        afterHitGroup.getChildren().addAll(stackPane4Hit);
        afterHitGroup.setLayoutX(layoutX4afterHit);
        afterHitGroup.setLayoutY(layoutY4afterHit);
        pane4bubble.getChildren().add(afterHitGroup);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), afterHitGroup);
        scaleTransition.setToX(1.4);
        scaleTransition.setToY(1.4);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), afterHitGroup);
        fadeTransition.setFromValue(1.0f);
        fadeTransition.setToValue(0.2f);
        int[] drc = generateDegree();
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.3), circle1);
        translateTransition1.setByX(12 * Math.cos(72 * drc[0]));
        translateTransition1.setByY(12 * Math.sin(72 * drc[0]));
        TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.3), circle2);
        translateTransition2.setByX(16 * Math.cos(72 * drc[1]));
        translateTransition2.setByY(16 * Math.cos(72 * drc[1]));
        TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(0.3), circle3);
        translateTransition3.setByX(22 * Math.cos(72 * drc[2]));
        translateTransition3.setByY(22 * Math.cos(72 * drc[2]));
        TranslateTransition translateTransition4 = new TranslateTransition(Duration.seconds(0.3), circle4);
        translateTransition4.setByX(26 * Math.cos(72 * drc[3]));
        translateTransition4.setByY(26 * Math.cos(72 * drc[3]));
        TranslateTransition translateTransition5 = new TranslateTransition(Duration.seconds(0.3), circle5);
        translateTransition5.setByX(30 * Math.cos(72 * drc[4]));
        translateTransition5.setByY(30 * Math.cos(72 * drc[4]));

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                scaleTransition,
                fadeTransition,
                translateTransition1,
                translateTransition2,
                translateTransition3,
                translateTransition4,
                translateTransition5
        );
        //parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                pane4bubble.getChildren().remove(afterHitGroup);
                parallelTransition.stop();
            }
        });
        parallelTransition.play();

    }

    private int[] generateDegree() {
        int returnValue[] = {0, 1, 2, 3, 4};
        int temp1;
        int temp2;
        int temp3;
        Random r = new Random();
        for (int i = 0; i < returnValue.length; i++) { //随机交换send.length次 
            temp1 = Math.abs(r.nextInt()) % (returnValue.length - 1); //随机产生一个位置
            temp2 = Math.abs(r.nextInt()) % (returnValue.length - 1); //随机产生另一个位置
            if (temp1 != temp2) {
                temp3 = returnValue[temp1];
                returnValue[temp1] = returnValue[temp2];
                returnValue[temp2] = temp3;
            }

        }
        return returnValue;
    }

    private int getSpeedRate() {
        Properties prop = new Properties();

        try {
            FileInputStream in = new FileInputStream("records.properties");
            prop.load(in);
        } catch (IOException ex) {
            Logger.getLogger(Bubble.class.getName()).log(Level.SEVERE, null, ex);
        }
        int compute = (-1) * Integer.parseInt(prop.getProperty("levelDifficulty")) + 6;
        return compute;

    }

    private static void showLifeHint() {
        int leftLife = --Bubble.initLifeCount;
        if (leftLife <= 0) {
            parentGame.noLife();
            initLifeCount=5;
            return;
        }
        Text lifeHint = new Text("" + leftLife + " life(s) Left");
        lifeHint.setFont(Font.loadFont(MyUtil.getMyFontStr(), 80));
        lifeHint.setLayoutX(200);
        lifeHint.setLayoutY(280);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), lifeHint);
        fadeTransition.setFromValue(0.5f);
        fadeTransition.setToValue(0f);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                pane4bubble.getChildren().remove(lifeHint);
                fadeTransition.stop();
            }
        });
        pane4bubble.getChildren().add(lifeHint);
        fadeTransition.play();
    }

    public Timeline getTimeline() {
        return timeline;
    }

}
