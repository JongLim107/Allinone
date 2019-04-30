package com.example.allinone.data.source;

/**
 * Created by Jong Lim on 19/4/19.
 */
public interface ILocalDataSource {
    /**
     * 保存用户名
     */
    void saveUserName(String userName);

    /**
     * 保存用户密码
     */

    void savePassword(String password);

    /**
     * 获取用户名
     */
    String getUserName();

    /**
     * 获取用户密码
     */
    String getPassword();
}
