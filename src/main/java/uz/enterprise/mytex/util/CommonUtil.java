package uz.enterprise.mytex.util;

import uz.enterprise.mytex.enums.Lang;

public class CommonUtil {

    public static String getFlagByLang(Lang lang){
        return switch (lang){
            case UZ -> "\uD83C\uDDFA\uD83C\uDDFF";
            case EN -> "\uD83C\uDDFA\uD83C\uDDF8";
            case RU -> "\uD83C\uDDF7\uD83C\uDDFA";
        };
    }
}
