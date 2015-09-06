package now.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Budget extends AppCompatActivity {

    /**
     * Called when the Activity is started
     * Loads the corresponding view (activity_budget.xml)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
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
     * Called when the plus button is clicked
     * @param view
     */
    public void sendPlus(View view) {
        // Do something in response to button click
        Log.d(Budget.class.getName(), "Plus Clicked");
        //TODO: update database, create Intent for Budget and startActivity
        // Find the EditText view and save the string it contains
        String valStr = ((EditText) findViewById(R.id.newTrans)).getText().toString();
        try {
            int val = Integer.parseInt(valStr);
        } catch(NumberFormatException e) {
            // this shouldn't happen???
        }

    }

    /**
     * Called when the minus button is clicked
     * @param view
     */
    public void sendMinus(View view) {
        Log.d(Budget.class.getName(), "Minus Clicked");
        //TODO: update database, create Intent for Budget and startActivity
    }
}
