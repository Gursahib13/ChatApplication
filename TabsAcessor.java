package com.example.testfreg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsAcessor extends FragmentPagerAdapter {
    public TabsAcessor(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            /*case 0:
                chatsfreg chatsfreg=new chatsfreg();
                return chatsfreg;*/
            case 0:
                Groups groups=new Groups();
                return groups;
            case 1:
                contacts contacts=new contacts();
                return contacts;
            default:
                return null;
        }

    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            /*case 0:

                return "Chats";*/
            case 0:

                return "Groups";
            case 1:

                return "About";
            default:
              return "Groups";
        }


    }




}
