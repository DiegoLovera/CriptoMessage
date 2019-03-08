package com.lovera.diego.criptomessage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Created by Diego Lovera on 27/08/2017.
 */

public class PasswordDialogFragment extends DialogFragment {
    private DialogCallback listener;
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private MaterialButton buttonAccept;
    private MaterialButton buttonCancel;

    public PasswordDialogFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_password, container, false);

        return v;
    }

    public void setDialogCallback(DialogCallback callback) {
        listener = callback;
    }
}
