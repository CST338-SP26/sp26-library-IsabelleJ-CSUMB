import Utilities.Code;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Library {

    public static final int LENDING_LIMIT = 5;
    private HashMap<Book, Integer> books;
    private static int libraryCard;
    private String name;
    private List<Reader> readers;
    private HashMap<String, Shelf> shelves;

    public Library(String name) {
        this.name = name;
    }

    public Code addBook(Book newBook) {
        if(books.containsKey(newBook)) {
            books.put(newBook,books.get(newBook)+1);
            System.out.println(books.get(newBook) + " copies of " + newBook.getTitle() + "in the stacks.");
        } else {
            books.put(newBook, 1);
            System.out.println(newBook.getTitle() + " added to the stacks.");
        }

        if(shelves.containsKey(newBook.getSubject())) {
            shelves.get(newBook.getSubject()).addBook(newBook);
            return Code.SUCCESS;
        } else {
            System.out.println("No shelf for " + newBook.getSubject() + "books");
            return Code.SHELF_EXISTS_ERROR;
        }
    }

    private Code addBookToShelf(Book aBook, Shelf aShelf) {
        return null;
    }

    public Code addReader(Reader aReader) {
        return null;
    }

    public Code addShelf(Shelf aShelf) {
        return null;
    }

    public Code addShelf(String aString) {
        return null;
    }

    public Code checkOutBook(Reader reader, Book book) {
        Code tempCode;
        if(!readers.contains(reader)) {
            System.out.println(reader.getName() + " doesn't have an account here");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        } else {
            if(reader.getBookCount() >= LENDING_LIMIT) {
                System.out.println(reader.getName() + " has reached the lending limit, " + LENDING_LIMIT);
                return Code.BOOK_LIMIT_REACHED_ERROR;
            } else {
                if(!books.containsKey(book)) {
                    System.out.println("ERROR: could not find " + book);
                    return Code.SHELF_EXISTS_ERROR;
                } else if(books.get(book) < 1) {
                    System.out.println("ERROR: no copies of " + book + "remain");
                    return Code.BOOK_NOT_IN_INVENTORY_ERROR;
                } else {
                    tempCode = reader.addBook(book);
                    if (tempCode != Code.SUCCESS) {
                        System.out.println("Couldn't check out " + book);
                        return tempCode;
                    }
                    tempCode = shelves.get(book.getSubject()).removeBook(book);
                    if (tempCode == Code.SUCCESS) {
                        System.out.println(book + " checked out successfully");
                    }
                    return tempCode;
                }
            }
        }
    }

    public LocalDate convertDate(String dateString, Code aCode) {
        return null;
    }

    public static int convertInt(String aString, Code aCode) {
        return 0;
    }

    private Code errorCode(int num) {
        return null;
    }

    public Book getBookByISBN(String isbn) {
        return null;
    }

    public int getLibraryCardNumber() {
        return this.libraryCard;
    }

    public String getName(){
        return this.name;
    }

    public String getReaderByCard(int cardNum) {
        return null;
    }

    public Shelf getShelf(String shelf) {
        return null;
    }

    public Shelf getShelf(int shelfNum) {
        return null;
    }

    public Code init(String fileName) {
        int num;
        Code tempcode;
        Scanner sc = new Scanner(System.in);
        try {
            FileReader reader = new FileReader(fileName);
            num = convertInt(fileName, Code.BOOK_COUNT_ERROR);
            if (num < 0) {
                return errorHandler(num);
            }
            tempcode = initBooks(num, sc);
            if (tempcode != Code.SUCCESS) {
                return tempcode;
            }
            listBooks();

            num = convertInt(fileName, Code.SHELF_COUNT_ERROR);
            if (num < 0) {
                return errorHandler(num);
            }
            tempcode = initShelves(num, sc);
            if (tempcode != Code.SUCCESS) {
                return tempcode;
            }
            listShelves();

            num = convertInt(fileName, Code.READER_COUNT_ERROR);
            if (num < 0) {
                return errorHandler(num);
            }
            tempcode = initReader(num, sc);
            if (tempcode != Code.SUCCESS) {
                return tempcode;
            }
            listReaders();

        } catch (FileNotFoundException e) {
            return Code.FILE_NOT_FOUND_ERROR;
        }
        return Code.SUCCESS;
    }

    private Code initBooks(int bookCount, Scanner scan) {
        Book currentBook;
        String currentString;
        String[] components = new String[6];
        if (bookCount < 1) {
            return Code.LIBRARY_ERROR;
        }
        for (int i = 0; i < bookCount; i++) {
            currentBook = (new Book(null, null, null, -1, null, null));
            currentString = scan.nextLine();
            for (int j = 0; j < components.length; j++) {
                if(currentString.equals("")) {
                    return Code.BOOK_RECORD_COUNT_ERROR;
                }
                components[i] = currentString.substring(0, currentString.indexOf(","));
                currentString = currentString.substring(currentString.indexOf(","));
            }
            currentBook.setAuthor(components[Book.AUTHOR_]);
            currentBook.setISBN(components[Book.ISBN_]);
            currentBook.setDueDate(convertDate(components[Book.DUE_DATE_], Code.DATE_CONVERSION_ERROR));
            currentBook.setSubject(components[Book.SUBJECT_]);
            currentBook.setTitle(components[Book.TITLE_]);
            currentBook.setPageCount(convertInt(components[Book.PAGE_COUNT_], Code.PAGE_COUNT_ERROR));
            if(currentBook.getDueDate() == null) {
                return Code.DATE_CONVERSION_ERROR;
            } else if (currentBook.getPageCount() >1) {
                return Code.PAGE_COUNT_ERROR;
            }
            addBook(currentBook);
        }
        return Code.SUCCESS;
    }

    //TODO substring each element into a string array then set them according to the final values in the class def
    public Code initReader(int num, Scanner aScanner) {
        return null;
    }

    public Code initShelves(int shelfCount, Scanner scan) {
        Shelf currentShelf;
        String currentString;
        String[] components = new String[2];
        if (shelfCount < 1) {
            return Code.SHELF_COUNT_ERROR;
        }
        for (int i = 0; i < shelfCount; i++) {
            currentShelf = new Shelf();
            currentString = scan.nextLine();
            for (int j = 0; j < components.length; j++) {
                components[i] = currentString.substring(0, currentString.indexOf(","));
                currentString = currentString.substring(currentString.indexOf(","));
            }
            currentShelf.setSubject(components[Shelf.SUBJECT_]);
            currentShelf.setShelfNumber(convertInt(components[Shelf.SHELF_NUMBER_], Code.SHELF_NUMBER_PARSE_ERROR));

            if (currentShelf.getShelfNumber() > 1) {
                return Code.SHELF_NUMBER_PARSE_ERROR;
            }
            addShelf(currentShelf);

        }
        if(shelfCount == shelves.size()) {
            return Code.SUCCESS;
        } else {
            return Code.SHELF_NUMBER_PARSE_ERROR;
        }
    }

    public int listBooks() {
        StringBuilder sb = new StringBuilder();
        int totalBooks = 0;
        Object[] allBooks = books.keySet().toArray();
        for (int i = 0; i < allBooks.length; i++) {
            totalBooks += books.get(allBooks[i]);
            sb.append(books.get(allBooks[i]));
            sb.append(" copies of ");
            sb.append(allBooks[i]);
            sb.append("\n");
        }
        return totalBooks;
    }

    public int listReaders() {
        return 0;
    }

    public int listReaders(boolean aBool) {
        return 0;
    }

    public int listShelves(boolean aBool) {
        return 0;
    }

    public int listShelves() {
        return 0;
    }

    public Code removeReader(Reader aReader) {
        return null;
    }

    public Code returnBook(Reader reader, Book book) {
        if (!reader.hasBook(book)) {
            System.out.println(reader.getName() + " doesn't have" + book.getTitle() + " checked out");
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        } else {
            if(!books.containsKey(book)) {
                return Code.BOOK_NOT_IN_INVENTORY_ERROR;
            } else {
                System.out.println(reader.getName() + " is returning " + book.getTitle());
                Code temp = reader.removeBook(book);
                if (temp == Code.SUCCESS) {
                    return returnBook(book);
                } else {
                    System.out.println("Could not return " + book.getTitle());
                    return temp;
                }
            }
        }
    }

    public Code returnBook(Book book) {
        if (!shelves.containsKey(book.getSubject())) {
            System.out.println("No shelf for " + book.getTitle());
            return Code.SHELF_EXISTS_ERROR;
        } else {
            shelves.get(book.getTitle()).addBook(book);
            return Code.SUCCESS;
        }
    }

    public Code errorHandler(int errorNum) {
        if(errorNum == -2) {
            return Code.BOOK_COUNT_ERROR;
        } else if (errorNum == -4) {
            return Code.READER_COUNT_ERROR;
        } else if (errorNum == -6) {
            return Code.SHELF_COUNT_ERROR;
        } else {
            return Code.UNKNOWN_ERROR;
        }
    }

}
