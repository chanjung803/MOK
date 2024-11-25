package kr.co.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnReserveClass;
    private TextView footerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnReserveClass = findViewById(R.id.btnReserveClass);
        footerTextView = findViewById(R.id.footerTextView);

        Intent intent = getIntent();
        String selectedRoom = intent.getStringExtra("selectedRoom");
        String selectedTime = intent.getStringExtra("selectedTime");
        int addedCount = intent.getIntExtra("addedCount", 0);

        if (selectedRoom != null && selectedTime != null) {
            footerTextView.setText(selectedRoom + " " + selectedTime + " 예약: " + addedCount + "명");
        } else {
            footerTextView.setText("예약된 정보가 없습니다.");
        }

        btnReserveClass.setOnClickListener(v -> {
            Intent classIntent = new Intent(MainActivity.this, ClassChoiceActivity.class);
            startActivity(classIntent);
        });
    }

}
