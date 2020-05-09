package com.example.auroruspolar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;

import src.com.b07.database.DatabaseDriverAndroid;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.enums.Roles;
import src.com.b07.users.Admin;
import src.com.b07.users.Customer;
import src.com.b07.validate.ValidateSales;

public class LoginActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    final DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(this);
    // final DatabaseInsertHelperAndroid db = new DatabaseInsertHelperAndroid(this);
    final DatabaseSelectHelperAndroid sdb = new DatabaseSelectHelperAndroid(this);
    SQLiteDatabase writableDatabase = mydb.getReadableDatabase();

    Button adminLogin = (Button) findViewById(R.id.userLogin);

    Intent transition = new Intent(getApplicationContext(), UserScreen.class);

    adminLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent transition = new Intent(getApplicationContext(), UserMenu.class);

        boolean validLogin = false;
        //EditText name = (EditText) findViewById(R.id.newAdminName);
        //EditText age = (EditText) findViewById(R.id.newAdminAge);
        EditText id = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        int userId;
        int roleId;

        if (id.getText().toString().equals(null) || id.getText().toString().isEmpty()) {
          userId = -1;
        } else {
          userId = Integer.parseInt(id.getText().toString());
        }

        try {
          if (ValidateSales.isValidUserIdAndroid(userId, sdb)) {
            roleId = sdb.getUserRoleIdAndroid(userId);
            if (sdb.getRoleNameAndroid(roleId).equals(Roles.CUSTOMER.toString())) {
              Customer customer = (Customer) sdb.getUserDetailsAndroid(userId);

              if (customer.authenticateAndroid(password.getText().toString(), sdb)) {
                validLogin = true;
              } else {
                password.setError("Invalid Password!");
              }
            } else {
              id.setError("User with ID " + userId + " isn't an admin!");
            }
          } else {
            id.setError("Invalid ID!");
            validLogin = false;
          }
        } catch (SQLException e) {
          id.setError("Invalid ID!");
        }

        if (validLogin) {
          transition.putExtra("id", Integer.toString(userId));
          startActivity(transition);
          finish();
        }

      }
    });

  }
}
