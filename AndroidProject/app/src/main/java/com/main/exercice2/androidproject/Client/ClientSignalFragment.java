package com.main.exercice2.androidproject.Client;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.main.exercice2.androidproject.Abonnement;
import com.main.exercice2.androidproject.Adapter.CommercantListAdapter;
import com.main.exercice2.androidproject.ClientList;
import com.main.exercice2.androidproject.Commercant.CommercantObjet;
import com.main.exercice2.androidproject.CommercantList;
import com.main.exercice2.androidproject.Interfaces.AlertType;
import com.main.exercice2.androidproject.Interfaces.Constantes;
import com.main.exercice2.androidproject.Interfaces.IButtonCLickedListener;
import com.main.exercice2.androidproject.R;
import com.main.exercice2.androidproject.abonnementList;

import java.util.ArrayList;
import java.util.List;

public class ClientSignalFragment extends Fragment implements View.OnClickListener {
    private IButtonCLickedListener mCallBack;
    private ImageView imageView;
    Spinner spinner;
    Client client;
    CommercantObjet commercantNonChoisi;

    private CheckBox checkBox ;


    ClientSignalFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_signal_client,container,false);
        imageView = rootView.findViewById(R.id.image_signal);
        checkBox = rootView.findViewById(R.id.checkBox);
        checkBox.setOnClickListener(this);
        commercantNonChoisi=new CommercantObjet("Choisir un commercant");
        List choix = new ArrayList<>();
        choix.add(commercantNonChoisi);
        client= ClientList.findClientId(getActivity().getIntent().getExtras().getInt("id"));
        for(Abonnement a : abonnementList.getAbonnementClient(client.getId())){
            CommercantObjet c = CommercantList.findClientId(a.getIdCommercant());
            if(!choix.contains(c)){
                choix.add(c);
            }
        }

        // Choix du type de signalement menu déroulant
        spinner = rootView.findViewById(R.id.typeAlert);

        //choix.add(AlertType.DEFAULT);
        //choix.add(AlertType.BOUCHERIE);
        //choix.add(AlertType.BOULANGERIE);
        //choix.add(AlertType.EPICERIE);
        //choix.add(AlertType.POISONNERIE);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, choix);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        rootView.findViewById(R.id.but_signal).setOnClickListener(this);
        rootView.findViewById(R.id.but_photo_signal).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallBack = (IButtonCLickedListener) getActivity();
    }


        @Override
    public void onClick(View view) {
        MainClient mc = (MainClient) getActivity();
        mc.getTypeFromFrag(spinner.getSelectedItem().toString());
        if(view.getId()==R.id.checkBox){
            mCallBack.onCheckClicked(view,checkBox.isChecked());
        }
        if(view.getId()==R.id.but_signal){
            CommercantObjet c=(CommercantObjet)spinner.getSelectedItem();
            if(!c.equals(commercantNonChoisi)){
                mCallBack.onButtonSignalClicked(view,checkBox.isChecked(),c);}
            else{
                Toast.makeText(this.getContext(),"Selectionnez un commercant auquel vous êtes abonné",Toast.LENGTH_LONG).show();
            }
        };
        if(view.getId()==R.id.but_photo_signal){
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA}, Constantes.REQUEST_CAMERA);
            }
            else{
                takePicture();
            }
            mCallBack.onButtonPictureSignalClicked(view);
        };

    }

    public void takePicture(){
        MainClient mc = (MainClient) getActivity();
        System.out.println("PASSE A FALSE DANS TAKE PICTURE");
        mc.setDefaultPicture(false);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult(intent, Constantes.REQUEST_CAMERA);

    }

    public void setImage(Bitmap bitmap){imageView.setImageBitmap(bitmap);}
}
