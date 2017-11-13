package fi.jamk.android.braintreeexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void confirmButton(View view) {
        finish();
    }
}
