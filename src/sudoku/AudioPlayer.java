package sudoku;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import javax.sound.sampled.*;
import java.time.Duration;

public class AudioPlayer {
    private static Clip clip;

    public static void playSound(String soundFile) {//backsound properti kena ular,tangga,dll
        try {
            InputStream audioLink = AudioPlayer.class.getResourceAsStream("/" + soundFile);
            AudioInputStream audio = AudioSystem.getAudioInputStream(audioLink);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
            Thread.sleep(Duration.ofMillis(1000));//durasi suara bisa diubah
            clip.stop();
            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playbackSound(String soundFile) {//backsound permainan sampe selesai
        try {
            InputStream audioLink = AudioPlayer.class.getResourceAsStream("/" + soundFile);
            AudioInputStream audio = AudioSystem.getAudioInputStream(audioLink);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void playSound2(String soundFile) {//backsound properti kena ular,tangga,dll
        try {
            InputStream audioLink = AudioPlayer.class.getResourceAsStream("/" + soundFile);
            AudioInputStream audio = AudioSystem.getAudioInputStream(audioLink);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
            Thread.sleep(Duration.ofMillis(5000));//durasi suara bisa diubah
            clip.stop();
            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
