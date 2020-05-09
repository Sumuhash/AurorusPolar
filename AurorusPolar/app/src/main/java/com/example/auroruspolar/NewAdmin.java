package com.example.auroruspolar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.sql.SQLException;

import src.com.b07.database.DatabaseDriverAndroid;
import src.com.b07.database.helper.DatabaseInsertHelperAndroid;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.enums.ItemTypes;
import src.com.b07.enums.Roles;
import src.com.b07.exceptions.DatabaseInsertException;
import src.com.b07.exceptions.InvalidAddressException;
import src.com.b07.exceptions.InvalidAgeException;
import src.com.b07.exceptions.InvalidItemIdException;
import src.com.b07.exceptions.InvalidItemNameException;
import src.com.b07.exceptions.InvalidNameException;
import src.com.b07.exceptions.InvalidPasswordException;
import src.com.b07.exceptions.InvalidPriceException;
import src.com.b07.exceptions.InvalidQuantityException;
import src.com.b07.exceptions.InvalidRoleException;
import src.com.b07.exceptions.InvalidRoleIdException;
import src.com.b07.exceptions.InvalidUserIdException;


public class NewAdmin extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_admin);

    final DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(this);
    final DatabaseInsertHelperAndroid db = new DatabaseInsertHelperAndroid(this);
    final DatabaseSelectHelperAndroid sdb = new DatabaseSelectHelperAndroid(this);
    SQLiteDatabase writableDatabase = mydb.getWritableDatabase();

    getApplicationContext().deleteDatabase("inventorymgmt.db");

    Button toNewEmployee = (Button) findViewById(R.id.newAdminNext);
    Intent transition = new Intent(getApplicationContext(), NewEmployee.class);

    toNewEmployee.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent transition = new Intent(getApplicationContext(), NewEmployee.class);
        //addData();

        //Initialize ROLES Table
        try {
          db.insertRoleAndroid(Roles.ADMIN.toString(), sdb);
          db.insertRoleAndroid(Roles.EMPLOYEE.toString(), sdb);
          db.insertRoleAndroid(Roles.CUSTOMER.toString(), sdb);

        } catch (DatabaseInsertException e1) {
        } catch (SQLException e1) {
        } catch (InvalidRoleException e1) {
        }

        // Initialize Database with items and empty inventory
        try {
          int fishing_rod = db.insertItemAndroid(ItemTypes.FISHING_ROD.toString(),
              new BigDecimal("25.50"));
          int hockey_stick = db.insertItemAndroid(ItemTypes.HOCKEY_STICK.toString(),
              new BigDecimal("99.99"));
          int protein_bar = db.insertItemAndroid(ItemTypes.PROTEIN_BAR.toString(),
              new BigDecimal("5.25"));
          int shoes = db.insertItemAndroid(ItemTypes.RUNNING_SHOES.toString(),
              new BigDecimal("50.79"));
          int skates =
              db.insertItemAndroid(ItemTypes.SKATES.toString(), new BigDecimal("85.99"));

          // Initialize Inventory
          db.insertInventoryAndroid(fishing_rod, 0, sdb);
          db.insertInventoryAndroid(hockey_stick, 0, sdb);
          db.insertInventoryAndroid(protein_bar, 0, sdb);
          db.insertInventoryAndroid(shoes, 0, sdb);
          db.insertInventoryAndroid(skates, 0, sdb);

        } catch (DatabaseInsertException e) {
        } catch (SQLException e) {
        } catch (InvalidItemNameException e) {
        } catch (InvalidPriceException e) {
        } catch (InvalidItemIdException e) {
        } catch (InvalidQuantityException e) {
        }

        boolean adminInserted = false;
        int adminId = 0;
        EditText name = (EditText) findViewById(R.id.newAdminName);
        EditText age = (EditText) findViewById(R.id.newAdminAge);
        EditText address = (EditText) findViewById(R.id.newAdminAddress);
        EditText password = (EditText) findViewById(R.id.newAdminPassword);
        //TextView temp = (TextView) findViewById(R.id.temp);
        //temp.setText("AhhEE");
        try {
          //inserted = true;
          int insertAge;
          if (age.getText().toString().equals(null) || age.getText().toString().isEmpty()) {
            insertAge = -1;
          } else {
            insertAge = Integer.parseInt(age.getText().toString());
          }
          adminId = db.insertNewUserAndroid(name.getText().toString(), insertAge,
              address.getText().toString(), password.getText().toString());
          //temp.setText("Kiki");
          //int adminRoleId = db.insertUserRoleAndroid(Roles.ADMIN.toString());

          //db.insertNewUserAndroid(adminId, adminRoleId);
          adminInserted = adminId != 0;

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

        if (adminInserted) {
          try {
            int adminRoleId = db.insertRoleAndroid(Roles.ADMIN.toString(), sdb);
            int userRole = db.insertUserRoleAndroid(adminId, adminRoleId, sdb);
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

   /* public void addData()
    {
        EditText name = (EditText) findViewById(R.id.newAdminName);
        EditText age = (EditText) findViewById(R.id.newAdminAge);
        EditText address = (EditText) findViewById(R.id.newAdminAddress);
        EditText password = (EditText) findViewById(R.id.newAdminPassword);

        //boolean inserted = false;
        // Attempt to create and add admin to database
        //while (!inserted) {
            try {
                //inserted = true;
                int adminId = db.insertNewUserAndroid(name.toString(), Integer.parseInt(age.toString()), address.toString(), password.toString());
                //int adminRoleId = db.insertUserRoleAndroid(Roles.ADMIN.toString());

                //db.insertNewUserAndroid(adminId, adminRoleId);

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
            } /*catch (InvalidRoleException e) {
                    System.out.println("Error adding admin role.");
                } catch (InvalidUserIdException e) {
                    System.out.println("Error creating user id.");
                } catch (InvalidRoleIdException e) {
                    System.out.println("Error creating admin id.");
                }
       // }
    }*/
}
