package src.com.b07.validate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import src.com.b07.database.helper.DatabaseSelectHelper;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.inventory.Item;
import src.com.b07.store.ItemizedSaleImpl;
import src.com.b07.users.User;

public class ValidateSales {

  public static boolean isValidUserId(int userId) throws SQLException {

    User temp = DatabaseSelectHelper.getUserDetails(userId);
    if (temp != null) {
      return true;
    }
    return false;
  }

  public static boolean isValidTotalPrice(BigDecimal price, int saleId) throws SQLException {
    HashMap<Item, Integer> items = new HashMap<Item, Integer>();
    ItemizedSaleImpl temp = (ItemizedSaleImpl) DatabaseSelectHelper.getItemizedSaleById(saleId);
    items = temp.getItemMap();
    BigDecimal count = new BigDecimal(0.00);
    for (Item i : items.keySet()) {
      BigDecimal x = new BigDecimal(items.get(i));
      count = count.add(i.getPrice().multiply(x));
    }
    if (count.equals(price)) {
      return true;
    }
    return false;
  }

  //Android Validators
  public static boolean isValidUserIdAndroid(int userId, DatabaseSelectHelperAndroid sdb) throws SQLException {

    User temp = sdb.getUserDetailsAndroid(userId);
    if (temp != null) {
      return true;
    }
    return false;
  }


}
