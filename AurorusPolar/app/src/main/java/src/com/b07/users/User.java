package src.com.b07.users;

import java.io.Serializable;
import java.sql.SQLException;
import src.com.b07.database.helper.DatabaseSelectHelper;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.exceptions.InvalidUserIdException;
import src.com.b07.security.PasswordHelpers;

public abstract class User implements Serializable {
  
  private int id;
  private String name;
  private int age;
  private String address; 
  private int roleId;
  private boolean authenticated;
  
  public User(int id, String name, int age, String address) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
    this.roleId = 0;
    this.authenticated = false;
  }

  public User(int id, String name, int age, String address, boolean authenticated) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
    this.authenticated = authenticated;
  }
  
  
  public int getId() {
    return id;
  }
  
  public void setId(int newId) {
    this.id = newId;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
  
  public int getRoleId() {
    return roleId;
  }
  
  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }
  /**
   * Returns true if password matches the user's password stored in the database
   * @param password
   * @return true if password matches the user's stored password
   * @throws InvalidUserIdException 
   */
  public final boolean authenticate(String password){
    String userPassword = "";
    try {
      userPassword = DatabaseSelectHelper.getPassword(id);
    } catch (SQLException e) {
      System.out.println("Error Accessing Database, Try again later");
      return false;
    }
    this.authenticated = PasswordHelpers.comparePassword(userPassword, password);
    return PasswordHelpers.comparePassword(userPassword, password);
  }

  //Android Aunthenticate
  public final boolean authenticateAndroid(String password, DatabaseSelectHelperAndroid sdb ){
    String userPassword = "";
    try {
      userPassword = sdb.getPasswordAndroid(id);
    } catch (SQLException e) {
      System.out.println("Error Accessing Database, Try again later");
      return false;
    }
    this.authenticated = PasswordHelpers.comparePassword(userPassword, password);
    return PasswordHelpers.comparePassword(userPassword, password);
  }

}
