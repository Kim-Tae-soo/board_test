package main.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.service.BoardService;
import main.service.BoardVO;

@Controller
public class BoardController {
	
	@Resource(name="boardService")
	private BoardService boardService; 
	
	@RequestMapping("/boardWrite.do")
	public String boardWrite() {
		
		return "board/boardWrite";
	}
	
	@RequestMapping("/boardWriteSave.do")
	@ResponseBody
	public String insertNBoard(BoardVO vo) throws Exception {
		
		
		String result = boardService.insertNBoard(vo);
		String msg = "";
		if(result==null) msg = "ok";
		else msg = "fail";
		
		return msg;
		
	}
	
	@RequestMapping("/boardList.do")
	public String selectNBoardList(BoardVO vo, ModelMap model) throws Exception{
		
		int unit = 5;
		
		//총 데이터 개수
		int total = boardService.selectNBoardTotal(vo);
		
		// 데이터 개수 / 10 = 한 화면에 예를 들면 12개 데이터면 - > 2개의 페이지가 나와야함.
		int totalPage = (int)Math.ceil((double)total/unit);
		
		int viewPage = vo.getViewPage();
		// 1->10 = 1 / 11-20=2 ...
		//startIndex : (1-1)*10 +1
		//endIndex : (2-1)*10+1
		int startIndex = (viewPage-1)*unit+1;
		int endIndex = startIndex+(unit-1);
		
		
		//페이지 번호 역순
		//total - 34 (4개의 페이지
		//1->34 2->24
		/*
		 * int pg1 = total - 0; int pg2 = total - 10; int pg3 = total - 20; int pg4 =
		 * total - 30;
		 */
		int startRowNo = total - (viewPage-1)*unit;
		
		vo.setStartIndex(startIndex);
		vo.setEndIndex(endIndex);
		
		
		
		List<?> list = boardService.selectNBoardList(vo);
		System.out.println("list : "+list);
		
		model.addAttribute("rowNumber",startRowNo);
		model.addAttribute("total",total);
		model.addAttribute("totalPage",totalPage);
		model.addAttribute("resultList", list);
		
		return "board/boardList";
	}
	
}
