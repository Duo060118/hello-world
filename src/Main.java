import java.util.List;
import java.util.Scanner;

public class Main {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            printMenu();
            int choice = getIntInput("请输入选择：");

            switch (choice) {
                case 1:
                    showAllBooks();
                    break;
                case 2:
                    showAllUsers();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    showUserRecords();
                    break;
                case 6:
                    showOverdueRecords();
                    break;
                case 0:
                    exit = true;
                    System.out.println("感谢使用图书借阅系统，再见！");
                    break;
                default:
                    System.out.println("无效选择，请重新输入");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== 图书借阅系统 =====");
        System.out.println("1. 查看所有图书");
        System.out.println("2. 查看所有用户");
        System.out.println("3. 借阅图书");
        System.out.println("4. 归还图书");
        System.out.println("5. 查看用户借阅记录");
        System.out.println("6. 查看逾期记录");
        System.out.println("0. 退出");
        System.out.println("=========================");
    }

    private static void showAllBooks() {
        System.out.println("\n===== 图书列表 =====");
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("暂无图书");
            return;
        }
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private static void showAllUsers() {
        System.out.println("\n===== 用户列表 =====");
        List<User> users = library.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("暂无用户");
            return;
        }
        for (User user : users) {
            System.out.println(user);
        }
    }

    private static void borrowBook() {
        System.out.println("\n===== 借阅图书 =====");
        String userId = getStringInput("请输入用户ID：");
        String bookId = getStringInput("请输入图书ID：");
        library.borrowBook(userId, bookId);
    }

    private static void returnBook() {
        System.out.println("\n===== 归还图书 =====");
        String userId = getStringInput("请输入用户ID：");
        String bookId = getStringInput("请输入图书ID：");
        library.returnBook(userId, bookId);
    }

    private static void showUserRecords() {
        System.out.println("\n===== 用户借阅记录 =====");
        String userId = getStringInput("请输入用户ID：");
        List<BorrowRecord> records = library.getUserBorrowRecords(userId);
        if (records.isEmpty()) {
            System.out.println("该用户暂无借阅记录");
            return;
        }
        for (BorrowRecord record : records) {
            System.out.println(record);
        }
    }

    private static void showOverdueRecords() {
        System.out.println("\n===== 逾期记录 =====");
        List<BorrowRecord> records = library.getOverdueRecords();
        if (records.isEmpty()) {
            System.out.println("暂无逾期记录");
            return;
        }
        for (BorrowRecord record : records) {
            System.out.println(record);
            System.out.println("逾期天数：" + record.getOverdueDays() + " 天");
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的数字");
            }
        }
    }
}