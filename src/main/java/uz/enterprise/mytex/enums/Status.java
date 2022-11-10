package uz.enterprise.mytex.enums;


import java.util.Objects;

public enum Status {
    ACTIVE, DISABLED, PENDING;

    public static boolean isActive(Status status){
        return Objects.equals(status, ACTIVE);
    }

    public static boolean isPending(Status status){
        return Objects.equals(status, PENDING);
    }

    public static boolean isDisabled(Status status){
        return Objects.equals(status, DISABLED);
    }
}
