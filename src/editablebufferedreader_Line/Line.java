/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editablebufferedreader_Line;

import java.util.Observable;

/**
 *
 * @author Evm7
 */
public class Line extends Observable {

    private StringLine line;
    private boolean mode; //True: sobreescriptura:setCharAt. False: inserció:insertCharAt
    private int posx; //és la posició del cursor respecte la línia, no sobre la comanda

    public Line(int max) {
        this.posx = 0;
        this.line = new StringLine(max);
        this.mode = Boolean.FALSE;
    }

    public Line(int maxX, int maxY) {
        this.posx = 0;
        this.line = new StringLine(maxX, maxY);
        this.mode = Boolean.FALSE;
    }

    public void addChar(char c) throws IndexOutOfBoundsException {
        //Comprovar si estam en mode Sobreescriptura o Inserció
        if (mode) {
            this.line.setCharAt(c, this.posx);
        } else {
            this.line.insertCharAt(c, this.posx);
        }
        this.posx++;

        Key arg = new Key(c+"");
        this.setChanged();
        this.notifyObservers(arg);
    }

    public void deleteChar() {
        try {
            this.line.deleteCharAt(this.posx - 1);
            this.posx--;
            Key arg = new Key(Key.LEFT_KEY);
            Key arg2 = new Key(Key.DEL_KEY);
            this.setChanged();
            this.notifyObservers(arg);
            this.setChanged();
            this.notifyObservers(arg2);
        } catch (IndexOutOfBoundsException ex) {
        }

    }

    public void suprimirChar() {
        try {
            this.line.suprCharAt(this.posx);
            Key arg = new Key(Key.DEL_KEY);
            this.setChanged();
            this.notifyObservers(arg);
        } catch (IndexOutOfBoundsException ex) {
        }

    }

    public void moveLeft() {
        if (this.posx > 0) {
            this.posx--;
            Key arg = new Key(Key.LEFT_KEY);
            this.setChanged();
            this.notifyObservers(arg);
        }
    }

    public void moveRight() {
        if (this.posx < this.line.length()) {
            this.posx++;
            Key arg = new Key(Key.RIGHT_KEY);
            this.setChanged();
            this.notifyObservers(arg);
        }
    }

    public void moveEnd() {
        this.posx = this.line.length();
        Key arg = new Key(String.format(Key.END_KEY, this.line.length()+1));
        this.setChanged();
        this.notifyObservers(arg);
    }

    public void moveHome() {
        this.posx = 0;
        Key arg = new Key(Key.HOME_KEY);
        this.setChanged();
        this.notifyObservers(arg);

    }

    public void moveUp() {
        int fin = this.posx - line.MAX;
        if (fin > 0) {
            this.posx = fin;
            Key arg = new Key(Key.UP_KEY);
            this.setChanged();
            this.notifyObservers(arg);
        } else {
            this.moveHome();;
        }
    }

    public void moveDown() throws IndexOutOfBoundsException {
        int fin = this.posx + line.MAX;
        if (fin < line.MAX) {
            this.posx = fin;
            Key arg = new Key(Key.DOWN_KEY);
            this.setChanged();
            this.notifyObservers(arg);
        } else {
            this.moveEnd();
        }
    }

    public int getLinePos() {
        return this.posx;
    }

    @Override
    public String toString() {
        return line.toString();
    }

    public boolean getMode() {
        return this.mode;
    }

    public boolean setMode() {
        this.mode = !this.mode;
        Key arg;
        if (this.mode) {
            arg = new Key(Key.OVERWRITE_KEY);
        } else {
            arg = new Key(Key.INSERT_KEY);
        }
        this.setChanged();
        this.notifyObservers(arg);
        return this.mode;
    }

    public void setPositionAt(int posx) {
        if (posx > 0 && posx < line.MAX) {
            this.posx = posx;
            Key arg = new Key(String.format(Key.MOVE_TO, 0, this.posx));
            this.setChanged();
            this.notifyObservers(arg);

        }

    }
}
