package com.skylightapp.Classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Adapters.ProductSpecAdapter;
import com.skylightapp.R;

import java.util.List;

public class ProductSpecificationFragment extends Fragment {


    public ProductSpecificationFragment() {
        // Required empty public constructor
    }

    private RecyclerView productSpecificationRecyclerView;
    public List<ProductSpecModel> productSpecificationModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_spec, container, false);
        productSpecificationRecyclerView = view.findViewById(R.id.product_specification_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productSpecificationRecyclerView.setLayoutManager(linearLayoutManager);



        /*productSpecificationModelList.add(new ProductSpecificationModel(0, "General"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(0, "Display"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(0, "General"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(0, "Display"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(0, "General"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(0, "Display"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(0, "General"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(0, "Display"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "No Technical Specification", "Aashirvaad Sugar Control Aata"));*/
        ProductSpecAdapter productSpecificationAdapter = new ProductSpecAdapter(productSpecificationModelList);
        productSpecificationRecyclerView.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();
        return view;
    }
}
