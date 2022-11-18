package uz.enterprise.mytex.constant;

public interface MessageKey {
    String UNAUTHORIZED = "invalid.token";
    String USER_DOES_NOT_EXIST = "user.does.not.exists";
    String USERNAME_EXISTS = "username.exists.in.the.system";
    String EMAIL_EXISTS = "email.exists.in.the.system";
    String INCORRECT_PASSWORD = "incorrect.password";
    String DATA_NOT_FOUND = "data.not.found";
    String INTERNAL_SERVER_ERROR = "internal.server.error";
    String INVALID_DATA = "invalid.data";
    String SUCCESS = "success";
    String ACCOUNT_PENDING = "auth.account.pending";
    String ACCOUNT_BLOCKED = "auth.account.blocked";
    String DEVICE_BLOCKED = "device.blocked";
    String SESSION_DISABLED = "session.disabled";
    String FORBIDDEN = "forbidden";
    String MAX_SIZE = "max.size.file";
    String DEVICE_ALREADY_BLOCKED = "device.already.blocked";
    String DEVICE_ALREADY_UNBLOCKED = "device.already.unblocked";
    String ACCOUNT_ALREADY_BLOCKED = "account.already.blocked";
    String ACCOUNT_ALREADY_UNBLOCKED = "account.already.unblocked";
    String DEVICE_NOT_BLOCKED = "device.not.blocked";
    String ACCOUNT_NOT_BLOCKED = "account.not.blocked";
}
