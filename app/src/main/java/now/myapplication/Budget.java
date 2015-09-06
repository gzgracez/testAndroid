package now.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class Budget extends AppCompatActivity {
    private SQLiteHelper dbHelper;
    private SimpleCursorAdapter dataAdapter;
    SQLiteDatabase dbwrite;
    SQLiteDatabase dbread;

    /**
     * Called when the Activity is started
     * Loads the corresponding view (activity_budget.xml)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        dbHelper = new SQLiteHelper(this);
        dbwrite = dbHelper.getWritableDatabase();
        dbread = dbHelper.getReadableDatabase();

        // Load transactions
        Cursor c = dbread.rawQuery("select * from Transactions", new String[]{}); // TODO: order by datetime asc
        // Find ListView
        ListView listview = (ListView) findViewById(R.id.transactions);

        // Make a list of the Transactions and add total
        List<Transaction> transactions = new ArrayList<Transaction>();
        float total = 0;
        while (c.moveToNext()) { // returns true if successful
            Log.d("Database entry: ", Float.toString(c.getFloat(1)));
            total += c.getFloat(1);
            transactions.add(Transaction.cursorToTrans(c));


        }
        // Make the adapter with the list of Transactions
        ArrayAdapter<Transaction> adapter = new ArrayAdapter<Transaction>(this, android.R.layout.simple_list_item_1, transactions);
        // Set the ListView to the adapter
        listview.setAdapter(adapter);

        TextView textView = (TextView) findViewById(R.id.textViewtotal);
        textView.setText("Total: " + total);
        // TODO: calculate total and display it
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Convenience function for sendPlus and sendMinus
     * @return number provided in EditText
     */
    public float getNum() {
        Log.d(Budget.class.getName(), "entered getNum()");
        // Find the EditText view and save the string it contains
        String valStr = ((EditText) findViewById(R.id.newTrans)).getText().toString();
        // Initialize val as -1 (TODO If it returns -1, something went wrong)
        float val = -1;
        try {
            val = Float.parseFloat(valStr);
        } catch(NumberFormatException e) {
            // this shouldn't happen???
        }
        Log.d(Budget.class.getName(), "gotNum");
        return val;
    }

    /**
     * Convenience function to add val to database and restart Budget
     * @param val
     */
    public void restart(float val) {
        Log.d(Budget.class.getName(), "restarting");

        // TODO: add val to dbwrite
        Log.d(Budget.class.getName(), "got db");
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_AMOUNT, val);

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(calendar.getTime());
        values.put(SQLiteHelper.COLUMN_DATETIME, date); // TODO

        dbwrite.insert(SQLiteHelper.TABLE_TRANS, null, values);

        // restart the Activity, which will reload the layout
        Intent intent = new Intent(this, Budget.class);
        startActivity(intent);
        finish();
    }

    /**
     * Called when the plus button is clicked
     * @param view
     */
    public void sendPlus(View view) {
        Log.d(Budget.class.getName(), "Plus Clicked " + getNum());
        // find the number in EditText and restart the activity with an intent containing that value
        restart(getNum());
    }

    /**
     * Called when the minus button is clicked
     * @param view
     */
    public void sendMinus(View view) {
        Log.d(Budget.class.getName(), "Minus Clicked " + (-getNum()));
        // find the number in EditText and restart the activity with an intent containing that value
        restart(-getNum());
    }
}
