package com.cxmhfut.utils;

import java.io.*;

public class FileUtil {
    /**
     * 读取文件
     */
    public static String readFile(String filename) {
        File file = new File(filename);
        StringBuilder text = new StringBuilder();
        try {
            String read;
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            while ((read = buffer.readLine()) != null) {
                text.append(read);
            }
            buffer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return text.toString();
    }
}
