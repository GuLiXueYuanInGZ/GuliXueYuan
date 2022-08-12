package exceldemo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 读操作监听器
 */
public class ExcelListener extends AnalysisEventListener<ReadData> {

    // 创建list集合封装最终的数据
    List<ReadData> list = new ArrayList<ReadData>();

    // 一行一行去读取 excel 内容
    @Override
    public void invoke(ReadData readData, AnalysisContext analysisContext) {
        System.out.println("***"+readData);
        list.add(readData);
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
