package com.spring.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.entity.Student;

@Repository
public class StudentDaoImp implements StudentDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Boolean saveStudent(Student student) {

		System.out.println("inside studentDao repository ");
		try {
			Session session = sessionFactory.openSession();
			Transaction txn = session.beginTransaction();
			session.save(student);
			txn.commit();
			session.close();
			System.out.println("Student saved successfully..");
			return true;
		} catch (Exception e) {
			System.out.println("error while saving the object");
			e.printStackTrace();
			return false;
		}
	}

	List<Student> res = new ArrayList<Student>();

	@Override
	public List<Student> getAllStudent() {

		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			String hql = "FROM Student";
			Query query = session.createQuery(hql);
			res = query.getResultList();
			txn.commit();
		} catch (Exception e) {
			System.out.println("Error while fetching the student list..");
			e.printStackTrace();
			if (txn != null)
				txn.rollback();
		}
		return res;
	}

	@Override
	public Student getStudentById(int id) {

		Session session = null;
		Transaction txn = null;
		Student std = new Student();
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			std = session.get(Student.class, id);
			txn.commit();
		} catch (Exception e) {
			System.out.println("Error while fetching th record in DB");
			e.printStackTrace();
			if (txn != null)
				txn.rollback();
		}
		return std;
	}

	@Override
	public List<Student> getAllStudentSearchBy(String city) {

		Session session = null;
		Transaction txn = null;
		List<Student> res = new ArrayList<Student>();

		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			String hql = "FROM Student S WHERE S.address = :city_name";
			Query query = session.createQuery(hql);
			query.setParameter("city_name", city);
			res = query.getResultList();
			txn.commit();
		} catch (Exception e) {
			System.out.println("Error while fetching results from DB");
			e.printStackTrace();
			if (res != null) {
				txn.rollback();
			}
		}
		return res;
	}

	@Override
	public List<Student> finaAllBysortedField(String field) {

		Session session = null;
		Transaction txn = null;
		List<Student> res = new ArrayList<Student>();

		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);
			Root<Student> root = criteriaQuery.from(Student.class);
			criteriaQuery.select(root).orderBy(builder.asc(root.get(field))); // Assuming 'field' is the name of the
																				// field by which you want to sort
			return res = session.createQuery(criteriaQuery).getResultList();
			// txn.commit();
		} catch (Exception e) {
			System.out.println("Error while fetching results from DB");
			e.printStackTrace();
			if (res != null) {
				txn.rollback();
			}
		}
		return res;
	}

	@Override
	public boolean updateStudentById(Student student) {

		Session session = null;
		Transaction txn = null;
		Student std = new Student();
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			std = session.get(Student.class, student.getId());

			if (std != null) {
				session.merge(student);
				return true;
			} else {
				System.out.println("The student with provided " + student.getId() + "is not present in DB");
				throw new RuntimeException("Student with specified ID is not present in DB");
			}

		} catch (Exception e) {
			System.out.println("Error while fetching th record in DB");
			e.printStackTrace();
			return false;
		} finally {
			txn.commit();
			session.close();
			if (txn != null)
				txn.rollback();
		}

	}

	@Override
	public Boolean deleteStudentById(int id) throws SQLException {
		Session session = null;
		Transaction txn = null;
		if(id==0)
			throw new SQLException();
		Student std = new Student();
		try {
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			std = session.get(Student.class, id);

			if (std != null) {
				session.remove(std);
				return true;
			} else {
				System.out.println("The student with provided " + id + "is not present in DB");
				throw new RuntimeException("Student with specified ID is not present in DB");
			}

		} catch (Exception e) {
			System.out.println("Error while deleting the record in DB");
			e.printStackTrace();
			return false;
		} finally {
			txn.commit();
			session.close();
			if (txn != null)
				txn.rollback();
		}
	}

}
