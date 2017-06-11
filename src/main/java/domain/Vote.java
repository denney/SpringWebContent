package domain;

/**
 * Created by admin on 2017/6/12.
 */
public class Vote {
    private Long id;
    private String VoteName;
    private String postImgPath;
    private String options;

    public Vote() {
    }

    public Vote(String voteName, String postImgPath, String options) {
        VoteName = voteName;
        this.postImgPath = postImgPath;
        this.options = options;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoteName() {
        return VoteName;
    }

    public void setVoteName(String voteName) {
        VoteName = voteName;
    }

    public String getPostImgPath() {
        return postImgPath;
    }

    public void setPostImgPath(String postImgPath) {
        this.postImgPath = postImgPath;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", VoteName='" + VoteName + '\'' +
                ", postImgPath='" + postImgPath + '\'' +
                ", options='" + options + '\'' +
                '}';
    }
}
