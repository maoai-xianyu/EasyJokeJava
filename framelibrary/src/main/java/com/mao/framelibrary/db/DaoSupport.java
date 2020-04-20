package com.mao.framelibrary.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.mao.baselibrary.baseUtils.LogU;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

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

        LogU.d("表语句--> " + createTableSql);

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
    public long insert(T o) {
        /*ContentValues values = new ContentValues();
        values.put("name",person.getName());
        values.put("age",person.getAge());
        values.put("flag",person.getFlag());
        db.insert("Person",null,values);*/


        // 使用的其实还是  原生的的使用方式， 封装一下
        ContentValues values = contentValuesByObj(o);
        // null 速度比第三方的快一倍左右
        return mSQLiteDatabase.insert(DaoUtil.getTableName(mClazz), null, values);
    }

    private ContentValues contentValuesByObj(T obj) {
        // 第三方的 使用对比一下 了解下源码
        ContentValues values = new ContentValues();
        // 封装values
        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            // 设置权限，私有和公有都可以访问
            field.setAccessible(true);
            try {
                String key = field.getName();
                // 获取 value
                Object value = field.get(obj);
                // put 第二个参数是类型 把它转化

                // 还是使用反射 获取方法 put   public void put(String key, Boolean value)
                Method putMethod = ContentValues.class.getDeclaredMethod("put", String.class, value.getClass());
                // 通过反射执行
                putMethod.invoke(values, key, value);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return values;
    }

    @Override
    public void insert(List<T> datas) {
        // 批量插入，采用事务
        mSQLiteDatabase.beginTransaction();
        for (T data : datas) {
            insert(data);
        }
        mSQLiteDatabase.setTransactionSuccessful();
        mSQLiteDatabase.endTransaction();



    }
}
