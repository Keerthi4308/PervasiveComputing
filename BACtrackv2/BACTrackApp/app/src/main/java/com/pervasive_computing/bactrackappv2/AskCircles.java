package com.pervasive_computing.bactrackappv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AskCircles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_circle);
        Button click_yes =findViewById(R.id.join_circles);
        Button click_no=findViewById(R.id.no_join_circles);

//        click_yes.setOnClickListener(new View.OnClickListener (){
//
//        });


    }

    public void onClickyes(View view) {
        startActivity(new Intent(getApplicationContext(), ViewCircles.class));

    }

    public void onClickno(View view) {
        startActivity(new Intent(getApplicationContext(), WillYouDrink.class));

    }

}
