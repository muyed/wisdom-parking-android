package com.cn.climax.wisdomparking.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.widget.numberkeyboard.OfoKeyboard;

public class NewOfoKeyBoardActivity extends AppCompatActivity {

    private EditText editText;
    private Button changebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofo_number_keyboard);

        editText = (EditText) findViewById(R.id.et_numberplate);
        changebutton = (Button) findViewById(R.id.bt_change_keyboard);
        final OfoKeyboard keyboard = new OfoKeyboard(NewOfoKeyBoardActivity.this);
        changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboard.attachTo(editText, true);
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboard.attachTo(editText, false);

            }
        });
        keyboard.setOnOkClick(new OfoKeyboard.OnOkClick() {
            @Override
            public void onOkClick() {
                Log.i(">>>>>>", "点击了确定");
                Toast.makeText(NewOfoKeyBoardActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        keyboard.setOnCancelClick(new OfoKeyboard.OnCancelClcik() {
            @Override
            public void onCancelClick() {
                Toast.makeText(NewOfoKeyBoardActivity.this, "隐藏键盘", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
