package com.example.auroruspolar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserScreen extends AppCompatActivity implements View.OnClickListener {

  static int fishingRodQuantity = 0;
  static int hockeyStickQuantity = 0;
  static int skatesQuantity = 0;
  static int runningShoesQuantity = 0;
  static int proteinBarQuantity = 0;
  static int totalQuantity = 0;
  TextView quantityDisplay = findViewById(R.id.totalItems);


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_screen);
    getItemQuantity();
  }

  private void getItemQuantity() {
    Button addFishingRod = findViewById(R.id.fishingRodAddToCart);
    Button addHockeyStick = findViewById(R.id.hockeyStickAddToCart);
    Button addSkates = findViewById(R.id.skatesAddToCart);
    Button addRunningShoes = findViewById(R.id.runningShoesToAddCart);
    Button addGranolaBar = findViewById(R.id.proteinBarAddToCart);
    Button proceedToCart = findViewById(R.id.proceedToCart);

    addFishingRod.setOnClickListener(this);
    addHockeyStick.setOnClickListener(this);
    addSkates.setOnClickListener(this);
    addRunningShoes.setOnClickListener(this);
    addGranolaBar.setOnClickListener(this);
    proceedToCart.setOnClickListener(this);
  }

  @Override
  public void onClick(View addToCart) {
    switch (addToCart.getId()) {
      case R.id.fishingRodAddToCart:
        fishingRodQuantity++;
        totalQuantity++;
        quantityDisplay.setText(totalQuantity);
        break;
      case R.id.hockeyStickAddToCart:
        hockeyStickQuantity++;
        totalQuantity++;
        quantityDisplay.setText(totalQuantity);
        break;
      case R.id.skatesAddToCart:
        skatesQuantity++;
        totalQuantity++;
        quantityDisplay.setText(totalQuantity);
        break;
      case R.id.runningShoesToAddCart:
        runningShoesQuantity++;
        totalQuantity++;
        quantityDisplay.setText(totalQuantity);
        break;
      case R.id.proteinBarAddToCart:
        proteinBarQuantity++;
        totalQuantity++;
        quantityDisplay.setText(totalQuantity);
        break;
      case R.id.proceedToCart:
        openShoppingCartActivity();
        break;

    }
  }

  private void openShoppingCartActivity() {
    Intent intent = new Intent(this, ShoppingCartActivity.class);
    startActivity(intent);
  }
}

