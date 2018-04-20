package com.example.dong.kiemsoatxequansu.ui.notebook;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dong.kiemsoatxequansu.R;
import com.example.dong.kiemsoatxequansu.app.App;
import com.example.dong.kiemsoatxequansu.data.importer.ObjectBoxImporter;
import com.example.dong.kiemsoatxequansu.data.model.DetailMatterChild;
import com.example.dong.kiemsoatxequansu.data.model.DetailSubMatterChild;
import com.example.dong.kiemsoatxequansu.data.model.DetailMatterChild_;
import com.example.dong.kiemsoatxequansu.data.model.DetailSubMatterChild_;
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
import com.example.dong.kiemsoatxequansu.utils.Commons;
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

    private Box<DetailSubMatterChild> detailSubMatterChildBox;//Bảng chung liên kết giữa bảng Vehicle và bảng SubMatterChild
    private Box<DetailMatterChild> detailMatterChildBox;//Bảng chung liên kết giữa bảng Vehicle và bảng SubMatterChild

    private Box<SubMatterChild> subMatterChildBox; // Bảng SubMatterChild (bảng con của bảng MatterChild)

    private ImageView ivBack;

    private AppBarLayout appbar;

    private TextView tvTitleNoteBook;

    private Button btnSearch;

    private Spinner spinVehicle, spinMatter, spinMatterChild, spinSubMatterChild;

    private RecyclerView recyclerView;

    SpecificationAdapter specificationAdapter;

    RecyclerView.LayoutManager layout;

    private List<Specification> listSpecification=new ArrayList<>(); // danh sách các chi tiết phụ tùng

    private ArrayList<String> listNameMatterChild = new ArrayList<>(); //danh sách tên công tác con
    private ArrayAdapter<String> adapterMatterChild; //adapter cho spinMatterChild
    private ArrayList<String> listNameSubMatterChild = new ArrayList<>(); //danh sách tên công tác con của con
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
            detailSubMatterChildBox = boxStore.boxFor(DetailSubMatterChild.class);
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

    @SuppressLint("ResourceType")
    private void setUpSpiner() {
        try {
            //vehicle
            String[] listNameVehicle = vehicleBox.query().build().property(Vehicle_.nameVehicle).findStrings();//danh sách tên xe mã hóa từ database
            //Giải mã
            List<String> listNameVehicleDecode=new ArrayList<>();//danh sách tên sau khi giải mã
            for(String valueEncode:listNameVehicle){
                listNameVehicleDecode.add(Commons.decodeString(valueEncode));//giải mã tên
            }
            ArrayAdapter<String> adapterVehicle = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listNameVehicleDecode);
            spinVehicle.setAdapter(adapterVehicle);

            //matter
            String[] listNameMatter = matterBox.query().build().property(Matter_.nameMatter).findStrings();
            //Giải mã
            List<String> listNameMatterDecode=new ArrayList<>();//danh sách tên sau khi giải mã
            for(String valueEncode:listNameMatter){
                listNameMatterDecode.add(Commons.decodeString(valueEncode));//giải mã tên
            }
            ArrayAdapter<String> adapterMatter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listNameMatterDecode);
            spinMatter.setAdapter(adapterMatter);

            //setup spinner matter child
            setUpSpinerMatterChild();

            //specification
           // listSpecification = specificationBox.query().build().find();
            listSpecification=new ArrayList<>();
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
     * Created by Dong on 06-Apr-18
     */
    private void addEvent() {
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Do something after 5s = 5000ms
//
//            }
//        }, 5000);
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
                    if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                        //  Collapsed
                        tvTitleNoteBook.setText(spinVehicle.getSelectedItem().toString());
                        btnSearch.setEnabled(false);
                        tvTitleNoteBook.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_car, 0, 0, 0);
                        btnSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tool_car, 0, 0, 0);

                        //Nếu xe có các vật liệu con
                        if (spinMatterChild.isEnabled()) {
                            btnSearch.setText(spinMatter.getSelectedItem().toString() + " - " + spinMatterChild.getSelectedItem().toString() + " - " + spinSubMatterChild.getSelectedItem().toString());
                        } else {//Nếu xe không có vật liệu con
                            btnSearch.setText(spinMatter.getSelectedItem().toString());

                        }
                    } else {
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
     * Lắng nghe thay đổi  spinner nguyên liệu con để thay đổi spin cháu
     * Created by Dong on 5-Apr-18
     */
    private AdapterView.OnItemSelectedListener onItemMatterChildListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //Lấy xe được chọn
            transactionTime = new TransactionTime(System.currentTimeMillis());
            String nameVehicle = spinVehicle.getSelectedItem().toString();
            Vehicle vehicle = vehicleBox.query().equal(Vehicle_.nameVehicle, Commons.encodeString(nameVehicle)).build().findFirst();

            //Lấy vật liệu con được chọn
            String nameMaterChild = spinMatterChild.getSelectedItem().toString();
            final MatterChild matterChild = matterChildBox.query().equal(MatterChild_.nameChildMatter, Commons.encodeString(nameMaterChild)).build().findFirst();

            //xóa danh sách cũ
            listNameSubMatterChild.clear();

            int[] listIdSubMatterChild = detailSubMatterChildBox.query()
                    .equal(DetailSubMatterChild_.idVehicle, vehicle != null ? vehicle.getIdVehicle() : 0)
                    .build()
                    .property(DetailSubMatterChild_.idSubMatterChild).findInts();

            for (int aListIdSubMatterChild : listIdSubMatterChild) {
                //Tìm subMatterchild theo id và theo idMatterChild
                SubMatterChild subMatterChild = subMatterChildBox.query().equal(SubMatterChild_.idSubMatterChild, aListIdSubMatterChild)
                        .equal(SubMatterChild_.idMatterChild, matterChild != null ? matterChild.getIdChildMatter() : 0)
                        .build().findFirst();
                if (subMatterChild != null) {
                    listNameSubMatterChild.add(subMatterChild.getNameSubMatterChild());
                }

            }


            List<String> listNameSubMatterChildDecode=new ArrayList<>();//danh sách tên sau khi giải mã
            for(String valueEncode:listNameSubMatterChild){
                listNameSubMatterChildDecode.add(Commons.decodeString(valueEncode));//giải mã tên
            }
            adapterSubMatterChild = new ArrayAdapter<>(SoTayActivity.this, android.R.layout.simple_spinner_dropdown_item, listNameSubMatterChildDecode);
            spinSubMatterChild.setAdapter(adapterSubMatterChild);
            transactionTime.setEnd(System.currentTimeMillis());
            Log.d("ObjectBox", "createAllFromJson Task completed in " + transactionTime.getDuration() + "ms");


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
                Vehicle vehicle = vehicleBox.query().equal(Vehicle_.nameVehicle, Commons.encodeString(nameVehicle)).build().findFirst();

                //Lấy tên nguyên vật liệu để tìm id của nguyên vật liệu đó
                String nameMatter = spinMatter.getSelectedItem().toString();
                Matter matter = matterBox.query().equal(Matter_.nameMatter, Commons.encodeString(nameMatter)).build().findFirst();


                List<Specification> data;
                if (spinMatterChild.isEnabled()) {
                    //Lấy tên nguyên vật liệu con để tìm id của nguyên vật liệu con đó
                    String nameMatterChild = spinMatterChild.getSelectedItem().toString();
                    MatterChild matterChild = matterChildBox.query().equal(MatterChild_.nameChildMatter, Commons.encodeString(nameMatterChild)).build().findFirst();

                    //Lấy tên nguyên vật liệu con để tìm id của nguyên vật liệu con đó
                    String nameSubMatterChild = spinSubMatterChild.getSelectedItem().toString();
                    SubMatterChild subMatterChild = subMatterChildBox.query().equal(SubMatterChild_.nameSubMatterChild, Commons.encodeString(nameSubMatterChild)).build().findFirst();
                    data = specificationBox.query()
                            .equal(Specification_.idVehicle, vehicle != null ? vehicle.getIdVehicle() : 0)
                            .equal(Specification_.idMatter, matter != null ? matter.getIdMatter() : 0)
                            .equal(Specification_.idChildMatter, matterChild != null ? matterChild.getIdChildMatter() : 0)
                            .equal(Specification_.idSubMatterChild, subMatterChild != null ? subMatterChild.getIdSubMatterChild() : 0)
                            .build().find();
                } else {
                    data = specificationBox.query()
                            .equal(Specification_.idVehicle, vehicle != null ? vehicle.getIdVehicle() : 0)
                            .equal(Specification_.idMatter, matter != null ? matter.getIdMatter() : 0)
                            .build().find();
                }

                //Kiểm tra dữ liệu không có
                if (data.isEmpty()) {
                    Toast.makeText(SoTayActivity.this, "Không có phụ tùng", Toast.LENGTH_LONG).show();
                } else {
                    //notifidata in recycleview
                    specificationAdapter.swap(data);
                }
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
                //Lấy xe được chọn
                transactionTime = new TransactionTime(System.currentTimeMillis());
                setUpSpinerMatterChild();
                transactionTime.setEnd(System.currentTimeMillis());
                Log.d("ObjectBox", "createAllFromJson Task completed in " + transactionTime.getDuration() + "ms");
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
            if(specificationAdapter!=null) {
                specificationAdapter.notifyDataSetChanged();
            }
            //Lấy xe được chọn
            transactionTime = new TransactionTime(System.currentTimeMillis());
            String nameVehicle = spinVehicle.getSelectedItem().toString();
            Vehicle vehicle = vehicleBox.query().equal(Vehicle_.nameVehicle, Commons.encodeString(nameVehicle)).build().findFirst();

            //Nếu vật liệu con là hiển thị thì cập nhật spin con và spin cháu

            if (spinMatterChild!=null&&spinMatterChild.isEnabled()) {
                //Cập nhật spin cháu
                if(spinMatterChild.getSelectedItem()!=null) {
                    String nameMaterChild = spinMatterChild.getSelectedItem().toString();
                    final MatterChild matterChild = matterChildBox.query().equal(MatterChild_.nameChildMatter, nameMaterChild).build().findFirst();
                    //xóa danh sách cũ
                    listNameSubMatterChild.clear();
                    int[] listIdSubMatterChild = detailSubMatterChildBox.query()
                            .equal(DetailSubMatterChild_.idVehicle, vehicle != null ? vehicle.getIdVehicle() : 0)
                            .build()
                            .property(DetailSubMatterChild_.idSubMatterChild).findInts();
                    for (int aListIdSubMatterChild : listIdSubMatterChild) {
                        //Tìm subMatterchild theo id và theo idMatterChild
                        SubMatterChild subMatterChild = subMatterChildBox.query().equal(SubMatterChild_.idSubMatterChild, aListIdSubMatterChild)
                                .equal(SubMatterChild_.idMatterChild, matterChild != null ? matterChild.getIdChildMatter() : 0)
                                .build().findFirst();
                        if (subMatterChild != null) {
                            listNameSubMatterChild.add(subMatterChild.getNameSubMatterChild());
                        }
                    }
                    adapterSubMatterChild = new ArrayAdapter<>(SoTayActivity.this, android.R.layout.simple_spinner_dropdown_item, listNameSubMatterChild);
                    spinSubMatterChild.setAdapter(adapterSubMatterChild);


                    setUpSpinerMatterChild();

                    transactionTime.setEnd(System.currentTimeMillis());
                    Log.d("ObjectBox", "createAllFromJson Task completed in " + transactionTime.getDuration() + "ms");
                }
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void setUpSpinerMatterChild() {
        try {
            String nameVehicle = spinVehicle.getSelectedItem().toString();//Lấy tên xe dk chọn ở spinner dưới dạng giải mã
            Vehicle vehicle = vehicleBox.query().equal(Vehicle_.nameVehicle, Commons.encodeString(nameVehicle)).build().findFirst();//mã hóa lại tên để tìm xe trong csdl
            String nameMater = spinMatter.getSelectedItem().toString();//Lấy tên nguyên liệu dk chọn ở spinner dưới dạng giải mã
            final Matter matter = matterBox.query().equal(Matter_.nameMatter,  Commons.encodeString(nameMater)).build().findFirst();//mã hóa lại tên để tìm nguyên liệu trong csdl

            //xóa danh sách cũ
            listNameMatterChild.clear();

            int[] listIdMatterChild = detailMatterChildBox.query()
                    .equal(DetailMatterChild_.idVehicle, vehicle != null ? vehicle.getIdVehicle() : 0)
                    .equal(DetailMatterChild_.idMatter, matter != null ? matter.getIdMatter() : 0)
                    .build()
                    .property(DetailMatterChild_.idChildMatter)
                    .findInts();
            for(int aListIdMatterChild:listIdMatterChild){
                //Tìm Matterchild theo id
                MatterChild matterChild = matterChildBox.query()
                        .equal(MatterChild_.idChildMatter, aListIdMatterChild)
                        .build().findFirst();
                if (matterChild != null) {
                    listNameMatterChild.add(matterChild.getNameChildMatter());
                }
            }

            //Giải mã
            List<String> listNameMatterChildDecode=new ArrayList<>();//danh sách tên sau khi giải mã
            for(String valueEncode:listNameMatterChild){
                listNameMatterChildDecode.add(Commons.decodeString(valueEncode));//giải mã tên
            }
            adapterMatterChild = new ArrayAdapter<>(SoTayActivity.this, android.R.layout.simple_spinner_dropdown_item, listNameMatterChildDecode);
            spinMatterChild.setAdapter(adapterMatterChild);
        } catch (Exception e) {
           e.printStackTrace();
        }

    }

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
