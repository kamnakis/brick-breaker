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
package gr.teicrete.oop2.brickbreaker.ui;

import gr.teicrete.oop2.brickbreaker.ui.screens.LostScreen;
import gr.teicrete.oop2.brickbreaker.ui.screens.WonScreen;
import gr.teicrete.oop2.brickbreaker.ui.bricks.ColorBombBrick;
import gr.teicrete.oop2.brickbreaker.ui.bricks.Brick;
import gr.teicrete.oop2.brickbreaker.ui.bricks.BombBrick;
import gr.teicrete.oop2.brickbreaker.system.GameManager;
import gr.teicrete.oop2.brickbreaker.ui.bricks.NewLineBrick;
import gr.teicrete.oop2.brickbreaker.ui.bricks.SuffleBrick;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Στέλιος
 */
public class Board extends JPanel {
    
    private final int ROWS;
    private final int COLUMNS;
    
    private JPanel[][] board;
    
    private ArrayList<Integer> specialBricks = new ArrayList<Integer>();
    private int randomIndex;
    
    private Color[] colors = {
        Color.BLUE,
        Color.GREEN,
        Color.MAGENTA,
        Color.YELLOW,
        Color.PINK,
        Color.CYAN,
        Color.ORANGE,
        Color.BLACK,
        Color.RED
    };
    
    private ArrayList<Color> brickColors = new ArrayList<Color>();

    private int level;
    private int totalBricks;
    private Random randomNumber;
    
