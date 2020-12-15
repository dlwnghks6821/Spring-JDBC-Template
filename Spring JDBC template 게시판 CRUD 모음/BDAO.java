package com.javalec.practice.BDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.ui.Model;

import com.javalec.practice.BDto.BDto;
import com.javalec.practice.util.Constant;
public class BDao {
	
	JdbcTemplate template = null;
	public BDao() {
		this.template = Constant.template;
		
	}
	public ArrayList<BDto> list() {
		//여러개 가져올땐 template.query를 사용//
		ArrayList<BDto> dtos = null;
		String query = "select bId,bName,bTitle,bContent,bDate,bHit,bGroup,bStep,bIndent from mvc_board order by bGroup desc, bStep asc";
		dtos=(ArrayList<BDto>) template.query(query, new BeanPropertyRowMapper<BDto>(BDto.class));
		System.out.println(dtos.get(1));
		return dtos;
	}
	public void modify(final int bId,final String bName, final String bTitle,final String bContent) {
		//게시물 수정//
		String query = "update mvc_board set bName=?,bTitle=?,bContent=? where bId=?";
		this.template.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps)throws SQLException{
				ps.setString(1, bName);
				ps.setString(2, bTitle);
				ps.setString(3, bContent);
				ps.setInt(4, bId);
			}
		});
	}
	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep,
			String bIndent) {
		// TODO Auto-generated method stub
		
	}
	public BDto contentView(int bId) {
		//조회수 upHit//
		upHit(bId);
		//post 읽어오기 & BDto 에 넣기 
		String query = "select bId,bName,bTitle,bContent,bDate,bHit,bGroup,bStep,bIndent from mvc_board where BID ='"+bId+"'";
		System.out.println(bId);
		//하나만 가져올때 사용//
		return template.queryForObject(query,new BeanPropertyRowMapper<BDto>(BDto.class));
		
	}
	private void upHit(final int bId) {
		//조회수 증가시키기//
		String query = "update mvc_board set bHit=bHit+1 where BID =?";
		this.template.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps)throws SQLException{
				ps.setInt(1, bId);
			}
		});
		
	}
	public void delete(final int bId) {
		//게시글 삭제//
		String query = "delete from mvc_board where bId=?";
		this.template.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps)throws SQLException{
				ps.setInt(1, bId);
			}
		});
		
	}
	
	//게시글 쓰기 //
	public void write(final String bName,final String bTitle, final String bContent) {
		String query = "insert into mvc_board(bId,bName,bTitle,bContent,bHit,bGroup,bStep,bIndent) "+
						"values(mvc_board_seq.nextval,?,?,?,0,mvc_board_seq.currval,0,0)";
		this.template.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps)throws SQLException{
				ps.setString(1, bName);
				ps.setString(2, bTitle);
				ps.setString(3, bContent);
				
			}
		});
		
	}
	
		
	
	
	
}
