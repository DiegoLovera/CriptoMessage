package com.lovera.diego.criptomessage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Diego Lovera on 27/08/2017.
 */

public class PasswordDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_password, null))
                .setPositiveButton(R.string.accept, (dialog, id) -> {
                    Dialog dialogView = getDialog();
                    final EditText textpass = dialogView.findViewById(R.id.passwordGlobal);
                    if (textpass.getText() == null){
                        Toast.makeText(MainActivity._context, R.string.empty_password, Toast.LENGTH_LONG).show();
                        DialogFragment newFragment = new PasswordDialogFragment();
                        newFragment.show(getFragmentManager(), "password");

                    } else {
                        if (textpass.getText().length() < 2){
                            Toast.makeText(MainActivity._context, R.string.short_password, Toast.LENGTH_LONG).show();
                            DialogFragment newFragment = new PasswordDialogFragment();
                            newFragment.show(getFragmentManager(), "password");
                        }
                        else{
                            MainActivity.PASSWORD = textpass.getText().toString();
                            Toast.makeText(MainActivity._context, R.string.correct_password, Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> PasswordDialogFragment.this.getDialog().cancel());
        return builder.create();
    }
}
