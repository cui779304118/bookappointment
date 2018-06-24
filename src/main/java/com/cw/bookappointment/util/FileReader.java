package com.cw.bookappointment.util;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileReader {
    public void read(Callback callback) throws IOException;
    public void read(String path);
    public void logRecord(String path, String str);
}
