package com.javalec.practice.controller;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javalec.practice.BDao.BDao;
import com.javalec.practice.BDto.BDto;
import com.javalec.practice.util.Constant;


@Controller
public class BController {
	public JdbcTemplate template;
	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		Constant.template = this.template;
		
	}
	//orm ==> 테이블과 클래스를 매핑시킴 
	//Read//
	//==> Mapping (/list) 이면 아래 메서드를(게시 글 목록 보기) 실행
	@RequestMapping("/list") // 게시 글 목록보기//
	public String list(Model model) {
		//게시 글 목록을 DB 에서 가져와서 ==> model 에 담은다음 ==> view 에다 던져주기//
		//1) DB에서 가져오기위해서 BDao 객체에 접근한다.//
		//2) BDao 객체에서 sql 문을 작성 
		//3) DB에서 가져온 값을 Dto 에 던져준다.//
		//4) Dto 에서 데이터를 setter() ,getter() 를통해 설정하고 다시 Controller 에 던져준다.//
		//5) Controller 에서 view로 model을 이용해서 데이터를 던져준다
		System.out.println("list()");
		BDao dao = new BDao();
		ArrayList<BDto>dtos = dao.list();
		model.addAttribute("dtos",dtos);
		dtos.get(1);
		return "list";
	}
	//create 화면 나오게 하기//
	@RequestMapping("/write_view") // 새글 쓰기  화면 나오게 하기 
	public String write_view(Model model) {
		System.out.println("write_view()");
		return "write_view";
	}
		//create//
		@RequestMapping("/write") //게시글 저장(등록)//
		public String write(HttpServletRequest req, Model model) {
			BDao dao = new BDao();
			String bName = req.getParameter("bName");
			String bTitle = req.getParameter("bTitle");
			String bContent = req.getParameter("bContent");
			model.addAttribute("req", req);
			dao.write(model);
			System.out.println("write()");
			//새글 저장//
			return "redirect:list";//글 목록으로 간다 
		}
		//read//
		@RequestMapping("/content_view")//게시글읽기
		public String content_view(HttpServletRequest req, Model model) {
			System.out.println("content_view()");
			//특정 bId인 게시글 읽어오기 (조회)
			//getParameter()사용 ==> queryString 에서 해당 게시물의 index(bId)값을 가져오게됨//
			//==> bId를 읽어와야하므로 HttpServletRequest 사용 //
			
			int bId=Integer.parseInt(req.getParameter("bId"));
			//dao 의 객체에 접근하기 위해서 dao 인스턴스화 //
			BDao dao = new BDao();
			ArrayList<BDto> dto = dao.contentView(bId);
			model.addAttribute("bId",dto);
			
			return "content_view";
		}
		//update//
		@RequestMapping(value="/modify",method=RequestMethod.POST)
		public String modify(HttpServletRequest req, Model model) {
			System.out.println("modify()");
			//수정내용 저장 
			String bId=req.getParameter("bId");
			String bName=req.getParameter("bNmae");
			String bTitle=req.getParameter("bTitle");
			String bContent=req.getParameter("bContent");
			BDao dao = new BDao();
			dao.modify(bId,bName,bTitle,bContent);
			return "redirect:list";
		}
		//delete//
		@RequestMapping(value="/delete")
		public String delete(HttpServletRequest req, Model model) {
			System.out.println("delete()");
			//게시글 삭제 하기 //
			String bId= req.getParameter("bId");
			BDao dao = new BDao();
			dao.delete(bId);
			return "redirect:list";//==> 삭제후 목록으로 돌아가기//
		}
		
		@RequestMapping("/reply_view")//댓글 쓰기 화면 표시/
		public String reply_view(HttpServletRequest req, Model model) {
			System.out.println("reply_view()");
			return "reply_view";
		}
		@RequestMapping("/reply")//댓글 저장//
		public String reply(HttpServletRequest req, Model model) {
			System.out.println("reply()");
			String bId = req.getParameter("bId");
			String bName = req.getParameter("bName");
			String bTitle = req.getParameter("bTitle");
			String bContent = req.getParameter("bContent");
			String bGroup = req.getParameter("bGroup");
			String bStep = req.getParameter("bStep");
			String bIndent = req.getParameter("bIndent");
			BDao dao = new BDao();
			dao.reply(bId,bName,bTitle,bContent,bGroup,bStep,bIndent);
			return "redirect:list";
		}
	}
	

