package com.example.xheng.welfaresociety.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.ui.widget.MFGT;
import com.pingplusplus.android.Pingpp;
import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PaymentHandler;
import com.pingplusplus.libone.PingppOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderForGoodsActivity extends AppCompatActivity {
    private static String URL = "http://218.244.151.190/demo/charge";

    @BindView(R.id.et_order_username)
    EditText mEtOrderUsername;
    @BindView(R.id.et_order_phone_num)
    EditText mEtOrderPhoneNum;
    @BindView(R.id.spinner_order_area)
    Spinner mSpinnerOrderArea;
    @BindView(R.id.et_order_address)
    EditText mEtOrderAddress;

    Pingpp mPingpp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_for_goods);
        ButterKnife.bind(this);

        //设置需要使用的支付方式
        PingppOne.enableChannels(new String[]{"wx", "alipay", "upacp", "bfb", "jdpay_wap"});

        // 提交数据的格式，默认格式为json
        // PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";

        PingppLog.DEBUG = true;

    }

    @OnClick({R.id.iv_order_back, R.id.btn_order_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_order_back:
                MFGT.finish(this);
                break;
            case R.id.btn_order_pay:
                if (!checkData()) {
                    return;
                }

                String orderNo = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

                // 计算总金额（以分为单位）
                int amount = 100;
                /*JSONArray billList = new JSONArray();
                for (Goods good : mList) {
                    amount += good.getPrice() * good.getCount() * 100;
                    billList.put(good.getName() + " x " + good.getCount());
                }*/
                // 构建账单json对象
                JSONObject bill = new JSONObject();

                // 自定义的额外信息 选填
                JSONObject extras = new JSONObject();
                try {
                    extras.put("extra1", "extra1");
                    extras.put("extra2", "extra2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    bill.put("order_no", orderNo);
                    bill.put("amount", amount);
                    bill.put("extras", extras);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //壹收款: 创建支付通道的对话框
                PingppOne.showPaymentChannels(this, bill.toString(), URL, new PaymentHandler() {
                    @Override
                    public void handlePaymentResult(Intent data) {
                        if (data != null) {
                            /**
                             * code：支付结果码  -2:服务端错误、 -1：失败、 0：取消、1：成功
                             * error_msg：支付结果信息
                             */
                            int code = data.getExtras().getInt("code");
                            String result = data.getExtras().getString("result");
                        }
                    }
                });
                break;
        }
    }

    private boolean checkData() {
        String username = mEtOrderUsername.getText().toString().trim();
        String phoneNum = mEtOrderPhoneNum.getText().toString().trim();

        String address = mEtOrderAddress.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            mEtOrderUsername.requestFocus();
            mEtOrderUsername.setError("收件人不能为空");
            return false;
        }
        if (TextUtils.isEmpty(address)) {
            mEtOrderAddress.requestFocus();
            mEtOrderAddress.setError("地址不能为空");
            return false;
        }
        if (TextUtils.isEmpty(phoneNum)) {
            mEtOrderPhoneNum.requestFocus();
            mEtOrderPhoneNum.setError("手机号不能为空");
            return false;
        }

        // ^1[3|4|5|7|8][0-9]{9}$
//        if (!username.matches("[0-9]{13}")) {
//            mEtOrderPhoneNum.requestFocus();
//            mEtOrderPhoneNum.setError("请输入正确的手机号码");
//            return false;
//        }

        return true;
    }
}
