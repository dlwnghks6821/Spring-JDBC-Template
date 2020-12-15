	//==>select<==//
//==> 글 목록 읽어 오기<==//
public ArrayList<BDto> list(){
    //==> DB에서 select 한 내용들을 ArrayList에 담는 역할을 해준다//
    ArrayList<BDto>dtos = null;
   //sql문에서 게시글을 조회해야하므로 게시판에 있는 id, 이름 , title 내용 등등 을 (모든 컬럼을 ) 명시해준다.//
    String sql = "select bId,bName,bTitle,bContent,bDate,bHit,bGroup,bStep,bIndent from mvc_board order by bGroup desc, bStep asc";
    //ORM 객체에 sql 문을 담아준다//
   //*여러개의 값을 가져올때는 template.query를 이용하면된다.//
    dtos = (ArrayList<BDto>) template.query(query, new BeanPropertyRowMapper<BDtop>(BDto.class));
    return dtos;

}

//==>클릭한 게시글 읽기<==//
==>참고로 int bId 는 jsp 에서 a태그로 queryString에 남긴 해당 게시물의 고유 id 번호//
==> 이 번호를 controller에 던져주고 controller는 그 값을 getParameter 를 이용해서
==> 받은다음 또 Dao에 던져준 상태이다(parameter(매개변수로)던져준상태)

public BDto contentView(int bId){
	upHit(bId);//==>조회수(게시글을 클릭해서 볼때마다 조회수 1씩증가)//
	//query 문에서 controller 에서 매개변수로 준 해당 게시물의 고유 id 번호를 기준으로 게시물의 내용 , 이름 , 기타등등을 select해오는 sql 문장
	String query = "select bId , bName , bContent , bDate , bHit , bGroup , bStep , bIndent from mvc_board where BID = '"+bId+"'";
	//==> 그런다음 query문을 DTO에 return 을 해주었다.//
	return template.queryForObject(query,new BeanPropertyRowMapper<BDto>(BDto.class));
	
}
//==>클릭한 게시글의 조회수 증가시키기<==//

private void upHit(final int bId) {
	==> 여기서 BID는 클릭한 해당 게시물의 고유 번호 를 기준으로 그 게시물의 조회수를 클릭할때마다 1씩 증가

	String query = "update mvc_board set bHit=bHit+1 where BID = ?";
	this.template.update(query, new PreparedStatementSetter( ) {
		ps.setInt(1, bId);
	}
      });
}


}



	//==>insert<==//
	//==>게시글 작성하기<==//
//==> bName, bTitle, bContent ==> Jsp 에서 던져준 parameter ==>Jsp(view) 에서 getParameter()로 던져준 값들을 parameter로 받아서 sql문에 넣어서 실행//
public void write(final String bName , final String bTitle, final String bContent){
	//SQL query문 ==> insert 
	// ? ? ? ==> jsp 에서 던져준 data 를 함수를 호출하면서 매개변수로 전달해준 parameter값들 을 ??? 에 setString()을 이용해서 sql 문 삽입 //
	//나머지 컬럼들의 값들은 데이터를 입력/조회 할때 sequence 등을 이용해서 자동으로 순차적으로 입력되게끔 DB에서 schema 작성//
	
	String query = "insert into mvc_board (bId,bName,bTitle,bContent,bHit,bGroup,bStep,bIndent) "+
		        "values(mvc_board_seq.nextval,?,?,?,0,mvC_board_seq.currval,0,0)";

	this.template.update(query, new PreparedStatementSetter(){
	@Override
	//넣어주는 값들을 setString()을 이용해서 넣는다//
	public void setValues(PreparedStatement ps)throws SQLException{
		ps.setString(1, bName);
		ps.setString(2, bTitle);
		ps.setString(3, bContent);
		//==>이 작업이 끝나면 , Controller 단에서는 return "redirect:list" ==>해줌으로써 , 글을 작성한다음(insert) 글목록으로 
		// 이동하게된다
	}
     });
}
}
}
	//==>delete<==//
	//==>게시글 삭제하기 < ==//
public void delete(final int bId){
	//==>bId :  Controller 에서 던져준 data 를 매개변수로 parameter를 이용해서 전달받은 것//
	String query = "delete from mvc_board where bId = ?";
	this.template.update(query, new PreparedStatementSetter( ) {
	   @Override
	    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setInt(1, bId) ;
	}
       });
}

	//==>update<==//
	//==>게시글 수정하기<==//

public void modify(final int bId,final String bName, final String bTitle, final String bContent) {
	==> Controller 에서 modify( ) 를 호출할때 매개변수로 (parameter) 를통해서 전달 
	==> update  또한 bId ( 게시물 고유번호 ) 를 기준으로 수정 
	String query = "update mvc_board set bName=?,bTitle=?,bContent=? where bId=?";
	this.template.update(query, new PreparedStatementSetter( ){
		@Override
		public void setValues(PreparedStatement ps) throws SQLException{
			ps.setString(1, bName);
			ps.setString(2, bTitle);
			ps.setString(3, bContent);
			ps.setInt(4,bId);
	}

});
}