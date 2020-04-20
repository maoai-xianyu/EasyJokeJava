package com.mao.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * @author zhangkun
 * @time 2020-04-20 13:51
 * @Description
 */
public interface IDaoSupport<T> {

    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz);

    // 插入数据
    public long insert(T t);

    // 批量插入 检测性能
    public void insert(List<T> datas);
}
