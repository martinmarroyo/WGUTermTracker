package logic;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.martinarroyo.termtracker.R;

import java.time.LocalDate;
import java.util.Calendar;

import logic.Term;

/**
 * Fragment to handle entering a new Term or modifying an existing one
 */
public class AddEditTerm extends AppCompatActivity {
    Term term; // The term to create based on the input given
    Button cancel;
    Button save;
    TextView startDate;
    DatePickerDialog.OnDateSetListener mDateSetListenerStart;
    LocalDate start;
    TextView endDate;
    DatePickerDialog.OnDateSetListener mDateSetListenerEnd;
    LocalDate end;
    public EditText termTitle;
    String title;
    public static final String NEW_TERM_TITLE = "New Title";
    public static final String NEW_TERM_START = "New Start";
    public static final String NEW_TERM_END = "New End";
    public static final String MOD_TERM_ID = "Mod ID";
    public static final String MOD_TERM_TITLE = "Mod Title";
    public static final String MOD_TERM_START = "Mod Start";
    public static final String MOD_TERM_END = "Mod End";
    public static final String MOD_TERM_POS = "Mod Position";
    boolean update = false;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.add_term);
        // Initialize views and buttons
        termTitle = (EditText) findViewById(R.id.term_title);
        startDate = (TextView) findViewById(R.id.startdate);
        endDate = (TextView) findViewById(R.id.enddate);
        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        // If there is an intent extra, set up the fields to reflect that
        Intent modIntent = getIntent();
        if (modIntent.hasExtra(MOD_TERM_ID)){
            update = true;
            termTitle.setText(modIntent.getStringExtra(MOD_TERM_TITLE));
            startDate.setText(modIntent.getStringExtra(MOD_TERM_START));
            endDate.setText(modIntent.getStringExtra(MOD_TERM_END));
            start = LocalDate.parse(startDate.getText());
            end = LocalDate.parse(endDate.getText());
        }

        // Register action listeners
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                cancel();
            }
        });
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                save();
            }
        });
        startDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchDatePicker(mDateSetListenerStart);
            }
        });
        endDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchDatePicker(mDateSetListenerEnd);
            }
        });
        // Initialize OnDateSetListeners
        mDateSetListenerStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                start = LocalDate.of(year, ++month, day);
                // Set the text for the text view
                startDate.setText(start.toString());
                //Toast.makeText(getApplicationContext(),"Start Date: "+start.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        mDateSetListenerEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                end = LocalDate.of(year,++month,day);
                // Set the text for the text view
                endDate.setText(end.toString());
                //Toast.makeText(getApplicationContext(),"End Date: "+end.toString(), Toast.LENGTH_SHORT).show();
            }
        };

    }


    /**
     * Functionality for the Cancel button
     */
    private void cancel(){
        Toast toast = Toast.makeText(getApplicationContext(),"Pressed cancel",Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();

    }

    /**
     * Functionality for the Save Button
     */
    //TODO add data validation for fields
    private void save(){

        this.title = termTitle.getText().toString();
        String termStart = start.toString();
        String termEnd = end.toString();
        Intent intent = new Intent();
        if (update){
            intent.putExtra(MOD_TERM_TITLE,this.title);
            intent.putExtra(MOD_TERM_START,termStart);
            intent.putExtra(MOD_TERM_END,termEnd);
            int id = getIntent().getIntExtra(MOD_TERM_ID,-1);
            intent.putExtra(MOD_TERM_ID,id);
        } else {
            intent.putExtra(NEW_TERM_TITLE,this.title);
            intent.putExtra(NEW_TERM_START,termStart);
            intent.putExtra(NEW_TERM_END,termEnd);
        }
        setResult(RESULT_OK,intent);
        finish();
    }

    /**
     * Helper function to launch DatePicker
     * @param dateSetListener
     */
    private void launchDatePicker(DatePickerDialog.OnDateSetListener dateSetListener) {
        // Create Calendar object
        Calendar cal = Calendar.getInstance();
        // get year, month and day
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        // Create the DatePickerDialog object
        DatePickerDialog dialog = new DatePickerDialog(this,android.R.style.Widget_Material_DatePicker, dateSetListener,year,month,day);
        // Show the dialog
        dialog.show();
    }
}
