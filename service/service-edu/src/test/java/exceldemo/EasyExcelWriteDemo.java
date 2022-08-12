package exceldemo;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyExcel 写操作
 */
public class EasyExcelWriteDemo {
    public static void main(String[] args) {
        String fileName = "H:\\在线教育--谷粒学院\\项目笔记课件\\day06\\随堂笔记\\writeDemo.xlsx";

        EasyExcel.write(fileName, DemoData.class).sheet("写操作").doWrite(data());
    }

    //循环设置要添加的数据，最终封装到list集合中
    private static List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("张三"+i);
            list.add(data);
        }
        return list;
    }
}
