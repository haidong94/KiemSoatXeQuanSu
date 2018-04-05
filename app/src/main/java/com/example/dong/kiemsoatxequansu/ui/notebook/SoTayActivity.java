package com.example.dong.kiemsoatxequansu.ui.notebook;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dong.kiemsoatxequansu.R;
import com.example.dong.kiemsoatxequansu.app.App;
import com.example.dong.kiemsoatxequansu.data.importer.ObjectBoxImporter;
import com.example.dong.kiemsoatxequansu.data.model.DetailMatterChild;
import com.example.dong.kiemsoatxequansu.data.model.DetailMatterChild_;
import com.example.dong.kiemsoatxequansu.data.model.Matter;
import com.example.dong.kiemsoatxequansu.data.model.MatterChild;
import com.example.dong.kiemsoatxequansu.data.model.MatterChild_;
import com.example.dong.kiemsoatxequansu.data.model.Matter_;
import com.example.dong.kiemsoatxequansu.data.model.Specification;
import com.example.dong.kiemsoatxequansu.data.model.Specification_;
import com.example.dong.kiemsoatxequansu.data.model.SubMatterChild;
import com.example.dong.kiemsoatxequansu.data.model.SubMatterChild_;
import com.example.dong.kiemsoatxequansu.data.model.Vehicle;
import com.example.dong.kiemsoatxequansu.data.model.Vehicle_;
import com.example.dong.kiemsoatxequansu.utils.TransactionTime;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by DONG on 31-Oct-17.
 */

public class SoTayActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 1;

    private BoxStore boxStore;

    private Box<Vehicle> vehicleBox; //bảng xe

    private Box<Matter> matterBox; //bảng nguyên vật liệu

    private Box<MatterChild> matterChildBox; //bảng con của bảng nguyên vật liệu

    private Box<Specification> specificationBox; //Bảng chi tiết phụ tùng

    private Box<DetailMatterChild> detailMatterChildBox;//Bảng chung liên kết giữa bảng Vehicle và bảng SubMatterChild

    private Box<SubMatterChild> subMatterChildBox; // Bảng SubMatterChild (bảng con của bảng MatterChild)

    private ImageView ivBack;

    private AppBarLayout appbar;

    private TextView tvTitleNoteBook;

    private Button btnSearch;

    private Spinner spinVehicle, spinMatter, spinMatterChild,spinSubMatterChild;

    private RecyclerView recyclerView;

    SpecificationAdapter specificationAdapter;

    RecyclerView.LayoutManager layout;

    private List<Specification> listSpecification; // danh sách các chi tiết phụ tùng

    private ArrayList<String> listNameSubMatterChild=new ArrayList<>(); //danh sách tên công tác con
    private ArrayAdapter<String> adapterSubMatterChild; //adapter cho spinSubMatterChild

    private TransactionTime transactionTime; //Thời gian thực

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sotaylaixe);

        checkPermission();
        try {
            boxStore = ((App) getApplication()).getBoxStore();
            vehicleBox = boxStore.boxFor(Vehicle.class);
            matterBox = boxStore.boxFor(Matter.class);
            matterChildBox = boxStore.boxFor(MatterChild.class);
            specificationBox = boxStore.boxFor(Specification.class);
            detailMatterChildBox = boxStore.boxFor(DetailMatterChild.class);
            subMatterChildBox = boxStore.boxFor(SubMatterChild.class);

            ObjectBoxImporter objectBoxImporter = new ObjectBoxImporter(getResources(), SoTayActivity.this);
            objectBoxImporter.importFromJson();

        } catch (Exception e) {
            e.printStackTrace();
        }


        addControl();
        setUpSpiner();
        addEvent();

    }

    private void setUpSpiner() {
        try {
            //vehicle
            String[] listNameVehicle = vehicleBox.query().build().property(Vehicle_.nameVehicle).findStrings();
            ArrayAdapter<String> adapterVehicle = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listNameVehicle);
            spinVehicle.setAdapter(adapterVehicle);

            //matter
            String[] listNameMatter = matterBox.query().build().property(Matter_.nameMatter).findStrings();
            ArrayAdapter<String> adapterMatter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listNameMatter);
            spinMatter.setAdapter(adapterMatter);

            //matter child
            String[] listNameMatterChild = matterChildBox.query().build().property(MatterChild_.nameChildMatter).findStrings();
            ArrayAdapter<String> adapterMatterChild = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listNameMatterChild);
            spinMatterChild.setAdapter(adapterMatterChild);

     //       spinSubMatterChild
//            matter child
  //          int[] listDetailMatterChild = detailMatterChildBox.query().build().property(DetailMatterChild_.idVehicle).findInts();
