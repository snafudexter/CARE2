package testapplication.com.testapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
public class AfterLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_after_login);


        Bundle bundle_value=getIntent().getBundleExtra("bundle");

        String name= bundle_value.getString("name_key");
        String qual= bundle_value.getString("qual_key");
        String marks= bundle_value.getString("marks_key");

        Log.d("values","name: "+ name +" qual: "+ qual);
    }

}
