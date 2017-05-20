package si.example.kamna.care;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabh on 5/18/2017.
 */

public class DoctorPanel extends AppCompatActivity {

    TextView add_pat;
    TextView update_info;
    TextView lst_pat;
    TextView vp;

    int doc_id;
    int pat_id;

    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.doctor_panel);

        dbHelper = new DBHelper(this, "caredb", null, 3);

        doc_id = getIntent().getExtras().getInt("doc_id");
        pat_id = 0;

        add_pat = (TextView)findViewById(R.id.txt_add_pat);
        update_info = (TextView)findViewById(R.id.txt_up_info);
        lst_pat = (TextView)findViewById(R.id.txt_lst_pat);
        vp = (TextView)findViewById(R.id.txt_vp);

        add_pat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getBaseContext(), AddPatient.class);

                Bundle b = new Bundle();
                b.putInt("doc_id", doc_id);
                b.putBoolean("for_update", false);
                i.putExtras(b);
                startActivity(i);
            }
        });

        update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pat_id == 0)
                {
                    Toast.makeText(getBaseContext(), "First select a patient from View Patient menu!", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Intent i = new Intent(getBaseContext(), AddPatient.class);

                    Bundle b = new Bundle();
                    b.putInt("doc_id", doc_id);
                    b.putBoolean("for_update", true);
                    b.putInt("pat_id", pat_id);
                    i.putExtras(b);
                    startActivity(i);

                }
            }
        });

        lst_pat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = dbHelper.getAllPatients();
                cursor.moveToFirst();

                final List<String> tempList = new ArrayList<>();
                final List<Integer> patIDs = new ArrayList<>();

                if(cursor.getCount()> 0) {
                    for (int i = 0; i < cursor.getCount(); i++)
                    {
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));

                        tempList.add(name);
                        patIDs.add(id);

                        cursor.moveToNext();
                    }

                }

                if(patIDs.size() > 0) {

                    final CharSequence[] items = tempList.toArray(new CharSequence[tempList.size()]);

                    AlertDialog.Builder builder = new AlertDialog.Builder(DoctorPanel.this);
                    builder.setTitle("Select Patient");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            pat_id = patIDs.get(i);
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        });

        vp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pat_id == 0)
                {
                    Toast.makeText(getBaseContext(), "First select a patient from View Patient menu!", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Intent i = new Intent(getBaseContext(), ViewPatient.class);

                    Bundle b = new Bundle();
                    b.putInt("pat_id", pat_id);
                    i.putExtras(b);
                    startActivity(i);

                }

            }
        });

    }


}
