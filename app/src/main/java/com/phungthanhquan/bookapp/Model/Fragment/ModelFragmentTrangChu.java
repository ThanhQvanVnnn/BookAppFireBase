package com.phungthanhquan.bookapp.Model.Fragment;

import com.phungthanhquan.bookapp.Object.AlbumBook;
import com.phungthanhquan.bookapp.Object.ItemBook;
import com.phungthanhquan.bookapp.Object.NXB;
import com.phungthanhquan.bookapp.Object.Slider;

import java.util.ArrayList;
import java.util.List;

public class ModelFragmentTrangChu {
    private List<Slider> sliderList ;
    private List<ItemBook> dsSach ;
    private List<NXB> dsNXB;
    private List<AlbumBook> dsAlbum;

    //lấy danh sách slider từ api
    public List<Slider> getDataSlider(){
        sliderList = new ArrayList<>();
        Slider slider1 = new Slider("https://znews-photo.zadn.vn/w660/Uploaded/jobunwj/2015_10_01/hoa_vang_co_xanh.jpg","https://webtruyen.com/public/images/toithayhoavangtrencoxanh1woCMXi6Ln.jpg");
        Slider slider2 = new Slider("https://isachhay.net/wp-content/uploads/2018/04/nha-gia-kim-sach-van-hoc-hay-nen-doc-03.jpg","https://www.fahasa.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/8/9/8935235213746.jpg");
        Slider slider3 = new Slider("https://isachhay.net/wp-content/uploads/2018/11/quang-ganh-lo-di-va-vui-song-review-sach-hay-isachhay.net_.jpg","https://nhanvan.vn/images/detailed/8/8935086828410.jpg");
        Slider slider4 = new Slider("https://vnwriter.net/wp-content/uploads/2017/07/sach-khi-loi-thuoc-ve-nhung-vi-sao-nxb-tre.jpg","https://www.fahasa.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/k/h/khi-loi-thuoc-ve-nhung-vi-sao-b.jpg");
        sliderList.add(slider1);
        sliderList.add(slider2);
        sliderList.add(slider3);
        sliderList.add(slider4);
         return sliderList;
    }

    //lấy ds sách mới
    public List<ItemBook> getDataDsSachMoi(){
        dsSach = new ArrayList<>();
        ItemBook itemBook1 = new ItemBook("Hoa vàng trên đồi cỏ",
                "https://webtruyen.com/public/images/toithayhoavangtrencoxanh1woCMXi6Ln.jpg",
                "15");
        ItemBook itemBook2 = new ItemBook("Nhà Giả Kim",
                "https://www.fahasa.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/8/9/8935235213746.jpg",
                "15");
        ItemBook itemBook3 = new ItemBook("Quảng gánh lo vui mà sống",
                "https://nhanvan.vn/images/detailed/8/8935086828410.jpg",
                "15");
        ItemBook itemBook4 = new ItemBook("Khi lỗi thuộc về những vì sao",
                "https://www.fahasa.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/k/h/khi-loi-thuoc-ve-nhung-vi-sao-b.jpg",
                "15");
        dsSach.add(itemBook1);
        dsSach.add(itemBook2);
        dsSach.add(itemBook3);
        dsSach.add(itemBook4);
        dsSach.add(itemBook1);
        dsSach.add(itemBook2);
        dsSach.add(itemBook3);
        dsSach.add(itemBook4);
        dsSach.add(itemBook1);
        dsSach.add(itemBook2);
        dsSach.add(itemBook3);
        dsSach.add(itemBook4);
        dsSach.add(itemBook1);
        dsSach.add(itemBook2);
        dsSach.add(itemBook3);
        dsSach.add(itemBook4);
        return dsSach;
    }

    //lấy ds sách khuyên đọc
    public List<ItemBook> getDataDsSachKhuyenDoc(){
        dsSach = new ArrayList<>();
        ItemBook itemBook1 = new ItemBook("Hoa vàng trên đồi cỏ",
                "https://webtruyen.com/public/images/toithayhoavangtrencoxanh1woCMXi6Ln.jpg",
                "15");
        ItemBook itemBook2 = new ItemBook("Nhà Giả Kim",
                "https://www.fahasa.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/8/9/8935235213746.jpg",
                "15");
        ItemBook itemBook3 = new ItemBook("Quảng gánh lo vui mà sống",
                "https://nhanvan.vn/images/detailed/8/8935086828410.jpg",
                "15");
        ItemBook itemBook4 = new ItemBook("Khi lỗi thuộc về những vì sao",
                "https://www.fahasa.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/k/h/khi-loi-thuoc-ve-nhung-vi-sao-b.jpg",
                "15");
        dsSach.add(itemBook1);
        dsSach.add(itemBook2);
        dsSach.add(itemBook3);
        dsSach.add(itemBook4);
        dsSach.add(itemBook1);
        dsSach.add(itemBook2);
        dsSach.add(itemBook4);
        dsSach.add(itemBook1);
        dsSach.add(itemBook2);
        return dsSach;
    }

