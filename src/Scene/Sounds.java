/******
 * Student name: Doreen Vaserman
 * Student ID: 308223627
 * Student name: Nadav Spitzer
 * Student ID: 302228275
 */
package Scene;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Sounds {

    public static void makeSound(String path) {
        play(path);
    }

    public static void makeLoopSound(String path) {
        AudioClip clip = play(path);
        clip.loop();
    }

    private static AudioClip play(String path) {
        File file = new File(path);
        URL url = null;
        if (file.canRead()) {
            try {
                url = file.toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        AudioClip clip = Applet.newAudioClip(url);
        clip.play();
        return clip;
    }
}
