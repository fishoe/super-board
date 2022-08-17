package your.dream.superboard.users.user.authentication;

public enum AuthenticationType {
    USER("USER"),
    NAVER("NV"),
    KAKAO("KKO");

    private String type;
    AuthenticationType(String type) {
        this.type = type;
    }
}
