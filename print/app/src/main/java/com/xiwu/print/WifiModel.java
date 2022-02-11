package com.xiwu.print;

import android.net.wifi.ScanResult;

/**
 * Created by by.huang on 2016/11/26.
 */

public class WifiModel {
    public ScanResult scanResult;
    public boolean isConnect;
    public boolean isSelect;
    public boolean isNeedPsw;


    public static WifiModel buildModel(ScanResult scanResult, boolean isConnect,boolean isSelect) {
        WifiModel model = new WifiModel();
        model.scanResult = scanResult;
        model.isConnect = isConnect;
        model.isSelect = isSelect;
        return model;
    }

    public ScanResult getScanResult() {
        return scanResult;
    }

    public void setScanResult(ScanResult scanResult) {
        this.scanResult = scanResult;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
