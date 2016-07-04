package com.sinoservices.doppler.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class DopplerUtil {
    public static boolean matchTableNotExist(String message) {
        Pattern pattern = Pattern.compile("^Table.*doesn't exist$");
        Matcher matcher = pattern.matcher(message);
        return matcher.matches();
    }

    public static void main(String[] a){
        Pattern pattern = Pattern.compile(".*\\.[s]{0,1}html.*");
        Matcher matcher = pattern.matcher("http://dsa/d/sa.html?aa=dsa");
        System.out.print(matcher.matches());
    }
}
