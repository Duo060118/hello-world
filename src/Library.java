import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private Map<String, Book> books;
    private Map<String, User> users;
    private List<BorrowRecord> borrowRecords;
    private int recordIdCounter;

    public Library() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
        this.borrowRecords = new ArrayList<>();
        this.recordIdCounter = 1;
        initializeSampleData();
    }

    private void initializeSampleData() {
        addBook(new Book("B001", "Java编程思想", "Bruce Eckel", "978-7-111-21382-6", 5));
        addBook(new Book("B002", "深入理解计算机系统", "Randal E. Bryant", "978-7-111-54493-7", 3));
        addBook(new Book("B003", "设计模式：可复用面向对象软件的基础", "Erich Gamma", "978-7-111-07554-7", 4));
        addBook(new Book("B004", "算法导论", "Thomas H. Cormen", "978-7-111-40701-0", 6));
        addBook(new Book("B005", "Python编程：从入门到实践", "Eric Matthes", "978-7-115-42857-7", 5));

        addUser(new User("U001", "张三", "13800138001", "zhangsan@example.com"));
        addUser(new User("U002", "李四", "13800138002", "lisi@example.com"));
        addUser(new User("U003", "王五", "13800138003", "wangwu@example.com"));
    }

    public void addBook(Book book) {
        books.put(book.getBookId(), book);
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public Book findBookById(String bookId) {
        return books.get(bookId);
    }

    public User findUserById(String userId) {
        return users.get(userId);
    }

    public boolean borrowBook(String userId, String bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);

        if (user == null) {
            System.out.println("错误：用户不存在");
            return false;
        }

        if (book == null) {
            System.out.println("错误：图书不存在");
            return false;
        }

        if (!book.isAvailable()) {
            System.out.println("错误：图书已全部借出");
            return false;
        }

        if (!user.canBorrow()) {
            System.out.println("错误：用户借阅数量已达上限（最多5本）");
            return false;
        }

        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        user.setBorrowedCount(user.getBorrowedCount() + 1);

        String recordId = "R" + String.format("%04d", recordIdCounter++);
        BorrowRecord record = new BorrowRecord(recordId, userId, bookId);
        borrowRecords.add(record);

        System.out.println("借阅成功！借阅记录ID：" + recordId);
        System.out.println("借阅日期：" + record.getBorrowDate());
        System.out.println("应还日期：" + record.getDueDate());
        return true;
    }

    public boolean returnBook(String userId, String bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);

        if (user == null) {
            System.out.println("错误：用户不存在");
            return false;
        }

        if (book == null) {
            System.out.println("错误：图书不存在");
            return false;
        }

        BorrowRecord record = null;
        for (BorrowRecord r : borrowRecords) {
            if (r.getUserId().equals(userId) && r.getBookId().equals(bookId) && !r.isReturned()) {
                record = r;
                break;
            }
        }

        if (record == null) {
            System.out.println("错误：未找到未归还的借阅记录");
            return false;
        }

        record.returnBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        user.setBorrowedCount(user.getBorrowedCount() - 1);

        if (record.isOverdue()) {
            int overdueDays = record.getOverdueDays();
            System.out.println("归还成功！但已逾期 " + overdueDays + " 天");
        } else {
            System.out.println("归还成功！");
        }
        return true;
    }

    public List<BorrowRecord> getUserBorrowRecords(String userId) {
        List<BorrowRecord> userRecords = new ArrayList<>();
        for (BorrowRecord record : borrowRecords) {
            if (record.getUserId().equals(userId)) {
                userRecords.add(record);
            }
        }
        return userRecords;
    }

    public List<BorrowRecord> getOverdueRecords() {
        List<BorrowRecord> overdueRecords = new ArrayList<>();
        for (BorrowRecord record : borrowRecords) {
            if (!record.isReturned() && record.isOverdue()) {
                overdueRecords.add(record);
            }
        }
        return overdueRecords;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        return new ArrayList<>(borrowRecords);
    }
}