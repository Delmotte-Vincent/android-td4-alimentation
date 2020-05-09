package com.main.exercice2.androidproject.Client;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.main.exercice2.androidproject.CommercantList;
import com.main.exercice2.androidproject.Interfaces.AlertType;
import com.main.exercice2.androidproject.Adapter.CommercantListAdapter;
import com.main.exercice2.androidproject.CommercantObjet;
import com.main.exercice2.androidproject.Interfaces.ICallBack;
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

import com.main.exercice2.androidproject.Interfaces.Constantes;

import static android.content.Context.LOCATION_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.main.exercice2.androidproject.Interfaces.Constantes.REQUEST_GPS;

public class ClientMapFragment extends Fragment implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {
    private MapView map;
    ArrayList<OverlayItem> items;
    ArrayList<CommercantObjet> commercantObjetArrayList;
    ArrayList<CommercantObjet> commercantObjetsShow;
    SearchView searchView;
    ListView listResearch;
    ListView listShow;
    ArrayAdapter adapter;
    ArrayAdapter adapter2;
    private ICallBack callBack;
    IMapController mapController;


    ClientMapFragment() {
    }

    private LocationManager lm;
    private Location currentLocation;
    GeoPoint startPoint = new GeoPoint(43.6520, 7.00517);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_map_client, container, false);
        searchView = rootView.findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        listResearch = rootView.findViewById(R.id.listSearch);
        listResearch.setOnItemClickListener(this);
        listShow = rootView.findViewById(R.id.com_list);
        listShow.setOnItemClickListener(this);
        commercantObjetsShow = new ArrayList<>();
        Configuration.getInstance().load(getActivity().getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()));

        //test
        map = rootView.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        //GeoPoint startPoint = new GeoPoint(43.6520, 7.00517);

        lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (lm != null) {
            if (!isGpsAble(lm)) {
                Toast.makeText(getContext(), "Please open GPS~", Toast.LENGTH_SHORT).show();
                openGPS2();
            }
        }
        //from GPS to get the latest location
        initLocation();

        //every 60 seconds get gps
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 8, mLocationListener);
        }

        //transformer location à geopoint
        mapController = map.getController();
        mapController.setCenter(startPoint);
        mapController.setZoom(18.0);

        items = new ArrayList<>();
        commercantObjetArrayList = new ArrayList<>();
        //CommercantObjet homeCom = new CommercantObjet("home","rallo's home", AlertType.DEFAULT,null,new GeoPoint(43.65020,7.00517));
        // CommercantObjet homeCom = new CommercantObjet("home", "rallo's home","er", AlertType.DEFAULT, null, startPoint);
        //CommercantObjet restoCom = new CommercantObjet("resto", "delice de maman", AlertType.DEFAULT, null, new GeoPoint(startPoint.getLatitude()+0.001,startPoint.getLongitude()));
        //CommercantObjet restoCom = new CommercantObjet("resto", "delice de maman", AlertType.DEFAULT, null, new GeoPoint(43.64950, 7.00517));
        //commercantObjetArrayList.add(homeCom);
        //commercantObjetArrayList.add(restoCom);
        commercantObjetArrayList = CommercantList.getCommercants();
        for (CommercantObjet c : commercantObjetArrayList) {
            items.add(new OverlayItem(c.getTitle(), c.getMessage(), c.getGeoPoint()));
        }
        //OverlayItem home = new OverlayItem("home","rallo's home", new GeoPoint(43.65020,7.00517));
        //Drawable m =home.getMarker(0);
        //items.add(home);


        //items.add(new OverlayItem("resto","delice de maman",new GeoPoint(43.64950,7.00517)));

        /** Mise en place de l'adapteur pour l'array list**/
        adapter = new CommercantListAdapter(this.getContext(), commercantObjetArrayList);
        ((ListView) rootView.findViewById(R.id.listSearch)).setAdapter(adapter);
        adapter2 = new CommercantListAdapter(this.getContext(), commercantObjetsShow);
        listShow.setAdapter(adapter2);

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(getActivity().getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        commercantObjetsShow.clear();
                        commercantObjetsShow.add(commercantObjetArrayList.get(index));
                        listShow.setAdapter(adapter2);
                        listShow.setVisibility(View.VISIBLE);
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {

                        callBack.sendCommercantObjet(commercantObjetArrayList.get(index));
                        return false;
                    }
                });

        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);


        listResearch.setVisibility(View.GONE);
        listShow.setVisibility(View.GONE);
        return rootView;
    }

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // when gps change, update location
            updateShow(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            // when GPS LocationProvider is available，update location
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_GPS);
            }
            updateShow(lm.getLastKnownLocation(provider));
        }

        @Override
        public void onProviderDisabled(String provider) {
            startPoint = new GeoPoint(43.6520, 7.00517);
        }
    };

    //define the function to update location
    private void updateShow(Location location) {
        if (location != null) {
            currentLocation = location;
        } else {
            currentLocation = null;
        }
    }

    //check if GPS permission is already permmitted
    private boolean isGpsAble(LocationManager lm) {
        return lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) ? true : false;
    }

    //打开设置页面让用户自己设置 ouvrir la page de settings
    void openGPS2() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, REQUEST_GPS);
    }

    private void initLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_GPS);

        } else {
            Location lc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            updateShow(lc);

            //every 60 seconds get gps
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 8, mLocationListener);

            startPoint = new GeoPoint(currentLocation);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_GPS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                }
                Location lc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                updateShow(lc);

                //every 60 seconds get gps
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 8, mLocationListener );

                this.startPoint = new GeoPoint(currentLocation);
            }else {
                this.startPoint = new GeoPoint(43.6520, 7.00517);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
        lm.removeUpdates(mLocationListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        listResearch.setVisibility(View.GONE);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(s.equals("")){
            listResearch.setVisibility(View.GONE);

        }
        else {
            listResearch.setVisibility(View.VISIBLE);
        }
        listShow.setVisibility(View.GONE);
        adapter.getFilter().filter(s);
        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callBack = (ICallBack) getActivity();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CommercantObjet commercantObjet =(CommercantObjet) adapterView.getItemAtPosition(i);
        System.out.println(view);
       if(adapterView==listResearch){
           System.out.println(commercantObjet);
           commercantObjetsShow.clear();
           commercantObjetsShow.add(commercantObjet);
           adapter2 = new CommercantListAdapter(this.getContext(),commercantObjetsShow);
           listShow.setAdapter(adapter2);
           listShow.setVisibility(View.VISIBLE);
           if (!searchView.isIconified()) {
               searchView.onActionViewCollapsed();}
           mapController.setCenter(commercantObjet.getGeoPoint());
           mapController.setZoom(19.0);

       }
       if(adapterView==listShow){
           callBack.sendCommercantObjet(commercantObjet);
       }
    }


}
