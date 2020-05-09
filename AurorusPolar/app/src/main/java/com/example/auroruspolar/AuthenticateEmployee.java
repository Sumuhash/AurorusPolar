package com.example.auroruspolar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;

import src.com.b07.database.DatabaseDriverAndroid;
import src.com.b07.database.helper.DatabaseInsertHelper;
import src.com.b07.database.helper.DatabaseInsertHelperAndroid;
import src.com.b07.database.helper.DatabaseSelectHelper;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
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

public class AuthenticateEmployee extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_authenticate_employee);

    DatabaseDriverAndroid db = new DatabaseDriverAndroid(this);
    db.getReadableDatabase();
    final DatabaseSelectHelperAndroid sdb = new DatabaseSelectHelperAndroid(this);
    final DatabaseInsertHelperAndroid idb = new DatabaseInsertHelperAndroid(this);
    final Button authenticate = (Button) findViewById(R.id.authenticate);
    Intent temp = getIntent();
    final int id = Integer.parseInt(temp.getStringExtra("id"));

    authenticate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent transition = new Intent(getApplicationContext(), EmployeeLogin.class);
        EditText name = (EditText) findViewById(R.id.employeeName);
        EditText address = (EditText) findViewById(R.id.employeeAddress);
        EditText age = (EditText) findViewById(R.id.employeeAge);
        EditText password = (EditText) findViewById(R.id.employeePassword);
        //boolean isValidEmployee = false;
        try {

          Inventory inventory = sdb.getInventoryAndroid();
          Employee employee = (Employee) sdb.getUserDetailsAndroid(id);
          EmployeeInterface employeeInterface =
              new EmployeeInterface(employee, inventory);
          int newEmployeeId = employeeInterface.createEmployeeAndroid(name.getText().toString(),
              Integer.parseInt(age.getText().toString()), password.getText().toString(),
              password.getText().toString(), idb, sdb);
          //isValidEmployee = true;

        } catch (SQLException e) {
          e.printStackTrace();
        } catch (DatabaseInsertException e) {
          e.printStackTrace();

        } catch (InvalidNameException e) {
          name.setError("Invalid id");

        } catch (InvalidAddressException e) {
          address.setError("Invalid id");

        } catch (InvalidPasswordException e) {
          password.setError("Invalid password");

        } catch (InvalidAgeException e) {
          age.setError("Invalid age");

        } catch (InvalidUserIdException e) {
          name.setError("Invalid Id");

        } catch (InvalidRoleException e) {
          e.printStackTrace();

        } catch (InvalidRoleIdException e) {
          e.printStackTrace();
        }
        startActivity(transition);
        finish();
      }
    });

  }
}
