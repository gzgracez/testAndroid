package now.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Budget extends AppCompatActivity {

    /**
     * Called when the Activity is started
     * Loads the corresponding view (activity_budget.xml)
     * @param savedInstanceState
     */

    private SQLiteHelper dbHelper;
    private SimpleCursorAdapter dataAdapter;
    SQLiteDatabase dbwrite;
    SQLiteDatabase dbread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        dbHelper = new SQLiteHelper(this);
        dbHelper.open();
        dbwrite = db.getWritableDatabase();
        dbread = db.getReadableDatabase();
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

    public void listView() {

        Cursor cursor = dbHelper.fetchAllValues();

        String[] columns = new String[]{
                //SQLiteHelper.COLUMN_ID,
                SQLiteHelper.COLUMN_VALUE//,
                //SQLiteHelper.COLUMN_DATETIME
        };

        int[] to = new int[] {
                R.id.transactions
        };

        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.activity_budget,
                cursor,
                columns,
                to,
                0
        );

        ListView listView = (ListView)findViewById(R.id.transactions);
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor)listView.getItemAtPosition(position);

                String value = cursor.getString(cursor.getColumnIndexOrThrow("code"));
                Toast.makeText(getApplicationContext(),value, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Convenience function for sendPlus and sendMinus
     * @return number provided in EditText
     */
    public int getNum() {
        // Find the EditText view and save the string it contains
        String valStr = ((EditText) findViewById(R.id.newTrans)).getText().toString();
        // Initialize val as -1 (If it returns -1, something went wrong)
        int val = -1;
        try {
            val = Integer.parseInt(valStr);
        } catch(NumberFormatException e) {
            // this shouldn't happen???
        }
        return val;
    }

    /**
     * Convenience function to add val to an intent and restart Budget
     * @param val
     */
    public void restart(int val) {
        Intent intent = new Intent(this, Budget.class);
        intent.putExtra("value", val);
        startActivity(intent);
        finish();
    }

    /**
     * Called when the plus button is clicked
     * @param view
     */
    public void sendPlus(View view) {
        String valStr = ((EditText) findViewById(R.id.newTrans)).getText().toString();
        Log.d(Budget.class.getName(), "Plus Clicked" + getNum());
        // find the number in EditText and restart the activity with an intent containing that value
        restart(getNum());
    }

    /**
     * Called when the minus button is clicked
     * @param view
     */
    public void sendMinus(View view) {
        Log.d(Budget.class.getName(), "Minus Clicked" + (-getNum()));
        // find the number in EditText and restart the activity with an intent containing that value
        restart(-getNum());
    }
}
