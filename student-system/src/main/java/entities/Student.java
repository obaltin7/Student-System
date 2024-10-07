package entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@OneToOne(mappedBy = "student",cascade = CascadeType.ALL)
	private StudentInfo info;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name="student_courses",
			joinColumns = @JoinColumn(name="student_id"),
			inverseJoinColumns = @JoinColumn(name="course_id")
			)
	private List<Course> courses;
	public Student() {
		// TODO Auto-generated constructor stub
	}
	
	public Student(int id, String name, StudentInfo info, List<Course> courses) {
		
		this.id = id;
		this.name = name;
		this.info = info;
		this.courses = courses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StudentInfo getInfo() {
		return info;
	}

	public void setInfo(StudentInfo info) {
		this.info = info;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Student id=" + id + ", name=" + name + ", info=" + info + ", courses=" + courses ;
	}
	
	
	
	

}
