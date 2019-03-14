/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editablebufferedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author virtual
 */
public class EditableBufferedReader extends BufferedReader {

    /**
     */
    public final static int CRTL_C = 3;
    public final static int EXIT = 13;
    public final static int ESC = 27;
    public final static int CLAU = 91;
    public final static int UP = 65;
    public final static int DOWN = 66;
    public final static int RIGHT = 67;
    public final static int LEFT = 68;
    public final static int INSERT = 50;
    public final static int WAVE = 126;
    public final static int SUPR = 51;
    public final static int HOME = 72;
    public final static int END = 70;
    public final static int DEL = 127;

    //KEYS
    public final static int EXIT_KEY = 1009;
    public final static int CARAC = 1010;

    /*
     *
     * @param args
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

    public EditableBufferedReader(Reader in) {
        super(in);
    }

    public String getNumCols() {
        List<String> comm = Arrays.asList("/bin/sh", "-c", "tput cols 2> /dev/tty");
        ProcessBuilder p = new ProcessBuilder(comm);
        String cols = null;
        try {
            Process pr = p.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            cols =reader.readLine();
        } catch (IOException ex) {
            System.out.println("Error introducing Raw mode");
        }
        return cols;

    }

    public void setRaw() {
        List<String> comm = Arrays.asList("/bin/sh", "-c", "stty -echo raw </dev/tty");
        ProcessBuilder p = new ProcessBuilder(comm);
        try {
            p.start();
        } catch (IOException ex) {
            System.out.println("Error introducing Raw mode");
        }
    }

    public void unsetRaw() {
        List<String> comm = Arrays.asList("/bin/sh", "-c", "stty echo cooked </dev/tty");
        // String[] comm1={"/bin/sh", "-c" ,"stty -echo raw <//dev/tty"};
        ProcessBuilder p = new ProcessBuilder(comm);
        try {
            p.start();
            // Runtime.getRuntime().exec(comm1).waitFor();
        } catch (IOException ex) {
            System.out.println("Error introducing Raw mode");
        }
    }

    @Override
    public int read() {
        /**
         * A ESC sequencers normal parser. Reads char-by-char. Reads symbols as
         * well as characters. The symbols to be recognised are:
         *
         * UP 27,91,65 DOWN 27,91,66 RIGHT 27.91,66 LEFT 27,91,68 INSERT
         * 27,91,50,126 SUPR 27,91,51,126 HOME 27,91,79,72 END 27,91,79,70
         *
         * @return an int. Symbols must be assigned an int (called KEY in
         * definitions) which doesn't conflict with existing characters.
         *
         */
        int lect = -1;
        try {
            lect = super.read();
            if (lect == EditableBufferedReader.ESC) {
                lect = super.read();
                if (lect == EditableBufferedReader.CLAU) {
                    lect = super.read();
                    return lect - 1000;
                } else {
                    return CARAC;
                }
            } else if (lect == EditableBufferedReader.EXIT) {
                return EXIT_KEY;
            } else if (lect == CRTL_C) {
                return EXIT_KEY;
            } else {
                return lect;
            }
        } catch (IOException ex) {
            System.out.println("Interrupted Exception");
        }
        return lect;
    }

    @Override
    public String readLine() {
        Line line = new Line(new Console(), Integer.parseInt((this.getNumCols())));
        this.setRaw();
        Boolean loop = Boolean.TRUE;
        int lect = -1;
        while (lect != EXIT_KEY) {
            lect = this.read();
            switch (lect) {
                case (UP - 1000):
                    line.moveUp();
                    break;
                case (DOWN - 1000):
                    line.moveDown();
                    break;
                case (RIGHT - 1000):
                    line.moveRight();
                    break;
                case (LEFT - 1000):
                    line.moveLeft();
                    break;
                case (INSERT - 1000):
                    line.setMode();
                    break;
                case (SUPR - 1000):
                    line.suprimirChar();
                    break;
                case (HOME - 1000):
                    line.moveHome();
                    break;
                case (END - 1000):
                    line.moveEnd();
                    break;
                case EXIT_KEY:
                    System.out.println("\nBye bye. Have a nice day!");
                    break;
                case CARAC:
                    System.out.println("Error while entering code");
                    this.unsetRaw();
                    return "ERROR";
                case DEL:
                    line.deleteChar();
                    break;
                default:
                    line.addChar((char) lect);
            }
        }
        this.unsetRaw();
        return line.toString();
    }
}
