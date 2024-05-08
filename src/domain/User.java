package domain;

public class User {
	    private String UserId;
	    private String Password;
	    private boolean isParent;

	    public String getUserId() {
	        return UserId;
	    }

	    public void setUserId(String UserId) {
	        this.UserId = UserId;
	    }

	    public String getPassword() {
	        return Password;
	    }

	    public void setPassword(String Password) {
	        this.Password = Password;
	    }

	    public boolean isParent() {
	        return isParent;
	    }

	    public void setParent(boolean parent) {
	        isParent = parent;
	    }
	
}
