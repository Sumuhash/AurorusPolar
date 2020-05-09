package com.example.auroruspolar;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;


public class CustomerListCart extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.customer_list_cart);
    // ShoppingCartActivity shoppingCart = new ShoppingCartActivity();
    // shoppingCart.displayPrice();
    //shoppingCart.displayQuantity();

    //Button goBack = findViewById(R.id.goBack);
    //goBack.setOnClickListener(this);

  }

  public void onClick(View v) {
    Intent intent = new Intent(this, UserScreen.class);
    startActivity(intent);
  }
}
