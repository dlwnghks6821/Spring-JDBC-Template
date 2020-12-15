//1) ==>게시글 목록(list)가져오기(select)//
//)Model 객체 ==> view 에 데이터를 전달해주기 위함//
public String list(Model model){
  BDao dao = new BDao();
  ArrayList<BDto>dtos = dao.list();
}
//2)==>게시글 읽기(게시글을 클릭했을때 클릭한 게시글의 내용을 가져온다(select)//
	//==>클릭할때 클릭한 게시물의 고유 번호(bId)를 가져와서 sql 문에서 그 bId에 해당하는 게시물을 view 에 던져주면 된다<==//
@RequestMapping("/content_view")
public String content_view(HttopServletRequest req , Model model){
	//==>게시물의 고유번호를 getParameter 를 이용해서 가져온다 ( a태그 이용 )
	//==><a href="content_view?bId=${dto.bId}">${dto.bTitle}</a>jsp에서 있는 a태그가  @RequestMapping("/content_view ")인content_view
	//==>로 이동 하게끔 a태그 경로를 지정했으므로 , 쿼리스트링에 해당 게시물을 클릭시 ${dto.bId} 가 입력되게 된다.//
	//==>때문에 이렇게 입력된 값을 쿼리스트링에 남겨 놨으므로 , getParameter 를 이용해서 값을 가져오면된다//
	//==>parseInt를 한이유는 쿼리스트링에서 getParameter( ) 를 이용하게되면 기본값이 String 자료 형이기 때문에 parseInt로 캐스팅을해준다.//

	int bId = Integer.parseInt(req.getParameter("bId"));//==> 값을 getParameter로 받은 상태 //

	//받은 데이터 값을 DAO에 전달 , sql 을 실행하게 하기 위해서 인스턴스화 한다.//
	BDao dao = new BDao();

	==>"select bId,bName,bTitle,bContent,bDate,bHit,bGroup,bStep,bIndent from mvc_board where BID ='"+bId+"'"; 이러한 내용을
	//==> dao 에서 처리를 해주고 , 그리고 그내용을 DTO에 담아서 DTO 안에잇는 constructor , getter() , setter() ,등을 이용해서 
	//==> 해당 게시물에 해당하는 내용물들을 설정 해준다(필드에 내용을 담는다)  

	BDto dto = dao.contentView(bId); //==> dao 에서 처리해준 데이터를 dto 에 담아서 데이터를 설정해준다//

	//==> 그다음에 컨트롤러에서 model 에 dto 에서 설정해준데이터 값들을 가져와서 model 에 담아주고 view 단에 
	//==> model 객체를 이용해서 view 에 던져준다.//

	model.addAttribute("content_view",dto);
	return "content_view";  == > 데이터를 model 에 담아서 return 해준다 (content_view.jsp)로 , 
	
}	


//3)==> 게시판에 게시물 작성하기(insert)//
@RequestMapping("/write_view")//==> 이 경로로 이동하게되면 글쓰기 jsp 를 return 하는 메소드를 실행//
public String write_view(HttpServletRequest req, Model model){
	return "write_view";
	//==> write_view.jsp ==> form 을 이용해서 데이터를 전송한다//
}
//==> 위에서(write_view.jsp 에서 전달된 데이터를 가공하는 단계)//
@RequestMapping(value="/write",method=RequestMethod.POST)
public String write(HttpServletRequest req, Model model){
	//==>위에서 (write_view.jsp 에서 전달한 데이터를 getParameter()를 이용해서
	//==>값을 dao 에서 받아서 sql 문에 전달해주는 역할을한다//

	//==> JSP form 에서 던져준 데이터를 받아서 처리한는단계 (get Parameter () )//
	String bId = req.getParameter("bId");
	String bName = req.getParameter("bName");
	String bTitle = req.getParameter("bTitle");
	String bContent = req.getParameter("bContent");
	// 던져준 데이터를 받아서 , sql 문에 넣은다음 실행시키기위해//
	//dao 객체를 인스턴스화 한다음(선언) dao 에 넘겨준다.//
	BDao dao = new BDao();
	//==>dao 에 getParameter()를 이용해서 전달받은 값들을 넘겨준다<==//
	dao.write(bName,bTitle,bContent); 
	//==>dao.write ==> dao객체의 write메서드에서 sql (insert into ~)를 이용하기위해 데이터를 parameter 로 전달<==//
	return "redirect:list"//==> 할일이 끝나면 ( 글쓰기를 다 한후에 리스트로 간다)//

}
//4)==> 게시판 글 삭제하기(delete)//
@RequestMapping(value="/delete")
public String delete(HttpServletRequest req , Model ,model) {
	==> 게시판 글 삭제 기준 <==
	1) 해당 게시물의 고유 번호 를 기준으로 그 게시물을 삭제 한다 는 조건
	때문에 bId 를 getParameter( ) 를 이용해서 가져오게된다. 
	
	String bId = req.getParameter("bId");
	==>getParameter 로 받은 데이터를 dao 에 전송 ==> sql 문을 이용해서 삭제를 하기위함.//
	BDao dao = new Bdao( );
	//==> 매개변수로 parameter 를 이용해서 bId를 전달해준다//
	dao.delete(Integer.parseInt(bId));
	return "redirect:list";

}
//5)==> 게시판 글 수정하기(update)//
@RequestMapping("/modify_view")
public String modify_view(HttpServletRequest req , Model model) {
	bId ==> 어느 게시물 을 기준으로 수정할지 에 대한 값을 전달받기위함
	jsp에서 (contentview jsp페이지에서)modify(수정)을 클릭시에 <a href="modify_view?bId=${content_view.bId }
	이러한 내용을 queryString으로 가져오게 됨
	이러한 내용을 기준으로 수정을 한다 
	
	int bId = Integer.parseInt(req.getParameter("bId"));
	BDao dao = new BDao();
	BDto dto = dao.contentView(bId);
	model.addAttribute("modify_view",dto);
	dto의 내용을 model에 담아서 modify_view.jsp에 던져준다
	return "modify_view";
	
}
@RequestMapping(value="/modify" ,method=RequestMethod.POST)
//==> post => modify_view.jsp 에서 던져준 데이터 값의 방식이 POST이기 때문에 Method를 POST로 해주었다.//
public String modify(HttpServletRequest req, Model model) {
	//==>getParameter( ) 를 이용해서  modify_view.jsp 에서 던져준 데이터(수정된 데이터를) 받아온다
	String bId = req.getParameter("bId");//==>jsp 에서 readonly 부분 ==> 수정은 못하는데 데이터는 전송가능//
	String bName = req.getParameter("bName");
	String bTitle= req.getParameter("bTitle");
	String bContent = req.getParameter("bContent"); 
	Bdao dao = new BDao( );
	dao.modify(Integer.parseInt(bId),bName,bTitle,bContent);
	return "redirect:list";
}


    