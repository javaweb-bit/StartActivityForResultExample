package com.example.startactivityforresultexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //  요청 코드 상수
    private static final int REQUEST_SELECT_RESULT = 1;
    private static final int REQUEST_SELECT_CONTACT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View btnSelect = findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  SelectActivity를 호출 결과
                Intent intent = new Intent(MainActivity.this,
                        SelectActivity.class);
                //  결과를 받기 위한 인텐트 호출 startActivityForResult
                //  결과는 onActivityResult로 콜백된다
                startActivityForResult(intent, REQUEST_SELECT_RESULT);   //  호출시 요청 코드 전달 -> 콜백으로 돌아온다
            }
        });
    }
}