package bussines;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import config.HbntCnfg;
import entities.Student;
import entities.StudentInfo;

public class StudentBussines {
	private static Scanner in=new Scanner(System.in);
	private static Session session=null;
	private static Transaction transaction=null;
	
	public static void run() {
		int islem=0;
		do {
			System.out.println("Öğrenci İşlemleri");
			System.out.println("1-Öğrenci Ekle");
			System.out.println("2-Öğrenci Listele");
			System.out.println("3-Öğrenci Getir");
			System.out.println("4-Öğrenci Sil");
			System.out.println("5-Öğrenci Güncelle");
			System.out.println("0-Çıkış");
			islem=in.nextInt();
			switch (islem) {
			case 1 -> ogrenciEkle();
			case 2 -> ogrenciListele();
			case 3 -> ogrenciGetir();
			case 4 -> ogrenciSil();
			case 5 -> ogrenciGuncelle();
			case 0 -> System.out.println("Çıkış Yapılıyor");
			default -> hataVer();
			}
		} while (islem!=0);

	}
	private static void hataVer() {
		System.out.println("Hatalı Giriş");
		
	}
	private static void ogrenciGuncelle() {
		session=HbntCnfg.getFactory().openSession();
		transaction=session.beginTransaction();
		try {
			System.out.println("Öğrenci İd Giriniz");
			int id=in.nextInt();
			Student student=session.get(Student.class, id);
			String sql="select * From student_info s WHERE s.student_id=:studentId";
			StudentInfo info=session.createNativeQuery(sql, StudentInfo.class)
					.setParameter("studentId", id)
					.uniqueResult();
			
			in.nextLine();
			System.out.println("Öğrenci Adı: ");
			String name=in.nextLine();
			System.out.println("Öğrenci Telefonu Giriniz: ");
			String phone=in.nextLine();
			if(!name.trim().isEmpty()) {
				student.setName(name);
			}
			if(!phone.trim().isEmpty()) {
				info.setPhone(phone);
			}
			info.setStudent(student);			
			session.merge(student);
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			kapat();
		}
	}
	private static void ogrenciSil() {
		session=HbntCnfg.getFactory().openSession();
		transaction=session.beginTransaction();
		try {
			System.out.println("Öğrenci İd Giriniz");
			int id=in.nextInt();
			Student student=session.get(Student.class, id);
			session.remove(student);
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			kapat();
		}
		
	}
	private static void ogrenciGetir() {
		session=HbntCnfg.getFactory().openSession();
		transaction=session.beginTransaction();
		try {
//			Student student=session.get(Student.class, 1);
			System.out.println("Öğrenci İd Giriniz");
			int id=in.nextInt();
			String sql="From Student s WHERE s.id=:studentId";
			Student student=session.createQuery(sql,Student.class)
					.setParameter("studentId", id)
					.uniqueResult();
			System.out.println(student);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			kapat();
		}
		
	}
	private static void ogrenciListele() {
		session=HbntCnfg.getFactory().openSession();
		transaction=session.beginTransaction();
		try {
//			String query="SELECT * FROM students";
//			List<Student> students=
//					session.createNativeQuery(query,Student.class).getResultList();
			
			List<Student> students=session.createQuery("From Student",Student.class).list();
			
			for (Student student : students) {
				System.out.println(student);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}finally {
			kapat();
		}
		
	}
	private static void ogrenciEkle() {
		Student student =new Student();
		StudentInfo info=new StudentInfo();
		in.nextLine();
		System.out.println("Öğrenci Adını Giriniz");
		String name=in.nextLine();
		System.out.println("Öğrenci Telefonunu giriniz");
		String phone=in.next();
		info.setPhone(phone);
		info.setStudent(student);
		student.setName(name);
		student.setInfo(info);
		session=HbntCnfg.getFactory().openSession();
		transaction=session.beginTransaction();
		try {
			session.persist(student);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}finally {
			kapat();			
		}
		
		
		
	}
	private static void kapat() {
		session=null;
		transaction=null;
		//HbntCnfg.shutDown();
		
	}
}
