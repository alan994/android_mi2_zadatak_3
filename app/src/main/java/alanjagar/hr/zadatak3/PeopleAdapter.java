package alanjagar.hr.zadatak3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alanjagar.hr.zadatak3.model.Person;

/**
 * Created by Alan on 07.07.2016..
 */
public class PeopleAdapter   extends RecyclerView.Adapter<PeopleAdapter.PersonViewHolder> {

    private List<Person> mDataList;
    private LayoutInflater inflater;

    public PeopleAdapter(Context context, List<Person> data) {
        inflater = LayoutInflater.from(context);
        this.mDataList = data;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewGroup row = (ViewGroup) inflater.inflate(R.layout.list_item, parent, false);
        PersonViewHolder holder = new PersonViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        Person current = mDataList.get(position);
        PersonViewHolder productHolder = (PersonViewHolder) holder;
        productHolder.setData(current);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFullName;
        private TextView tvSubject;
        private TextView tvGrade;

        public PersonViewHolder(View itemView) {
            super(itemView);
            tvFullName = (TextView) itemView.findViewById(R.id.tvFullName);
            tvSubject = (TextView) itemView.findViewById(R.id.tvSubject);
            tvGrade = (TextView) itemView.findViewById(R.id.tvGrade);
        }

        public  void setData(Person person){
            tvFullName.setText(person.getLastName() + ", " + person.getFirstName());
            tvSubject.setText(person.getSubject());
            tvGrade.setText(String.valueOf(person.getGrade()));
        }
    }
}
