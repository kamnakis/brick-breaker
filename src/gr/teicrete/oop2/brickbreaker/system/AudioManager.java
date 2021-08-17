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

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Στέλιος
 */
public class AudioManager {
    
    private static Clip bgClip;
    
    private static AudioManager audioManager;
    
    private AudioManager() {

    }

    /**
     * Return an instance of the game manager.
     *
     * @return
     */
    public static AudioManager getAudioManager() {
        if(audioManager == null){
            audioManager = new AudioManager();
        }
        return audioManager;
    }
    
    /**
     * Plays the sound of the fileName located in the sounds package.
     *
     * @param fileName
     */
    public static synchronized void playSound(final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("../sounds/" + fileName));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }    
    
    /**
     * Plays the sound of the fileName located in the sounds package continiously.
     *
     * @param fileName
     */
    public static synchronized void playMusic(final String fileName) {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("./src/gr/teicrete/oop2/brickbreaker/sounds/" + fileName));
            bgClip = AudioSystem.getClip();
            bgClip.open(inputStream);
            bgClip.loop(Clip.LOOP_CONTINUOUSLY);
            Thread.sleep(100); // looping as long as this thread is alive
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Stops the background music
     */
    public static synchronized void stopMusic(){
        bgClip.stop();
    }
    
    /**
     * Starts the background music
     */
    public static synchronized void startMusic(){
        bgClip.start();
    }
}
