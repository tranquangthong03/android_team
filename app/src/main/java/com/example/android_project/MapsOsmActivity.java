package com.example.android_project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsOsmActivity extends AppCompatActivity {

    private MapView map;
    private Button btnConfirm;
    private LocationManager locationManager;
    private String selectedAddress = ""; // Biến lưu địa chỉ đã chọn
    private boolean isFirstLocate = true; // Cờ để chỉ zoom lần đầu tiên

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Cấu hình User-Agent cho OSM (QUAN TRỌNG: Không có dòng này map không load)
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_maps_osm);

        // 2. Ánh xạ View
        map = findViewById(R.id.map);
        btnConfirm = findViewById(R.id.btn_confirm_location);

        // 3. Cài đặt Map
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.getController().setZoom(18.0);

        // 4. Kiểm tra quyền GPS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            setupLocation();
        }

        // 5. Lắng nghe sự kiện kéo bản đồ (để lấy địa chỉ ở tâm màn hình)
        map.addMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                // Khi người dùng kéo map, lấy tọa độ tâm
                GeoPoint center = (GeoPoint) map.getMapCenter();
                getAddressFromLatLon(center.getLatitude(), center.getLongitude());
                return true;
            }

            @Override
            public boolean onZoom(ZoomEvent event) { return false; }
        });

        // 6. Xử lý nút Xác nhận
        btnConfirm.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("new_address", selectedAddress); // Trả địa chỉ về Fragment
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void setupLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            // Cách 1: Thử lấy vị trí cuối cùng được lưu (Nhanh nhất)
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (lastKnownLocation != null) {
                updateMapLocation(lastKnownLocation);
            }

            // Cách 2: Lắng nghe GPS (Vệ tinh - Chính xác nhưng chậm)
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
            }

            // Cách 3: Lắng nghe Network (Wifi/3G - Kém chính xác nhưng rất nhanh)
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, locationListener);
            }

        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi quyền truy cập vị trí", Toast.LENGTH_SHORT).show();
        }
    }

    // Tách Listener ra thành biến riêng cho gọn
    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (isFirstLocate) {
                updateMapLocation(location);
                isFirstLocate = false;
            }
        }
    };

    // Hàm cập nhật map (được gọi ở trên)
    private void updateMapLocation(Location location) {
        GeoPoint myPos = new GeoPoint(location.getLatitude(), location.getLongitude());
        map.getController().animateTo(myPos); // Dùng animate cho mượt
        getAddressFromLatLon(location.getLatitude(), location.getLongitude());
    }

    // Hàm chuyển đổi Tọa độ -> Tên đường (Reverse Geocoding)
    private void getAddressFromLatLon(double lat, double lon) {
        // Chạy trên luồng phụ để không đơ giao diện
        new Thread(() -> {
            Geocoder geocoder = new Geocoder(MapsOsmActivity.this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    String addressLine = addresses.get(0).getAddressLine(0);

                    // Cập nhật giao diện (phải run on UI thread)
                    runOnUiThread(() -> {
                        selectedAddress = addressLine;
                        btnConfirm.setText("Chọn: " + (addressLine.length() > 25 ? addressLine.substring(0, 25) + "..." : addressLine));
                        btnConfirm.setEnabled(true);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> btnConfirm.setText("Không lấy được tên đường"));
            }
        }).start();
    }

    // Xử lý kết quả xin quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupLocation();
        } else {
            Toast.makeText(this, "Cần quyền vị trí để dùng bản đồ!", Toast.LENGTH_SHORT).show();
        }
    }
}