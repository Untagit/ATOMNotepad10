package site.quasiphainomenon.atomnotepad10;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class NoteEditActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentsEditText;
    private Button saveNoteButton;
    private Button showNoteDelActivity;
    private Button showMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        titleEditText = findViewById(R.id.titleEditText);
        contentsEditText = findViewById(R.id.contentsEditText);

        showNoteDelActivity = findViewById(R.id.showNoteDelActivity);
        showMainActivity = findViewById(R.id.showMainActivity);
        saveNoteButton = findViewById(R.id.saveNoteButton);

        String noteTitle = getIntent().getStringExtra("note_title");
        String noteContents = getIntent().getStringExtra("note_contents");

        titleEditText.setText(noteTitle);
        contentsEditText.setText(noteContents);

        showNoteDelActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int noteId = getIntent().getIntExtra("note_id", -1);

                Intent intent = new Intent(NoteEditActivity.this, NoteDelActivity.class);
                intent.putExtra("note_id", noteId);
                startActivity(intent);
            }
        });

        showMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteEditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void saveData(View view) {

        int noteId = getIntent().getIntExtra("note_id", -1);

        String updatedTitle = titleEditText.getText().toString();
        String updatedContents = contentsEditText.getText().toString();

        String currentDate = CurrentDate.getCurrentDate();
        String updatedDate = currentDate;

        JSONObject data = new JSONObject();
        try {
            data.put("id", noteId);
            data.put("date", updatedDate);
            data.put("title", updatedTitle);
            data.put("contents", updatedContents);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new SendDataToServerTask().execute(data);

        Intent intent = new Intent(NoteEditActivity.this, MainActivity.class);
        startActivity(intent);

    }

    private class SendDataToServerTask extends AsyncTask<JSONObject, Void, Void> {

        @Override
        protected Void doInBackground(JSONObject... jsonObjects) {

            JSONObject data = jsonObjects[0];

            try {
                URL url = new URL("http://example.com/example.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(data.toString());

                writer.flush();
                writer.close();
                os.close();

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