    /**
     * Creates the board and the bricks
     */
    public Board() {
        //Init variables
        level = GameManager.level;
        ROWS = 12 + (level / 2);
        COLUMNS = 14 + (level - 1)/2;
        board = new JPanel[ROWS][COLUMNS];
        totalBricks = ROWS * COLUMNS;
        
        //Init colors
        if(level <= (2*(colors.length-4))){
            for(int i = 0; i < (4 + (int)((level - 1)/2)); i++){
                brickColors.add(colors[i]);
            }
        }  
        else{
            for(int i = 0; i < colors.length; i++){
                brickColors.add(colors[i]);
            }
        }
        
        //Sets the level properties.
        setBackground(Color.WHITE);
        setLayout(new GridLayout(ROWS, COLUMNS));
        
        //Get the random index for the special bricks.
        for(int i = 0; i <= ((ROWS * COLUMNS) * 5 / 100); i++){
            do{
                randomNumber = new Random();
                randomIndex = randomNumber.nextInt(ROWS*COLUMNS);
            }while(specialBricks.contains(randomIndex));
            
            specialBricks.add(randomIndex);
        }
        
        //Create and add the bricks
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                //Create the instances of the panels and set their details
                board[i][j] = new JPanel();
                board[i][j].setLayout(new GridLayout(1, 1));
                board[i][j].setOpaque(false);
                
                //Get a random number from 0 to the length of the brickColors array
                //list and create the instances of the buttons with random colors.
                if(specialBricks.contains(i*COLUMNS + j)){
                    randomNumber = new Random();
                    switch(randomNumber.nextInt(4)){
                        case 0:board[i][j].add(new BombBrick(Color.WHITE));break;
                        case 1:
                            randomNumber = new Random();
                            board[i][j].add(new ColorBombBrick(brickColors.get(randomNumber.nextInt(brickColors.size()))));
                            break;
                        case 2:board[i][j].add(new NewLineBrick(Color.WHITE));break;
                        case 3:board[i][j].add(new SuffleBrick(Color.WHITE));break;
                    }
                    
                }
                else{
                    randomNumber = new Random();
                    board[i][j].add(new Brick(brickColors.get(randomNumber.nextInt(brickColors.size()))));
                }
                add(board[i][j]);
            }
        }
        
        setNeighbors();
    }
    
    /**
    * This method is called when a from the brick when it gets destroyed
    */
    public void brickDestroyedAction(){
        repaint();
        revalidate();
        
        //Move the bricks down and left when needed.
        while (hasUnwantedEmpties()) {
            moveDown();
            setNeighbors();
        }
        for (int i = 0; i < getEmptyColumns(); i++) {
            moveLeft();
            setNeighbors();
        }
        
        //Check if the player lost/won and do the appropriate action.
        if(hasWon() || hasLost()){
            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    loadScreen();
                }
            },
            1200
            );
        }
        
        //Set the score
        scoreUpdate();
    }
    
    /**
     * Updates the scores.
     */
    public void scoreUpdate(){
        //Update the score
        int bricksDestroyed = totalBricks - countBricks();
        
        if(bricksDestroyed <= 4){
            GameManager.getGameManager().addToCurrentScore(bricksDestroyed);
        }
        else if(bricksDestroyed > 4 && bricksDestroyed <= 12){
            GameManager.getGameManager().addToCurrentScore((int)(1.5* bricksDestroyed));
        }
        else if(bricksDestroyed > 12){
            GameManager.getGameManager().addToCurrentScore(2* bricksDestroyed);
        }
        
        totalBricks -= bricksDestroyed;
    }
    
    /**
     * Counts the non-destroyed bricks
     * @return
     */
    public int countBricks(){
        int counter = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if(board[i][j].getComponentCount() != 0){
                    counter++;
                }
            }
        }
        
        return counter;
    }

    /**
     * Sets the neighbors of each brick.
     */
    public void setNeighbors() {
        Brick brick;
        
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j].getComponentCount() != 0) {
                    brick = (Brick) board[i][j].getComponent(0);
                    if (i == 0 || board[i - 1][j].getComponentCount() == 0) {
                        brick.setUp(null);
                    } else {
                        brick.setUp((Brick) board[i - 1][j].getComponent(0));
                    }

                    if (i == ROWS - 1 || board[i + 1][j].getComponentCount() == 0) {
                        brick.setDown(null);
                    } else {
                        brick.setDown((Brick) board[i + 1][j].getComponent(0));
                    }

                    if (j == 0 || board[i][j - 1].getComponentCount() == 0) {
                        brick.setLeft(null);
                    } else {
                        brick.setLeft((Brick) board[i][j - 1].getComponent(0));
                    }

                    if (j == COLUMNS - 1 || board[i][j + 1].getComponentCount() == 0) {
                        brick.setRight(null);
                    } else {
                        brick.setRight((Brick) board[i][j + 1].getComponent(0));
                    }
                }
            }
        }
    }

    /**
     * Checks if every brick has another one down of it.
     * Returns true if it does, returns false otherwise.
     * @return
     */
    public boolean hasUnwantedEmpties() {
        boolean flag = false;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (i != ROWS - 1 && board[i][j].getComponentCount() != 0 
                        && board[i + 1][j].getComponentCount() == 0) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * Returns the index of the first empty column.
     * @return
     */
    public int getFirstEmptyColumn() {
        for (int j = 0; j < COLUMNS; j++) {
            if (board[ROWS - 1][j].getComponentCount() == 0) {
                return j;
            }
        }
        return -1;
    }

    /**
     * Returns the amount of empty columns
     * @return
     */
    public int getEmptyColumns() {
        int emptyColumns = 0;
        for (int j = 0; j < COLUMNS; j++) {
            if (board[ROWS - 1][j].getComponentCount() == 0) {
                emptyColumns++;
            }
        }
        
        return emptyColumns;
    }

    /**
     * Moves every brick that has nothing down of it one position down.
     */
    public void moveDown() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (i != ROWS - 1 && board[i][j].getComponentCount() != 0 && board[i + 1][j].getComponentCount() == 0) {
                    board[i + 1][j].add(board[i][j].getComponent(0));
                    repaint();
                    revalidate();
                }
            }
        }
    }

    /**
     * Moves every brick from the first empty column and to the right
     * one postion to the left.
     */
    public void moveLeft() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = getFirstEmptyColumn(); j < COLUMNS; j++) {
                if (j != 0 && board[i][j].getComponentCount() != 0 && board[i][j - 1].getComponentCount() == 0) {
                    board[i][j - 1].add(board[i][j].getComponent(0));
                    repaint();
                    revalidate();
                }
            }
        }
    }

    /**
     * Checks if there is any brick on the board
     * If there aren't any it shows the win screen.
     * @return 
     */
    public boolean hasWon() {
        boolean hasWon = true;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j].getComponentCount() != 0) {
                    hasWon = false;
                    break;
                }
            }
        }
        
        return hasWon;
    }

    /**
     * Checks if the only bricks that are on the board have no relative neighbors.
     * @return 
     */
    public boolean hasLost() {
        boolean hasLost = true;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j].getComponentCount() != 0) {
                    Brick brick = (Brick) board[i][j].getComponent(0);
                    if (brick.canBePressed()) {
                        hasLost = false;
                        break;
                    }
                }
            }
        }
        
        return hasLost;
    }
    
    /**
    * Removes the board and adds the suitable screen.
    */
    public void loadScreen(){
        JPanel base = (JPanel) getParent();
        base.removeAll();
        base.setLayout(new BorderLayout());

        if(GameManager.currentScore >= GameManager.getGameManager().calculateScoreRequired()){
            base.add(new WonScreen());
        }
        else{
            base.add(new LostScreen());
        }

        base.repaint();
        base.revalidate();
    }
    
    /**
     *  Returns true if the first line of the board is empty
     * @return
     */
    public boolean checkFirstLine(){
        boolean isEmpty = true;
        
        for(int i = 0; i < COLUMNS; i++){
            if(board[0][i].getComponentCount() != 0){
                isEmpty = false;
            }
        }
        
        return isEmpty;
    }

    /**
     *  Fills the first line of the board with simple bricks
     */
    public void fillFirstLine() {
        if(checkFirstLine()){
            for(int i = 0; i < COLUMNS; i++){
                randomNumber = new Random();
                board[0][i].add(new Brick(brickColors.get(randomNumber.nextInt(brickColors.size()))));
            }
        }
    }

    /**
     *  Returns the array list with the colors supported on the specific level
     * @return
     */
    public ArrayList<Color> getBrickColors() {
        return brickColors;
    }
}
