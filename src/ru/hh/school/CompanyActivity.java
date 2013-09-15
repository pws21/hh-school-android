package ru.hh.school;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class CompanyActivity extends Activity {
	
		private TextView fullname;
		private TextView position;
		private TextView sex;
		private TextView birth;
		private TextView salary;
		private Button email;
		private Button phone;
		private EditText response;
		
		private void toggleRow(int id, boolean hidden) {
			Log.d(this.getLocalClassName(), "id: "+id+"; h: "+hidden);
			if (hidden) {
				TableLayout tbl = (TableLayout) findViewById(R.id.table1);
				TableRow tr = (TableRow) findViewById(id);
				tbl.removeView(tr);
			}
		}
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity2);
			final Bundle extras = getIntent().getExtras();
			fullname = (TextView) findViewById(R.id.fullNameText);
			fullname.setText(extras.getString(Resume.FULLNAME));
			position = (TextView) findViewById(R.id.positionText);
			position.setText(extras.getString(Resume.POSITION));
			toggleRow(R.id.rowPosition, extras.getString(Resume.POSITION).isEmpty());
			
			sex = (TextView) findViewById(R.id.sexText);
			sex.setText(extras.getString(Resume.SEX));
			toggleRow(R.id.rowSex, extras.getString(Resume.SEX).isEmpty());
			
			birth = (TextView) findViewById(R.id.birthText);
			birth.setText(extras.getString(Resume.BIRTHDAY));
			toggleRow(R.id.rowBirth, extras.getString(Resume.BIRTHDAY).isEmpty());
			
			salary = (TextView) findViewById(R.id.salaryText);
			salary.setText(extras.getString(Resume.SALARY));
			toggleRow(R.id.rowSalary, extras.getString(Resume.SALARY).isEmpty());
			
			email = (Button) findViewById(R.id.makeMail);
			View bEmail = (View) findViewById(R.id.lineBelowMail);
			phone = (Button) findViewById(R.id.makeCall);
			View bPhone = (View) findViewById(R.id.lineBelowCall);
			
			if (!extras.getString(Resume.PHONE).isEmpty()) {
				phone.setText(" " + extras.getString(Resume.PHONE));
				phone.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:" + extras.getString(Resume.PHONE)));
						startActivity(intent);
					}
				});
				phone.setVisibility(View.VISIBLE);
				bPhone.setVisibility(View.VISIBLE);
			}
			else {
				phone.setVisibility(View.GONE);
				bPhone.setVisibility(View.GONE);
			}
			
			if (!extras.getString(Resume.EMAIL).isEmpty()) {
				email.setText(" " + extras.getString(Resume.EMAIL));
				email.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent i = new Intent(Intent.ACTION_SEND);
						i.setType("message/rfc822");
						i.putExtra(Intent.EXTRA_EMAIL  , new String[]{extras.getString(Resume.EMAIL)});
						i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.dflt_subject));
						try {
						    startActivity(Intent.createChooser(i, "Send mail..."));
						} catch (android.content.ActivityNotFoundException ex) {
						    Toast.makeText(CompanyActivity.this, getString(R.string.no_email_client), Toast.LENGTH_SHORT).show();
						}
					}
				});
				email.setVisibility(View.VISIBLE);
				bEmail.setVisibility(View.VISIBLE);
			}
			else {
				email.setVisibility(View.GONE);
				bEmail.setVisibility(View.GONE);
			}
			
			response = (EditText) findViewById(R.id.responseEdit);
			
			Button replyButton = (Button) findViewById(R.id.replyButton);
			replyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.putExtra(Resume.RESPONSE, response.getText().toString());
					setResult(RESULT_OK, intent);
					finish();
				}
			});
	}
}