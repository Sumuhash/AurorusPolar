package src.com.b07.database.helper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import src.com.b07.database.DatabaseSelector;
import src.com.b07.enums.Roles;
import src.com.b07.inventory.Inventory;
import src.com.b07.inventory.InventoryImpl;
import src.com.b07.inventory.Item;
import src.com.b07.inventory.ItemImpl;
import src.com.b07.store.ItemizedSaleImpl;
import src.com.b07.store.Sale;
import src.com.b07.store.SaleImpl;
import src.com.b07.store.SalesLog;
import src.com.b07.store.SalesLogImpl;
import src.com.b07.users.Admin;
import src.com.b07.users.Customer;
import src.com.b07.users.Employee;
import src.com.b07.users.User;

/*
 * TODO: Complete the below methods to be able to get information out of the database. TODO: The
 * given code is there to aide you in building your methods. You don't have TODO: to keep the exact
 * code that is given (for example, DELETE the System.out.println()) TODO: and decide how to handle
 * the possible exceptions
 */

public class DatabaseSelectHelper extends DatabaseSelector {

  /*
   * Begin Phase 2 upgrade DatabaseInsertSelecter functions
   */
  /**
   * Returns a list of the account IDs associated with userId
   * 
   * @param userId
   * @return a list of account IDs
   * @throws SQLException
   */
  public static List<Integer> getUserAccounts(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserAccounts(userId, connection);

    List<Integer> userAccountIds = new ArrayList<Integer>();
    while (results.next()) {
      userAccountIds.add(results.getInt("ID"));
    }
    connection.close();
    return userAccountIds;
  }

  /**
   * Returns a HashMap of the account details of accountId
   * 
   * @param accountId
   * @return a HashMap of account details
   * @throws SQLException
   */
  public static HashMap<Integer, Integer> getAccountDetails(int accountId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getAccountDetails(accountId, connection);

    HashMap<Integer, Integer> accountSummary = new HashMap<Integer, Integer>();
    while (results.next()) {
      accountSummary.put(results.getInt("ITEMID"), results.getInt("QUANTITY"));
    }
    connection.close();
    return accountSummary;
  }

  /*
   * End of Phase 2 upgrade DatabaseInsertSelecter functions
   */

