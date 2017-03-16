package com.example.xheng.welfaresociety.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.ui.fragment.NewGoodsFragment;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BoutiqueChildActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_child);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_bc, new NewGoodsFragment())
                .commit();
        mTextView.setText(getIntent().getStringExtra(I.Boutique.NAME));
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        MFGT.finish(BoutiqueChildActivity.this);
    }
}
