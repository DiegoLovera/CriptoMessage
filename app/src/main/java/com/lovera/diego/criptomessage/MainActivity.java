package com.lovera.diego.criptomessage;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FABToolbarLayout morph;
    public static Context _context;
    public static String PASSWORD = null;
    private EditText text1 = null;
    private EditText text2 = null;
    private FragmentManager _fM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        morph = (FABToolbarLayout) findViewById(R.id.fabtoolbar);
        Button encrypt = (Button) findViewById(R.id.button2);
        Button decrypt = (Button) findViewById(R.id.button);
        text1 = (EditText) findViewById(R.id.editText);
        text2 = (EditText) findViewById(R.id.editText2);
        text1.requestFocus();
        _context = this;
        _fM = getFragmentManager();

        final Cryp util = new Cryp();

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (text1.getText().toString().equals("") || text1.getText() == null){
                    Toast.makeText(_context, R.string.toast_no_text, Toast.LENGTH_SHORT).show();
                } else {
                    if (PASSWORD == null) {
                        Toast.makeText(_context, R.string.toast_no_password, Toast.LENGTH_SHORT).show();
                    } else {
                        text2.setText(util.Cifrar(text1.getText().toString(), PASSWORD));
                    }
                }
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text1.getText().toString().equals("") || text1.getText() == null){
                    Toast.makeText(_context, R.string.toast_no_text, Toast.LENGTH_SHORT).show();
                } else {
                    if (PASSWORD == null) {
                        Toast.makeText(_context, R.string.toast_no_password, Toast.LENGTH_SHORT).show();
                    } else {
                        text2.setText(util.Descifrar(text1.getText().toString(), PASSWORD));
                    }
                }
            }
        });

        View uno, dos, tres, cuatro;

        uno = findViewById(R.id.uno);
        dos = findViewById(R.id.dos);
        cuatro = findViewById(R.id.cuatro);
        tres = findViewById(R.id.tres);

        fab.setOnClickListener(this);
        uno.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);
        cuatro.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        morph.hide();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            morph.show();
        } else if (v.getId() == R.id.uno){
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", this.text2.getText().toString());
            clipboard.setPrimaryClip(clip);

        } else if (v.getId() == R.id.dos){
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            String pasteData = "";
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            pasteData = (String) item.getText();
            text1.setText(pasteData);

        } else if (v.getId() == R.id.tres){
            this.text1.setText("");
            this.text2.setText("");
        } else if (v.getId() == R.id.cuatro){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, this.text2.getText().toString());
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share"));
        }


        morph.hide();
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
            newFragment.show(_fM, "password");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


