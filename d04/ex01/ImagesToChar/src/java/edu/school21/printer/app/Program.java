package edu.school21.printer.app;

import edu.school21.printer.logic.Logic;

import java.io.IOException;
import java.net.URL;

public class Program {
    private static final int ARG_BLACK = 0;
    private static final int ARG_WHITE = 1;

    private static final String BMP_PATH = "resources/it.bmp";


    public static void main(String[] args) {


        if (args.length != 2) {
            System.out.println("Arguments error");
            System.out.println("Arguments should be "
                    + "[BLACK_SYMBOL]"
                    + " [WHITE_SYMBOL] "
            );
            System.exit(-1);
        }
        char black = args[ARG_BLACK].charAt(0);
        char white = args[ARG_WHITE].charAt(0);

        try {
            char[][] output = Logic.readBMPImage(white, black);
            for (char[] line : output) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}