package com.fxly.monkeytest;

/**
 * Created by Lambert Liu on 2016-12-30.
 */

import java.io.*;

/**
 * Java读取相对路径的文件
 *
 * @author leizhimin 2010-1-15 10:59:43
 */
public class ReadFile {
    public static void main(String[] args) {
        readTextA_ByClassPath();
        readTextA_ByProjectRelativePath();
        readTextB_ByProjectRelativePath();
    }

    /**
     * 通过工程相对路径读取（包内）文件，注意不以“/”开头
     */
    public static void readTextA_ByProjectRelativePath() {
        System.out.println("-----------------readTextA_ByProjectRelativePath---------------------");
        File f = new File("src/com/lavasoft/res/a.txt");
        String a = file2String(f, "GBK");
        System.out.println(a);
    }

    /**
     * 通过工程相对路径读取（包外）文件，注意不以“/”开头
     */
    public static void readTextB_ByProjectRelativePath() {
        System.out.println("-----------------readTextB_ByProjectRelativePath---------------------");
        File f = new File("doc/b.txt");
        String b = file2String(f, "GBK");
        System.out.println(b);
    }


    /**
     * 通过CLASSPATH读取包内文件，注意以“/”开头
     */
    public static void readTextA_ByClassPath() {
        System.out.println("-----------------readTextA_ByClassPath---------------------");
        InputStream in = ReadFile.class.getResourceAsStream("/com/lavasoft/res/a.txt");
        String a = stream2String(in, "GBK");
        System.out.println(a);
    }

    /**
     * 文件转换为字符串
     *
     * @param f             文件
     * @param charset 文件的字符集
     * @return 文件内容
     */
    public static String file2String(File f, String charset) {
        String result = null;
        try {
            result = stream2String(new FileInputStream(f), charset);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 文件转换为字符串
     *
     * @param in            字节流
     * @param charset 文件的字符集
     * @return 文件内容
     */
    public static String stream2String(InputStream in, String charset) {
        StringBuffer sb = new StringBuffer();
        try {
            Reader r = new InputStreamReader(in, charset);
            int length = 0;
            for (char[] c = new char[1024]; (length = r.read(c)) != -1;) {
                sb.append(c, 0, length);
            }
            r.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
