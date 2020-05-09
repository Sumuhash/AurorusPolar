package src.com.b07.store;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import src.com.b07.database.helper.DatabaseInsertHelper;
import src.com.b07.database.helper.DatabaseSelectHelper;
import src.com.b07.database.helper.DatabaseUpdateHelper;
import src.com.b07.exceptions.DatabaseInsertException;
import src.com.b07.exceptions.InvalidItemIdException;
import src.com.b07.exceptions.InvalidPriceException;
import src.com.b07.exceptions.InvalidQuantityException;
import src.com.b07.exceptions.InvalidSaleIdException;
import src.com.b07.exceptions.InvalidUserIdException;
import src.com.b07.inventory.Item;
import src.com.b07.users.Customer;

public class ShoppingCart {
  private HashMap<Item, Integer> items;
  private Customer customer;
  private BigDecimal total;
  private static final BigDecimal TAXRATE = new BigDecimal("1.13");

  public ShoppingCart(Customer customer) {
    this.customer = customer;
    this.items = new HashMap<Item, Integer>();
    this.total = new BigDecimal("0.00");

  }


  public int getItemQuantity(Item item) {
    if (containsItem(item)) {
      return this.items.get(getKey(item));
    }
    return 0;
  }

  public boolean containsItem(Item item) {
    List<Item> items = this.getItems();
    for (Item i : items) {
      if (item.getId() == i.getId()) {
        return true;
      }
    }
    return false;
  }

  private Item getKey(Item item) {
    for (Item i : this.getItems()) {
      if (i.getId() == item.getId()) {
        return i;
      }
    }
    return null;
  }

  public void addItem(Item item, int quantity) {
    if (this.containsItem(item)) {
      this.items.put(this.getKey(item), quantity + this.getItemQuantity(this.getKey(item)));
    } else {
      this.items.put(item, quantity);
    }

    BigDecimal quantityDecimal = new BigDecimal(quantity);
    this.total = this.total.add(item.getPrice().multiply(quantityDecimal));
  }

  public void removeItem(Item item, int quantity) {
    if (this.containsItem(item)) {
      this.items.put(this.getKey(item), this.getItemQuantity(this.getKey(item)) - quantity);
      if (this.getItemQuantity(this.getKey(item)) == 0) {
        this.items.remove(this.getKey(item));
      }
      BigDecimal quantityDecimal = new BigDecimal(quantity);
      this.total = this.total.subtract(item.getPrice().multiply(quantityDecimal));
    }
  }

  public void restoreCart(int accountId) throws SQLException {
    HashMap<Integer, Integer> accountItems = DatabaseSelectHelper.getAccountDetails(accountId);
    // HashMap<Item, Integer> newItems = new HashMap<Item, Integer>();

    for (Integer itemId : accountItems.keySet()) {
      Item item = DatabaseSelectHelper.getItem(itemId);
      // newItems.put(item, accountItems.get(itemId));

      this.addItem(item, accountItems.get(itemId));
    }
  }

  public static boolean hasEmptyCarts(int userId) throws SQLException {
    List<Integer> customerAccounts = DatabaseSelectHelper.getUserAccounts(userId);
    if (!customerAccounts.isEmpty()) {
      for (Integer accountId : customerAccounts) {
        if (DatabaseSelectHelper.getAccountDetails(accountId).isEmpty()) {
          return true;
        }
      }
    }
    return false;
  }

  public static List<Integer> getEmptyAccounts(int userId) throws SQLException {
    List<Integer> savedCarts = new ArrayList<Integer>();
    List<Integer> customerAccounts = DatabaseSelectHelper.getUserAccounts(userId);
    if (!customerAccounts.isEmpty()) {
      for (Integer accountId : customerAccounts) {
        if (DatabaseSelectHelper.getAccountDetails(accountId).isEmpty()) {
          savedCarts.add(accountId);
        }
      }
    }
    return savedCarts;
  }

  public void saveCart(int accountId) throws SQLException, InvalidItemIdException,
      DatabaseInsertException, InvalidQuantityException {
    List<Item> items = getItems();
    for (Item item : items) {
      DatabaseInsertHelper.insertAccountLine(accountId, item.getId(), this.getItemQuantity(item));
    }
  }

  public List<Item> getItems() {
    List<Item> itemList = new ArrayList<Item>();
    for (Item item : this.items.keySet()) {
      itemList.add(item);
    }
    return itemList;
  }

  public Customer getCustomer() {
    return customer;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public BigDecimal getTaxRate() {
    return TAXRATE;
  }

  public void clearCart() {
    this.items.clear();
    this.total = BigDecimal.ZERO;
  }

  private boolean hasSufficientInventory() throws SQLException {
    for (Item item : this.getItems()) {
      if (this.getItemQuantity(item) > DatabaseSelectHelper.getInventoryQuantity(item.getId())) {
        return false;
      }
    }
    return true;
  }

  private boolean isValidTotalPrice(BigDecimal price) throws SQLException {
    List<Item> items = this.getItems();
    BigDecimal count = new BigDecimal("0.00").setScale(2);

    for (Item i : items) {
      BigDecimal x = new BigDecimal(this.getItemQuantity(i));
      count = count.add(i.getPrice().multiply(x).setScale(2));
    }
    if (count.setScale(2).equals(price)) {
      return true;
    }
    return false;
  }

  public boolean checkOut() {
    if (this.customer != null) {
      int customerId = this.customer.getId();

      try {
        if (hasSufficientInventory()) {
          try {
            if (isValidTotalPrice(this.total)) {
              int saleId = DatabaseInsertHelper.insertSale(customerId, this.total);
              // Inventory databaseInventory = DatabaseSelectHelper.getInventory();

              for (Item item : this.getItems()) {
                DatabaseInsertHelper.insertItemizedSale(saleId, item.getId(),
                    this.getItemQuantity(item));
                DatabaseUpdateHelper
                    .updateInventoryQuantity(DatabaseSelectHelper.getInventoryQuantity(item.getId())
                        - this.getItemQuantity(item), item.getId());
              }
            }
          } catch (InvalidUserIdException e1) {
            // TODO Auto-generated catch block
            System.out.println("Invalid User Id");
            return false;
          } catch (DatabaseInsertException e1) {
            // TODO Auto-generated catch block
            System.out.println("Database error");
            return false;
          } catch (SQLException e1) {
            // TODO Auto-generated catch block
            System.out.println("SQL error");
            return false;
          } catch (InvalidItemIdException e) {
            // TODO Auto-generated catch block
            System.out.println("Invalid item id");
            return false;
          } catch (InvalidQuantityException e) {
            // TODO Auto-generated catch block
            System.out.println("Invalid quantity");
            return false;
          } catch (InvalidSaleIdException e) {
            // TODO Auto-generated catch block
            System.out.println("Invalid sale Id");
            return false;
          } catch (InvalidPriceException e) {
            // TODO Auto-generated catch block
            System.out.println("Invalid Price! Cart might be empty!");
            return false;
          }
          clearCart();
          return true;
        } else {
          System.out.println("Checkout aborted. Insufficient Inventory");
          return false;
        }

      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return false;
  }
}
