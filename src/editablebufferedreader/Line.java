/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editablebufferedreader;

/**
 *
 * @author Evm7
 */
public class Line {

    private StringLine line;
    private boolean mode; //True: sobreescriptura:setCharAt. False: inserció:insertCharAt
    private int posx; //és la posició del cursor respecte la línia, no sobre la comanda

    public Line(int maxX) {
        this.posx = 0;
        this.line = new StringLine(maxX);
        this.mode = Boolean.FALSE;
    }

    public Line(int maxX, int maxY) {
        this.posx = 0;
        this.line = new StringLine(maxX, maxY);
        this.mode = Boolean.FALSE;
    }

    public Line(boolean mode, StringLine newLine) {
        this.line = newLine;
        this.mode = mode;
        this.posx = newLine.length();
    }

    public void addChar(char c) throws IndexOutOfBoundsException {
        //Comprovar si estam en mode Sobreescriptura o Inserció
        if (mode) {
            this.line.setCharAt(c, this.posx);
        } else {
            this.line.insertCharAt(c, this.posx);
        }
        this.posx++;
    }

    public void deleteChar() throws IndexOutOfBoundsException {
        this.line.deleteCharAt(this.posx - 1);
        this.posx--;
    }

    public void suprimirChar() throws IndexOutOfBoundsException {
        this.line.suprCharAt(this.posx);
    }

    public void moveLeft() {
        if (this.posx > 0) {
            this.posx--;
        } else {
            throw new IndexOutOfBoundsException("Left");
        }
    }

    public void moveRight() {
        if (this.posx < this.line.MAX) {
            this.posx++;
        } else {
            throw new IndexOutOfBoundsException("Right");
        }
    }

    public void moveEnd() {
        this.posx = this.line.length();
    }

    public void moveHome() {
        this.posx = 0;
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

    public void setMode(Boolean mode) {
        this.mode = mode;
    }

    public void setPos(int posx) {
        if (posx > 0 && posx < line.MAX) {
            this.posx = posx;
        }
    }

    public int getLength() {
        return this.line.length();
    }

    public Line concat(Line line_to_concat) {
        if(line!=null){
            this.posx+=line_to_concat.getLength();
        return (new Line(this.mode, this.line.concat(line_to_concat.getStringLine())));
        }
        return null;
    }

    public StringLine getStringLine() {
        return this.line;
    }
}
