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
            Thread.sleep(Duration.ofMillis(200).toMillis());  // Durasi suara bisa diubah
            clip.stop();
            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playbackSound(String soundFile, float volume) {
        try {
            InputStream audioLink = AudioPlayer.class.getResourceAsStream("/" + soundFile);
            AudioInputStream audio = AudioSystem.getAudioInputStream(audioLink);

            clip = AudioSystem.getClip();
            clip.open(audio);

            // Set volume
            setVolume(volume);

            // Play the sound continuously
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to set the volume level
    private static void setVolume(float volume) {
        if (clip != null) {
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                // Get the current volume range
                float minGain = volumeControl.getMinimum();
                float maxGain = volumeControl.getMaximum();

                // Scale the volume value
                float volumeValue = minGain + (maxGain - minGain) * volume;
                volumeControl.setValue(volumeValue);
            }
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
            Thread.sleep(Duration.ofMillis(700).toMillis());  // Durasi suara bisa diubah
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
