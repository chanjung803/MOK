package kr.co.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReserveInfoActivity extends AppCompatActivity {

    private Spinner spinnerPersonCount;
    private LinearLayout linearLayoutPersonInfo;
    private TextView tvRoomAndTime;
    private TextView tvCurrentDate;
    private String selectedRoom, selectedTime, reservationKey;
    private int personCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserveinfo);

        // Intent로 전달받은 선택된 호수와 시간 정보를 가져오기
        Intent intent = getIntent();
        selectedRoom = intent.getStringExtra("selectedRoom");
        selectedTime = intent.getStringExtra("selectedTime");
        reservationKey = intent.getStringExtra("reservationKey");

        // 호수와 시간을 표시
        tvRoomAndTime = findViewById(R.id.tvRoomAndTime);
        tvRoomAndTime.setText(selectedRoom + " " + selectedTime);

        // 현재 날짜 표시
        tvCurrentDate = findViewById(R.id.tvCurrentDate);
        setCurrentDate();

        // Spinner와 동적으로 입력할 LinearLayout 연결
        spinnerPersonCount = findViewById(R.id.spinnerPersonCount);
        linearLayoutPersonInfo = findViewById(R.id.linearLayoutPersonInfo);

        spinnerPersonCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                personCount = position + 1; // 선택된 인원 수
                updatePersonFields(personCount);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        // 예약 확정 버튼
        Button btnConfirmReservation = findViewById(R.id.btnConfirmReservation);
        btnConfirmReservation.setOnClickListener(v -> {
            // 예약 확정
            Toast.makeText(this, "예약이 완료되었습니다.", Toast.LENGTH_SHORT).show();

            // MainActivity로 이동
            Intent resultIntent = new Intent(this, MainActivity.class);
            resultIntent.putExtra("reservationKey", reservationKey);
            resultIntent.putExtra("addedCount", personCount);
            resultIntent.putExtra("selectedRoom", selectedRoom);
            resultIntent.putExtra("selectedTime", selectedTime);
            startActivity(resultIntent);

            finish();
        });
    }

    private void updatePersonFields(int personCount) {
        linearLayoutPersonInfo.removeAllViews();

        for (int i = 0; i < personCount; i++) {
            EditText etName = new EditText(this);
            etName.setHint("이름 " + (i + 1));
            linearLayoutPersonInfo.addView(etName);

            EditText etStudentId = new EditText(this);
            etStudentId.setHint("학번 " + (i + 1));
            linearLayoutPersonInfo.addView(etStudentId);
        }
    }

    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 (E요일)", Locale.getDefault());
        tvCurrentDate.setText(dateFormat.format(calendar.getTime()));
    }
}
