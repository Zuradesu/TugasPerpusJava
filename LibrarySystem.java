import java.util.Scanner;
import java.util.ArrayList;

// Parent class User
class User {
    protected String username;
    protected String password;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}

// Class Buku
class Buku {
    String judul;
    String pengarang;
    boolean tersedia;
    
    public Buku(String judul, String pengarang) {
        this.judul = judul;
        this.pengarang = pengarang;
        this.tersedia = true;
    }
}

// Child class Admin
class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }
    
    public void tambahBuku(ArrayList<Buku> daftarBuku, String judul, String pengarang) {
        daftarBuku.add(new Buku(judul, pengarang));
        System.out.println("Buku berhasil ditambahkan!");
    }
    
    public void hapusBuku(ArrayList<Buku> daftarBuku, String judul) {
        daftarBuku.removeIf(b -> b.judul.equalsIgnoreCase(judul));
        System.out.println("Buku berhasil dihapus!");
    }
}

// Child class Member
class Member extends User {
    public Member(String username, String password) {
        super(username, password);
    }
    
    public void pinjamBuku(ArrayList<Buku> daftarBuku, String judul) {
        for (Buku b : daftarBuku) {
            if (b.judul.equalsIgnoreCase(judul) && b.tersedia) {
                b.tersedia = false;
                System.out.println("Buku berhasil dipinjam!");
                return;
            }
        }
        System.out.println("Buku tidak tersedia!");
    }
}

// Main Class
public class LibrarySystem {
    static ArrayList<Buku> daftarBuku = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        Admin admin = new Admin("admin", "admin123");
        Member member = new Member("user", "user123");
        
        daftarBuku.add(new Buku("Java Programming", "James Gosling"));
        daftarBuku.add(new Buku("Clean Code", "Robert C. Martin"));
        
        System.out.print("Masukkan username: ");
        String username = scanner.next();
        System.out.print("Masukkan password: ");
        String password = scanner.next();
        
        if (admin.login(username, password)) {
            menuAdmin(admin);
        } else if (member.login(username, password)) {
            menuMember(member);
        } else {
            System.out.println("Login gagal!");
        }
    }
    
    static void menuAdmin(Admin admin) {
        while (true) {
            System.out.println("\n1. Lihat Daftar Buku\n2. Cari Buku\n3. Tambah Buku\n4. Hapus Buku\n5. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();
    
            switch (pilihan) {
                case 1:
                    System.out.println("\n=== Daftar Buku ===");
                    for (Buku b : daftarBuku) {
                        System.out.println(b.judul + " oleh " + b.pengarang + " | " + (b.tersedia ? "Tersedia" : "Dipinjam"));
                    }
                    break;
                case 2:
                    System.out.print("Masukkan judul buku yang dicari: ");
                    String cariJudul = scanner.nextLine();
                    boolean ditemukan = false;
                    for (Buku b : daftarBuku) {
                        if (b.judul.equalsIgnoreCase(cariJudul)) {
                            System.out.println("Buku ditemukan: " + b.judul + " oleh " + b.pengarang + " | " + (b.tersedia ? "Tersedia" : "Dipinjam"));
                            ditemukan = true;
                            break;
                        }
                    }
                    if (!ditemukan) {
                        System.out.println("Buku tidak ditemukan.");
                    }
                    break;
                case 3:
                    System.out.print("Judul: ");
                    String judul = scanner.nextLine();
                    System.out.print("Pengarang: ");
                    String pengarang = scanner.nextLine();
                    admin.tambahBuku(daftarBuku, judul, pengarang);
                    System.out.println("Buku berhasil ditambahkan!");
                    break;
                case 4:
                    System.out.print("Judul buku yang akan dihapus: ");
                    String hapusJudul = scanner.nextLine();
                    admin.hapusBuku(daftarBuku, hapusJudul);
                    System.out.println("Buku berhasil dihapus!");
                    break;
                case 5:
                    return; // Keluar dari menu admin
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }
    
    
    static void menuMember(Member member) {
        while (true) {
            System.out.println("\n1. Lihat Daftar Buku\n2. Cari Buku\n3. Pinjam Buku\n4. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();
    
            switch (pilihan) {
                case 1:
                    System.out.println("\n=== Daftar Buku ===");
                    for (Buku b : daftarBuku) {
                        System.out.println(b.judul + " oleh " + b.pengarang + " | " + (b.tersedia ? "Tersedia" : "Dipinjam"));
                    }
                    break;
                case 2:
                    System.out.print("Masukkan judul buku yang dicari: ");
                    String cariJudul = scanner.nextLine();
                    boolean ditemukan = false;
                    for (Buku b : daftarBuku) {
                        if (b.judul.equalsIgnoreCase(cariJudul)) {
                            System.out.println("Buku ditemukan: " + b.judul + " oleh " + b.pengarang + " | " + (b.tersedia ? "Tersedia" : "Dipinjam"));
                            ditemukan = true;
                            break;
                        }
                    }
                    if (!ditemukan) {
                        System.out.println("Buku tidak ditemukan.");
                    }
                    break;
                case 3:
                    System.out.print("Judul buku yang akan dipinjam: ");
                    String judul = scanner.nextLine();
                    member.pinjamBuku(daftarBuku, judul);
                    break;
                case 4:
                    return; // Keluar dari menu member
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }
    
    
}
