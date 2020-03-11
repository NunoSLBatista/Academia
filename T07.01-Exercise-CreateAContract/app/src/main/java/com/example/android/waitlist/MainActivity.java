package com.example.android.waitlist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.android.waitlist.data.TestUtil;
import com.example.android.waitlist.data.WaitListDbHelper;
import com.example.android.waitlist.data.WaitlistContract;
import com.example.android.waitlist.data.WaitlistContract.*;


public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;
    private SQLiteDatabase mDb;
    EditText mPersonNameEditText;
    EditText mPartySizeEditText;
    RecyclerView waitlistRecyclerView;

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPartySizeEditText = (EditText) findViewById(R.id.party_count_edit_text);
        mPersonNameEditText = (EditText) findViewById(R.id.person_name_edit_text);

        // Set local attributes to corresponding views
        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                removeGuest(id);
                updateList();
            }
        }).attachToRecyclerView(waitlistRecyclerView);


        WaitListDbHelper waitListDbHelper = new WaitListDbHelper(this);
        mDb = waitListDbHelper.getWritableDatabase();

        TestUtil.insertFakeData(mDb);
        updateList();


    }


    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    public void addToWaitlist(View view) {

        if(mPersonNameEditText.getText().length() == 0 ||
                mPartySizeEditText.getText().length() == 0) {
            return;
        }
        // COMPLETED (10) Create an integer to store the party size and initialize to 1
        //default party size to 1
        int partySize = 1;
        // COMPLETED (11) Use Integer.parseInt to parse mNewPartySizeEditText.getText to an integer
        try {
            //mNewPartyCountEditText inputType="number", so this should always work
            partySize = Integer.parseInt(mPartySizeEditText.getText().toString());
        } catch (NumberFormatException ex) {
            // COMPLETED (12) Make sure you surround the Integer.parseInt with a try catch and log any exception
            Log.e(LOG_TAG, "Failed to parse party size text to number: " + ex.getMessage());
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(WaitlistEntry.COLUMN_GUEST_NAME, mPersonNameEditText.getText().toString());
        contentValues.put(WaitlistEntry.COLUMN_PARTY_SIZE, partySize);

        WaitListDbHelper waitListDbHelper = new WaitListDbHelper(this);
        mDb = waitListDbHelper.getWritableDatabase();

        mDb.insert(WaitlistEntry.TABLE_NAME, null, contentValues);
      updateList();

    }

    public void updateList(){
        Cursor cursor = getAllGuests();

        mAdapter = new GuestListAdapter(this, cursor);
        // Link the adapter to the RecyclerView
        waitlistRecyclerView.setAdapter(mAdapter);
    }

    public Cursor getAllGuests(){

        return mDb.query(WaitlistEntry.TABLE_NAME, null, null, null, null, null, WaitlistEntry.COLUMN_TIMESTAMP);

    }

    public boolean removeGuest(long id){

       if(mDb.delete(WaitlistEntry.TABLE_NAME, WaitlistEntry._ID + " = ?", new String[]{String.valueOf(id)}) > 0){
           return true;
       }
        return false;

    }


}
