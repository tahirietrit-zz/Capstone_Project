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
 * Created by macb on 12/04/16.
 */
public class FeedFragment extends Fragment {
    @Bind(R.id.feed_recyclerview)
    RecyclerView feedRecyclerview;

    private RecyclerView.LayoutManager mLayoutManager;
    FeedAdapter feedAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feed_fragment_layout ,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        System.out.println("on View created");
        feedRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        feedRecyclerview.setLayoutManager(mLayoutManager);

        feedAdapter = new FeedAdapter(getActivity());
        feedRecyclerview.setAdapter(feedAdapter);
    }
}
