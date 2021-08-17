/*
 * The MIT License
 *
 * Copyright 2017 Στέλιος.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package gr.teicrete.oop2.brickbreaker.system;

import gr.teicrete.oop2.brickbreaker.interfaces.Observable;
import gr.teicrete.oop2.brickbreaker.interfaces.Observer;
import java.util.ArrayList;

/**
 *
 * @author Στέλιος
 */
public class GameManager implements Observable{

    /**
     * The current level, beggining with level 1
     */
    public static int level = 1;

    /**
     * The current total score
     */
    public static int totalScore = 0;

    /**
     * The current score.
     */
    public static int currentScore = 0;
    
    /**
     * The observers list.
     */
    private static ArrayList<Observer> observers = new ArrayList<>();

    private static GameManager gameManager;
    
    private GameManager() {
    }

    /**
     * Return an instance of the game manager.
     *
     * @return
     */
    public static GameManager getGameManager() {
        if(gameManager == null){
            gameManager = new GameManager();
        }
        return gameManager;
    }
    
    
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        for(int i = 0; i < observers.size(); i++){
            if(observers.get(i).equals(observer)){
                observers.remove(i);
            }
        }
    }

    @Override
    public void notifyObservers() {
        for(int i = 0; i < observers.size(); i++){
            observers.get(i).update();
        }
    }

    /**
     * Increases the level by 1
     */
    public void addLevel() {
        level++;
    }

    /**
     * Adds the current score to the total score.
     */
    public void addCurrentScoreToTotal() {
        totalScore += currentScore;
    }

    /**
     * Add the points to the current score
     *
     * @param points
     */
    public void addToCurrentScore(int points) {
        currentScore += points;
        notifyObservers();
    }

    /**
     * Calculates and returns the points required to pass the level.
     *
     * @return
     */
    public int calculateScoreRequired() {
        return 80 + (level * 20);
    }
}
