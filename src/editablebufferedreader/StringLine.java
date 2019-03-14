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
public class StringLine {

    private char[] string;
    public final int MAX; //DEFINIREM EL LÍMIT DE caràctese possibles en LA PANTALLA DE COMANDA
    private int length = 0;

    public StringLine(int max) {
        this.MAX = 10 * max;
        string = new char[10 * MAX]; //en aquest cas sols es poden fer 10 linies de coid
    }

    public StringLine(int maxX, int maxY) {
        this.MAX = maxX;
        string = new char[maxY * MAX];
    }

    //insert permet introduir un caràcter entre dos altres, sense reemplaçar-ne cap dels dos
    public StringLine insertCharAt(char c, int offset) throws IndexOutOfBoundsException {
        if ((offset > this.MAX)) { //comprovar si el offset es mayor que el limite.
            throw new IndexOutOfBoundsException("Next");
        }
        
        for (int i = this.MAX - 1; i > offset; i--) {
            this.string[i] = this.string[i - 1];
        }

        this.string[offset] = c;
        this.length++;
        return this;

    }

    public StringLine deleteCharAt(int offset) throws IndexOutOfBoundsException {
        
         if (offset < 0) {
         throw new IndexOutOfBoundsException("Previous");
         }
         
        if (offset == 0 ){
            return this;
        }

        for (int i = offset; i < this.length - 1; i++) {
            this.string[i] = this.string[i + 1];
        }
        this.string[this.length - 1] = ' ';
        this.length--;
        return this;
    }

    //implica reemplaçament, pel qual no s'augmenta length (exceptuant no hi ha res)
    public StringLine setCharAt(char c, int offset) {
        this.string[offset] = c;
        if (length == 0) {
            this.length++;
        }
        return this;
    }

    @Override
    public String toString() {
        return new String(this.string, 0, this.MAX);
    }

    public int length() {
        return this.length;
    }
}