    //lấy ds sách văn học trong nước
    public List<ItemBook> getDataDsSachVanHocTrongNuoc(){
        dsSach = new ArrayList<>();
        ItemBook itemBook1 = new ItemBook("Hoa vàng trên đồi cỏ",
                "https://webtruyen.com/public/images/toithayhoavangtrencoxanh1woCMXi6Ln.jpg",
                "15");
        ItemBook itemBook2 = new ItemBook("Nhà Giả Kim",
                "https://www.fahasa.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/8/9/8935235213746.jpg",
                "15");
        ItemBook itemBook3 = new ItemBook("Quảng gánh lo vui mà sống",
                "https://nhanvan.vn/images/detailed/8/8935086828410.jpg",
                "15");
        ItemBook itemBook4 = new ItemBook("Khi lỗi thuộc về những vì sao",
                "https://www.fahasa.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/k/h/khi-loi-thuoc-ve-nhung-vi-sao-b.jpg",
                "15");
        dsSach.add(itemBook1);
        dsSach.add(itemBook2);
        dsSach.add(itemBook3);
        dsSach.add(itemBook4);
        dsSach.add(itemBook1);
        dsSach.add(itemBook2);
        dsSach.add(itemBook3);
        dsSach.add(itemBook4);
        dsSach.add(itemBook1);
        dsSach.add(itemBook2);
        dsSach.add(itemBook3);
        dsSach.add(itemBook4);
        dsSach.add(itemBook1);
        dsSach.add(itemBook2);
        dsSach.add(itemBook3);
        dsSach.add(itemBook4);
        return dsSach;
    }

    //lấy ds NXB
    public List<NXB> getDataDsNhaXuatBan(){
        dsNXB = new ArrayList<>();
        NXB nxb1 = new NXB("http://static.ybox.vn/2016/04/26/4.jpg","Tiền Phong");
        NXB nxb2 = new NXB("https://www.luatvietphong.vn/Uploads/nxb.jpg","Cây Gõ");
        NXB nxb3 = new NXB("http://nxbthongtintruyenthong.vn/vi/0/Home/Image?url=%2Ffiles%2Fabout%2Flogo_NXB_resize.jpg","Cháu chào bác ạ");
        NXB nxb4 = new NXB("https://cdnmedia.thethaovanhoa.vn/2012/11/04/06/49/randomhousepenguin.jpg","Tuổi trẻ");
        NXB nxb5 = new NXB("https://salt.tikicdn.com/ts/publisher/82/d3/ca/60ce90db68e224cc2900be7ae41db451.png","Doanh Nhân");
        NXB nxb6 = new NXB("http://hieusach.vn/upload/8415/20140123/logo_nxbphunu.jpg","Tri Thức");
        dsNXB.add(nxb1);
        dsNXB.add(nxb2);
        dsNXB.add(nxb3);
        dsNXB.add(nxb4);
        dsNXB.add(nxb5);
        dsNXB.add(nxb6);
        return dsNXB;
    }
    //lấy ds Abum
    public List<AlbumBook> getDataDsAlBum(){
        dsAlbum = new ArrayList<>();
        AlbumBook albumBook1 = new AlbumBook("https://images-na.ssl-images-amazon.com/images/I/51OMI4Jez3L._SX260_.jpg",
                "Game Of Thrones");
        AlbumBook albumBook2 = new AlbumBook("https://images-na.ssl-images-amazon.com/images/I/5177950yDnL._SX357_BO1,204,203,200_.jpg",
                "Sherlock Home");
        AlbumBook albumBook3 = new AlbumBook("https://sachvui.com/cover/2015/tam-quoc-dien-nghia.jpg",
                "Tam Quốc Diễn Nghĩa");
        AlbumBook albumBook4 = new AlbumBook("https://www.fahasa.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/c/o/combo-1.jpg",
                "Harry Potter");
        AlbumBook albumBook5 = new AlbumBook("https://www.sachkhaitam.com/Data/Sites/1/Product/10376/combo-04-cuon-nhan-to-enzyme.png",
                "Nhân tố Enzyme");
        dsAlbum.add(albumBook1);
        dsAlbum.add(albumBook2);
        dsAlbum.add(albumBook3);
        dsAlbum.add(albumBook4);
        dsAlbum.add(albumBook5);
        return dsAlbum;
    }

}
