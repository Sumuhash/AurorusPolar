package com.example.auroruspolar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminScreen extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admin_screen);
    Button buttonProfile = (Button) findViewById(R.id.profile);
    Button buttonViewbook = (Button) findViewById((R.id.viewbook));
    Button buttonSave = (Button) findViewById((R.id.saveDatabase));
    Button buttonPromote = (Button) findViewById((R.id.promote));
    Button buttonLoad = (Button) findViewById((R.id.loadDatabase));
    Button buttonActive = (Button) findViewById(R.id.activeAccount);
    Button buttonHistoric = (Button) findViewById(R.id.historicAccount);

    buttonProfile.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openFile(0);
      }
    });

    buttonViewbook.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openFile(1);
      }
    });

    buttonSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openFile(2);
      }
    });

    buttonPromote.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openFile(3);
      }
    });

    buttonLoad.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openFile(4);
      }
    });

    buttonActive.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openFile(5);
      }
    });

    buttonHistoric.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openFile(6);
      }
    });
  }


  public void openFile(int value) {
    Intent intent;
    if (value == 0) {
      intent = new Intent(this, ProfileActivity.class);
      Intent temp = getIntent();
      intent.putExtra("id", temp.getStringExtra("id"));
    } else if (value == 1) {
      intent = new Intent(this, AdminViewbooks.class);
    } else if (value == 2) {
      intent = new Intent(this, SaveDatabase.class); // not done yet
    } else if (value == 3) {
      intent = new Intent(this, AdminPromote.class);
    } else if (value == 4) {
      intent = new Intent(this, LoadDatabase.class); //change activity name
    } else if (value == 5) {
      intent = new Intent(this, AdminUserActiveRequest.class); //change activity name
    } else if (value == 6) {
      intent = new Intent(this, AdminUserInactiveRequest.class); //change activity name
    } else {
      intent = new Intent(this, AdminScreen.class);
    }
    startActivity(intent);
  }


}






