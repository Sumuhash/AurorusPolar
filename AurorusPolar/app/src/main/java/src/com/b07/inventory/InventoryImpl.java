package src.com.b07.inventory;

import java.util.HashMap;

public class InventoryImpl implements Inventory {

  private HashMap<Item, Integer> itemMap;
  private int totalItems;

  public InventoryImpl(HashMap<Item, Integer> itemMap, int totalItems) {
    this.itemMap = itemMap;
    this.totalItems = totalItems;
  }
  
  public InventoryImpl() {
    this.itemMap = new HashMap<Item, Integer>();
    this.totalItems = 0;
  }
  
  @Override
  public HashMap<Item, Integer> getItemMap() {
    return itemMap;
  }

  @Override
  public void setItemMap(HashMap<Item, Integer> itemMap) {
    this.itemMap = itemMap;
  }

  @Override
  public void updateMap(Item item, Integer value) {
    this.itemMap.put(item, value);
  }

  @Override
  public int getTotalItems() {
    return this.totalItems;
  }

  @Override
  public void setTotalItems(int total) {
    this.totalItems = total;
  }

}
