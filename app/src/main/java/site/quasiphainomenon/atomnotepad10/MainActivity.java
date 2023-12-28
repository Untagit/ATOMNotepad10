package site.quasiphainomenon.atomnotepad10;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView notesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesListView = (ListView) findViewById(R.id.notesList);
        getJSON("http://example.com/example.php");
        setOnClickListener();
    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadIntoListView(s);
                    Toast.makeText(getApplicationContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        List<Note> notesList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            int noteId = obj.getInt("id");
            String date = obj.getString("date");
            String title = obj.getString("title");
            String contents = obj.getString("contents");

            notesList.add(new Note(noteId, date, title, contents));
        }

        notesList.sort(new NoteComparator());
        NoteAdapter noteAdapter = new NoteAdapter(this, notesList);
        notesListView.setAdapter(noteAdapter);
    }

    private void setOnClickListener()
    {
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Note selectedNote = (Note) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, NoteViewActivity.class);

                intent.putExtra("note_id", selectedNote.getId());
                intent.putExtra("note_title", selectedNote.getTitle());
                intent.putExtra("note_contents", selectedNote.getContents());

                startActivity(intent);
            }
        });
    }

    public void newNote(View view)
    {
        Intent newNoteIntent = new Intent(this, NoteAddActivity.class);
        startActivity(newNoteIntent);
    }
}

