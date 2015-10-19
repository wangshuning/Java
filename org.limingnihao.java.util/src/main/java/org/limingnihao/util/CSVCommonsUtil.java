package org.limingnihao.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.limingnihao.model.FileBean;
import org.limingnihao.model.SortBean;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishiming on 15/10/16.
 */
public class CSVCommonsUtil {

    FileWriter fileWriter = null;

    //CSV文件头
    private static final Object [] FILE_HEADER = {"用户名","密码","名称","年龄"};

    /**
     * 写CSV文件
     *
     * @param fileName
     */
    public static void writeCsvFile(String fileName) {
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        //创建 CSVFormat
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator('\n').withDelimiter(',').withQuote('"').withQuoteMode(QuoteMode.ALL);
        try {
            //初始化FileWriter
            fileWriter = new FileWriter(fileName);
            //初始化 CSVPrinter
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            //创建CSV文件头
            csvFilePrinter.printRecord(FILE_HEADER);

            // 用户对象放入List
            List<SortBean> userList = new ArrayList<SortBean>();
            userList.add(new SortBean("zhangsan", "123456"));
            userList.add(new SortBean("lisi", "123"));
            userList.add(new SortBean("wangwu", "456"));
            userList.add(new SortBean("zhaoliu", "zhaoliu"));

            // 遍历List写入CSV
            for (SortBean user : userList) {
                List<String> userDataRecord = new ArrayList<String>();
                userDataRecord.add(user.getDirection());
                userDataRecord.add(user.getProperty());
                csvFilePrinter.printRecord(user);
            }
            System.out.println("CSV文件创建成功~~~");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args){
        writeCsvFile("/Volumes/Software/Syn/users.csv");
    }

}