  public static List<Integer> getRoleIds() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getRoles(connection);
    List<Integer> ids = new ArrayList<>();
    while (results.next()) {
      ids.add(results.getInt("ID"));
    }
    results.close();
    connection.close();
    return ids;
  }

  public static String getRoleName(int roleId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    String role = DatabaseSelector.getRole(roleId, connection);
    connection.close();
    return role;
  }

  public static int getUserRoleId(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int roleId = DatabaseSelector.getUserRole(userId, connection);
    connection.close();
    return roleId;
  }

  public static List<Integer> getUsersByRole(int roleId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUsersByRole(roleId, connection);
    List<Integer> userIds = new ArrayList<>();
    while (results.next()) {
      userIds.add(results.getInt("USERID"));
    }
    results.close();
    connection.close();
    return userIds;

  }

  public static List<User> getUsersDetails() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUsersDetails(connection);
    List<User> users = new ArrayList<>();
    while (results.next()) {
      int userId = results.getInt("ID");
      int roleId = getUserRoleId(userId);
      String roleName = getRoleName(roleId);

      if (roleName.equals(Roles.ADMIN.toString())) {
        // Make new admin user
        User user = new Admin(userId, results.getString("NAME"), results.getInt("AGE"),
            results.getString("ADDRESS"));
        user.setRoleId(roleId);
        users.add(user);

      } else if (roleName.equals(Roles.EMPLOYEE.toString())) {
        // Make new employee user
        Employee user = new Employee(userId, results.getString("NAME"), results.getInt("AGE"),
            results.getString("ADDRESS"));
        user.setRoleId(roleId);
        users.add(user);

      } else if (roleName.equals(Roles.CUSTOMER.toString())) {
        // Make new customer user
        Customer user = new Customer(userId, results.getString("NAME"), results.getInt("AGE"),
            results.getString("ADDRESS"));
        user.setRoleId(roleId);
        users.add(user);
      }
    }
    results.close();
    connection.close();
    return users;
  }

  public static User getUserDetails(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserDetails(userId, connection);
    User user = null;
    while (results.next()) {
      int roleId = getUserRoleId(results.getInt("ID"));
      String roleName = getRoleName(roleId);

      if (roleName.equals(Roles.ADMIN.toString())) {
        // Make new admin user
        user = new Admin(userId, results.getString("NAME"), results.getInt("AGE"),
            results.getString("ADDRESS"));
        user.setRoleId(roleId);

      } else if (roleName.equals(Roles.EMPLOYEE.toString())) {
        // Make new employee user
        user = new Employee(userId, results.getString("NAME"), results.getInt("AGE"),
            results.getString("ADDRESS"));
        user.setRoleId(roleId);

      } else if (roleName.equals(Roles.CUSTOMER.toString())) {
        // Make new customer user
        user = new Customer(userId, results.getString("NAME"), results.getInt("AGE"),
            results.getString("ADDRESS"));
        user.setRoleId(roleId);
      }
    }
    results.close();
    connection.close();
    return user;
  }

  public static String getPassword(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    String password = DatabaseSelector.getPassword(userId, connection);
    connection.close();
    return password;
  }

  public static List<Item> getAllItems() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getAllItems(connection);
    List<Item> items = new ArrayList<>();
    while (results.next()) {
      BigDecimal price = new BigDecimal(results.getString("PRICE"));

      // Make new Item and add to list
      Item item = new ItemImpl(results.getInt("ID"), results.getString("NAME"), price);
      items.add(item);

    }
    results.close();
    connection.close();
    return items;
  }

  public static Item getItem(int itemId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getItem(itemId, connection);
    Item item = null;
    while (results.next()) {
      BigDecimal price = new BigDecimal(results.getString("PRICE"));

      // Make a new item with the data extracted
      item = new ItemImpl(itemId, results.getString("NAME"), price);
    }
    results.close();
    connection.close();
    return item;
  }

  public static Inventory getInventory() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getInventory(connection);

    // Make new Inventory,
    Inventory inventory = new InventoryImpl();
    int total = 0;
    while (results.next()) {
      int quantity = results.getInt("QUANTITY");

      // Add the item and quantity to inventory
      inventory.updateMap(getItem(results.getInt("ITEMID")), quantity);
      total = total + quantity;
    }
    inventory.setTotalItems(total);
    results.close();
    connection.close();

    return inventory;
  }

  public static int getInventoryQuantity(int itemId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int quantity = DatabaseSelector.getInventoryQuantity(itemId, connection);
    connection.close();
    return quantity;
  }

  public static SalesLog getSales() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getSales(connection);
    HashMap<Integer, SaleImpl> salesMap = new HashMap<Integer, SaleImpl>();

    while (results.next()) {
      int saleId = results.getInt("ID");
      BigDecimal totalPrice = new BigDecimal(results.getString("TOTALPRICE"));
      SaleImpl tempSaleImpl =
          new SaleImpl(saleId, getUserDetails(results.getInt("USERID")), totalPrice);
      salesMap.put(saleId, tempSaleImpl);
    }
    SalesLog newSalesLog = new SalesLogImpl(null, salesMap);
    results.close();
    connection.close();
    return newSalesLog;

  }

  public static Sale getSaleById(int saleId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getSaleById(saleId, connection);
    Sale sale = null;
    while (results.next()) {
      BigDecimal totalPrice = new BigDecimal(results.getString("TOTALPRICE"));
      // Make new sale with extracted info
      sale =
          new SaleImpl(results.getInt("ID"), getUserDetails(results.getInt("USERID")), totalPrice);
    }
    results.close();
    connection.close();
    return sale; // return sale
  }

  public static List<Sale> getSalesToUser(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelectHelper.getSalesToUser(userId, connection);
    List<Sale> sales = new ArrayList<>();
    while (results.next()) {
      BigDecimal totalPrice = new BigDecimal(results.getString("TOTALPRICE"));
      // make new sale, add to list
      Sale sale =
          new SaleImpl(results.getInt("ID"), getUserDetails(results.getInt("USERID")), totalPrice);
      sales.add(sale);
    }
    results.close();
    connection.close();
    return sales;
  }

  public static Sale getItemizedSaleById(int saleId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getItemizedSaleById(saleId, connection);
    HashMap<Item, Integer> itemMap = new HashMap<Item, Integer>();
    Sale itemizedSale = null;
    while (results.next()) {
      itemMap.put(getItem(results.getInt("ITEMID")), results.getInt("QUANTITY"));
      itemizedSale = new ItemizedSaleImpl(results.getInt("SALEID"), itemMap);
    }

    results.close();
    connection.close();
    return itemizedSale;
  }

  public static SalesLog getItemizedSales() throws SQLException {

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getItemizedSales(connection);

    // Initialize empty salesLog
    SalesLog log = new SalesLogImpl(null, null);

    while (results.next()) {
      int id = results.getInt("SALEID");
      // Add the itemized sale to the salesLog
      log.addItemizedSale(id, results.getInt("ITEMID"), results.getInt("QUANTITY"));
    }

    results.close();
    connection.close();
    return log;
  }

  public static List<Integer> getUserActiveAccount(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserActiveAccounts(userId, connection);

    List<Integer> userActiveAccountIds = new ArrayList<Integer>();
    while (results.next()) {
      userActiveAccountIds.add(results.getInt("ID"));
    }
    results.close();
    connection.close();
    return userActiveAccountIds;

  }

  public static List<Integer> getUserInactiveAccount(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserInactiveAccounts(userId, connection);

    List<Integer> userInactiveAccountIds = new ArrayList<Integer>();
    while (results.next()) {
      userInactiveAccountIds.add(results.getInt("ID"));
    }
    results.close();
    connection.close();
    return userInactiveAccountIds;

  }

}
