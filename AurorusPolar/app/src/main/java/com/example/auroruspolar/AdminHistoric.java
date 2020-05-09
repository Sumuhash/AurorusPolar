package com.example.auroruspolar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.SQLException;

import src.com.b07.database.DatabaseDriverAndroid;
import src.com.b07.database.helper.DatabaseInsertHelperAndroid;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.users.Admin;

public class AdminHistoric extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admin_historic);

    final DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(this);
    final DatabaseInsertHelperAndroid db = new DatabaseInsertHelperAndroid(this);
    final DatabaseSelectHelperAndroid sdb = new DatabaseSelectHelperAndroid(this);
    Intent intent = getIntent();
    final int idTemp = Integer.parseInt(intent.getStringExtra("id"));
    String active = "";
    try {
      active = Admin.listInactiveUsersAndroid(idTemp, sdb);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    TextView printActive = (TextView) findViewById(R.id.information);
    TextView name = (TextView) findViewById(R.id.name);
    printActive.setText(active);
    try {
      printActive.setText(sdb.getUserDetailsAndroid(idTemp).getName());
    } catch (SQLException e) {
    }

  }


}

