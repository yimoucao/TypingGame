/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typingpracticegame;

/**
 *
 * @author Simon
 */
public class ScoreRecord implements Comparable {

    private String userName;
    private Integer score;

    public ScoreRecord(String userName, Integer score) {
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
        return this.score.compareTo(((ScoreRecord) o).getScore());
    }

    @Override
    public String toString() {
        return userName + " : " + score;
    }
    
    

}
