package tai.models.tags;

public class TagData {
    private String tag;
    private Integer count;

    public TagData(String tag) {
        this.tag = tag;
        this.count = 1;
    }

    public TagData(String tag, Integer count) {
        this.tag = tag;
        this.count = count;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void incrementCount(){
        this.count++;
    }
}
