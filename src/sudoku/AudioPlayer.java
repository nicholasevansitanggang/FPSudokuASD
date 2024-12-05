package sudoku;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import javax.sound.sampled.*;
import java.time.Duration;

public class AudioPlayer {
    private static Clip clip;

    // Method untuk memutar suara dengan durasi pendek
    public static void playSound(String soundFile) {
        try {
            InputStream audioLink = AudioPlayer.class.getResourceAsStream("/" + soundFile);
            AudioInputStream audio = AudioSystem.getAudioInputStream(audioLink);
            clip = AudioSystem.getClip();  // Simpan clip yang sedang diputar
            clip.open(audio);
            clip.start();
            Thread.sleep(Duration.ofMillis(1000).toMillis());  // Durasi suara bisa diubah
            clip.stop();
            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method untuk memutar suara yang berulang terus menerus
    public static void playbackSound(String soundFile) {
        try {
            InputStream audioLink = AudioPlayer.class.getResourceAsStream("/" + soundFile);
            AudioInputStream audio = AudioSystem.getAudioInputStream(audioLink);
            clip = AudioSystem.getClip();  // Simpan clip yang sedang diputar
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // Suara berulang terus menerus
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method untuk memutar suara dengan durasi tertentu (contoh 5 detik)
    public static void playSound2(String soundFile) {
        try {
            InputStream audioLink = AudioPlayer.class.getResourceAsStream("/" + soundFile);
            AudioInputStream audio = AudioSystem.getAudioInputStream(audioLink);
            clip = AudioSystem.getClip();  // Simpan clip yang sedang diputar
            clip.open(audio);
            clip.start();
            Thread.sleep(Duration.ofMillis(5000).toMillis());  // Durasi suara bisa diubah
            clip.stop();
            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method untuk menghentikan suara yang sedang diputar
    public static void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
