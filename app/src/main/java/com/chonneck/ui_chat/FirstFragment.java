package com.chonneck.ui_chat;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.chonneck.ui_chat.databinding.FragmentFirstBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        if(((MainActivity) getActivity()).err != null){
            Snackbar.make(view, ((MainActivity) getActivity()).err, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
        }

        super.onViewCreated(view, savedInstanceState);
        EditText textView3 = (EditText) getView().findViewById(R.id.editTextTextPersonName);
        EditText textView4 = (EditText) getView().findViewById(R.id.editTextTextPassword);
        EditText textView5 = (EditText) getView().findViewById(R.id.editTextTextPersonName3);
        EditText textView6 = (EditText) getView().findViewById(R.id.editTextNumberDecimal);

        if(((MainActivity) getActivity()).getUser()!=null) {
            textView3.setText(((MainActivity) getActivity()).getUser());
            textView4.setText(((MainActivity) getActivity()).getPass());
            textView5.setText(((MainActivity) getActivity()).getIP());
            textView6.setText(((MainActivity) getActivity()).getPort().toString());
        }

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).mode = 0;
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
                ((MainActivity) getActivity()).setInf(textView3.getText().toString(), textView4.getText().toString(), textView5.getText().toString(), Integer.parseInt(textView6.getText().toString()));
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_ThirdFragment);
                ((MainActivity) getActivity()).setInf(textView3.getText().toString(), textView4.getText().toString(), textView5.getText().toString(), Integer.parseInt(textView6.getText().toString()));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}