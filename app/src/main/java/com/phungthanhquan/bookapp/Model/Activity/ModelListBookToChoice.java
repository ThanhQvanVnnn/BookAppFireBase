package com.phungthanhquan.bookapp.Model.Activity;

import com.phungthanhquan.bookapp.Object.ItemBook;

import java.util.ArrayList;
import java.util.List;

public class ModelListBookToChoice {

    private List<ItemBook> itemBooks;

    public List<ItemBook> getdsSach(){
        itemBooks = new ArrayList<>();
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
        itemBooks.add(itemBook1);
        itemBooks.add(itemBook2);
        itemBooks.add(itemBook3);
        itemBooks.add(itemBook4);
        itemBooks.add(itemBook1);
        itemBooks.add(itemBook2);
        itemBooks.add(itemBook3);
        itemBooks.add(itemBook4);
        itemBooks.add(itemBook1);
        itemBooks.add(itemBook2);
        itemBooks.add(itemBook3);
        itemBooks.add(itemBook4);
        return itemBooks;
    }
}
