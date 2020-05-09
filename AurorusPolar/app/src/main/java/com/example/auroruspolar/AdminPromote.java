package com.example.auroruspolar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.database.helper.DatabaseUpdateHelperAndroid;
import src.com.b07.enums.Roles;
import src.com.b07.exceptions.InvalidRoleIdException;
import src.com.b07.exceptions.InvalidUserIdException;
import src.com.b07.users.Employee;
import src.com.b07.validate.ValidateSales;

public class AdminPromote extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admin_promote);
    final DatabaseSelectHelperAndroid sdb = new DatabaseSelectHelperAndroid(this);
    final DatabaseUpdateHelperAndroid udb = new DatabaseUpdateHelperAndroid(this);
    final EditText id = (EditText) findViewById(R.id.id);
    Button submit = (Button) findViewById(R.id.confirmation);
    final boolean[] result = {false};
    final Employee[] employee = {null};
    final String[] name = new String[1];
    name[0] = "";

    submit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int employeeId;
        int roleId;
        boolean validEmployee;

        if (id.getText().toString().equals(null) || id.getText().toString().isEmpty()) {
          employeeId = -1;
        } else {
          try {
            employeeId = Integer.parseInt(id.getText().toString());
          } catch (NumberFormatException e) {
            employeeId = -1;
          }

        }

        try {
          if (ValidateSales.isValidUserIdAndroid(employeeId, sdb)) {
            roleId = sdb.getUserRoleIdAndroid(employeeId);
            if (sdb.getRoleNameAndroid(roleId).equals(Roles.EMPLOYEE.toString())) {
              Employee employee = (Employee) sdb.getUserDetailsAndroid(employeeId);
              validEmployee = true;
              result[0] = udb
                  .updateUserRoleAndroid(sdb.getUserRoleIdAndroid(employeeId), employeeId, sdb);
            } else {
              id.setError("User with ID " + employeeId + " isn't an admin!");
            }
          } else {
            id.setError("Invalid ID!");
            validEmployee = false;
          }
        } catch (SQLException e) {
          id.setError("Invalid ID!");
        } catch (InvalidRoleIdException e) {
          e.printStackTrace();
        } catch (InvalidUserIdException e) {
          e.printStackTrace();
        }

                /*
                try {
                    employee[0] = (Employee) sdb.getUserDetailsAndroid(Integer.parseInt(id.getText().toString()));
                    name[0] = employee[0].getName();
                } catch (SQLException e) {
                }
                if (employee[0] != null){
                    int employeeId = employee[0].getRoleId();
                    try {
                        result[0] = udb.updateUserRoleAndroid(employee[0].getRoleId(), employeeId);
                    } catch (SQLException e){
                    } catch (InvalidUserIdException e) {
                    } catch (InvalidRoleIdException e) {
                    }
                }*/

        if (result[0]) {
          confirmationMessage(true, name[0]);
        } else {
          confirmationMessage(false, name[0]);


        }
      }

    });

  }

  public void confirmationMessage(boolean value, String name) {
    if (value == true) {
      Toast.makeText(getApplicationContext(), name + " is now an Admin", Toast.LENGTH_LONG).show();
      super.onBackPressed();
    } else {
      Toast.makeText(getApplicationContext(), "Not Successful", Toast.LENGTH_LONG).show();
      super.onBackPressed();

    }
  }
}
