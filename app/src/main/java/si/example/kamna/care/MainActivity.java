package si.example.kamna.care;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_login, et_pwd;
    Button bt_login;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;

        bt_login= (Button) findViewById(R.id.bt_login);
        ;

        et_login= (EditText) findViewById(R.id.et_id);
        et_pwd= (EditText) findViewById(R.id.et_pwd);

        bt_login.setOnClickListener(this);

        Toast.makeText(this,"Create call",Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"Start call",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        Toast.makeText(this,"Resume call",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {

        Toast.makeText(this,"Pause call",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        Toast.makeText(this,"Stop call",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        Toast.makeText(this,"Restart call",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this,"Destroy call",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {

            case R.id.bt_login:
                String id_val=et_login.getText().toString().trim();
                String pwd_val=et_pwd.getText().toString().trim();
                if((id_val.length() > 0 ) && (pwd_val.length() >0 ))
                {

                    if(id_val.equals("admin") && pwd_val.equals("admin"))
                    {
                        Intent intent=new Intent(mContext,AfterLogin.class);

                        Bundle bundle_var=new Bundle();
                        bundle_var.putString("name_key","Kamna");
                        bundle_var.putString("qual_key","abc");
                        bundle_var.putString("marks_key","123");

                        intent.putExtra("bundle",bundle_var);

                        startActivity(intent);
                    }else{
                        //  Toast.makeText(mContext,"Invalid credentials",Toast.LENGTH_SHORT).show();
                        et_login.setError("Invalid credentials");
                        et_pwd.setError("Invalid credentials");


                        //Show message invalid credentials
                    }
                }else{
                    Log.d("value","check");
                    Toast.makeText(mContext,"Fill the fields first",Toast.LENGTH_SHORT).show();
                    //Show message to fill the fields first
                }
                break;
        }


    }
}
