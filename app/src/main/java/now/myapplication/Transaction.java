package now.myapplication;

import android.database.Cursor;

public class Transaction {
    private int id;
    private int value;
    private String datetime;

    public Transaction() {
    }

    public static Transaction cursorToTrans(Cursor c) {
        Transaction trans = new Transaction();
        trans.id = c.getInt(0);
        trans.value = c.getInt(1);
        trans.datetime = c.getString(2);
        return trans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int val) {
        this.value = val;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String toString() {
        return Integer.toString(value);
    }
}