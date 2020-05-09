package src.com.b07.store;

import java.io.Serializable;
import java.math.BigDecimal;
import src.com.b07.users.User;

public class SaleImpl implements Sale, Serializable {
  private int id;
  private User user;
  private BigDecimal totalPrice;

  public SaleImpl(int id, User user, BigDecimal totalPrice) {
    this.id = id;
    this.user = user;
    this.totalPrice = totalPrice;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal price) {
    this.totalPrice = price;
  }

}
