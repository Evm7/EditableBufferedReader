/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editablebufferedreader_EDITOR;

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
        System.out.print(Key.CLEAR_KEY);
        this.lines = lines;
    }

    public void updateView(Object arg) {
        Key key = (Key) arg;
        System.out.print(key.getCode());
    }

    public void update(Observable obs, Object args) {
        /*
         * By checking if they have same reference we check we are updating
         * the correct observer.
         */
        if (obs == lines) {
            updateView(args);
        }
    }

    public void clear() {
        System.out.print(Key.CLEAR_KEY);
        System.out.flush();
    }

    public void moveTo(int posx, int posy) {
        System.out.print(String.format(Key.MOVE_TO, posy, posx));
    }
}
