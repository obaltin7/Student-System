import java.util.Scanner;

import bussines.CourseBussines;
import bussines.StudentBussines;
import bussines.TeacherBussines;

public class Aplication {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		do {
			System.out.println("Yapmak İstediğiniz işlemi seçiniz");
			System.out.println("1- Öğrenci İşlemleri");
			System.out.println("2- Ders İşlemleri");
			System.out.println("3- Öğretmen İşlemleri");
			System.out.println("0- Çıkış");
			int islem = in.nextInt();

			if (islem == 1) {
				StudentBussines.run();
			} else if (islem == 2) {
				CourseBussines.run();
			} else if (islem == 3) {
				TeacherBussines.run();
			} else {
				break;
			}
		} while (true);

	}

}
