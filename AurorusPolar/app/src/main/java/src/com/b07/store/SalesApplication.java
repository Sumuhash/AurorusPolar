package src.com.b07.store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import src.com.b07.database.DatabaseDriver;
import src.com.b07.store.*;
import src.com.b07.database.helper.DatabaseInsertHelper;
import src.com.b07.database.helper.DatabaseSelectHelper;
import src.com.b07.database.helper.DatabaseUpdateHelper;
import src.com.b07.enums.ItemTypes;
import src.com.b07.enums.Roles;
import src.com.b07.exceptions.ConnectionFailedException;
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
import src.com.b07.exceptions.InvalidUserIdException;
import src.com.b07.inventory.Inventory;
import src.com.b07.inventory.Item;
import src.com.b07.serializableDatabase.DatabaseDeserializer;
import src.com.b07.serializableDatabase.DatabaseSerializer;
import src.com.b07.users.Admin;
import src.com.b07.users.Customer;
import src.com.b07.users.Employee;
import src.com.b07.users.User;
import src.com.b07.validate.ValidateUserRole;
import src.com.b07.*;

public class SalesApplication {
  /**
   * This is the main method to run your entire program! Follow the "Pulling it together"
   * instructions to finish this off.
   * 
   * @param argv unused.
   */
  public static void main(String[] argv) {
    BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    if (connection == null) {
      System.out.print("NOOO");
    }
    try {
      // TODO Check what is in argv
      // If it is -1
      /*
       * TODO This is for the first run only! Add this code:
       * DatabaseDriverExtender.initialize(connection); Then add code to create your first account,
       * an administrator with a password Once this is done, create an employee account as well.
       * 
       */
      // If it is 1
      /*
       * TODO In admin mode, the user must first login with a valid admin account This will allow
       * the user to promote employees to admins. Currently, this is all an admin can do.
       */
      // If anything else - including nothing
      /*
       * TODO Create a context menu, where the user is prompted with: 1 - Employee Login 2 -
       * Customer Login 0 - Exit Enter Selection:
       */
      // If the user entered 1
      /*
       * TODO Create a context menu for the Employee interface Prompt the employee for their id and
       * password Attempt to authenticate them. If the Id is not that of an employee or password is
       * incorrect, end the session If the Id is an employee, and the password is correct, create an
       * EmployeeInterface object then give them the following options: 1. authenticate new employee
       * 2. Make new User 3. Make new account 4. Make new Employee 5. Restock Inventory 6. Exit
       * 
       * Continue to loop through as appropriate, ending once you get an exit code (9)
       */
      // If the user entered 2
      /*
       * TODO create a context menu for the customer Shopping cart Prompt the customer for their id
       * and password Attempt to authenticate them If the authentication fails or they are not a
       * customer, repeat If they get authenticated and are a customer, give them this menu: 1. List
       * current items in cart 2. Add a quantity of an item to the cart 3. Check total price of
       * items in the cart 4. Remove a quantity of an item from the cart 5. check out 6. Exit
       * 
       * When checking out, be sure to display the customers total, and ask them if they wish to
       * continue shopping for a new order
       * 
       * For each of these, loop through and continue prompting for the information needed Continue
       * showing the context menu, until the user gives a 6 as input.
       */
      // If the user entered 0
      /*
       * TODO Exit condition
       */
      // If the user entered anything else:
      /*
       * TODO Re-prompt the user
       */

      String input = "";
      /*
       * Initialization Mode, Create new Admin and Employee Account and initialize ITEMS, ROLES and
       * INVENTORY tables in database
       */
      if (argv.length != 0 && argv[0].equals("-1")) {
        DatabaseDriverExtender.initialize(connection);

        // Initialize database with roles
        try {
          DatabaseInsertHelper.insertRole(Roles.ADMIN.toString());
          DatabaseInsertHelper.insertRole(Roles.EMPLOYEE.toString());
          DatabaseInsertHelper.insertRole(Roles.CUSTOMER.toString());

        } catch (DatabaseInsertException e1) {
        } catch (SQLException e1) {
        } catch (InvalidRoleException e1) {
        }

        // Initialize Database with items and empty inventory
        try {
          int fishing_rod = DatabaseInsertHelper.insertItem(ItemTypes.FISHING_ROD.toString(),
              new BigDecimal("25.50"));
          int hockey_stick = DatabaseInsertHelper.insertItem(ItemTypes.HOCKEY_STICK.toString(),
              new BigDecimal("99.99"));
          int protein_bar = DatabaseInsertHelper.insertItem(ItemTypes.PROTEIN_BAR.toString(),
              new BigDecimal("5.25"));
          int shoes = DatabaseInsertHelper.insertItem(ItemTypes.RUNNING_SHOES.toString(),
              new BigDecimal("50.79"));
          int skates =
              DatabaseInsertHelper.insertItem(ItemTypes.SKATES.toString(), new BigDecimal("85.99"));

          // Initialize Inventory
          DatabaseInsertHelper.insertInventory(fishing_rod, 0);
          DatabaseInsertHelper.insertInventory(hockey_stick, 0);
          DatabaseInsertHelper.insertInventory(protein_bar, 0);
          DatabaseInsertHelper.insertInventory(shoes, 0);
          DatabaseInsertHelper.insertInventory(skates, 0);

        } catch (DatabaseInsertException e) {
        } catch (SQLException e) {
        } catch (InvalidItemNameException e) {
        } catch (InvalidPriceException e) {
        } catch (InvalidItemIdException e) {
        } catch (InvalidQuantityException e) {
        }

        // Prompt for Admin account info
        System.out.println("Create New Admin Account");
        System.out.println("-------------------------");
        System.out.print("Name: ");
        input = buffer.readLine();
        String name = input;
        System.out.print("Age: ");
        input = buffer.readLine();
        int age = Integer.parseInt(input);
        System.out.print("Address: ");
        input = buffer.readLine();
        String address = input;
        System.out.print("Password: ");
        input = buffer.readLine();
        String password = input;

        boolean inserted = false;

        // Attempt to create and add admin to database
        while (!inserted) {
          try {
            int adminId = DatabaseInsertHelper.insertNewUser(name, age, address, password);
            inserted = true;
            int adminRoleId = DatabaseInsertHelper.insertRole(Roles.ADMIN.toString());

            DatabaseInsertHelper.insertUserRole(adminId, adminRoleId);
          } catch (InvalidNameException e) {
            // Re-prompt for Name
            System.out.print("Invalid Name! Try again.\nName: ");
            input = buffer.readLine();
            name = input;
          } catch (DatabaseInsertException e) {
            // Error Accessing Database
            System.out.print("Problem adding to database. Try Again");
          } catch (SQLException e) {
            // Error Accessing Database
            System.out.print("Problem accessing to database. Try Again");
          } catch (InvalidAddressException e) {
            // Re-prompt for Address
            System.out.print("Invalid Address! Try again. \nAddress: ");
            input = buffer.readLine();
            address = input;
          } catch (InvalidAgeException e) {
            // Re-prompt for Age
            System.out.print("Invalid Age! Try again. \nAge: ");
            input = buffer.readLine();
            age = Integer.parseInt(input);
          } catch (InvalidPasswordException e) {
            // Re-prompt for Password
            System.out.print("Invalid Password! Try again. \nPassword: ");
            input = buffer.readLine();
            password = input;
          } catch (InvalidRoleException e) {
            System.out.println("Error adding admin role.");
          } catch (InvalidUserIdException e) {
            System.out.println("Error creating user id.");
          } catch (InvalidRoleIdException e) {
            System.out.println("Error creating admin id.");
          }
        }

        // Prompt user for employee account info
        System.out.println("\nCreate New Employee Account:");
        System.out.println("-------------------------");
        System.out.print("Name: ");
        input = buffer.readLine();
        name = input;
        System.out.print("Age: ");
        input = buffer.readLine();
        age = Integer.parseInt(input);
        System.out.print("Address: ");
        input = buffer.readLine();
        address = input;
        System.out.print("Password: ");
        input = buffer.readLine();
        password = input;

        inserted = false;
        // Attempt to create and add new employee to database
        while (!inserted) {
          try {
            int employeeId = DatabaseInsertHelper.insertNewUser(name, age, address, password);
            inserted = true;
            int employeeRoleId = DatabaseInsertHelper.insertRole(Roles.EMPLOYEE.toString());
            DatabaseInsertHelper.insertUserRole(employeeId, employeeRoleId);
          } catch (InvalidNameException e) {
            // Re-prompt for Name
            System.out.print("Invalid Name! Try again.\nName: ");
            input = buffer.readLine();
            name = input;
          } catch (DatabaseInsertException e) {
            // Error Accessing Database
            System.out.print("Problem adding to database. Try Again");
          } catch (SQLException e) {
            // Error Accessing Database
            System.out.print("Problem accessing to database. Try Again");
          } catch (InvalidAddressException e) {
            // Re-prompt for Address
            System.out.print("Invalid Address! Try again. \nAddress: ");
            input = buffer.readLine();
            address = input;
          } catch (InvalidAgeException e) {
            // Re-prompt for Age
            System.out.print("Invalid Age! Try again. \nAge: ");
            input = buffer.readLine();
            age = Integer.parseInt(input);
          } catch (InvalidPasswordException e) {
            // Re-prompt for Password
            System.out.print("Invalid Password! Try again. \nPassword: ");
            input = buffer.readLine();
            password = input;
          } catch (InvalidRoleException e) {
            System.out.println("Error adding admin role.");
          } catch (InvalidUserIdException e) {
            System.out.println("Error creating user id.");
          } catch (InvalidRoleIdException e) {
            System.out.println("Error creating admin id.");
          }
        }

        /*
         * ADMIN MODE Admin can either promote an employee or view books
         */
      } else if (argv.length != 0 && argv[0].equals("1")) {

        // Prompt for Admin info
        System.out.println("Admin Login");
        System.out.println("-------------------------");
        int adminId = 0;
        boolean validId = false;
        while (!validId) {
          try {
            System.out.print("ID: ");
            input = buffer.readLine();

            adminId = Integer.parseInt(input);
            int roleId = 0;

            if (ValidateUserRole.isValidUserId(adminId)) {
              roleId = DatabaseSelectHelper.getUserRoleId(adminId);
              if (DatabaseSelectHelper.getRoleName(roleId).equals(Roles.ADMIN.toString())) {
                validId = true;
              } else {
                System.out.println("User with ID" + adminId + " isn't an admin! Try Again");
              }
            } else {
              System.out.println("ID doesn't exist, Try Again");
            }
          } catch (NumberFormatException e) {
            System.out.println("Invalid ID format, Enter an integer");
            System.out.print("ID: ");
            input = buffer.readLine();
          } catch (SQLException e) {
            System.out.println("ID doesn't exist, Try Again");
            System.out.print("ID: ");
            input = buffer.readLine();
          }
        }

        Admin admin = null;
        try {
          admin = (Admin) DatabaseSelectHelper.getUserDetails(adminId);

        } catch (SQLException e) {
          System.out.print("Problem accessing to database. Try Again");
        }

        System.out.println("\nHi " + admin.getName() + "! Please Enter your password");
        System.out.print("Password: ");
        String password = buffer.readLine();

        // Context menu for ADMIN MODE
        while (true) {
          String adminChoice = "";
          System.out.println(
              "\n1 - Promote Employee\n2 - View Books\n3 - Save Database\n4 - Load Database\n0 - Exit");
          System.out.print("Enter Selection: ");

          adminChoice = buffer.readLine();

          switch (adminChoice) {
            case "1":
              try {
                if (admin.authenticate(password)) {

                  // Prompt Admin for employee info to promote
                  System.out.println("\nPromote Employee:");
                  System.out.println("-------------------------");
                  System.out.print("Employee user ID: ");
                  input = buffer.readLine();
                  int employeeId = Integer.parseInt(input);

                  int employeeRoleId = DatabaseSelectHelper.getUserRoleId(employeeId);
                  if (DatabaseSelectHelper.getRoleName(employeeRoleId)
                      .equals(Roles.EMPLOYEE.toString())) {
                    Employee employee = (Employee) DatabaseSelectHelper.getUserDetails(employeeId);

                    try {
                      admin.promoteEmployee(employee);
                      System.out.println("Successfully promoted " + employee.getName()
                          + "(Employee ID :" + employee.getId() + ")");
                    } catch (InvalidRoleException e) {
                      System.out.println("Invalid role");
                    } catch (InvalidRoleIdException e) {
                      System.out.println("Invalid role id");
                    } catch (InvalidUserIdException e) {
                      System.out.println("Invalid User ID");
                    }
                  } else {
                    System.out.println("User with ID " + employeeId + " is not an employee!");
                  }

                }
              } catch (SQLException e) {
                System.out.println(
                    "Problem accessing to database. Entered ID does not exist. Terminating session.");
              }
              break;

            case "2":
              System.out.println("\nView Books");
              System.out.println("-------------------------");
              try {
                admin.viewBooks();
              } catch (SQLException e) {
                System.out.println("Error Accessing Database");
              }
              break;

            case "3":
              System.out.println("\nSave Database");
              System.out.println("-------------------------");
              admin.saveDatabase();
              break;

            case "4":
              System.out.println("\nLoad Database");
              System.out.println("-------------------------");
              try {
                connection.close();
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
              connection = admin.loadDatabase();
              if (connection != null) {
                System.out.println("Successfully loaded Database stored in database_copy.ser");
              } else {
                System.out.println("Error loading saved Database, Terminating session.");
                // connection.close();
                System.exit(0);
              }
              break;

            case "0":
              // ----------------DELETE THIS----------------------------
              // admin.saveDatabase();
              // connection = admin.loadDatabase(connection);
              /*
               * ArrayList<Object> database = admin.storeDatabase();
               * DatabaseSerializer.serialize(database);
               * 
               * ArrayList<Object> test = null; try { connection.close(); connection =
               * DatabaseDriverExtender.reinitialize(); test = DatabaseDeserializer.deserialize(); }
               * catch (DatabaseInsertException e) { // TODO Auto-generated catch block
               * e.printStackTrace(); } catch (SQLException e) { // TODO Auto-generated catch block
               * e.printStackTrace(); }
               * 
               * /* System.out.println("\nDeserialized: Role Table"); ArrayList<String> roles =
               * (ArrayList<String>) test.get(0); for (String ay : roles) { System.out.println(ay);
               * }
               * 
               * ArrayList<User> users = (ArrayList<User>) test.get(1);
               * System.out.println("\nDeserialized: User Table"); for (User x : users) {
               * System.out.println("User ID: " + x.getId() + " Name: " + x.getName() + " Role ID: "
               * + x.getRoleId()); }
               * 
               * HashMap<Integer, String> passes = (HashMap<Integer, String>) test.get(2);
               * System.out.println("\nDeserialized: UserPass Table"); for (int p : passes.keySet())
               * { System.out.println("User ID: " + p + " Pass: " + passes.get(p)); }
               * 
               * HashMap<Integer, Integer> userRoles = (HashMap<Integer, Integer>) test.get(3); for
               * (Integer q : userRoles.keySet()) { System.out.println("User ID: " + q +
               * " Role ID: " + userRoles.get(q)); }
               * 
               * ArrayList<Item> items1 = (ArrayList<Item>) test.get(4);
               * System.out.println("\nDeserialized: Item Table"); for (Item i : items1) {
               * System.out.println( "Item ID: " + i.getId() + " Name: " + i.getName() + " Price: "
               * + i.getPrice()); }
               * 
               * HashMap<Integer, SaleImpl> salesTable = (HashMap<Integer, SaleImpl>) test.get(5);
               * System.out.println("\nDeserialized: Sale Table"); for (Integer s :
               * salesTable.keySet()) { System.out .println("Sale ID: " + s + " User ID: " +
               * salesTable.get(s).getUser().getId() + " Total Price: " +
               * salesTable.get(s).getTotalPrice()); }
               * 
               * HashMap<Integer, ItemizedSaleImpl> itemized = (HashMap<Integer, ItemizedSaleImpl>)
               * test.get(6); System.out.println("\nTest: Itemized Sale Table"); for (Integer y :
               * itemized.keySet()) { for (Item j : itemized.get(y).getItemMap().keySet()) {
               * System.out.println("Sale ID: " + y + " Item: " + j.getName() + "(" + j.getId() +
               * ")" + " Quantity: " + itemized.get(y).getItemMap().get(j)); } }
               * 
               * HashMap<Item, Integer> invent = (HashMap<Item, Integer>) test.get(7);
               * System.out.println("\nDeserialized: Inventory Table"); for (Item z :
               * invent.keySet()) { System.out.println("Item ID: " + z.getId() + " Quantity: " +
               * invent.get(z)); }
               * 
               * HashMap<Integer, Integer> account = (HashMap<Integer, Integer>) test.get(8);
               * List<Integer> activeIds = (ArrayList<Integer>) test.get(9); List<Integer>
               * inactiveIds = (ArrayList<Integer>) test.get(10);
               * 
               * System.out.println("\nDesrialized: Account Table"); for (int t : account.keySet())
               * { int stat = 0; if (activeIds.contains(t)) { stat = 1; } else if
               * (inactiveIds.add(t)) { stat = 0; } System.out.println("Account ID: " + t +
               * " User ID: " + account.get(t) + " Active: " + stat); } HashMap<Integer,
               * HashMap<Integer, Integer>> accountSum = (HashMap<Integer, HashMap<Integer,
               * Integer>>) test.get(11);
               * System.out.println("\nDeserialized: Account Summary Table"); for (int a :
               * accountSum.keySet()) { for (int d : accountSum.get(a).keySet()) {
               * System.out.println("Account ID: " + a + " Item ID: " + d + " Quantity: " +
               * accountSum.get(a).get(d)); } }
               */
              // -------------------DELETE THIS----------------------------
              try {
                connection.close();
              } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
              System.exit(0);
          }
        }

        /*
         * DEFAULT MODE Either Employee or Customer can log in
         */
      } else if (argv.length == 0 || !argv[0].equals("-1") && !argv[0].equals("1")) {

        boolean loop = true;

        while (loop) {
          // ------------------DELETE AFTER--------------------------------
          /*
           * try { List<Integer> roleIds = DatabaseSelectHelper.getRoleIds(); List<String> roles =
           * new ArrayList<String>(); for (int c : roleIds) {
           * roles.add(DatabaseSelectHelper.getRoleName(c)); }
           * 
           * List<User> users = DatabaseSelectHelper.getUsersDetails(); HashMap<Integer, String>
           * pass = new HashMap<Integer, String>(); List<Item> items =
           * DatabaseSelectHelper.getAllItems(); HashMap<Integer, SaleImpl> sales =
           * DatabaseSelectHelper.getSales().getSalesMap(); HashMap<Integer, ItemizedSaleImpl>
           * itemizedSales = DatabaseSelectHelper.getItemizedSales().getItemizedSalesMap();
           * HashMap<Item, Integer> inventory = DatabaseSelectHelper.getInventory().getItemMap();
           * HashMap<Integer, Integer> userRoles = new HashMap<Integer, Integer>(); HashMap<Integer,
           * Integer> accounts = new HashMap<Integer, Integer>(); HashMap<Integer, HashMap<Integer,
           * Integer>> accountDets = new HashMap<Integer, HashMap<Integer, Integer>>();
           * 
           * List<Integer> active = new ArrayList<Integer>(); List<Integer> inactive = new
           * ArrayList<Integer>();
           * 
           * 
           * System.out.println("Test: Roles Table"); for (String r : roles) {
           * System.out.println("Role: " + r); }
           * 
           * System.out.println("\nTest: User Table"); for (User x : users) { System.out.println(
           * "User ID: " + x.getId() + " Name: " + x.getName() + " Role ID: " + x.getRoleId());
           * userRoles.put(x.getId(), x.getRoleId()); pass.put(x.getId(),
           * DatabaseSelectHelper.getPassword(x.getId())); for (int h :
           * DatabaseSelectHelper.getUserAccounts(x.getId())) { accounts.put(h, x.getId());
           * accountDets.put(h, DatabaseSelectHelper.getAccountDetails(h)); }
           * 
           * for (int u : DatabaseSelectHelper.getUserActiveAccount(x.getId())) { active.add(u); }
           * 
           * for (int v : DatabaseSelectHelper.getUserInactiveAccount(x.getId())) { inactive.add(v);
           * } }
           * 
           * System.out.println("\nTest: UserPass Table"); for (int p : pass.keySet()) {
           * System.out.println("User ID: " + p + " Pass: " + pass.get(p)); }
           * 
           * System.out.println("\nTest: User roles Table"); for (Integer q : userRoles.keySet()) {
           * System.out.println("User ID: " + q + " Role ID: " + userRoles.get(q)); }
           * 
           * System.out.println("\nTest: Item Table"); for (Item i : items) { System.out.println(
           * "Item ID: " + i.getId() + " Name: " + i.getName() + " Price: " + i.getPrice()); }
           * 
           * System.out.println("\nTest: Sale Table"); for (Integer s : sales.keySet()) {
           * System.out.println("Sale ID: " + s + " User ID: " + sales.get(s).getUser().getId() +
           * " Total Price: " + sales.get(s).getTotalPrice()); }
           * 
           * System.out.println("\nTest: Itemized Sale Table"); for (Integer y :
           * itemizedSales.keySet()) { for (Item j : itemizedSales.get(y).getItemMap().keySet()) {
           * System.out.println("Sale ID: " + y + " Item: " + j.getName() + "(" + j.getId() + ")" +
           * " Quantity: " + itemizedSales.get(y).getItemMap().get(j)); } }
           * 
           * System.out.println("\nTest: Inventory Table"); for (Item z : inventory.keySet()) {
           * System.out.println("Item ID: " + z.getId() + " Quantity: " + inventory.get(z)); }
           * 
           * System.out.println("\nTest: Account Table"); for (int t : accounts.keySet()) { int stat
           * = 0; if (active.contains(t)) { stat = 1; } else if (inactive.add(t)) { stat = 0; }
           * System.out.println("Account ID: " + t + " User ID: " + accounts.get(t) + " Active: " +
           * stat); }
           * 
           * System.out.println("\nTest: Account Summary Table"); for (int a : accountDets.keySet())
           * { for (int d : accountDets.get(a).keySet()) { System.out.println("Account ID: " + a +
           * " Item ID: " + d + " Quantity: " + accountDets.get(a).get(d)); } }
           * 
           * } catch (SQLException e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
           */
          // --------------------------------DELETE--------------------------------------------

          // Print Login Option menu
          System.out.println("\n1 - Employee Login\n2 - Customer Login\n0 - Exit");
          System.out.print("Enter Selection: ");

          input = buffer.readLine();
          String choice = input;

          switch (choice) {
            case "0":
              try {
                connection.close();
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
              System.exit(0);

            case "1":
              // Prompt for Employee login
              System.out.println("\nEmployee Login");
              System.out.println("-------------------------");
              boolean validInput = false;
              while (!validInput) {
                System.out.print("\nEmployee Id: ");
                input = buffer.readLine();

                try {
                  int id = Integer.parseInt(input);
                  boolean validId = ValidateUserRole.isValidUserId(id);
                  if (!validId) {
                    System.out.println("ID does not exist Terminating Session");
                    validInput = false;
                  } else if (DatabaseSelectHelper
                      .getRoleName(DatabaseSelectHelper.getUserRoleId(id))
                      .equals(Roles.EMPLOYEE.toString())) {
                    Employee employee = (Employee) DatabaseSelectHelper.getUserDetails(id);

                    System.out
                        .println("\nHi " + employee.getName() + "! Please enter your password");
                    System.out.print("Password: ");

                    String employeePass = buffer.readLine();
                    if (employee.authenticate(employeePass)) {
                      validInput = true;
                      Inventory inventory = DatabaseSelectHelper.getInventory();
                      EmployeeInterface employeeInterface =
                          new EmployeeInterface(employee, inventory);

                      String employeeChoice = "";
                      while (!employeeChoice.equals("6")) {

                        System.out.println(
                            "\nWelcome " + employeeInterface.getCurrentEmployee().getName() + "!");

                        // Display context menu for Employee
                        System.out.println("\n1.Authenticate new employee");
                        System.out.println("2.Make new User");
                        System.out.println("3.Make new account");
                        System.out.println("4.Make new Employee");
                        System.out.println("5.Restock Inventory");
                        System.out.println("6.Exit");

                        System.out.print("Enter Selection: ");
                        employeeChoice = buffer.readLine();

                        switch (employeeChoice) {
                          case "1":
                            System.out.println("\nAuthenicate new employee");
                            System.out.println("-------------------------");

                            System.out.print("Name: ");
                            input = buffer.readLine();
                            String name = input;

                            System.out.print("Age: ");
                            input = buffer.readLine();
                            int age = Integer.parseInt(input);

                            System.out.print("Address: ");
                            input = buffer.readLine();
                            String address = input;

                            System.out.print("Password: ");
                            input = buffer.readLine();
                            String password = input;

                            boolean validEmployeeInfo = false;
                            int newEmployeeId = 0;
                            try {
                              while (!validEmployeeInfo) {
                                newEmployeeId =
                                    employeeInterface.createEmployee(name, age, address, password);
                                validEmployeeInfo = true;
                              }

                            } catch (InvalidNameException e) {
                              System.out.print("Invalid Name! Try again.\nName: ");
                              input = buffer.readLine();
                              name = input;
                              validEmployeeInfo = false;
                            } catch (DatabaseInsertException e) {
                              System.out.print("Problem adding to database. Try Again");
                            } catch (SQLException e) {
                              System.out.print("Problem accessing to database. Try Again");
                            } catch (InvalidAddressException e) {
                              System.out.print("Invalid Address! Try again. \nAddress: ");
                              input = buffer.readLine();
                              address = input;
                              validEmployeeInfo = false;
                            } catch (InvalidAgeException e) {
                              System.out.print("Invalid Age! Try again. \nAge: ");
                              input = buffer.readLine();
                              age = Integer.parseInt(input);
                              validEmployeeInfo = false;
                            } catch (InvalidPasswordException e) {
                              System.out.print("Invalid Password! Try again. \nPassword: ");
                              input = buffer.readLine();
                              password = input;
                              validEmployeeInfo = false;
                            } catch (InvalidRoleException e) {
                              System.out.println("Error adding employee role.");
                            } catch (InvalidUserIdException e) {
                              System.out.println("Error creating user id.");
                            } catch (InvalidRoleIdException e) {
                              System.out.println("Error finding employee id.");
                            }

                            System.out.println("\nNew Employee Login:");
                            Employee newEmployee =
                                (Employee) DatabaseSelectHelper.getUserDetails(newEmployeeId);
                            System.out.println("Employee ID: " + newEmployeeId);
                            System.out.print("Password: ");
                            String pass = buffer.readLine();

                            // Might change this, to only check password once
                            while (!newEmployee.authenticate(pass)) {
                              System.out.println("Invalid Password! Try again");
                              System.out.print("Password: ");
                              pass = buffer.readLine();
                            }

                            employeeInterface.setCurrentEmployee(newEmployee);
                            System.out.println("\nLogged in as Employee " + newEmployee.getId()
                                + ": " + newEmployee.getName());

                            break;
                          case "2":
                            // Prompt for new Customer info
                            System.out.println("\nMake New User");
                            System.out.println("-------------------------");

                            System.out.print("Name: ");
                            input = buffer.readLine();
                            name = input;

                            System.out.print("Age: ");
                            input = buffer.readLine();
                            age = Integer.parseInt(input);

                            System.out.print("Address: ");
                            input = buffer.readLine();
                            address = input;

                            System.out.print("Password: ");
                            input = buffer.readLine();
                            password = input;

                            int customerId = 0;
                            try {
                              customerId =
                                  employeeInterface.createCustomer(name, age, address, password);
                            } catch (InvalidNameException e) {
                              System.out.print("Invalid Name! Try again.\nName: ");
                              input = buffer.readLine();
                              name = input;
                              validEmployeeInfo = false;
                            } catch (DatabaseInsertException e) {
                              System.out.print("Problem adding to database. Try Again");
                            } catch (SQLException e) {
                              System.out.print("Problem accessing to database. Try Again");
                            } catch (InvalidAddressException e) {
                              System.out.print("Invalid Address! Try again. \nAddress: ");
                              input = buffer.readLine();
                              address = input;
                              validEmployeeInfo = false;
                            } catch (InvalidAgeException e) {
                              System.out.print("Invalid Age! Try again. \nAge: ");
                              input = buffer.readLine();
                              age = Integer.parseInt(input);
                              validEmployeeInfo = false;
                            } catch (InvalidPasswordException e) {
                              System.out.print("Invalid Password! Try again. \nPassword: ");
                              input = buffer.readLine();
                              password = input;
                              validEmployeeInfo = false;
                            } catch (InvalidRoleException e) {
                              System.out.println("Error adding employee role.");
                            } catch (InvalidUserIdException e) {
                              System.out.println("Error creating user id.");
                            } catch (InvalidRoleIdException e) {
                              System.out.println("Error finding customer id.");
                            }

                            System.out.println("User " + customerId + " successfully created");

                            break;
                          case "3":
                            System.out.println("\nMake New Account:");
                            System.out.print("Enter Customer ID: ");
                            customerId = Integer.parseInt(buffer.readLine());
                            try {
                              // Check if entered ID is a valid customer ID
                              if (DatabaseSelectHelper
                                  .getUserRoleId(customerId) == DatabaseInsertHelper
                                      .getRoleId(Roles.CUSTOMER.toString())) {
                                DatabaseInsertHelper.insertAccount(customerId, true);
                                System.out.println("Successfully created account for "
                                    + DatabaseSelectHelper.getUserDetails(customerId).getName());
                              }
                            } catch (SQLException e) {
                              System.out.println("ID doesn't exist!");
                            } catch (InvalidUserIdException e) {
                              System.out.println("Invalid ID. Try Again");
                            } catch (DatabaseInsertException e) {
                              System.out.println("Error Inserting to Database");
                            }

                            break;
                          case "4":
                            // Prompt for new Employee info
                            System.out.println("\nMake New Employee");
                            System.out.println("-------------------------");

                            System.out.print("Name: ");
                            input = buffer.readLine();
                            name = input;

                            System.out.print("Age: ");
                            input = buffer.readLine();
                            age = Integer.parseInt(input);

                            System.out.print("Address: ");
                            input = buffer.readLine();
                            address = input;

                            System.out.print("Password: ");
                            input = buffer.readLine();
                            password = input;
                            validEmployeeInfo = false;
                            newEmployeeId = 0;
                            try {
                              while (!validEmployeeInfo) {
                                newEmployeeId =
                                    employeeInterface.createEmployee(name, age, address, password);
                                validEmployeeInfo = true;
                              }

                            } catch (InvalidNameException e) {
                              System.out.print("Invalid Name! Try again.\nName: ");
                              input = buffer.readLine();
                              name = input;
                              validEmployeeInfo = false;
                            } catch (DatabaseInsertException e) {
                              System.out.print("Problem adding to database. Try Again");
                            } catch (SQLException e) {
                              System.out.print("Problem accessing to database. Try Again");
                            } catch (InvalidAddressException e) {
                              System.out.print("Invalid Address! Try again. \nAddress: ");
                              input = buffer.readLine();
                              address = input;
                              validEmployeeInfo = false;
                            } catch (InvalidAgeException e) {
                              System.out.print("Invalid Age! Try again. \nAge: ");
                              input = buffer.readLine();
                              age = Integer.parseInt(input);
                              validEmployeeInfo = false;
                            } catch (InvalidPasswordException e) {
                              System.out.print("Invalid Password! Try again. \nPassword: ");
                              input = buffer.readLine();
                              password = input;
                              validEmployeeInfo = false;
                            } catch (InvalidRoleException e) {
                              System.out.println("Error adding employee role.");
                            } catch (InvalidUserIdException e) {
                              System.out.println("Error creating user id.");
                            } catch (InvalidRoleIdException e) {
                              System.out.println("Error finding employee id.");
                            }
                            break;

                          case "5":
                            System.out.println("\nRestock Inventory");
                            System.out.println("-------------------------");

                            List<Item> items = DatabaseSelectHelper.getAllItems();

                            // Output current store inventory
                            System.out.println("\nCurrent Store Inventory");
                            for (Item iterator : items) {
                              System.out
                                  .println(iterator.getId() + " - " + iterator.getName() + ": "
                                      + DatabaseSelectHelper.getInventoryQuantity(iterator.getId())
                                      + " units");
                            }
                            // Prompt for restock info
                            System.out.print("\nEnter item ID: ");
                            input = buffer.readLine();

                            int itemId = Integer.parseInt(input);
                            System.out.print("Enter restock amount: ");
                            input = buffer.readLine();
                            int quantity = Integer.parseInt(input);

                            Item item = DatabaseSelectHelper.getItem(itemId);
                            if (item != null && quantity > 0) {
                              employeeInterface.restockInventory(item, quantity);
                              System.out.println("Successfully restocked " + item.getName());
                            } else {
                              System.out.println("Invalid Item ID or quantity!");
                            }
                            break;
                        }
                      }
                      // 6 is entered, exit
                      break;
                    } else {
                      System.out.println("Incorrect Password. Terminating session");
                      // System.exit(0);
                      validInput = false;
                    }
                  } else {
                    System.out.println("Employee ID doesn't exist, Terminating session");
                    // System.exit(0);
                    validInput = false;
                  }
                } catch (NumberFormatException e) {
                  // TODO Auto-generated catch block
                  System.out.print("Invalid format, Please enter a valid integer id.\n");
                  validInput = false;

                } catch (SQLException e) {
                  // TODO Auto-generated catch block
                  System.out.print("Error Accessing Database. Terminating session");
                  System.exit(0);
                }
                break;
              }
              break;
            case "2":
              // Prompt Customer Login
              System.out.println("\nCustomer Login");
              System.out.println("-------------------------");

              validInput = false;
              while (!validInput) {
                System.out.print("Customer Id: ");
                input = buffer.readLine();

                try {
                  int id = Integer.parseInt(input);
                  if (DatabaseSelectHelper.getRoleName(DatabaseSelectHelper.getUserRoleId(id))
                      .equals(Roles.CUSTOMER.toString())) {
                    Customer customer = (Customer) DatabaseSelectHelper.getUserDetails(id);

                    System.out
                        .println("\nHi " + customer.getName() + "! Please enter your password");
                    System.out.print("Password: ");
                    String customerPassword = buffer.readLine();

                    if (customer.authenticate(customerPassword)) {
                      validInput = true;
                      ShoppingCart customerCart = new ShoppingCart(customer);
                      System.out.println("\nWelcome " + customerCart.getCustomer().getName() + "!");

                      boolean restoredCart = false;
                      int cartId = 0;

                      // If customer has an account, ask if they want to restore their cart
                      List<Integer> customerAccounts =
                          DatabaseSelectHelper.getUserAccounts(customerCart.getCustomer().getId());
                      if (!customerAccounts.isEmpty()) {
                        System.out.print(
                            "You have previous shopping cart(s) saved. Would you like to restore your cart?(Y\\N): ");
                        if (buffer.readLine().equalsIgnoreCase("Y")) {
                          System.out.println("Your current saved cart account IDs are: ");
                          for (Integer accountId : customerAccounts) {
                            System.out.println(accountId);
                          }
                          boolean validCart = false;


                          while (!validCart) {
                            System.out.print("Enter Account ID: ");
                            try {
                              cartId = Integer.parseInt(buffer.readLine());
                              customerCart.restoreCart(cartId);
                              validCart = true;
                              restoredCart = true;
                            } catch (NumberFormatException e) {
                              System.out.println("Invalid format! Enter an integer!!");
                              validCart = false;
                            } catch (SQLException e) {
                              System.out.println("Invalid account ID! Try Again");
                              validCart = false;
                            }
                          }
                        }
                      }

                      String customerChoice = "";

                      while (!customerChoice.equals("6")) {

                        System.out.println("\n1. List current items in cart");
                        System.out.println("2. Add a quantity of an item to the cart");
                        System.out.println("3. Check total price of items in the cart");
                        System.out.println("4. Remove a quantity of an item from the cart");
                        System.out.println("5. check out");
                        System.out.println("6. Exit");

                        System.out.print("\nEnter Selection: ");

                        customerChoice = buffer.readLine();

                        switch (customerChoice) {
                          case "1":
                            // Output cart's contents
                            System.out.println("\n" + customer.getName() + "'s Shopping Cart:");

                            for (Item item : customerCart.getItems()) {
                              System.out.println(
                                  item.getName() + "  \t: " + customerCart.getItemQuantity(item)
                                      + " units @$" + item.getPrice() + "/unit");
                            }

                            if (customerCart.getItems().size() == 0) {
                              System.out.println("Cart is empty!");
                            }
                            break;

                          case "2":
                            List<Item> items = DatabaseSelectHelper.getAllItems();

                            System.out.println("Add a quantity of an item to the cart");
                            System.out.println("-------------------------");

                            // Output store's current inventory
                            System.out.println("\nCurrent Store Inventory");
                            for (Item iterator : items) {
                              System.out
                                  .println(iterator.getId() + "-" + iterator.getName() + "\t: "
                                      + DatabaseSelectHelper.getInventoryQuantity(iterator.getId())
                                      + " units available \t$" + iterator.getPrice() + "/unit");
                            }

                            // Prompt for item and quantity
                            System.out.print("\nEnter item ID: ");
                            input = buffer.readLine();

                            int itemId = Integer.parseInt(input);
                            Item item = DatabaseSelectHelper.getItem(itemId);

                            System.out.print("Enter quantity: ");
                            input = buffer.readLine();

                            int quantity = Integer.parseInt(input);

                            if (item != null && quantity > 0) {
                              customerCart.addItem(item, quantity);
                              System.out.println(
                                  "Successfully added " + quantity + " units of " + item.getName()
                                      + " to " + customerCart.getCustomer().getName() + "'s cart");
                            } else {
                              System.out.println("Invalid Item ID or quantity!");
                            }
                            break;

                          case "3":
                            System.out.println("Check total cart price");
                            System.out.println("-------------------------");

                            BigDecimal subtotal = customerCart.getTotal();
                            BigDecimal taxRate = customerCart.getTaxRate();

                            // Output item prices
                            for (Item itemIter : customerCart.getItems()) {
                              int itemQuantity = customerCart.getItemQuantity(itemIter);
                              BigDecimal pricePerUnit =
                                  DatabaseSelectHelper.getItem(itemIter.getId()).getPrice();

                              BigDecimal price =
                                  pricePerUnit.multiply(new BigDecimal(itemQuantity));

                              System.out.println(itemIter.getName() + " x" + itemQuantity + "\t: "
                                  + "$" + price.setScale(2, BigDecimal.ROUND_HALF_UP));
                            }

                            // Output Subtotal and Total price
                            System.out.println("Subtotal: $" + subtotal + "\tTotal: $"
                                + subtotal.multiply(taxRate).setScale(2, BigDecimal.ROUND_HALF_UP));
                            break;

                          case "4":
                            System.out.println("Remove a quantity from cart");
                            System.out.println("-------------------------");
                            System.out.println("\n" + customer.getName() + "'s Shopping Cart:");

                            // Output cart's current contents
                            for (Item itemIter : customerCart.getItems()) {
                              System.out.println(itemIter.getId() + "-" + itemIter.getName()
                                  + " \t: " + customerCart.getItemQuantity(itemIter) + " units @$"
                                  + itemIter.getPrice() + "/unit");
                            }

                            // Prompt for item and quantity to remove
                            System.out.print("\nEnter item ID: ");
                            input = buffer.readLine();

                            itemId = Integer.parseInt(input);
                            item = DatabaseSelectHelper.getItem(itemId);

                            System.out.print("Enter quantity: ");
                            input = buffer.readLine();

                            quantity = Integer.parseInt(input);

                            if (item != null && quantity > 0
                                && quantity <= customerCart.getItemQuantity(item)
                                && customerCart.containsItem(item)) {
                              customerCart.removeItem(item, quantity);
                              System.out.println("Successfully removed " + quantity + " units of "
                                  + item.getName() + " from " + customerCart.getCustomer().getName()
                                  + "'s cart");
                            } else {
                              System.out.println("Invalid Item ID or quantity!");
                            }

                            break;
                          case "5":
                            if (!restoredCart) {
                              if (customerCart.checkOut()) {
                                System.out.println("Successfully Checked Out "
                                    + customerCart.getCustomer().getName() + "'s cart");
                              }
                            } else {
                              if (customerCart.checkOut()) {
                                System.out.println("Successfully Checked Out "
                                    + customerCart.getCustomer().getName() + "'s cart");
                              }
                              DatabaseUpdateHelper.updateAccountStatus(cartId, false);
                            }
                            break;
                        }
                      }
                      // 6 is entered
                      // Ask if customer would like to save cart before exiting
                      if (!restoredCart) {
                        System.out.print("Would you like to save your cart? (Y/N): ");
                        if (buffer.readLine().equalsIgnoreCase("Y")) {
                          if (ShoppingCart.hasEmptyCarts(customerCart.getCustomer().getId())) {
                            System.out.println("Your current available cart account IDs are: ");
                            for (Integer accountId : ShoppingCart
                                .getEmptyAccounts(customerCart.getCustomer().getId())) {
                              System.out.println(accountId);
                            }

                            boolean validCart = false;
                            int accountId = 0;
                            while (!validCart) {
                              System.out.print("Enter Account ID: ");
                              try {
                                accountId = Integer.parseInt(buffer.readLine());
                              } catch (NumberFormatException e) {
                                System.out.println("Invalid format! Enter an integer!!");
                                validCart = false;
                              }

                              if (!ShoppingCart.getEmptyAccounts(customerCart.getCustomer().getId())
                                  .contains(accountId)) {
                                System.out.print("Invalid Account ID! Please Try Again!");
                                validCart = false;
                              } else {
                                try {
                                  customerCart.saveCart(accountId);
                                  System.out.println(
                                      "Successfully saved " + customerCart.getCustomer().getName()
                                          + "'s cart to Account " + accountId);
                                } catch (InvalidItemIdException e) {
                                  System.out.println("Invalid ID! Saving cart was unsuccesful");
                                } catch (DatabaseInsertException e) {
                                  System.out.println(
                                      "Error Accessing Database. Saving cart was unsuccesful");
                                } catch (InvalidQuantityException e) {
                                  System.out
                                      .println("Invalid Quantity! Saving cart was unsuccesful");
                                }
                                break;
                              }
                            }
                          } else {
                            System.out.println(
                                "You don't have any accounts available to save this cart. Please contact an employee to create an account");
                            boolean validEmployeeId = false;
                            //int employeeId = 0;
                            while (!validEmployeeId) {
                              System.out.print("Employee ID: ");
                              try {
                                int employeeId = Integer.parseInt(buffer.readLine());
                                if (DatabaseSelectHelper
                                    .getRoleName(DatabaseSelectHelper.getUserRoleId(employeeId))
                                    .equals(Roles.EMPLOYEE.toString())) {
                                  validEmployeeId = true;
                                  System.out.print("Password: ");
                                  String employeePass = buffer.readLine();
                           
                                  Employee employee =
                                      (Employee) DatabaseSelectHelper.getUserDetails(employeeId);
                                  if (employee.authenticate(employeePass)) {
                                     try {
                                    // Check if entered ID is a valid customer ID
                                    if (DatabaseSelectHelper
                                        .getUserRoleId(customerCart.getCustomer().getId()) == DatabaseInsertHelper
                                            .getRoleId(Roles.CUSTOMER.toString())) {
                                      DatabaseInsertHelper.insertAccount(customerCart.getCustomer().getId(), true);
                                      System.out.println("Successfully created account for "
                                          + DatabaseSelectHelper.getUserDetails(customerCart.getCustomer().getId()).getName());
                                    } else {
                                      System.out.println("Invalid Password! Unable to create user account.");
                                    }
                                  } catch (SQLException e) {
                                    System.out.println("ID doesn't exist!");
                                  } catch (InvalidUserIdException e) {
                                    System.out.println("Invalid ID. Try Again");
                                  } catch (DatabaseInsertException e) {
                                    System.out.println("Error Inserting to Database");
                                  }
                                  }
                                 

                                }
                              } catch (NumberFormatException e) {
                                System.out
                                    .print("Invalid format, Please enter a valid integer id.\n");
                                validEmployeeId = false;
                              }

                            }



                            break;
                          }
                        }
                      }

                      break;
                    } else {
                      System.out.println("Invalid Password! Try again\n");
                      validInput = false;
                    }
                  } else {
                    System.out.println("Customer ID doesn't exist, Try Again\n");
                    validInput = false;
                  }
                } catch (NumberFormatException e) {
                  // TODO Auto-generated catch block
                  System.out.print("Invalid format, Please enter a valid integer id.\n");
                  validInput = false;

                } catch (SQLException e) {
                  // TODO Auto-generated catch block
                  System.out.println("Customer ID doesn't exist, Try Again\n");
                  validInput = false;
                }
                break;
              }
              break;
            default:
              loop = true;
          }
        }
      }
    } catch (ConnectionFailedException e) {
      System.out.println("Error creating Connection");
    } catch (IOException e) {
      System.out.println("Error reading input");
    } finally {
      try {
        connection.close();
      } catch (Exception e) {
        System.out.println("Looks like it was closed already :)");
      }
    }
  }
}