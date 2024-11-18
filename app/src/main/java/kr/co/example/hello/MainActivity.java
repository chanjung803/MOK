package kr.co.example.hello;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnSetting;
    Button btnReserveClass;
    private TextView footerTextView; // Footer에 예약 정보를 표시할 TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("예약 시작");

        // 버튼 연결
        btnSetting = findViewById(R.id.btnSetting);
        btnReserveClass = findViewById(R.id.btnReserveClass); // 예약 버튼

        // Footer TextView 연결
        footerTextView = findViewById(R.id.footerTextView);

        // Intent로 전달된 예약 정보를 가져옴
        Intent intent = getIntent();
        String selectedDate = intent.getStringExtra("selectedDate");
        String selectedRoom = intent.getStringExtra("selectedRoom");
        String selectedTime = intent.getStringExtra("selectedTime");

        // 예약 정보가 있을 경우 Footer에 표시
        if (selectedDate != null && selectedRoom != null && selectedTime != null) {
            footerTextView.setText(selectedDate + " " + selectedRoom + " " + selectedTime);
        }

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