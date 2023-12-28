package site.quasiphainomenon.atomnotepad10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(Context context, List<Note> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Note note = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_cell, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.cellTitle);
        TextView dateTextView = convertView.findViewById(R.id.cellNoteDate);

        titleTextView.setText(note.getTitle());
        dateTextView.setText(note.getDate());

        return convertView;
    }
}

