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

import java.util.List;

import io.objectbox.query.Query;
import io.objectbox.rx.RxQuery;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter adapter;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        Query<Post> postBox = BoxManager.getStore().boxFor(Post.class).query().build();

        compositeDisposable.add(Observable.merge(RxQuery.observable(postBox), Remote.syncPosts())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::setPosts)
                .subscribe());
    }

    private void setPosts(List<Post> posts) {
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
