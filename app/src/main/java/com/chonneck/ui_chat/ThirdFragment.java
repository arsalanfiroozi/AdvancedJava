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

import com.chonneck.ui_chat.databinding.FragmentThirdBinding;
import com.google.android.material.snackbar.Snackbar;

public class ThirdFragment extends Fragment {

    private FragmentThirdBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        binding = FragmentThirdBinding.inflate(inflater, container, false);
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
        EditText textView7 = (EditText) getView().findViewById(R.id.editTextTextPassword2);
        EditText textView8 = (EditText) getView().findViewById(R.id.editTextTextPassword3);

        if(((MainActivity) getActivity()).getGender()!=null) {
            textView3.setText(((MainActivity) getActivity()).getUser());
            textView4.setText(((MainActivity) getActivity()).getPass());
            textView5.setText(((MainActivity) getActivity()).getIP());
            textView6.setText(((MainActivity) getActivity()).getPort().toString());
            textView7.setText(((MainActivity) getActivity()).getGender());
            textView8.setText(((MainActivity) getActivity()).getAge());
        }

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).mode = 1;
                ((MainActivity) getActivity()).setInf2(textView3.getText().toString(), textView4.getText().toString(), textView5.getText().toString(), Integer.parseInt(textView6.getText().toString()), textView7.getText().toString(), textView8.getText().toString());
                NavHostFragment.findNavController(ThirdFragment.this)
                        .navigate(R.id.action_ThirdFragment_to_SecondFragment);
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ThirdFragment.this)
                        .navigate(R.id.action_ThirdFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}