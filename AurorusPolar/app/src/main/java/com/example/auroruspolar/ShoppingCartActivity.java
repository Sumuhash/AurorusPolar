package com.example.auroruspolar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.sql.SQLDataException;
import java.sql.SQLException;

import src.com.b07.database.DatabaseDriverAndroid;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.enums.Roles;


public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener {


  public void displayPrice() {
    TextView cartPrice = findViewById(R.id.cartTotalPrice);
    cartPrice.setText(calculateTotalPrice());
  }

  public void displayQuantity() {
    TextView fishingRodQuantityDisplay = (TextView) findViewById(R.id.fishingRodQuantity);
    TextView hockeyStickQuantityDisplay = (TextView) findViewById(R.id.fishingRodQuantity);
    TextView skatesQuantityDisplay = (TextView) findViewById(R.id.skatesQuantity);
    TextView proteinBarQuantityDisplay = (TextView) findViewById(R.id.proteinBarQuantity);
    TextView shoesQuantityDisplay = (TextView) findViewById(R.id.runningShoesQuantity);

    fishingRodQuantityDisplay.setText(UserScreen.fishingRodQuantity);
    hockeyStickQuantityDisplay.setText(UserScreen.hockeyStickQuantity);
    skatesQuantityDisplay.setText(UserScreen.skatesQuantity);
    proteinBarQuantityDisplay.setText(UserScreen.proteinBarQuantity);
    shoesQuantityDisplay.setText(UserScreen.runningShoesQuantity);
  }

  private CharSequence calculateTotalPrice() {
    // Need to update so that it gets item prices from the db.
    // Need to make this follow way better design choice..
    // hashmap of quantities as keys and price as values
    float totalPrice = 0.0f;
    totalPrice += UserScreen.fishingRodQuantity * 25.50;
    totalPrice += UserScreen.hockeyStickQuantity * 99.99;
    totalPrice += UserScreen.runningShoesQuantity * 85.99;
    totalPrice += UserScreen.proteinBarQuantity * 5.25;
    totalPrice += UserScreen.skatesQuantity * 50.79;
    return Float.toString(totalPrice);
  }

  private void updateDatabase() {
    final DatabaseSelectHelperAndroid sdb = new DatabaseSelectHelperAndroid(this);

    //take to a confirmed page or smtg in the end.
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    displayQuantity();
    displayPrice();
    final Button discountCode = (Button) findViewById(R.id.discountButton);
    DatabaseDriverAndroid db = new DatabaseDriverAndroid(this);
    db.getReadableDatabase();
    final DatabaseSelectHelperAndroid mydb = new DatabaseSelectHelperAndroid(this);
    discountCode.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String code = discountCode.getText().toString();
        TextView price = (TextView) findViewById(R.id.total);

        try {
          String roleName = mydb.getRoleNameAndroid(Integer.parseInt(code));
          if (roleName.equals(Roles.EMPLOYEE.name()) || roleName.equals(Roles.ADMIN.name())) {
            float discountedPrice = (float) (Float.parseFloat(calculateTotalPrice().toString())
                * 0.7);
            TextView displayDiscountedPrice = findViewById(R.id.cartTotalPrice);
            displayDiscountedPrice.setText(Float.toString(discountedPrice));
            //newPrice = newP rice*0.7;
          } else {
            discountCode.setError("Not a valid code :(");
          }
        } catch (SQLException e) {
          discountCode.setError("Error accessing database");
        }
      }
    });
  }

  @Override
  public void onClick(View v) {
    // when user clicks checkout, needs to update the database.
    updateDatabase();
  }
}
