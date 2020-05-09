package src.com.b07.store;

import java.sql.SQLException;
import java.util.List;
import src.com.b07.database.helper.DatabaseInsertHelper;
import src.com.b07.database.helper.DatabaseInsertHelperAndroid;
import src.com.b07.database.helper.DatabaseSelectHelper;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.database.helper.DatabaseUpdateHelper;
import src.com.b07.enums.Roles;
import src.com.b07.exceptions.DatabaseInsertException;
import src.com.b07.exceptions.InvalidAddressException;
import src.com.b07.exceptions.InvalidAgeException;
import src.com.b07.exceptions.InvalidItemIdException;
import src.com.b07.exceptions.InvalidNameException;
import src.com.b07.exceptions.InvalidPasswordException;
import src.com.b07.exceptions.InvalidQuantityException;
import src.com.b07.exceptions.InvalidRoleException;
import src.com.b07.exceptions.InvalidRoleIdException;
import src.com.b07.exceptions.InvalidUserIdException;
import src.com.b07.inventory.Inventory;
import src.com.b07.inventory.Item;
import src.com.b07.users.Employee;

public class EmployeeInterface {
  private Employee currentEmployee;
  private Inventory inventory;

  /**
   * Creates a new employee interface with employee and inventory
   * @param employee
   * @param inventory
   */
  public EmployeeInterface(Employee employee, Inventory inventory) {
    this.currentEmployee = employee;
    this.inventory = inventory;
  }

  /**
   * Creates a new employee interface with inventory
   * @param inventory
   */
  public EmployeeInterface(Inventory inventory) {
    this.currentEmployee = null;
    this.inventory = inventory;
  }

  /**
   * Returns the current employee of this employee interface
   * 
   * @return the current employee of this employee interface
   */
  public Employee getCurrentEmployee() {
    return this.currentEmployee;
  }



  /**
   * Sets the current employee of this employee interface to employee
   * 
   * @param employee
   */
  public void setCurrentEmployee(Employee employee) {
    this.currentEmployee = employee;
  }

  /**
   * Returns true if the this employee interface has a current employee
   * 
   * @return true if the this employee interface has a current employee
   */
  public boolean hasCurrentEmployee() {
    return this.currentEmployee != null;
  }

  /**
   * Returns true if item in the database and current EmployeeInterface is successfully restocked
   * 
   * @param item
   * @param quantity
   * @return true is restocked item successfully
   */
  public boolean restockInventory(Item item, int quantity) {
    int prevQuantity = 0;
    try {
      // Check if item is already in the database inventory
      prevQuantity = DatabaseSelectHelper.getInventoryQuantity(item.getId());
    } catch (SQLException e) {

      // If item isn't in the database inventory, try to add it
      try {
        DatabaseInsertHelper.insertInventory(item.getId(), quantity);
      } catch (SQLException e1) {
        System.out.println("Error connecting to database");
        return false;
      } catch (DatabaseInsertException e1) {
        System.out.println("Error inserting to database");
        return false;
      } catch (InvalidItemIdException e1) {
        System.out.println("Invalid Item ID");
        return false;
      } catch (InvalidQuantityException e1) {
        System.out.println("Invalid Quantity");
        return false;
      }
    }
    try {
      DatabaseUpdateHelper.updateInventoryQuantity(prevQuantity + quantity, item.getId());
    } catch (SQLException e) {
      System.out.println("Error connecting to database");
      return false;
    } catch (InvalidQuantityException e) {
      System.out.println("Invalid Quantity");
      return false;
    } catch (InvalidItemIdException e) {
      System.out.println("Invalid Item ID");
      return false;
    }
    this.inventory.updateMap(item, prevQuantity + quantity);
    return true;
  }

  /**
   * Creates a customer and adds them to the database, returning their user ID
   * 
   * @param name
   * @param age
   * @param address
   * @param password
   * @return the new customer's ID
   * @throws InvalidNameException
   * @throws DatabaseInsertException
   * @throws SQLException
   * @throws InvalidAddressException
   * @throws InvalidAgeException
   * @throws InvalidPasswordException
   * @throws InvalidRoleException
   * @throws InvalidUserIdException
   * @throws InvalidRoleIdException
   */
  public int createCustomer(String name, int age, String address, String password)
      throws InvalidNameException, DatabaseInsertException, SQLException, InvalidAddressException,
      InvalidAgeException, InvalidPasswordException, InvalidRoleException, InvalidUserIdException,
      InvalidRoleIdException {
    int userId = DatabaseInsertHelper.insertNewUser(name, age, address, password);
    int roleId = 0;

    List<Integer> roleIds = DatabaseSelectHelper.getRoleIds();
    for (Integer i : roleIds) {
      if (DatabaseSelectHelper.getRoleName(i).equals(Roles.CUSTOMER.toString()) && roleId == 0) {
        roleId = i;
      }
    }
    DatabaseInsertHelper.insertUserRole(userId, roleId);
    return userId;
  }

  /**
   * Creates a new employee and adds them to the database, returning their user ID
   * 
   * @param name
   * @param age
   * @param address
   * @param password
   * @return the new employee's ID
   * @throws InvalidNameException
   * @throws DatabaseInsertException
   * @throws SQLException
   * @throws InvalidAddressException
   * @throws InvalidAgeException
   * @throws InvalidPasswordException
   * @throws InvalidRoleException
   * @throws InvalidUserIdException
   * @throws InvalidRoleIdException
   */
  public int createEmployee(String name, int age, String address, String password)
      throws InvalidNameException, DatabaseInsertException, SQLException, InvalidAddressException,
      InvalidAgeException, InvalidPasswordException, InvalidRoleException, InvalidUserIdException,
      InvalidRoleIdException {
    int userId = DatabaseInsertHelper.insertNewUser(name, age, address, password);
    // int roleId = DatabaseInsertHelper.insertRole(Roles.EMPLOYEE.toString());
    DatabaseInsertHelper.insertUserRole(userId, this.currentEmployee.getRoleId());
    return userId;
  }

  public int createEmployeeAndroid(String name, int age, String address, String password, DatabaseInsertHelperAndroid idb, DatabaseSelectHelperAndroid sdb)
          throws InvalidNameException, DatabaseInsertException, SQLException, InvalidAddressException,
          InvalidAgeException, InvalidPasswordException, InvalidRoleException, InvalidUserIdException,
          InvalidRoleIdException {
    int userId = idb.insertNewUserAndroid(name, age, address, password);
    // int roleId = DatabaseInsertHelper.insertRole(Roles.EMPLOYEE.toString());
    idb.insertUserRoleAndroid(userId, this.currentEmployee.getRoleId(), sdb);
    return userId;
  }

  public int createCustomerAndroid(String name, int age, String address, String password, DatabaseInsertHelperAndroid idb, DatabaseSelectHelperAndroid sdb)
          throws InvalidNameException, DatabaseInsertException, SQLException, InvalidAddressException,
          InvalidAgeException, InvalidPasswordException, InvalidRoleException, InvalidUserIdException,
          InvalidRoleIdException {
    int userId = idb.insertNewUserAndroid(name, age, address, password);
    int roleId = 0;

    List<Integer> roleIds = sdb.getRoleIdsAndroid();
    for (Integer i : roleIds) {
      if (sdb.getRoleNameAndroid(i).equals(Roles.CUSTOMER.toString()) && roleId == 0) {
        roleId = i;
      }
    }
    idb.insertUserRoleAndroid(userId, roleId, sdb);
    return userId;
  }
}
