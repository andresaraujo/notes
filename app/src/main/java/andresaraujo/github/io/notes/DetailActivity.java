package andresaraujo.github.io.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    private EditText titleEdit;
    private EditText contentEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();

        setupToolbar();
        setupView(extras);
    }

    private void setupView(Bundle extras) {
        titleEdit = (EditText) findViewById(R.id.note_title);
        contentEdit = (EditText) findViewById(R.id.note_content);
        if (extras != null){
            titleEdit.setText(extras.getString("note_title"));
            contentEdit.setText(extras.getString("note_content"));
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {

        if ((titleEdit.getText() == null || titleEdit.getText().toString().isEmpty()) &&
                (contentEdit.getText() == null || contentEdit.getText().toString().isEmpty())) {
            setResult(RESULT_CANCELED);
        } else {
            Intent intent = new Intent();
            intent.putExtra("note_title", titleEdit.getText().toString());
            intent.putExtra("note_content", contentEdit.getText().toString());

            setResult(RESULT_OK, intent);
        }
        super.finish();
    }
}
