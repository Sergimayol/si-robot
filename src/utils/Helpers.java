package utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Helpers {
    private Helpers() {
        throw new IllegalStateException("Utility class");
    }

    public static BufferedImage readImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (Exception e) {
            throw new RuntimeException("Error reading image: " + path);
        }
    }

    public static ImageIcon escalateImageIcon(String iconPath, int width, int height) {
        Image image = new ImageIcon(iconPath).getImage();
        return new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }
}
