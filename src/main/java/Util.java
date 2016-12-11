public class Util {

    public static boolean contains (String[] array, String theChosenOne ) {

        if ( array.length == 0 ) return false;

        for (String s : array) {
            if (s.equals(theChosenOne)) return true;
        }

        return false;
    }

}
