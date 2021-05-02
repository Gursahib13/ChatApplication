package com.example.testfreg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Holder extends FirebaseRecyclerAdapter<Contacts,Holder.myviewholder>
{
    public Holder(@NonNull FirebaseRecyclerOptions<Contacts> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Contacts model) {
holder.username.setText(model.getName());
        holder.userstatus.setText(model.getStatus());
        Glide.with(holder.userimage.getContext()).load(model.getImage()).into(holder.userimage);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.users_display_layout,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView username,userstatus;
        CircleImageView userimage;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            username= itemView.findViewById(R.id.user_profile_name);
            userstatus=itemView.findViewById(R.id.user_status);
            userimage=itemView.findViewById(R.id.user_profile_image);
        }

    }
}
