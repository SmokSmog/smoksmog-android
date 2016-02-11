package com.antyzero.smoksmog.ui.screen.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.ui.BaseDragonActivity;
import com.antyzero.smoksmog.ui.screen.ActivityModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import pl.malopolska.smoksmog.model.Station;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class OrderActivity extends BaseDragonActivity {

    @Bind( R.id.recyclerView )
    RecyclerView recyclerView;

    private List<Station> stationList = new ArrayList<>();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_order );

        SmokSmogApplication.get( this )
                .getAppComponent()
                .plus( new ActivityModule( this ) )
                .inject( this );

        recyclerView.setLayoutManager( new LinearLayoutManager( this, VERTICAL, false ) );
        recyclerView.setAdapter( new OrderAdapter(stationList) );
    }

    public static void start( Context context ) {
        context.startActivity( new Intent( context, OrderActivity.class ) );
    }
}
