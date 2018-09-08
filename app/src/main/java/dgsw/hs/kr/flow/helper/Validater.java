package dgsw.hs.kr.flow.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018-04-26.
 */

public class Validater {
    private final String EMAIL_VALID = "^[a-zA-Z0-9]+@dgsw\\.hs\\.kr$";
    private final String PASSWORD_VALID = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()])(?=\\S+$).{8,16}$";

    public boolean emailValidation(String inputEmail) {
        Pattern emailPattern = Pattern.compile(EMAIL_VALID);

        Matcher matcher = emailPattern.matcher(inputEmail);

        return matcher.matches();
    }

    public boolean passwordVaildation(String inputPassword){

        Pattern passwordPattern = Pattern.compile(PASSWORD_VALID);

        Matcher matcher = passwordPattern.matcher(inputPassword);

        return matcher.matches();
    }
}
