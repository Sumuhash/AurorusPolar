package src.com.b07.validate;

public class ValidateUsers {
  public static boolean isValidAddress(String address) {
    if (address.length() < 100 && address != null && address.length() > 0) {
      return true;
    }
    return false;
  }

  public static boolean isValidName(String name) {
    if (name != null && !name.isEmpty()) {
      return true;
    }
    return false;
  }

  public static boolean isValidPassword(String password) {
    if (password != null && !password.isEmpty()) {
      return true;
    }
    return false;
  }

  public static boolean isValidAge(int age) {
    if (age > 0 ) {
      return true;
    }
    return false;
  }

}
