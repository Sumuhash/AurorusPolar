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
import src.com.b07.database.helper.DatabaseInsertHelperAndroid;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.enums.Roles;
import src.com.b07.users.Admin;
import src.com.b07.validate.ValidateSales;

public class AdminLogin extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_login);

    final DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(this);
    // final DatabaseInsertHelperAndroid db = new DatabaseInsertHelperAndroid(this);
    final DatabaseSelectHelperAndroid sdb = new DatabaseSelectHelperAndroid(this);
    SQLiteDatabase writableDatabase = mydb.getReadableDatabase();

    Button adminLogin = (Button) findViewById(R.id.adminLogin);

    Intent transition = new Intent(getApplicationContext(), AdminScreen.class);

    adminLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent transition = new Intent(getApplicationContext(), AdminScreen.class);

        boolean validLogin = false;
        //EditText name = (EditText) findViewById(R.id.newAdminName);
        //EditText age = (EditText) findViewById(R.id.newAdminAge);
        EditText id = (EditText) findViewById(R.id.adminUsername);
        EditText password = (EditText) findViewById(R.id.adminPassword);

        int adminId;
        int roleId;

        if (id.getText().toString().equals(null) || id.getText().toString().isEmpty()) {
          adminId = -1;
        } else {
          adminId = Integer.parseInt(id.getText().toString());
        }

        try {
          if (ValidateSales.isValidUserIdAndroid(adminId, sdb)) {
            roleId = sdb.getUserRoleIdAndroid(adminId);
            if (sdb.getRoleNameAndroid(roleId).equals(Roles.ADMIN.toString())) {
              Admin admin = (Admin) sdb.getUserDetailsAndroid(adminId);

              if (admin.authenticateAndroid(password.getText().toString(), sdb)) {
                validLogin = true;
              } else {
                password.setError("Invalid Password!");
              }
            } else {
              id.setError("User with ID " + adminId + " isn't an admin!");
            }
          } else {
            id.setError("Invalid ID!");
            validLogin = false;
          }
        } catch (SQLException e) {
          id.setError("Invalid ID!");
        }

        if (validLogin) {
          startActivity(transition);
          finish();
        }

      }
    });
  }
}
