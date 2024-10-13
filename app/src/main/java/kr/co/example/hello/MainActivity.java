package kr.co.example.hello;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnSetting;
    Button btnReserveClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("예약 시작");

        // 버튼 연결
        btnSetting = findViewById(R.id.btnSetting);
        btnReserveClass = findViewById(R.id.btnReserveClass); // 예약 버튼

        // 강의실 예약 버튼 클릭 시 ClassChoiceActivity로 바로 이동
        btnReserveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ClassChoiceActivity로 이동
                Intent intent = new Intent(MainActivity.this, ClassChoiceActivity.class);
                startActivity(intent);
            }
        });
    }

}