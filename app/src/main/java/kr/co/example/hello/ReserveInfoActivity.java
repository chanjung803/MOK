package kr.co.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ReserveInfoActivity extends AppCompatActivity {

    private Spinner spinnerPersonCount;
    private LinearLayout linearLayoutPersonInfo;
    private TextView tvRoomAndTime;
    private int personCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserveinfo);

        tvRoomAndTime = findViewById(R.id.tvRoomAndTime);
        spinnerPersonCount = findViewById(R.id.spinnerPersonCount);

        tvRoomAndTime.setText("D422호 09:00");

        Button btnConfirmReservation = findViewById(R.id.btnConfirmReservation);
        btnConfirmReservation.setOnClickListener(v -> {
            Toast.makeText(this, "예약이 완료되었습니다.", Toast.LENGTH_SHORT).show();

            Reservation reservation = new Reservation("D422", "09:00", personCount);
            MainActivity.addReservation(reservation);
            MainActivity.setBookingInProgress(false);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
