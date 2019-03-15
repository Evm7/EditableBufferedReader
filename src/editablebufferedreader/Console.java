/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editablebufferedreader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Evm7
 */
public class Console implements Observer {

    private MultiLine lines;

    public Console(MultiLine lines) {
        //Netejam Consola i ens situem en el principi de la consola.
        controlConsole("\033[0;0H \033[2J");
        this.lines = lines;
    }

    public void controlConsole(String comanda) {
        List<String> comm = Arrays.asList("bash", "-c", "echo -en '" + comanda + "' > /dev/tty");
        ProcessBuilder p = new ProcessBuilder(comm);
        try {
            Process pr = p.start();
            if (pr.waitFor() == 1) {
                pr.destroy();
            }
        } catch (IOException ex) {
            System.out.println("Error Connecting to Bash");
        } catch (InterruptedException ex) {
            System.out.println("Error Connecting to Bash");
        }
    }

    public void updateView() {
        this.clear();
        System.out.print(lines.toString());
        System.out.flush();
        this.moveTo(lines.getLinePosX() + 1, lines.getLinePosY() + 1);
    }

    public void update(Observable obs, Object args) {
        /*
         * By checking if they have same reference we check we are updating
         * the correct observer.
         */
        if (obs == lines) {
            updateView();
        }
    }

    public void clear() {
        this.controlConsole("\033[H \033[2J");
        System.out.flush();
    }

    public void moveTo(int posx, int posy) {
        /*
         * Console doesn't help movement between lines if posx exceed number of cols.
         * Moreover, doesn't understand fact that Class Line can occupy more than one
         * phisical line. Need some Maths to help with that.
         * Understand that Coordenates starts at 1, not 0 as Java.
         */
        int realX = posx % this.lines.MAX_Cols;
        int realY = 0;
        Line[] l = this.lines.getLines();
        for (int i = 0; i <= posy; i++) {
            realY += l[i].getLength() / this.lines.MAX_Cols;
            if ((l[i].getLength() % this.lines.MAX_Cols) != 0) {
                realY++;
            }
        }

        controlConsole("\033[" + (realY + 1) + ";" + (realX + 1) + "f");
    }
}
