package com.phungthanhquan.bookapp.Presenter.Activity;

import android.app.Dialog;

import com.phungthanhquan.bookapp.Model.Activity.DetailBookModel;
import com.phungthanhquan.bookapp.Object.BinhLuan;
import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.Object.Marketing;
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
      detailBookModel.layChiTietSach(id, new DetailBookModel.CallBackChiTietSach() {
          @Override
          public void LayChiTietSach(Book book) {
              interfaceViewActivityDetailBook.hienThiNoiDungSach(book);
          }
      });
    }

    @Override
    public void xuliHienThiDsDanhGia(String id) {
        detailBookModel.layDanhSachBinhLuan(id, new DetailBookModel.CallBackLayBinhLuan() {
            @Override
            public void LayDSBinhLuan(List<BinhLuan> dsBinhLuan) {
                    interfaceViewActivityDetailBook.hienThiDsDanhGia(dsBinhLuan);
            }
        });
    }

    @Override
    public void xuliHienThidsSachCungTheLoai(String category_id) {
        detailBookModel.layDanhSachCungTheLoai(category_id, new DetailBookModel.CallBackLayCungTheLoai() {
            @Override
            public void LayDSSach(List<Marketing> marketingList) {
                if(marketingList.size()>0) {
                    interfaceViewActivityDetailBook.hienThiDsSachCungTheLoai(marketingList);
                }
            }
        });
    }

    @Override
    public void xuliThemBinhLuan(BinhLuan binhLuan) {
        detailBookModel.ThemBinhLuan(binhLuan, new DetailBookModel.CallBackThemBinhLuan() {
            @Override
            public void ThemBinhLuan(Boolean bo) {
                if(bo){
                    interfaceViewActivityDetailBook.hienThiThemBinhLuan();
                }
            }
        });
    }
}
