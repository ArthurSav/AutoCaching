package com.arthursaveliev.autocaching;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arthursaveliev.autocaching.api.Remote;
import com.arthursaveliev.autocaching.api.cache.Cacheable;
import com.arthursaveliev.autocaching.api.model.Post;
import com.arthursaveliev.autocaching.ui.PostAdapter;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter adapter;

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        //local sync

        //remote sync
        disposable = Remote.syncPosts()
                .doOnNext(this::setPosts)
                .subscribe();
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
        disposable.dispose();
    }
}
