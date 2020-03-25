package com.rya.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rya.model.Role;
import com.rya.model.User;
import com.rya.repo.RoleRepo;
import com.rya.repo.UserRepo;
import com.rya.repo.impl.RoleRepoImpl;
import com.rya.repo.impl.UserRepoImpl;

@RestController
@RequestMapping("users")
public class UserController {
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	Gson gson = new Gson();
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private UserRepoImpl userRepoImpl;
	
	@Autowired
	private RoleRepoImpl roleRepoImpl;
	
	@RequestMapping("/checkDb")
	public void testDbConnection(){
		//list of users
		userRepo.findAll().forEach(x -> System.out.println(x));
		
		//list of roles
		roleRepo.findAll().forEach(x -> System.out.println(x));
		
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes="application/json")
	public void registerUser(HttpServletResponse response, @RequestBody User user) {
		System.out.println(user.toString());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepoImpl.insertWithQuery(user);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		try(PrintWriter out = response.getWriter()){
		    out.print(gson.toJson("User registered successfully"));
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/findIdByUserName", method = RequestMethod.POST, consumes="application/json")
	public void findIdByUserName(HttpServletResponse response, @RequestBody String inputStr) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(inputStr).getAsJsonObject();
		int userId = userRepoImpl.findByUserName(jsonObject.get("userName").getAsString());
		PrintWriter out;
		try {
			out = response.getWriter();
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    out.print("userId:"+userId);
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/findIdByRoleName",method = RequestMethod.POST, consumes="application/json")
	public void findIdByRoleName(HttpServletResponse response,  @RequestBody String inputStr) {
		try {
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = parser.parse(inputStr).getAsJsonObject();
		int roleId = roleRepoImpl.findByRoleName(jsonObject.get("roleName").getAsString());
		PrintWriter out = response.getWriter();
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    out.print("roleId:"+roleId);
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/assignRole", method = RequestMethod.PUT, consumes="application/json")
	public void registerUser(HttpServletResponse response, @RequestBody String inputStr) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(inputStr).getAsJsonObject();
		
		int userId = userRepoImpl.findByUserName(jsonObject.get("userName").getAsString());
		int roleId = roleRepoImpl.findByRoleName(jsonObject.get("roleName").getAsString());
		userRepoImpl.updateUserRole(userId, roleId);
		System.out.println("==User's role updated==");
		PrintWriter out;
		try {
			out = response.getWriter();
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    out.print(gson.toJson("User's role updated successfully"));
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes="application/json")
	public void login(HttpServletResponse response, @RequestBody String inputStr) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(inputStr).getAsJsonObject();
		String bcryptedPasswordfromDb = userRepoImpl.findPasswordByUserName(jsonObject.get("userName").getAsString());
		boolean passwordMatched = BCrypt.checkpw(jsonObject.get("password").getAsString(), bcryptedPasswordfromDb);
		String result;
		if(passwordMatched) {
			result = "Login successful";
		}else {
			result = "Login Failed";
		}
		PrintWriter out;
		try {
			out = response.getWriter();
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    out.print(gson.toJson(result));
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.PUT, consumes="application/json")
	public void updatePassword(HttpServletResponse response, @RequestBody String inputStr) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(inputStr).getAsJsonObject();
		userRepoImpl.updatePassword(jsonObject.get("userName").getAsString(), bCryptPasswordEncoder.encode(jsonObject.get("password").getAsString()));
		PrintWriter out;
		try {
			out = response.getWriter();
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    out.print(gson.toJson("Password updated successfully"));
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/user", method = RequestMethod.DELETE, consumes="application/json")
	public void deleteUser(HttpServletResponse response, @RequestBody String inputStr) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(inputStr).getAsJsonObject();
		userRepoImpl.deleteUser(jsonObject.get("userName").getAsString());
		PrintWriter out;
		try {
			out = response.getWriter();
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    out.print(gson.toJson("User deleted successfully"));
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.PUT, consumes="application/json")
	public void updateUser(HttpServletResponse response, @RequestBody String inputStr) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(inputStr).getAsJsonObject();
		int roleId = roleRepoImpl.findByRoleName(jsonObject.get("roleName").getAsString());
		User user = new User();
		user.setRoleId(roleId);
		user.setUserName(jsonObject.get("userName").getAsString());
		user.setFirstName(jsonObject.get("firstName").getAsString());
		user.setLastName(jsonObject.get("lastName").getAsString());
		user.setPassword(bCryptPasswordEncoder.encode(jsonObject.get("password").getAsString()));
		userRepoImpl.updateUser(user);
		PrintWriter out;
		try {
			out = response.getWriter();
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    out.print(gson.toJson("User updated successfully"));
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/roleByUserName", method = RequestMethod.POST, consumes="application/json")
	public void roleByUserName(HttpServletResponse response, @RequestBody String inputStr) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(inputStr).getAsJsonObject();
		String role = userRepoImpl.findRoleByUserName(jsonObject.get("userName").getAsString());
		PrintWriter out;
		try {
			out = response.getWriter();
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    out.print(gson.toJson(role));
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/userlist")
	public void userList(HttpServletResponse response) {
		List<User> userList = userRepoImpl.getUserList();
		PrintWriter out;
		try {
			out = response.getWriter();
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    out.print(gson.toJson(userList));
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/rolelist")
	public void roleList(HttpServletResponse response) {
		List<Role> roleList = roleRepoImpl.getRoleList();
		PrintWriter out;
		try {
			out = response.getWriter();
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    out.print(gson.toJson(roleList));
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * @RequestMapping(value = "/rolelist") public ResponseEntity<List<Role>>
	 * roleList(HttpServletResponse response) { List<Role> roleList =
	 * roleRepoImpl.getRoleList(); if(roleList.isEmpty()) { return new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); }else { return new
	 * ResponseEntity<List<Role>>(roleList, HttpStatus.OK); } }
	 */
	
	
}
