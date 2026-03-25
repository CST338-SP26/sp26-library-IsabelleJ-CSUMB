import java.time.LocalDate;
import java.util.Objects;

public class Book {
    public static final int AUTHOR_ = 0;
    public static final int DUE_DATE_ = 1;
    public static final int ISBN_ = 2;
    public static final int PAGE_COUNT_ = 3;
    public static final int SUBJECT_ = 4;
    public static final int TITLE_ = 5;
    private String author;
    private LocalDate dueDate;
    private String isbn;
    private int pageCount;
    private String subject;
    private String title;

    public Book(String author, String isbn, String subject, int pageCount, String title, LocalDate dueDate) {
        this.author = author;
        this.isbn = isbn;
        this.subject = subject;
        this.pageCount = pageCount;
        this.title = title;
        this.dueDate = dueDate;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getISBN() {
        return isbn;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return AUTHOR_ == book.AUTHOR_ && ISBN_ == book.ISBN_ && PAGE_COUNT_ == book.PAGE_COUNT_ && SUBJECT_ == book.SUBJECT_ && TITLE_ == book.TITLE_ && pageCount == book.pageCount && Objects.equals(author, book.author) && Objects.equals(isbn, book.isbn) && Objects.equals(subject, book.subject) && Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(AUTHOR_, ISBN_, PAGE_COUNT_, SUBJECT_, TITLE_, author, isbn, pageCount, subject, title);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title);
        sb.append(" by ");
        sb.append(author);
        sb.append(" ISBN: ");
        sb.append(isbn);
        return sb.toString();
    }
}

