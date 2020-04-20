package com.mao.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * @author zhangkun
 * @time 2020-04-20 14:07
 * @Description 单例模式
 */
public class DaoSupportFactory {

    private static DaoSupportFactory mFactory = null;


    // 持有外部数据库的引用
    private SQLiteDatabase mSQLiteDatabase;

    private DaoSupportFactory() {
        // 把数据库放到内存卡里  判断是否有存储卡， 6.0 要动态申请权限
        File dbRoot = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "easyJoke" + File.separator + "database");
        if (!dbRoot.exists()) {
            dbRoot.mkdirs();
        }

        File dbFile = new File(dbRoot, "easyJoke.db");

        // 打开或者创建一个数据库

        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, null);

    }

    public static DaoSupportFactory getInstance() {
        if (mFactory == null) {
            synchronized (DaoSupportFactory.class) {
                if (mFactory == null) {
                    mFactory = new DaoSupportFactory();
                }
            }
        }
        return mFactory;
    }

    public <T> IDaoSupport<T> getIDaoSupport(Class<T> clazz) {
        IDaoSupport iDaoSupport = new DaoSupport();
        iDaoSupport.init(mSQLiteDatabase, clazz);
        return iDaoSupport;
    }
}
