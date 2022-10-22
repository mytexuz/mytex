package uz.enterprise.mytex.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorUtil {

    private ErrorUtil(){

    }

    public static String getStacktrace(final Throwable error){
        final StringWriter writer = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(writer, true);
        error.printStackTrace(printWriter);
        return writer.getBuffer().toString();
    }
}
