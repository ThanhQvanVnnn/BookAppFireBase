package com.phungthanhquan.bookapp.View.InterfaceView;



import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.ChuongSach;
import com.phungthanhquan.bookapp.Object.DauTrang;
import com.phungthanhquan.bookapp.Object.Marketing;

import java.util.List;

public interface InterfaceViewActivityRead {
     void layChuongSach(List<ChuongSach> chuongSachList);
     void layDauTrang(List<DauTrang> dauTrangListReturn);
     void layBookCase(BookCase bookCase);
}
