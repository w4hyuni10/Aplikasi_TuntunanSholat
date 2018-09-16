package org.d3ifcool.jagosholat;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TataCaraFragment extends Fragment {

    public TataCaraFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tatacara, container, false);

        // -----------------------------------------------------------------------------------------
        // Deklarasi Element XML
        TabLayout tabLayout = rootView.findViewById(R.id.tablayout_feature);
        ViewPager viewPager = rootView.findViewById(R.id.viewpager_feature);
        // -----------------------------------------------------------------------------------------

        // -----------------------------------------------------------------------------------------
        // Membuat ViewPager (SLIDER)
        TataCaraPagerAdapter tataCaraPagerAdapter = new TataCaraPagerAdapter(getActivity(),getFragmentManager());
        viewPager.setAdapter(tataCaraPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        // -----------------------------------------------------------------------------------------

        return rootView;
    }

}

