package exceldemo;

import com.alibaba.excel.EasyExcel;

/**
 * EasyExcel 读操作
 */
public class EasyExcelReadDemo {

    public static void main(String[] args) {
        String fileName = "H:\\在线教育--谷粒学院\\项目笔记课件\\day06\\随堂笔记\\writeDemo.xlsx";

        EasyExcel.read(fileName, ReadData.class, new ExcelListener()).sheet().doRead();
    }
}
