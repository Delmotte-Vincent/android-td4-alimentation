package com.main.exercice2.androidproject.Client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentTransaction;


import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.widget.ImageView;

import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.widget.TextView;

import com.main.exercice2.androidproject.ClientList;


import com.main.exercice2.androidproject.CommercantObjet;

import com.main.exercice2.androidproject.Interfaces.Constantes;
import com.main.exercice2.androidproject.Interfaces.IButtonCLickedListener;
import com.main.exercice2.androidproject.Interfaces.ICallBack;
import com.main.exercice2.androidproject.MainActivity;
import com.main.exercice2.androidproject.Notification;
import com.main.exercice2.androidproject.NotificationReceiver;
import com.main.exercice2.androidproject.R;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.List;


public class MainClient extends AppCompatActivity implements IButtonCLickedListener, Constantes, ICallBack {
    private Bitmap picture;
    private static final String CHANNEL_ID ="channel1";
    private int notificationId = 0;
    BottomNavigationView bottomNavigationView;
    ClientAlertFragment clientAlertFragment;
    ClientSignalFragment clientSignalFragment;
    ClientMapFragment clientMapFragment;
    TextView  clientName;
    private static final String TAG = "TWEET" ;
    private Client client ;
    private String type;
    private boolean defaultPicture;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);
        bottomNavigationView=findViewById(R.id.activity_main_bottom_navigation);
        clientName= findViewById(R.id.client);
        Bundle b = this.getIntent().getExtras();
        int id =b.getInt("id");

        client = ClientList.findClientId(id);
        clientName.setText(client.getFirstName()+" "+client.getLastName());

        this.configureBottomView();

        ClientProfilFragment clientProfilFragment = (ClientProfilFragment)getSupportFragmentManager().findFragmentById(R.id.client_frame);
        if (clientProfilFragment==null){
            FragmentTransaction trans= getSupportFragmentManager().beginTransaction();
            trans.replace(R.id.client_frame, new ClientProfilFragment());
            trans.addToBackStack(null);
            trans.commit();
        }
        clientAlertFragment= new ClientAlertFragment();
        clientMapFragment=new ClientMapFragment();

    }

    private void configureBottomView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return MainClient.this.updateFragment(item.getItemId());
            }
        });;
    }

    private boolean updateFragment(int itemId) {
        FragmentTransaction trans;
        trans= getSupportFragmentManager().beginTransaction();
        switch (itemId) {
            case R.id.action_profil:
                trans.replace(R.id.client_frame, new ClientProfilFragment());
                break;
            case R.id.action_alertes :
                trans.replace(R.id.client_frame, clientAlertFragment);
                break;
            case R.id.action_map:
                trans.replace(R.id.client_frame, clientMapFragment);
                break;
            case R.id.action_signal :
                trans.replace(R.id.client_frame, clientSignalFragment=new ClientSignalFragment());
                break;
        }
        trans.addToBackStack(null);
        trans.commit();
        return true;
    }


    /**
     * Recupère le titre du signalement
     * @return
     */
    public String getSignalTitle() {
        EditText title = findViewById(R.id.titre_signal);
        return !title.getText().toString().equals("") ? title.getText().toString() : "Sans titre";
    }

    /**
     * Récupère la description du signalement
     * @return
     */
    public String getSignalDesc() {
        EditText title = findViewById(R.id.desc_signal);
        return !title.getText().toString().equals("") ? title.getText().toString() : "Sans description";
    }

    public Drawable getSignalPicture() {
        ImageView imageView = findViewById(R.id.image_signal);
        return imageView.getDrawable();
    }

    @Override
    public void onButtonSignalClicked(View but,boolean checked) {
        String titre = getSignalTitle();
        String desc = getSignalDesc();
        Drawable draw = getSignalPicture();


        sendNotificationOnChannel(titre, desc, CHANNEL_ID, NotificationCompat.PRIORITY_MAX);
        Toast.makeText(this,"Nouveau Signalement : "+titre+" à été créé",Toast.LENGTH_LONG).show();


        clientAlertFragment.newAlert(titre,desc, type, draw, this.defaultPicture);
        if(checked)
            this.shareOnTwitter(this,titre+"\n"+desc,null);
            //shareTwitter(titre+"\n"+desc);
    }


    /**
     * Création de la notification
     * @param titre
     * @param desc
     * @param channelId
     * @param priority
     */
    private void sendNotificationOnChannel(String titre, String desc, String channelId, int priority) {
        Intent activityIntent = new Intent(this, MainActivity.class);
        //TODO trouver le moyen de mettre MainClient.class sans que ça plante
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        Intent broadcastIntent = new Intent (this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", desc);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap picture = this.picture;

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.baseline_account_circle_black_18dp)
                .setContentTitle(titre)
                .setContentText(desc)
                .setPriority(priority)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setShowWhen(true)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent);

        if (!this.defaultPicture) {
            notification.setLargeIcon(picture)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(picture).bigLargeIcon(null));
        }

        Notification.getNotificationManager().notify(++notificationId, notification.build());
    }

    @Override
    public void onButtonPictureSignalClicked(View view) {

    }

    @Override
    public void onCheckClicked(View view, boolean status) {
        if(status)
            Toast.makeText(this,"cette notification va être transferer à Twitter ",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"cette notification ne va pas être transferer à Twitter ",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CAMERA:{
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Toast toast = Toast.makeText(getApplicationContext(),"autorisation caméra validée",Toast.LENGTH_LONG);
                    toast.show();
                    clientSignalFragment.takePicture();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),"autorisation caméra refusée",Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            }
            case REQUEST_GPS: {  //GPS FINE LOCATION only autorisation result code
                if( grantResults.length > 0 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "FINE authorisation Granted", Toast.LENGTH_LONG);
                    toast.show();
                    clientMapFragment.openGPS2();
                } else {
                    Log.d( TAG, "(only) FINE LOCATION permission NOT Granted");
                }
            } break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== REQUEST_CAMERA){
            if (resultCode==RESULT_OK){
                picture = (Bitmap) data.getExtras().get("data");
                clientSignalFragment.setImage(picture);
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

    private void shareTwitter(String message) {
        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, message);
        tweetIntent.setType("text/plain");
        PackageManager packManager = getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if (resolved) {
            startActivity(tweetIntent);
        } else {
            Intent i = new Intent();
            i.putExtra(Intent.EXTRA_TEXT, message);
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message)));
            startActivity(i);
            Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf(TAG, "UTF-8 should always be supported", e);
            return "";
        }
    }

    public  void shareOnTwitter(AppCompatActivity appCompatActivity, String textBody, Uri fileUri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setPackage("com.twitter.android");
        intent.putExtra(Intent.EXTRA_TEXT,!TextUtils.isEmpty(textBody) ? textBody : "");

        if (fileUri != null) {
            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");
        }

        try {
            appCompatActivity.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
            Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(findViewById(R.id.com_list)!=null&&findViewById(R.id.com_list).getVisibility()==(View.VISIBLE)) {
                findViewById(R.id.com_list).setVisibility(View.GONE);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * Permet d'obtenir le type de signalement renseigné dans le fragment
     * @param type
     */
    public void getTypeFromFrag(String type) {
        this.type = type;
    }

    public void setDefaultPicture(Boolean defaultPicture) {
        this.defaultPicture = defaultPicture;
    }

    @Override
    public void sendCommercantObjet(CommercantObjet c) {
        ClientCommercantFragment clientCommercantFragment = new ClientCommercantFragment();
        Bundle args = new Bundle();
        //On aura juste à passer l'id du commercant dans le bundle
        getSupportFragmentManager().beginTransaction().replace(R.id.client_frame,clientCommercantFragment).commit();

    }
}
