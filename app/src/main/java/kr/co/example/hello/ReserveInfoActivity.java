package kr.co.example.hello;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private int selectedPersonCount = 1; // 기본값
    private int availableSeats; // 예약 가능 인원 변수
    private SharedPreferences sharedPreferences;

    private TextView tvRoomAndTime; // 호수와 시간을 표시할 TextView
    private TextView tvCurrentDate; // 현재 날짜를 표시할 TextView
    private String selectedRoom, selectedTime; // Intent로 전달받은 호수와 시간

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserveinfo);  // reserveinfo.xml과 연결

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

        // SharedPreferences 초기화 (예약 가능 인원 값을 유지하기 위해 사용)
        sharedPreferences = getSharedPreferences("ReservationPrefs", MODE_PRIVATE);
        availableSeats = sharedPreferences.getInt("availableSeats", 0); // 기본값 0

        // Spinner와 동적으로 입력할 LinearLayout 연결
        spinnerPersonCount = findViewById(R.id.spinnerPersonCount);
        linearLayoutPersonInfo = findViewById(R.id.linearLayoutPersonInfo);

        // Spinner 선택 리스너 설정
        spinnerPersonCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedPersonCount = position + 1;  // 선택된 인원수 (1~4)
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
            // 인원수를 선택한 만큼 예약 가능 인원을 증가시키고 저장
            increaseAvailableSeats(selectedPersonCount);

            // 예약 완료 메시지
            Toast.makeText(ReserveInfoActivity.this, "예약이 완료되었습니다.", Toast.LENGTH_SHORT).show();

            // MainActivity로 이동
            Intent intent1 = new Intent(ReserveInfoActivity.this, MainActivity.class);
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
            linearLayoutPersonInfo.addView(etName);

            // 학번 입력 EditText
            EditText etStudentId = new EditText(this);
            etStudentId.setHint("학번 " + (i + 1));
            linearLayoutPersonInfo.addView(etStudentId);
        }
    }

    // 예약 가능 인원을 증가시키고 SharedPreferences에 저장
    private void increaseAvailableSeats(int addedSeats) {
        availableSeats += addedSeats; // 인원 수만큼 증가
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("availableSeats", availableSeats);
        editor.apply();  // 변경사항 적용
    }

    // 현재 날짜를 설정하는 메서드
    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 (E요일)", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        tvCurrentDate.setText(currentDate);  // 현재 날짜를 TextView에 설정
    }
}
