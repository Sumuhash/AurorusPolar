package src.com.b07.database.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import src.com.b07.database.DatabaseDriverAndroid;
import src.com.b07.database.DatabaseSelector;
import src.com.b07.enums.Roles;
import src.com.b07.inventory.Inventory;
import src.com.b07.inventory.InventoryImpl;
import src.com.b07.inventory.Item;
import src.com.b07.inventory.ItemImpl;
import src.com.b07.store.ItemizedSaleImpl;
import src.com.b07.store.Sale;
import src.com.b07.store.SaleImpl;
import src.com.b07.store.SalesLog;
import src.com.b07.store.SalesLogImpl;
import src.com.b07.users.Admin;
import src.com.b07.users.Customer;
import src.com.b07.users.Employee;
import src.com.b07.users.User;

/*
 * TODO: Complete the below methods to be able to get information out of the database. TODO: The
 * given code is there to aide you in building your methods. You don't have TODO: to keep the exact
 * code that is given (for example, DELETE the System.out.println()) TODO: and decide how to handle
 * the possible exceptions
 */

public class DatabaseSelectHelperAndroid extends DatabaseDriverAndroid{

    public DatabaseSelectHelperAndroid(Context context)
    {
        super(context);
    }
    /*
     * Begin Phase 2 upgrade DatabaseInsertSelecter functions
     */
    /**
     * Returns a list of the account IDs associated with userId
     *
     * @param userId
     * @return a list of account IDs
     * @throws SQLException
     */


    public List<Integer> getUserAccountsAndroid(int userId) throws SQLException {
        //Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
        //ResultSet results = DatabaseSelector.getUserAccounts(userId, connection);

        Cursor results = super.getUserAccounts(userId);
        List<Integer> userAccountIds = new ArrayList<Integer>();

        while (results.moveToNext()) {
            userAccountIds.add(results.getInt(results.getColumnIndex("ID")));
        }
        results.close();
        return userAccountIds;
    }

    /**
     * Returns a HashMap of the account details of accountId
     *
     * @param accountId
     * @return a HashMap of account details
     * @throws SQLException
     */
    public HashMap<Integer, Integer> getAccountDetailsAndroid(int accountId) throws SQLException {
        Cursor results = super.getAccountDetails(accountId);
        HashMap<Integer, Integer> accountSummary = new HashMap<Integer, Integer>();
        while (results.moveToNext()) {
            accountSummary.put(results.getInt(results.getInt(results.getColumnIndex("ITEMID"))), results.getInt(results.getColumnIndex("QUANTITY")));
        }
        results.close();
        return accountSummary;
    }

    /*
     * End of Phase 2 upgrade DatabaseInsertSelecter functions
     */

    public List<Integer> getRoleIdsAndroid() throws SQLException {
        //ResultSet results = DatabaseSelector.getRoles(connection);
        Cursor results = this.getRoles();

        List<Integer> ids = new ArrayList<>();
        while (results.moveToNext()) {
            ids.add(results.getInt(results.getColumnIndex("ID")));
        }
        results.close();
        return ids;
    }

    public String getRoleNameAndroid(int roleId) throws SQLException {
        String role = super.getRole(roleId);
        return role;
    }

    public int getUserRoleIdAndroid(int userId) throws SQLException {
        //Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
        int roleId = super.getUserRole(userId);
        return roleId;
    }

    public List<Integer> getUsersByRoleAndroid(int roleId) throws SQLException {
        //Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
        Cursor results = super.getUsersByRole(roleId);
        List<Integer> userIds = new ArrayList<>();
        while (results.moveToNext()) {
            userIds.add(results.getInt(results.getColumnIndex("USERID")));
        }

        results.close();
        return userIds;
    }

