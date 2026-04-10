import Utilities.Code;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
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

//    private Code addBookToShelf(Book aBook, Shelf aShelf) {
//        return null;
//    }

    public Code addReader(Reader reader) {
        if(readers.contains(reader)) {
            System.out.println(reader.getName() + " already has an account!");
            return Code.READER_ALREADY_EXISTS_ERROR;
        } else if(getReaderByCard(reader.getCardNumber()) != null) {
            System.out.println(getReaderByCard(reader.getCardNumber()).getName() + " and " + reader.getName() + "have the same card number!");
            return Code.READER_CARD_NUMBER_ERROR;
        } else {
            readers.add(reader);
            System.out.println(reader.getName() + " added to the library!");
        }
        if(reader.getCardNumber() > libraryCard) {
            libraryCard = reader.getCardNumber();
        }
        return Code.SUCCESS;
    }

    public Code addShelf(Shelf aShelf) {
        if(shelves.containsKey(aShelf.getSubject())) {
            System.out.println("ERROR: Shelf already exists" + shelves.get(aShelf.getSubject()));
            return Code.SHELF_EXISTS_ERROR;
        } else {
            aShelf.setShelfNumber(shelves.size() + 1);
            shelves.put(aShelf.getSubject(), aShelf);
            Object[] allBooks = books.keySet().toArray();
            Book temp;
            for (int i = 0; i < allBooks.length; i++) {
                temp = (Book) allBooks[i];
                if (temp.getSubject().equals(aShelf.getSubject())) {
                    shelves.get(aShelf.getSubject()).addBook(temp);
                }
            }
        }
        return Code.SUCCESS;
    }

    public Code addShelf(String shelfSubject) {
        Shelf tempShelf = new Shelf();
        tempShelf.setSubject(shelfSubject);
        return addShelf(tempShelf);
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

    public LocalDate convertDate(String date, Code errorCode) {
        String temp;
        int num[] = new int[3];
        if(date.equals("0000")) {
            return LocalDate.EPOCH;
        } else {
            for (int i = 0; i < num.length; i++) {
                if (date.isEmpty()) {
                    System.out.println("Error: date conversion error, could not parse: " + date);
                    System.out.println("Using default date (01-jan-1970)");
                    return LocalDate.EPOCH;
                }
                temp = date.substring(0, date.indexOf("-"));
                date = date.substring(date.indexOf("-"));
                num[i] = convertInt(temp, Code.DATE_CONVERSION_ERROR);
            }
            if(num[2] < 0) {
                System.out.println("Error converting date: Year " + num[2]);
                System.out.println("Using default date (01-jan-1970)");
                return LocalDate.EPOCH;
            } else if (num[1] < 0) {
                System.out.println("Error converting date: month " + num[1]);
                System.out.println("Using default date (01-jan-1970)");
                return LocalDate.EPOCH;
            } else if (num[0] < 0) {
                System.out.println("Error converting date: Day " + num[0]);
                System.out.println("Using default date (01-jan-1970)");
                return LocalDate.EPOCH;
            }
        }
        return LocalDate.of(num[2],num[1],num[0]);
    }

    public static int convertInt(String recordCountString, Code code) {
        Integer processedInt = -101;
        try {
            recordCountString.trim();
            processedInt = Integer.parseInt(recordCountString);
        } catch (NumberFormatException exception) {
            if (code == Code.BOOK_COUNT_ERROR) {
                System.out.println("Error: Could not read number of books");
                return -2;
            } else if (code == Code.PAGE_COUNT_ERROR) {
                System.out.println("Error: could not parse page count");
                return -8;
            } else if (code == Code.DATE_CONVERSION_ERROR) {
                System.out.println("Error: Could not parse date component");
                return -101;
            } else {
                System.out.println("Error: Unknown conversion error");
            }
        }
        return processedInt;
    }

    private Code errorCode(int codeNumber) {
        for (Code code : Code.values()) {
            if (code.getCode() == codeNumber) {
                return code;
            }
        }
        return Code.UNKNOWN_ERROR;
    }


    public Book getBookByISBN(String isbn) {
        Object[] allBooks = books.keySet().toArray();
        Book temp;
        for (int i = 0; i < allBooks.length; i++) {
            temp = (Book) allBooks[i];
            if((temp.getISBN().equals(isbn))) {
                return temp;
            }
        }
        System.out.println("ERROR: Could not find a book with isbn: " + isbn);
        return null;
    }

    public int getLibraryCardNumber() {
        return this.libraryCard + 1;
    }

    public String getName(){
        return this.name;
    }

    public Reader getReaderByCard(int cardNumber) {
        for (int i = 0; i < readers.size(); i++) {
            if(readers.get(i).getCardNumber() == cardNumber) {
                return readers.get(i);
            }
        }
        System.out.println("Could not find a reader with Card #" + cardNumber);
        return null;
    }

    public Shelf getShelf(String shelf) {
        return shelves.get(shelf);
    }

    public Shelf getShelf(int shelfNumber) {
        String[] allShelves = (String[]) shelves.keySet().toArray();
        for (int i = 0; i < allShelves.length; i++) {
            if(shelves.get(allShelves[i]).getShelfNumber() == shelfNumber) {
                return shelves.get(allShelves[i]);
            }
        }
        System.out.println("No shelf number " + shelfNumber + "found");
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

    public Code initReader(int readerCount, Scanner scan) {
        Reader currentReader;
        String currentString;
        Book checkBook;
        String[] components = new String[5];
        if (readerCount < 1) {
            return Code.READER_COUNT_ERROR;
        }
        for (int i = 0; i < readerCount; i++) {
            currentReader = (new Reader(-1, null, null));
            currentString = scan.nextLine();
            for (int j = 0; j < components.length; j++) {
                if(currentString.equals("")) {
                    return Code.BOOK_RECORD_COUNT_ERROR;
                }
                components[i] = currentString.substring(0, currentString.indexOf(","));
                currentString = currentString.substring(currentString.indexOf(","));
            }
            checkBook = getBookByISBN(components[Reader.BOOK_COUNT_]);
            if (!books.containsKey(checkBook)) {
                System.out.println("ERROR");
            }
            currentReader.setName(components[Reader.NAME_]);
            currentReader.setPhone(components[Reader.PHONE_]);
            currentReader.getBooks().get(i).setDueDate(convertDate(components[Reader.BOOK_START_], Code.DUE_DATE_ERROR));
            currentReader.setCardNumber(convertInt(components[Reader.CARD_NUMBER_], Code.READER_CARD_NUMBER_ERROR));
            checkOutBook(currentReader, currentReader.getBooks().get(i));
            addReader(currentReader);

        }
        return Code.SUCCESS;
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
        System.out.println(readers.toString());
        return readers.size();
    }

    public int listReaders(boolean aBool) {
       StringBuilder sb = new StringBuilder();
        if (aBool == true) {
           for (int i = 0; i < readers.size(); i++) {
               sb.append(readers.get(i).getName());
               sb.append(" (#");
               sb.append(readers.get(i).getCardNumber());
               sb.append("\n");
           }
       } else {
           listReaders();
           }
       return readers.size();
    }

    public int listShelves(boolean showBooks) {
        String[] allShelves = (String[]) shelves.keySet().toArray();
        for (int i = 0; i < shelves.size(); i++) {

            if(showBooks == true) {
                shelves.get(allShelves[i]).listBooks();
            } else {
                System.out.println(allShelves[i]);
            }
        }
        return shelves.size();
    }

    public int listShelves() {
        return listShelves(false);
    }

    public Code removeReader(Reader reader) {
        if(readers.contains(reader) && !reader.getBooks().isEmpty()) {
            System.out.println(reader.getName() + " must return all books!");
            return Code.READER_STILL_HAS_BOOKS_ERROR;
        } else if (!readers.contains(reader)) {
            System.out.println(reader.getName() + " is not part of this library");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }
        readers.remove(reader);
        return Code.SUCCESS;
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
