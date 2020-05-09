package src.com.b07.users;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import src.com.b07.database.helper.DatabaseSelectHelper;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.database.helper.DatabaseUpdateHelper;
import src.com.b07.exceptions.ConnectionFailedException;
import src.com.b07.exceptions.DatabaseInsertException;
import src.com.b07.exceptions.InvalidRoleException;
import src.com.b07.exceptions.InvalidRoleIdException;
import src.com.b07.exceptions.InvalidUserIdException;
import src.com.b07.inventory.Item;
import src.com.b07.serializableDatabase.DatabaseDeserializer;
import src.com.b07.serializableDatabase.DatabaseSerializer;
import src.com.b07.store.DatabaseDriverExtender;
import src.com.b07.store.SalesLog;

// ROLES = 0
// USERS = 1
// USERPW = 2
// USERROLES = 3
// ITEMS = 4
// SALES = 5
// ITEMIZEDSALES = 6
// INVENTORY = 7
// ACCOUNTS = 8
// Active Accounts = 9
// Inactive Accounts = 10
// ACCOUNTSUMMARY = 11

public class Admin extends User {

  public Admin(int id, String name, int age, String address) {
    super(id, name, age, address);
  }

  public Admin(int id, String name, int age, String address, boolean authenticated) {
    super(id, name, age, address, authenticated);
  }

  /**
   * 
   * @param employee
   * @return boolean
   */
  public boolean promoteEmployee(Employee employee)
      throws InvalidRoleException, SQLException, InvalidRoleIdException, InvalidUserIdException {
    int employeeId = employee.getRoleId();
    return DatabaseUpdateHelper.updateUserRole(this.getRoleId(), employeeId);
  }

  // View Book Helpers
  public static int getItemQuantity(Item item, HashMap<Item, Integer> items) {
    if (containsItem(item, items)) {
      return items.get(getKey(item, items));
    }
    return 0;
  }

  public static boolean containsItem(Item item, HashMap<Item, Integer> items) {
    for (Item i : items.keySet()) {
      if (item.getId() == i.getId()) {
        return true;
      }
    }
    return false;
  }

  private static Item getKey(Item item, HashMap<Item, Integer> items) {
    for (Item i : items.keySet()) {
      if (i.getId() == item.getId()) {
        return i;
      }
    }
    return null;
  }

  public void viewBooks() throws SQLException {
    BigDecimal total = new BigDecimal("0.00");

    SalesLog sales = DatabaseSelectHelper.getSales();
    SalesLog itemized = DatabaseSelectHelper.getItemizedSales();


    for (int sale : sales.getSalesMap().keySet()) {
      boolean firstItem = true;
      System.out.println("Customer: " + sales.getSalesMap().get(sale).getUser().getName());
      System.out.println("Purchase Number: " + sales.getSalesMap().get(sale).getId());
      System.out.println("Total Purchase Price: " + sales.getSalesMap().get(sale).getTotalPrice());
      total = total.add(sales.getSalesMap().get(sale).getTotalPrice());

      HashMap<Item, Integer> x = itemized.getItemizedSalesMap().get(sale).getItemMap();
      for (Item item : DatabaseSelectHelper.getAllItems()) {
        if(firstItem){
          System.out
            .println("Itemized Breakdown: " + item.getName() + ":" + getItemQuantity(item, x));
          firstItem = false;
        } else { 
          System.out
          .println("                    " + item.getName() + ":" + getItemQuantity(item, x));
        }
        
      }
      System.out.println("-------------------------------------------------------------");
    }
    for (Item addedItem : DatabaseSelectHelper.getAllItems()) {
      System.out.println("Number " + addedItem.getName() + " Sold: "
          + itemized.totalNumberOfItemSold(addedItem.getId()));
    }
    System.out.println("TOTAL SALES: " + total);
  }

  // Phase 3
  public void listActiveUsers(int userId) throws SQLException {
    List<Integer> activeAccounts = new ArrayList<Integer>();

    activeAccounts = DatabaseSelectHelper.getUserActiveAccount(userId);

    for (Integer account : activeAccounts) {
      System.out.println("Account Id: " + account);
    }
  }

  public static  String listActiveUsersAndroid(int userId, DatabaseSelectHelperAndroid sdb) throws SQLException {
    List<Integer> activeAccounts = new ArrayList<Integer>();
    String temp;
    temp = "";
    activeAccounts = sdb.getUserActiveAccountAndroid(userId);

    for (Integer account : activeAccounts) {
      temp = "Account Id: " + account + "/n" + temp;

    }
    return temp;
  }


  public void listInactiveUsers(int userId) throws SQLException {
    List<Integer> inactiveAccounts = new ArrayList<Integer>();

    inactiveAccounts = DatabaseSelectHelper.getUserInactiveAccount(userId);

    for (Integer account : inactiveAccounts) {
      System.out.println("Account Id: " + account);
    }
  }

  public static String listInactiveUsersAndroid(int userId, DatabaseSelectHelperAndroid sdb) throws SQLException {
    List<Integer> inactiveAccounts = new ArrayList<Integer>();
    String temp;
    temp = "";
    inactiveAccounts = sdb.getUserInactiveAccountAndroid(userId);

    for (Integer account : inactiveAccounts) {
      temp = "Account Id: " + account + "/n" + temp;
    }
    return temp;
  }

  public void saveDatabase() {
    ArrayList<Object> database = DatabaseSerializer.storeDatabase();
    DatabaseSerializer.serialize(database);
  }

  public Connection loadDatabase() {
    try {
      //connection.close();
      Connection newConn = DatabaseDriverExtender.reinitialize();
      DatabaseDeserializer.deserialize();
      return newConn;
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ConnectionFailedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (DatabaseInsertException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  //Android Methods
  public static String viewBooksAndroid(DatabaseSelectHelperAndroid sdb) throws SQLException {
    BigDecimal total = new BigDecimal("0.00");

    SalesLog sales = sdb.getSalesAndroid();
    SalesLog itemized = sdb.getItemizedSalesAndroid();
    String result = "";


    for (int sale : sales.getSalesMap().keySet()) {
      boolean firstItem = true;
      result = result + "Customer: " + sales.getSalesMap().get(sale).getUser().getName() + "\nPurchase Number: " + sales.getSalesMap().get(sale).getId() +"\nTotal Purchase Price: $" + sales.getSalesMap().get(sale).getTotalPrice().toString();
      total = total.add(sales.getSalesMap().get(sale).getTotalPrice());

      HashMap<Item, Integer> x = itemized.getItemizedSalesMap().get(sale).getItemMap();
      for (Item item : sdb.getAllItemsAndroid()) {
        if(firstItem){
          result = result + "\nItemized Breakdown: " + item.getName() + ":" + getItemQuantity(item, x);
          firstItem = false;
        } else {
          result = result + "\n                                     " + item.getName() + ":" + getItemQuantity(item, x);
        }

      }
      result = result + "\n-------------------------------------------------------------";
    }
    for (Item addedItem : sdb.getAllItemsAndroid()) {
      result = result + "\nNumber " + addedItem.getName() + " Sold: "
              + itemized.totalNumberOfItemSold(addedItem.getId());
    }
    result = result + "\nTOTAL SALES: $" + total.toString();
    return result;
  }


}
