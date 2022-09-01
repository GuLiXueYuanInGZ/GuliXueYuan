package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author int-128
 * @since 2022-08-30
 */
public interface PayLogService extends IService<PayLog> {

    Map createNatvie(String orderNo);

    //订单支付状态
    Map<String, String> queryPayStatus(String orderNo);

    //添加记录、更新
    void updateOrderStatus(Map<String, String> map);

}
