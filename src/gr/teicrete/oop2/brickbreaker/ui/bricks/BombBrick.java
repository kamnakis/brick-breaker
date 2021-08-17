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
package gr.teicrete.oop2.brickbreaker.ui.bricks;

import gr.teicrete.oop2.brickbreaker.system.AudioManager;
import gr.teicrete.oop2.brickbreaker.ui.Board;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Στέλιος
 */
public class BombBrick extends Brick{
    
    private boolean destroyed = false;
    private Image image;
    private Rectangle innerArea;
    
    /**
     * Sets the background of the brick to the color of the parameter.
     * @param color
     */
    public BombBrick(Color color) {
        super(color);
        image = (new ImageIcon(this.getClass().getResource("../../icons/bomb.png"))).getImage();
        innerArea = new Rectangle();
    }
    
    /**
     * Paints the image of the brick.
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        if(image != null){
            SwingUtilities.calculateInnerArea(this, innerArea);
            g.drawImage(image, innerArea.x, innerArea.y, innerArea.width, innerArea.height, this);
        }
    }
    
    /**
    * This is called when the button (brick) is pressed
    * @param e
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        Board board = (Board) getParent().getParent();
        bombDestroy();
        board.brickDestroyedAction();
    }
    
    /**
     * Removes the brick from the panel it is on.
     * It also destroys all the neighbor bricks.
     */
    public void bombDestroy(){
        JPanel panel = (JPanel) getParent();
        panel.remove(0);
        destroyed = true;
        AudioManager.playSound("bomb_sound.wav");

        if (getUp() != null && !getUp().isDestroyed()) {
            getUp().setDestroyed(true);
            getUp().getParent().remove(0);
        }
        if (getLeft() != null && !getLeft().isDestroyed()) {
            getLeft().setDestroyed(true);
            getLeft().getParent().remove(0);
            if (getLeft().getUp() != null && !getLeft().getUp().isDestroyed()) {
                getLeft().getUp().setDestroyed(true);
                getLeft().getUp().getParent().remove(0);
            }
            if (getLeft().getDown() != null && !getLeft().getDown().isDestroyed()) {
                getLeft().getDown().setDestroyed(true);
                getLeft().getDown().getParent().remove(0);
            }
        }
        if (getDown() != null && !getDown().isDestroyed()) {
            getDown().setDestroyed(true);
            getDown().getParent().remove(0);
            if (getDown().getLeft() != null && !getDown().getLeft().isDestroyed()) {
                getDown().getLeft().setDestroyed(true);
                getDown().getLeft().getParent().remove(0);
            }
            if (getDown().getRight() != null && !getDown().getRight().isDestroyed()) {
                getDown().getRight().setDestroyed(true);
                getDown().getRight().getParent().remove(0);
            }
        }
        if (getRight() != null && !getRight().isDestroyed()) {
            getRight().setDestroyed(true);
            getRight().getParent().remove(0);
            if (getRight().getUp() != null && !getRight().getUp().isDestroyed()) {
                getRight().getUp().setDestroyed(true);
                getRight().getUp().getParent().remove(0);
            }
            if (getRight().getDown() != null && !getRight().getDown().isDestroyed()) {
                getRight().getDown().setDestroyed(true);
                getRight().getDown().getParent().remove(0);
            }
        }
    }
    
    /*
    *   Returns true if the brick is terminal
    */

    /**
     *
     * @return
     */

    @Override
    public boolean canBePressed(){
        return true;
    }
}
