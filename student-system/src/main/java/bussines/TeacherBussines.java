package bussines;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import config.HbntCnfg;
import entities.Teacher;

public class TeacherBussines {
	private static Scanner in = new Scanner(System.in);
	private static Session session = null;
	private static Transaction transaction = null;

	public static void run() {
		int islem = 0;
		do {
			System.out.println("Öğretmen İşlemleri");
			System.out.println("1-Öğretmen Ekle");
			System.out.println("2-Öğretmen Listele");
			System.out.println("3-Öğretmen Getir");
			System.out.println("4-Öğretmen Sil");
			System.out.println("5-Öğretmen Güncelle");
			System.out.println("0-Çıkış");
			islem = in.nextInt();
			switch (islem) {
			case 1 -> ogretmenEkle();
			case 2 -> ogretmenListele();
			case 3 -> ogretmenGetir();
			case 4 -> ogretmenSil();
			case 5 -> ogretmenGuncelle();
			case 0 -> System.out.println("Çıkış Yapılıyor");
			default -> hataVer();
			}
		} while (islem != 0);
	}

	private static void ogretmenEkle() {
		session = HbntCnfg.getFactory().openSession();
		transaction = session.beginTransaction();
		try {
			System.out.println("Yeni Öğretmenin Adı Giriniz: ");
			in.nextLine(); 
			String name = in.nextLine();
			Teacher teacher = new Teacher();
			teacher.setName(name);

			session.persist(teacher);
			transaction.commit();
			System.out.println("Öğretmen başarıyla eklendi!");

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			kapat();
		}
	}

	private static void ogretmenListele() {
		session = HbntCnfg.getFactory().openSession();
		try {
			List<Teacher> teachers = session.createQuery("from Teacher", Teacher.class).list();
			System.out.println("Öğretmen Listesi:");
			for (Teacher teacher : teachers) {
				System.out.println(teacher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			kapat();
		}
	}

	private static void ogretmenGetir() {
		session = HbntCnfg.getFactory().openSession();
		try {
			System.out.println("Öğretmen ID Giriniz: ");
			int id = in.nextInt();
			Teacher teacher = session.get(Teacher.class, id);
			if (teacher != null) {
				System.out.println("Bulunan Öğretmen: " + teacher);
			} else {
				System.out.println("Bu ID'ye sahip öğretmen bulunamadı.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			kapat();
		}
	}

	private static void ogretmenSil() {
		session = HbntCnfg.getFactory().openSession();
		transaction = session.beginTransaction();
		try {
			System.out.println("Silinecek Öğretmenin ID Giriniz: ");
			int id = in.nextInt();
			Teacher teacher = session.get(Teacher.class, id);
			if (teacher != null) {
				session.remove(teacher);
				transaction.commit();
				System.out.println("Öğretmen başarıyla silindi.");
			} else {
				System.out.println("Bu ID'ye sahip öğretmen bulunamadı.");
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			kapat();
		}
	}

	private static void ogretmenGuncelle() {
		session = HbntCnfg.getFactory().openSession();
		transaction = session.beginTransaction();
		try {
			System.out.println("Güncellenecek Öğretmenin ID Giriniz: ");
			int id = in.nextInt();
			Teacher teacher = session.get(Teacher.class, id);
			if (teacher != null) {
				in.nextLine(); 
				System.out.println("Yeni Öğretmen Adı: ");
				String name = in.nextLine();

				if (!name.trim().isEmpty()) {
					teacher.setName(name);
					session.merge(teacher);
					transaction.commit();
					System.out.println("Öğretmen başarıyla güncellendi.");
				}
			} else {
				System.out.println("Bu ID'ye sahip öğretmen bulunamadı.");
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			kapat();
		}
	}

	private static void kapat() {
		if (session != null) {
			session.close();
		}
	}

	private static void hataVer() {
		System.err.println("Hatalı giriş!");
	}
}
