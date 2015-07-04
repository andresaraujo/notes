package andresaraujo.github.io.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        NoteAdapter.OnNoteClickCallback {

    private View view;
    private NoteAdapter noteAdapter;

    private Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.layout);
        setupToolbar();
        setupFab();
        setupRecycleView();
    }

    private void setupRecycleView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        ArrayList<Note> notes =  new ArrayList<Note>();
        notes.add(new Note("a", "b"));
        notes.add(new Note("c", "d"));

        noteAdapter = new NoteAdapter(notes, this);

        recyclerView.setAdapter(noteAdapter);

    }

    private void setupFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fab:
                startActivityForResult(new Intent(this, DetailActivity.class), 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0){
            if (resultCode == Activity.RESULT_OK) {
                String noteTitle = data.getStringExtra("note_title");
                String noteContent = data.getStringExtra("note_content");

                if(selectedNote != null) {
                    selectedNote.title = noteTitle;
                    selectedNote.content = noteContent;
                    selectedNote =  null;
                }else {
                    noteAdapter.add(new Note(noteTitle, noteContent));
                }
                noteAdapter.notifyDataSetChanged();
            } else {
                Snackbar.make(view, "Empty note discarded", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onNoteClick(Note note) {
        selectedNote = note;
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("note_title", note.title);
        intent.putExtra("note_content", note.content);
        startActivityForResult(intent, 0);
    }
}