//            Integer[] what = Arrays.stream( listDetailMatterChild ).boxed().toArray( Integer[]::new );
//            ArrayAdapter<Integer> adapterDetailMatterChild = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, int_numbers);
//            spinSubMatterChild.setAdapter(adapterDetailMatterChild);

            //specification
            listSpecification = specificationBox.query().build().find();
            recyclerView = findViewById(R.id.recyclerview);
            recyclerView.setHasFixedSize(true);
            layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layout);
            specificationAdapter = new SpecificationAdapter(listSpecification, this);
            recyclerView.setAdapter(specificationAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sự kiện các control
     * Created_by hhdong 05/04/2018
     */
    private void addEvent() {
        spinVehicle.setOnItemSelectedListener(onItemListener);
        spinMatter.setOnItemSelectedListener(onItemMatterListener);
        spinMatterChild.setOnItemSelectedListener(onItemMatterChildListener);
        btnSearch.setOnClickListener(clickButtonSearch);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Sự kiên khi cuộn appbar
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                try {
                    if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                    {
                        //  Collapsed
                        tvTitleNoteBook.setText(spinVehicle.getSelectedItem().toString());
                        btnSearch.setText(spinMatter.getSelectedItem().toString()+" - "+spinMatterChild.getSelectedItem().toString()+" - "+spinSubMatterChild.getSelectedItem().toString());
                        btnSearch.setEnabled(false);
                        tvTitleNoteBook.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_car, 0, 0, 0);
                        btnSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tool_car, 0, 0, 0);
                    }
                    else
                    {
                        //Expanded
                        tvTitleNoteBook.setText(getResources().getString(R.string.notebook));
                        btnSearch.setText(getResources().getString(R.string.search));
                        btnSearch.setEnabled(true);
                        tvTitleNoteBook.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        btnSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                } catch (Exception e) {
                   e.printStackTrace();
                }

            }
        });

    }

    /**
     * Lắng nghe thay đổi khi chọn spinner nguyên liệu con
     * Created_by hhdong 05/04/2018
     */
    private  AdapterView.OnItemSelectedListener onItemMatterChildListener=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //Lấy xe được chọn
            transactionTime = new TransactionTime(System.currentTimeMillis());
            String nameVehicle = spinVehicle.getSelectedItem().toString();
            Vehicle vehicle = vehicleBox.query().equal(Vehicle_.nameVehicle, nameVehicle).build().findFirst();

            //Lấy vật liệu con được chọn
            String nameMaterChild = spinMatterChild.getSelectedItem().toString();
            final MatterChild matterChild = matterChildBox.query().equal(MatterChild_.nameChildMatter, nameMaterChild).build().findFirst();

            //xóa danh sách cũ
            listNameSubMatterChild.clear();

            int[] listIdSubMatterChild=detailMatterChildBox.query()
                    .equal(DetailMatterChild_.idVehicle, vehicle != null ? vehicle.getIdVehicle() : 0)
                    .build()
                    .property(DetailMatterChild_.idSubMatterChild).findInts();

            for (int aListIdSubMatterChild : listIdSubMatterChild) {
                //Tìm subMatterchild theo id và theo idMatterChild
                SubMatterChild subMatterChild = subMatterChildBox.query().equal(SubMatterChild_.idSubMatterChild, aListIdSubMatterChild)
                        .equal(SubMatterChild_.idMatterChild, matterChild != null ? matterChild.getIdChildMatter() : 0)
                        .build().findFirst();
                if (subMatterChild != null) {
                    listNameSubMatterChild.add(subMatterChild.getNameSubMatterChild());
                }

            }

            transactionTime.setEnd(System.currentTimeMillis());
            Log.d("ObjectBox", "createAllFromJson Task completed in " + transactionTime.getDuration() + "ms");


            adapterSubMatterChild = new ArrayAdapter<>(SoTayActivity.this, android.R.layout.simple_spinner_dropdown_item, listNameSubMatterChild);
            spinSubMatterChild.setAdapter(adapterSubMatterChild);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /**
     * Sự kiện click tìm kiếm
     * Created_by hhdong 05/04/2018
     */
    private View.OnClickListener clickButtonSearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {
                //Lấy tên xe để tìm id của xe đó
                String nameVehicle = spinVehicle.getSelectedItem().toString();
                Vehicle vehicle = vehicleBox.query().equal(Vehicle_.nameVehicle, nameVehicle).build().findFirst();

                //Lấy tên nguyên vật liệu để tìm id của nguyên vật liệu đó
                String nameMatter = spinMatter.getSelectedItem().toString();
                Matter matter = matterBox.query().equal(Matter_.nameMatter, nameMatter).build().findFirst();

                //Lấy tên nguyên vật liệu con để tìm id của nguyên vật liệu con đó
                String nameMatterChild = spinMatterChild.getSelectedItem().toString();
                MatterChild matterChild = matterChildBox.query().equal(MatterChild_.nameChildMatter, nameMatterChild).build().findFirst();

                List<Specification> data;
                if (spinMatterChild.isEnabled()) {
                    data = specificationBox.query()
                            .equal(Specification_.idVehicle, vehicle != null ? vehicle.getIdVehicle() : 0)
                            .equal(Specification_.idMatter, matter != null ? matter.getIdMatter() : 0)
                            .equal(Specification_.idChildMatter, matterChild != null ? matterChild.getIdChildMatter() : 0)
                            .build().find();
                }else {
                    data = specificationBox.query()
                            .equal(Specification_.idVehicle, vehicle != null ? vehicle.getIdVehicle() : 0)
                            .equal(Specification_.idMatter, matter != null ? matter.getIdMatter() : 0)
                            .build().find();
                }
                //notifidata in recycleview
                specificationAdapter.swap(data);
            } catch (Exception e) {
               e.printStackTrace();
            }

        }
    };

    /**
     * Lắng nghe thay đổi khi chọn spinner nguyên liệu
     * Created_by hhdong 05/04/2018
     */
    private AdapterView.OnItemSelectedListener onItemMatterListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            listSpecification.clear();
            specificationAdapter.notifyDataSetChanged();
            if (position == 0) { //nếu là vị trí 1 (1 là vật liệu chính) thì hiện spinnerMatterChild
                spinMatterChild.setEnabled(true);
                spinSubMatterChild.setEnabled(true);
            } else { //ẩn spinnerMatterChild
                spinMatterChild.setEnabled(false);
                spinSubMatterChild.setEnabled(false);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /**
     * Lắng nghe thay đổi khi chọn spinner Xe
     * Created_by hhdong 05/04/2018
     */
    private AdapterView.OnItemSelectedListener onItemListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            listSpecification.clear();
            specificationAdapter.notifyDataSetChanged();
            //Lấy xe được chọn
            transactionTime = new TransactionTime(System.currentTimeMillis());
            String nameVehicle = spinVehicle.getSelectedItem().toString();
            Vehicle vehicle = vehicleBox.query().equal(Vehicle_.nameVehicle, nameVehicle).build().findFirst();

            //Lấy vật liệu con được chọn
            String nameMaterChild = spinMatterChild.getSelectedItem().toString();
            final MatterChild matterChild = matterChildBox.query().equal(MatterChild_.nameChildMatter, nameMaterChild).build().findFirst();

            //xóa danh sách cũ
            listNameSubMatterChild.clear();

            int[] listIdSubMatterChild=detailMatterChildBox.query()
                    .equal(DetailMatterChild_.idVehicle, vehicle != null ? vehicle.getIdVehicle() : 0)
                    .build()
                    .property(DetailMatterChild_.idSubMatterChild).findInts();

            for (int aListIdSubMatterChild : listIdSubMatterChild) {
                //Tìm subMatterchild theo id và theo idMatterChild
                SubMatterChild subMatterChild = subMatterChildBox.query().equal(SubMatterChild_.idSubMatterChild, aListIdSubMatterChild)
                        .equal(SubMatterChild_.idMatterChild, matterChild != null ? matterChild.getIdChildMatter() : 0)
                        .build().findFirst();
                if (subMatterChild != null) {
                    listNameSubMatterChild.add(subMatterChild.getNameSubMatterChild());
                }

            }

            transactionTime.setEnd(System.currentTimeMillis());
            Log.d("ObjectBox", "createAllFromJson Task completed in " + transactionTime.getDuration() + "ms");


            adapterSubMatterChild = new ArrayAdapter<>(SoTayActivity.this, android.R.layout.simple_spinner_dropdown_item, listNameSubMatterChild);
            spinSubMatterChild.setAdapter(adapterSubMatterChild);

            //  spinMatterChild.setEnabled(false); //ẩn công tác con
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /**
     * Khai báo control
     * Created_by hhdong 05/04/2018
     */
    private void addControl() {
        try {
            ivBack = findViewById(R.id.ivBack);
            appbar = findViewById(R.id.appbar);
            tvTitleNoteBook = findViewById(R.id.tvTitleNoteBook);
            spinVehicle = findViewById(R.id.spinVehicle);
            spinMatter = findViewById(R.id.spinMatter);
            spinMatterChild = findViewById(R.id.spinMatterChild);
            spinSubMatterChild = findViewById(R.id.spinSubMatterChild);
            btnSearch = findViewById(R.id.btnSearch);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Kiểm tra quyền truy cập
     * Created_by hhdong 05/04/2018
     */
    private void checkPermission() {
        try {
            String[] listPermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            boolean isOn = false;

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //   textStatus1.setText("On");
            } else {
                //   textStatus1.setText("Off");
                isOn = true;
            }
            if (isOn) {
                ActivityCompat.requestPermissions(this, listPermission, MY_PERMISSIONS_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
