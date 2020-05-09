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
import src.com.b07.exceptions.DatabaseInsertException;
import src.com.b07.exceptions.InvalidAddressException;
import src.com.b07.exceptions.InvalidAgeException;
import src.com.b07.exceptions.InvalidNameException;
import src.com.b07.exceptions.InvalidPasswordException;
import src.com.b07.exceptions.InvalidRoleException;
import src.com.b07.exceptions.InvalidRoleIdException;
import src.com.b07.exceptions.InvalidUserIdException;

public class NewEmployee extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_employee);

    final DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(this);
    final DatabaseInsertHelperAndroid db = new DatabaseInsertHelperAndroid(this);
    final DatabaseSelectHelperAndroid sdb = new DatabaseSelectHelperAndroid(this);
    SQLiteDatabase writableDatabase = mydb.getWritableDatabase();

    Button toMain = (Button) findViewById(R.id.newEmployeeNext);
    final Intent transition = new Intent(this, MainActivity.class);

    toMain.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        boolean employeeInserted = false;
        int employeeId = 0;

        EditText name = (EditText) findViewById(R.id.newEmployeeName);
        EditText age = (EditText) findViewById(R.id.newEmployeeAge);
        EditText address = (EditText) findViewById(R.id.newEmployeeAddress);
        EditText password = (EditText) findViewById(R.id.newEmployeePassword);

        try {
          //inserted = true;

          int insertAge;
          if (age.getText().toString().equals(null) || age.getText().toString().isEmpty()) {
            insertAge = -1;
          } else {
            insertAge = Integer.parseInt(age.getText().toString());
          }
          employeeId = db.insertNewUserAndroid(name.getText().toString(), insertAge,
              address.getText().toString(), password.getText().toString());
          //int adminRoleId = db.insertUserRoleAndroid(Roles.ADMIN.toString());

          //db.insertNewUserAndroid(adminId, adminRoleId);
          employeeInserted = employeeId != 0;

        } catch (InvalidNameException e) {
          name.setError("Invalid name! Try again.");
        } catch (DatabaseInsertException e) {
          // Error Accessing Database
          System.out.print("Problem adding to database. Try Again");
        } catch (SQLException e) {
          // Error Accessing Database
          System.out.print("Problem accessing to database. Try Again");
        } catch (InvalidAddressException e) {
          address.setError("Invalid Address! Try again.");
        } catch (InvalidAgeException e) {
          age.setError("Invalid age! Try again.");
        } catch (InvalidPasswordException e) {
          password.setError("Invalid Password! Trey again.");
        }

        if (employeeInserted) {
          try {
            int employeeRoleId = db.insertRoleAndroid(Roles.EMPLOYEE.toString(), sdb);
            int userRole = db.insertUserRoleAndroid(employeeId, employeeRoleId, sdb);
          } catch (DatabaseInsertException e) {
            e.printStackTrace();
          } catch (SQLException e) {
            e.printStackTrace();
          } catch (InvalidRoleException e) {
            e.printStackTrace();
          } catch (InvalidRoleIdException e) {
            e.printStackTrace();
          } catch (InvalidUserIdException e) {
            e.printStackTrace();
          }
          startActivity(transition);
          finish();
        }

      }
    });

  }
}
