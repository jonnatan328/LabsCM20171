package co.edu.udea.compumovil.gr04_20171.lab2.event.detailEvent;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;

import co.edu.udea.compumovil.gr04_20171.lab2.R;
import co.edu.udea.compumovil.gr04_20171.lab2.event.data.Event;
import co.edu.udea.compumovil.gr04_20171.lab2.event.data.EventDbHelper;

public class DetailEventActivity extends AppCompatActivity {

    private EventDbHelper eventDbHelper;

    private ImageView imageField;
    private TextView nameField;
    private TextView personInChargeField;
    private CardView ratingField;
    private TextView dateField;
    private TextView locationField;
    private TextView descriptionField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        //get id of the event
        Bundle extras = getIntent().getExtras();
        final String eventId = extras.getString("idEvent");
        String eventName = extras.getString("nameEvent");
        Log.d("id del Event", eventId);
       // Cursor cursorEvent2 = eventDbHelper.getEventByName(eventName);
        //Log.d("cursor 2", String.valueOf(cursorEvent2.getCount()));
        Cursor cursorEvent = eventDbHelper.getEventById(eventId);
        cursorEvent.moveToPosition(0);
        Event event = null;
        try {
            event = new Event(cursorEvent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        loadEvent(event);

    }

    public void loadEvent(Event event){
        //imageField.setBackground();
        nameField.setText(event.getName());
        personInChargeField.setText(event.getPersonInCharge());
        //ratingField;
        dateField.setText((CharSequence) event.getDate());
        locationField.setText(event.getLocation());
        descriptionField.setText(event.getDescription());
    }
}
