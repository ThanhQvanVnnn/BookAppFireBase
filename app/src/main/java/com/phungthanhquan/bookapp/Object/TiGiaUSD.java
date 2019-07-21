package com.phungthanhquan.bookapp.Object;

public class TiGiaUSD {
    private String type;
    private String imageurl;
    private String muatienmat;
    private String muack;
    private String bantienmat;
    private String banck;

    public TiGiaUSD() {
    }

    public TiGiaUSD(String type, String imageurl, String muatienmat, String muack, String bantienmat, String banck) {
        this.type = type;
        this.imageurl = imageurl;
        this.muatienmat = muatienmat;
        this.muack = muack;
        this.bantienmat = bantienmat;
        this.banck = banck;
    }

    @Override
    public String toString() {
        return "TiGiaUSD{" +
                "type='" + type + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", muatienmat='" + muatienmat + '\'' +
                ", muack='" + muack + '\'' +
                ", bantienmat='" + bantienmat + '\'' +
                ", banck='" + banck + '\'' +
                '}';
    }
}
