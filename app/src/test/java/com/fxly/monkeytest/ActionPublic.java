package com.fxly.monkeytest;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Lambert Liu on 2016-12-30.
 */

public class ActionPublic {

    public static void main(String args){
        File file= new File("/sorage/emulated/0/data.txt");
     //   String[] strs=file2StringArray(file);

    }






    /**
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！)
     *
     * @param res            原字符串
     * @param filePath 文件路径
     * @return 成功标记
     */
    public static boolean string2File(String res, String filePath) {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }






//    从xml转化为document再到String
    public   static   String   xmlFile2String(String   fileName) throws SAXException,IOException,ParserConfigurationException,TransformerFactoryConfigurationError, TransformerException

    {



        DocumentBuilderFactory   documentBuilderFactory   =   DocumentBuilderFactory.newInstance();

        InputSource   inputSource   =   new InputSource(fileName);

        Document document   =   documentBuilderFactory.newDocumentBuilder().parse(inputSource);

        StringWriter   sw   =   new StringWriter();

        Transformer serializer   =   TransformerFactory.newInstance().newTransformer();

        serializer.transform(new DOMSource(document),   new StreamResult(sw));

        return   sw.toString();




//        doc转化为string：
//
//        DOMSource   domSource   =   new   DOMSource(doc);
//
//        StringWriter   writer   =   new   StringWriter();
//
//        StreamResult   result   =   new   StreamResult(writer);
//
//        TransformerFactory   tf   =   TransformerFactory.newInstance();
//
//        Transformer   transformer   =   tf.newTransformer();
//
//        transformer.transform(domSource,   result);
//
//        System.out.println(   writer.toString());
//
//        doc转化为xml：
//
//        TransformerFactory   tFactory   =   TransformerFactory.newInstance();
//
//        Transformer   transformer   =   tFactory.newTransformer();
//
//        DOMSource   source   =   new   DOMSource(doc);
//
//        FileOutputStream   outStream   =   new   FileOutputStream( "outFile.xml ");
//
//        StreamResult   result   =   new   StreamResult(outStream);
//
//        transformer.transform(source,   result);
    }

}
