package com.example.venkatanarayana.apptask.ui;


import android.os.Bundle;
import android.view.View;

import com.example.venkatanarayana.apptask.R;
import com.example.venkatanarayana.apptask.databinding.ActivityPendingRequestsBinding;
import com.example.venkatanarayana.apptask.model.Resource;
import com.example.venkatanarayana.apptask.room.PullRequestEntity;
import com.example.venkatanarayana.apptask.viewmodel.PullRequestsViewModel;

import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {
    private PullRequestsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_requests);
        setupBindings(savedInstanceState);
    }

    private void setupBindings(Bundle savedInstanceState) {
        ActivityPendingRequestsBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_pending_requests);
        viewModel = ViewModelProviders.of(this).get(PullRequestsViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        activityBinding.setModel(viewModel);
        setupListUpdate();

    }

    private void setupListUpdate() {
//        viewModel.loading.set(View.VISIBLE);
//        viewModel.fetchList();
        viewModel.getPullRequests().observe(MainActivity.this, new Observer<Resource<List<PullRequestEntity>>>() {
            @Override
            public void onChanged(Resource<List<PullRequestEntity>> pullRequests) {
                if (pullRequests.getStatus() != Resource.Status.LOADING)
                    viewModel.loading.set(View.GONE);
                if (pullRequests.getStatus() == Resource.Status.FAILURE || pullRequests.getValue().size() == 0) {
                    viewModel.showEmpty.set(View.VISIBLE);
                    viewModel.setPullRequestsInAdapter(Collections.emptyList());
                } else {
                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setPullRequestsInAdapter(pullRequests.getValue());
                }
            }
        });
    }

}
