package domain;

public class Wish {
    private int wishId;
    private String wishName;
    private String wishDescription;
    private String deadline;
    private String wishStatus;
    private String wishProgress;
    private String wishTarget;
    private int parentId;
    private int childId;


    public int getWishId() {
        return wishId;
    }

    public void setWishId(int wishId) {
        this.wishId = wishId;
    }

    public String getWishName() {
        return wishName;
    }

    public void setWishName(String wishName) {
        this.wishName = wishName;
    }

    public String getWishDescription() {
        return wishDescription;
    }

    public void setWishDescription(String wishDescription) {
        this.wishDescription = wishDescription;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getWishStatus() {
        return wishStatus;
    }

    public void setWishStatus(String wishStatus) {
        this.wishStatus = wishStatus;
    }

    public String getWishProgress() {
        return wishProgress;
    }

    public void setWishProgress(String wishProgress) {
        this.wishProgress = wishProgress;
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

    public String getWishTarget() {
        return wishTarget;
    }

    public void setWishTarget(String wishTarget) {
        this.wishTarget = wishTarget;
    }
}
