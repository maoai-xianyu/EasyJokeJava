package com.mao.baselibrary.baseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseJson {
    public BaseJson() {
    }

    public static String getString(String resource, String key) {
        return getString(resource, key, (String)null);
    }

    public static String getString(String resource, String key, String value) {
        if (StringU.isEmpty(resource)) {
            return value;
        } else if (StringU.isEmpty(key)) {
            return value;
        } else {
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(resource);
                return !jsonObject.has(key) ? value : jsonObject.getString(key);
            } catch (JSONException var5) {
                return value;
            }
        }
    }

    public static JSONArray getJsonArray(String resource, String key) {
        JSONObject jsonObject = null;
        if (StringU.isEmpty(resource)) {
            return null;
        } else if (StringU.isEmpty(key)) {
            return null;
        } else {
            try {
                jsonObject = new JSONObject(resource);
                return !jsonObject.has(key) ? null : jsonObject.getJSONArray(key);
            } catch (JSONException var4) {
                return null;
            }
        }
    }

    public static long getLong(String resource, String key) {
        return getLong(resource, key, 0L);
    }

    public static long getLong(String resource, String key, long value) {
        if (StringU.isEmpty(resource)) {
            return value;
        } else if (StringU.isEmpty(key)) {
            return value;
        } else {
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(resource);
                return !jsonObject.has(key) ? value : jsonObject.getLong(key);
            } catch (JSONException var6) {
                return value;
            }
        }
    }

    public static int getInt(String resource, String key) {
        return getInt(resource, key, 0);
    }

    public static int getInt(String resource, String key, int value) {
        if (StringU.isEmpty(resource)) {
            return value;
        } else if (StringU.isEmpty(key)) {
            return value;
        } else {
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(resource);
                return !jsonObject.has(key) ? value : jsonObject.getInt(key);
            } catch (JSONException var5) {
                return value;
            }
        }
    }

    public static boolean getBoolean(String resource, String key) {
        return getBoolean(resource, key, false);
    }

    public static boolean getBoolean(String resource, String key, boolean value) {
        if (StringU.isEmpty(resource)) {
            return value;
        } else if (StringU.isEmpty(key)) {
            return value;
        } else {
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(resource);
                return !jsonObject.has(key) ? value : jsonObject.getBoolean(key);
            } catch (JSONException var5) {
                return value;
            }
        }
    }

    public static double getDouble(String resource, String key) {
        return getDouble(resource, key, 0.0D);
    }

    public static double getDouble(String resource, String key, double value) {
        if (StringU.isEmpty(resource)) {
            return value;
        } else if (StringU.isEmpty(key)) {
            return value;
        } else {
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(resource);
                return !jsonObject.has(key) ? value : jsonObject.getDouble(key);
            } catch (JSONException var6) {
                return value;
            }
        }
    }

    public static JSONObject getJSONObject(String resource, String key) {
        if (StringU.isEmpty(resource)) {
            return null;
        } else if (StringU.isEmpty(key)) {
            return null;
        } else {
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(resource);
                return !jsonObject.has(key) ? null : jsonObject.getJSONObject(key);
            } catch (JSONException var4) {
                return null;
            }
        }
    }

    public static JSONArray getJSONArray(String resource, String key) {
        if (StringU.isEmpty(resource)) {
            return null;
        } else if (StringU.isEmpty(key)) {
            return null;
        } else {
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(resource);
                return !jsonObject.has(key) ? null : jsonObject.getJSONArray(key);
            } catch (JSONException var4) {
                return null;
            }
        }
    }
}
