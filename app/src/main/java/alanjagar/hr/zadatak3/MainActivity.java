package alanjagar.hr.zadatak3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import alanjagar.hr.zadatak3.model.Person;

public class MainActivity extends AppCompatActivity {

    private List<Person> people;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String content = readFile();
        if(content == null){
            return;
        }

        try{
            people = parse(content);
            Toast.makeText(this, "Parsanje uspijelo", Toast.LENGTH_LONG).show();
            setUpList();
        } catch (Exception e) {
            Toast.makeText(this, "Greška prilikom parsanja datoteke", Toast.LENGTH_LONG).show();
        }
    }

    public void setUpList(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        PeopleAdapter adapter = new PeopleAdapter(this, people);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        //Potencijalno nepotrebno
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Even if we don't use it then also our items shows default animation. #Check Docs
    }

    private String readFile(){
        InputStream is = this.getResources().openRawResource(R.raw.people);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        try {
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine.trim());
            }
        } catch (IOException e) {
            Toast.makeText(this, "Čitanje filea nije prošlo", Toast.LENGTH_LONG).show();
            return null;
        }
        return sb.toString();
    }

    private List<Person> parse(String file) throws Exception {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(file));

        String tagName = "", tagInnerText = "";
        boolean inItemDataTag = false, inPerson = false;
        List<Person> resultList = new ArrayList<>();

        Person person = null;

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            tagInnerText = parser.getText();
            switch (eventType){
                case XmlPullParser.START_TAG:
                    tagName = parser.getName();

                    if(tagName.trim().equals("person")){
                        inItemDataTag = true;
                        person = new Person();
                    }
                    else if(tagName.trim().equals("firstName")){
                        inItemDataTag = true;
                    }
                    else if(tagName.trim().equals("lastName")){
                        inItemDataTag = true;
                    }
                    else if(tagName.trim().equals("subject")){
                        inItemDataTag = true;
                    }
                    else if(tagName.trim().equals("grade")){
                        inItemDataTag = true;
                    }

                    break;
                case XmlPullParser.END_TAG:
                    if(tagName.toLowerCase().trim().equals("person")){
                        //resultList.add(person); //Ovo ne radi iz nekog meni nepoznator zazloga
                        inItemDataTag = false;
                    }
                    else if(tagName.trim().equals("firstName")){
                        inItemDataTag = false;
                    }
                    else if(tagName.trim().equals("lastName")){
                        inItemDataTag = false;
                    }
                    else if(tagName.trim().equals("subject")){
                        inItemDataTag = false;
                    }
                    else if(tagName.trim().equals("grade")){
                        inItemDataTag = false;
                    }
                    break;
                case XmlPullParser.TEXT:
                    if(!inItemDataTag){
                        break;
                    }
                    switch(tagName){
                        case "firstName":
                            person.setFirstName(tagInnerText);
                            break;
                        case "lastName":
                            person.setLastName(tagInnerText);
                            break;
                        case "subject":
                            person.setSubject(tagInnerText);
                            break;
                        case "grade":
                            int grade = Integer.parseInt(tagInnerText);
                            person.setGrade(grade);
                            resultList.add(person);
                            break;
                    }
                    break;
            }
            eventType = parser.next();
        }

        return resultList;
    }
}
