package com.lovera.diego.criptomessage;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity {

    public static String PASSWORD;
    private EditText text1;
    private EditText text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button encrypt = findViewById(R.id.button2);
        Button decrypt = findViewById(R.id.button);
        text1 = findViewById(R.id.editText);
        text2 = findViewById(R.id.editText2);
        text1.requestFocus();

        final SimpleCypher util = new SimpleCypher();

        encrypt.setOnClickListener(v -> {

            if (text1.getText().toString().equals("") ||
                    text1.getText() == null){
                Toast.makeText(getApplicationContext(),
                        R.string.toast_no_text,
                        Toast.LENGTH_SHORT).show();
            } else {
                if (PASSWORD == null) {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_no_password,
                            Toast.LENGTH_SHORT).show();
                } else {
                    text2.setText(util.Cifrar(
                            text1.getText().toString(),
                            PASSWORD));
                }
            }
        });

        decrypt.setOnClickListener(v -> {
            if (text1.getText().toString().equals("") || text1.getText() == null){
                Toast.makeText(getApplicationContext(), R.string.toast_no_text, Toast.LENGTH_SHORT).show();
            } else {
                if (PASSWORD == null) {
                    Toast.makeText(getApplicationContext(), R.string.toast_no_password, Toast.LENGTH_SHORT).show();
                } else {
                    text2.setText(util.Descifrar(text1.getText().toString(), PASSWORD));
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
            DialogFragment newFragment = new PasswordDialogFragment();
            newFragment.show(getSupportFragmentManager(), "password");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


