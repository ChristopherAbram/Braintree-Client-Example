package fi.jamk.android.braintreeexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class FailureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);

        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        TextView textView = (TextView) findViewById(R.id.result_text);
        textView.setText(extras.getString("status"));
    }

    public void onBackButton(View view) {
        super.onBackPressed();
    }
}
