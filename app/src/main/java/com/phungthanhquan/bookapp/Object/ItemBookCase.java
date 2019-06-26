package com.phungthanhquan.bookapp.Object;

public class ItemBookCase extends ItemBook {

    private float phantramdoc;

    public ItemBookCase(String title, String urlImage, String bookID, String tenTacGia, float phantramdoc) {
        super(title, urlImage, bookID, tenTacGia);
        this.phantramdoc = phantramdoc;
    }

    public float getPhantramdoc() {
        return phantramdoc;
    }

    public void setPhantramdoc(float phantramdoc) {
        this.phantramdoc = phantramdoc;
    }
}
