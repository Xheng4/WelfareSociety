package com.example.xheng.welfaresociety.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderForGoodsActivity extends AppCompatActivity {

    @BindView(R.id.et_order_username)
    EditText mEtOrderUsername;
    @BindView(R.id.et_order_phone_num)
    EditText mEtOrderPhoneNum;
    @BindView(R.id.spinner_order_area)
    Spinner mSpinnerOrderArea;
    @BindView(R.id.et_order_address)
    EditText mEtOrderAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_for_goods);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.iv_order_back, R.id.btn_order_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_order_back:
                MFGT.finish(this);
                break;
            case R.id.btn_order_pay:
                
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
        if (!username.matches("^1[3|4|5|7|8][0-9]{9}$")) {
            mEtOrderPhoneNum.requestFocus();
            mEtOrderPhoneNum.setError("请输入正确的手机号码");
            return false;
        }

        return true;
    }
}
