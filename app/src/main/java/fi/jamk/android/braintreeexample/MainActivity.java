package fi.jamk.android.braintreeexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.braintreepayments.api.BraintreePaymentActivity;
import com.braintreepayments.api.PaymentRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class MainActivity extends AppCompatActivity {

    private AsyncHttpClient mClient = null;
    private String clientToken = null;

    private Button mPayButton = null;

    private static final int REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get UI button view:
        mPayButton = (Button) findViewById(R.id.button);
        mPayButton.setEnabled(false);

        // Get client token from server:
        getClientToken();
    }

    private void getClientToken(){
        mClient = new AsyncHttpClient();
        mClient.get(Constants.SERVER + "/" + Constants.CLIENT_TOKEN, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                clientToken = responseString;

                // Enable payment button:
                mPayButton.setEnabled(true);
            }
        });
    }

    public void requestPayment(View view){
        PaymentRequest paymentRequest = new PaymentRequest()
                .clientToken(clientToken)
                .amount("$1.00")
                .primaryDescription("Coffee with sugar")
                .secondaryDescription("Some description")
                .submitButtonText("Pay");

        startActivityForResult(paymentRequest.getIntent(this), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_CODE:
                if (resultCode == BraintreePaymentActivity.RESULT_OK) {

                    PaymentMethodNonce paymentMethodNonce = data.getParcelableExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE);

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("payment_method_nonce", paymentMethodNonce.getNonce());
                    requestParams.put("amount", "1.00");

                    mClient.post(Constants.SERVER + "/" + Constants.TRANSACTION, requestParams, new TextHttpResponseHandler() {

                        @Override
                        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                            //Toast.makeText(MainActivity.this, "Failure: " + responseString, Toast.LENGTH_LONG).show();
                            Intent resultIntent = new Intent(getBaseContext(), FailureActivity.class);
                            startActivity(resultIntent);
                            //finish();
                        }

                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                            //Toast.makeText(MainActivity.this, "Success: " + responseString, Toast.LENGTH_LONG).show();
                            Intent resultIntent = new Intent(getBaseContext(), SuccessActivity.class);
                            startActivity(resultIntent);
                        }
                    });
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
