package com.mao.framelibrary.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.collection.ArrayMap;

import com.mao.baselibrary.baseUtils.LogU;
import com.mao.framelibrary.db.curd.QuerySupport;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhangkun
 * @time 2020-04-20 13:57
 * @Description
 */
public class DaoSupport<T> implements IDaoSupport<T> {


    private SQLiteDatabase mSQLiteDatabase;

    private Class<T> mClazz;

    private static final Object[] mPutMethodArgs = new Object[2];
    private static final Map<String, Method> mPutMethods = new ArrayMap<>();

    private QuerySupport<T> mQuerySupport;


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

                // 方法使用反射，反射在一定程度上会影响性能
                // 源码里面  activity的创建用到了反射   View创建用到了反射
                // 第三方以及源码给我们提供的最好的学习  AppCompatViewInflater
                mPutMethodArgs[0] = key;
                mPutMethodArgs[1] = value;

                // 还是使用反射 获取方法 put   public void put(String key, Boolean value)

                String fieldTypeName = field.getType().getName();
                Method putMethod = mPutMethods.get(fieldTypeName);
                if (putMethod == null) {
                    putMethod = ContentValues.class.getDeclaredMethod("put", String.class, value.getClass());
                    mPutMethods.put(fieldTypeName, putMethod);
                }
                // Method putMethod = ContentValues.class.getDeclaredMethod("put", String.class, value.getClass());
                // 通过反射执行
                // putMethod.invoke(values, key, value);
                putMethod.invoke(values, mPutMethodArgs);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mPutMethodArgs[0] = null;
                mPutMethodArgs[1] = null;
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

    @Override
    public QuerySupport<T> querySupport() {
        if (mQuerySupport == null) {
            mQuerySupport = new QuerySupport<>(mSQLiteDatabase, mClazz);
        }
        return mQuerySupport;
    }

    /**
     * 删除
     */
    @Override
    public int delete(String whereClause, String[] whereArgs) {
        return mSQLiteDatabase.delete(DaoUtil.getTableName(mClazz), whereClause, whereArgs);
    }

    /**
     * 更新  这些你需要对  最原始的写法比较明了 extends
     */
    @Override
    public int update(T obj, String whereClause, String... whereArgs) {
        ContentValues values = contentValuesByObj(obj);
        return mSQLiteDatabase.update(DaoUtil.getTableName(mClazz),
                values, whereClause, whereArgs);
    }

    // 结合到
    // 1. 网络引擎的缓存
    // 2. 资源加载的源码NDK

    @Override
    public List<T> queryAll() {
        Cursor cursor = mSQLiteDatabase.query(DaoUtil.getTableName(mClazz), null, null, null, null, null, null);
        return cursorToList(cursor);
    }

    /**
     * 通过Cursor封装成查找对象
     *
     * @return 对象集合列表
     */
    private List<T> cursorToList(Cursor cursor) {
        List<T> list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    T instance = mClazz.newInstance();
                    Field[] fields = mClazz.getDeclaredFields();
                    for (Field field : fields) {
                        // 遍历属性
                        field.setAccessible(true);
                        String name = field.getName();
                        // 获取角标
                        int index = cursor.getColumnIndex(name);
                        if (index == -1) {
                            continue;
                        }
                        // 通过反射获取 游标的方法 field.getType() 获取类型
                        Method cursorMethod = cursorMethod(field.getType());
                        if (cursorMethod != null) {
                            Object value = cursorMethod.invoke(cursor, index);
                            if (value == null) {
                                continue;
                            }

                            // 处理一些特殊的部分
                            if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                                if ("0".equals(String.valueOf(value))) {
                                    value = false;
                                } else if ("1".equals(String.valueOf(value))) {
                                    value = true;
                                }
                            } else if (field.getType() == char.class || field.getType() == Character.class) {
                                value = ((String) value).charAt(0);
                            } else if (field.getType() == Date.class) {
                                long date = (Long) value;
                                if (date <= 0) {
                                    value = null;
                                } else {
                                    value = new Date(date);
                                }
                            }
                            field.set(instance, value);
                        }
                    }
                    // 加入集合
                    list.add(instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 获取游标
     * @param type
     * @return
     * @throws Exception
     */
    private Method cursorMethod(Class<?> type) throws Exception {
        String methodName = getColumnMethodName(type);
        Method method = Cursor.class.getMethod(methodName, int.class);
        return method;
    }

    private String getColumnMethodName(Class<?> fieldType) {
        String typeName;
        if (fieldType.isPrimitive()) {
            typeName = DaoUtil.capitalize(fieldType.getName());
        } else {
            typeName = fieldType.getSimpleName();
        }
        String methodName = "get" + typeName;
        if ("getBoolean".equals(methodName)) {
            methodName = "getInt";
        } else if ("getChar".equals(methodName) || "getCharacter".equals(methodName)) {
            methodName = "getString";
        } else if ("getDate".equals(methodName)) {
            methodName = "getLong";
        } else if ("getInteger".equals(methodName)) {
            methodName = "getInt";
        }
        return methodName;
    }
}
