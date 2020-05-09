package src.com.b07.validate;

import java.sql.SQLException;
import src.com.b07.database.helper.DatabaseSelectHelper;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;

public class ValidateItemizedsales {

  public static boolean isValidQuantity(int quantity) {

    if (quantity > 0) {
      return true;
    }
    return false;
  }

  public static boolean isValidSaleId(int saleId) throws SQLException {
    if (DatabaseSelectHelper.getSaleById(saleId) != null) {
      return true;
    }
    return false;
  }

  //Android Validators
  public static boolean isValidItemId(int itemId) throws SQLException {

    if (DatabaseSelectHelper.getItem(itemId) != null) {
      return true;
    }
    return false;
  }

  public static boolean isValidSaleIdAndroid(int saleId, DatabaseSelectHelperAndroid sdb) throws SQLException {
    if (sdb.getSaleByIdAndroid(saleId) != null) {
      return true;
    }
    return false;
  }


  public static boolean isValidItemIdAndroid(int itemId, DatabaseSelectHelperAndroid sdb) throws SQLException {

    if (sdb.getItemAndroid(itemId) != null) {
      return true;
    }
    return false;
  }
}
