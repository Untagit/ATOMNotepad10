package site.quasiphainomenon.atomnotepad10;

import java.util.ArrayList;

public class Note {
    public static ArrayList<Note> noteArrayList = new ArrayList<>();

    private int id;
    private String date;
    private String title;
    private String contents;

    public Note(int id, String date, String title, String contents)
    {
        this.id = id;
        this.date = date;
        this.title = title;
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String created) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }


    public static Note getNoteForID(int passedNoteID)
    {
        for (Note note : noteArrayList)
        {
            if(note.getId() == passedNoteID)
                return note;
        }
        return null;
    }

    @Override
    public String toString() {
        return title;
    }
}
