package com.lovera.diego.criptomessage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Created by Diego Lovera on 27/08/2017.
 */

public class DialogPassword extends DialogFragment {
    private DialogCallback listener;
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private MaterialButton buttonAccept;
    private MaterialButton buttonCancel;
    private Context mContext;

    public DialogPassword() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_NoTitle_Transparent);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_password, container, false);
        textInputEditText = v.findViewById(R.id.input_dialog_password);
        textInputLayout = v.findViewById(R.id.layout_input_dialog_password);
        buttonAccept = v.findViewById(R.id.button_accept_dialog_password);
        buttonCancel = v.findViewById(R.id.button_cancel_dialog_password);
        textInputEditText.requestFocus();

        buttonAccept.setOnClickListener(v1 -> {
            if (textInputEditText.getText() != null) {
                String password = textInputEditText.getText().toString();
                if (!password.isEmpty() && password.length() > 2) {
                    if (listener != null) {
                        listener.onPasswordSet(password);
                    }
                    textInputLayout.setErrorEnabled(false);
                    dismiss();
                } else {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(mContext.getResources().getString(R.string.short_password));
                }
            } else {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError( mContext.getResources().getString(R.string.empty_password));
            }
        });

        buttonCancel.setOnClickListener(v2 -> {
            dismiss();
            if (listener != null) {
                listener.onCancelDialog();
            }
        });
        return v;
    }

    public void setDialogCallback(DialogCallback callback) {
        listener = callback;
    }
}
