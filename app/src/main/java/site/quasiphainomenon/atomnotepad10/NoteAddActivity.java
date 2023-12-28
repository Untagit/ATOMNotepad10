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

public class NoteAddActivity extends AppCompatActivity {

    private EditText titleCreateText;
    private EditText contentsCreateText;
    private Button showMainActivity;
    private Button saveNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);

        titleCreateText = findViewById(R.id.titleCreateText);
        contentsCreateText = findViewById(R.id.contentsCreateText);

        showMainActivity = findViewById(R.id.showMainActivity);
        saveNoteButton = findViewById(R.id.saveNoteButton);

        showMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void saveData(View view) {

        String createdTitle = titleCreateText.getText().toString();
        String createdContents = contentsCreateText.getText().toString();

        String currentDate = CurrentDate.getCurrentDate();
        String updatedDate = currentDate;

        JSONObject data = new JSONObject();
        try {
            data.put("date", updatedDate);
            data.put("title", createdTitle);
            data.put("contents", createdContents);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new NoteAddActivity.SendDataToServerTask().execute(data);

        Intent intent = new Intent(NoteAddActivity.this, MainActivity.class);
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
