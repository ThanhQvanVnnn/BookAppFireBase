package com.phungthanhquan.bookapp.Model.Fragment;

import com.phungthanhquan.bookapp.Object.DanhMuc;

import java.util.ArrayList;
import java.util.List;

public class ModelFragmentDanhMuc {
    private List<DanhMuc> danhMucList;
    public List<DanhMuc> getDanhMuc(){
        danhMucList = new ArrayList<>();
        DanhMuc danhMuc1 = new DanhMuc("1","http://giaynangchieucao.com/blog/upload/images/goc/product/100-cach-lam-lang-man-trong-tinh-yeu%20(4).jpg",
                "Lãng Mạn");
        DanhMuc danhMuc2 = new DanhMuc("2","http://truyenngontinh18.com/upload/icon/20180502/truyen-gon-tinh-nguoc-1.jpg",
                "Ngôn Tình");
        DanhMuc danhMuc3 = new DanhMuc("3","http://static.ybox.vn/2018/2/8/5b40f8c2-0ce4-11e8-b50e-2e995a9a3302.png",
                "Tiểu thuyết tình yêu");
        DanhMuc danhMuc4 = new DanhMuc("4","https://vnwriter.net/wp-content/uploads/2017/07/truyen-trinh-tham-hay.jpg",
                "Trinh thám/Kinh dị");
        DanhMuc danhMuc5 = new DanhMuc("5","http://www.bca.org.vn/wp-content/uploads/2018/10/hop-dong-hon-nhan-co-dau-14-tuoi.jpg",
                "Truyện tuổi teen");
        DanhMuc danhMuc6 = new DanhMuc("6","http://300b5338.vws.vegacdn.vn/image/img.news/0/0/0/733.jpg?v=2&w=480&h=295&nocache=1",
                "Văn học kinh điển");
        DanhMuc danhMuc7 = new DanhMuc("7","http://genknews.genkcdn.vn/thumb_w/660/2018/8/24/k1gtwgz55scbpnp629km-1535092943235390664980.jpg",
                "Viển tưởng, thần thoại");
        danhMucList.add(danhMuc1);
        danhMucList.add(danhMuc2);
        danhMucList.add(danhMuc3);
        danhMucList.add(danhMuc4);
        danhMucList.add(danhMuc5);
        danhMucList.add(danhMuc6);
        danhMucList.add(danhMuc7);
        danhMucList.add(danhMuc1);
        danhMucList.add(danhMuc2);
        danhMucList.add(danhMuc3);
        danhMucList.add(danhMuc4);
        danhMucList.add(danhMuc5);
        danhMucList.add(danhMuc6);
        danhMucList.add(danhMuc7);
        return danhMucList;
    }
}
