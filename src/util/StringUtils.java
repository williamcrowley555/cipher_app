package util;

public class StringUtils {

    public static boolean isUpperCase(String str) {
        char ch;
        boolean lowerCaseFlag = false;

        for(int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);

            if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }

            if(lowerCaseFlag)
                return false;
        }

        return true;
    }

    public static boolean isLowerCase(String str) {
        char ch;
        boolean upperCaseFlag = false;

        for(int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);

            if (Character.isUpperCase(ch)) {
                upperCaseFlag = true;
            }

            if(upperCaseFlag)
                return false;
        }

        return true;
    }
}
