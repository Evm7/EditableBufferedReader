/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editablebufferedreader;

import java.util.Observable;

/**
 *
 * @author virtual
 */
public class MultiLine extends Observable {

    private StringLine[] strings;
    private boolean mode; //True: sobreescriptura. False: inserció
    private int posx, posy; //és la posició del cursor respecte la línia, no sobre la comanda
    public final int MAX_Lines; //Màxim de línies verticals possibles

    public MultiLine(int maxCols, int maxFils) {
        this.mode = Boolean.FALSE;
        this.posx = 0;
        this.posy = 0;
        this.MAX_Lines = maxFils;
        this.strings = new StringLine[MAX_Lines];
        this.strings[this.posy] = new StringLine(maxCols);

    }

    public Boolean newLine() {
        if (this.posy < this.MAX_Lines) {
            this.posy++;
            this.posx = 0;
            this.strings[this.posy] = new StringLine(this.strings[this.posy - 1].MAX);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void addChar(char c) {
        //Comprovar si estam en mode Sobreescriptura o Inserció
        try {
            if (mode) {
                this.strings[this.posy].setCharAt(c, this.posx);
            } else {
                this.strings[this.posy].insertCharAt(c, this.posx);
            }
        } catch (IndexOutOfBoundsException ex) {
            if (this.newLine()) {
                this.addChar(c);
            }
        }
        this.setChanged();
        this.notifyObservers();

    }

    public void deleteChar() {
        try {
            this.strings[this.posy].deleteCharAt(this.posx - 1);
            this.posx--;
        } catch (IndexOutOfBoundsException ex) {
            if (this.posy != 0) {
                this.posy--;
                this.posx = this.strings[this.posy].length();
                this.deleteChar();
            }
        }
        this.setChanged();
        this.notifyObservers();
    }

    public void suprimirChar() {
        this.strings[this.posy].deleteCharAt(this.posx);
        this.setChanged();
        this.notifyObservers();
    }

    public void moveLeft() {
        if (this.posx > 0) {
            this.posx--;
            this.setChanged();
            this.notifyObservers();
        } else if (this.posy > 0) {
            this.posy--;
            this.posx = strings[this.posy].length();
            this.setChanged();
            this.notifyObservers();
        }

    }

    public void moveRight() {
        if (this.posx < this.strings[this.posy].MAX) {
            this.posx++;
            this.setChanged();
            this.notifyObservers();
        } else if (this.strings[this.posy + 1] != null) {
            this.posy++;
            this.posx = 0;
            this.setChanged();
            this.notifyObservers();
        }

    }

    public void moveEnd() {
        this.posx = strings[this.posy].length();
        this.setChanged();
        this.notifyObservers();
    }

    public void moveHome() {
        this.posx = 0;
        this.setChanged();
        this.notifyObservers();

    }

    public void moveUp() {
        if (this.posy != 0) {
            this.posy--;
            this.setChanged();
            this.notifyObservers();
        }
    }

    public void moveDown() {
        if ((this.posy < this.MAX_Lines) && (strings[this.posy + 1] != null)) {
            this.posy++;
            this.setChanged();
            this.notifyObservers();
        }
    }

    public int[] getLinePos() {
        int[] pos = new int[2];
        pos[0] = this.posx;
        pos[1] = this.posy;
        return pos;
    }

    public int getNumCols() {
        return this.strings[this.posy].MAX;
    }

    public int getNumLines() {
        return this.MAX_Lines;
    }

    public void click() {

    }

    public String toString() {
        String str = "";
        for (int i = 0; i < this.strings.length; i++) {
            str += "\n" + strings[i].toString();
        }
        return str;
    }

    public boolean getMode() {
        return this.mode;
    }

    public void setMode() {
        this.mode = !this.mode;
        this.setChanged();
        this.notifyObservers();
    }

    public void setPositionAt(int posx, int posy) {
        if ((posx >= 0) && (posx < strings[0].MAX) && (posy >= 0) && (posy < this.MAX_Lines)) {
            this.posx = posx;
            this.posy = posy;
            this.setChanged();
            this.notifyObservers();
        }
    }

}
