import Utilities.Code;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reader {
    public static int CARD_NUMBER_ = 0;
    public static int NAME_ = 1;
    public static int PHONE_ = 2;
    public static int BOOK_COUNT_ = 3;
    public static int BOOK_START_ = 4;
    private int cardNumber;
    private String name;
    private String phone;
    private List<Book> books;

    public Reader(int cardNumber, String name, String phone) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.phone = phone;
        books = new ArrayList<Book>();
    }

    public Code addBook(Book aBook) {
        if(books.contains(aBook)) {
            return Code.BOOK_ALREADY_CHECKED_OUT_ERROR;
        }
        books.add(aBook);
        return Code.SUCCESS;
    }

    public Code removeBook(Book aBook) {
        try {
            if(!books.contains(aBook)) {
                return Code.READER_DOESNT_HAVE_BOOK_ERROR;
            } else {
                books.remove(aBook);
                return Code.SUCCESS;
            }
        } catch (Exception e) {
            return Code.READER_COULD_NOT_REMOVE_BOOK_ERROR;
        }
    }

    public boolean hasBook(Book abook) {
        if(books.contains(abook)) {
            return true;
        } else {
            return false;
        }
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public int getBookCount() {
        return books.size();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return cardNumber == reader.cardNumber && Objects.equals(name, reader.name) && Objects.equals(phone, reader.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, name, phone);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" (#");
        sb.append(cardNumber);
        sb.append(") has checked out ");
        sb.append(books);
        return sb.toString();
    }
}
