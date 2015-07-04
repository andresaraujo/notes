package andresaraujo.github.io.notes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    ArrayList<Note> notes;
    OnNoteClickCallback onNoteClickCallback;

    public NoteAdapter(ArrayList<Note> notes, OnNoteClickCallback onNoteClickCallback) {
        this.onNoteClickCallback = onNoteClickCallback;
        this.notes = notes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_note_item, viewGroup, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder noteViewHolder, int position) {
        final Note note = notes.get(position);

        noteViewHolder.titleView.setText(note.title);
        noteViewHolder.contentView.setText(note.content);

        noteViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoteClickCallback.onNoteClick(note);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void add(Note note) {
        notes.add(note);
    }

    // Note ViewHolder
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleView;
        TextView contentView;

        View view;

        public NoteViewHolder(View itemView) {
            super(itemView);

            this.view = itemView;
            titleView = (TextView) itemView.findViewById(R.id.title);
            contentView = (TextView) itemView.findViewById(R.id.content);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }

    }

    interface OnNoteClickCallback {
        void onNoteClick(Note note);
    }

}
