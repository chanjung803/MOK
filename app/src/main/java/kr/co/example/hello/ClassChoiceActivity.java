package kr.co.example.hello;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
    private int availableSeats;  // 예약 가능 인원 변수
    private TextView availableSeatsTextView;  // 예약 가능 인원을 표시할 TextView
    private TextView currentDateTextView;  // 현재 날짜를 표시할 TextView
    private TextView currentTimeTextView;  // 현재 시간을 표시할 TextView
    private SharedPreferences sharedPreferences;  // SharedPreferences 선언
    private Handler handler = new Handler();  // 실시간 시간 갱신용 핸들러

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

        // 현재 날짜와 시간 TextView 연결
        currentDateTextView = findViewById(R.id.tvCurrentDate);
        currentTimeTextView = findViewById(R.id.tvCurrentTime);

        // 현재 날짜를 설정
        setCurrentDate();

        // 실시간 시간 업데이트 시작
        updateCurrentTime();

        // "이전" 버튼 연결 및 클릭 리스너 설정
        Button btnBack = findViewById(R.id.btnBackClass);
        btnBack.setOnClickListener(v -> finish());

        // 시간 버튼 연결 및 클릭 리스너 설정 (예시)
        Button btnD422Time0900 = findViewById(R.id.btnD422Time0900);
        btnD422Time0900.setOnClickListener(createTimeClickListener("09:00"));

        // 추가 시간 버튼 연결 및 리스너 설정 ...
    }

    // 클릭 리스너를 반환하는 메서드
    private View.OnClickListener createTimeClickListener(final String time) {
        return v -> {
            // ReserveInfoActivity로 이동하며 선택한 시간을 전달
            Intent intent = new Intent(ClassChoiceActivity.this, ReserveInfoActivity.class);
            intent.putExtra("selectedRoom", "D422호");
            intent.putExtra("selectedTime", time);
            startActivity(intent);
        };
    }

    // 예약 가능 인원 TextView 업데이트
    private void updateAvailableSeats() {
        String seatsText = "예약 가능 인원: " + availableSeats + "/30";
        availableSeatsTextView.setText(seatsText);
    }

    // 현재 날짜를 설정하는 메서드
    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 (E요일)", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        currentDateTextView.setText(currentDate);
    }

    // 실시간 시간을 설정하는 메서드
    private void updateCurrentTime() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String currentTime = timeFormat.format(calendar.getTime());
                currentTimeTextView.setText(currentTime);  // 현재 시간을 TextView에 설정
                handler.postDelayed(this, 1000);  // 1초마다 갱신
            }
        }, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);  // 핸들러 콜백 제거
    }
}