package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Logic {

    public static char[][] readBMPImage(String path, char white, char black) throws IOException {

        BufferedImage image = ImageIO.read(new FileInputStream(path));
        char[][] arr = new char[image.getHeight()][image.getWidth()];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int color = image.getRGB(x, y);
                if (color == Color.black.getRGB()) {
                    arr[y][x] = black;
                } else {
                    arr[y][x] = white;
                }
            }
        }
        return arr;
    }
}
