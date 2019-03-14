/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editablebufferedreader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author virtual
 */
public final class Console {

    public Console() {
        //Netejam Consola i ens situem en el principi de la consola.
        controlConsole("\033[0;0H \033[2J");
    }

    public void controlConsole(String comanda) {
        List<String> comm =  Arrays.asList("bash", "-c", "echo -en '"+comanda+"' > /dev/tty");
        ProcessBuilder p = new ProcessBuilder(comm);
        try {
            p.start();
        } catch (IOException ex) {
            System.out.println("Error Connecting to Bash");
        }
    }
    
    public void print(String line){
        this.controlConsole("\033[s");
        this.clear();
        System.out.print(line);
        this.controlConsole("\033[u");
    }

    public void moveUp() {
        this.controlConsole("\033[2A");
    }

    public void moveDown() {
        this.controlConsole("\033[2B");
    }

    public void moveRigth() {
        this.controlConsole("\033[2C");
    }

    public void moveLeft() {
        this.controlConsole("\033[2D");
    }

    public void moveHome(int posy) {
        this.controlConsole("\033[0"+posy+"H");
    }

    public void moveEnd(int posx) {
        this.controlConsole("\033["+posx+";0H");
    }

    public void clear() {
        this.controlConsole("\033[2J \033[f");
    }

    public void moveTo(int posx, int posy){
        this.controlConsole("\033["+posx+";"+posy+"f");
    }
}
