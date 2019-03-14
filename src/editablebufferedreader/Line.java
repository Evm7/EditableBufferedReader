/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editablebufferedreader;

/**
 *
 * @author virtual
 */
public class Line {

    private Console console;
    private StringLine line;
    private boolean mode = Boolean.FALSE; //True: sobreescriptura:setCharAt. False: inserció:insertCharAt
    private int posx; //és la posició del cursor respecte la línia, no sobre la comanda

    public Line(Console console, int max) {
        this.console = console;
        this.posx = 0;
        this.line = new StringLine(max);
    }

    public void addChar(char c) {
        //Comprovar si estam en mode Sobreescriptura o Inserció
        if (mode) {
            this.line.setCharAt(c, this.posx);
        } else {
            this.line.insertCharAt(c, this.posx);
        }
        this.posx++;
        this.console.print(this.line.toString());
    }

    public void deleteChar() {
        this.line.deleteCharAt(this.posx - 1);
        this.posx--;
        this.console.print(this.line.toString());

    }

    public void suprimirChar() {
        this.line.deleteCharAt(this.posx);
        this.console.print(this.line.toString());
    }

    public void moveLeft() {
        if (this.posx > 0) {
            this.posx--;
            this.console.moveLeft();
        }
    }

    public void moveRight() {
        if (this.posx < this.line.length()) {
            this.posx++;
            this.console.moveRigth();
        }
    }

    public void moveEnd() {
        this.posx = this.line.length();
        this.console.moveEnd(this.posx);
    }

    public void moveHome() {
        this.posx = 0;
        this.console.moveHome(0);

    }

    public void moveUp() {
        int fin = this.posx - this.getNumCols();
        if (fin > 0) {
            this.posx = fin;
            this.console.moveUp();

        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void moveDown() {
        int fin = this.posx + this.getNumCols();
        if (fin < line.MAX) {
            this.posx = fin;
            this.console.moveDown();
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
        return this.mode;
    }

    public Boolean setPositionAt(int posx) {
        if (posx > 0 && posx < line.MAX) {
            this.posx = posx;
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
