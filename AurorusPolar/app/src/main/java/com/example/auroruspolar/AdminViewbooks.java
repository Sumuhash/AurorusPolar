package com.example.auroruspolar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.sql.SQLException;

import src.com.b07.database.DatabaseDriverAndroid;
import src.com.b07.database.helper.DatabaseInsertHelperAndroid;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.exceptions.DatabaseInsertException;
import src.com.b07.exceptions.InvalidItemIdException;
import src.com.b07.exceptions.InvalidPriceException;
import src.com.b07.exceptions.InvalidQuantityException;
import src.com.b07.exceptions.InvalidSaleIdException;
import src.com.b07.exceptions.InvalidUserIdException;
import src.com.b07.users.Admin;

public class AdminViewbooks extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admin_viewbooks);

    final DatabaseSelectHelperAndroid sdb = new DatabaseSelectHelperAndroid(this);

    String viewbook = "";
    try {
      viewbook = Admin.viewBooksAndroid(sdb);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    TextView printViewBooks = findViewById(R.id.books);
    printViewBooks.setText(viewbook);

  }
}
