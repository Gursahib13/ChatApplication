package com.example.testfreg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends FirebaseRecyclerAdapter<Contacts,UserAdapter.myviewholder>
        {



            public UserAdapter(@NonNull FirebaseRecyclerOptions<Contacts> options) {
                super(options);
            }

            @Override
            protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Contacts model)
            {
                holder.username.setText(model.getName());
                holder.status.setText(model.getStatus());
                Glide.with(holder.imageView.getContext()).load(model.getImage()).into(holder.imageView);
            }

            @NonNull
            @Override
            public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.users_display_layout,parent,false);
                return new myviewholder(view);
            }

            public class myviewholder extends RecyclerView.ViewHolder
            {
                CircleImageView imageView;
                TextView username,status;
                public myviewholder(@NonNull View itemView) {
                    super(itemView);
                    username=itemView.findViewById(R.id.user_profile_name);
                    status=itemView.findViewById(R.id.user_status);
                    imageView=itemView.findViewById(R.id.user_profile_image);
                }
            }
}
