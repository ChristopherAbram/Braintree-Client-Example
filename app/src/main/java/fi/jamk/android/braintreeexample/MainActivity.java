package fi.jamk.android.braintreeexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;

public class MainActivity extends AppCompatActivity {

    private AsyncHttpClient mClient = null;
    private String clientToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mClient = new AsyncHttpClient();

    }

    private void getClientToken(){

    }
}
