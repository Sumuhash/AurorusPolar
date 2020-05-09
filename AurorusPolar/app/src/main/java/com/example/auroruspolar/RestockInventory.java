package com.example.auroruspolar;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.sql.SQLException;
import java.util.List;
import src.com.b07.database.DatabaseDriverAndroid;
import src.com.b07.database.helper.DatabaseSelectHelper;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.inventory.Inventory;
import src.com.b07.inventory.Item;
import src.com.b07.store.EmployeeInterface;
import src.com.b07.users.Employee;

public class RestockInventory extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restock_inventory);

    final DatabaseDriverAndroid db = new DatabaseDriverAndroid(this);
    db.getReadableDatabase();
    final DatabaseSelectHelperAndroid sdb = new DatabaseSelectHelperAndroid(this);
    Intent temp = getIntent();
    final int id = Integer.parseInt(temp.getStringExtra("id"));

    try {
      List<Item> items = sdb.getAllItemsAndroid();
      TextView inventory = (TextView) findViewById(R.id.inventory);
      for (Item iterator : items) {
        inventory.append(iterator.getId() + " - " + iterator.getName() + ": "
            + sdb.getInventoryQuantityAndroid(iterator.getId())
            + " units\n");

      }
    } catch (SQLException e) {

      e.printStackTrace();
    }

    /*Button restock = (Button) findViewById(R.id.authenticate);
    restock.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent transition = new Intent(getApplicationContext(), EmployeeScreen.class);

        EditText itemId = (EditText) findViewById(R.id.restockItemId);
        EditText quantity = (EditText) findViewById(R.id.restockQuantity);
        try {
          Item item = sdb.getItemAndroid(Integer.parseInt(itemId.getText().toString()));
          Inventory inventory = sdb.getInventoryAndroid();
          Employee employee = (Employee) sdb.getUserDetailsAndroid(id);
          EmployeeInterface employeeInterface =
              new EmployeeInterface(employee, inventory);

          if (item != null && Integer.parseInt(quantity.getText().toString()) > 0) {
            employeeInterface
                .restockInventory(item, Integer.parseInt(quantity.getText().toString()));
            System.out.println("Successfully restocked " + item.getName());
          } else {
            System.out.println("Invalid Item ID or quantity!");
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
        startActivity(transition);
        finish();
      }
    });*/
  }
}

