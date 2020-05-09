package src.com.b07.serializableDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import src.com.b07.database.DatabaseDriver;
import src.com.b07.database.helper.DatabaseInsertHelper;
import src.com.b07.enums.Roles;
import src.com.b07.exceptions.ConnectionFailedException;
import src.com.b07.exceptions.DatabaseInsertException;
import src.com.b07.exceptions.InvalidAddressException;
import src.com.b07.exceptions.InvalidAgeException;
import src.com.b07.exceptions.InvalidItemIdException;
import src.com.b07.exceptions.InvalidItemNameException;
import src.com.b07.exceptions.InvalidNameException;
import src.com.b07.exceptions.InvalidPriceException;
import src.com.b07.exceptions.InvalidQuantityException;
import src.com.b07.exceptions.InvalidRoleException;
import src.com.b07.exceptions.InvalidRoleIdException;
import src.com.b07.exceptions.InvalidSaleIdException;
import src.com.b07.exceptions.InvalidUserIdException;
import src.com.b07.inventory.Item;
import src.com.b07.store.ItemizedSaleImpl;
import src.com.b07.store.SaleImpl;
import src.com.b07.users.User;

public class DatabaseDeserializer {

  public static ArrayList<Object> deserialize()
      throws ConnectionFailedException, DatabaseInsertException, SQLException {
    try {
      FileInputStream fileIn = new FileInputStream("database_copy.ser");
      ObjectInputStream in = new ObjectInputStream(fileIn);
      Object z = in.readObject();

      ArrayList<Object> db = (ArrayList<Object>) z;

      // Insert the ROLES table
      ArrayList<String> roles = (ArrayList<String>) db.get(0);
      for (String roleName : roles) {
        try {
          DatabaseInsertHelper.insertRole(roleName);
        } catch (InvalidRoleException e) {
        }
      }
      // Insert the USERS table
      ArrayList<User> users = (ArrayList<User>) db.get(1);
      for (User x : users) {
        try {
          DatabaseInsertHelper.insertUser(x.getName(), x.getAge(), x.getAddress());
        } catch (InvalidNameException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (InvalidAgeException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (InvalidAddressException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

      // Insert USERSPW Table
      HashMap<Integer, String> passwords = (HashMap<Integer, String>) db.get(2);
      for (int id : passwords.keySet()) {
        DatabaseInsertHelper.insertUnhashedPassword(id, passwords.get(id));
      }

      // Insert USERROLE
      HashMap<Integer, Integer> userRoles = (HashMap<Integer, Integer>) db.get(3);
      for (Integer userId : userRoles.keySet()) {
        try {
          DatabaseInsertHelper.insertUserRole(userId, userRoles.get(userId));
        } catch (InvalidUserIdException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (InvalidRoleIdException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

      // Insert ITEMS table
      ArrayList<Item> items = (ArrayList<Item>) db.get(4);
      for (Item item : items) {
        try {
          DatabaseInsertHelper.insertItem(item.getName(), item.getPrice());
        } catch (InvalidItemNameException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (InvalidPriceException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

      // Insert SALES table
      HashMap<Integer, SaleImpl> sales = (HashMap<Integer, SaleImpl>) db.get(5);
      for (Integer saleId : sales.keySet()) {
        try {
          DatabaseInsertHelper.insertSale(sales.get(saleId).getUser().getId(),
              sales.get(saleId).getTotalPrice());
        } catch (InvalidUserIdException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (InvalidPriceException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

      // Insert ITEMIZEDSALES table
      HashMap<Integer, ItemizedSaleImpl> itemizedSales =
          (HashMap<Integer, ItemizedSaleImpl>) db.get(6);
      for (Integer saleId : itemizedSales.keySet()) {
        for (Item item : itemizedSales.get(saleId).getItemMap().keySet()) {
          try {
            DatabaseInsertHelper.insertItemizedSale(saleId, item.getId(),
                itemizedSales.get(saleId).getItemMap().get(item));
          } catch (InvalidItemIdException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (InvalidQuantityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (InvalidSaleIdException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (InvalidUserIdException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }

      // Insert INVENTORY Table
      HashMap<Item, Integer> inventory = (HashMap<Item, Integer>) db.get(7);
      for (Item item : inventory.keySet()) {
        try {
          DatabaseInsertHelper.insertInventory(item.getId(), inventory.get(item));
        } catch (InvalidItemIdException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (InvalidQuantityException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

      // Insert ACCOUNT Table
      HashMap<Integer, Integer> accounts = (HashMap<Integer, Integer>) db.get(8);
      
      List<Integer> activeIds = (ArrayList<Integer>) db.get(9);
      List<Integer> inactiveIds = (ArrayList<Integer>) db.get(10);

      //Object [] keys = accounts.keySet().toArray();
      //Arrays.sort(keys);
      
      List<Integer> keys = new ArrayList<Integer>(accounts.keySet());
      Collections.sort(keys);
      for (int accountId : keys) {
        boolean active = false;
        if (activeIds.contains(accountId)) {
          active = true;
        } else if (inactiveIds.contains(accountId)) {
          active = false;
        }
        try {
          System.out.println(accountId);
          DatabaseInsertHelper.insertAccount(accounts.get(accountId), active);
        } catch (InvalidUserIdException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

      // Insert ACCOUNTSUMMARY table
      HashMap<Integer, HashMap<Integer, Integer>> accountSummary =
          (HashMap<Integer, HashMap<Integer, Integer>>) db.get(11);

      for (int accountId : accountSummary.keySet()) {
        for (int accountItemId : accountSummary.get(accountId).keySet()) {
          try {
            DatabaseInsertHelper.insertAccountLine(accountId, accountItemId,
                accountSummary.get(accountId).get(accountItemId));
          } catch (InvalidItemIdException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (InvalidQuantityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }

      in.close();
      fileIn.close();
      return (ArrayList<Object>) z;
    } catch (IOException i) {
      System.out.println("BOI class not found");
      i.printStackTrace();
      return null;
    } catch (ClassNotFoundException c) {
      System.out.println("Cat class not found");
      c.printStackTrace();
      return null;
    }

    // ArrayList<Object> deserializedList = null;
    // return deserializedList;
  }

}