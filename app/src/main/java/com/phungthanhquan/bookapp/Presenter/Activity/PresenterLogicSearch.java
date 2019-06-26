package com.phungthanhquan.bookapp.Presenter.Activity;

import com.phungthanhquan.bookapp.Model.Activity.ModelActivitySearch;
import com.phungthanhquan.bookapp.Object.ItemBook;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivitySearch;

import java.util.List;

public class PresenterLogicSearch implements InPresenterSearch {
    InterfaceViewActivitySearch interfaceViewActivitySearch;
    ModelActivitySearch modelActivitySearch;

    public PresenterLogicSearch(InterfaceViewActivitySearch interfaceViewActivitySearch) {
        this.interfaceViewActivitySearch = interfaceViewActivitySearch;
        modelActivitySearch = new ModelActivitySearch();
    }

    @Override
    public void xuliTimKiem(String kitu) {
        List<ItemBook> itemBooks = modelActivitySearch.getDataSachSearch(kitu);
        if (itemBooks.size()>0){
            interfaceViewActivitySearch.timkiemsachthanhcong(itemBooks);
        }else {
            interfaceViewActivitySearch.timkiemsachthatbai();
        }
    }
}
