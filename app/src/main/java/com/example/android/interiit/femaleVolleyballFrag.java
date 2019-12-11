package com.example.android.interiit;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
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

public class femaleVolleyballFrag extends Fragment {

    ListView lv;
    listArrayAdapter adapter;
    logoMap logo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.activity_fragment_list, container, false);

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
        empMap.put("Day","");
        empMap.put("Time","");
        empMap.put("Court","");

        DocumentReference ref = badF.collection("VollyBall").document("female");

        for(int i=1 ; i<=32; i++)
        {
            empMap.put("MNo",i);
            ref.collection("matches")
                    .document(String.valueOf(i))
                    .set(empMap);
        }*/

        badF.collection("VollyBall").document("female").collection("matches").orderBy("MNo").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
            {
                if(e!=null)
                {
                    // Toast.makeText(Volleyball.this,"some exception",Toast.LENGTH_SHORT).show();
                }
                for(DocumentChange match: queryDocumentSnapshots.getDocumentChanges()) {
                    if (match.getType() == DocumentChange.Type.ADDED) {
                        int uri1 = (int) logoM.get(match.getDocument().get("team1"));
                        int uri2 = (int) logoM.get(match.getDocument().get("team2"));
                        list.add(new CardClass(Integer.parseInt(match.getDocument().getId()), uri1, match.getDocument().get("team1").toString(), uri2, match.getDocument().get("team2").toString()));
                    }
                }

                adapter=new listArrayAdapter(getActivity(),0,list);
                lv.setAdapter(adapter);


            }
        });

        final int [] cursor=new int[32];
        /*listArrayAdapter adapter=new listArrayAdapter(getActivity(),0,list);
        lv.setAdapter(adapter);*/
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Task<DocumentSnapshot> task;
                task = badF.collection("VollyBall").document("female").collection("matches").document(String.valueOf(i+1)).get();
                while(!task.isComplete());

                DocumentSnapshot ds = task.getResult();


                LinearLayout scoreView=view.findViewById(R.id.score_view_set1);
                RelativeLayout location=view.findViewById(R.id.location);
                TextView score1=view.findViewById(R.id.score1set1);
                TextView score2=view.findViewById(R.id.score2set1);
                TextView court=view.findViewById(R.id.court);
                TextView day=view.findViewById(R.id.day);
                TextView time=view.findViewById(R.id.time);
                TextView status=view.findViewById(R.id.match_status);
                if(cursor[i]==0 ){
                    cursor[i]=1;
                    if(ds.get("flag").toString().equals("0"))
                        status.setVisibility(View.VISIBLE);
                    else {
                        status.setVisibility(View.GONE);
                        scoreView.setVisibility(View.VISIBLE);
                        location.setVisibility(View.VISIBLE);
                        score1.setText(ds.get("s1score1").toString());
                        score2.setText(ds.get("s1score2").toString());
                        court.setText("Court No." + ds.get("Court").toString());
                        day.setText("Day " + ds.get("Day").toString());
                        time.setText(ds.get("Time").toString());
                    }
                }
                else{
                    status.setVisibility(View.GONE);
                    cursor[i]=0;
                }
            }
        });
        return rootView;
    }







    public static void expand(final View v) {

        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, android.view.animation.Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }



    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

}
