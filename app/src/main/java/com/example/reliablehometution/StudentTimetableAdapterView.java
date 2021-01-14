package com.example.reliablehometution;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentTimetableAdapterView  extends RecyclerView.Adapter<StudentTimetableAdapterView.ListItemHolder> {

    private List<StudentTeacherTimetable> mNoteList;
    private StudentPrpfilePage studentPrpfilePage;

    public StudentTimetableAdapterView(List<StudentTeacherTimetable> mNoteList, StudentPrpfilePage studentPrpfilePage) {
        this.mNoteList = mNoteList;
        this.studentPrpfilePage = studentPrpfilePage;
    }

    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_for_timetable, parent, false);

        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        holder.mTeacher.setText(mNoteList.get(position).getTeacher());
        //Toast.makeText(studentPrpfilePage,"aa  "+mNoteList.get(position).getTeacher(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public class ListItemHolder extends
            RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView mTeacher;
        public ListItemHolder(View view) {
            super(view);
            mTeacher = (TextView)
                    view.findViewById(R.id.teacher_timetable);

            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            //studentList.finish();
        }
    }
}
