package com.example.auroruspolar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminUserActiveRequest extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admin_custid_active);

    Button sumbit = (Button) findViewById(R.id.submit);
    final TextView custid = (TextView) findViewById(R.id.custId);
    final Intent transition;

    sumbit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent transition = new Intent(getApplicationContext(), AdminActive.class);
        transition.putExtra("ids", custid.toString());
        startActivity(transition);
      }
    });


  }
}
