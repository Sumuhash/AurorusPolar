package src.com.b07.database.helper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import src.com.b07.database.DatabaseUpdater;
import src.com.b07.exceptions.InvalidAddressException;
import src.com.b07.exceptions.InvalidAgeException;
import src.com.b07.exceptions.InvalidItemIdException;
import src.com.b07.exceptions.InvalidItemNameException;
import src.com.b07.exceptions.InvalidNameException;
import src.com.b07.exceptions.InvalidPriceException;
import src.com.b07.exceptions.InvalidQuantityException;
import src.com.b07.exceptions.InvalidRoleException;
import src.com.b07.exceptions.InvalidRoleIdException;
import src.com.b07.exceptions.InvalidUserIdException;
import src.com.b07.validate.ValidateInventory;
import src.com.b07.validate.ValidateRoles;
import src.com.b07.validate.ValidateUserRole;
import src.com.b07.validate.ValidateUsers;
import src.com.b07.validate.ValidateItems;

public class DatabaseUpdateHelper extends DatabaseUpdater {

  public static boolean updateRoleName(String name, int id)
      throws InvalidRoleException, SQLException, InvalidRoleIdException {
    List<Integer> roleIds = DatabaseSelectHelper.getRoleIds();

    if (!ValidateRoles.isValidRoleName(name)) {
      throw new InvalidRoleException();
    } else if (!roleIds.contains(id)) {
      throw new InvalidRoleIdException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateRoleName(name, id, connection);
    connection.close();
    return complete;
  }

  public static boolean updateUserName(String name, int userId)
      throws SQLException, InvalidUserIdException, InvalidNameException {
    if (!ValidateUsers.isValidName(name)) {
      throw new InvalidNameException();
    } else if (!ValidateUserRole.isValidUserId(userId)) {
      throw new InvalidUserIdException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserName(name, userId, connection);
    connection.close();
    return complete;
  }

  public static boolean updateUserAge(int age, int userId)
      throws SQLException, InvalidAgeException, InvalidUserIdException {
    if (!ValidateUsers.isValidAge(age)) {
      throw new InvalidAgeException();
    } else if (!ValidateUserRole.isValidUserId(userId)) {
      throw new InvalidUserIdException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserAge(age, userId, connection);
    connection.close();
    return complete;
  }

  public static boolean updateUserAddress(String address, int userId)
      throws SQLException, InvalidAddressException, InvalidUserIdException {
    if (!ValidateUsers.isValidAddress(address)) {
      throw new InvalidAddressException();
    } else if (!ValidateUserRole.isValidUserId(userId)) {
      throw new InvalidUserIdException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserAddress(address, userId, connection);
    connection.close();
    return complete;

  }

  public static boolean updateUserRole(int roleId, int userId)
      throws SQLException, InvalidUserIdException, InvalidRoleIdException {

    if (!ValidateUserRole.isValidUserId(userId)) {
      throw new InvalidUserIdException();
    } else if (!ValidateUserRole.isValidRoleId(roleId)) {
      throw new InvalidRoleIdException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserRole(roleId, userId, connection);
    connection.close();
    return complete;
  }

  public static boolean updateItemName(String name, int itemId)
      throws SQLException, InvalidItemNameException, InvalidItemIdException {
    if (!ValidateItems.isValidName(name)) {
      throw new InvalidItemNameException();
    } else if (!ValidateInventory.isValidItemId(itemId)) {
      throw new InvalidItemIdException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateItemName(name, itemId, connection);
    connection.close();
    return complete;
  }

  public static boolean updateItemPrice(BigDecimal price, int itemId)
      throws SQLException, InvalidPriceException, InvalidItemIdException {
    if (!ValidateItems.isValidPrice(price)) {
      throw new InvalidPriceException();
    } else if (!ValidateInventory.isValidItemId(itemId)) {
      throw new InvalidItemIdException();
    }

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateItemPrice(price, itemId, connection);
    connection.close();
    return complete;
  }

  public static boolean updateInventoryQuantity(int quantity, int itemId)
      throws SQLException, InvalidQuantityException, InvalidItemIdException {
    if (!ValidateInventory.isValidQuantity(quantity)) {
      throw new InvalidQuantityException();
    } else if (!ValidateInventory.isValidItemId(itemId)) {
      throw new InvalidItemIdException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateInventoryQuantity(quantity, itemId, connection);
    connection.close();
    return complete;
  }

  public static boolean updateAccountStatus(int accountId, boolean active) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateAccountStatus(accountId, active, connection);
    connection.close();
    return complete;
  }
}
