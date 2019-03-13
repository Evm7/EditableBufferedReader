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
    public final static int O = 79;
    public final static int HOME = 72;
    public final static int END = 70;

    /**
     * UP 27,91,91,65 DOWN 27,91,91,66 RIGHT 27.91,91,66 LEFT 27,91,91,68 INSERT
     * 27,91,91,50,126 SUPR 27,91,91,51,126 HOME 27,91,79,72 END 27,91,79,70
     *
     * /
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
        List<String> comm = Arrays.asList("bash", "-c", "tput cols > /dev/tty");
        // String[] comm1={"bash", "-c" ,"tput cols 2 > /dev/tty"};
        ProcessBuilder p = new ProcessBuilder(comm);
        p.redirectErrorStream(true);

        String cols = null;
        String result = null;
        try {
            int i = 0;
            Process pr = p.start();
            //Process pr = Runtime.getRuntime().exec(comm1);
            BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            StringBuilder builder = new StringBuilder();
            while ((cols = reader.readLine()) != null) {
                builder.append(cols);
                builder.append(System.getProperty("line.separator"));
            }
            result = builder.toString();
        } catch (IOException ex) {
            System.out.println("Error introducing Raw mode");
        }
        return result;

    }

    public void setRaw() {
        List<String> comm = Arrays.asList("/bin/sh", "-c", "stty -echo raw </dev/tty");
        // String[] comm1={"/bin/sh", "-c" ,"stty raw -echo <//dev/tty"};
        ProcessBuilder p = new ProcessBuilder(comm);
        try {
            p.start();
            // Runtime.getRuntime().exec(comm1).waitFor();
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
        int lect = -1;
        try {
            lect = super.read();
            if (lect == EditableBufferedReader.ESC) {
                lect = super.read();

                if (lect == EditableBufferedReader.CLAU) {
                    lect = super.read();
                    switch (lect) {
                        case UP:
                            return 1000;
                        case DOWN:
                            return 1001;
                        case RIGHT:
                            return 1002;
                        case LEFT:
                            return 1003;
                        case INSERT:
                            return 1004;
                        case SUPR:
                            return 1005;
                        case HOME:
                            return 1006;
                        case END:
                            return 1007;
                        default:
                            return 1010;
                    }
                } else {
                    return 1010;
                }
            } else if (lect == EditableBufferedReader.EXIT) {
                return 1009;
            } else if (lect == CRTL_C) {
                return 1009;
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
        Line line = new Line(new Console());
        this.setRaw();
        Boolean loop = Boolean.TRUE;
        int lect = -1;
        while (lect != 1009) {
            lect = this.read();
            switch (lect) {
                case 1000:
                    line.moveUp();
                    break;
                case 1001:
                    line.moveDown();
                    break;
                case 1002:
                    line.moveRight();
                    break;
                case 1003:
                    line.moveLeft();
                    break;
                case 1004:
                    line.setMode();
                    break;
                case 1005:
                    line.suprimirChar();
                    break;
                case 1006:
                    line.moveHome();
                    break;
                case 1007:
                    line.moveEnd();
                    break;
                case 1009:
                    System.out.println("\nBye bye. Have a nice day!");
                    break;
                case 1010:
                    System.out.println("Error while entering code");
                    this.unsetRaw();
                    return "ERROR";
                case 127:
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
