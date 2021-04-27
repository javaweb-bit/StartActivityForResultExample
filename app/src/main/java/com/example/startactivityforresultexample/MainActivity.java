package com.example.startactivityforresultexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //  요청 코드 상수
    private static final int REQUEST_SELECT_RESULT = 1;
    private static final int REQUEST_SELECT_CONTACT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View btnAddress = findViewById(R.id.btnAddress);
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  암시적 인텐트로 주소록 호출
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(Uri.parse("content://com.android.contacts/data/phones"));
                //  실행 시킬 수 있는 인텐트인가?
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_SELECT_CONTACT);
                }
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode,    //  요청 코드
                                    int resultCode,     //  결과 코드 성공이면 RESULT_OK
                                    @Nullable Intent data) {    //  인텐트
        //  요청 코드 확인
        if (requestCode == REQUEST_SELECT_RESULT &&
            resultCode == RESULT_OK) {  //  성공
            String result = data.getStringExtra("result");
            Toast.makeText(MainActivity.this,
                    "선택결과:" + result,
                    Toast.LENGTH_LONG)
                    .show();
        } else if (requestCode == REQUEST_SELECT_CONTACT &&
                    resultCode == RESULT_OK) {  //  주소록을 선택했다
            Uri contactsUri = data.getData();
            Log.d("[Contacts]", contactsUri.toString());

            //  뽑아내고자 하는 필드 선택
            String[] projection = new String[] {
                    ContactsContract.Contacts._ID,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };

            //  질의 수행
            Cursor cursor = getContentResolver()
                    .query(contactsUri,
                            projection,
                            null,
                            null,
                            null);

            //  질의 결과가 있으면
            if (cursor != null && cursor.moveToFirst()) {
                //  레코드 인덱스 추출
                int numIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                //  컬럼 데이터 추출
                String phoneNumber = cursor.getString(numIndex);

                Toast.makeText(MainActivity.this,
                        "선택한 전화번호:" + phoneNumber,
                        Toast.LENGTH_LONG)
                        .show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}