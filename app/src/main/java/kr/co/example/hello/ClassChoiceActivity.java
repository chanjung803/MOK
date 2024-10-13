package kr.co.example.hello;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ClassChoiceActivity extends AppCompatActivity {

    private static final int MAX_SEATS = 30;  // 최대 예약 가능 인원
    int availableSeats;  // 예약 가능 인원 변수
    TextView availableSeatsTextView;  // 예약 가능 인원을 표시할 TextView
    TextView currentDateTextView;  // 현재 날짜를 표시할 TextView
    SharedPreferences sharedPreferences;  // SharedPreferences 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classchoice);  // XML 레이아웃 설정

        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("ReservationPrefs", MODE_PRIVATE);

        // 이전에 저장된 예약 가능 인원 값을 불러옴 (없으면 기본값 1 사용)
        availableSeats = sharedPreferences.getInt("availableSeats", 1);

        // 예약 가능 인원을 표시할 TextView 연결
        availableSeatsTextView = findViewById(R.id.tvD422Seats);
        updateAvailableSeats();  // 예약 가능 인원 표시

        // 현재 날짜를 표시할 TextView 연결
        currentDateTextView = findViewById(R.id.tvCurrentDate);

        // 현재 날짜를 가져와 TextView에 설정
        setCurrentDate();

        // "이전" 버튼 연결 및 클릭 리스너 설정
        Button btnBack = findViewById(R.id.BtnBackClass);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 액티비티를 종료하고 이전 화면으로 돌아감
                finish();
            }
        });

        // 시간 버튼 연결 및 클릭 리스너 설정 (각 시간 선택 시 ReserveInfoActivity로 이동)
        Button btnD422Time0900 = findViewById(R.id.btnD422Time0900);
        Button btnD422Time1000 = findViewById(R.id.btnD422Time1000);
        Button btnD422Time1100 = findViewById(R.id.btnD422Time1100);
        Button btnD422Time1200 = findViewById(R.id.btnD422Time1200);
        Button btnD422Time1300 = findViewById(R.id.btnD422Time1300);
        Button btnD422Time1400 = findViewById(R.id.btnD422Time1400);
        Button btnD422Time1500 = findViewById(R.id.btnD422Time1500);
        Button btnD422Time1600 = findViewById(R.id.btnD422Time1600);
        Button btnD422Time1700 = findViewById(R.id.btnD422Time1700);
        Button btnD422Time1800 = findViewById(R.id.btnD422Time1800);

        // 각 시간 버튼에 클릭 리스너 적용
        btnD422Time0900.setOnClickListener(createTimeClickListener("09:00"));
        btnD422Time1000.setOnClickListener(createTimeClickListener("10:00"));
        btnD422Time1100.setOnClickListener(createTimeClickListener("11:00"));
        btnD422Time1200.setOnClickListener(createTimeClickListener("12:00"));
        btnD422Time1300.setOnClickListener(createTimeClickListener("13:00"));
        btnD422Time1400.setOnClickListener(createTimeClickListener("14:00"));
        btnD422Time1500.setOnClickListener(createTimeClickListener("15:00"));
        btnD422Time1600.setOnClickListener(createTimeClickListener("16:00"));
        btnD422Time1700.setOnClickListener(createTimeClickListener("17:00"));
        btnD422Time1800.setOnClickListener(createTimeClickListener("18:00"));
    }

    // 클릭 리스너를 반환하는 메서드
    private View.OnClickListener createTimeClickListener(final String time) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 예약 가능 인원이 다 찼는지 확인
                if (availableSeats >= MAX_SEATS) {
                    Toast.makeText(ClassChoiceActivity.this, "예약 가능 인원이 모두 찼습니다.", Toast.LENGTH_SHORT).show();
                    return;  // 더 이상 예약 불가
                }
                // ReserveInfoActivity로 이동하며 선택한 시간을 전달
                Intent intent = new Intent(ClassChoiceActivity.this, ReserveInfoActivity.class);
                intent.putExtra("selectedRoom", "D422호");
                intent.putExtra("selectedTime", time);
                startActivity(intent);
            }
        };
    }

    // 예약 가능 인원을 증가시키는 메서드
    private void increaseAvailableSeats(int increment) {
        if (availableSeats + increment > MAX_SEATS) {
            Toast.makeText(this, "더 이상 예약할 수 없습니다. 최대 예약 인원은 30명입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        availableSeats += increment;  // 예약 가능 인원을 증가
        updateAvailableSeats();  // 화면에 업데이트

        // 증가된 인원을 SharedPreferences에 저장
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("availableSeats", availableSeats);
        editor.apply();  // 변경사항 적용
    }

    // 예약 가능 인원 TextView 업데이트
    private void updateAvailableSeats() {
        String seatsText = "예약 가능 인원: " + availableSeats + "/30";
        availableSeatsTextView.setText(seatsText);  // TextView에 반영
    }

    // 현재 날짜를 설정하는 메서드
    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 (E요일)", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        currentDateTextView.setText(currentDate);  // 현재 날짜를 TextView에 설정
    }
}