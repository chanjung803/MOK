package kr.co.example.hello;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class ClassChoiceActivity extends AppCompatActivity {

    private static final String[] ROOM_NAMES = {"팹랩", "D421", "D422", "D423", "D424", "D425", "D426", "D427", "D428", "D429", "D430"};
    private static final String[] TIME_SLOTS = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};
    private static final int MAX_CAPACITY = 30;

    private TextView currentDateTextView, currentTimeTextView;
    private LinearLayout containerLayout;
    private Handler handler = new Handler();
    private HashMap<String, Integer> reservations = new HashMap<>(); // 시간대별 예약 인원 저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classchoice);

        // UI 요소 연결
        currentDateTextView = findViewById(R.id.tvCurrentDate);
        currentTimeTextView = findViewById(R.id.tvCurrentTime);
        containerLayout = findViewById(R.id.containerLayout);

        // 뒤로가기 버튼 설정
        Button btnBackClass = findViewById(R.id.btnBackClass);
        btnBackClass.setOnClickListener(v -> finish());

        // 현재 날짜 및 시간 업데이트
        setCurrentDate();
        updateCurrentTime();

        // 예약 현황 초기화
        initializeReservations();

        // 동적 UI 생성
        createDynamicRoomButtons();
    }

    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 (E요일)", Locale.getDefault());
        currentDateTextView.setText(dateFormat.format(calendar.getTime()));
    }

    private void updateCurrentTime() {
        handler.postDelayed(() -> {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            currentTimeTextView.setText(timeFormat.format(calendar.getTime()));
            handler.postDelayed(this::updateCurrentTime, 1000);
        }, 0);
    }

    private void initializeReservations() {
        for (String room : ROOM_NAMES) {
            for (String time : TIME_SLOTS) {
                String key = room + "_" + time;
                reservations.put(key, 0); // 초기값 0으로 설정
            }
        }
    }

    private void createDynamicRoomButtons() {
        for (String room : ROOM_NAMES) {
            // 강의실 이름 TextView 추가
            TextView roomTextView = new TextView(this);
            roomTextView.setText(room + "호");
            roomTextView.setTextSize(16);
            roomTextView.setGravity(Gravity.START);
            roomTextView.setPadding(10, 20, 10, 10);
            roomTextView.setTypeface(null, android.graphics.Typeface.BOLD);
            containerLayout.addView(roomTextView);

            // 시간 버튼과 현황 표시
            HorizontalScrollView scrollView = new HorizontalScrollView(this);
            scrollView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            LinearLayout timeButtonLayout = new LinearLayout(this);
            timeButtonLayout.setOrientation(LinearLayout.HORIZONTAL);
            timeButtonLayout.setPadding(0, 10, 0, 10);

            for (String time : TIME_SLOTS) {
                String key = room + "_" + time;

                // 버튼과 텍스트뷰를 담을 레이아웃
                LinearLayout buttonAndStatusLayout = new LinearLayout(this);
                buttonAndStatusLayout.setOrientation(LinearLayout.VERTICAL);
                buttonAndStatusLayout.setGravity(Gravity.CENTER_HORIZONTAL);

                // 시간 버튼
                Button timeButton = new Button(this);
                timeButton.setText(time);
                timeButton.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                timeButton.setTextColor(Color.WHITE);
                timeButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                // 예약 현황 텍스트뷰
                TextView statusTextView = new TextView(this);
                statusTextView.setText(reservations.get(key) + "/" + MAX_CAPACITY);
                statusTextView.setTextSize(12);
                statusTextView.setGravity(Gravity.CENTER);
                statusTextView.setTextColor(Color.BLACK);

                // 버튼 클릭 리스너
                timeButton.setOnClickListener(v -> {
                    if (reservations.get(key) < MAX_CAPACITY) {
                        Intent intent = new Intent(this, ReserveInfoActivity.class);
                        intent.putExtra("selectedRoom", room);
                        intent.putExtra("selectedTime", time);
                        intent.putExtra("reservationKey", key);
                        startActivityForResult(intent, 100);
                    } else {
                        Toast.makeText(this, "예약이 꽉 찼습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                // 레이아웃에 추가
                buttonAndStatusLayout.addView(timeButton);
                buttonAndStatusLayout.addView(statusTextView);
                timeButtonLayout.addView(buttonAndStatusLayout);
            }

            scrollView.addView(timeButtonLayout);
            containerLayout.addView(scrollView);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            String key = data.getStringExtra("reservationKey");
            int addedCount = data.getIntExtra("addedCount", 0);

            if (key != null && reservations.containsKey(key)) {
                reservations.put(key, reservations.get(key) + addedCount);
            }

            // 예약 현황 갱신
            updateReservationStatus();
        }
    }

    private void updateReservationStatus() {
        containerLayout.removeAllViews();
        createDynamicRoomButtons(); // 동적 UI를 다시 생성하여 갱신된 데이터 반영
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}

