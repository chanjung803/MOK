package kr.co.example.hello;

public class Reservation {
    private final String room;
    private final String time;
    private final int peopleCount;

    public Reservation(String room, String time, int peopleCount) {
        this.room = room;
        this.time = time;
        this.peopleCount = peopleCount;
    }

    public String getRoom() {
        return room;
    }

    public String getTime() {
        return time;
    }

    public int getPeopleCount() {
        return peopleCount;
    }
}
