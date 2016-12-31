package gr.katsos.nikos;

public class Util {

    public static boolean contains (String[] array, String theChosenOne ) {

        for (String s : array) {
            if (s.equals(theChosenOne)) return true;
        }

        return false;
    }

}
