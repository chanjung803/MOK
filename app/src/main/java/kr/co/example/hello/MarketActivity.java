package kr.co.example.hello;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MarketActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market);  // 올바른 레이아웃 파일 사용

        // "이전" 버튼 연결 및 클릭 리스너 설정
        Button btnBack = findViewById(R.id.BtnBackMarket);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 액티비티를 종료하고 이전 화면으로 돌아감
                finish();
            }
        });
    }
}