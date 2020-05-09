package src.com.b07.database.helper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import src.com.b07.database.DatabaseInserter;
import src.com.b07.exceptions.DatabaseInsertException;
import src.com.b07.exceptions.InvalidAddressException;
import src.com.b07.exceptions.InvalidAgeException;
import src.com.b07.exceptions.InvalidItemIdException;
import src.com.b07.exceptions.InvalidItemNameException;
import src.com.b07.exceptions.InvalidNameException;
import src.com.b07.exceptions.InvalidPasswordException;
import src.com.b07.exceptions.InvalidPriceException;
import src.com.b07.exceptions.InvalidQuantityException;
import src.com.b07.exceptions.InvalidRoleException;
import src.com.b07.exceptions.InvalidRoleIdException;
import src.com.b07.exceptions.InvalidSaleIdException;
import src.com.b07.exceptions.InvalidUserIdException;
import src.com.b07.validate.ValidateInventory;
import src.com.b07.validate.ValidateItemizedsales;
import src.com.b07.validate.ValidateItems;
import src.com.b07.validate.ValidateRoles;
import src.com.b07.validate.ValidateSales;
import src.com.b07.validate.ValidateUserRole;
import src.com.b07.validate.ValidateUsers;


public class DatabaseInsertHelper extends DatabaseInserter {

  // Helper
  /**
   * This methods is a helper to find the RoleId given a roleName
   * 
   * @param roleName
   * @return id
   */
  public static int getRoleId(String roleName) throws SQLException {

    List<Integer> roleIds = DatabaseSelectHelper.getRoleIds();
    for (Integer id : roleIds) {
      if (DatabaseSelectHelper.getRoleName(id).equals(roleName)) {
        return id;
      }
    }
    return -1;
  }

  /*
   * Begin Phase 2 upgrade DatabaseInsertHelper functions
   */

