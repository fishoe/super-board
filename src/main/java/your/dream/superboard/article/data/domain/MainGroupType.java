package your.dream.superboard.article.data.domain;

public enum MainGroupType {
    AUTHORIZED("AUTHORIZED"),
    AUTHENTICATED("AUTHENTICATED"),
    MIXED("MIXED"),
    ANONYMOUS("ANONYMOUS");

    private String type;

    MainGroupType(String type) {
        this.type = type;
    }
}
