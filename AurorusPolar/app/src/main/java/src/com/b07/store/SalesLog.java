package src.com.b07.store;

import java.math.BigDecimal;
// import com.b07.users.User;
import java.sql.SQLException;
import java.util.HashMap;

import src.com.b07.database.helper.DatabaseSelectHelperAndroid;

public interface SalesLog {
  public void addItemizedSale(int saleId, int itemId, int quantity) throws SQLException;

  public BigDecimal getTotalPrice(int salesId, int itemId);

  public int totalNumberOfItemSold(int itemid);

  public HashMap<Integer, SaleImpl> getSalesMap();

  public void setSalesMap(HashMap<Integer, SaleImpl> salesMap);

  public HashMap<Integer, ItemizedSaleImpl> getItemizedSalesMap();

  public void setItemizedSalesMap(HashMap<Integer, ItemizedSaleImpl> itemizedSalesMap);

  public void addItemizedSaleAndroid(int saleId, int itemId, int quantity, DatabaseSelectHelperAndroid sdb) throws SQLException;
}