    public List<User> getUsersDetailsAndroid() throws SQLException {
        Cursor results = super.getUsersDetails();
        List<User> users = new ArrayList<>();
        while (results.moveToNext()) {
            int userId = results.getInt(results.getColumnIndex("ID"));
            int roleId = getUserRoleIdAndroid(userId);
            String roleName = getRoleNameAndroid(roleId);

            if (roleName.equals(Roles.ADMIN.toString())) {
                // Make new admin user
                User user = new Admin(userId, results.getString(results.getColumnIndex("NAME")), results.getInt(results.getColumnIndex("AGE")),
                        results.getString(results.getColumnIndex("ADDRESS")));
                user.setRoleId(roleId);
                users.add(user);

            } else if (roleName.equals(Roles.EMPLOYEE.toString())) {
                // Make new employee user
                Employee user = new Employee(userId, results.getString(results.getColumnIndex("NAME")), results.getInt(results.getColumnIndex("AGE")),
                        results.getString(results.getColumnIndex("ADDRESS")));
                user.setRoleId(roleId);
                users.add(user);

            } else if (roleName.equals(Roles.CUSTOMER.toString())) {
                // Make new customer user
                Customer user = new Customer(userId, results.getString(results.getColumnIndex("NAME")), results.getInt(results.getColumnIndex("AGE")),
                        results.getString(results.getColumnIndex("ADDRESS")));
                user.setRoleId(roleId);
                users.add(user);
            }
        }
        results.close();
        return users;
    }

    public User getUserDetailsAndroid(int userId) throws SQLException {
        //Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
        Cursor results = super.getUserDetails(userId);
        User user = null;

        while (results.moveToNext()) {
            int roleId = getUserRoleIdAndroid(results.getInt(results.getColumnIndex("ID")));
            String roleName = this.getRoleNameAndroid(roleId);

            if (roleName.equals(Roles.ADMIN.toString())) {
                // Make new admin user
                user = new Admin(userId, results.getString(results.getColumnIndex("NAME")), results.getInt(results.getColumnIndex("AGE")),
                        results.getString(results.getColumnIndex("ADDRESS")));
                user.setRoleId(roleId);

            } else if (roleName.equals(Roles.EMPLOYEE.toString())) {
                // Make new employee user
                user = new Employee(userId, results.getString(results.getColumnIndex("NAME")), results.getInt(results.getColumnIndex("AGE")),
                        results.getString(results.getColumnIndex("ADDRESS")));
                user.setRoleId(roleId);

            } else if (roleName.equals(Roles.CUSTOMER.toString())) {
                // Make new customer user
                user = new Customer(userId, results.getString(results.getColumnIndex("NAME")), results.getInt(results.getColumnIndex("AGE")),
                        results.getString(results.getColumnIndex("ADDRESS")));
                user.setRoleId(roleId);
            }
        }
        results.close();
        return user;
    }

    public String getPasswordAndroid(int userId) throws SQLException {
        String password = super.getPassword(userId);
        return password;
    }

    public List<Item> getAllItemsAndroid() throws SQLException {
        Cursor results = super.getAllItems();
        List<Item> items = new ArrayList<>();
        while (results.moveToNext()) {
            BigDecimal price = new BigDecimal(results.getString(results.getColumnIndex("PRICE")));

            // Make new Item and add to list
            Item item = new ItemImpl(results.getInt(results.getColumnIndex("ID")), results.getString(results.getColumnIndex("NAME")), price);
            items.add(item);
        }
        results.close();
        return items;
    }

    public Item getItemAndroid(int itemId) throws SQLException {
        Cursor results = super.getItem(itemId);
        Item item = null;

        while (results.moveToNext()) {
            BigDecimal price = new BigDecimal(results.getString(results.getColumnIndex("PRICE")));

            // Make a new item with the data extracted
            item = new ItemImpl(itemId, results.getString(results.getColumnIndex("NAME")), price);
        }
        results.close();
        return item;
    }

    public Inventory getInventoryAndroid() throws SQLException {
        Cursor results = super.getInventory();
        // Make new Inventory,
        Inventory inventory = new InventoryImpl();
        int total = 0;
        while (results.moveToNext()) {
            int quantity = results.getInt(results.getColumnIndex("QUANTITY"));
            // Add the item and quantity to inventory
            inventory.updateMap(getItemAndroid(results.getInt(results.getColumnIndex("ITEMID"))), quantity);
            total = total + quantity;
        }
        inventory.setTotalItems(total);
        results.close();
        return inventory;
    }

    public int getInventoryQuantityAndroid(int itemId) throws SQLException {
        int quantity = super.getInventoryQuantity(itemId);
        return quantity;
    }

