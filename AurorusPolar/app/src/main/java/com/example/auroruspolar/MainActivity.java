package com.example.auroruspolar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import src.com.b07.users.Admin;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button createNewEmAd = (Button) findViewById(R.id.createNewEmAd);
    Button adminLogin = (Button) findViewById(R.id.adminButton);
    Button employeeLogin = (Button) findViewById(R.id.employeeButton);
    Button userLogin = (Button) findViewById(R.id.userButton);

    createNewEmAd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent transition = new Intent(getApplicationContext(), NewAdmin.class);
        startActivity(transition);
        finish();
      }
    });

    adminLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent transition = new Intent(getApplicationContext(), AdminLogin.class);
        startActivity(transition);
        finish();
      }
    });

    userLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent transition = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(transition);
        finish();
      }
    });

    employeeLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent transition = new Intent(getApplicationContext(), EmployeeLogin.class);
        startActivity(transition);
        finish();
      }
    });

  }
}
