package com.javalec.practice.BDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;

import com.javalec.practice.BDto.BDto;
import com.javalec.practice.util.Constant;
public class BDao {
	
	JdbcTemplate template = null;
	public BDao() {
		this.template = Constant.template;
		
	}
	public ArrayList<BDto> list() {
		ArrayList<BDto> dtos = null;
		String query = "select bId,bName,bTitle,bContent,bDate,bHit,bGroup,bStep,bIndent from mvc_board order by bGroup desc, bStep asc";
		dtos=(ArrayList<BDto>) template.query(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		System.out.println(dtos.get(1));
		return dtos;
	}
	public void modify(String bId,String bName, String bTitle,String bContent) {
		
	}
	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep,
			String bIndent) {
		// TODO Auto-generated method stub
		
	}
	public ArrayList<BDto> contentView(int bId) {
		ArrayList<BDto> dto = null;
		//post 읽어오기 & BDto 에 넣기 
		String query = "select bContent from mvc_board where BID ='"+bId+"'";
		System.out.println(bId);
		
		dto=(ArrayList<BDto>) template.query(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		return dto;
	}
	public void delete(String bId) {
		
		
	}
	/*
	public void write(String bId,String bName, String bTitle,String bContent) {
		
		
		
	}
	*/
	public void write(Model model) {
		Map<String,Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		BDao dao = new BDao();
		dao.write(bName,bTitle,bContent);
		
	}
	private ArrayList<BDto> write(String bName, String bTitle, String bContent) {
		ArrayList<BDto> dtos2 = null;
		String query = " bId,bName,bTitle,bContent,bDate,bHit,bGroup,bStep,bIndent from mvc_board order by bGroup desc, bStep asc";
		dtos2 = (ArrayList<BDto>) template.query(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		return dtos2;
		
	}
	
	
}
