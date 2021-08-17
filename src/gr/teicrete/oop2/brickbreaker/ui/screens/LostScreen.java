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
package gr.teicrete.oop2.brickbreaker.ui.screens;

import gr.teicrete.oop2.brickbreaker.system.AudioManager;
import gr.teicrete.oop2.brickbreaker.system.GameManager;
import gr.teicrete.oop2.brickbreaker.ui.Board;
import gr.teicrete.oop2.brickbreaker.ui.Score;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Στέλιος
 */
public class LostScreen extends JPanel {

    /**
     * Creates the lost screen's properties.
     */
    public LostScreen() {
        //Play win sound
        AudioManager.playSound("lose_sound.wav");
        
        //Sets the screens properties
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        GameManager.getGameManager().addCurrentScoreToTotal();
        
        //Add the you_lost image
        JButton icon = new JButton(new ImageIcon(this.getClass().getResource("../../icons/you_lost.png")));
        icon.setOpaque(false);
        icon.setContentAreaFilled(false);
        icon.setBorderPainted(false);
        icon.setFocusPainted(false);
        add(icon, BorderLayout.NORTH);
        
        //Crate and add the score panel
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        scorePanel.setBackground(Color.WHITE);
        scorePanel.add(new JLabel("Total Score : "+GameManager.totalScore));
        scorePanel.add(new JLabel("Score : " + GameManager.currentScore));
        scorePanel.add(new JLabel(GameManager.getGameManager().calculateScoreRequired() +  " points required."));
        add(scorePanel, BorderLayout.CENTER);
        
        //Create and add the panel with the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton loadSameLevel = new JButton("Try Again!");
        loadSameLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFirstLevel();
            }
        });
        buttonPanel.add(loadSameLevel);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Loads the first level.
     */
    public void loadFirstLevel(){
        //Load first level.
        GameManager.level = 0;
        GameManager.totalScore = 0;
        GameManager.currentScore = 0;
        
        JPanel base = (JPanel) getParent();
        base.removeAll();
        base.setLayout(new BorderLayout());

        base.add(new Board(), BorderLayout.CENTER);
        base.add(new Score(), BorderLayout.NORTH);
        
        base.repaint();
        base.revalidate();
    }

}
