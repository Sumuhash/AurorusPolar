package src.com.b07.database.helper;

import android.content.Context;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import src.com.b07.database.DatabaseDriver;
import src.com.b07.database.DatabaseDriverAndroid;
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


public class DatabaseInsertHelperAndroid extends DatabaseDriverAndroid {

    public DatabaseInsertHelperAndroid(Context context)
    {
        super(context);
    }
    // Helper
    /**
     * This methods is a helper to find the RoleId given a roleName
     *
     * @param roleName
     * @return id
     */


  public int getRoleId(String roleName, DatabaseSelectHelperAndroid sdb) throws SQLException {

    //DatabaseSelectHelper temp = new DatabaseSelectHelper(this);

    List<Integer> roleIds = sdb.getRoleIdsAndroid();
    for (Integer id : roleIds) {
      if (sdb.getRoleNameAndroid(id).equals(roleName)) {
        return id; //hi
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
     * @throws InvalidUserIdException, SQLException, DatabaseInsertException
     * @return accountId
     */

    public int insertAccountAndroid(int userId, boolean active, DatabaseSelectHelperAndroid sdb)
            throws InvalidUserIdException, SQLException, DatabaseInsertException {
        if (!ValidateUserRole.isValidUserIdAndroid(userId, sdb)) {
            throw new InvalidUserIdException();
        }
        //Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
        int accountId = Math.toIntExact(super.insertAccount(userId, active));
        return accountId;
    }

    /**
     * This methods inserts an account line
     *
     * @param accountId, itemId, quantity
     * @throws InvalidQuantityException, SQLException, DatabaseInsertException, InvalidItemIdException
     * @return id
     */

    public int insertAccountLineAndroid(int accountId, int itemId, int quantity, DatabaseSelectHelperAndroid sdb) throws SQLException,
            InvalidItemIdException, DatabaseInsertException, InvalidQuantityException {
        if (!ValidateInventory.isValidItemIdAndroid(itemId, sdb)) {
            throw new InvalidItemIdException();
        } else if (!ValidateItemizedsales.isValidQuantity(quantity)) {
            throw new InvalidQuantityException();
        }

        int id = Math.toIntExact(super.insertAccountLine(accountId, itemId, quantity));
        return id;
    }

    /*
     * End of Phase 2 upgrade DatabaseInsertHelper functions
     */

  public int insertRoleAndroid(String name, DatabaseSelectHelperAndroid sdb)
      throws DatabaseInsertException, SQLException, InvalidRoleException {
    if (!ValidateRoles.isValidRoleName(name)) {
      throw new InvalidRoleException();
    }
    // Check for duplicate roles
    if (ValidateRoles.containsRoleAndroid(name, sdb)) {
     return getRoleId(name, sdb);
    }

    int roleId = Math.toIntExact(super.insertRole(name));
    return roleId;
  }

    public int insertNewUserAndroid(String name, int age, String address, String password)
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

        int userId = Math.toIntExact(super.insertNewUser(name, age, address, password));
        return userId;
    }

    public int insertUserRoleAndroid(int userId, int roleId, DatabaseSelectHelperAndroid sdb)
            throws DatabaseInsertException, SQLException, InvalidUserIdException, InvalidRoleIdException {

        if (!ValidateUserRole.isValidRoleIdAndroid(roleId, sdb) && !ValidateUserRole.isValidUserIdAndroid(userId, sdb)) {
            throw new InvalidRoleIdException();
        }
        int userRoleId = Math.toIntExact(super.insertUserRole(userId, roleId));
        return userRoleId;
    }

    public int insertItemAndroid(String name, BigDecimal price) throws DatabaseInsertException,
            SQLException, InvalidItemNameException, InvalidPriceException {

      if (!ValidateItems.isValidName(name)) {
            throw new InvalidItemNameException();
        } else if (!ValidateItems.isValidPrice(price)) {
            throw new InvalidPriceException();
        }
        int itemId = Math.toIntExact(super.insertItem(name, price));
        return itemId;
    }

    public int insertInventoryAndroid(int itemId, int quantity, DatabaseSelectHelperAndroid sdb) throws SQLException,
            DatabaseInsertException, InvalidItemIdException, InvalidQuantityException {

      if (!ValidateInventory.isValidItemIdAndroid(itemId, sdb)) {
            throw new InvalidItemIdException();
        } else if (!ValidateInventory.isValidQuantity(quantity)) {
            throw new InvalidQuantityException();
        }
        int inventoryId = Math.toIntExact(super.insertInventory(itemId, quantity));
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
    public int insertSaleAndroid(int userId, BigDecimal totalPrice, DatabaseSelectHelperAndroid sdb)
            throws InvalidUserIdException, DatabaseInsertException, SQLException, InvalidPriceException {
        if (!ValidateSales.isValidUserIdAndroid(userId, sdb)) {
            throw new InvalidUserIdException();
        } else if (totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceException();
        }

        int saleId = Math.toIntExact(super.insertSale(userId, totalPrice));
        return saleId;
    }

    /**
     * This methods inserts an itemized sale to a HashMap to keep track for future use
     *
     * @param saleId, itemId, quantity
     * @throws InvalidUserIdException, SQLException, DatabaseInsertExceptionInvalid, QuantityException,
     *         InvalidSaleIdException, InvalidUserIdException
     * @return itemId
     */

    public int insertItemizedSaleAndroid(int saleId, int itemId, int quantity, DatabaseSelectHelperAndroid sdb)
            throws SQLException, InvalidItemIdException, DatabaseInsertException,
            InvalidQuantityException, InvalidSaleIdException, InvalidUserIdException {

        if (!ValidateItemizedsales.isValidItemIdAndroid(itemId, sdb)) {
            throw new InvalidItemIdException();
        } else if (!ValidateItemizedsales.isValidQuantity(quantity)) {
            throw new InvalidQuantityException();
        } else if (!ValidateItemizedsales.isValidSaleIdAndroid(saleId, sdb)) {
            throw new InvalidSaleIdException();
        }

        int itemizedId = Math.toIntExact(super.insertItemizedSale(saleId, itemId, quantity));
        return itemizedId;
    }

    // Phase 3
  /*
  public int insertUser(String name, int age, String address)
      throws InvalidNameException, InvalidAgeException, InvalidAddressException, SQLException {
    if (!ValidateUsers.isValidName(name)) {
      throw new InvalidNameException();
    } else if (!ValidateUsers.isValidAge(age)) {
      throw new InvalidAgeException();
    } else if (!ValidateUsers.isValidAddress(address)) {
      throw new InvalidAddressException();
    }

    int userId = Math.toIntExact(super.insertSerializedUser(name, age, address));
    return userId;
  }

  public static boolean insertUnhashedPassword(int userId, String password) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean result = DatabaseInserter.insertUnhashedPassword(password, userId,
        connection);
    connection.close();
    return result;
  }*/

}
