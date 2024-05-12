package chat.utils;

import java.time.format.DateTimeFormatter;

public class ServerConfiguration {
    public static final int SERVER_PORT = 8000;
    public static final String SERVER_NAME = "localhost";
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

}
