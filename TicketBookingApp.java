import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class BaseTicket {
    protected String name;
    protected String destination;
    protected String category;
    protected int quantity;
    protected String date;
    protected int totalPrice;
    protected String bookingTime;
    protected String ticketCode;

    public BaseTicket(String name, String destination, String category, int quantity, String date, int totalPrice, String bookingTime, String ticketCode) {
        this.name = name;
        this.destination = destination; 
        this.category = category;
        this.quantity = quantity;
        this.date = date;
        this.totalPrice = totalPrice;
        this.bookingTime = bookingTime;
        this.ticketCode = ticketCode;
    }

    public String getName() { return name; }
    public String getDestination() { return destination; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public String getDate() { return date; }
    public int getTotalPrice() { return totalPrice; }
    public String getBookingTime() { return bookingTime; }
    public String getTicketCode() { return ticketCode; }

    public String toString() {
        return "Kode: " + ticketCode + " - " + name + " - " + destination + " - " + category + " - " + quantity + " tiket - " + date + " - Rp " + totalPrice + " - Dipesan pada " + bookingTime;
    }
}

class Ticket extends BaseTicket {
    public Ticket(String name, String destination, String category, int quantity, String date, int totalPrice, String bookingTime, String ticketCode) {
        super(name, destination, category, quantity, date, totalPrice, bookingTime, ticketCode);
    }

    @Override
    public String toString() {
        return "[TIKET] " + super.toString();
    }
}

class TicketManager {
    private List<Ticket> tickets = new ArrayList<>();
    private static final String[] locations = {"Borobudur", "Taman Mini Indonesia Indah", "Pantai Parangtritis", "Gunung Bromo", "Kawah Ijen", "Dieng Plateau", "Kebun Raya Bogor", "Tangkuban Perahu", "Candi Prambanan", "Karimunjawa"};
    private static final int[] prices = {50000, 30000, 40000}; // Dewasa, Anak-anak, Lansia
    private static final Map<String, Integer> ticketAvailability = new HashMap<>();
    
    static {
        for (String location : locations) {
            ticketAvailability.put(location, 100); // Setiap destinasi memiliki 100 tiket
        }
    }

    private String generateTicketCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void bookTicket(Scanner scanner) {
        System.out.print("Masukkan nama pemesan: ");
        String name = scanner.nextLine();
        
        System.out.println("\nPilih destinasi wisata:");
        for (int i = 0; i < locations.length; i++) {
            System.out.println((i + 1) + ". " + locations[i] + " (Sisa: " + ticketAvailability.get(locations[i]) + " tiket)");
        }
        int locationChoice = scanner.nextInt();
        scanner.nextLine();
        if (locationChoice < 1 || locationChoice > locations.length) {
            System.out.println("Destinasi tidak valid!");
            return;
        }
        String destination = locations[locationChoice - 1];

        System.out.print("Masukkan kategori (Dewasa/Anak-anak/Lansia): ");
        String category = scanner.nextLine().toLowerCase();
        int price;
        switch (category) {
            case "dewasa": price = prices[0]; break;
            case "anak-anak": price = prices[1]; break;
            case "lansia": price = prices[2]; break;
            default: System.out.println("Kategori tidak valid!"); return;
        }

        System.out.print("Masukkan jumlah tiket: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        
        if (quantity > ticketAvailability.get(destination)) {
            System.out.println("Tiket tidak mencukupi!");
            return;
        }
        
        System.out.print("Masukkan tanggal kunjungan (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        
        String bookingTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int totalPrice = price * quantity;
        String ticketCode = generateTicketCode();
        
        System.out.println("Pilih metode pembayaran: 1. Transfer Bank 2. E-Wallet");
        int paymentMethod = scanner.nextInt();
        scanner.nextLine();
        
        if (paymentMethod == 1) {
            System.out.println("Pembayaran melalui Transfer Bank berhasil.");
        } else if (paymentMethod == 2) {
            System.out.println("Pembayaran melalui E-Wallet berhasil.");
        } else {
            System.out.println("Metode pembayaran tidak valid!");
            return;
        }

        tickets.add(new Ticket(name, destination, category, quantity, date, totalPrice, bookingTime, ticketCode));
        ticketAvailability.put(destination, ticketAvailability.get(destination) - quantity);
        System.out.println("\nTiket berhasil dipesan! Kode Tiket: " + ticketCode + " Total harga: Rp " + totalPrice);
    }

    public void viewTickets() {
        if (tickets.isEmpty()) {
            System.out.println("\nBelum ada tiket yang dipesan.");
            return;
        }
        
        System.out.println("\n===== DAFTAR TIKET =====");
        Collections.sort(tickets, Comparator.comparing(Ticket::getName));
        for (int i = 0; i < tickets.size(); i++) {
            System.out.println((i + 1) + ". " + tickets.get(i));
        }
    }

    public void saveTickets() {
        try (FileWriter writer = new FileWriter("tiket_wisata.txt")) {
            writer.write("===== DAFTAR TIKET =====\n");
            for (Ticket ticket : tickets) {
                writer.write(ticket.toString() + "\n");
            }
            System.out.println("\nTiket berhasil disimpan ke 'tiket_wisata.txt'!");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan tiket.");
        }
    }
}

public class TicketBookingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicketManager ticketManager = new TicketManager();
        
        while (true) {
            System.out.println("\n===== APLIKASI PENJUALAN TIKET WISATA =====");
            System.out.println("1. Pesan Tiket");
            System.out.println("2. Lihat Daftar Tiket");
            System.out.println("3. Simpan Tiket ke File");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    ticketManager.bookTicket(scanner);
                    break;
                case 2:
                    ticketManager.viewTickets();
                    break;
                case 3:
                    ticketManager.saveTickets();
                    break;
                case 4:
                    System.out.println("Terima kasih telah menggunakan aplikasi!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");            //
            }
        }
    }
}

