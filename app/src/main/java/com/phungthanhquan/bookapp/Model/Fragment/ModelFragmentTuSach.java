package com.phungthanhquan.bookapp.Model.Fragment;

import com.phungthanhquan.bookapp.Object.ItemBook;
import com.phungthanhquan.bookapp.Object.ItemBookCase;

import java.util.ArrayList;
import java.util.List;

public class ModelFragmentTuSach {
    private List<ItemBookCase> itemBookCaseList;

    public List<ItemBookCase> layDsSachTrongTuSach(){
        itemBookCaseList = new ArrayList<>();
        ItemBookCase itemBook1 = new ItemBookCase("Hoa vàng trên đồi cỏ",
                "https://webtruyen.com/public/images/toithayhoavangtrencoxanh1woCMXi6Ln.jpg"
                ,"1"
                ,"Nguyễn Du"
                ,70);
        ItemBookCase itemBook2 = new ItemBookCase("Nhà Giả Kim",
                "https://www.fahasa.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/8/9/8935235213746.jpg",
                "2"
        ,"Lê Văn Thanh",0 );
        ItemBookCase itemBook3 = new ItemBookCase("Quảng gánh lo vui mà sống",
                "https://nhanvan.vn/images/detailed/8/8935086828410.jpg",
                "3","Trần Kay",15);
        ItemBookCase itemBook4 = new ItemBookCase("Khi lỗi thuộc về những vì sao",
                "https://www.fahasa.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/k/h/khi-loi-thuoc-ve-nhung-vi-sao-b.jpg",
                "4","Nguyễn Văn A",16);
        itemBookCaseList.add(itemBook1);
        itemBookCaseList.add(itemBook2);
        itemBookCaseList.add(itemBook3);
        itemBookCaseList.add(itemBook4);
        itemBookCaseList.add(itemBook1);
        itemBookCaseList.add(itemBook2);
        itemBookCaseList.add(itemBook3);
        itemBookCaseList.add(itemBook4);
        itemBookCaseList.add(itemBook1);
        itemBookCaseList.add(itemBook2);
        itemBookCaseList.add(itemBook3);
        itemBookCaseList.add(itemBook4);
        itemBookCaseList.add(itemBook1);
        itemBookCaseList.add(itemBook2);
        itemBookCaseList.add(itemBook3);
        return itemBookCaseList;
    }
}
