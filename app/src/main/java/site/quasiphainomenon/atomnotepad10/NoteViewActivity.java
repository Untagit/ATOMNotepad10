package site.quasiphainomenon.atomnotepad10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NoteViewActivity extends AppCompatActivity {

    private TextView noteTitleTextView;
    private TextView noteContentsTextView;
    private Button editNoteButton;
    private Button showMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        noteTitleTextView = findViewById(R.id.noteTitleTextView);
        noteContentsTextView = findViewById(R.id.noteContentsTextView);
        editNoteButton = findViewById(R.id.editNoteButton);
        showMainActivity = findViewById(R.id.showMainActivity);

        int noteId = getIntent().getIntExtra("note_id", -1);
        String noteTitle = getIntent().getStringExtra("note_title");
        String noteContents = getIntent().getStringExtra("note_contents");

        noteTitleTextView.setText(noteTitle);
        noteContentsTextView.setText(noteContents);

        editNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NoteViewActivity.this, NoteEditActivity.class);

                intent.putExtra("note_id", noteId);
                intent.putExtra("note_title", noteTitle);
                intent.putExtra("note_contents", noteContents);

                startActivity(intent);
            }
        });

        showMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteViewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
