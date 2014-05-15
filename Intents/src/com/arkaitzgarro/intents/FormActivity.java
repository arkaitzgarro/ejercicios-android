package com.arkaitzgarro.intents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FormActivity extends Activity {

	public static final String EMAIL_FIELD = "email";

	EditText email;
	Button ok, cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);

		email = (EditText) findViewById(R.id.email);
		ok = (Button) findViewById(R.id.ok);
		cancel = (Button) findViewById(R.id.cancel);

		Intent intent = getIntent();
		if (intent != null) {
			String email = intent.getStringExtra(EMAIL_FIELD);
			TextView lblEmail = (TextView) findViewById(R.id.lblEmail);
			lblEmail.setText(email);
		}

		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String data = email.getText().toString();

				Intent result = new Intent();
				result.putExtra(EMAIL_FIELD, data);

				setResult(RESULT_OK, result);
				finish();
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}

}
