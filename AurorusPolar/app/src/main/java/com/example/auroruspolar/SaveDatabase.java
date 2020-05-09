package com.example.auroruspolar;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.SQLException;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.users.Admin;

public class SaveDatabase extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_save_database);
    Button submit = (Button) findViewById(R.id.confirmation);

    Toast.makeText(getApplicationContext(), "Successfully saved database at new location",
        Toast.LENGTH_LONG).show();
    super.onBackPressed();


  }
}
