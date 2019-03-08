package com.lovera.diego.criptomessage;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diegolovera.simplecypher.Exceptions.EmptyPasswordException;
import com.diegolovera.simplecypher.Exceptions.InvalidLetterException;
import com.diegolovera.simplecypher.SimpleCypher;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements DialogCallback {
    private EditText mInputText;
    private EditText mOutputText;
    private Button mButtonEncrypt;
    private Button mButtonDecrypt;
    private SimpleCypher mCypher;
    private SharedPreferences mPreferences;
    private static final String PASSWORD_KEY = "PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        mInputText.requestFocus();

        mPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String password = mPreferences.getString(PASSWORD_KEY, "");
        if (password.isEmpty()) {
            Snackbar.make(findViewById(R.id.mainCoordinator),
                    "A password must be set", Snackbar.LENGTH_LONG)
                    .setAction("Set password", v -> showDialogPassword()).show();
        } else {
            mCypher = new SimpleCypher.SimpleCypherBuilder(password).build();
        }

        mButtonEncrypt.setOnClickListener(v -> encrypt());
        mButtonDecrypt.setOnClickListener(v -> decrypt());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.set_password) {
            showDialogPassword();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPasswordSet(String password) {
        mPreferences.edit().putString(PASSWORD_KEY, password).apply();
        mCypher = new SimpleCypher.SimpleCypherBuilder(password).build();
    }

    @Override
    public void onCancelDialog() {
        String password = mPreferences.getString(PASSWORD_KEY, "");
        if (password.isEmpty()) {
            Snackbar.make(findViewById(R.id.mainCoordinator),
                    "A password must be set", Snackbar.LENGTH_LONG)
                    .setAction("Set password", v -> showDialogPassword()).show();
        }
    }

    private void showDialogPassword(){
        DialogPassword dialog = new DialogPassword();
        dialog.show(getSupportFragmentManager(), "password");
        dialog.setDialogCallback(this);
    }

    private void setViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mButtonEncrypt = findViewById(R.id.button_encrypt);
        mButtonDecrypt = findViewById(R.id.button_decrypt);
        mInputText = findViewById(R.id.input_text);
        mOutputText = findViewById(R.id.output_text);
        mOutputText.setFocusable(false);
        mOutputText.setOnClickListener(v -> {
            if (!mOutputText.getText().toString().isEmpty()) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Encrypted text", mOutputText.getText().toString());
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                    Snackbar.make(findViewById(R.id.mainCoordinator),
                            "Text copied to the clipboard", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void encrypt() {
        if (mInputText.getText() == null || mInputText.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    R.string.toast_no_text,
                    Toast.LENGTH_SHORT).show();
        } else {
            if (mCypher == null) {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_no_password,
                        Toast.LENGTH_SHORT).show();
            } else {
                try {
                    mOutputText.setText(mCypher.encrypt(mInputText.getText().toString()));
                } catch (InvalidLetterException e) {
                    Toast.makeText(getApplicationContext(),
                            e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } catch (EmptyPasswordException e) {
                    Toast.makeText(getApplicationContext(),
                            e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void decrypt() {
        if (mInputText.getText() == null || mInputText.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.toast_no_text, Toast.LENGTH_SHORT).show();
        } else {
            if (mCypher == null) {
                Toast.makeText(getApplicationContext(), R.string.toast_no_password, Toast.LENGTH_SHORT).show();
            } else {
                try {
                    mOutputText.setText(mCypher.decrypt(mInputText.getText().toString()));
                } catch (InvalidLetterException e) {
                    Toast.makeText(getApplicationContext(),
                            e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } catch (EmptyPasswordException e) {
                    Toast.makeText(getApplicationContext(),
                            e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}


