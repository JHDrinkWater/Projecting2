package com.ximalaya.ting.android.opensdk.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bun.miitmdid.core.ErrorCode;
import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.miitmdid.interfaces.IIdentifierListener;
import com.bun.miitmdid.interfaces.IdSupplier;

/**
 * Created by caict on 2020/6/8.
 */
public class DemoHelper implements IIdentifierListener {

    private AppIdsUpdater _listener;
    public DemoHelper(AppIdsUpdater callback){
        _listener=callback;

    }

    public void getDeviceIds(Context cxt){

        long timeb=System.currentTimeMillis();
        // 方法调用
        int nres = CallFromReflect(cxt);

        long timee=System.currentTimeMillis();
        long offset=timee-timeb;
        if(nres == ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT){//不支持的设备

        }else if( nres == ErrorCode.INIT_ERROR_LOAD_CONFIGFILE){//加载配置文件出错

        }else if(nres == ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT){//不支持的设备厂商

        }else if(nres == ErrorCode.INIT_ERROR_RESULT_DELAY){//获取接口是异步的，结果会在回调中返回，回调执行的回调可能在工作线程

        }else if(nres == ErrorCode.INIT_HELPER_CALL_ERROR){//反射调用出错

        }
        Log.d(getClass().getSimpleName(),"return value: "+String.valueOf(nres));

        System.out.println("DemoHelper.getDeviceIds  " + nres);
    }

    /*
     * 方法调用
     *
     * */
    private int CallFromReflect(Context cxt){
        return MdidSdkHelper.InitSdk(cxt,true,this);
    }

    /*
     * 获取相应id
     *
     * */
    @Override
    public void OnSupport(boolean isSupport, IdSupplier _supplier) {
        System.out.println("DemoHelper.OnSupport  " + isSupport);
        if(_supplier==null) {
            return;
        }

        System.out.println("DemoHelper.OnSupport  oaid = " + _supplier.getOAID());
        String oaid=_supplier.getOAID();
        if(_listener!=null){
            _listener.OnOaidAvalid(oaid);
        }
    }

    public interface AppIdsUpdater{
        void OnOaidAvalid(@NonNull String ids);
    }

}
