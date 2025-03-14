import java.util.ArrayList;
import java.util.Scanner;

class Buku {
    String judul;
    String penulis;
    boolean tersedia;

    Buku(String judul, String penulis) {
        this.judul = judul;
        this.penulis = penulis;
        this.tersedia = true;
    }
}

abstract class User {
    String nama;
    User(String nama) {
        this.nama = nama;
    }
    abstract void menu();
}

class Admin extends User {
    Admin(String nama) {
        super(nama);
    }
    
    @Override
    void menu() {
        while (true) {
            System.out.println("\n1. Tambah Buku");
            System.out.println("2. Hapus Buku");
            System.out.println("3. Cari Buku");
            System.out.println("4. Lihat Daftar Buku");
            System.out.println("5. Ganti Peran");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu: ");
            
            int pilihan = Perpustakaan.getInputAngka();
            
            switch (pilihan) {
                case 1: Perpustakaan.tambahBuku(); break;
                case 2: Perpustakaan.hapusBuku(); break;
                case 3: Perpustakaan.cariBuku(); break;
                case 4: Perpustakaan.lihatDaftarBuku(); break;
                case 5: return;
                case 6: System.exit(0);
                default: System.out.println("Pilihan tidak valid. Coba lagi.");
            }
        }
    }
}

class Member extends User {
    Member(String nama) {
        super(nama);
    }
    
    @Override
    void menu() {
        while (true) {
            System.out.println("\n1. Lihat Buku");
            System.out.println("2. Pinjam Buku");
            System.out.println("3. Kembalikan Buku");
            System.out.println("4. Ganti Peran");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            
            int pilihan = Perpustakaan.getInputAngka();
            
            switch (pilihan) {
                case 1: Perpustakaan.lihatDaftarBuku(); break;
                case 2: Perpustakaan.pinjamBuku(); break;
                case 3: Perpustakaan.kembalikanBuku(); break;
                case 4: return;
                case 5: System.exit(0);
                default: System.out.println("Pilihan tidak valid. Coba lagi.");
            }
        }
    }
}

public class Perpustakaan {
    static ArrayList<Buku> daftarBuku = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        while (true) {
            System.out.print("Masukkan nama Anda: ");
            String nama = scanner.nextLine();
            
            User user = null;
            while (user == null) {
                System.out.print("Masuk sebagai Admin atau Member? ");
                String peran = scanner.nextLine().trim().toLowerCase();
                if (peran.equals("admin")) {
                    user = new Admin(nama);
                } else if (peran.equals("member")) {
                    user = new Member(nama);
                } else {
                    System.out.println("Input salah! Masukkan 'Admin' atau 'Member'.");
                }
            }
            user.menu();
        }
    }
    
    static void tambahBuku() {
        while (true) {
            System.out.print("Masukkan judul buku: ");
            String judul = scanner.nextLine();
            System.out.print("Masukkan penulis buku: ");
            String penulis = scanner.nextLine();
            daftarBuku.add(new Buku(judul, penulis));
            System.out.println("Buku berhasil ditambahkan!");
            
            System.out.print("Tambah buku lagi? (y/n): ");
            if (!scanner.nextLine().equalsIgnoreCase("y")) break;
        }
    }
    
    static void hapusBuku() {
        if (daftarBuku.isEmpty()) {
            System.out.println("Tidak ada buku di perpustakaan.");
            return;
        }
        lihatDaftarBuku();
        System.out.print("Masukkan nomor buku yang ingin dihapus: ");
        int index = getInputAngka() - 1;
        if (index >= 0 && index < daftarBuku.size()) {
            daftarBuku.remove(index);
            System.out.println("Buku berhasil dihapus.");
        } else {
            System.out.println("Nomor buku tidak valid.");
        }
    }
    
    static void cariBuku() {
        System.out.print("Masukkan judul buku yang dicari: ");
        String cari = scanner.nextLine();
        for (Buku buku : daftarBuku) {
            if (buku.judul.equalsIgnoreCase(cari)) {
                System.out.println("Buku ditemukan: " + buku.judul + " oleh " + buku.penulis);
                return;
            }
        }
        System.out.println("Buku tidak ditemukan.");
    }
    
    static void lihatDaftarBuku() {
        if (daftarBuku.isEmpty()) {
            System.out.println("Tidak ada buku di perpustakaan.");
            return;
        }
        for (int i = 0; i < daftarBuku.size(); i++) {
            Buku buku = daftarBuku.get(i);
            System.out.println((i + 1) + ". " + buku.judul + (buku.tersedia ? " (Tersedia)" : " (Dipinjam)"));
        }
    }
    
    static void pinjamBuku() {
        lihatDaftarBuku();
        System.out.print("Masukkan nomor buku yang ingin dipinjam: ");
        int index = getInputAngka() - 1;
        if (index >= 0 && index < daftarBuku.size() && daftarBuku.get(index).tersedia) {
            daftarBuku.get(index).tersedia = false;
            System.out.println("Buku berhasil dipinjam.");
        } else {
            System.out.println("Nomor buku tidak valid atau sudah dipinjam.");
        }
    }
    
    static void kembalikanBuku() {
        lihatDaftarBuku();
        System.out.print("Masukkan nomor buku yang ingin dikembalikan: ");
        int index = getInputAngka() - 1;
        if (index >= 0 && index < daftarBuku.size() && !daftarBuku.get(index).tersedia) {
            daftarBuku.get(index).tersedia = true;
            System.out.println("Buku berhasil dikembalikan.");
        } else {
            System.out.println("Nomor buku tidak valid atau buku belum dipinjam.");
        }
    }
    
    static int getInputAngka() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input salah! Masukkan angka yang valid.");
            }
        }
    }
}