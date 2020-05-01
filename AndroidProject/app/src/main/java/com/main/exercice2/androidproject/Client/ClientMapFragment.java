package com.main.exercice2.androidproject.Client;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.main.exercice2.androidproject.AlertType;
import com.main.exercice2.androidproject.CommercantListAdapter;
import com.main.exercice2.androidproject.CommercantObjet;
import com.main.exercice2.androidproject.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.Objects;

public class ClientMapFragment extends Fragment implements SearchView.OnQueryTextListener {
    private MapView map ;
    ArrayList<OverlayItem> items;
    ArrayList<CommercantObjet> commercantObjetArrayList;
    SearchView searchView;
    ListView listView;
    ArrayAdapter adapter;
    ClientMapFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_map_client,container,false);
        searchView = rootView.findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        listView = rootView.findViewById(R.id.listSearch);
        Configuration.getInstance().load(getActivity().getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()));

        //test
        map= rootView.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        GeoPoint startPoint = new GeoPoint(43.6520,7.00517);
        IMapController mapController = map.getController();
        mapController.setCenter(startPoint);
        mapController.setZoom(18.0);

        items = new ArrayList<>();
        commercantObjetArrayList=new ArrayList<>();
        CommercantObjet homeCom = new CommercantObjet("home","rallo's home", AlertType.DEFAULT,null,new GeoPoint(43.65020,7.00517));
        CommercantObjet restoCom = new CommercantObjet("resto","delice de maman",AlertType.DEFAULT,null,new GeoPoint(43.64950,7.00517));
        commercantObjetArrayList.add(homeCom);
        commercantObjetArrayList.add(restoCom);
        for(CommercantObjet c : commercantObjetArrayList){
            items.add(new OverlayItem(c.getTitle(),c.getMessage(),c.getGeoPoint()));
        }
        //OverlayItem home = new OverlayItem("home","rallo's home", new GeoPoint(43.65020,7.00517));
        //Drawable m =home.getMarker(0);
        //items.add(home);


        //items.add(new OverlayItem("resto","delice de maman",new GeoPoint(43.64950,7.00517)));

        /** Mise en place de l'adapteur pour l'array list**/

        adapter =new CommercantListAdapter(this.getContext(),commercantObjetArrayList);
        ((ListView)rootView.findViewById(R.id.listSearch)).setAdapter(adapter);

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(getActivity().getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        return false;
                    }
                });

        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);

        listView.setVisibility(View.GONE);
        return rootView;
    }


    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        listView.setVisibility(View.GONE);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(s.equals("")){
            listView.setVisibility(View.GONE);
        }
        else {
            listView.setVisibility(View.VISIBLE);
        }
        adapter.getFilter().filter(s);
        return true;
    }
}
