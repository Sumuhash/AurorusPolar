package src.com.b07.validate;

import java.math.BigDecimal;



public class ValidateItems {

  /**
   * validate name.
   * 
   * @param name to be validated.
   * @return true/false.
   */
  public static boolean isValidName(String name) {
    if (name != null && name.length() < 64) {
      return true;
    }
    return false;
  }

  /**
   * validate price.
   * 
   * @param price to be validated.
   * @return true/false.
   */
  public static boolean isValidPrice(BigDecimal price) {

    BigDecimal zero = new BigDecimal("0.00");
    String[] temp = price.toString().split("\\.");

    if (temp.length > 1 && price.compareTo(zero) == 1) {
      if (temp[1].length() == 2) {
        return true;
      }
    }
    return false;
  }

}
