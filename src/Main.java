import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


// Üye için oluşturulan parent class
class Uye {
    private final String isim;
    private final String soyIsim;
    private final String email;

    public Uye(String isim, String soyIsim, String email) {
        this.isim = isim;
        this.soyIsim = soyIsim;
        this.email = email;
    }

    public String getIsim() {
        return isim;
    }

    public String getSoyIsim() {
        return soyIsim;
    }

    public String getEmail() {
        return email;
    }
}

// genel üye child class'ı
class GenelUye extends Uye {

    public GenelUye(String isim, String soyIsim, String email) {
        super(isim, soyIsim, email);
    }

    public boolean isElit() {
        boolean isElit = false;
        return isElit;
    }
}


// elit üye child class'ı
class ElitUye extends Uye {

    public ElitUye(String isim, String soyIsim, String email) {
        super(isim, soyIsim, email);
    }

    public boolean isElit() {
        boolean isElit = true;
        return isElit;
    }
}


public class Main {
    public static void main(String[] args) throws IOException {

        // girdi almak için scanner açar
        Scanner sc = new Scanner(System.in);

        // genel üye ve elit üye için array listler oluşturur
        ArrayList<GenelUye> genelUyeler = new ArrayList<>();
        ArrayList<ElitUye> elitUyeler = new ArrayList<>();

        while (true) {

            // menü print eder
            System.out.println("**************************");
            System.out.println("1-Elit üye ekleme\n2-Genel üye ekleme\n3-Mail gönderme");
            String input = sc.nextLine();

            // inputun integer kontrolünü yapar
            if (!isNumeric(input)) {
                System.out.println("Gecersiz girdi!");
            } else {
                int inputNumeric = Integer.parseInt(input);

                switch (inputNumeric) {
                    case 1:
                        // Elit üye ekleme case'i için inputlar alınıp guncelle fonskiyonu çağırır
                        System.out.println("Elit uyenin ismini giriniz : ");
                        String uyeIsım = sc.nextLine();
                        System.out.println("Elit uyenin soyadını giriniz : ");
                        String uyeSoyad = sc.nextLine();
                        System.out.println("Elit uyenin mail adresini giriniz : ");
                        String uyeMail = sc.nextLine();
                        elitUyeler.add(new ElitUye(uyeIsım, uyeSoyad, uyeMail));
                        System.out.println("Uye basariyla eklendi!");
                        guncelle(genelUyeler, elitUyeler);
                        break;
                    case 2:
                        // Genel üye ekleme case'i için inputlar alınıp guncelle fonskiyonu çağırır
                        System.out.println("Genel uyenin ismini giriniz : ");
                        String uyeIsım2 = sc.nextLine();
                        System.out.println("Genel uyenin soyadını giriniz : ");
                        String uyeSoyad2 = sc.nextLine();
                        System.out.println("Genel uyenin mail adresini giriniz : ");
                        String uyeMail2 = sc.nextLine();
                        genelUyeler.add(new GenelUye(uyeIsım2, uyeSoyad2, uyeMail2));
                        System.out.println("Uye basariyla eklendi!");
                        guncelle(genelUyeler, elitUyeler);
                        break;


                    case 3:
                        // Mail yollamak için başlık ve içerik inputları alır
                        System.out.println("Lutfen mail başlığını giriniz!");
                        String baslik = sc.nextLine();
                        System.out.println("Lutfen mesajınızı giriniz!");
                        String mesaj = sc.nextLine();
                        while (true) {
                            System.out.println("**************************");
                            System.out.println("1-Elit üyelere mail yolla\n2-Genel üyelere mail yolla\n3-Tüm üyelere mail yolla");
                            int input2 = sc.nextInt();
                            if (input2 == 1) {

                                // elit üyelere mail atmak için mailYolla fonksiyonuna gerekli parametreler yazar
                                mailYolla(elitUyeler, genelUyeler, true, baslik, mesaj);
                                System.out.println("Elit uyelere mail gönderildi!");
                                // eof karakterini handle yapmak için konulan nextLine komutu
                                sc.nextLine();
                                break;
                            } else if (input2 == 2) {

                                // genel üyelere mail atmak için mailYolla fonksiyonuna gerekli parametreler yazar
                                mailYolla(elitUyeler, genelUyeler, false, baslik, mesaj);
                                System.out.println("Genel uyelere mail gönderildi!");
                                // eof karakterini handle yapmak için konulan nextLine komutu
                                sc.nextLine();
                                break;
                            } else if (input2 == 3) {
                                // tüm üyelere atmak için mailYolla
                                // fonksiyonu, genel üye ve elit üyelere
                                // atan iki farklı tür parametreleri kullanarak çağırır
                                mailYolla(elitUyeler, genelUyeler, true, baslik, mesaj);
                                mailYolla(elitUyeler, genelUyeler, false, baslik, mesaj);
                                System.out.println("Tüm üyelere mail gönderildi!");

                                // eof karakterini handle yapmak için konulan nextLine komutu
                                sc.nextLine();
                                break;
                            } else {
                                System.out.println("Lutfen gecerli bir sayı giriniz!");
                            }

                        }
                        break;
                    default:
                        System.out.println("Lütfen menüdeki seçenklerden birini seçiniz!");
                        break;
                }
            }


        }

    }


