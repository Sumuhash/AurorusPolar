package com.example.auroruspolar;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoadDatabase extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_load_database);

    Button load = (Button) findViewById(R.id.confirmation);
    EditText path = (EditText) findViewById(R.id.id);


  }
}
