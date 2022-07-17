package com.chonneck.ui_chat;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.chonneck.ui_chat.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {
    Client cli1 = null;
    EditText textView = null;
    EditText textView2 = null;

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (EditText) getView().findViewById(R.id.editTextTextMultiLine);
        textView2 = (EditText) getView().findViewById(R.id.editTextTextPersonName2);
        try {
            String IP = ((MainActivity) getActivity()).getIP();
            Integer Port = ((MainActivity) getActivity()).getPort();
            cli1 = new Client(IP, Port);
            if(((MainActivity) getActivity()).mode==0) {
                try {
                    cli1.sendMessage(textView, "l");
                    String user = ((MainActivity) getActivity()).getUser();
                    cli1.sendMessage(textView, user);
                    String pass = ((MainActivity) getActivity()).getPass();
                    cli1.sendMessage(textView, pass);
                } catch (Exception e) {
                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_FirstFragment);
                }
                String m = cli1.receiveMessage();
                if(m.equals("Ok")){
                    cli1.listenForMessage(textView);
                } else {
                    ((MainActivity) getActivity()).err = m;
                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_FirstFragment);
                }
            } else {
                try {
                    cli1.sendMessage(textView, "s");
                    String user = ((MainActivity) getActivity()).getUser();
                    cli1.sendMessage(textView, user);
                    String pass = ((MainActivity) getActivity()).getPass();
                    cli1.sendMessage(textView, pass);
                    String gender = ((MainActivity) getActivity()).getGender();
                    cli1.sendMessage(textView, gender);
                    String age = ((MainActivity) getActivity()).getAge();
                    cli1.sendMessage(textView, age);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String m = cli1.receiveMessage();
                if(m.equals("Ok")){
                    cli1.listenForMessage(textView);
                } else {
                    ((MainActivity) getActivity()).err = m;
                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_ThirdFragment);
                }
            }
        } catch (Exception e){
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_FirstFragment);
        }

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cli1.sendMessage(textView, textView2.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textView2.setText("");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}