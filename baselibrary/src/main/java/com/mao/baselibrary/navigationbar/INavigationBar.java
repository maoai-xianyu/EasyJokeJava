package com.mao.baselibrary.navigationbar;

/**
 * @author zhangkun
 * @time 2020-04-19 16:19
 * @Description 导航条规范
 */
public interface INavigationBar {
    /**
     * 头部规范
     * @return
     */

    public int bindLayoutId();


    /**
     * 绑定头部的参数
     */
    public void applyView();
}
