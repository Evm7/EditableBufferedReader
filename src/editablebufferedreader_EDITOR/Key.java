/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editablebufferedreader_EDITOR;

/**
 *
 * @author virtual
 */
public class Key {

    public final static int CRTL_C = 3;
    public final static int EXIT = 13;
    public final static int ESC = 27;
    public final static int CLAU = 91;
    public final static int UP = 65;
    public final static int DOWN = 66;
    public final static int RIGHT = 67;
    public final static int LEFT = 68;
    public final static int INSERT = 50;
    public final static int WAVE = 126;
    public final static int SUPR = 51;
    public final static int HOME = 72;
    public final static int END = 70;
    public final static int DEL = 127;

    //KEYS
    public final static int EXIT_KEY = 1009;
    public final static int CARAC = 1010;

    //CODES
    public static final String CLEAR_KEY = "\033[H\033[2J";
    public static final String UP_KEY = "\033[1A";
    public static final String DOWN_KEY = "\033[1B";
    public static final String RIGHT_KEY = "\033[1C";
    public static final String LEFT_KEY = "\033[1D";
    public static final String HOME_KEY = "\033[%d;0f";
    public static final String END_KEY = "\033[%d;%df";
    public static final String INSERT_KEY = "\033[4h";
    public static final String OVERWRITE_KEY = "\033[4l";
    public static final String DEL_KEY = "\033[P";
    public static final String ADD = "add";
    public static final String MOVE_TO = "\033[%d;%df";
    public static final String NEW_LINE = "\033[L";

    private String code;

    public Key(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
