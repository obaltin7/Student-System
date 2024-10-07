package config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



import entities.Course;
import entities.Student;
import entities.StudentInfo;
import entities.Teacher;

public class HbntCnfg {
	private static final SessionFactory factory=buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		Properties prop=props();
		Configuration cfg=new Configuration();
		try {
			cfg.addProperties(prop);
			cfg.addAnnotatedClass(Student.class);
			cfg.addAnnotatedClass(StudentInfo.class);
			cfg.addAnnotatedClass(Course.class);
			cfg.addAnnotatedClass(Teacher.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}
		return cfg.buildSessionFactory();
	}
	
	private static Properties props() {
		Properties prop=new Properties();
		prop.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		prop.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/student_system");
		prop.put("hibernate.connection.username", "root");
		prop.put("hibernate.connection.password", "onur2001");
		prop.put("hibernate.hbm2ddl.auto", "update");
		prop.put("hibernate.show_sql", "true");
		return prop;
	}
	
	public static SessionFactory getFactory() {
		return factory;
	}
	public static void shutDown() {
		factory.close();
	}
	

}
