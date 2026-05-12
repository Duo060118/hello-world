public class User {
    private String userId;
    private String name;
    private String phone;
    private String email;
    private int borrowedCount;
    private static final int MAX_BORROW_LIMIT = 5;

    public User(String userId, String name, String phone, String email) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.borrowedCount = 0;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void setBorrowedCount(int borrowedCount) {
        this.borrowedCount = borrowedCount;
    }

    public boolean canBorrow() {
        return borrowedCount < MAX_BORROW_LIMIT;
    }

    public int getRemainingBorrowLimit() {
        return MAX_BORROW_LIMIT - borrowedCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", borrowedCount=" + borrowedCount +
                '}';
    }
}