package com.main.exercice2.androidproject.Commercant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.main.exercice2.androidproject.AlertDialogCustom;
import com.main.exercice2.androidproject.Client.MainClient;
import com.main.exercice2.androidproject.CommercantList;
import com.main.exercice2.androidproject.Interfaces.Constantes;
import com.main.exercice2.androidproject.Interfaces.IButtonCLickedListener;
import com.main.exercice2.androidproject.MainActivity;
import com.main.exercice2.androidproject.Notification;
import com.main.exercice2.androidproject.Post;
import com.main.exercice2.androidproject.PostList;
import com.main.exercice2.androidproject.abonnementList;
import com.main.exercice2.androidproject.R;


public class CommercantSignalement extends AppCompatActivity implements Constantes {
    private static final String CHANNEL_ID ="channel1";
    private IButtonCLickedListener mCallBack;
    private EditText title;
    private EditText description;
    private Button but_signal;
    private Button but_signal_photo;
    CommercantObjet commercant;
    private String type="inconnu";
    private boolean defaultPicture;
    private int notificationId = 0;
    private Bitmap picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercant_signalement);


        Bundle b = this.getIntent().getExtras();
        int id =b.getInt("id");
        commercant = CommercantList.findClientId(id);

        //this.defaultPicture=true;

        ActivityCompat.requestPermissions(CommercantSignalement.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, PackageManager.PERMISSION_GRANTED);

        title = findViewById(R.id.titre_signal);
        description = findViewById(R.id.desc_signal);

        but_signal = findViewById(R.id.but_signal);

        but_signal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String titre = getSignalTitle();
                String desc = getSignalDesc();
                Drawable draw = getSignalPicture();

                AlertDialogCustom.AlertDialogCalendar(CommercantSignalement.this, titre, desc);
                AlertDialogCustom.AlertDialogSMS(CommercantSignalement.this, title, description,commercant.getId(), commercant.getPhoneNumber());

                sendNotificationOnChannel(titre, desc, CHANNEL_ID, NotificationCompat.PRIORITY_MAX);
                Toast.makeText(CommercantSignalement.this,"Nouveau Signalement : "+titre+" à été créé",Toast.LENGTH_LONG).show();
                PostList.getAlertes().add(new Post(titre,desc, type, draw, false,commercant));

            }
        });

        but_signal_photo = findViewById(R.id.but_photo_signal);
        but_signal_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(CommercantSignalement.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(CommercantSignalement.this,new String[]{Manifest.permission.CAMERA}, Constantes.REQUEST_CAMERA);
                }
                else{
                    takePicture();
                }
            }
        });
    }

    private void sendNotificationOnChannel(String titre, String desc, String channelId, int priority) {

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId);
        RemoteViews expandedView;

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent intent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        if (!this.defaultPicture) {
            expandedView = new RemoteViews(getPackageName(), R.layout.notification_expanded_image);

            expandedView.setImageViewBitmap(R.id.image_view_expanded, this.picture);
            expandedView.setTextViewText(R.id.text_view_expanded_1, titre);
            expandedView.setTextViewText(R.id.text_view_expanded_2, type);
        } else {
            expandedView = new RemoteViews(getPackageName(), R.layout.notification_expanded_no_image);

            expandedView.setTextViewText(R.id.text_view_expanded_1, titre);
            expandedView.setTextViewText(R.id.text_view_expanded_2, type);
            expandedView.setTextViewText(R.id.text_view_expanded_3, desc);
        }

        notification.setSmallIcon(R.drawable.eat_it_white)
                .setContentTitle(titre)
                .setContentText(type)
                .setColor(Color.BLUE)
                .setContentIntent(intent)
                .setCustomBigContentView(expandedView)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle());

        Notification.getNotificationManager().notify(++notificationId, notification.build());
    }

    /**
     * Recupère le titre du signalement
     * @return
     */
    public String getSignalTitle() {
        title = findViewById(R.id.titre_signal);
        return !title.getText().toString().equals("") ? title.getText().toString() : "Sans titre";
    }

    /**
     * Récupère la description du signalement
     * @return
     */
    public String getSignalDesc() {
        description = findViewById(R.id.desc_signal);
        return !description.getText().toString().equals("") ? description.getText().toString() : "Sans description";
    }

    public Drawable getSignalPicture() {
        ImageView imageView = findViewById(R.id.image_signal);
        return imageView.getDrawable();
    }

    public void takePicture(){
        //MainClient mc = (MainClient) getActivity();
        System.out.println("PASSE A FALSE DANS TAKE PICTURE");
        //mc.setDefaultPicture(false);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.startActivityForResult(intent, Constantes.REQUEST_CAMERA);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== REQUEST_CAMERA){
            if (resultCode==RESULT_OK){
                picture = (Bitmap) data.getExtras().get("data");
                this.setImage(picture);
            }
            else if (resultCode==RESULT_CANCELED){
                Toast toast = Toast.makeText(getApplicationContext(),"la photo n'a pas été prise",Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(),"Erreur de caméra",Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    private void setImage(Bitmap picture) {
        ImageView i = findViewById(R.id.image_signal);
        i.setImageBitmap(picture);
    }

}
