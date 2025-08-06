package com.himedia.board.dto;

import lombok.Data;

@Data
public class Paging {
    private int page=1;
    private int totalCount;
    private int beginPage;
    private int endPage;
    private int displayRow=10;
    private int displayPage=10;
    private boolean prev;
    private boolean next;
    private int startNum;
    private int endNum;

    public void calPaging() {
        // 1. beginPage 와 endPage 계산 : page(현재 페이지)와 displayPage 값을 이용해서 계산
        double temp = page / (double)displayPage;
        // 1/10->0.1  5/10->0.5  15/10->1.5  25/10->2.5
        temp = Math.ceil(temp); // 소수점 올림해서 정수로 계산
        // 0.1->1  0.5->1  1.5->2  2.5->3

        endPage = (int)(temp * displayPage); // endPage = (int)(Math.ceil(page / (double)displayPage) * displayPage);
        // 1.0->10  2.0->20  3.0->30

        beginPage = endPage - (displayPage -1);
        // 10->1  20->11  30->21

        // 2. 멤버변수는 아니지만 중간연산에 필요한 totalPage를 계산
        int totalPage = (int)Math.ceil(totalCount / (double)displayRow);
        // 108/10->10.8->11   75/10->7.5->8

        // 3. prev 와 next를 결정
        if (totalPage < endPage) {
            endPage = totalPage;
            next = false;
        } else {
            next = true;
        }

        prev = beginPage ==1 ? false : true; // 시작페이지가 1인 경우만 prev=false;

        // 4. startNum, endNum
        startNum = (page - 1) * displayRow;
        endNum = page * displayRow;

        System.out.println("beginPage : " + beginPage + ", endPage : " + endPage + ", startNum : " + startNum + ", endNum : " + endNum);
    }
}
