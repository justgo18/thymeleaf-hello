package com.testborad.youtubeborad.controller;


import com.testborad.youtubeborad.entity.Board;
import com.testborad.youtubeborad.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class Boardcontroller {


    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")  //localhost:8080/board/write

    public String boardWriteForm() {

        return "boardwrite"; // templates>boardwrite.html 리턴
    }

    @PostMapping("/board/writepro") //url 이랑 form테그 url이랑 일치해야함
    public String boardwritepro(Board board, Model model, MultipartFile file) throws Exception {

        boardService.write(board, file);



            model.addAttribute("message", "글 작성이 완료되었습니다.");
            model.addAttribute("searchUrl", "/board/list");
        return "message";

    }



      //board를 받아옴

    @GetMapping("/board/list")
    public String boardlist(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword) {

        Page<Board> list = null;

        if(searchKeyword == null){
             list = boardService.boardList(pageable);
        }else {
             list = boardService.boardSearchList(searchKeyword, pageable);
        }



        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1) ;
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage );




        return "boardlist";
    } //데이터를 받아서 페이지로 보냄

    @GetMapping("/board/view") //localhost:8080/board/view?id=1
    public String boardview(Model model, Integer id) {

        model.addAttribute("board", boardService.boardview(id));
        return "boardview";

    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id) {

        boardService.boardDelete(id);
        return "redirect:/board/list";

    }


    @GetMapping("/board/modify/{id}") //Pathvariable은 주소상의 {id}부분을 실행해서 인티저 부분의 id로 들어온다.
    public String boardModify(@PathVariable("id") Integer id,
                              Model model){

        model.addAttribute("board", boardService.boardview(id));


        return "boardmodify";
    }

    @PostMapping("board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, MultipartFile file) throws Exception{

        Board boardTemp = boardService.boardview(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardService.write(boardTemp, file);

        return "redirect:/board/list";

    }

}
