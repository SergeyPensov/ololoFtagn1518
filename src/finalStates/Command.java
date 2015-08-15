package finalStates;

import finalStates.com.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public abstract class Command {
    public abstract String letters();

    public abstract UnitState apply(UnitState s);

    private Set<Character> lettersSet = null;

    public boolean has(char c) {
        if (null == lettersSet) {
            final String let = letters();
            lettersSet = new HashSet<>(let.length());
            for (int i = 0; i < let.length(); ++i) {
                lettersSet.add(let.charAt(i));
            }
        }
        return lettersSet.contains(c);
    }

    public static final Command C_LEFT = new Left();
    public static final Command C_RIGHT = new Right();
    public static final Command C_SW = new SW();
    public static final Command C_SE = new SE();
    public static final Command C_CW = new CW();
    public static final Command C_CCW = new CCW();

    public static Command[] commands = {C_LEFT, C_RIGHT, C_SW, C_SE, C_CW, C_CCW};

    public static Command getCommand(int index) {
        return commands[index];
    }

    public static String encode(List<Command> list) {
        StringBuilder sb = new StringBuilder(list.size());

        for (int startCommand = 0; startCommand < list.size(); ) {

            String foundPhrase = null;
            for (String magicWord : magicWords) {
                final int l = magicWord.length();

                boolean found = true;
                for (int i = 0; i < l; ++i) {
                    final char c = magicWord.charAt(i);
                    if (i + startCommand >= list.size()) {
                        found = false;
                        break;
                    }
                    Command com = list.get(i + startCommand);
                    if (!com.has(c)) {
                        found = false;
                        break;
                    }
                }

                if (found) {
                    if (foundPhrase == null || foundPhrase.length() < l) {
                        foundPhrase = magicWord;
                    }
                }
            }

            if (foundPhrase != null) {
                System.out.println(foundPhrase);
                sb.append(foundPhrase);
                startCommand += foundPhrase.length();
            } else {
                sb.append(list.get(startCommand).letters().charAt(0));
                startCommand++;
            }
        }

        return sb.toString();
    }

    public static final String[] magicWords =
            {
                    "Ei!", "Ia! Ia!!", "R'lyeh", "Yuggoth", "Tsathoggua", "YogSothoth",
                    "Necronomicon", "vigintillion", "Cthulhu fhtagn!",
                    "Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn.",
                    "In his house at R'lyeh dead Cthulhu waits dreaming.",
                    "The Laundry", "Planet 10", "Yoyodyne", "monkeyboy",
                    "John Bigboote", "BLUE HADES", "CASE NIGHTMARE GREEN"
            };
}
