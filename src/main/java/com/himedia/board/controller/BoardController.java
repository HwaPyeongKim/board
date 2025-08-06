package com.himedia.board.controller;

import com.himedia.board.dto.BoardDto;
import com.himedia.board.dto.Paging;
import com.himedia.board.service.BoardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

@Controller
public class BoardController {

    @Autowired
    BoardService bs;

    @Autowired
    ServletContext context;

    @GetMapping("/main")
    public ModelAndView mainPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HashMap<String, Object> result = new HashMap<>();
        result = bs.selectBoard(request);
        ArrayList<BoardDto> list = (ArrayList<BoardDto>) result.get("boardList");
        Paging paging = (Paging)result.get("paging");
        mav.addObject("boardList", list);
        mav.addObject("paging", paging);
//        mav.addObject("boardList", result.get("boardList"));
//        mav.addObject("paging", result.get("paging"));
        mav.setViewName("main");

        return mav;
    }

    @GetMapping("/boardView")
    public ModelAndView boardView(@RequestParam("num") int num) {
        ModelAndView mav = new ModelAndView();
        // HashMap<String, Object> result = new HashMap<String, object>();
        HashMap<String, Object> result = bs.getBoard(num);
        mav.addObject("board", result.get("board"));
        mav.addObject("replyList", result.get("replyList"));
        mav.setViewName("board/boardView");
        return mav;
    }

    @GetMapping("/insertBoardForm")
    public String insertBoardForm() {
        return "board/insertBoard";
    }

    @GetMapping("/selectimg")
    public String selectimg() {
        return "board/selectimg";
    }

    @PostMapping("/fileupload")
    public String fileupload(@RequestParam("image") MultipartFile file, HttpServletRequest request, Model model) {
        String path = context.getRealPath("/images");
        String filename = file.getOriginalFilename();
        Calendar today = Calendar.getInstance();
        long t = today.getTimeInMillis();
        String fn1 = filename.substring(0, filename.indexOf('.'));
        String fn2 = filename.substring(filename.indexOf('.'));
        String savefilename = fn1 + t + fn2;
        String uploadPath = path + "/" + savefilename;
        try {
            file.transferTo(new File(uploadPath));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("image", filename);
        model.addAttribute("savefilename", savefilename);
        return "board/completeUpload";
    }

    @PostMapping("/insertBoard")
    public ModelAndView insertBoard(@ModelAttribute("dto") @Valid BoardDto boarddto, BindingResult result, Model model) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/insertBoard");
        if (result.hasFieldErrors("title")) {
            model.addAttribute("msg", result.getFieldError("title").getDefaultMessage());
        } else if (result.hasFieldErrors("content")) {
            model.addAttribute("msg", result.getFieldError("content").getDefaultMessage());
        } else if (result.hasFieldErrors("pass")) {
            model.addAttribute("msg", result.getFieldError("pass").getDefaultMessage());
        } else {
            bs.insert(boarddto);
            int num = boarddto.getNum();

            HashMap<String, Object> board = bs.getBoard(num);
            mav.addObject("board", board.get("board"));
            mav.addObject("replyList", board.get("replyList"));
            mav.setViewName("board/boardView");
        }
        model.addAttribute("dto", boarddto);
        return mav;
    }

    /*
    @PostMapping("/insertBoard")
    public ModelAndView insertBoard(@Valid BoardDto boarddto, BindingResult result, @RequestParam("uploadImage") MultipartFile file, Model model) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/insertBoard");
        if (result.hasFieldErrors("title")) {
            model.addAttribute("msg", result.getFieldError("title").getDefaultMessage());
        } else if (result.hasFieldErrors("content")) {
            model.addAttribute("msg", result.getFieldError("content").getDefaultMessage());
        } else if (result.hasFieldErrors("pass")) {
            model.addAttribute("msg", result.getFieldError("pass").getDefaultMessage());
        } else {
            // 파일 업로드

            String path = content.getRealPath("/images");
            String filename = file.getOriginalFilename();
            Calendar today = Calendar.getInstance();
            long t = today.getTimeInMillis();
            String fn1 = filename.substring(0, filename.indexOf('.'));
            String fn2 = filename.substring(filename.indexOf('.'));
            String uploadFilePath = path + "/" + fn1 + t + fn2;
            String savefilename = fn1 + t + fn2;
            try {
                file.transferTo(new File(uploadFilePath));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            boarddto.setImage(filename);
            boarddto.setSavefilename(savefilename);
            bs.insert(boarddto);
            int num = boarddto.getNum();

            HashMap<String, Object> board = bs.getBoard(num);
            mav.addObject("board", board.get("board"));
            mav.addObject("replyList", board.get("replyList"));
            mav.setViewName("board/boardView");
        }
        model.addAttribute("dto", boarddto);
        return mav;
    }
    */

    @GetMapping("/updateBoardForm")
    public ModelAndView updateBoardForm(@RequestParam("num") int num) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("dto", bs.getBoardOne(num));
        mav.addObject("oldfilename", bs.getBoardOne(num).getSavefilename());
        mav.setViewName("changeBoard");
        return mav;
    }

    @PostMapping("/updateBoard")
    public String updateBoard(@ModelAttribute("dto") @Valid BoardDto boarddto, BindingResult result, @RequestParam("oldfilename") String oldfilename, Model model) {
        model.addAttribute("oldfilename", oldfilename);
        String url = "changeBoard";
        BoardDto bdto = bs.getBoardOne(boarddto.getNum());

        if (result.hasFieldErrors("pass")) {
            model.addAttribute("msg", result.getFieldError("pass").getDefaultMessage());
        } else if (result.hasFieldErrors("title")) {
            model.addAttribute("msg", result.getFieldError("title").getDefaultMessage());
        } else if (result.hasFieldErrors("content")) {
            model.addAttribute("msg", result.getFieldError("content").getDefaultMessage());
        } else if (!boarddto.getPass().equals(bdto.getPass())) {
            model.addAttribute("msg", "패스워드가 일치하지 않습니다.");
        } else {
            bs.update(boarddto);
            url = "redirect:/boardViewWithoutCnt?num="+bdto.getNum();
        }

        return url;
    }

    @GetMapping("/boardViewWithoutCnt")
    public ModelAndView boardViewWithoutCnt(@RequestParam("num") int num) {
        ModelAndView mav = new ModelAndView();
        HashMap<String, Object> result = bs.getBoardWithoutCnt(num);
        mav.addObject("board", result.get("board"));
        mav.addObject("replyList", result.get("replyList"));
        mav.setViewName("board/boardView");

        return mav;
    }

    @GetMapping("/deleteBoard")
    public String deleteBoard(@RequestParam("num") int num, @RequestParam("pass") String pass, Model model) {
        BoardDto bdto = bs.getBoardOne(num);
        if (bdto.getPass().equals(pass)) {
            bs.delete(num);
            return "redirect:/main";
        } else {
            model.addAttribute("num",num);
            return "board/deleteFail";
        }
    }

    @GetMapping("/updatePassOpenWindow")
    public String updatePass(@RequestParam("num") int num, Model model) {
        model.addAttribute("num",num);
        return "board/updatePass";
    }

    @PostMapping("/updatePass")
    public String updatePass(@RequestParam(value="oldPass", required = false) String oldpass, @RequestParam(value="newPass", required = false) String newpass, @RequestParam(value="confirmPass", required = false) String confirmpass, @RequestParam("num") int num, Model model) {
        String url = "board/updatePass";
        BoardDto bdto = bs.getBoardOne(num);
        model.addAttribute("num", num);
        if (oldpass == null || newpass == null || confirmpass == null) {
            model.addAttribute("msg","입력란을 모두 채워주세요.");
        } else if (!bdto.getPass().equals(oldpass)) {
            model.addAttribute("msg","기존 비밀번호가 일치하지 않습니다.");
        } else if (!newpass.equals(confirmpass)) {
            model.addAttribute("msg","새 비밀번호 확인이 일치하지 않습니다.");
        } else {
            bs.updatePass(num, newpass);
            url = "board/completeUpdatePass";
        }
        return url;
    }
}