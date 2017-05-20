package si.example.kamna.care;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabh on 5/18/2017.
 */

public class ViewPatient extends AppCompatActivity
{

    List<Prescription> prescriptions;

    ListView listView;

    DBHelper dbHelper;
    TextView name, age, number, wh;

    Button addPresc;

    AlertDialog prescDiaglog;
    View prescView;

    AlertDialog prescDetailDialog;
    View prescDetailView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient);
        dbHelper = new DBHelper(this, "caredb", null, 3);

        prescriptions = new ArrayList<>();

        name = (TextView)findViewById(R.id.txt_nm);
        age = (TextView)findViewById(R.id.txt_ag);
        number = (TextView)findViewById(R.id.txt_nu);
        wh = (TextView)findViewById(R.id.txt_wh);
        addPresc = (Button)findViewById(R.id.btn_addPresc);
        listView = (ListView)findViewById(R.id.ap_prescList);
        updateList();

        init_listview();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        prescView = inflater.inflate(R.layout.add_presc_layout, null);
        builder.setView(prescView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText prob, med, dos;
                prob = (EditText)prescView.findViewById(R.id.ap_prob);
                med = (EditText)prescView.findViewById(R.id.ap_med);
                dos = (EditText)prescView.findViewById(R.id.ap_dos);

                dbHelper.insertPrescription(prob.getText().toString(), med.getText().toString(), dos.getText().toString());
                updateList();
                init_listview();
            }
        });

        builder.setTitle("Add Prescription");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                prescDiaglog.dismiss();
            }
        });

        builder.setCancelable(true);
        prescDiaglog = builder.create();

        addPresc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                prescDiaglog.show();
            }
        });

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        prescDetailView = inflater.inflate(R.layout.presc_details, null);
        builder1.setView(prescDetailView);

        builder1.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                prescDetailDialog.dismiss();
            }
        });

        builder1.setTitle("Prescription Details");
        prescDetailDialog = builder1.create();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                EditText prob, med, dos;
                prob = (EditText)prescDetailView.findViewById(R.id.prd_prob);
                med = (EditText)prescDetailView.findViewById(R.id.prd_med);
                dos = (EditText)prescDetailView.findViewById(R.id.prd_dos);

                prob.setEnabled(false);
                med.setEnabled(false);
                dos.setEnabled(false);

                prob.setText(prescriptions.get(i).prob);
                med.setText(prescriptions.get(i).med);
                dos.setText(prescriptions.get(i).dos);

                prescDetailDialog.show();

            }
        });



        Cursor cursor = dbHelper.getPatient(getIntent().getExtras().getInt("pat_id"));
        cursor.moveToFirst();

        if(cursor.getCount()> 0)
        {
            name.setText(cursor.getString(cursor.getColumnIndex("name")));
            age.setText(cursor.getString(cursor.getColumnIndex("age")));
            number.setText(cursor.getString(cursor.getColumnIndex("number")));
            String str_wh = cursor.getString(cursor.getColumnIndex("weight")) + " kgs" + " / " + cursor.getString(cursor.getColumnIndex("height")) + " cm";
            wh.setText(str_wh);
        }
    }

    void updateList()
    {
        prescriptions.clear();
        Cursor cursor = dbHelper.getAllPrescriptions();

        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();

            for(int i = 0; i < cursor.getCount(); i++)
            {
                Prescription prescription = new Prescription();

                prescription.prob = cursor.getString(cursor.getColumnIndex("prob"));
                prescription.med = cursor.getString(cursor.getColumnIndex("med"));
                prescription.dos = cursor.getString(cursor.getColumnIndex("dos"));
                prescription.time = cursor.getString(cursor.getColumnIndex("t"));

                prescriptions.add(prescription);
                cursor.moveToNext();
            }

        }

    }

    void init_listview()
    {

        List<String> temp_list = new ArrayList<>();

        for(int i = 0; i < prescriptions.size(); i++)
        {
            temp_list.add(prescriptions.get(i).time);
        }
        listView.setAdapter(null);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.txt_time,temp_list));

    }

    private class Prescription
    {
        public String prob, med, dos, time;
    }

}
