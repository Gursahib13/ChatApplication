package com.example.testfreg;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class Groups extends Fragment {
    private View groupfregmentview;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_group=new ArrayList<>();
    private DatabaseReference RootRef;

    public Groups() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        groupfregmentview=inflater.inflate(R.layout.fragment_groups, container, false);
        RootRef= FirebaseDatabase.getInstance().getReference().child("Groups");
        Initilizefileds();
        RetrieveDisplaygroups();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                String currentGroupName=adapterView.getItemAtPosition(position).toString();
               // Toast.makeText(getContext(),currentGroupName+" Pressed",Toast.LENGTH_SHORT).show();
                Intent go=new Intent(getContext(),groupchatactivity.class);
                go.putExtra("groupName",currentGroupName);
                startActivity(go);
            }
        });
        return groupfregmentview;
    }

    private void RetrieveDisplaygroups()
    {
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Set<String> set=new HashSet<>();//to remove dupicate
                Iterator iterator=dataSnapshot.getChildren().iterator();
                while(iterator.hasNext())
                {
                    set.add(((DataSnapshot)iterator.next()).getKey());

                }
                list_of_group.clear();
                list_of_group.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Initilizefileds()
    {
        listView=groupfregmentview.findViewById(R.id.list_view);
        arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list_of_group);
        listView.setAdapter(arrayAdapter);

    }
}
