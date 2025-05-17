package com.yf.exam.modules.sys.system.service;

/**
 * 数据字典工具类
 */
public interface SysDictService {

    /**
     * 查找数据字典
     * @param table 表名
     * @param text  显示字段名
     * @param key   键字段名
     * @param value 键字段对应的值
     * @return 显示字段内容
     */
    String findDict(String table,
                    String text,
                    String key,
                    String value);
}