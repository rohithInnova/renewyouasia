package com.rya.repo.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.rya.model.User;
import com.rya.repo.UserRepo;

@Repository
public class UserRepoImpl implements UserRepo{

@PersistenceContext
    private EntityManager entityManager;

	@Transactional
	public void insertWithQuery(User user) {
	    entityManager.createNativeQuery("INSERT INTO POC_DB.TBL_USER (first_name, last_name, user_name, password) VALUES (?,?,?,?)")
	      .setParameter(1, user.getFirstName())
	      .setParameter(2, user.getLastName())
	      .setParameter(3, user.getUserName())
	      .setParameter(4, user.getPassword())
	      .executeUpdate();
	}
	
	@Transactional
	public Integer findByUserName(String userName) {
		int userId = (int) entityManager.createNativeQuery("select id from POC_DB.TBL_USER where user_name = ?")
				.setParameter(1, userName)
			   .getSingleResult();
	   return userId;
	}
	
	@Transactional
	public String findPasswordByUserName(String userName) {
	   String password = (String) entityManager.createNativeQuery("select password from POC_DB.TBL_USER where user_name = ?")
				.setParameter(1, userName)
			   .getSingleResult();
	   return password;
	}
	
	@Transactional
	public String findRoleByUserName(String userName) {
	   String roleName = (String) entityManager.createNativeQuery("select r.role_name from POC_DB.TBL_USER u join POC_DB.TBL_ROLE r on u.role_id=r.id where u.user_name = ?")
				.setParameter(1, userName)
				   .getSingleResult();
	   return roleName;
	}
	
	@Transactional
	public void updatePassword(String userName, String password) {
	    entityManager.createNativeQuery("update POC_DB.TBL_USER set password = ? where user_name = ?")
	      .setParameter(1, password)
	      .setParameter(2, userName)
	      .executeUpdate();
	}
	
	@Transactional
	public void updateUserRole(int userId, int roleId) {
	    entityManager.createNativeQuery("update POC_DB.TBL_USER set role_id = ? where id = ?")
	      .setParameter(1, roleId)
	      .setParameter(2, userId)
	      .executeUpdate();
	}
	
	@Transactional
	public void updateUser(User user) {
		entityManager.createNativeQuery("update POC_DB.TBL_USER set role_id = ?, password=?, first_name=?, last_name=? where user_name = ?")
	      .setParameter(1, user.getRoleId())
	      .setParameter(2, user.getPassword())
	      .setParameter(3, user.getFirstName())
	      .setParameter(4, user.getLastName())
	      .setParameter(5, user.getUserName())
	      .executeUpdate();
	}
	
	@Transactional
	public void deleteUser(String userName) {
		entityManager.createNativeQuery(
			      "DELETE FROM POC_DB.TBL_USER WHERE user_name = ?")
		.setParameter(1, userName)
		.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> getUserList(){
		return entityManager.createNativeQuery("select * from POC_DB.TBL_USER")
				.getResultList();
	}
	
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAllById(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends User> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<User> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(User entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends User> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends User> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends User> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}
	
}