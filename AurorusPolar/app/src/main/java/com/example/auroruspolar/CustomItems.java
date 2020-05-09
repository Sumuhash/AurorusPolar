package com.example.auroruspolar;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomItems {

  private ImageView imageResource;
  private Button buttonResource;
  private TextView price;
  private String quantity;

  /*public CustomItems(int imageResource, int buttonResource, String price, String quantity)
  {
      this.imageResource = imageResource;
      this.buttonResource = buttonResource;
      this.price = price;
      this.quantity = quantity;
  }*/
  public CustomItems(ImageView imageResource, Button buttonResource, TextView price) {
    this.imageResource = imageResource;
    this.buttonResource = buttonResource;
    this.price = price;
  }

  public String getPrice() {
    return this.price.getText().toString();
  }

  public String getQuantity() {
    return this.quantity;
  }

  public ImageView getImage() {
    return this.imageResource;
  }

  public Button getButton() {
    return this.buttonResource;
  }

  public TextView getPriceText() {
    return this.price;
  }

  public int getImageResource() {
    return this.imageResource.getId();
  }

  public int getButtonResource() {
    return this.buttonResource.getId();
  }

}
