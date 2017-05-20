package si.example.kamna.care;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Prabh on 5/18/2017.
 */

public class AddPatient extends AppCompatActivity {

    EditText ename, eage, enumber, eweight, eheight, eemail, ebp, ediab, eprobs;
    Button btn;

    int doc_id;

    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pnt_info);

        doc_id = getIntent().getExtras().getInt("doc_id");


        ename = (EditText)findViewById(R.id.name);
        eage = (EditText)findViewById(R.id.age);
        enumber = (EditText)findViewById(R.id.et_num);
        eweight = (EditText)findViewById(R.id.et_wt);
        eheight = (EditText)findViewById(R.id.et_ht);
        eemail = (EditText)findViewById(R.id.et_id);
        ebp = (EditText)findViewById(R.id.et_bp);
        ediab = (EditText)findViewById(R.id.et_db);
        eprobs = (EditText)findViewById(R.id.et_dp);

        dbHelper = new DBHelper(this, "caredb", null, 3);

        if(getIntent().getExtras().getBoolean("for_update") == true)
        {
            //getPat

            Cursor cursor = dbHelper.getPatient(getIntent().getExtras().getInt("pat_id"));
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                ename.setText(cursor.getString(cursor.getColumnIndex("name")));
                eage.setText(cursor.getString(cursor.getColumnIndex("age")));
                enumber.setText(cursor.getString(cursor.getColumnIndex("number")));
                eweight.setText(cursor.getString(cursor.getColumnIndex("weight")));
                eheight.setText(cursor.getString(cursor.getColumnIndex("height")));
                eemail.setText(cursor.getString(cursor.getColumnIndex("email")));
                ebp.setText(cursor.getString(cursor.getColumnIndex("bp")));
                ediab.setText(cursor.getString(cursor.getColumnIndex("diab")));
                eprobs.setText(cursor.getString(cursor.getColumnIndex("probs")));

            }



        }

        btn = (Button)findViewById(R.id.bt_done);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getExtras().getBoolean("for_update") == true)
                {
                    dbHelper.deletePatient(getIntent().getExtras().getInt("pat_id"));
                }

                String name = ename.getText().toString();
                String probs = eprobs.getText().toString();
                String email = eemail.getText().toString();
                int age = Integer.parseInt(eage.getText().toString());
                long number = Long.parseLong(enumber.getText().toString());
                int weight = Integer.parseInt(eweight.getText().toString());
                int height = Integer.parseInt(eheight.getText().toString());
                int bp = Integer.parseInt(ebp.getText().toString());
                int diab = Integer.parseInt(ediab.getText().toString());

                dbHelper.insertPatient(name, age, number, weight, height, email, bp, diab,probs, doc_id);

                end();

            }
        });


    }

    void end()
    {
        finish();
    }
}
