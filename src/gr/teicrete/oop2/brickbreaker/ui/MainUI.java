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

import gr.teicrete.oop2.brickbreaker.system.AudioManager;
import gr.teicrete.oop2.brickbreaker.system.GameManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Στέλιος
 */
public class MainUI extends JFrame {
    
    private JPanel base;
    JButton logo;
    
    /**
     * Creates the frame and adds the board to it.
     */
    public MainUI() {
        //Sets the frame properties
        setTitle("Brick Breaker!");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        //Start playing background music.
        AudioManager.playMusic("background.wav");
        
        //Set a menu
        JMenuBar menu = new JMenuBar();
        JMenu music = new JMenu("Music");
        JMenuItem mute = new JMenuItem("Mute");
        
        JMenu game = new JMenu("Game");
        JMenuItem newgame = new JMenuItem("New Game");
        JMenuItem mainmenu = new JMenuItem("Main Menu");
        
        game.add(mainmenu);
        game.add(newgame);
        menu.add(game);
        music.add(mute);
        menu.add(music);
        
        setJMenuBar(menu);
        
        mute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem button = (JMenuItem) e.getSource();
                if(button.getText().equals("Mute")){
                    button.setText("Unmute");
                    AudioManager.stopMusic();
                }
                else{
                    button.setText("Mute");
                    AudioManager.startMusic();
                }
            }
        });
        
        newgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem button = (JMenuItem) e.getSource();
                base.removeAll();
                base.setLayout(new BorderLayout());
                base.setBackground(Color.WHITE);
                
                GameManager.level = 1;
                
                //Creates and adds the board
                base.add(new Board(), BorderLayout.CENTER);

                //Creates and adds a label to show the score
                base.add(new Score(), BorderLayout.NORTH);
                
                repaint();
                revalidate();
            }
        });
        
        mainmenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem button = (JMenuItem) e.getSource();
                base.removeAll();
                base.setLayout(new BorderLayout());
                base.setBackground(Color.WHITE);
                
                GameManager.level = 1;
                
                //Adds the logo
                base.add(logo);
                
                repaint();
                revalidate();
            }
        });
        
        //Sets the panel that the game is going to be added on.
        base = new JPanel();
        base.setLayout(new BorderLayout());
        base.setBackground(Color.WHITE);
        
        //Add the menu image
        logo = new JButton(new ImageIcon(this.getClass().getResource("../icons/brickbreaker_logo.png")));
        logo.setOpaque(false);
        logo.setContentAreaFilled(false);
        logo.setBorderPainted(false);
        logo.setFocusPainted(false);
        base.add(logo);
        
        //Adds the base panel to the frame.
        add(base);
        
        setVisible(true);
    }
}
