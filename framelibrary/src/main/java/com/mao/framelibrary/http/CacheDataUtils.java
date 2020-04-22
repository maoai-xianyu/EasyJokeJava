package com.mao.framelibrary.http;

import com.mao.baselibrary.baseUtils.LogU;
import com.mao.framelibrary.db.DaoSupportFactory;
import com.mao.framelibrary.db.IDaoSupport;

import java.util.List;

/**
 * @author zhangkun
 * @time 2020-04-21 18:41
 * @Description
 */
public class CacheDataUtils {

    public static String getCacheHttpRequestResultJson(String url) {
        final IDaoSupport<CacheData> daoSupport = DaoSupportFactory.getInstance().getIDaoSupport(CacheData.class);
        List<CacheData> cacheDataList = daoSupport.querySupport()
                .selection("mUrlKey = ?")
                .selectionArgs(url).query();
        if (cacheDataList.size() != 0) {
            // 有数据
            CacheData cacheData = cacheDataList.get(0);
            String cacheResultJson = cacheData.getResultJson();
            return cacheResultJson;
        }
        return null;
    }

    public static long cacheData(String url, String resultJson) {
        final IDaoSupport<CacheData> daoSupport = DaoSupportFactory.getInstance().getIDaoSupport(CacheData.class);
        daoSupport.delete("mUrlKey = ?", url);
        long number = daoSupport.insert(new CacheData(url, resultJson));
        LogU.d("缓存数据插入 number ---> "+number);
        return number;
    }
}
