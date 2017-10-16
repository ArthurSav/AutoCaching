package com.arthursaveliev.autocaching;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.arthursaveliev.autocaching.api.Remote;
import com.arthursaveliev.autocaching.api.model.Post;
import com.arthursaveliev.autocaching.data.BoxManager;
import com.arthursaveliev.autocaching.ui.PostAdapter;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter adapter;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        //local sync

        //remote sync
        compositeDisposable.add(Remote.syncPosts()
            .doOnNext(this::setPosts)
            .subscribe());
        compositeDisposable.add(Remote.syncUsers()
        .subscribe());
        compositeDisposable.add(Remote.syncUser()
            .subscribe());

    }

    private void setPosts(List<Post> posts){
        if (recyclerView.getAdapter() == null) {
            adapter = new PostAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(adapter);
        }
        adapter.setPosts(posts);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
