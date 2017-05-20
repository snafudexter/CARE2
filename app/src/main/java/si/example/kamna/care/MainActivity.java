package si.example.kamna.care;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;

        dbHelper = new DBHelper(this, "caredb", null, 3);
        dbHelper.insertDoc("prabh@live.in", "parker");

        bt_login= (Button) findViewById(R.id.bt_login);


        et_login= (EditText) findViewById(R.id.et_login);
        et_pwd= (EditText) findViewById(R.id.et_pwd);

        bt_login.setOnClickListener(this);

        Toast.makeText(this,"Create call",Toast.LENGTH_SHORT).show();
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
                    Cursor cursor = dbHelper.getDoc(id_val);
                    cursor.moveToFirst();
                    int c = cursor.getCount();
                    if(cursor.getCount()> 0)
                    {
                        String ps = cursor.getString(cursor.getColumnIndex("pass"));
                        if( ps.compareTo(pwd_val) == 0)
                        {
                            Intent i = new Intent(this, DoctorPanel.class);

                            Bundle b = new Bundle();
                            b.putInt("doc_id", cursor.getInt(cursor.getColumnIndex("id")));
                            i.putExtras(b);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    }

                break;
        }


    }
}
