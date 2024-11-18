package kr.co.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReserveInfoActivity extends AppCompatActivity {

    private Spinner spinnerPersonCount;
    private LinearLayout linearLayoutPersonInfo;
    private TextView tvRoomAndTime;
    private TextView tvCurrentDate;
    private String selectedRoom, selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserveinfo);  // reserveinfo.xml과 연결

        // 뒤로가기 버튼 설정
        Button btnBackReserve = findViewById(R.id.btnBackReserve);
        btnBackReserve.setOnClickListener(v -> finish()); // 현재 액티비티 종료

        // Intent로 전달받은 선택된 호수와 시간 정보를 가져오기
        Intent intent = getIntent();
        selectedRoom = intent.getStringExtra("selectedRoom");
        selectedTime = intent.getStringExtra("selectedTime");

        // 호수와 시간을 표시할 TextView 연결
        tvRoomAndTime = findViewById(R.id.tvRoomAndTime);
        tvRoomAndTime.setText(selectedRoom + " " + selectedTime);  // 호수와 시간 정보 표시

        // 현재 날짜를 표시할 TextView 연결
        tvCurrentDate = findViewById(R.id.tvCurrentDate);
        setCurrentDate(); // 현재 날짜 설정

        // Spinner와 동적으로 입력할 LinearLayout 연결
        spinnerPersonCount = findViewById(R.id.spinnerPersonCount);
        linearLayoutPersonInfo = findViewById(R.id.linearLayoutPersonInfo);

        // Spinner 선택 리스너 설정
        spinnerPersonCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int selectedPersonCount = position + 1;  // 선택된 인원수 (1~4)
                updatePersonFields(selectedPersonCount);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 선택하지 않은 경우 기본값 유지
            }
        });

        // 예약 확정 버튼 클릭 시
        Button btnConfirmReservation = findViewById(R.id.btnConfirmReservation);
        btnConfirmReservation.setOnClickListener(v -> {
            // 예약 완료 메시지
            Toast.makeText(ReserveInfoActivity.this, "예약이 완료되었습니다.", Toast.LENGTH_SHORT).show();

            // 예약한 정보를 Intent로 MainActivity에 전달
            Intent intent1 = new Intent(ReserveInfoActivity.this, MainActivity.class);
            intent1.putExtra("selectedDate", tvCurrentDate.getText().toString());
            intent1.putExtra("selectedRoom", selectedRoom);
            intent1.putExtra("selectedTime", selectedTime);

            // MainActivity로 이동
            startActivity(intent1);
            finish(); // 현재 액티비티 종료
        });
    }

    // 선택된 인원수에 맞게 EditText 동적으로 추가
    private void updatePersonFields(int personCount) {
        // 기존의 입력 필드 제거
        linearLayoutPersonInfo.removeAllViews();

        // 선택된 인원수만큼 이름, 학번 입력 필드 추가
        for (int i = 0; i < personCount; i++) {
            // 이름 입력 EditText
            EditText etName = new EditText(this);
            etName.setHint("이름 " + (i + 1));
            etName.setFocusable(true);
            etName.setFocusableInTouchMode(true);
            etName.setInputType(InputType.TYPE_CLASS_TEXT);
            etName.setBackgroundResource(android.R.drawable.edit_text);
            etName.setTextColor(ContextCompat.getColor(this, android.R.color.black)); // 텍스트 색상 설정
            etName.setHintTextColor(ContextCompat.getColor(this, android.R.color.darker_gray)); // 힌트 색상 설정
            linearLayoutPersonInfo.addView(etName);

            // 학번 입력 EditText
            EditText etStudentId = new EditText(this);
            etStudentId.setHint("학번 " + (i + 1));
            etStudentId.setFocusable(true);
            etStudentId.setFocusableInTouchMode(true);
            etStudentId.setInputType(InputType.TYPE_CLASS_NUMBER);
            etStudentId.setBackgroundResource(android.R.drawable.edit_text);
            etStudentId.setTextColor(ContextCompat.getColor(this, android.R.color.black)); // 텍스트 색상 설정
            etStudentId.setHintTextColor(ContextCompat.getColor(this, android.R.color.darker_gray)); // 힌트 색상 설정
            linearLayoutPersonInfo.addView(etStudentId);
        }
    }

    // 현재 날짜를 설정하는 메서드
    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 (E요일)", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        tvCurrentDate.setText(currentDate);  // 현재 날짜를 TextView에 설정
    }
}
