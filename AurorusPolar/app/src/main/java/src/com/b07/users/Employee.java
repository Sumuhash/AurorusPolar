package src.com.b07.users;

public class Employee extends User {

  public Employee(int id, String name, int age, String address) {
    super(id, name, age, address);
  }

  public Employee(int id, String name, int age, String address, boolean authenticated) {
    super(id, name, age, address, authenticated);
  }
}
