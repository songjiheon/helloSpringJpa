package kr.ac.hansung.cse.exception;

public class DuplicateCategoryException extends RuntimeException {

    private String categoryName;

    /*커스텀 예외 생성
    * 중복된 카테고리가 존재 시 메시지
    */
    public DuplicateCategoryException(String name) {
        super("이미 존재하는 카테고리입니다 : " + name);
        this.categoryName = name;
    }


    public String getCategoryName() {
        return categoryName;
    }

}
