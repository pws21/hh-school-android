package ru.hh.school;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.os.Bundle;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class ResumeActivity extends FragmentActivity {
	
	private static final int REQ_SEND_RESUME = 100;
	
	private EditText fullname;
	private EditText phone;
	private EditText birth;
	private Calendar clndr = Calendar.getInstance();
	private Spinner sex;
	private EditText salary;
	private EditText position;
	private EditText email;
	private Button sendButton;
	
	private DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

	    @Override
	    public void onDateSet(DatePicker view, int year, int monthOfYear,
	            int dayOfMonth) {
	        clndr.set(Calendar.YEAR, year);
	        clndr.set(Calendar.MONTH, monthOfYear);
	        clndr.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	        updateLabel();
	    }
	    
	    private void updateLabel() {
		    String myFormat = "dd/MM/yyyy"; 
		    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
		    birth.setText(sdf.format(clndr.getTime()));
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity1);
		
		fullname = (EditText) findViewById(R.id.fullNameEdit);
		birth = (EditText) findViewById(R.id.birthDayEdit);
		birth.setInputType(InputType.TYPE_NULL);
		sex = (Spinner) findViewById(R.id.sexSpinner);
		position = (EditText) findViewById(R.id.positionEdit);
		salary = (EditText) findViewById(R.id.salaryEdit);
		phone = (EditText) findViewById(R.id.phoneEdit);
		email = (EditText) findViewById(R.id.emailEdit);
		sendButton = (Button) findViewById(R.id.sendButton);
		
		final DatePickerDialog dateDialog = new DatePickerDialog(this, date, clndr
	            .get(Calendar.YEAR), clndr.get(Calendar.MONTH),
	            clndr.get(Calendar.DAY_OF_MONTH));
		
		birth.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            dateDialog.show();
	        }
	    });
		birth.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					dateDialog.show();
				}
			}
		});
		phone.setFilters(new InputFilter[]{new PhoneInputFilter()});
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (fullname.getText().toString().isEmpty()) {
					fullname.setError(getString(R.string.value_required));
					return;
				}
				Intent sendIntent = new Intent(v.getContext(), CompanyActivity.class);
				sendIntent.putExtra(Resume.FULLNAME, fullname.getText().toString());
				sendIntent.putExtra(Resume.BIRTHDAY, birth.getText().toString());
				sendIntent.putExtra(Resume.SEX, sex.getSelectedItem().toString());
				sendIntent.putExtra(Resume.POSITION, position.getText().toString());
				sendIntent.putExtra(Resume.SALARY, salary.getText().toString());
				sendIntent.putExtra(Resume.PHONE, phone.getText().toString());
				sendIntent.putExtra(Resume.EMAIL, email.getText().toString());
				startActivityForResult(sendIntent, REQ_SEND_RESUME);
			}
		});
	}
	
	
	private void showDialog(String title, String msg) {
		DialogFragment newFragment = ResponseDialog.newInstance(title, msg);
		FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
		fTrans.add(newFragment, null);
        //newFragment.show(fTrans, "dialog");
		//http://stackoverflow.com/questions/12105064/actions-in-onactivityresult-and-error-can-not-perform-this-action-after-onsavei
        fTrans.commitAllowingStateLoss();
    }
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_SEND_RESUME) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                this.showDialog(getString(R.string.response_title), extras.getString(Resume.RESPONSE).toString());
            }
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity1, menu);
		return true;
	}
	

}
