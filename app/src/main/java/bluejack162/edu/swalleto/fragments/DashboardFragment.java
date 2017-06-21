package bluejack162.edu.swalleto.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import bluejack162.edu.swalleto.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final TextView tvBalanceMoney;

        tvBalanceMoney = (TextView) v.findViewById(R.id.tvBalanceMoney);
        
        tvBalanceMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), tvBalanceMoney.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}
