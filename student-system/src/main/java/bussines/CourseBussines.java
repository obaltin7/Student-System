package bussines;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import config.HbntCnfg;
import entities.Course;
import entities.Student;
import entities.Teacher;

public class CourseBussines {
	private static Scanner in = new Scanner(System.in);
	private static Session session = null;
	private static Transaction transaction = null;

	public static void run() {

		int islem = 0;
		do {
			System.out.println("Kurs İşlemleri");
			System.out.println("1-Kurs Ekle");
			System.out.println("2-Kurs Listele");
			System.out.println("3-Kurs Getir");
			System.out.println("4-Kurs Sil");
			System.out.println("5-Kurs Güncelle");
			System.out.println("0-Çıkış");
			islem = in.nextInt();
			switch (islem) {
			case 1 -> kursEkle();
			case 2 -> kursListele();
			case 3 -> kursGetir();
			case 4 -> kursSil();
			case 5 -> kursGuncelle();
			case 0 -> System.out.println("Çıkış Yapılıyor");
			default -> hataVer();
			}
		} while (islem != 0);

	}

	private static void kursEkle() {
	    session = HbntCnfg.getFactory().openSession();
	    transaction = session.beginTransaction();
	    try {
	        System.out.println("Yeni Kurs Adı Giriniz: ");
	        String name = in.nextLine();
	        in.nextLine();
	        System.out.println("Kurs Öğretmeninin ID'sini Giriniz: ");
	        int teacherId = in.nextInt();
	        Teacher teacher = session.get(Teacher.class, teacherId);

	        if (teacher == null) {
	            System.out.println("Girilen ID'ye sahip öğretmen bulunamadı.");
	            return;
	        }

	        Course course = new Course();
	        course.setName(name);
	        course.setTeacher(teacher);

	        List<Student> students = new ArrayList<>();
	        System.out.println("Kursu kaç öğrenci alıyor?");
	        int ogrSayisi = in.nextInt();
	        in.nextLine();

	        for (int i = 0; i < ogrSayisi; i++) {
	            System.out.println((i+1) + ". Öğrenci ID Giriniz: ");
	            int studentId = in.nextInt();
	            Student student = session.get(Student.class, studentId);

	            if (student != null) {
	                students.add(student);
	            } else {
	                System.out.println("Girilen ID'ye sahip öğrenci bulunamadı.");
	            }
	        }
	        course.setStudents(students);

	        session.persist(course);
	        transaction.commit();
	        System.out.println("Kurs başarıyla eklendi!");

	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        kapat();
	    }
	}


	private static void kursListele() {
	    session = HbntCnfg.getFactory().openSession();
	    try {
	        List<Course> courses = session.createQuery("from Course", Course.class).getResultList();
	        if (courses.isEmpty()) {
	            System.out.println("Kayıtlı kurs bulunamadı.");
	        } else {
	            System.out.println("Kurs Listesi: ");
	            for (Course course : courses) {
	                System.out.println(course);
	                System.out.println("Kursu Alan Öğrenciler: ");
		            for (Student student : course.getStudents()) {
		                System.out.println(student.getName());
		            }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        kapat();
	    }
	}


	private static void kursGetir() {
	    session = HbntCnfg.getFactory().openSession();
	    try {
	        System.out.println("Kurs ID Giriniz:");
	        int id = in.nextInt();
	        Course course = session.get(Course.class, id);

	        if (course != null) {
	            System.out.println("Kurs Bilgileri: ");
	            System.out.println(course);
	            System.out.println("Kursu Alan Öğrenciler: ");
	            for (Student student : course.getStudents()) {
	                System.out.println(student.getName());
	            }
	        } else {
	            System.out.println("Girilen ID'ye sahip bir kurs bulunamadı.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        kapat();
	    }
	}


	private static void kursSil() {
	    session = HbntCnfg.getFactory().openSession();
	    transaction = session.beginTransaction();
	    try {
	        System.out.println("Silinecek Kursun ID'sini Giriniz: ");
	        int id = in.nextInt();
	        Course course = session.get(Course.class, id);

	        if (course != null) {
	            session.remove(course);
	            transaction.commit();
	            System.out.println("Kurs başarıyla silindi!");
	        } else {
	            System.out.println("Girilen ID'ye sahip bir kurs bulunamadı.");
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


	private static void kursGuncelle() {
	    session = HbntCnfg.getFactory().openSession();
	    transaction = session.beginTransaction();
	    try {
	        System.out.println("Kurs ID Giriniz");
	        int id = in.nextInt();
	        Course course = session.get(Course.class, id);
	        
	        if (course == null) {
	            System.out.println("Girilen ID'ye sahip bir kurs bulunamadı.");
	            return;
	        }

	        System.out.println("Kurs Bilgileri: ");
	        System.out.println(course);

	       
	        in.nextLine(); 
	        System.out.println("Yeni Kurs Adı: ");
	        String name = in.nextLine();
	        if (!name.trim().isEmpty()) {
	            course.setName(name);
	        }

	        System.out.println("Kursu kaç öğrenci alıyor?");
	        int ogrSayisi = in.nextInt();
	        in.nextLine(); 

	        List<Student> students = new ArrayList<>();
	        for (int i = 0; i < ogrSayisi; i++) {
	            System.out.println((i+1) + ". Öğrenci ID Giriniz: ");
	            int studentId = in.nextInt();
	            Student student = session.get(Student.class, studentId);

	            if (student != null) {
	                students.add(student);
	            } else {
	                System.out.println("Girilen ID'ye sahip öğrenci bulunamadı.");
	            }
	        }
	        course.setStudents(students);

	        System.out.println("Yeni Öğretmen ID Giriniz: ");
	        int teacherId = in.nextInt();
	        Teacher teacher = session.get(Teacher.class, teacherId);
	        if (teacher != null) {
	            course.setTeacher(teacher);
	        } else {
	            System.out.println("Girilen ID'ye sahip öğretmen bulunamadı.");
	        }

	        session.merge(course);
	        transaction.commit();
	        System.out.println("Kurs başarıyla güncellendi!");

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