    public SalesLog getSalesAndroid() throws SQLException {
        Cursor results = super.getSales();
        HashMap<Integer, SaleImpl> salesMap = new HashMap<Integer, SaleImpl>();

        while (results.moveToNext()) {
            int saleId = results.getInt(results.getColumnIndex("ID"));
            BigDecimal totalPrice = new BigDecimal(results.getString(results.getColumnIndex("TOTALPRICE")));
            SaleImpl tempSaleImpl =
                    new SaleImpl(saleId, getUserDetailsAndroid(Math.toIntExact(results.getInt(results.getColumnIndex("USERID")))), totalPrice);
            salesMap.put(saleId, tempSaleImpl);
        }
        SalesLog newSalesLog = new SalesLogImpl(null, salesMap);
        results.close();
        return newSalesLog;

    }

    public Sale getSaleByIdAndroid(int saleId) throws SQLException {
        Cursor results = super.getSaleById(saleId);
        Sale sale = null;
        while (results.moveToNext()) {
            BigDecimal totalPrice = new BigDecimal(results.getString(results.getColumnIndex("TOTALPRICE")));
            // Make new sale with extracted info
            sale =
                    new SaleImpl(results.getInt(results.getColumnIndex("ID")), getUserDetailsAndroid(Math.toIntExact(results.getInt(results.getColumnIndex("USERID")))), totalPrice);
        }
        results.close();
        return sale; // return sale
    }

    public List<Sale> getSalesToUserAndroid(int userId) throws SQLException {
        Cursor results = super.getSalesToUser(userId);
        List<Sale> sales = new ArrayList<>();
        while (results.moveToNext()) {
            BigDecimal totalPrice = new BigDecimal(results.getString(results.getColumnIndex("TOTALPRICE")));
            // make new sale, add to list
            Sale sale =
                    new SaleImpl(results.getInt(results.getColumnIndex("ID")), getUserDetailsAndroid(Math.toIntExact(results.getInt(results.getColumnIndex("USERID")))), totalPrice);
            sales.add(sale);
        }
        results.close();
        return sales;
    }

    public Sale getItemizedSaleByIdAndroid(int saleId) throws SQLException {
        Cursor results = super.getItemizedSaleById(saleId);
        HashMap<Item, Integer> itemMap = new HashMap<Item, Integer>();
        Sale itemizedSale = null;
        while (results.moveToNext()) {
            itemMap.put(getItemAndroid(results.getInt(results.getColumnIndex("ITEMID"))), results.getInt(results.getColumnIndex("QUANTITY")));
            itemizedSale = new ItemizedSaleImpl(results.getInt(results.getColumnIndex("SALEID")), itemMap);
        }

        results.close();
        return itemizedSale;
    }

    public SalesLog getItemizedSalesAndroid() throws SQLException {
        Cursor results = super.getItemizedSales();
        // Initialize empty salesLog
        SalesLog log = new SalesLogImpl(null, null);
        while (results.moveToNext()) {
            int id = results.getInt(results.getColumnIndex("SALEID"));
            // Add the itemized sale to the salesLog
            log.addItemizedSaleAndroid(id, results.getInt(results.getColumnIndex("ITEMID")), results.getInt(results.getColumnIndex("QUANTITY")), this);
        }
        results.close();
        return log;
    }

    public List<Integer> getUserActiveAccountAndroid(int userId) throws SQLException {

        Cursor results = super.getUserActiveAccounts(userId);

        List<Integer> userActiveAccountIds = new ArrayList<Integer>();
        while (results.moveToNext()) {
            userActiveAccountIds.add(results.getInt(results.getColumnIndex("ID")));
        }
        results.close();
        return userActiveAccountIds;

    }
    public List<Integer> getUserInactiveAccountAndroid(int userId) throws SQLException {
        Cursor results = super.getUserInactiveAccounts(userId);
        List<Integer> userInactiveAccountIds = new ArrayList<Integer>();
        while (results.moveToNext()) {
            userInactiveAccountIds.add(results.getInt(results.getColumnIndex("ID")));
        }
        results.close();
        return userInactiveAccountIds;

    }

}
