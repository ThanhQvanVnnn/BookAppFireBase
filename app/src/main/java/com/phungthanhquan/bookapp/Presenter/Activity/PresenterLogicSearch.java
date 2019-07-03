package com.phungthanhquan.bookapp.Presenter.Activity;

import com.phungthanhquan.bookapp.Model.Activity.SearchModel;
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
//        List<ItemBook> itemBooks = searchModel.getDataSearch(characters);
//        if (itemBooks.size()>0){
//            interfaceViewActivitySearch.searchSuccess(itemBooks);
//        }else {
//            interfaceViewActivitySearch.searchFail();
//        }
    }
}
