package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tahirietrit.ireport.R;

import adapters.FeedAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by macb on 11/05/16.
 */
public class ProfileFragment extends Fragment {
    @Bind(R.id.profile_recyclerview)
    RecyclerView profileRecyclerview;

    private RecyclerView.LayoutManager mLayoutManager;
    FeedAdapter feedAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_layout ,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        profileRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        profileRecyclerview.setLayoutManager(mLayoutManager);

        feedAdapter = new FeedAdapter(getActivity());
        profileRecyclerview.setAdapter(feedAdapter);
    }
}
