package com.xiwu.print;

import android.Manifest;
import android.net.wifi.ScanResult;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1088;
    private WifiUtils mWifiUtil;
    private ListView mListView;
    private List<WifiModel> datas = new ArrayList<>();
    private TextView mResultTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final IntentFilter filter = new IntentFilter(WifiUtils.Action_NetWork_Success);
//        registerReceiver(mReceiver, filter);

        String[] permissions = new String[1];
        permissions[0] =  Manifest.permission.ACCESS_COARSE_LOCATION;
        ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQUEST_CODE);

        this.initWifi();
        this.initView();



    }

//    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            final String action = intent.getAction();
//           if (action.equalsIgnoreCase(WifiUtils.Action_NetWork_Success)) {
//               Log.i("by","网络连接成功");
//            }
//        }
//    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mReceiver);
    }



    private void initView(){

        mResultTxt = (TextView) findViewById(R.id.txt_result);

        Button connPrintBtn = (Button) findViewById(R.id.conn_print_btn);
        connPrintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this.connPrint();
            }
        });
        datas = generateDatas();
        //打印机ssid
        WifiModel printModel = new WifiModel();
        for(WifiModel model :datas){
            if(model.scanResult.SSID.equalsIgnoreCase("printer-AP")){
                printModel = model;
            }
        }
        //连接打印机wifi
        boolean result = mWifiUtil.connect(printModel.scanResult.SSID, WifiUtils.WifiCipherType.WIFICIPHER_WEP,"");
        mResultTxt.setText(result ? "连接打印机成功" : "连接打印机失败");

    }


    private List<WifiModel> generateDatas() {

        datas.clear();
        List<ScanResult> scanResults = mWifiUtil.getWifiList();
        if (scanResults != null && scanResults.size() > 0) {
            String connectSsid = mWifiUtil.getConnectWifiSsid();
            for (ScanResult scanResult : scanResults) {
                WifiModel model = WifiModel.buildModel(scanResult, false, false);
                if (connectSsid.equalsIgnoreCase(scanResult.SSID) && mWifiUtil.isWifiConnect()) {
                    model.isConnect = true;
                }
                boolean isNeedPsw = mWifiUtil.checkIsCurrentWifiHasPassword(scanResult);
                model.isNeedPsw = isNeedPsw;
                datas.add(model);
            }
        }
        return getResult(datas);
    }

    private List<WifiModel> getResult (List<WifiModel> datas)
    {
        for (int i = 0; i < datas.size() - 1; i++)
        {
            for (int j = i + 1; j < datas.size(); j++)
            {
                if (datas.get(i).scanResult.SSID.equalsIgnoreCase(datas.get(j).scanResult.SSID))
                {
                    datas.remove(j);
                    j--;
                }
            }
        }
        return datas;
    }

    private void initWifi(){
        mWifiUtil = WifiUtils.getInstance();
        mWifiUtil.init();
        int state = mWifiUtil.checkState();
        Log.i("by","当前wifi状态:"+state);
        mWifiUtil.openWifi();
        mWifiUtil.startScan();
    }


}
