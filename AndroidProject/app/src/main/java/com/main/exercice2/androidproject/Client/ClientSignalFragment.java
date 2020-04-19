package com.main.exercice2.androidproject.Client;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.main.exercice2.androidproject.IButtonCLickedListener;
import com.main.exercice2.androidproject.MainActivity;
import com.main.exercice2.androidproject.R;

public class ClientSignalFragment extends Fragment implements View.OnClickListener {
    private IButtonCLickedListener mCallBack;

    ClientSignalFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_signal_client,container,false);
        rootView.findViewById(R.id.but_signal).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallBack = (IButtonCLickedListener) getActivity();
    }

    @Override
    public void onClick(View view) {
        System.out.println("test");
        mCallBack.onButtonSignalClicked(view);
    }
}
