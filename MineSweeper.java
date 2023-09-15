import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Mayın Tarlası Oyuna Hoşgeldiniz!");
        System.out.print("Satır Sayısını Giriniz: ");
        int satirSayisi = scanner.nextInt();
        System.out.print("Sütun Sayısını Giriniz: ");
        int sutunSayisi = scanner.nextInt();

        int elemanSayisi = satirSayisi * sutunSayisi;
        int mayinSayisi = elemanSayisi / 4;

        char[][] oyunTahtasi = new char[satirSayisi][sutunSayisi];
        char[][] mayinTahtasi = new char[satirSayisi][sutunSayisi];

        // Oyun tahtasını başlangıçta boş olarak doldur
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                oyunTahtasi[i][j] = '-';
            }
        }

        // Mayın tahtasını başlangıçta boş olarak doldur
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                mayinTahtasi[i][j] = '-';
            }
        }

        // Mayınları rastgele yerleştir
        Random random = new Random();
        for (int i = 0; i < mayinSayisi; i++) {
            int randomSatir = random.nextInt(satirSayisi);
            int randomSutun = random.nextInt(sutunSayisi);

            // Eğer bu hücrede zaten mayın varsa, tekrar yerleştir
            while (mayinTahtasi[randomSatir][randomSutun] == '*') {
                randomSatir = random.nextInt(satirSayisi);
                randomSutun = random.nextInt(sutunSayisi);
            }

            mayinTahtasi[randomSatir][randomSutun] = '*';
        }

        boolean oyunDevamEdiyor = true;

        while (oyunDevamEdiyor) {
            // Oyun tahtasını görüntüle
            tahtaGoruntule(oyunTahtasi);

            // Kullanıcıdan hamle al
            int secilenSatir, secilenSutun;
            do {
                System.out.print("Satır Giriniz: ");
                secilenSatir = scanner.nextInt();
                System.out.print("Sütun Giriniz: ");
                secilenSutun = scanner.nextInt();
            } while (secilenSatir < 0 || secilenSatir >= satirSayisi || secilenSutun < 0 || secilenSutun >= sutunSayisi);

            if (mayinTahtasi[secilenSatir][secilenSutun] == '*') {
                // Kullanıcı mayına bastı, oyunu kaybetti
                oyunDevamEdiyor = false;
                System.out.println("Game Over!!");
                tahtaGoruntule(mayinTahtasi);
            } else {
                // Kullanıcı mayına basmadı, etrafındaki mayınları kontrol et
                int mayinSayisiCevresi = mayinSayisiniHesapla(mayinTahtasi, secilenSatir, secilenSutun);
                oyunTahtasi[secilenSatir][secilenSutun] = (char) (mayinSayisiCevresi + '0');

                // Kazanma koşulu kontrolü
                int acilmamisHucresiSayisi = elemanSayisi - mayinSayisi;
                int acilanHucresiSayisi = 0;
                for (int i = 0; i < satirSayisi; i++) {
                    for (int j = 0; j < sutunSayisi; j++) {
                        if (oyunTahtasi[i][j] != '-' && oyunTahtasi[i][j] != '*') {
                            acilanHucresiSayisi++;
                        }
                    }
                }

                if (acilanHucresiSayisi == acilmamisHucresiSayisi) {
                    oyunDevamEdiyor = false;
                    System.out.println("Oyunu Kazandınız!");
                    tahtaGoruntule(oyunTahtasi);
                }
            }
        }

        scanner.close();
    }

    // Bir hücrenin etrafındaki mayın sayısını hesapla
    public static int mayinSayisiniHesapla(char[][] tahta, int satir, int sutun) {
        int mayinSayisi = 0;
        int satirSayisi = tahta.length;
        int sutunSayisi = tahta[0].length;

        int[][] etrafKoordinatlar = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, /* hücre */ {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] koordinat : etrafKoordinatlar) {
            int yeniSatir = satir + koordinat[0];
            int yeniSutun = sutun + koordinat[1];

            if (yeniSatir >= 0 && yeniSatir < satirSayisi && yeniSutun >= 0 && yeniSutun < sutunSayisi) {
                if (tahta[yeniSatir][yeniSutun] == '*') {
                    mayinSayisi++;
                }
            }
        }

        return mayinSayisi;
    }

    // Tahtayı görüntüle
    public static void tahtaGoruntule(char[][] tahta) {
        int satirSayisi = tahta.length;
        int sutunSayisi = tahta[0].length;

        System.out.println("===========================");
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                System.out.print(tahta[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("===========================");
    }
}
