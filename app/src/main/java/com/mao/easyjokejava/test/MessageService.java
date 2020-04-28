package com.mao.easyjokejava.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.mao.easyjokejava.UserAidl;

/**
 * @author zhangkun
 * @time 2020-04-27 22:16
 * @Description
 */
public class MessageService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mUserAidl.asBinder();
    }


    private final UserAidl mUserAidl = new UserAidl.Stub() {
        @Override
        public String getUsername() throws RemoteException {
            return "zhangkun@boxfish.cn";
        }

        @Override
        public String getUserPsw() throws RemoteException {
            return "test11111";
        }
    };

}