  /**
   * This methods inserts an account so a customer can have the option to return
   * 
   * @param userId
   * @throws InvalidUserException, SQLException, DatabaseInsertException
   * @return accountId
   */
  public static int insertAccount(int userId, boolean active)
      throws InvalidUserIdException, SQLException, DatabaseInsertException {
    if (!ValidateUserRole.isValidUserId(userId)) {
      throw new InvalidUserIdException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int accountId = DatabaseInserter.insertAccount(userId, active, connection);
    connection.close();
    return accountId;
  }

  /**
   * This methods inserts an account line
   * 
   * @param accountId, itemId, quantity
   * @throws InvalidQuantityException, SQLException, DatabaseInsertException, InvalidItemIdException
   * @return id
   */

  public static int insertAccountLine(int accountId, int itemId, int quantity) throws SQLException,
      InvalidItemIdException, DatabaseInsertException, InvalidQuantityException {
    if (!ValidateInventory.isValidItemId(itemId)) {
      throw new InvalidItemIdException();
    } else if (!ValidateItemizedsales.isValidQuantity(quantity)) {
      throw new InvalidQuantityException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int id = insertAccountLine(accountId, itemId, quantity, connection);
    return id;
  }

  /*
   * End of Phase 2 upgrade DatabaseInsertHelper functions
   */

  public static int insertRole(String name)
      throws DatabaseInsertException, SQLException, InvalidRoleException {
    if (!ValidateRoles.isValidRoleName(name)) {
      throw new InvalidRoleException();
    }
    // Check for duplicate roles
    if (ValidateRoles.isContainsRole(name)) {
      return getRoleId(name);
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int roleId = DatabaseInserter.insertRole(name, connection);
    connection.close();
    return roleId;
  }

  public static int insertNewUser(String name, int age, String address, String password)
      throws InvalidNameException, DatabaseInsertException, SQLException, InvalidAddressException,
      InvalidAgeException, InvalidPasswordException {
    if (!ValidateUsers.isValidName(name)) {
      throw new InvalidNameException();
    } else if (!ValidateUsers.isValidAge(age)) {
      throw new InvalidAgeException();
    } else if (!ValidateUsers.isValidAddress(address)) {
      throw new InvalidAddressException();
    } else if (!ValidateUsers.isValidPassword(password)) {
      throw new InvalidPasswordException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int userId = DatabaseInserter.insertNewUser(name, age, address, password, connection);
    connection.close();
    return userId;
  }

  public static int insertUserRole(int userId, int roleId)
      throws DatabaseInsertException, SQLException, InvalidUserIdException, InvalidRoleIdException {

    if (!ValidateUserRole.isValidRoleId(roleId) && !ValidateUserRole.isValidUserId(userId)) {
      throw new InvalidRoleIdException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int userRoleId = DatabaseInserter.insertUserRole(userId, roleId, connection);
    connection.close();
    return userRoleId;
  }

  public static int insertItem(String name, BigDecimal price) throws DatabaseInsertException,
      SQLException, InvalidItemNameException, InvalidPriceException {
    if (!ValidateItems.isValidName(name)) {
      throw new InvalidItemNameException();
    } else if (!ValidateItems.isValidPrice(price)) {
      throw new InvalidPriceException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int itemId = DatabaseInserter.insertItem(name, price, connection);
    connection.close();
    return itemId;
  }

  public static int insertInventory(int itemId, int quantity) throws SQLException,
      DatabaseInsertException, InvalidItemIdException, InvalidQuantityException {
    if (!ValidateInventory.isValidItemId(itemId)) {
      throw new InvalidItemIdException();
    } else if (!ValidateInventory.isValidQuantity(quantity)) {
      throw new InvalidQuantityException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int inventoryId = DatabaseInserter.insertInventory(itemId, quantity, connection);
    connection.close();
    return inventoryId;
  }

  /**
   * InsertSale into database
   * 
   * @param userId
   * @param totalPrice
   * @return saleId
   * @throws SQLException, InvalidUserIdException, DatabaseInsertException, InvalidPriceException
   */
  public static int insertSale(int userId, BigDecimal totalPrice)
      throws InvalidUserIdException, DatabaseInsertException, SQLException, InvalidPriceException {
    if (!ValidateSales.isValidUserId(userId)) {
      throw new InvalidUserIdException();
    } else if (totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
      throw new InvalidPriceException();
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int saleId = DatabaseInserter.insertSale(userId, totalPrice, connection);
    connection.close();
    return saleId;
  }

  /**
   * This methods inserts an itemized sale to a HashMap to keep track for future use
   * 
   * @param saleId, itemId, quantity
   * @throws InvalidUserException, SQLException, DatabaseInsertExceptionInvalid, QuantityException,
   *         InvalidSaleIdException, InvalidUserIdException
   * @return itemId
   */

  public static int insertItemizedSale(int saleId, int itemId, int quantity)
      throws SQLException, InvalidItemIdException, DatabaseInsertException,
      InvalidQuantityException, InvalidSaleIdException, InvalidUserIdException {

    if (!ValidateItemizedsales.isValidItemId(itemId)) {
      throw new InvalidItemIdException();
    } else if (!ValidateItemizedsales.isValidQuantity(quantity)) {
      throw new InvalidQuantityException();
    } else if (!ValidateItemizedsales.isValidSaleId(saleId)) {
      throw new InvalidSaleIdException();
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int itemizedId = DatabaseInserter.insertItemizedSale(saleId, itemId, quantity, connection);
    connection.close();
    return itemizedId;
  }

  // Phase 3
  public static int insertUser(String name, int age, String address)
      throws InvalidNameException, InvalidAgeException, InvalidAddressException, SQLException {
    if (!ValidateUsers.isValidName(name)) {
      throw new InvalidNameException();
    } else if (!ValidateUsers.isValidAge(age)) {
      throw new InvalidAgeException();
    } else if (!ValidateUsers.isValidAddress(address)) {
      throw new InvalidAddressException();
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int userId = DatabaseInserter.insertSerializedUser(name, age, address, connection);
    connection.close();
    return userId;
  }

  public static boolean insertUnhashedPassword(int userId, String password) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean result = DatabaseInserter.insertUnhashedPassword(password, userId,
        connection);
    connection.close();
    return result;
  }

}
