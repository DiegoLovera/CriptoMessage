package com.lovera.diego.criptomessage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.diegolovera.simplecypher.Exceptions.EmptyPasswordException;
import com.diegolovera.simplecypher.Exceptions.InvalidLetterException;
import com.diegolovera.simplecypher.SimpleCypher;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText mInputText;
    private TextInputEditText mOutputText;
    private SimpleCypher mCypher;
    private SharedPreferences mPreferences;
    private BottomAppBar mBottomAppBar;
    private CoordinatorLayout mCoordinatorLayout;
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
            showPasswordMissing();
        } else {
            mCypher = new SimpleCypher.SimpleCypherBuilder(password).build();
        }
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
        if (id == R.id.share_app) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "https://github.com/DiegoLovera/SimpleCypher");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share"));
            return true;
        } else if (id == R.id.check_repo) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/DiegoLovera/SimpleCypher"));
            startActivity(browserIntent);
            return true;
        } else if (id == R.id.personal_page) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://diegolovera.github.io/"));
            startActivity(browserIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showPasswordMissing() {
        Snackbar.make(mCoordinatorLayout,
                R.string.toast_no_password, Snackbar.LENGTH_LONG)
                .setAction(R.string.set_password, v -> showDialogPassword()).show();
    }

    public void showTextMissing() {
        Snackbar.make(mCoordinatorLayout, R.string.toast_no_text, Snackbar.LENGTH_LONG).show();
    }

    private void showDialogPassword(){
        DialogPassword dialog = new DialogPassword();
        dialog.show(getSupportFragmentManager(), "password");
        dialog.setDialogCallback(new DialogCallback() {
            @Override
            public void onPasswordSet(String password) {
                mPreferences.edit().putString(PASSWORD_KEY, password).apply();
                mCypher = new SimpleCypher.SimpleCypherBuilder(password).build();
            }

            @Override
            public void onCancelDialog() {
                String password = mPreferences.getString(PASSWORD_KEY, "");
                if (password.isEmpty()) {
                    showPasswordMissing();
                }
            }
        });
    }

    private void copyTextToClipboard() {
        if (!Objects.requireNonNull(mOutputText.getText()).toString().isEmpty()) {
            ClipboardManager clipboard = (ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Encrypted text",
                    mOutputText.getText().toString());
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
                Snackbar.make(mCoordinatorLayout,
                        R.string.text_copied, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setViews() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            if (!Objects.requireNonNull(mOutputText.getText()).toString().isEmpty()) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mOutputText.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share"));
            } else {
                showTextMissing();
            }
        });
        mCoordinatorLayout = findViewById(R.id.mainCoordinator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mBottomAppBar = findViewById(R.id.bottomBar);
        setSupportActionBar(toolbar);
        mBottomAppBar.replaceMenu(R.menu.bottom_appbar_menu);
        mBottomAppBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.copy) {
                copyTextToClipboard();
                return true;
            } else if (id == R.id.clear) {
                mInputText.setText("");
                mOutputText.setText("");
                return true;
            }
            return super.onOptionsItemSelected(item);
        });
        mBottomAppBar.setNavigationOnClickListener(v -> showDialogPassword());
        Button mButtonEncrypt = findViewById(R.id.button_encrypt);
        Button mButtonDecrypt = findViewById(R.id.button_decrypt);
        mButtonDecrypt.setOnClickListener(v -> decrypt());
        mButtonEncrypt.setOnClickListener(v -> encrypt());

        mInputText = findViewById(R.id.input_text);

        mInputText.setOnTouchListener((view, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (mInputText.getRight()
                        - mInputText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    mInputText.setText("");
                    return false;
                }
            }
            return false;
        });

        mOutputText = findViewById(R.id.output_text);
        mOutputText.setFocusable(false);
        mOutputText.setOnClickListener(v -> copyTextToClipboard());
    }

    private void encrypt() {
        if (mInputText.getText() == null || mInputText.getText().toString().isEmpty()){
            showTextMissing();
        } else {
            if (mCypher == null) {
                hideKeyboard();
                showPasswordMissing();
            } else {
                try {
                    hideKeyboard();
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
        if (mInputText.getText() == null || mInputText.getText().toString().isEmpty()) {
            showTextMissing();
        } else {
            if (mCypher == null) {
                hideKeyboard();
                showPasswordMissing();
            } else {
                try {
                    hideKeyboard();
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

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}


