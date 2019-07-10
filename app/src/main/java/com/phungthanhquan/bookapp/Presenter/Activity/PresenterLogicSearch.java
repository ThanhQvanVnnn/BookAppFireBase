package com.phungthanhquan.bookapp.Presenter.Activity;

import com.phungthanhquan.bookapp.Model.Activity.SearchModel;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivitySearch;

import java.util.List;

public class PresenterLogicSearch implements InPresenterSearch {
    InterfaceViewActivitySearch interfaceViewActivitySearch;
    SearchModel searchModel;

    public PresenterLogicSearch(InterfaceViewActivitySearch interfaceViewActivitySearch) {
        this.interfaceViewActivitySearch = interfaceViewActivitySearch;
        searchModel = new SearchModel();
    }

    @Override
    public void handlerSearch(String characters) {
        searchModel.getDataSearch(characters, new SearchModel.Callback() {
            @Override
            public void myCallBack(List<Marketing> marketingList) {
                if(marketingList.size()>0) {
                    interfaceViewActivitySearch.searchSuccess(marketingList);
                }else {
                    interfaceViewActivitySearch.searchFail();
                }
            }
        });
    }
}
