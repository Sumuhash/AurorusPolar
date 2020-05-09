package com.example.auroruspolar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserMenu extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_customer_screen);

    Button buttonAddQuantity = (Button) findViewById(R.id.addQuantity);
    Button buttonListItems = (Button) findViewById((R.id.listCurrItems));
    Button buttonRemoveQuantity = (Button) findViewById((R.id.removeQuantity));
    Button buttonCheckOut = (Button) findViewById((R.id.checkOut));
    Button buttonLogOff = (Button) findViewById((R.id.logOff));
    Button buttonCheckPrice = (Button) findViewById(R.id.cartPrice);

    Intent temp = getIntent();
    String name = temp.getStringExtra("id");

    buttonListItems.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openFile(0);
      }
    });

    buttonAddQuantity.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openFile(1);
      }
    });

    buttonCheckPrice.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openFile(2);
      }
    });

    buttonRemoveQuantity.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openFile(3);
      }
    });

    buttonCheckOut.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openFile(4);
      }
    });

    buttonLogOff.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openFile(5);
      }
    });
  }

  public void openFile(int value) {
    Intent intent;
    if (value == 0) {
      intent = new Intent(this, CustomerListCart.class);
      Intent temp = getIntent();
      intent.putExtra("id", temp.getStringExtra("id"));
    } else if (value == 1) {
      intent = new Intent(this, UserScreen.class); //change activity name Add quantity
    } else if (value == 2) {
      intent = new Intent(this, CustomerCheckPriceofCart.class); //change activity name CheckPrice
    } else if (value == 3) {
      intent = new Intent(this, UserMenu.class); //change activity name Remove Quantity
    } else if (value == 4) {
      intent = new Intent(this, ShoppingCartActivity.class); //change activity name Checkout
    } else if (value == 5) {
      intent = new Intent(this, MainActivity.class); //change activity name Logoff
    } else {
      intent = new Intent(this, AdminScreen.class);
    }
    startActivity(intent);
  }
}

