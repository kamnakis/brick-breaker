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

import gr.teicrete.oop2.brickbreaker.ui.Board;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 *
 * @author Στέλιος
 */
public class Brick extends JButton implements ActionListener{

    private Brick up, down, left, right;
    private boolean destroyed = false;

    /**
     * Sets the background of the brick to the color of the parameter.
     * @param color
     */
    public Brick(Color color) {
        setBackground(color);
        addActionListener(this);
    }
    
    /**
    * This is called when the button (brick) is pressed
    * @param e
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (hasRelativeNeighbors()) {
            Board board = (Board) getParent().getParent();
            destroy();
            board.brickDestroyedAction();
        }
    }

    /**
     * Removes the brick from the panel it is on.
     */
    public void destroy() {
        JPanel panel = (JPanel) getParent();
        panel.remove(0);
        destroyed = true;

        if (getUp() != null && getUp().getBackground().equals(getBackground()) && !getUp().isDestroyed()) {
            getUp().destroy();
        }
        if (getDown() != null && getDown().getBackground().equals(getBackground()) && !getDown().isDestroyed()) {
            getDown().destroy();
        }
        if (getLeft() != null && getLeft().getBackground().equals(getBackground()) && !getLeft().isDestroyed()) {
            getLeft().destroy();
        }
        if (getRight() != null && getRight().getBackground().equals(getBackground()) && !getRight().isDestroyed()) {
            getRight().destroy();
        }
    }

    /**
     * Returns true if any of the neighbors has the same color as this brick.
     * @return
     */
    public boolean hasRelativeNeighbors() {
        if (up != null && up.getBackground().equals(getBackground())
                || (down != null && down.getBackground().equals(getBackground()))
                || (left != null && left.getBackground().equals(getBackground()))
                || (right != null && right.getBackground().equals(getBackground()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if the brick is destroyed
     * @return
     */
    public boolean isDestroyed() {
        return destroyed;
    }
    
    /**
     * Sets the destroyed boolean
     * @param destroyed
     */
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
    

    /**
     * Returns the brick above this one.
     * @return
     */
    public Brick getUp() {
        return up;
    }

    /**
     * Sets the brick above this one.
     * @param up
     */
    public void setUp(Brick up) {
        this.up = up;
    }

    /**
     * Returns the brick down of this one.
     * @return
     */
    public Brick getDown() {
        return down;
    }

    /**
     * Sets the brick down of this one.
     * @param down
     */
    public void setDown(Brick down) {
        this.down = down;
    }

    /**
     * Returns the brick on the left left to this.
     * @return
     */
    public Brick getLeft() {
        return left;
    }

    /**
     * Sets the brick on the left to this.
     * @param left
     */
    public void setLeft(Brick left) {
        this.left = left;
    }

    /**
     * Returns the brick on the right to this.
     * @return
     */
    public Brick getRight() {
        return right;
    }

    /**
     * Sets the brick on the right to this.
     * @param right
     */
    public void setRight(Brick right) {
        this.right = right;
    }
    
    /*
    *   Returns true if the brick is terminal
    */

    /**
     *
     * @return
     */

    public boolean canBePressed(){
        if(hasRelativeNeighbors())
            return true;
        return false;
    }
}
