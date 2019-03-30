/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editablebufferedreader_EDITOR;

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
        this.MAX_Lines = maxFils - 1;
        this.MAX_Cols = maxCols - 3; //Important to note that we take 3 out just to be able to show complete line afterwards
        this.lines = new Line[MAX_Lines];
        this.lines[this.posy] = new Line(this.MAX_Cols);
    }

    public Boolean newLine() {
        //Alert with the possibility of not being in the last Line but not able to support more Lines.
        if (this.lines[this.MAX_Lines - 1] == null) {
            if (this.lines[this.posy + 1] == null) {
                //implies we are creating new line because next lines are empry
                this.posy++;
                for (int i = this.MAX_Lines - 1; i > this.posy; i--) {
                    this.lines[i] = this.lines[i - 1];
                }
                this.lines[this.posy] = new Line(this.MAX_Cols);
                this.lines[this.posy].setMode(this.mode);

                Key arg = new Key(Key.NEXT_LINE);
                this.setChanged();
                this.notifyObservers(arg);

                arg = new Key(Key.NEW_LINE);
                this.setChanged();
                this.notifyObservers(arg);
                arg = new Key("\r");
                this.setChanged();
                this.notifyObservers(arg);
                return Boolean.TRUE;
            } else if ((this.lines[this.posy + 1] == null) && this.lines[this.posy].getLength() == this.MAX_Cols) {
                /*
                 *   Implies we are adding characters in full line so characters who are after move forware.
                 *   Last character move to next line and so on.
                 */
                this.readaptForw();
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    /*
     *  Delete last character of line before and position the cursor over there.
     *  It is important to move all the other characters -
     */
    public void concatDelete() {
        char c = this.lines[this.posy].getStringLine().toString().charAt(0);
        this.suprimirChar();
        this.posy--;
        this.moveEnd();
        this.deleteChar();
        this.addChar(c);
    }

    public void readaptForw() { //i is the current line where we delete
        int currentY = this.posy;
        int currentX = this.lines[this.posy].getLinePos();
        this.posy = this.MAX_Lines;
        while (this.posy > currentY) {
            if (lines[this.posy] != null) {
                if (lines[this.posy].getLength() == this.MAX_Cols) {
                    char c = this.lines[this.posy].getStringLine().toString().charAt(this.MAX_Lines - 1);
                    this.moveEnd();
                    this.deleteChar();
                    this.posy++;
                    this.moveHome();
                    this.addChar(c);
                    this.posy = this.posy - 2;
                }
            } else {
                this.posy--;
            }
        }
        this.setPositionAt(currentX, currentY);
    }

    public void readaptBack(Boolean fact) { //i is the current line where we delete
        int currentY = this.posy;
        int currentX = this.lines[this.posy].getLinePos();
        if (fact) { //it implies that we have delete first row character
            this.posy++;
        }
        while (this.lines[this.posy + 1] != null) {
            this.posy++;
            char c = this.lines[this.posy].getStringLine().toString().charAt(0);
            this.moveHome();
            this.suprimirChar();
            this.posy--;
            this.moveEnd();
            this.addChar(c);
            this.posy++;
        }
        this.setPositionAt(currentX, currentY);
    }

    public void addChar(char c) {
        //Comprovar si estam en mode Sobreescriptura o Inserció
        try {
            this.lines[this.posy].addChar(c);
        } catch (IndexOutOfBoundsException ex) {
            if (this.newLine()) {
                this.lines[this.posy].addChar(c);
            }
        }
        /*
         Key arg = new Key(Key.ADD_CHAR);
         this.setChanged();
         this.notifyObservers(arg);
         */
        Key arg = new Key(c + "");
        this.setChanged();
        this.notifyObservers(arg);

    }

    public void deleteChar() {
        Boolean fact = Boolean.FALSE;
        try {
            this.lines[this.posy].deleteChar();
            Key arg = new Key(Key.LEFT_KEY);
            Key arg2 = new Key(Key.DEL_KEY);
            this.setChanged();
            this.notifyObservers(arg);
            this.setChanged();
            this.notifyObservers(arg2);

        } catch (IndexOutOfBoundsException ex) {
            if (this.posy != 0) {
                this.concatDelete();
                fact = Boolean.TRUE;
            }
        }
        if (this.lines[this.posy + 1] != null) {
            this.readaptBack(fact);
        }

    }

    public void suprimirChar() {
        try {
            this.lines[this.posy].suprimirChar();
            Key arg = new Key(Key.DEL_KEY);
            this.setChanged();
            this.notifyObservers(arg);
        } catch (IndexOutOfBoundsException ex) {
            if ((this.posy < this.MAX_Lines - 1) && (this.lines[this.posy + 1] != null)) {
                //this.concat();   
            }
        }
        if (this.lines[this.posy + 1] != null) {
            this.readaptBack(Boolean.FALSE);
        }
    }

    public void moveLeft() {
        try {
            this.lines[this.posy].moveLeft();
            Key arg = new Key(Key.LEFT_KEY);
            this.setChanged();
            this.notifyObservers(arg);
        } catch (IndexOutOfBoundsException ex) {
            if (this.posy != 0) {
                this.moveUp();
                this.moveEnd();
            }
        }
    }

    public void moveRight() {
        try {
            this.lines[this.posy].moveRight();
            Key arg = new Key(Key.RIGHT_KEY);
            this.setChanged();
            this.notifyObservers(arg);

        } catch (IndexOutOfBoundsException ex) {
            if ((this.posy < this.MAX_Lines) && (this.lines[this.posy + 1] != null)) {
                this.moveDown();
                this.moveHome();
            }
        }

    }

    public void moveEnd() {
        this.lines[this.posy].moveEnd();
        Key arg = new Key(String.format(Key.END_KEY, this.posy + 1, this.lines[this.posy].getLength() + 1));
        this.setChanged();
        this.notifyObservers(arg);
    }

    public void moveHome() {
        this.lines[this.posy].moveHome();
        Key arg = new Key(String.format(Key.HOME_KEY, this.posy + 1));
        this.setChanged();
        this.notifyObservers(arg);

    }

    public void moveUp() {
        Key arg;
        if (this.posy != 0) {
            int posx = this.getLinePosX();
            this.posy--;
            try {
                this.lines[this.posy].setPos(posx);
            } catch (IndexOutOfBoundsException ex) {
                this.moveEnd();
            }
            arg = new Key(Key.UP_KEY);
            this.setChanged();
            this.notifyObservers(arg);
        } else {
            this.moveHome();
        }

    }

    public void moveDown() {
        if ((this.posy < this.MAX_Lines) && (lines[this.posy + 1] != null)) {
            int posx = this.getLinePosX();
            this.posy++;
            try {
                this.lines[this.posy].setPos(posx);
                Key arg = new Key(Key.DOWN_KEY);
                this.setChanged();
                this.notifyObservers(arg);
            } catch (IndexOutOfBoundsException ex) {
                this.moveEnd();
            }

        } else {
            this.moveEnd();
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
        while ((i < this.MAX_Lines - 1) && (lines[i] != null)) {
            str += "\r" + lines[i].toString() + "\n";
            i++;
        }
        return str;
    }

    public boolean getMode() {
        return this.mode;
    }

    public void setMode() {
        this.mode = !this.mode;
        int i = 0;
        while (this.lines[i] != null) {
            this.lines[i].setMode(this.mode);
            i++;
        }
        Key arg;
        if (this.mode) {
            arg = new Key(Key.OVERWRITE_KEY);
        } else {
            arg = new Key(Key.INSERT_KEY);
        }
        this.setChanged();
        this.notifyObservers(arg);
    }

    public void setPositionAt(int posx, int posy) {
        if ((posx >= 0) && (posx < (this.MAX_Cols)) && (posy >= 0) && (posy < this.MAX_Lines)) {
            if (this.lines[posy] != null) {
                this.posy = posy;
                this.lines[this.posy].setPos(posx);
                Key arg = new Key(String.format(Key.MOVE_TO, posy + 1, posx + 1));
                this.setChanged();
                this.notifyObservers(arg);
            }

        }
    }

    public Line[] getLines() {
        return this.lines;

    }

}
