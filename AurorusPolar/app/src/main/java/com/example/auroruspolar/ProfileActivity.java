package com.example.auroruspolar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

import src.com.b07.database.helper.DatabaseSelectHelperAndroid;

public class ProfileActivity extends AppCompatActivity {

  DatabaseSelectHelperAndroid sdb = new DatabaseSelectHelperAndroid(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admin_profile);
    Intent temp = getIntent();
    final int idTemp = Integer.parseInt(temp.getStringExtra("id"));

    TextView name = (TextView) findViewById(R.id.name);
    TextView age = (TextView) findViewById(R.id.age);
    TextView address = (TextView) findViewById(R.id.address);
    TextView id = (TextView) findViewById(R.id.id);
    try {
      name.setText(sdb.getUserDetailsAndroid(idTemp).getName());
      id.setText(Integer.toString(idTemp));
      address.setText(sdb.getUserDetailsAndroid(idTemp).getAddress());
      age.setText(Integer.toString(sdb.getUserDetailsAndroid(idTemp).getAge()));

    } catch (SQLException e) {
    }


  }
}
