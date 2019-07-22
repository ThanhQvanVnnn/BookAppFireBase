package com.phungthanhquan.bookapp.Model.Fragment;

import android.content.Context;

import com.phungthanhquan.bookapp.Model.Room.DbRoomAccess;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.ChuongSach;
import com.phungthanhquan.bookapp.Object.DauTrang;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChuongSachModel {
    private Context context;

    public ChuongSachModel(Context context) {
        this.context = context;
    }

    public interface CallBackChuongSach{
        void layChuongSach(List<ChuongSach> chuongSachList);
    }
    public interface CallBackDauTrang{
        void layDauTrang(List<DauTrang> dauTrangList);
    }
    public interface CallBackLayBookCase{
        void layBookCase(BookCase bookcase);
    }

    public void LayChuongSach(String id,CallBackChuongSach callBackChuongSach){
        DbRoomAccess.getInstance(context).getAllChapterTask(context,id,callBackChuongSach);
    }
    public void LaydauTrang(String id,CallBackDauTrang callBackDauTrang){
        DbRoomAccess.getInstance(context).getAllDauTrangTask(context,id,callBackDauTrang);
    }
    public void LayBookCase(String id,CallBackLayBookCase callBackLayBookCase){
        try {
           BookCase bookCase = DbRoomAccess.getInstance(context).getBookCaseByIDTask(context,id);
           callBackLayBookCase.layBookCase(bookCase);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
