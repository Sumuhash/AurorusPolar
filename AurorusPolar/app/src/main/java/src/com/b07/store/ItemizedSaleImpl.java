package src.com.b07.store;

import java.io.Serializable;
import java.util.HashMap;
import src.com.b07.inventory.Item;

public class ItemizedSaleImpl implements Sale, Serializable {

  private int id;
  private HashMap<Item, Integer> itemMap;

  public ItemizedSaleImpl(int id, HashMap<Item, Integer> itemMap) {
    this.id = id;
    this.itemMap = itemMap;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  public HashMap<Item, Integer> getItemMap() {
    return itemMap;
  }

  public void setItemMap(HashMap<Item, Integer> itemMap) {
    this.itemMap = itemMap;
  }



}
