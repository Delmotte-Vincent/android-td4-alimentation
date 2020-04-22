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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.main.exercice2.androidproject.Constantes;
import com.main.exercice2.androidproject.IButtonCLickedListener;
import com.main.exercice2.androidproject.MainActivity;
import com.main.exercice2.androidproject.R;

public class ClientSignalFragment extends Fragment implements View.OnClickListener {
    private IButtonCLickedListener mCallBack;
    private ImageView imageView;
    ClientSignalFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_signal_client,container,false);
        imageView=rootView.findViewById(R.id.image_signal);
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
        if(view.getId()==R.id.but_signal)mCallBack.onButtonSignalClicked(view);
        if(view.getId()==R.id.but_photo_signal){
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA}, Constantes.REQUEST_CAMERA);
            }
            else{takePicture();}
            mCallBack.onButtonPictureSignalClicked(view);
        };
    }

    public void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult(intent, Constantes.REQUEST_CAMERA);
    }

    public void setImage(Bitmap bitmap){imageView.setImageBitmap(bitmap);}
}
