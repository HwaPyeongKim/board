package com.himedia.board.controller;

import com.google.gson.Gson;
import com.himedia.board.dto.KakaoProfile;
import com.himedia.board.dto.MemberDto;
import com.himedia.board.dto.OAuthToken;
import com.himedia.board.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class MemberController {

    @Autowired
    MemberService ms;

    @GetMapping("/")
    public String index() {
        return "member/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("dto") @Valid MemberDto memberdto, BindingResult result, HttpServletRequest request, Model model) {
        String url = "member/loginForm";
        if (result.hasFieldErrors("userid")) {
            model.addAttribute("msg", result.getFieldError("userid").getDefaultMessage());
        } else if (result.hasFieldErrors("pwd")) {
            model.addAttribute("msg", result.getFieldError("pwd").getDefaultMessage());
        } else {
            MemberDto mdto = ms.getMember(memberdto.getUserid());
            if (mdto == null) {
                model.addAttribute("msg","아이디와 패스워드를 확인하세요.");
            } else if (!memberdto.getPwd().equals(mdto.getPwd())) {
                model.addAttribute("msg","아이디와 패스워드를 확인하세요.");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("loginUser", mdto);
                url = "redirect:/main";
            }
        }
        return url;
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "member/joinForm";
    }

    @GetMapping("/idcheck")
    public String idcheck(@RequestParam("userid") String userid, Model model) {
        MemberDto mdto = ms.getMember(userid);
        if (mdto == null) {
            model.addAttribute("result","1");
        } else {
            model.addAttribute("result","0");
        }
        model.addAttribute("userid", userid);
        return "member/idcheck";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("dto") @Valid MemberDto memberdto, BindingResult result, @RequestParam(value="reid", required = false) String reid, @RequestParam(value="pwd_check", required = false) String pwd_check, Model model) {
        String url = "member/joinForm";
        model.addAttribute("reid", reid);
        if (result.hasFieldErrors("userid")) {
            model.addAttribute("msg", result.getFieldError("userid").getDefaultMessage());
        } else if (!memberdto.getUserid().equals(reid)) {
            model.addAttribute("msg","아이디 중복체크를 해주세요.");
        } else if (!pwd_check.equals(memberdto.getPwd())) {
            model.addAttribute("msg","패스워드가 일치하지 않습니다.");
        } else if (result.hasFieldErrors("pwd")) {
            model.addAttribute("msg", result.getFieldError("pwd").getDefaultMessage());
        } else if (result.hasFieldErrors("name")) {
            model.addAttribute("msg", result.getFieldError("name").getDefaultMessage());
        } else if (result.hasFieldErrors("email")) {
            model.addAttribute("msg", result.getFieldError("email").getDefaultMessage());
        } else if (result.hasFieldErrors("phone")) {
            model.addAttribute("msg", result.getFieldError("phone").getDefaultMessage());
        } else {
            ms.insert(memberdto);
            model.addAttribute("msg","회원가입이 완료되었습니다.");
            url = "member/loginForm";
        }

        return url;
    }

    @GetMapping("/kakaostart")
    public @ResponseBody String kakaostart() {
        String exeCode = "<script type='text/javascript'>"
                + "location.href='https://kauth.kakao.com/oauth/authorize"
                + "?client_id=4559dcf2c48f8e357f069128599fc0ed"
                + "&redirect_uri=http://localhost:8070/kakaoLogin"
                + "&response_type=code'"
                + "</script>";

        return exeCode;
    }

    @GetMapping("/kakaoLogin")
    public String kakaoLogin(@RequestParam("code") String code, Model model, HttpServletRequest request) throws IOException {
        // System.out.println("code:" + code);

        // 개인정보 요청을 위한 2차 토큰을 요청합니다
        // 요청할 url 과 전달인수 설정
        String endpoint = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(endpoint);

        String bodyData = "grant_type=authorization_code";
        bodyData += "&client_id=4559dcf2c48f8e357f069128599fc0ed";
        bodyData += "&redirect_uri=http://localhost:8070/kakaoLogin";
        bodyData += "&code=" + code;
        // url 객체에 bodyData 를 더해서 요청합니다.

        // Stream 연결 // import - java.net.HttpUrlConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // http header 값 넣기 (요청 내용에 헤더 추가)
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        conn.setDoOutput(true); // 송신설정
        BufferedWriter bw = new BufferedWriter(
          new OutputStreamWriter(conn.getOutputStream(),"UTF-8")
        );
        bw.write(bodyData);
        bw.flush(); // 실제송신 시점

        // 요청에 의한 수신 객체 생성
        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8")
        );
        String input = "";
        StringBuilder sb = new StringBuilder(); // 조각난 String 을 조럽하기 위한 객체
        while ((input=br.readLine()) != null) { // 응답 수신
            sb.append(input); // 수신 내용의 누적
            // System.out.println(input); // 수신 내용을 그때 그때 출력
        }

        /*
{
"access_token":"QbzL9A1qqFxq052gev9h5vSUhdlYLCSqAAAAAQoXEO8AAAGYQMfsLiJyl_dg0lnq"
,"token_type":"bearer"
,"refresh_token":"-hQZS6E00Gfx3llTUkuJMgRzmLsesHzTAAAAAgoXEO8AAAGYQMfsIyJyl_dg0lnq"
,"expires_in":21599,"scope":"profile_image profile_nickname"
,"refresh_token_expires_in":5183999
}
        * */

        Gson gson = new Gson();
        // sb -> oAuthToken 복사
        OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);
        // System.out.println("oAuthToken.accessToken : " + oAuthToken.getAccess_token());

        // 2차 accessToken을 이용하여 실제 개인 정보 요청 & 수신
        System.out.println();
        endpoint = "https://kapi.kakao.com/v2/user/me";
        url = new URL(endpoint);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer " + oAuthToken.getAccess_token());
        conn.setDoOutput(true);
        br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8")
        );
        input = "";
        sb = new StringBuilder();
        while ((input=br.readLine()) != null) {
            sb.append(input);
            // System.out.println(input); // 수신한 내용을 콘솔창에 출력
        }

        /*
{
"id":
,"connected_at":"2025-07-25T08:51:41Z"
,"properties":{
    "nickname":"김화평"
    ,"profile_image":"http://k.kakaocdn.net/dn/cbyBLi/btsNA6ydFGI/Cp3EyKRmZJCHHqwgHlcwyK/img_640x640.jpg"
    ,"thumbnail_image":"http://k.kakaocdn.net/dn/cbyBLi/btsNA6ydFGI/Cp3EyKRmZJCHHqwgHlcwyK/img_110x110.jpg"
}
,"kakao_account":{
    "profile_nickname_needs_agreement":false
    ,"profile_image_needs_agreement":false
    ,"profile":{
        "nickname":"김화평"
        ,"thumbnail_image_url":"http://k.kakaocdn.net/dn/cbyBLi/btsNA6ydFGI/Cp3EyKRmZJCHHqwgHlcwyK/img_110x110.jpg"
        ,"profile_image_url":"http://k.kakaocdn.net/dn/cbyBLi/btsNA6ydFGI/Cp3EyKRmZJCHHqwgHlcwyK/img_640x640.jpg"
        ,"is_default_image":false
        ,"is_default_nickname":false}
    }
}
        * */

        gson = new Gson();
        KakaoProfile kakaoProfile = gson.fromJson(sb.toString(), KakaoProfile.class);

        System.out.println("id : " + kakaoProfile.getId());
        KakaoProfile.KakaoAccount ac = kakaoProfile.getKakao_account();
        KakaoProfile.KakaoAccount.Profile pf = ac.getProfile();
        // System.out.println("nickname : " + pf.getNickname());

        String id = kakaoProfile.getId();
        String nickname = pf.getNickname();

        // 전송된 개인 정보를 이용해서 회원가입 & 로그인을 진행합니다.
        MemberDto mdto = ms.getMember(id);
        // mdto 가 null 이면 회원가입 후 로그인을 진행
        // null 이 아니면 로그인만 진행
        if (mdto == null) {
            mdto = new MemberDto();
            mdto.setUserid(id);
            mdto.setName(nickname);
            mdto.setEmail(nickname);
            mdto.setProvider("kakao");
            ms.insert(mdto);
        }
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", mdto);
        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");
        return "member/loginForm";
    }

    @GetMapping("/updateMemberForm")
    public String updateMemberForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDto mdto = (MemberDto)session.getAttribute("loginUser");
        model.addAttribute("dto", mdto);
        return "member/updateForm";
    }

    @PostMapping("/updateMember")
    public String updateMember(@ModelAttribute("dto") @Valid MemberDto memberdto, BindingResult result, @RequestParam(value="pwd_check", required = false) String pwd_check, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String url = "member/updateForm";
        if (result.hasFieldErrors("pwd") && memberdto.getProvider() == null) {
            model.addAttribute("msg", result.getFieldError("pwd").getDefaultMessage());
        } else if (!memberdto.getPwd().equals(pwd_check) && memberdto.getProvider() == null) {
            model.addAttribute("msg", result.getFieldError("pwd_check").getDefaultMessage());
        } else if (result.hasFieldErrors("name")) {
            model.addAttribute("msg", result.getFieldError("name").getDefaultMessage());
        } else if (result.hasFieldErrors("email")) {
            model.addAttribute("msg", result.getFieldError("email").getDefaultMessage());
        } else if (result.hasFieldErrors("phone")) {
            model.addAttribute("msg", result.getFieldError("phone").getDefaultMessage());
        } else {
            ms.update(memberdto);
            session.setAttribute("loginUser", memberdto);
            url = "redirect:/main";
        }
        return url;
    }

    @GetMapping("/deleteMember")
    public String deleteMember(@RequestParam("userid") String userid, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");
        ms.deleteMember(userid);
        model.addAttribute("msg","회원 탈퇴가 완료되었습니다.");
        return "member/loginForm";
    }
}