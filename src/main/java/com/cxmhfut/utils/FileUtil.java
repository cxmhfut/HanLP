package com.cxmhfut.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private final static int FILE_TYPE_FILE = 0;
    private final static int FILE_TYPE_DIR = 1;

    /**
     * 读取文件
     */
    public static String readFile(String filename) {
        File file = new File(filename);
        StringBuilder text = new StringBuilder();
        try {
            String line;
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            while ((line = buffer.readLine()) != null) {
                text.append(line);
            }
            buffer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return text.toString();
    }

    public static List<String> listSubDirNames(String dirName) {
        return listSubFileNames(dirName, FILE_TYPE_DIR);
    }

    public static List<String> listSubFileNames(String dirName) {
        return listSubFileNames(dirName, FILE_TYPE_FILE);
    }

    private static List<String> listSubFileNames(String dirName, int FILE_TYPE) {

        //如果dirName不以文件分隔符结尾，自动添加文件分隔符
        if (dirName.endsWith(File.separator)) {
            dirName = dirName + File.separator;
        }

        File dirFile = new File(dirName);

        //如果dir对应的文件不存在，或者不是一个文件夹则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            System.err.println("找不到文件目录:" + dirName);
            return null;
        }

        File[] files = dirFile.listFiles();

        if (files == null) {
            return null;
        }

        List<String> fileNames = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                if (FILE_TYPE == FILE_TYPE_FILE)
                    fileNames.add(file.getAbsolutePath());
            } else {
                if (FILE_TYPE == FILE_TYPE_DIR)
                    fileNames.add(file.getAbsolutePath());
            }
        }

        return fileNames;
    }
}
