package kr.co.example.hello;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MyReservationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myreservations);

        // XML의 ID를 정확히 확인
        LinearLayout containerLayout = findViewById(R.id.containerLayout);

        if (containerLayout == null) {
            throw new IllegalStateException("containerLayout is not defined in myreservations.xml");
        }

        // 예약 데이터를 가져옵니다.
        List<Reservation> reservations = MainActivity.getReservationList();

        if (reservations == null || reservations.isEmpty()) {
            TextView emptyTextView = new TextView(this);
            emptyTextView.setText("예약된 정보가 없습니다.");
            emptyTextView.setTextSize(16);
            emptyTextView.setPadding(16, 16, 16, 16);
            containerLayout.addView(emptyTextView);
        } else {
            for (Reservation reservation : reservations) {
                TextView textView = new TextView(this);
                textView.setText(reservation.getRoom() + " - " + reservation.getTime() + " - " + reservation.getPeopleCount() + "명");
                textView.setTextSize(16);
                textView.setPadding(16, 16, 16, 16);

                // 지난 예약 구분
                if (isPastReservation(reservation.getTime())) {
                    textView.setTextColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    textView.setTextColor(getResources().getColor(android.R.color.black));
                }

                containerLayout.addView(textView);
            }
        }
    }

    private boolean isPastReservation(String time) {
        // 간단한 시간 비교 로직 (현재 시간과 비교)
        return false; // 임시 값
    }
}

