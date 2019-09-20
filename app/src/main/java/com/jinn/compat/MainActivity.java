package com.jinn.compat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jinn.jbutton.weight.JButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View btn1;
    private View btn2;
    private View btn3;
    private View btn4;
    private View btn5;
    private View btn6;
    private View btn7;
    private View btn8;
    private JButton btn9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                break;
            case R.id.btn2:
                break;
            case R.id.btn3:
                break;
            case R.id.btn4:
                break;
            case R.id.btn5:
                break;
            case R.id.btn6:
                toast("想得美");
                break;
            case R.id.btn7:
                toast("谢谢");
                btn9.setText("可禁用");
                btn9.setEnabled(true);
                break;
            case R.id.btn8:
                break;
            case R.id.btn9:
                btn9.setText("我被禁用了");
                btn9.setEnabled(false);
                break;
        }
    }


    private void toast(String word){
        Toast.makeText(this,word,Toast.LENGTH_LONG).show();
    }
}
