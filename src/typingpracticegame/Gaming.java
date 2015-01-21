/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typingpracticegame;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Simon
 */
public class Gaming extends Application {

    //private Timeline timeline1;
    private Timeline delayTimeline;
    private LinkedBlockingQueue<Bubble> myQueue;
    private int bubblesCount = 0;
    private int hitCount = 0;
    private static int initLifeCount = 5;
    private Gaming thisGame = this;

    Stage gameStage;
    Pane gameGrid;
    Text hitNum2show;
    Text bbCount2show;

    @Override
    public void start(Stage gameStage) throws Exception {
        this.gameStage = gameStage;
        init(gameStage);
        gameStage.show();
        play();
    }

    private void init(Stage gameStage) {
        //in case we would like to close whole demo
        //javafx.application.Platform.exit();

        gameStage.initStyle(StageStyle.TRANSPARENT);
        gameStage.initModality(Modality.APPLICATION_MODAL);
        //create root node of scene, i.e. group
        gameGrid = new Pane();
        //gameGrid.setHgap(10);
        //gameGrid.setVgap(10);
        //gameGrid.setPadding(new Insets(25, 25, 25, 25));

        //create scene with set width, height and color
        gameGrid.getStyleClass().add("game_grid");
        Scene scene = new Scene(gameGrid, 959, 569, Color.TRANSPARENT);
        //set scene to stage
        gameStage.setScene(scene);
        scene.getStylesheets().add(TypingPracticeGame.class.getResource("mainPanel.css").toExternalForm());
        //center stage on screen
        gameStage.centerOnScreen();
        //show the stage
        gameStage.show();

        Button return2MainMenu = new Button("Main Menu");
        return2MainMenu.setOnAction((ActionEvent event) -> {
            //in case we would like to close whole demo
            //javafx.application.Platform.exit();
            //however we want to close only this instance of stage
            gameStage.close();
            stop();
        });

        Button pauseBtn = new Button("Pause");
        pauseBtn.setOnAction((ActionEvent event) -> {
            pauseAll();
        });
        Button resumeBtn = new Button("Resume");
        resumeBtn.setOnAction((ActionEvent event) -> {
            play();
        });
        hitNum2show = new Text("Hit: " + hitCount);
        hitNum2show.setId("hit_num_to_show");

        bbCount2show = new Text("All: " + bubblesCount);
        bbCount2show.setId("bb_count_to_show");

        VBox gameVbox = new VBox();
        gameVbox.setId("gameVbox");
        gameVbox.setSpacing(10);
        gameVbox.setPadding(new Insets(20, 20, 20, 20));
        gameVbox.setAlignment(Pos.TOP_CENTER);
        gameVbox.setTranslateY(180);
        gameVbox.setLayoutX(740);
        gameVbox.setLayoutY(20);
        gameVbox.getChildren().addAll(hitNum2show, bbCount2show, return2MainMenu, pauseBtn, resumeBtn);

        gameGrid.getChildren().addAll(gameVbox);

        Path grass = PathBuilder.create()
                .elements(
                        new MoveTo(40, 520),
                        new LineTo(700, 520)
                )
                .build();
        grass.setFill(Color.BLACK);
        grass.setStrokeWidth(4);
        grass.setId("grass");
        gameGrid.getChildren().addAll(grass);

        myQueue = new LinkedBlockingQueue(200);
        double intervalRate = getIntervalRate();

        delayTimeline = new Timeline();
        delayTimeline.setCycleCount(Timeline.INDEFINITE);
        delayTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(intervalRate), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            Bubble aBubble = new Bubble(gameGrid, thisGame);

                            myQueue.put(aBubble);
                            bubblesCount += 1;
                            bbCount2show.setText("All: " + bubblesCount);
                            for (Bubble b : myQueue) {

                                //System.out.print(b.getTheChar() + " ");
                                if (b.getTheChar().isEmpty()) {

                                    myQueue.remove(b);
                                    //System.out.println(myQueue.size());
                                }

                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Gaming.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                })
        );
        delayTimeline.play();

        gameGrid.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {
                gameGrid.requestFocus();
                me.consume();
            }
        });
        gameGrid.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent ke) {
                //createLetter(ke.getText());
                for (Bubble b : myQueue) {
                    if (b.getTheChar().isEmpty()) {
                        myQueue.remove(b);
                    } else if (b.getTheChar().equalsIgnoreCase(ke.getCode().toString())) {
                        b.gotHit();
                        hitCount += 1;

                        hitNum2show.setText("Hit: " + hitCount);
                        myQueue.remove(b);
                    }
                }
            }
        });

    }

    public void play() {
        delayTimeline.play();
        //timeline1.play();
        for (Bubble b : myQueue) {
            if (b.getTheChar().isEmpty()) {
                myQueue.remove(b);
            } else {
                b.getTimeline().play();
            }
        }
    }

    @Override
    public void stop() {
        //timeline1.stop();
        for (Bubble b : myQueue) {
            if (b.getTheChar().isEmpty()) {
                myQueue.remove(b);
            } else {
                b.getTimeline().stop();
            }
        }
        delayTimeline.stop();
    }

    public void pauseAll() {
        //timeline1.pause();
        delayTimeline.pause();
        for (Bubble b : myQueue) {
            if (b.getTheChar().isEmpty()) {
                myQueue.remove(b);
            } else {
                b.getTimeline().pause();
            }
        }
    }

    private double getIntervalRate() {
        double compute = (-0.5) * getLevelDifficulty() + 2.6;
        return compute;

    }

    public static int getInitLifeCount() {
        return initLifeCount;
    }
    
    public void setInitLifeCount(int lifeCount){
        this.initLifeCount=lifeCount;
    }

    public void noLife() {
        stop();
        int scoreWeight = (getLevelDifficulty() - 1) * 5;
        if (scoreWeight == 0) {
            scoreWeight += 1;
        }
        int score = hitCount * scoreWeight;
        GameFinishedHint gfh = new GameFinishedHint();
        gfh.setScore(score);
        try {
            gfh.start(new Stage());
        } catch (Exception ex) {
            Logger.getLogger(Gaming.class.getName()).log(Level.SEVERE, null, ex);
        }
        initLifeCount=0;
        gameStage.close();
    }

    private int getLevelDifficulty() {
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
