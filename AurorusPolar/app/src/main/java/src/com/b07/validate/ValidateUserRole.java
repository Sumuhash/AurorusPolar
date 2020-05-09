package src.com.b07.validate;

import java.sql.SQLException;
import src.com.b07.database.helper.DatabaseSelectHelper;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.users.User;

public class ValidateUserRole {

  /**
   * validate userId.
   * 
   * @param userId to be validated.
   * @return true/false.
   * @throws SQLException if the connection is in use or unavailable.
   */
  public static boolean isValidUserId(int userId) throws SQLException {

    User temp = DatabaseSelectHelper.getUserDetails(userId);
    if (temp != null) {
      return true;
    }
    return false;
  }


  /**
   * validate roleId.
   * 
   * @param roleId to be validated.
   * @return true/false.
   * @throws SQLException if the connection is in use or unavailable.
   */
  public static boolean isValidRoleId(int roleId) throws SQLException {

    if (DatabaseSelectHelper.getRoleName(roleId) != null) {
      return true;
    }

    return false;
  }

  //Android Validators
  public static boolean isValidUserIdAndroid(int userId, DatabaseSelectHelperAndroid sdb) throws SQLException {
    User temp = sdb.getUserDetailsAndroid(userId);
    if (temp != null) {
      return true;
    }
    return false;
  }

  public static boolean isValidRoleIdAndroid(int roleId, DatabaseSelectHelperAndroid sdb) throws SQLException {

    if (sdb.getRoleNameAndroid(roleId) != null) {
      return true;
    }
    return false;
  }
}
