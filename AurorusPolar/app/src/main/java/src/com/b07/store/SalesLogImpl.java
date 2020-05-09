package src.com.b07.store;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import src.com.b07.database.helper.DatabaseSelectHelper;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.inventory.Item;


public class SalesLogImpl implements SalesLog, Serializable {
  private HashMap<Integer, SaleImpl> salesMap;
  private HashMap<Integer, ItemizedSaleImpl> itemizedSalesMap;

  public SalesLogImpl(HashMap<Integer, ItemizedSaleImpl> itemizedSalesMap,
      HashMap<Integer, SaleImpl> salesMap) {
    if (itemizedSalesMap == null) {
      this.itemizedSalesMap = new HashMap<Integer, ItemizedSaleImpl>();
    } else {
      this.itemizedSalesMap = itemizedSalesMap;
    }
    if (salesMap == null) {
      this.salesMap = new HashMap<Integer, SaleImpl>();
    } else {
      this.salesMap = salesMap;
    }
  }

  /***
   * Checks if the saleId exists in the itemizedSaleMap
   * 
   * @param saleId
   * @return true/false
   */
  private boolean containsSale(int saleId) {

    for (int i : itemizedSalesMap.keySet()) {
      if (i == saleId) {
        return true;
      }

    }
    return false;
  }

  @Override
  /**
   * This methods add an itemizedSale to the HashMap
   * 
   * @param itemId, saleId, quantity
   */
  public void addItemizedSale(int saleId, int itemId, int quantity) throws SQLException {
    if (containsSale(saleId)) {
      Item item = DatabaseSelectHelper.getItem(itemId);
      ItemizedSaleImpl x = itemizedSalesMap.get(saleId);
      if (x.getItemMap().get(item) != null) {
        quantity = x.getItemMap().get(item) + quantity;
        x.getItemMap().put(item, quantity);
        itemizedSalesMap.put(saleId, x);
      } else {
        x.getItemMap().put(item, quantity);
        itemizedSalesMap.put(saleId, x);
      }
    } else {
      HashMap<Item, Integer> x = new HashMap<Item, Integer>();
      Item item = DatabaseSelectHelper.getItem(itemId);
      x.put(item, quantity);
      ItemizedSaleImpl itemizedSale = new ItemizedSaleImpl(saleId, x);
      itemizedSalesMap.put(saleId, itemizedSale);
    }

  }

  /***
   * Gets the sales map
   * 
   * @param
   * @return salesMap
   */
  public HashMap<Integer, SaleImpl> getSalesMap() {
    return salesMap;
  }

  /**
   * Sets the SalesMap
   * 
   * @param salesMap
   *
   */
  public void setSalesMap(HashMap<Integer, SaleImpl> salesMap) {
    this.salesMap = salesMap;
  }

  /**
   * Gets the itemizedSalesMap
   * 
   * @return itemizedSAlesMap
   *
   */
  public HashMap<Integer, ItemizedSaleImpl> getItemizedSalesMap() {
    return itemizedSalesMap;
  }

  /**
   * Sets the itemeizedSalesMap
   * 
   * @param itemizedSalesMap
   *
   */
  public void setItemizedSalesMap(HashMap<Integer, ItemizedSaleImpl> itemizedSalesMap) {
    this.itemizedSalesMap = itemizedSalesMap;
  }

  @Override
  /**
   * This methods is a helper to find total price of items
   * 
   * @param itemId, saleId
   * @return totalPrice
   */
  public BigDecimal getTotalPrice(int salesId, int itemId) {
    BigDecimal totalPrice = new BigDecimal(0.00);
    for (Integer i : salesMap.keySet()) {
      totalPrice = salesMap.get(i).getTotalPrice().add(totalPrice);
    }
    return totalPrice;
  }

  @Override
  /**
   * This methods is a helper to find total numbers of items sold for specific items to help with
   * view books
   * 
   * @param itemId
   * @return result
   */
  public int totalNumberOfItemSold(int itemid) {
    int result = 0;
    for (Integer id : itemizedSalesMap.keySet()) {
      HashMap<Item, Integer> items = itemizedSalesMap.get(id).getItemMap();
      for (Item item : items.keySet()) {
        if (item.getId() == itemid) {
          result = result + items.get(item);
        }
      }
    }
    return result;
  }

//Android methods
  @Override
public void addItemizedSaleAndroid(int saleId, int itemId, int quantity, DatabaseSelectHelperAndroid sdb) throws SQLException {
  if (containsSale(saleId)) {
    Item item = sdb.getItemAndroid(itemId);
    ItemizedSaleImpl x = itemizedSalesMap.get(saleId);
    if (x.getItemMap().get(item) != null) {
      quantity = x.getItemMap().get(item) + quantity;
      x.getItemMap().put(item, quantity);
      itemizedSalesMap.put(saleId, x);
    } else {
      x.getItemMap().put(item, quantity);
      itemizedSalesMap.put(saleId, x);
    }
  } else {
    HashMap<Item, Integer> x = new HashMap<Item, Integer>();
    Item item = sdb.getItemAndroid(itemId);
    x.put(item, quantity);
    ItemizedSaleImpl itemizedSale = new ItemizedSaleImpl(saleId, x);
    itemizedSalesMap.put(saleId, itemizedSale);
  }

}
}
