package src.com.b07.validate;

import java.sql.SQLException;
import java.util.List;
import src.com.b07.database.helper.DatabaseSelectHelper;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.enums.Roles;

public class ValidateRoles {

  public static boolean isValidRoleName(String name) {

    for (Roles role : Roles.values()) {
      if (role.name().equals(name)) {
        return true;
      }
    }
    return false;

  }
  public static boolean isContainsRole(String roleName) throws SQLException {
    List<Integer> roleIdList = DatabaseSelectHelper.getRoleIds();
    boolean containsRole = false;
    for (int roleId : roleIdList) {
      if (DatabaseSelectHelper.getRoleName(roleId).equals(roleName)) {
        containsRole = true;
      }
    }
    return containsRole;
  }

  //Android Validators
  public static boolean containsRoleAndroid(String roleName, DatabaseSelectHelperAndroid sdb) throws SQLException {
    List<Integer> roleIdList = sdb.getRoleIdsAndroid();
    boolean containsRole = false;
    for (int roleId : roleIdList) {
      if (sdb.getRoleNameAndroid(roleId).equals(roleName)) {
        containsRole = true;
      }
    }
    return containsRole;
  }

}
