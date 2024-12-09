package kr.co.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView footerTextView;
    private View drawerView;
    private static List<Reservation> reservationList = new ArrayList<>();
    private static boolean isBookingInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        drawerView = findViewById(R.id.drawerView);

        ImageButton btnHamburger = findViewById(R.id.btnHamburger);
        btnHamburger.setOnClickListener(v -> drawerLayout.openDrawer(drawerView));

        Button btnMyReservations = findViewById(R.id.btnMyReservations);
        btnMyReservations.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyReservationsActivity.class);
            startActivity(intent);
        });

        Button btnReserveClass = findViewById(R.id.btnReserveClass);
        btnReserveClass.setOnClickListener(v -> {
            if (isBookingInProgress) {
                footerTextView.setText("이미 예약 진행 중입니다.");
            } else {
                Intent intent = new Intent(MainActivity.this, ClassChoiceActivity.class);
                startActivity(intent);
            }
        });

        footerTextView = findViewById(R.id.footerTextView);
        updateFooter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFooter();
    }

    private void updateFooter() {
        if (!reservationList.isEmpty()) {
            Reservation latestReservation = reservationList.get(reservationList.size() - 1);
            footerTextView.setText(latestReservation.getRoom() + " - " + latestReservation.getTime() + " - " + latestReservation.getPeopleCount() + "명");
        } else {
            footerTextView.setText("예약된 정보가 없습니다.");
        }
    }

    public static void addReservation(Reservation reservation) {
        reservationList.add(reservation);
    }

    public static List<Reservation> getReservationList() {
        return reservationList;
    }

    public static void setBookingInProgress(boolean inProgress) {
        isBookingInProgress = inProgress;
    }
}
