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

    private Line[] lines;
    private boolean mode; //True: sobreescriptura. False: inserció
    private int posy; //és la posició del cursor respecte la línia, no sobre la comanda
    public final int MAX_Lines; //Màxim de línies verticals possibles
    public final int MAX_Cols;

    public MultiLine(int maxCols, int maxFils) {
        this.mode = Boolean.FALSE;
        this.posy = 0;
        this.MAX_Lines = maxFils;
        this.MAX_Cols = maxCols;
        this.lines = new Line[MAX_Lines];
        this.lines[this.posy] = new Line(this.MAX_Cols, this.MAX_Lines);
    }

    public Boolean newLine() {
        //Alert with the possibility of not being in the last Line but not able to support more Lines.
        if (this.lines[this.MAX_Lines - 1] == null) {
            this.posy++;
            for (int i = this.MAX_Lines - 1; i > this.posy; i--) {
                this.lines[i] = this.lines[i - 1];
            }
            this.lines[this.posy] = new Line(this.MAX_Cols, this.MAX_Lines);
            this.lines[this.posy].setMode(this.mode);
            this.setChanged();
            this.notifyObservers();
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    //NO SE SI SE UTILITZARÀ PER ARA
    public void concat() { //True down : False up
        Line l = this.lines[this.posy].concat(this.lines[this.posy + 1]);

        if (l != null) {
            this.lines[this.posy++] = l;
        } else {
            for (int i = this.posy++; i > this.MAX_Lines - 1; i++) {
                this.lines[i] = this.lines[i + 1];
            }
            this.lines[this.MAX_Lines - 1] = null;
        }
        this.setChanged();
        this.notifyObservers();
    }

    public void addChar(char c) {
        //Comprovar si estam en mode Sobreescriptura o Inserció
        try {
            this.lines[this.posy].addChar(c);
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
            this.lines[this.posy].deleteChar();
        } catch (IndexOutOfBoundsException ex) {
            if (this.posy != 0) {
                this.posy--;
                //implies line is ffinished due to NextLineDot)
                if (this.lines[this.posy].getLength() != this.MAX_Cols) {
                    this.concat();
                }
            }
        }
        this.setChanged();
        this.notifyObservers();
    }

    public void suprimirChar() {
        try {
            this.lines[this.posy].suprimirChar();
        } catch (IndexOutOfBoundsException ex) {
            if ((this.posy < this.MAX_Lines - 1) && (this.lines[this.posy + 1] != null)) {
                this.concat();
            }
        }
        this.setChanged();
        this.notifyObservers();
    }

    public void moveLeft() {
        try {
            this.lines[this.posy].moveLeft();
        } catch (IndexOutOfBoundsException ex) {
            if (this.posy > 0) {
                this.posy--;
            }
        }
        this.setChanged();
        this.notifyObservers();
    }

    public void moveRight() {
        try {
            this.lines[this.posy].moveRight();
        } catch (IndexOutOfBoundsException ex) {
            if (this.posy < this.MAX_Lines - 1) {
                this.posy++;
            }
        }
        this.setChanged();
        this.notifyObservers();
    }

    public void moveEnd() {
        this.lines[this.posy].moveEnd();
        this.setChanged();
        this.notifyObservers();
    }

    public void moveHome() {
        this.lines[this.posy].moveHome();
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
        if ((this.posy < this.MAX_Lines) && (lines[this.posy + 1] != null)) {
            this.posy++;
            this.setChanged();
            this.notifyObservers();
        }
    }

    public int getLinePosY() {
        return this.posy;
    }
    
    public int getLinePosX() {
        return this.lines[this.posy].getLinePos();
    }


    public void click() {

    }

    public String toString() {
        String str = "";
        int i = 0;
        while (lines[i] != null) {
            str += "\n" + lines[i].toString();
            i++;
        }
        return str;
    }

    public boolean getMode() {
        return this.mode;
    }

    public void setMode() {
        this.mode = !this.mode;
        for(Line l: lines){
            l.setMode(this.mode);
        }
        this.setChanged();
        this.notifyObservers();
    }

    public void setPositionAt(int posx, int posy) {
        if ((posx >= 0) && (posx < (this.MAX_Cols*this.MAX_Lines-1)) && (posy >= 0) && (posy < this.MAX_Lines)) {
            this.posy = posy;
            if(this.lines[this.posy]!=null){
                this.lines[this.posy].setPos(posx);
            }
            this.setChanged();
            this.notifyObservers();
        }
    }
    
    public Line[] getLines(){
        return this.lines;
        
    }

}
