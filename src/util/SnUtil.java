package util;

import java.util.Date;

public class SnUtil {

    public static String getStudentSn(String prefic,String suffix){
        return prefic+new Date().getTime()+suffix;
    }
}
