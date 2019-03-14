/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editablebufferedreader;

import java.util.Observable;

/**
 *
 * @author Evm7
 */
public class Line extends Observable {

    private StringLine line;
    private boolean mode = Boolean.FALSE; //True: sobreescriptura:setCharAt. False: inserció:insertCharAt
    private int posx; //és la posició del cursor respecte la línia, no sobre la comanda

    public Line(int max) {
        this.posx = 0;
        this.line = new StringLine(max);
    }

    public void addChar(char c) throws IndexOutOfBoundsException {
        //Comprovar si estam en mode Sobreescriptura o Inserció
        if (mode) {
            this.line.setCharAt(c, this.posx);
        } else {
            this.line.insertCharAt(c, this.posx);
        }
        this.posx++;
        this.setChanged();
        this.notifyObservers();
    }

    public void deleteChar() throws IndexOutOfBoundsException {
        this.line.deleteCharAt(this.posx - 1);
        this.posx--;
        this.setChanged();
        this.notifyObservers();

    }

    public void suprimirChar() throws IndexOutOfBoundsException {
        this.line.deleteCharAt(this.posx);
        this.setChanged();
        this.notifyObservers();
    }

    public void moveLeft() {
        if (this.posx > 0) {
            this.posx--;
            this.setChanged();
            this.notifyObservers();
        }
    }

    public void moveRight() {
        if (this.posx < this.line.length()) {
            this.posx++;
            this.setChanged();
            this.notifyObservers();
        }
    }

    public void moveEnd() {
        this.posx = this.line.length();
        this.setChanged();
        this.notifyObservers();
    }

    public void moveHome() {
        this.posx = 0;
        this.setChanged();
        this.notifyObservers();

    }

    public void moveUp() throws IndexOutOfBoundsException {
        int fin = this.posx - this.getNumCols();
        if (fin > 0) {
            this.posx = fin;
            this.setChanged();
            this.notifyObservers();

        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void moveDown() throws IndexOutOfBoundsException {
        int fin = this.posx + this.getNumCols();
        if (fin < line.MAX) {
            this.posx = fin;
            this.setChanged();
            this.notifyObservers();
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public int getLinePos() {
        return this.posx;
    }

    public int getNumCols() {
        int limit = 40;
        return limit;
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
        this.setChanged();
        this.notifyObservers();
        return this.mode;
    }

    public Boolean setPositionAt(int posx) {
        if (posx > 0 && posx < line.MAX) {
            this.posx = posx;
            return Boolean.TRUE;
        }
        this.setChanged();
        this.notifyObservers();
        return Boolean.FALSE;
    }

}
