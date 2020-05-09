package com.example.auroruspolar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FilterByPrice extends AppCompatActivity {

  private ArrayList<CustomItems> saleItems = new ArrayList<>();

  private void addItems() {
    TextView tempPrice;
    TextView tempQuant;
    Button tempButton;
    ImageView tempImage;

    tempPrice = (TextView) findViewById(R.id.fishingRodPrice);
    tempQuant = (TextView) findViewById(R.id.fishingRodQuantity);
    tempButton = (Button) findViewById(R.id.fishingRodAddToCart);
    tempImage = (ImageView) findViewById(R.id.fishingRod);

    CustomItems fishingRod = new CustomItems(tempImage, tempButton, tempPrice);
    saleItems.add(fishingRod);

    tempPrice = (TextView) findViewById(R.id.hockeyStickPrice);
    tempQuant = (TextView) findViewById(R.id.hockeyStickQuantity);
    tempButton = (Button) findViewById(R.id.hockeyStickAddToCart);
    tempImage = (ImageView) findViewById(R.id.hockeyStick);
    CustomItems hockeyStick = new CustomItems(tempImage, tempButton, tempPrice);
    saleItems.add(hockeyStick);

    tempPrice = (TextView) findViewById(R.id.skatesPrice);
    tempQuant = (TextView) findViewById(R.id.skatesQuantity);
    tempButton = (Button) findViewById(R.id.skatesAddToCart);
    tempImage = (ImageView) findViewById(R.id.skates);
    CustomItems skates = new CustomItems(tempImage, tempButton, tempPrice);
    saleItems.add(skates);

    tempPrice = (TextView) findViewById(R.id.proteinBarPrice);
    tempQuant = (TextView) findViewById(R.id.proteinBarQuantity);
    tempButton = (Button) findViewById(R.id.proteinBarAddToCart);
    tempImage = (ImageView) findViewById(R.id.proteinBar);

    CustomItems proteinBar = new CustomItems(tempImage, tempButton, tempPrice);
    saleItems.add(proteinBar);

    tempPrice = (TextView) findViewById(R.id.runningShoesPrice);
    tempQuant = (TextView) findViewById(R.id.runningShoesQuantity);
    tempButton = (Button) findViewById(R.id.runningShoesToAddCart);
    tempImage = (ImageView) findViewById(R.id.runningShoes);
    CustomItems runningShoes = new CustomItems(tempImage, tempButton, tempPrice);
    saleItems.add(runningShoes);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filter_by_price);

    final LinearLayout items = (LinearLayout) findViewById(R.id.priceFilter);

    Button button = (Button) findViewById(R.id.userButton);
    addItems();
    Collections.sort(saleItems, new CustomComparatorPrice());

    Button filterByPrice = (Button) findViewById(R.id.filterByPrice);

    filterByPrice.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        for (CustomItems i : saleItems) {
          items.addView(i.getImage());
          items.addView(i.getPriceText());
          items.addView(i.getButton());
        }
      }
    });

  }

  private class CustomComparatorPrice implements Comparator<CustomItems> {

    @Override
    public int compare(CustomItems o1, CustomItems o2) {
      return o1.getPrice().compareTo(o2.getPrice());
    }
  }
}
