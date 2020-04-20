package com.mao.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author zhangkun
 * @time 2020-04-20 13:51
 * @Description
 */
public interface IDaoSupport<T> {

    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz);

    // 插入数据
    public int insert(T t);
}
