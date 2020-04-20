package com.mao.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

import com.mao.baselibrary.baseUtils.LogU;

import java.lang.reflect.Field;

/**
 * @author zhangkun
 * @time 2020-04-20 13:57
 * @Description
 */
public class DaoSupport<T> implements IDaoSupport<T> {


    private SQLiteDatabase mSQLiteDatabase;

    private Class<T> mClazz;


    @Override
    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz) {
        this.mSQLiteDatabase = sqLiteDatabase;
        this.mClazz = clazz;
        // 创建表
        /*"create table if not exists Person ("
                + "id integer primary key autoincrement, "
                + "name text, "
                + "age integer, "
                + "flag boolean)";*/

        StringBuffer sb = new StringBuffer();

        sb.append("create table if not exists ")
                .append(DaoUtil.getTableName(mClazz))
                .append("(id integer primary key autoincrement, ");

        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);// 设置权限
            String name = field.getName();
            String type = field.getType().getSimpleName();// int String boolean
            //  type需要进行转换 int --> integer, String text;
            sb.append(name).append(DaoUtil.getColumnType(type)).append(", ");
        }

        sb.replace(sb.length() - 2, sb.length(), ")");

        String createTableSql = sb.toString();

        LogU.d("表语句--> "+createTableSql);

        // 创建表
        mSQLiteDatabase.execSQL(createTableSql);

    }

    /**
     * 插入数据
     *
     * @param o
     * @return
     */
    @Override
    public int insert(T o) {
        /*ContentValues values = new ContentValues();
        values.put("name",person.getName());
        values.put("age",person.getAge());
        values.put("flag",person.getFlag());
        db.insert("Person",null,values);*/
        return 0;
    }
}
