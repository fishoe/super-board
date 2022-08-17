package your.dream.superboard.article.data.domain;

public enum GroupRoleType {
    // 익명(로그인 안됨) > 인증된 사용자 > 권한 부여된 사용자 > 서포터 > 매니저 > 관리자
    ADMIN("ADMIN"), // 관리자 권한
    MANAGER("MANAGER"), // 매니저 권한
    SUPPORTER("SUPPORTER"), // 서포터 권한
    AUTHORITY("AUTHORITY"), // 권한 부여된 사용자
    USER("USER"), // 인증된 사용자
    ANONYMOUS("ANONYMOUS"); // 익명 사용자

    private String name;

    GroupRoleType(String name) {
        this.name = name;
    }
}
