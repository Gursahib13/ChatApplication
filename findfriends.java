package com.example.testfreg;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
public class findfriends extends AppCompatActivity
{
    private Toolbar mToolbar;
    private RecyclerView findfriend;
   // FirebaseDatabase firebaseDatabase;
  //  ScrollView scrollView;
    private DatabaseReference UserRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findfriends);
      //  scrollView=findViewById(R.id.scroll);
        UserRef= FirebaseDatabase.getInstance().getReference().child("Users");
        findfriend=findViewById(R.id.find_friend_recylerview);
        findfriend.setHasFixedSize(true);
        findfriend.setLayoutManager(new LinearLayoutManager(this));
     //   firebaseDatabase=FirebaseDatabase.getInstance();
        mToolbar=findViewById(R.id.find_friend);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Find Friends");
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseRecyclerAdapter<Contacts,Holder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Contacts, Holder>(Contacts.class,R.layout.users_display_layout,Holder.class,UserRef)
//        {
//            @Override
//            protected void populateViewHolder(Holder hollder, Contacts contacts, int i)
//            {
//             hollder.setdetails(getApplicationContext(),contacts.)
//            }
//        };
//    }
      @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Contacts> options=new FirebaseRecyclerOptions.Builder<Contacts>().setQuery(UserRef,Contacts.class).build();
        FirebaseRecyclerAdapter<Contacts,FindFriendViewHolder> adapter= new FirebaseRecyclerAdapter<Contacts, FindFriendViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FindFriendViewHolder holder, final int position, @NonNull Contacts model)
            {
                holder.userName.setText(model.getName());
                holder.userstatus.setText(model.getStatus());
               Picasso.get().load(model.getImage()).placeholder(R.drawable.profileimg).into(holder.profileImage);
               holder.itemView.setOnClickListener(new View.OnClickListener() {//on click on find users
                   @Override
                   public void onClick(View view)
                   {
                        String visit_user_id=getRef(position).getKey();
                        Intent user=new Intent(findfriends.this,Profile.class);
                        user.putExtra("visit",visit_user_id);
                        startActivity(user);
                   }
               });
            }

            @NonNull
            @Override
            public FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_display_layout,parent,false);
                FindFriendViewHolder viewHolder=new FindFriendViewHolder(view);
                return viewHolder;

            }
        };
        findfriend.setAdapter(adapter);
        adapter.startListening();


    }
    public static class FindFriendViewHolder extends RecyclerView.ViewHolder
    {
        TextView userName,userstatus;
        CircleImageView profileImage;

        public FindFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.user_profile_name);
            userstatus=itemView.findViewById(R.id.user_status);
            profileImage=itemView.findViewById(R.id.user_profile_image);
        }
    }
}
