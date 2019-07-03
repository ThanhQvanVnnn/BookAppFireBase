package com.phungthanhquan.bookapp.Model.Activity;

import com.phungthanhquan.bookapp.Object.BinhLuan;
import com.phungthanhquan.bookapp.Object.Book;

import java.util.ArrayList;
import java.util.List;

public class DetailBookModel {
    private Book books;
    private List<BinhLuan> binhLuanList;

    //lấy sách từ id sách
    public Book getBook(){
        books = new Book(1,"https://webtruyen.com/public/images/toithayhoavangtrencoxanh1woCMXi6Ln.jpg"
                ,"Tôi thấy hoa vàng trên cỏ xanh"
                ,"Phùng Thanh Quân"
                ,"Lao Động"
                ,139
                ,"20-11-1996"
                ,1500000,3
                ,"Sách phác hoạ sự tiến hoá của mật mã từ khi nó xuất hiện cho tới nay và đồng thời là những chứng minh về sự hợp thời của nó.\n" +
                "\n" +
                "“Trong hàng ngàn năm, vua chúa cũng như các tướng lĩnh đều dựa vào mạng lưới thông tin liên lạc hiểu quả để cai trị đất nước và chỉ huy quân đội của mình. Đồng thời, tất cả họ đều ý thức được những hậu quả của việc để lọt thông tin của mình vào tay đối phương, để lộ những bí mật quý giá cho các nước thù địch cũng như hậu quả của sự phản bội cung cấp thông tin sống còn cho các lực lượng đối kháng. Chính nỗi lo sợ bị kẻ thù xem trộm đã thúc đẩy sự ra đời và phát triển của mật mã: đó là những kỹ thuật nhằm che giấu, ngụy trang thông tin, khiến cho chỉ những người cần được nhận mới có thề đọc được.\n" +
                "\n" +
                "Mong muốn giữ bí mật đã khiến các quốc gia thiết lập những cơ quan mật mã, có nhiệm vụ đảm bảo an toàn cho thông tin liên lạc bằng việc phát mình và sử dụng những mật mã tốt nhất có thể được. Trong khi đó, những người phá mã của đối phương cũng lại cố gắng để giải mã và đánh cắp những bí mật. Người giải mã là những nhà “giả kim thuật” về ngôn ngữ, một nhóm người bí ẩn chuyên tìm cách phỏng đoán những từ ngữ có nghĩa từ những ký hiệu vô nghĩa. Lịch sử của mật mã là câu chuyện về cuộc chiến kéo dài hàng thế kỷ giữa người lập mã và người giải mã, cuộc chạy đua vũ khí trí tuệ đã có tác động rất to lớn đến tiến trình của lịch sử.\n" +
                "\n" +
                "Khi viết cuốn Mật mã này, tác giả có hai mục đích chính. Một là nhằm phác họa sự tiến hóa của mật mã. Từ tiến hóa dùng ở đây là hoàn toàn thích hợp vì sự phát triển của mật mã cũng có thể coi là một cuộc đấu tranh tiến hoá. Một mật mã luôn bị người phá mã tấn công. Khi người phá mã đã tìm ra một vũ khí mới để phát hiện điểm yếu của một mật mã thì mật mã đó không còn hữu dụng nữa. Khi đó hoặc nó sẽ bị xoá sổ hoặc nó sẽ được cải tiến thành một loại mật mã mới, mạnh hơn. Đến lượt mình, mật mã mới này chỉ phát triển mạnh mẽ cho tới khi người phá mã lại xác định được điểm yếu của nó, và cứ tiếp tục mãi như vậy. Điều này cũng tương tự như tình huống đối mặt với một giống vi khuẩn gây bệnh chẳng hạn. Vi khuẩn sống, phát triển mạnh và tồn tại cho đến khi bác sĩ tìm ra chất kháng sinh làm lộ ra những điểm yếu của vi khuẩn và tiêu diệt nó. Vi khuẩn buộc phải tiến hoá và lừa lại kháng sinh, và nếu thành công thì chúng sẽ lại phát triển mạnh mẽ và tái xác lập trở lại. Vi khuẩn liên tục bị buộc phải tiến hoá để sống sót trước sự tấn công dữ dội của các loại kháng sinh mới.\n" +
                "\n" +
                "Cuộc chiến liên miên giữa người lập mã và người phá mã đã thúc đẩy hàng loạt những đột phá khoa học đáng kể. Người lập mật mã đã liên tục cố gắng xây dựng những loại mã mạnh hơn bao giờ hết để bảo vệ thông tin, trong khi những người phá mã cũng lại kiên trì tìm ra những phương pháp mạnh hơn nữa để phá vở chúng. Trong những cố gắng nhằm phá vỡ và bảo vệ thông tin bí mật, cả hai phía đã phải huy động nhiều lĩnh vực chuyên môn và công nghệ khác nhau, từ toán học cho tới ngôn ngữ học từ lý thuyết thông tin cho đến lý thuyết lượng tử. Đổi lại, những người lập mã và phá mã cũng đã làm giàu thêm cho những lĩnh vực này và thành quả của họ đã đẩy nhanh tốc độ phát triển cộng nghệ, mà đáng kể nhất là trong lĩnh vực máy tính hiện đại…”"
        ,257);
        return books;
    }

    //lấy danh sách bình luận từ id sách
    public List<BinhLuan> getListBinhLuan(){
        binhLuanList = new ArrayList<>();
        BinhLuan binhLuan1 = new BinhLuan("1"
                ,"http://thuthuat123.com/uploads/2018/01/27/anh-dai-dien-dep-nhat-39_093007.jpg"
        ,"Nguyễn văn A"
        ,4
        ,"1/1/1578"
        ,"Sách hay quá hà");
        BinhLuan binhLuan2 = new BinhLuan("2"
                ,"http://thuthuat123.com/uploads/2018/01/27/anh-dai-dien-dep-nhat-36_092934.jpg"
                ,"Lê Thị Lèo"
                ,1
                ,"1/1/2102"
                ,"Quá dở");
        BinhLuan binhLuan3 = new BinhLuan("3"
                ,"https://vinagamemobile.com/wp-content/uploads/2018/04/avatar-facebook-dep.jpg"
                ,"ken"
                ,3
                ,"30/4/1863"
                ,"Cũng tạm ổn");
        BinhLuan binhLuan4 = new BinhLuan("4"
                ,"http://thuthuat123.com/uploads/2018/01/27/anh-dai-dien-dep-nhat-70_095736.jpg"
                ,"Nguyễn Thị"
                ,5
                ,"6/3/2019"
                ,"Nội dung sắc sảo, qqqqqqqqqqqqqqqqqqqqqqqqqqqqqhgiufgdaoufguigsfiugouagdougsouafgiusagfiugfuqsgdiuagfiyshgiyfdiyasfsiyFIYSASIYDTUIASGDIYSATDIYASFDYASFIYGIqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        binhLuanList.add(binhLuan1);
        binhLuanList.add(binhLuan2);
        binhLuanList.add(binhLuan3);
        binhLuanList.add(binhLuan4);
        binhLuanList.add(binhLuan1);
        binhLuanList.add(binhLuan2);
        binhLuanList.add(binhLuan3);
        binhLuanList.add(binhLuan4);
        return binhLuanList;
    }
}
