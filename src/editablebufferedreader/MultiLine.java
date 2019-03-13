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
public class MultiLine {
     private Console console;
    private StringLine[] strings;
    private boolean mode = Boolean.TRUE; //True: sobreescriptura. False: inserció
    private int posx, posy; //és la posició del cursor respecte la línia, no sobre la comanda
    private int length;
    public final int MAX_Lines; //Màxim de línies verticals possibles
    public final int MAX_Cols; //Màxim de columnes horitzontals possibles    
    private int limit; //és la posició que queda per escriure en la línia de comanda

    public MultiLine(Console console) {
        this.console = console;
        this.posx = 0;
        this.posy = 0;
        this.length = 0;
        this.MAX_Lines = this.getNumLines();
        this.MAX_Cols = this.getNumCols();
        this.limit = this.MAX_Cols - this.posx;
        this.strings = new StringLine[MAX_Lines];
        this.strings[this.posy] = new StringLine();

    }

    public boolean addChar(char c) {
        //Comprovar si estam en mode Sobreescriptura o Inserció
        if (mode) {
            this.strings[this.posy].setCharAt(c, this.posx);
        } else {
            this.strings[this.posy].insertCharAt(c, this.posx);
            this.length++;
        }
        boolean fin = outOfBoundaryX(Boolean.TRUE);
        if (fin) {
            this.console.print(this.strings[posy].toString());

        }
        return fin;
    }

    public boolean deleteChar() {
        if (this.length == 0) {
            throw new IndexOutOfBoundsException();
        } else {
            this.strings[this.posy].deleteCharAt(this.posx - 1);
            this.posx--;
            this.length--;
        }
        boolean fin = outOfBoundaryX(Boolean.TRUE);
        if (fin) {
            this.console.print(this.strings[posy].toString());

        }
        return fin;
    }

    public boolean suprimirChar() {
        if (this.length == 0) {
            throw new IndexOutOfBoundsException();
        } else {
            this.strings[this.posy].deleteCharAt(this.posx);
            this.length--;
        }
        boolean fin = outOfBoundaryX(Boolean.TRUE);
        if (fin) {
            this.console.print(this.strings[posy].toString());
        }
        return fin;
    }

    public boolean moveLeft() {
        if (this.posx == 0) {
            return false;
        }
        boolean fin = outOfBoundaryX(Boolean.FALSE);
        if (fin) {
            this.console.moveLeft();
        }
        return fin;

    }

    public boolean moveRight() {
        if (this.posx == this.length) {
            return false;
        }

        boolean fin = outOfBoundaryX(Boolean.TRUE);
        if (fin) {
            this.console.moveRigth();
        }
        return fin;

    }

    public void moveEnd() {
        this.posx = strings[this.posy].length();
        this.console.moveEnd(strings[this.posy].length());
    }

    public void moveHome() {
        this.posx = 0;
        this.console.moveHome(this.posy);

    }

    public boolean moveUp() {
        boolean fin = outOfBoundaryY(Boolean.TRUE);
        if (fin) {
            this.console.moveUp();
        }
        return fin;
    }

    public boolean moveDown() {
        boolean fin = outOfBoundaryY(Boolean.FALSE);
        if (fin) {
            this.console.moveDown();
        }
        return fin;
    }

    public Boolean outOfBoundaryX(Boolean direction) {
        //True es right --- False es Left
        int direct = -1;
        if (direction) {
            direct = 1;
        }
        if (this.posx + direct == this.limit) {
            this.posy += direct;
            this.strings[this.posy] = new StringLine();
            this.posx = 0;
        } else {
            this.posx += direct;
        }
        if (this.posx >= this.strings[0].MAX && this.posy >= this.MAX_Lines && direction) {
            return Boolean.FALSE;
        } else if (this.posx <= 0 && this.posy <= 0 && !direction) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    public Boolean outOfBoundaryY(Boolean direction) {
        //True es Up ---- False es Down
        int direct = 1;
        if (direction) {
            direct = -1;
        }
        if (this.posy <= 0 && direction) {
            return Boolean.FALSE;
        } else if (this.posy >= this.MAX_Lines - 1) {
            return Boolean.FALSE;
        } else {
            this.posy += direct;
            return Boolean.TRUE;
        }
    }

    public int[] getLinePos() {
        int[] pos = new int[2];
        pos[0] = this.posx;
        pos[1] = this.posy;
        return pos;
    }

    public int getNumCols() {
        int limit = 40;
        return limit;
    }

    public int getNumLines() {
        int limit = 1;
        return limit;
    }

    public void click() {

    }

    public String toString() {
        String str = "";
        for (int i = 0; i < this.strings.length; i++) {
            str += strings[i].toString();
        }
        return str;
    }

    public boolean getMode() {
        return this.mode;
    }

    public boolean setMode() {
        this.mode = !this.mode;
        return this.mode;
    }

    public Boolean setPositionAt(int posx, int posy) {
        if (posx > 0 && posx < strings[0].MAX && posy > 0 && posy < this.MAX_Lines) {
            this.posx = posx;
            this.posy = posy;
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
