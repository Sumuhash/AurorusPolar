package com.example.auroruspolar;

import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EmployeeScreen extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_employee_screen);

    Button makeNewEmployee = (Button) findViewById(R.id.employee);
    Button authenticate = (Button) findViewById(R.id.auth);
    Button logOff = (Button) findViewById(R.id.logOff);
    Button newUser = (Button) findViewById(R.id.user);
    Button restock = (Button) findViewById(R.id.stock);
    Button newAccount = (Button) findViewById(R.id.account);
    Intent temp = getIntent();
    String name = temp.getStringExtra("name");

    TextView employeeName = (TextView) findViewById(R.id.name);
    employeeName.setText(name);

    makeNewEmployee.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent transition = new Intent(getApplicationContext(), AuthenticateEmployee.class);
        Intent temp = getIntent();
        transition.putExtra("id", temp.getStringExtra("id"));
        startActivity(transition);

      }
    });
    authenticate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent transition = new Intent(getApplicationContext(), AuthenticateEmployee.class);
        Intent temp = getIntent();
        transition.putExtra("id", temp.getStringExtra("id"));
        startActivity(transition);
      }
    });
    logOff.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent transition = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(transition);
        finish();
      }
    });
    newUser.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent transition = new Intent(getApplicationContext(), MakeNewUser.class);
        Intent temp = getIntent();
        transition.putExtra("id", temp.getStringExtra("id"));
        startActivity(transition);

      }
    });
    restock.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent transition = new Intent(getApplicationContext(), RestockInventory.class);
        Intent temp = getIntent();
        transition.putExtra("id", temp.getStringExtra("id"));
        startActivity(transition);
      }
    });
    newAccount.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent transition = new Intent(getApplicationContext(), MakeNewAccount.class);
        startActivity(transition);

      }
    });

  }
}
