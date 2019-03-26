/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editablebufferedreader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author virtual
 */
public class provesRaw {

    public static void main(String[] args) {

   /*
        System.out.println("Staring Raw Mode...");
        EditableBufferedReader edb = new EditableBufferedReader(new InputStreamReader(System.in));

         edb.setRaw();
         MultiLine lines = new MultiLine(40,10);
        Console console = new Console(lines);
        lines.addObserver(console);
        System.out.println("dios del futbol");
      */
                EditableBufferedReader edb = new EditableBufferedReader(new InputStreamReader(System.in));

        int a=0;
        a = edb.read();
         
         System.out.println("La variable a:" + (char)a);
         edb.unsetRaw();
        
        /* */
        /*
         System.console().printf("echo tput cols");
         List<String> com =  Arrays.asList("bash", "-c", "echo -en '\033[0;0H \033[2J' > /dev/tty");
         ProcessBuilder p = new ProcessBuilder(com);
         try {
         p.start();
         // Runtime.getRuntime().exec(comm1).waitFor();
         } catch (IOException ex) {
         System.out.println("Error introducing Raw mode");
         }
         EditableBufferedReader edb = new EditableBufferedReader(new InputStreamReader(System.in));
         int a =edb.read();
         System.out.println("La variable a:" + a );
         */
         /*
        System.out.print((char) 0x1B);
        System.out.print((char) 0x5B);
        System.out.print("18t");
        Pattern pattern = Pattern.compile("8;(\\d+);(\\d+)");
        Scanner scanner = new Scanner(System.in);
        scanner.findWithinHorizon(pattern, 0);
        int numRows = Integer.parseInt(scanner.match().group(2));
        scanner.close();
        System.out.println("La variable a:" + numRows);
                 */
    }
}
