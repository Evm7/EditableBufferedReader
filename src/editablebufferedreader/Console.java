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
public class Console {

    public Console() {
        //Netejam Consola i ens situem en el principi de la consola.
        List<String> com =  Arrays.asList("bash", "-c", "echo -en '\033[0;0H \033[2J' > /dev/tty");
        controlConsole(com);
    }

    public void controlConsole(List<String> comanda) {
        ProcessBuilder p = new ProcessBuilder(comanda);
        try {
            p.start();
            // Runtime.getRuntime().exec(comm1).waitFor();
        } catch (IOException ex) {
            System.out.println("Error introducing Raw mode");
        }
    }
    
    public void print(String line){
        List<String> save_reference =  Arrays.asList("bash", "-c", "echo -en '\033[s' > /dev/tty");
        this.controlConsole(save_reference);
        this.clear();
        System.out.print(line);
        List<String> restart_reference =  Arrays.asList("bash", "-c", "echo -en '\033[s' > /dev/tty");
        this.controlConsole(restart_reference);
    }

    public void moveUp() {
        List<String> com = Arrays.asList("bash", "-c", "echo -en '\033[2A' > /dev/tty");
        this.controlConsole(com);
    }

    public void moveDown() {
        List<String> com = Arrays.asList("bash", "-c", "echo -en '\033[2B' > /dev/tty");
        this.controlConsole(com);
    }

    public void moveRigth() {
        List<String> com = Arrays.asList("bash", "-c", "echo -en '\033[2C' > /dev/tty");
        this.controlConsole(com);
    }

    public void moveLeft() {
        List<String> com = Arrays.asList("bash", "-c", "echo -en '\033[2D' > /dev/tty");
        this.controlConsole(com);
    }

    public void moveHome(int posy) {
        List<String> com = Arrays.asList("bash", "-c", "echo -en '\033[0;"+posy+"H' > /dev/tty");
        this.controlConsole(com);
    }

    public void moveEnd(int posx) {
        List<String> com = Arrays.asList("bash", "-c", "echo -en '\033["+posx+";0H' > /dev/tty");
        this.controlConsole(com);
    }

    public void clear() {
        List<String> com = Arrays.asList("bash", "-c", "echo -en '\033[2J \033[f' > /dev/tty");
        this.controlConsole(com);
    }

    public void moveTo(int posx, int posy){
        List<String> com = Arrays.asList("bash", "-c", "echo -en '\033["+posx+";"+posy+"f' > /dev/tty");
        this.controlConsole(com);
    }
}
