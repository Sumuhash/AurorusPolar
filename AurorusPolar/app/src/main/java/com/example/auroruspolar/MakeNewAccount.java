package com.example.auroruspolar;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.sql.SQLException;
import src.com.b07.database.DatabaseDriverAndroid;
import src.com.b07.database.helper.DatabaseInsertHelper;
import src.com.b07.database.helper.DatabaseInsertHelperAndroid;
import src.com.b07.database.helper.DatabaseSelectHelper;
import src.com.b07.database.helper.DatabaseSelectHelperAndroid;
import src.com.b07.exceptions.DatabaseInsertException;
import src.com.b07.exceptions.InvalidUserIdException;
import src.com.b07.users.Employee;

public class MakeNewAccount extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_make_new_account);

    final DatabaseDriverAndroid db = new DatabaseDriverAndroid(this);
    db.getReadableDatabase();
    db.getWritableDatabase();
    final DatabaseInsertHelperAndroid idb = new DatabaseInsertHelperAndroid(this);
    final DatabaseSelectHelperAndroid sdb = new DatabaseSelectHelperAndroid(this);

    Button createAccount = (Button) findViewById(R.id.newAccount);
    createAccount.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        EditText accountId = (EditText) findViewById(R.id.accountId);
        Intent transition = new Intent(getApplicationContext(), EmployeeScreen.class);
        try {
          idb.insertAccountAndroid(Integer.parseInt(accountId.getText().toString()), true, sdb);
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (InvalidUserIdException e) {
          accountId.setError("Invalid Id");
        } catch (DatabaseInsertException e) {
          e.printStackTrace();
        }
        startActivity(transition);
        finish();
      }
    });

  }
}
