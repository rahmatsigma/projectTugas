import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Produk {
    private String nama;
    private double hargaAsli;
    private double persentaseDiskon;

    public Produk(String nama, double hargaAsli, double persentaseDiskon) {
        this.nama = nama;
        this.hargaAsli = hargaAsli;
        this.persentaseDiskon = persentaseDiskon;
    }

    public double hitungHargaDiskon() {
        return hargaAsli - (hargaAsli * persentaseDiskon / 100);
    }

    public void tampilkanInfo() {
        System.out.println("Nama Produk: " + nama);
        System.out.println("Harga Asli: Rp " + hargaAsli);
        System.out.println("Persentase Diskon: " + persentaseDiskon + "%");
        System.out.println("Harga Setelah Diskon: Rp " + hitungHargaDiskon());
    }

    public String getNama() {
        return nama;
    }

    public double getHargaAsli() {
        return hargaAsli;
    }

    public double getPersentaseDiskon() {
        return persentaseDiskon;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHargaAsli(double hargaAsli) {
        this.hargaAsli = hargaAsli;
    }

    public void setPersentaseDiskon(double persentaseDiskon) {
        this.persentaseDiskon = persentaseDiskon;
    }

    public static Produk cariProduk(ArrayList<Produk> daftarProduk, String nama) {
        for (Produk produk : daftarProduk) {
            if (produk.getNama().equalsIgnoreCase(nama)) {
                return produk;
            }
        }
        return null; 
    }

    public static void urutkanProdukBerdasarkanHarga(ArrayList<Produk> daftarProduk) {
        Collections.sort(daftarProduk, new Comparator<Produk>() {
            public int compare(Produk p1, Produk p2) {
                return Double.compare(p1.getHargaAsli(), p2.getHargaAsli());
            }
        });
    }

    public static void main(String[] args) {
        ArrayList<Produk> daftarProduk = new ArrayList<>();

        daftarProduk.add(new Produk("Laptop", 15000000, 10));
        daftarProduk.add(new Produk("Smartphone", 5000000, 15));
        daftarProduk.add(new Produk("Tablet", 3000000, 5));

        System.out.println("Informasi Produk Sebelum Sorting:");
        for (Produk produk : daftarProduk) {
            produk.tampilkanInfo();
            System.out.println("=============================");
        }

        urutkanProdukBerdasarkanHarga(daftarProduk);
        System.out.println("Informasi Produk Setelah Sorting Berdasarkan Harga:");
        for (Produk produk : daftarProduk) {
            produk.tampilkanInfo();
            System.out.println("=============================");
        }

        String namaProdukYangDicari = "Smartphone";
        Produk produkDitemukan = cariProduk(daftarProduk, namaProdukYangDicari);
        if (produkDitemukan != null) {
            System.out.println("Produk Ditemukan:");
            produkDitemukan.tampilkanInfo();
        } else {
            System.out.println("Produk dengan nama " + namaProdukYangDicari + " tidak ditemukan.");
        }
    }
}
