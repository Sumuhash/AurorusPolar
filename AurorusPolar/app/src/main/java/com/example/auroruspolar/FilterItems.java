package com.example.auroruspolar;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class FilterItems extends AppCompatActivity {


    /*private ArrayList<CustomItems> saleItems = new ArrayList<>();

    private void addItems() {
        TextView tempPrice;
        TextView tempQuant;

        tempPrice = (TextView) findViewById(R.id.fishingRodPrice);
        tempQuant = (TextView) findViewById(R.id.fishingRodQuantity);
        CustomItems fishingRod = new CustomItems(R.drawable.fishing_rod, R.id.fishingRodAddToCart ,tempPrice.getText().toString(), tempQuant.getText().toString());
        saleItems.add(fishingRod);

        tempPrice = (TextView) findViewById(R.id.hockeyStickPrice);
        tempQuant = (TextView) findViewById(R.id.hockeyStickQuantity);
        CustomItems hockeyStick = new CustomItems(R.drawable.hockey_stick, R.id.hockeyStickAddToCart, tempPrice.getText().toString(), tempQuant.getText().toString());
        saleItems.add(hockeyStick);

        tempPrice = (TextView) findViewById(R.id.skatesPrice);
        tempQuant = (TextView) findViewById(R.id.skatesQuantity);
        CustomItems skates = new CustomItems(R.drawable.skates, R.id.skatesAddToCart, tempPrice.getText().toString(), tempQuant.getText().toString());
        saleItems.add(skates);

        tempPrice = (TextView) findViewById(R.id.runningShoesPrice);
        tempQuant = (TextView) findViewById(R.id.runningShoesQuantity);
        CustomItems runningShoes = new CustomItems(R.drawable.running_shoes, R.id.runningShoesToAddCart, tempPrice.getText().toString(), tempQuant.getText().toString());
        saleItems.add(runningShoes);

        tempPrice = (TextView) findViewById(R.id.proteinBarPrice);
        tempQuant = (TextView) findViewById(R.id.proteinBarQuantity);
        CustomItems proteinBar = new CustomItems(R.drawable.protein_bar, R.id.skatesAddToCart, tempPrice.getText().toString(), tempQuant.getText().toString());
        saleItems.add(proteinBar);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);
        LinearLayout items = (LinearLayout) findViewById(R.id.itemList);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        items.setLayoutParams(params);

        Button button = (Button) findViewById(R.id.userButton);
       // addItems();
       // Collections.sort(saleItems, new CustomComparatorPrice());




    }
    private class CustomComparatorPrice implements Comparator<CustomItems>
    {
        @Override
        public int compare(CustomItems o1, CustomItems o2) {
            return o1.getPrice().compareTo(o2.getPrice());
        }
    }*/
}
