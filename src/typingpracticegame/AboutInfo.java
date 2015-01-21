/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typingpracticegame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
public class AboutInfo extends Application {

    @Override
    public void start(Stage aboutStage) throws Exception {
        init(aboutStage);
        aboutStage.show();
    }

    private void init(Stage aboutStage) {
        aboutStage.initStyle(StageStyle.TRANSPARENT);
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getStyleClass().add("about_vbox");

        Scene scene = new Scene(vbox, 331, 500, Color.TRANSPARENT);
        scene.getStylesheets().add(TypingPracticeGame.class.getResource("options.css").toExternalForm());

        aboutStage.setScene(scene);
        aboutStage.centerOnScreen();
        aboutStage.show();

        Text aboutTitle = new Text("About");
        aboutTitle.setFont(Font.loadFont(MyUtil.getMyFontStr(), 70));
        
        Text gameName = new Text("Enjoy your typing time!");
        gameName.setFont(Font.loadFont(MyUtil.getMyFontStr(), 30));
        Text bySimon = new Text("By Simon");
        bySimon.setFont(Font.loadFont(MyUtil.getMyFontStr(), 30));
        Text email = new Text("ctimeprints@gmail.com");
        email.setFont(Font.loadFont(MyUtil.getMyFontStr(), 30));

        Button toMain = new Button("Return");
        toMain.setOnAction((ActionEvent event) -> {
            //in case we would like to close whole demo
            //javafx.application.Platform.exit();
            //however we want to close only this instance of stage
            aboutStage.close();

        });

        vbox.getChildren().addAll(aboutTitle, gameName, bySimon, email, toMain);
    }

}
