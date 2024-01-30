package com.mp.helloworldplus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NameInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NameInputFragment extends Fragment {

    private nameViewModel nameViewModel;

    public NameInputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.nameViewModel = new ViewModelProvider(requireActivity()).get(com.mp.helloworldplus.nameViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_name_input, container, false);
        EditText nameEdit = view.findViewById(R.id.nameText);
        Button confirmBtn = view.findViewById(R.id.confirmBtn);
        TextView greeting = view.findViewById(R.id.greetingTextView);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String name = nameEdit.getText().toString();
                nameViewModel.setName(name);
                greeting.setText("Hello " + name);
            }
        });

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //Log name when user changes it.
        nameViewModel.getName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String newName) {
                Log.v("Name from fragment", newName);
            }
        });

    }
}