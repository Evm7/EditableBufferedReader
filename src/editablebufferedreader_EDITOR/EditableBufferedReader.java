/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editablebufferedreader_EDITOR;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Evm7
 */
public class EditableBufferedReader extends BufferedReader {

    /**
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
            cols = reader.readLine();
        } catch (IOException ex) {
            System.out.println("Error introducing Raw mode");
        }
        return cols;
    }

    public String getNumFils() {
        List<String> comm = Arrays.asList("/bin/sh", "-c", "tput lines 2> /dev/tty");
        ProcessBuilder p = new ProcessBuilder(comm);
        String cols = null;
        try {
            Process pr = p.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            cols = reader.readLine();
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
        ProcessBuilder p = new ProcessBuilder(comm);
        try {
            p.start();
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
        int lect;
        try {
            if ((lect = super.read()) != Key.ESC) {
                return lect;
            }
            lect = super.read();
            if (lect == Key.CLAU) {
                lect = super.read();
                return lect - 1000;
            }
        } catch (IOException ex) {
            System.out.println("Interrupted Exception");
        }
        return Key.CARAC;
    }

    @Override
    public String readLine() throws IOException {
        MultiLine lines = new MultiLine(Integer.parseInt(this.getNumCols()), Integer.parseInt(this.getNumFils()));
        Console console = new Console(lines);
        lines.addObserver(console);
        this.setRaw();
        int lect = -1;
        try {
            while (lect != Key.CRTL_C) {
                lect = this.read();
                switch (lect) {
                    case (Key.UP - 1000):
                        lines.moveUp();
                        break;
                    case (Key.DOWN - 1000):
                        lines.moveDown();
                        break;
                    case (Key.RIGHT - 1000):
                        lines.moveRight();
                        break;
                    case (Key.LEFT - 1000):
                        lines.moveLeft();
                        break;
                    case (Key.INSERT - 1000):
                        //to flush Buffer
                        this.read();
                        lines.setMode();
                        break;
                    case (Key.SUPR - 1000):
                        //to flush Buffer
                        this.read();
                        lines.suprimirChar();
                        break;
                    case (Key.HOME - 1000):
                        lines.moveHome();
                        break;
                    case (Key.END - 1000):
                        lines.moveEnd();
                        break;
                    case Key.EXIT:
                        lines.newLine();
                        break;
                    case Key.CARAC:
                        console.clear();
                        System.out.println("Error while entering code");
                        this.unsetRaw();
                        return "ERROR";
                    case Key.CRTL_C:
                        break;
                    case Key.DEL:
                        lines.deleteChar();
                        break;
                    default:
                        lines.addChar((char) lect);
                        break;
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Error: out of boundaries");
        }
        this.unsetRaw();
        console.clear();
        return lines.toString();
    }
}
