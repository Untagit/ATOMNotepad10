package site.quasiphainomenon.atomnotepad10;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class NoteComparator implements Comparator<Note> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public int compare(Note note1, Note note2) {
        try {
            Date date1 = dateFormat.parse(note1.getDate());
            Date date2 = dateFormat.parse(note2.getDate());

            return date2.compareTo(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

