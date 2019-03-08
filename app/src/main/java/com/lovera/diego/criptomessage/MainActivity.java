package com.lovera.diego.criptomessage;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diegolovera.simplecypher.Exceptions.EmptyPasswordException;
import com.diegolovera.simplecypher.Exceptions.InvalidLetterException;
import com.diegolovera.simplecypher.SimpleCypher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity {
    private EditText mInputText;
    private EditText mOutputText;
    private SimpleCypher mCypher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button encrypt = findViewById(R.id.button2);
        Button decrypt = findViewById(R.id.button);
        mInputText = findViewById(R.id.editText);
        mOutputText = findViewById(R.id.editText2);
        mInputText.requestFocus();

        encrypt.setOnClickListener(v -> {

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
        });

        decrypt.setOnClickListener(v -> {
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
        });
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            PasswordDialogFragment newFragment = new PasswordDialogFragment();
            newFragment.show(getSupportFragmentManager(), "password");
            newFragment.setDialogCallback(password -> mCypher = new SimpleCypher.SimpleCypherBuilder(password).build());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


