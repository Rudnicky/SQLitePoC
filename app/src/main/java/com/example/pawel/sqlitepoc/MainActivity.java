package com.example.pawel.sqlitepoc;

import android.database.sqlite.SQLiteCursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    // initialize variables within the butterknife
    @BindView(R.id.insertButton) Button insertButton;
    @BindView(R.id.readDataButton) Button readDataButton;
    @BindView(R.id.updateButton) Button updateButton;
    @BindView(R.id.deleteButton) Button deleteButton;
    @BindView(R.id.capacityEditText) EditText capacityEditText;
    @BindView(R.id.nameEditText) EditText nameEditText;
    @BindView(R.id.idEditText) EditText idEditText;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        db = new DatabaseHelper(this);
    }

    @OnClick(R.id.readDataButton)
    public void onReadDataButonClicked() {
        SQLiteCursor cursor = db.readData();
        if (cursor.getCount() > 0 ) {
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                buffer.append("ID: " + cursor.getString(0) + "\n");
                buffer.append("Name: " + cursor.getString(1) + "\n");
                buffer.append("Capacity: " + cursor.getString(2) + "\n");
            }
            showRecords("Record", buffer.toString());
        } else {
            showRecords("Error", "There's no records");
        }
    }

    @OnClick(R.id.insertButton)
    public void onInsertButonClicked() {
        if (idEditText.getText().toString().matches("")) {
            Toast.makeText(MainActivity.this, "No Data to insert", Toast.LENGTH_LONG).show();
        } else {
            boolean isDataInserted =  db.writeData(nameEditText.getText().toString(),
                    Integer.parseInt(capacityEditText.getText().toString()));
            if (isDataInserted) {
                Toast.makeText(MainActivity.this, "Data inserted!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Data NOT inserted!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.updateButton)
    public void onUpdateButonClicked() {
        if (idEditText.getText().toString().matches("")) {
            Toast.makeText(MainActivity.this, "No Data to update", Toast.LENGTH_LONG).show();
        } else {
            boolean isDataUpdated =  db.updateData(
                    idEditText.getText().toString(),
                    nameEditText.getText().toString(),
                    Integer.parseInt(capacityEditText.getText().toString()));
            if (isDataUpdated) {
                Toast.makeText(MainActivity.this, "Data updated!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Data NOT updated!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.deleteButton)
    public void onDeleteButonClicked() {
        boolean isDataDeleted =  db.deleteData(idEditText.getText().toString());
        if (isDataDeleted) {
            Toast.makeText(MainActivity.this, "Data deleted!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Data NOT deleted!", Toast.LENGTH_LONG).show();
        }
    }

    private void showRecords(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
