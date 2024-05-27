package domain;

/**
 * 用来存放正在使用系统的人的身份
 */
public class Temp {
    private int parentId;
    private int childId;
    private boolean isParent;
    private String name;

    public Temp(int parentId, int childId, boolean isParent, String name) {
        this.parentId = parentId;
        this.childId = childId;
        this.isParent = isParent;
        this.name = name;
    }
    public Temp(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public Temp(int parentId, int childId, boolean isParent) {
        this.parentId = parentId;
        this.childId = childId;
        this.isParent = isParent;
    }

    public Temp(int parentId) {
        this.parentId = parentId;
    }
}
