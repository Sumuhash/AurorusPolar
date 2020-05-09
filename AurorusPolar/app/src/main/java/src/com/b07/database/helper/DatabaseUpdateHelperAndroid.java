package src.com.b07.database.helper;

import android.content.Context;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import src.com.b07.database.DatabaseDriverAndroid;
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
import src.com.b07.validate.*;
import src.com.b07.validate.ValidateUsers;

public class DatabaseUpdateHelperAndroid extends DatabaseDriverAndroid {

    public DatabaseUpdateHelperAndroid(Context context)
    {
        super(context);
    }
  /*
  public boolean updateRoleNameAndroid(String name, int id)
      throws InvalidRoleException, SQLException, InvalidRoleIdException {
    List<Integer> roleIds = DatabaseSelectHelper.getRoleIds();

    if (!ValidateRoles.isValidRoleName(name)) {
      throw new InvalidRoleException();
    } else if (!roleIds.contains(id)) {
      throw new InvalidRoleIdException();
    }
    boolean complete = super.updateRoleName(name, id);
    return complete;
  }*/

    public boolean updateUserNameAndroid(String name, int userId)
            throws SQLException, InvalidUserIdException, InvalidNameException {
        if (!ValidateUsers.isValidName(name)) {
            throw new InvalidNameException();
        } else if (!ValidateUserRole.isValidUserId(userId)) {
            throw new InvalidUserIdException();
        }
        boolean complete = super.updateUserName(name, userId);
        return complete;
    }

    public boolean updateUserAgeAndroid(int age, int userId)
            throws SQLException, InvalidAgeException, InvalidUserIdException {
        if (!ValidateUsers.isValidAge(age)) {
            throw new InvalidAgeException();
        } else if (!ValidateUserRole.isValidUserId(userId)) {
            throw new InvalidUserIdException();
        }

        boolean complete = super.updateUserAge(age, userId);
        return complete;
    }

    public boolean updateUserAddressAndroid(String address, int userId)
            throws SQLException, InvalidAddressException, InvalidUserIdException {
        if (!ValidateUsers.isValidAddress(address)) {
            throw new InvalidAddressException();
        } else if (!ValidateUserRole.isValidUserId(userId)) {
            throw new InvalidUserIdException();
        }

        boolean complete = super.updateUserAddress(address, userId);
        return complete;

    }

    public boolean updateUserRoleAndroid(int roleId, int userId, DatabaseSelectHelperAndroid sdb)
            throws SQLException, InvalidUserIdException, InvalidRoleIdException {

        if (!ValidateUserRole.isValidUserIdAndroid(userId, sdb)) {
            throw new InvalidUserIdException();
        } else if (!ValidateUserRole.isValidRoleIdAndroid(roleId, sdb)) {
            throw new InvalidRoleIdException();
        }
        boolean complete = super.updateUserRole(roleId, userId);
        return complete;
    }

    public boolean updateItemNameAndroid(String name, int itemId)
            throws SQLException, InvalidItemNameException, InvalidItemIdException {
        if (!ValidateItems.isValidName(name)) {
            throw new InvalidItemNameException();
        } else if (!ValidateInventory.isValidItemId(itemId)) {
            throw new InvalidItemIdException();
        }

        boolean complete = super.updateItemName(name, itemId);
        return complete;
    }

    public boolean updateItemPriceAndroid(BigDecimal price, int itemId)
            throws SQLException, InvalidPriceException, InvalidItemIdException {
        if (!ValidateItems.isValidPrice(price)) {
            throw new InvalidPriceException();
        } else if (!ValidateInventory.isValidItemId(itemId)) {
            throw new InvalidItemIdException();
        }

        boolean complete = super.updateItemPrice(price, itemId);
        return complete;
    }

    public boolean updateInventoryQuantityAndroid(int quantity, int itemId)
            throws SQLException, InvalidQuantityException, InvalidItemIdException {
        if (!ValidateInventory.isValidQuantity(quantity)) {
            throw new InvalidQuantityException();
        } else if (!ValidateInventory.isValidItemId(itemId)) {
            throw new InvalidItemIdException();
        }
        boolean complete = super.updateInventoryQuantity(quantity, itemId);
        return complete;
    }

    public boolean updateAccountStatusAndroid(int accountId, boolean active) throws SQLException {

        boolean complete = super.updateAccountStatus(accountId, active);
        return complete;
    }
}