    // girilen sayının integer olup olmadığını kontrol eden fonksiyon
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void mailYolla(ArrayList<ElitUye> liste1, ArrayList<GenelUye> liste2, boolean isElite, String baslik, String mesaj) {

        // Smtp server kurulumu ve gönderici email hesabı girişi
        String host = "smtp-mail.outlook.com";
        String port = "587";


        // TODO kullanıcı email ve şifresini düzenleyiniz
        String senderEmail = "";
        String senderPassword = "";


        // Email giriş özelliklerini belirleme
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Şifre doğrulama
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        };

        // Mesaj objesini ve giriş oturumu objelerini oluşturma
        Session session = Session.getInstance(props, auth);
        MimeMessage msg = new MimeMessage(session);


        if (isElite) {
            for (ElitUye elitUye : liste1) {
                // Alıcı bilgisini belirle
                String alicimail = elitUye.getEmail();
                try {
                    // Başlık belirleme
                    msg.setFrom(new InternetAddress(senderEmail));
                    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(alicimail));
                    msg.setSubject(baslik);

                    // Mesaj belirleme
                    msg.setText(mesaj);

                    // Mesajı gönderme
                    Transport.send(msg);
                } catch (MessagingException ex) {
                    System.out.println("Mesaj gönderilirken hata olustu: " + ex.getMessage());
                }

            }
        } else {
            for (GenelUye genelUye : liste2) {
                // Alıcı bilgisini belirle
                String alicimail = genelUye.getEmail();
                try {
                    // Başlık belirleme
                    msg.setFrom(new InternetAddress(senderEmail));
                    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(alicimail));
                    msg.setSubject(baslik);

                    // Mesaj belirleme
                    msg.setText(mesaj);

                    // Mesajı gönderme
                    Transport.send(msg);
                } catch (MessagingException ex) {
                    System.out.println("Mesaj gönderilirken hata olustu: " + ex.getMessage());
                }

            }
        }

    }


    // Bu fonksiyon her eleman eklendiğinde Kullanicilar.txt dosyasını güncelliyor
    public static void guncelle(ArrayList<GenelUye> genel, ArrayList<ElitUye> elit) throws FileNotFoundException {

        // txt dosyası açılıyor
        PrintWriter dosya = new PrintWriter("Kullanicilar.txt");
        dosya.println("#Elit uyeler #");
        dosya.println("");

        // döngüyle array listten ad, soyad ve mail yerleştirme

        for (ElitUye elitUye : elit) {
            dosya.println(elitUye.getIsim() + "\t" + elitUye.getSoyIsim() + "\t" + elitUye.getEmail());
            dosya.println("");
        }
        dosya.println("#Genel Uyeler #");
        dosya.println("");
        for (GenelUye genelUye : genel) {
            dosya.println(genelUye.getIsim() + "\t" + genelUye.getSoyIsim() + "\t" + genelUye.getEmail());
            dosya.println("");
        }
        // dosya .close() fonksiyonu ile kapatılır.
        dosya.close();
    }


}





