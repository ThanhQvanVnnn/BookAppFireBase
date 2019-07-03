package com.phungthanhquan.bookapp.Presenter.Activity;

import com.phungthanhquan.bookapp.Model.Activity.DetailBookModel;
import com.phungthanhquan.bookapp.Object.BinhLuan;
import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityDetailBook;

import java.util.List;

public class PresenterBookDetail implements InPresenterBookDetail {
    InterfaceViewActivityDetailBook interfaceViewActivityDetailBook;
    DetailBookModel detailBookModel;

    public PresenterBookDetail(InterfaceViewActivityDetailBook interfaceViewActivityDetailBook) {
        this.interfaceViewActivityDetailBook = interfaceViewActivityDetailBook;
        detailBookModel = new DetailBookModel();
    }


    @Override
    public void xuliHienThiSach(String id) {
        Book book = detailBookModel.getBook();
        if(book!=null)
        interfaceViewActivityDetailBook.hienThiNoiDungSach(book);
    }

    @Override
    public void xuliHienThiDsDanhGia(String id) {
        List<BinhLuan> binhLuans = detailBookModel.getListBinhLuan();
        if(binhLuans.size()!=0){
            interfaceViewActivityDetailBook.hienThiDsDanhGia(binhLuans);
        }
    }
}
