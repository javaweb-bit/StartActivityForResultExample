package com.example.startactivityforresultexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    @Override
    public void onClick(View v) {
        //  누른 버튼의 id 확인
        int id = v.getId();
        String result = "?";

        switch(id) {
            case R.id.btnScissor:
                result = "가위";
                break;
            case R.id.btnRock:
                result = "바위";
                break;
            case R.id.btnPaper:
                result = "보";
                break;
        }

        //  결과를 돌려주려면 인텐트에 결과를 설정하고 종료
        Intent intent = getIntent();
        intent.putExtra("result", result);
        //  결과를 설정
        setResult(RESULT_OK, intent);
        finish();   //  호출한 액티비티의 onActivityResult로 콜백이 전달
    }
}