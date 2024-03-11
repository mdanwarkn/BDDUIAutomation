package common;

import java.io.File;
import java.time.format.DateTimeFormatter;

public class StaticClass {

    public static final int SHORT_TIMEOUT = 10;
    public static final int MEDIUM_TIMEOUT = 30;
    public static final int LONG_TIMEOUT = 60;

    public static final int IMPLICIT_TIMEOUT = 5;

    public static final String INPUT_SHEET_PATH = System.getProperty("user.dir")+ File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator;

    public static final DateTimeFormatter DATE_TIME_FORMATTER_FORMAT1 = DateTimeFormatter.ofPattern("d MMMM yyyy");

    public static final DateTimeFormatter DATE_TIME_FORMATTER_FORMAT2 = DateTimeFormatter.ofPattern("MMM dd yyyy");



}
