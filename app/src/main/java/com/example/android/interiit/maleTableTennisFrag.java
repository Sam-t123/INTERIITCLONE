package com.example.android.interiit;

import androidx.fragment.app.Fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;


public class maleTableTennisFrag extends Fragment {

    ListView lv;
    listArrayAdapter adapter;
    logoMap logo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView= inflater.inflate(R.layout.activity_fragment_list, container, false);


        logo = new logoMap();
        final Map logoM = logo.getMap();

        //Female Volleyball Schedule
        lv=rootView.findViewById(R.id.Schedule);
        final FirebaseFirestore badF;
        badF = FirebaseFirestore.getInstance();
        final ArrayList<CardClass> list=new ArrayList<>() ;

        /*Map empMap= new HashMap<>();
        empMap.put("flag","0");
        empMap.put("team1","madras");
        empMap.put("team2","madras");
        empMap.put("s1score1","");
        empMap.put("s1score2","");
        empMap.put("s2score1","");
        empMap.put("s2score2","");
        empMap.put("s3score1","");
        empMap.put("s3score2","");
        empMap.put("s4score1","");
        empMap.put("s4score2","");
        empMap.put("s5score1","");
        empMap.put("s5score2","");
        empMap.put("Day","");
        empMap.put("Time","");
        empMap.put("Court","");

        DocumentReference ref = badF.collection("TT").document("male");

        for(int i=1 ; i<=48; i++)
        {
            empMap.put("MNo",i);
            ref.collection("matches")
                    .document(String.valueOf(i))
                    .set(empMap);
        }*/

        badF.collection("TT").document("male").collection("matches").orderBy("MNo").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
            {
                if(e!=null)
                {
                    // Toast.makeText(Volleyball.this,"some exception",Toast.LENGTH_SHORT).show();
                }
                for(DocumentChange match: queryDocumentSnapshots.getDocumentChanges())
                {
                    if(match.getType() == DocumentChange.Type.ADDED)
                    {
                        int uri1 = (int) logoM.get(match.getDocument().get("team1"));
                        int uri2 = (int) logoM.get(match.getDocument().get("team2"));
                        list.add(new CardClass(Integer.parseInt(match.getDocument().getId()),uri1,match.getDocument().get("team1").toString(),uri2,match.getDocument().get("team2").toString() ));
                    }
                    adapter=new listArrayAdapter(getActivity(),0,list);
                    lv.setAdapter(adapter);

                }
            }
        });

        final int [] cursor=new int[48];
        /*listArrayAdapter adapter=new listArrayAdapter(getActivity(),0,list);
        lv.setAdapter(adapter);*/
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Task<DocumentSnapshot> task;
                task = badF.collection("TT").document("male").collection("matches").document(String.valueOf(i+1)).get();
                while(!task.isComplete());

                DocumentSnapshot ds = task.getResult();


                final TextView scoreView=view.findViewById(R.id.score_view);
                final TextView resultView=view.findViewById(R.id.result_view);
                final RelativeLayout location=view.findViewById(R.id.location);



                TextView court=view.findViewById(R.id.court);
                TextView day=view.findViewById(R.id.day);
                TextView time=view.findViewById(R.id.time);

                final TextView status=view.findViewById(R.id.match_status);

                ListView lsv=rootView.findViewById(R.id.Schedule);
                lsv.setOnScrollListener(new AbsListView.OnScrollListener() {
                    private int mLastFirstVisibleItem;

                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                        location.setVisibility(View.GONE);
                        status.setVisibility(View.GONE);
                        scoreView.setVisibility(View.GONE);
                        resultView.setVisibility(View.GONE);


                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {

                       /* location.setVisibility(View.GONE);
                        status.setVisibility(View.GONE);
                        scoreView1.setVisibility(View.GONE);
                        scoreView2.setVisibility(View.GONE);
                        scoreView3.setVisibility(View.GONE);
                        scoreView4.setVisibility(View.GONE);
                        scoreView5.setVisibility(View.GONE);*/

                    }
                });

                if(cursor[i]==0 ){
                    cursor[i]=1;
                    location.setVisibility(View.VISIBLE);
                    court.setText("Court No." + ds.get("Court").toString());
                    day.setText("Day " + ds.get("Day").toString());
                    time.setText(ds.get("Time").toString());
                    if(ds.get("flag").toString().equals("0")){
                        status.setVisibility(View.VISIBLE);

                    }
                    else {
                        status.setVisibility(View.GONE);
                        scoreView.setVisibility(View.VISIBLE);
                        location.setVisibility(View.VISIBLE);
                        if(!ds.get("s2score1").toString().equals(""))
                            resultView.setVisibility(View.VISIBLE);
                        scoreView.setText(ds.get("s1score1").toString());
                        resultView.setText(ds.get("s1score2").toString());

                    }
                }
                else{
                    cursor[i]=0;
                    location.setVisibility(View.GONE);
                    status.setVisibility(View.GONE);
                    scoreView.setVisibility(View.GONE);
                    resultView.setVisibility(View.GONE);
                }
            }
        });
        return rootView;

    }


}
