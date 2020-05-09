package com.example.auroruspolar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import src.com.b07.database.DatabaseDriver;
import src.com.b07.database.DatabaseDriverAndroid;
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

public class MakeNewUser extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_employee);
    TextView title = (TextView) findViewById(R.id.title);
    title.setText("Create new User");
    final DatabaseDriverAndroid db = new DatabaseDriverAndroid(this);
    db.getReadableDatabase();
    final DatabaseSelectHelperAndroid sdb = new DatabaseSelectHelperAndroid(this);
    final DatabaseInsertHelperAndroid idb = new DatabaseInsertHelperAndroid(this);
    Intent temp = getIntent();
    final int id = Integer.parseInt(temp.getStringExtra("id"));

    Button userNext = (Button) findViewById(R.id.newEmployeeNext);
    final Intent transition = new Intent(getApplicationContext(), EmployeeScreen.class);
    final String[] name = new String[1];
    name[0] = "";

    userNext.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        EditText name = (EditText) findViewById(R.id.newEmployeeName);
        EditText age = (EditText) findViewById(R.id.newEmployeeAge);
        EditText address = (EditText) findViewById(R.id.newEmployeeAddress);
        EditText password = (EditText) findViewById(R.id.newEmployeePassword);

        try {
          Inventory inventory = sdb.getInventoryAndroid();
          Employee employee = (Employee) sdb.getUserDetailsAndroid(id);
          EmployeeInterface employeeInterface =
              new EmployeeInterface(employee, inventory);
          Integer customerId = employeeInterface
              .createCustomerAndroid(name.getText().toString(),
                  Integer.parseInt(age.getText().toString()),
                  password.getText().toString(), password.getText().toString(), idb, sdb);
          confirmationMessage(true, sdb.getUserDetailsAndroid(customerId).getName(), customerId);

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
      }
    });
  }

  public void confirmationMessage(boolean value, String name, int customerId) {
    if (value) {
      Toast.makeText(getApplicationContext(), name + " is now a User, with ID " + customerId,
          Toast.LENGTH_LONG).show();
      super.onBackPressed();
    } else {
      Toast.makeText(getApplicationContext(), "Not Successful", Toast.LENGTH_LONG).show();
      super.onBackPressed();

    }
  }
}
