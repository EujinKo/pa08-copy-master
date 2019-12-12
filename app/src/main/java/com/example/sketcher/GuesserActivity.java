package com.example.sketcher;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GuesserActivity extends AppCompatActivity {

    public static DrawingFragment drawingFragment = null;
    int[] color_arr = {R.color.colorRed, R.color.colorGreen, R.color.colorBlue,
            R.color.colorPurple, R.color.aqua, R.color.pink, R.color.grey, R.color.orange,
            R.color.colorWhite};
    float[] size_arr = {15, 30, 60};
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference hostRef;
    private String name = "ev1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        hostRef = database.getReference().child("host");

        SomeFragment sf = new SomeFragment();
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.main, sf);
        transaction.addToBackStack(null);
        transaction.commit();

//        this.drawingFragment = new DrawingFragment();
//        drawingFragment.setContainerActivity(this);
//        FragmentTransaction transaction = getSupportFragmentManager()
//                .beginTransaction();
//        transaction.add(R.id.draw_layout, drawingFragment);
//
//        transaction.addToBackStack(null);
//        transaction.commit();

//        turns(this);

        new CountDownTimer(20000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                TextView tv = (TextView) findViewById(R.id.timerText);
                tv.setText("seconds remaining: " + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
//                hostRef.setValue("ev2");
//                turns();
            }
        }.start();

    }

//    public void turns(View v){
//        if(hostRef.toString() == name){
//            this.drawingFragment = new DrawingFragment();
//            drawingFragment.setContainerActivity(this);
//            FragmentTransaction transaction = getSupportFragmentManager()
//                    .beginTransaction();
//            transaction.add(R.id.draw_layout, drawingFragment);
//
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//
//        if(hostRef.toString() != name){
//            System.out.println("switchFrag");
//            SomeFragment sf = new SomeFragment();
//            FragmentTransaction transaction =
//                    getSupportFragmentManager().beginTransaction();
//
//            transaction.replace(R.id.main, sf);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//    }

    public void switchFrag(View v){
        System.out.println("switchFrag");
        SomeFragment sf = new SomeFragment();
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.main, sf);
        transaction.addToBackStack(null);
        transaction.commit();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//    }


    public void clearDrawing(View v){
        DrawingView dv = drawingFragment.getDrawingView();
        dv.startNew();
    }

    public void changeBrushSize(View v){
        DrawingView dv = drawingFragment.getDrawingView();
        int viewID = v.getId();

        if(viewID == findViewById(R.id.button_small).getId()){
            dv.changePaintSize(size_arr[0]);
        }else if(viewID == findViewById(R.id.button_medium).getId()){
            dv.changePaintSize(size_arr[1]);
        }else if(viewID == findViewById(R.id.button_large).getId()){
            dv.changePaintSize(size_arr[2]);
        }
    }

    public void example(){
        Bitmap bitmap = drawingFragment.getDrawingView().getBitmap();
        String name = "canvas";
        File filesDir = getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }

    }

    /**
     * This function calls the contacts list fragment
     *  which shows lists of contact and lets the user select
     *  which person to share with
     * @param v
     */
    public void onClickShare(View v) throws Exception {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Test");
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        String timeStamp = new SimpleDateFormat(
                "yyyMMdd_HHmmss").format(new Date());

        myRef.setValue("Erick test sent at time: " + timeStamp);

        System.out.println("share button clicked");
        example();
    }

    /**
     * This function changes the paint brush color
     * @param v
     */
    public void changePaintColor(View v){
        DrawingView dv = drawingFragment.getDrawingView();
        int viewID = v.getId();

        if(viewID == findViewById(R.id.button_red).getId()){
            dv.changePaintColor(color_arr[0]);
        }else if(viewID == findViewById(R.id.button_green).getId()){
            dv.changePaintColor(color_arr[1]);
        }else if(viewID == findViewById(R.id.button_blue).getId()){
            dv.changePaintColor(color_arr[2]);
        }else if(viewID == findViewById(R.id.button_purple).getId()){
            dv.changePaintColor(color_arr[3]);
        }else if(viewID == findViewById(R.id.button_aqua).getId()){
            dv.changePaintColor(color_arr[4]);
        }else if(viewID == findViewById(R.id.button_pink).getId()){
            dv.changePaintColor(color_arr[5]);
        }else if(viewID == findViewById(R.id.button_grey).getId()){
            dv.changePaintColor(color_arr[6]);
        }else if(viewID == findViewById(R.id.button_orange).getId()){
            dv.changePaintColor(color_arr[7]);
        }else if(viewID == findViewById(R.id.button_erase).getId()){
            dv.changePaintColor(color_arr[8]);
        }



    }





    public Uri saveImageToFile(Bitmap bitmap){
        //creates bitmap to draw on
        Canvas canvas = new Canvas(bitmap);
        Uri ImageUri = null;

        try{
            File imageFile = createImageFile();
            FileOutputStream out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);

            //Saves the uri for the view group image file
            ImageUri = FileProvider.getUriForFile(
                    this,"com.example.Sketcher.FileProvider",
                    imageFile);
        }catch(Exception e){
            e.printStackTrace();
        }
        //ss
        return ImageUri;
    }




    private File createImageFile() throws Exception{
        String timeStamp = new SimpleDateFormat(
                "yyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir);

//        myRef.getDatabase().getReference();
//        DatabaseReference mountainsRef = myRef.child('mountains.jpg');






        return image;
    }


    public void startOnlineGame(View view){
//        Uri uri = saveImageToFile(drawingFragment.getDrawingView().getBitmap());

    }





}





//        if(hostRef.toString() == name){
//                this.drawingFragment = new DrawingFragment();
//                drawingFragment.setContainerActivity(this);
//                FragmentTransaction transaction = getSupportFragmentManager()
//                .beginTransaction();
//                transaction.add(R.id.draw_layout, drawingFragment);
//
//                transaction.addToBackStack(null);
//                transaction.commit();
//                }