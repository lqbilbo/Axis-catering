package scratch;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTests {

    public static void main(String[] args) {

        /*String s = "\uFE64" + "script" + "\uFE65";

        s = Normalizer.normalize(s, Normalizer.Form.NFKC);
        Pattern pattern = Pattern.compile("[<>]");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            throw new IllegalStateException();
        } else {

        }

        System.out.println(s);*/

        String maliciousInput = "<scr" + "\uFDEF" + "ipt>";
        String s = Normalizer.normalize(maliciousInput, Normalizer.Form.NFKC);

        s = s.replaceAll("[\\p{Cn}]", "\uFFFD");
        Pattern pattern = Pattern.compile("<script>");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            throw new IllegalArgumentException("Invalid input");
        }

    }
}
