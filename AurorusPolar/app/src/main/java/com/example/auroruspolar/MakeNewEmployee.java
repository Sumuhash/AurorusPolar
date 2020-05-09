package com.example.auroruspolar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
import src.com.b07.inventory.Inventory;
import src.com.b07.store.EmployeeInterface;
import src.com.b07.users.Employee;

public class MakeNewEmployee extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_employee);
    TextView title = (TextView) findViewById(R.id.title);
    title.setText("Create new Employee");

    final DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(this);
    final DatabaseInsertHelperAndroid idb = new DatabaseInsertHelperAndroid(this);
    final DatabaseSelectHelperAndroid sdb = new DatabaseSelectHelperAndroid(this);

    SQLiteDatabase writableDatabase = mydb.getWritableDatabase();
    Intent temp = getIntent();
    final int id = Integer.parseInt(temp.getStringExtra("id"));

    Button toMain = (Button) findViewById(R.id.newEmployeeNext);
    final String[] name = new String[1];
    name[0] = "";

    toMain.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        boolean employeeInserted = false;
        int employeeId = 0;
        Intent transition = new Intent(getApplicationContext(), EmployeeScreen.class);

        EditText name = (EditText) findViewById(R.id.newEmployeeName);
        EditText age = (EditText) findViewById(R.id.newEmployeeAge);
        EditText address = (EditText) findViewById(R.id.newEmployeeAddress);
        EditText password = (EditText) findViewById(R.id.newEmployeePassword);

        try {
          //inserted = true;
          int insertAge = 0;
          if (age.getText().toString().equals(null) || age.getText().toString().isEmpty()) {
            insertAge = -1;
          } else {
            insertAge = Integer.parseInt(age.getText().toString());
          }
          Inventory inventory = sdb.getInventoryAndroid();
          Employee employee = (Employee) sdb.getUserDetailsAndroid(id);
          EmployeeInterface employeeInterface =
              new EmployeeInterface(employee, inventory);
          int newEmployeeId = employeeInterface.createEmployeeAndroid(name.getText().toString(),
              Integer.parseInt(age.getText().toString()), password.getText().toString(),
              password.getText().toString(), idb, sdb);
          //int adminRoleId = db.insertUserRoleAndroid(Roles.ADMIN.toString());
          //confirmationMessage(true, "kiki", employeeId);
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
        } catch (InvalidRoleException e) {
          e.printStackTrace();
        } catch (InvalidRoleIdException e) {
          e.printStackTrace();
        } catch (InvalidUserIdException e) {
          e.printStackTrace();
        }

        if (employeeInserted) {
          try {
            int employeeRoleId = idb.insertRoleAndroid(Roles.EMPLOYEE.toString(), sdb);
            int userRole = idb.insertUserRoleAndroid(employeeId, employeeRoleId, sdb);
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

  public void confirmationMessage(boolean value, String name, int employeeId) {
    if (value) {
      Toast.makeText(getApplicationContext(), name + " is now a User, with ID " + employeeId,
          Toast.LENGTH_LONG).show();
      super.onBackPressed();
    } else {
      Toast.makeText(getApplicationContext(), "Not Successful", Toast.LENGTH_LONG).show();
      super.onBackPressed();

    }
  }
}
