package uz.enterprise.mytex.enums;

import java.util.Objects;

public enum BlockingStatus {
    BLOCKED, UNBLOCKED;

    public static boolean isBlocked(BlockingStatus status) {
        return Objects.equals(status, BLOCKED);
    }

    public static boolean isUnBlocked(BlockingStatus status) {
        return Objects.equals(status, UNBLOCKED);
    }
}
