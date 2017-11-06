package com.example.guedes.sqlitecrud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.guedes.sqlitecrud.Helper.DBHelper;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnDelete, btnUpdate;
    EditText edtEmail;

    ListView lstEmail;

    String saveEmail = ""; //salvar o email atual para atualizar/deletar


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SQLiteDatabase.loadLibs(this);

        //iniciar view
        lstEmail = findViewById(R.id.lstEmail);
        lstEmail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) lstEmail.getItemAtPosition(i);
                edtEmail.setText(item);
                saveEmail = item;
            }
        });

        edtEmail = findViewById(R.id.edtEmail);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.getInstance(MainActivity.this).insertNewEmail(edtEmail.getText().toString());
                reloadEmail();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.getInstance(MainActivity.this).updateEmail(saveEmail, edtEmail.getText().toString());
                reloadEmail();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.getInstance(MainActivity.this).deleteEmail(edtEmail.getText().toString());
                reloadEmail();
            }
        });

    }

    private void reloadEmail() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,DBHelper.getInstance(this).getAllEmail());

        lstEmail.setAdapter(adapter);
    }
}
