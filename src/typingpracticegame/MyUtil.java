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

/**
 *
 * @author Simon
 */
public class MyUtil {

    private static final String myFontStr = TypingPracticeGame.class.getResource("assets/senty.ttf").toExternalForm();

    public static String getMyFontStr() {
        return myFontStr;
    }

}
