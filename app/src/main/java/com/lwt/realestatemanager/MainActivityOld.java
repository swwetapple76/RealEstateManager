//package com.lwt.realestatemanager;
//
//
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.lwt.realestatemanager.utils.Utils;
//
//public class MainActivityOld extends AppCompatActivity {
//
//    private TextView textViewMain;
//    private TextView textViewQuantity;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_old);
//
////        this.textViewMain = findViewById(R.id.activity_second_activity_text_view_main);
//        // fixed first bug
//        this.textViewMain = findViewById(R.id.activity_main_activity_text_view_main);
//        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);
//
//        this.configureTextViewMain();
//        this.configureTextViewQuantity();
//    }
//
//    private void configureTextViewMain(){
//        this.textViewMain.setTextSize(15);
//        this.textViewMain.setText("Le premier bien immobilier enregistré vaut ");
//    }
//
//    private void configureTextViewQuantity(){
//        int quantity = Utils.convertDollarToEuro(100);
//        this.textViewQuantity.setTextSize(20);
//        //fixed the second bug
////        this.textViewQuantity.setText(quantity);
//        this.textViewQuantity.setText(String.valueOf(quantity));
//    }
//}
