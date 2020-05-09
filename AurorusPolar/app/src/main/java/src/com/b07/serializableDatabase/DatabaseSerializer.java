package src.com.b07.serializableDatabase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import src.com.b07.database.helper.DatabaseSelectHelper;
import src.com.b07.inventory.Item;
import src.com.b07.store.ItemizedSaleImpl;
import src.com.b07.store.SaleImpl;
import src.com.b07.users.User;

public class DatabaseSerializer {

  public static void serialize(ArrayList<Object> database) {
    try {
      FileOutputStream fileOut = new FileOutputStream("database_copy.ser");
      ObjectOutputStream out = new ObjectOutputStream(fileOut);

      out.writeObject(database);
      out.close();
      fileOut.close();
      System.out.println("Current Database was successfully saved in database_copy.ser");
    } catch (IOException i) {
      i.printStackTrace();
    }

  }
  
  
  public static ArrayList<Object> storeDatabase() {
    ArrayList<Object> database = new ArrayList<Object>();

    try {
      // Store ROLES
      List<Integer> roleIds;
      roleIds = DatabaseSelectHelper.getRoleIds();

      ArrayList<String> rolesTable = new ArrayList<String>();
      for (int c : roleIds) {
        rolesTable.add(DatabaseSelectHelper.getRoleName(c));
      }
      database.add(rolesTable);

      // Store USERS
      List<User> usersTable = DatabaseSelectHelper.getUsersDetails();
      database.add(usersTable);

      // Store USERPW
      HashMap<Integer, String> userPassTable = new HashMap<Integer, String>();
      for (User user : usersTable) {
        userPassTable.put(user.getId(), DatabaseSelectHelper.getPassword(user.getId()));
      }
      database.add(userPassTable);

      // Store USERROLE
      HashMap<Integer, Integer> userRoleTable = new HashMap<Integer, Integer>();
      for (User user : usersTable) {
        userRoleTable.put(user.getId(), user.getRoleId());
      }
      database.add(userRoleTable);

      // Store ITEMS
      ArrayList<Item> itemsTable = (ArrayList<Item>) DatabaseSelectHelper.getAllItems();
      database.add(itemsTable);

      // Store SALES
      HashMap<Integer, SaleImpl> salesTable = DatabaseSelectHelper.getSales().getSalesMap();
      database.add(salesTable);

      // Store ITEMIZEDSALES
      HashMap<Integer, ItemizedSaleImpl> itemizedSalesTable =
          DatabaseSelectHelper.getItemizedSales().getItemizedSalesMap();
      database.add(itemizedSalesTable);

      // Store INVENTORY
      HashMap<Item, Integer> inventoryTable = DatabaseSelectHelper.getInventory().getItemMap();
      database.add(inventoryTable);

      // Store ACCOUNTS and lists of active/inactive accounts
      HashMap<Integer, Integer> accountsTable = new HashMap<Integer, Integer>();
      List<Integer> activeAccountIds = new ArrayList<Integer>();
      List<Integer> inactiveAccountIds = new ArrayList<Integer>();

      for (User user : usersTable) {
        for (int accountId : DatabaseSelectHelper.getUserAccounts(user.getId())) {
          accountsTable.put(accountId, user.getId());
        }

        for (int activeId : DatabaseSelectHelper.getUserActiveAccount(user.getId())) {
          activeAccountIds.add(activeId);
        }

        for (int inactiveId : DatabaseSelectHelper.getUserInactiveAccount(user.getId())) {
          inactiveAccountIds.add(inactiveId);
        }
      }
      database.add(accountsTable);
      database.add(activeAccountIds);
      database.add(inactiveAccountIds);

      // Store ACCOUNTSUMMARY
      HashMap<Integer, HashMap<Integer, Integer>> accountSummaryTable =
          new HashMap<Integer, HashMap<Integer, Integer>>();
      for (User user : usersTable) {
        for (int accountId : DatabaseSelectHelper.getUserAccounts(user.getId())) {
          accountSummaryTable.put(accountId, DatabaseSelectHelper.getAccountDetails(accountId));
        }
      }
      database.add(accountSummaryTable);

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return database;
  }

}
