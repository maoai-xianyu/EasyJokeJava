package com.mao.baselibrary.baseUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public class GsonU {


    /**
     * String jsonData = GsonU.string(collect);
     *
     * @param o
     * @return
     */
    public static String string(Object o) {
        Gson gson = (new GsonBuilder()).setLenient().create();
        return gson.toJson(o);
    }

    /**
     * List<CourseHistoryDate> list = GsonU.getListByKey(s,"course_date", new TypeToken<ArrayList<CourseHistoryDate>>() {
     * }.getType());
     *
     * @param resource
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getListByKey(String resource, String key, Type type) {
        Gson gson = (new GsonBuilder()).setLenient().create();
        String value = BaseJson.getString(resource, key);
        return !StringU.isEmpty(value) && !StringU.equals("[]", value.replace(" ", "")) ? (T) gson.fromJson(value, type) : null;
    }

    /**
     * AdvertisementInfo advertisementInfo = GsonU.getBeanByKey(adContent, KeyMaps.AD.INFO_KEY, AdvertisementInfo.class);
     *
     * @param resource
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getBeanByKey(String resource, String key, Type type) {
        Gson gson = (new GsonBuilder()).setLenient().create();
        String value = BaseJson.getString(resource, key);
        return StringU.isEmpty(value) ? null : (T) gson.fromJson(value, type);
    }

    /**
     * MainMedal medal = GsonU.convert(data, MainMedal.class);
     *
     * @param str
     * @param cls
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T convert(String str, Class<T> cls) throws JsonSyntaxException {
        Gson gson = (new GsonBuilder()).setLenient().create();
        return gson.fromJson(str, cls);
    }

    /**
     * List<ReviewClassReportProblem> problems = GsonU.convert(problemsStr, new TypeToken<List<ReviewClassReportProblem>>() {
     * }.getType());
     *
     * @param str
     * @param type
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T convert(String str, Type type) throws JsonSyntaxException {
        Gson gson = (new GsonBuilder()).setLenient().create();
        return gson.fromJson(str, type);
    }
}