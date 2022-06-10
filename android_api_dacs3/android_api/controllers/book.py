import cloudinary.uploader

from android_api.models import Book, User, Category_Book, Category, Library
from android_api import db


def create_book(bookName, bookImage, bookAuthor, bookDescription):
    try:
        book = Book(bookName, bookImage, bookAuthor, bookDescription, "123", "123")
        db.session.add(book)
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def show_all_book():
    try:
        book = db.session.query(Book.id_book, Book.bookName, Book.bookImage, User.userFirstname, User.id_user,
                                Book.bookDescription, Book.bookStatus, Book.bookNumberLove).join(User,
                                                                                                 User.id_user == Book.bookAuthor,
                                                                                                 isouter=True).all()
        data = []
        for item in book:
            data.append({
                "id_book": item.id_book,
                "bookName": item.bookName,
                "bookImage": item.bookImage,
                "bookAuthor": item.userFirstname,
                "id_user": item.id_user,
                "bookDescription": item.bookDescription,
            })
        return data
    except Exception as e:
        print(e)
        return None


def show_by_id_book(id_book):
    try:
        book = db.session.query(Book.id_book, Book.bookName, Book.bookImage, User.userFirstname, User.id_user,
                                Book.bookDescription, Book.bookStatus, Book.bookNumberLove).filter_by(id_book = id_book).join(User,
                                                                                                 User.id_user == Book.bookAuthor,
                                                                                                 isouter=True).all()

        data = []
        for item in book:
            data.append({
                "id_book": item.id_book,
                "bookName": item.bookName,
                "bookImage": item.bookImage,
                "bookAuthor": item.userFirstname,
                "id_user": item.id_user,
                "bookDescription": item.bookDescription,
                "bookStatus": item.bookStatus,
                "bookNumberLove": item.bookNumberLove
            })
        return data
    except Exception as e:
        print(e)
        return None


def update_book(id_book, bookName, bookImage, bookDescription):
    try:
        book = Book.query.filter_by(id_book = id_book).first()
        book.bookName = bookName
        cloudinary.config(cloud_name="dqbktgymd", api_key="187563468568422",
                          api_secret="4kUFB1aVl8rf3Tv9kl2nFOSA3vg")
        des_book = book.bookImage
        if des_book:
            cut = des_book.split('/')
            cloudinary.uploader.destroy(cut[len(cut)-1])
        book.bookImage = bookImage
        book.bookDescription = bookDescription
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def delete_book(id_book):
    try:
        book = Book.query.get(id_book)
        db.session.delete(book)
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def category_book_show():
    # try:
        book_category = db.session.query(Book.id_book, Book.bookName, Book.bookImage, User.userFirstname, Book.bookDescription, Book.bookStatus,Book.bookNumberLove, Category_Book.id_category)\
            .join(User, User.id_user == Book.bookAuthor, isouter=True)\
            .join(Category_Book, Category_Book.id_book == Book.id_book, isouter=True).all()
        category = Category.query.all()
        data = []
        data1 = []
        for item in category:
            for jtem in book_category:
                if item.id_category == jtem.id_category:
                    print(item.id_category, jtem.id_category)
                    data1.append({
                        "id_book": jtem.id_book,
                        "bookName": jtem.bookName,
                        "bookImage": jtem.bookImage,
                        "bookAuthor": jtem.userFirstname,
                        "bookDescription": jtem.bookDescription,
                        "bookStatus": jtem.bookStatus,
                        "bookNumberLove": jtem.bookNumberLove,
                        "id_category": jtem.id_category
                    })
            data.append({
                "id_category": item.id_category,
                "categoryName": item.categoryName,
                "book": data1
            })
        return data
    # except Exception as e:
    #     print(e)
    #     return None


def read_your_library(id_user):
    try:
        book = db.session.query(Book.id_book, Book.bookName, Book.bookImage, User.userFirstname,
                                Book.bookDescription).join(User,
                                                        User.id_user == Book.bookAuthor,
                                                        isouter=True).all()
        book_library = db.session.query(Book.id_book, Book.bookName, Book.bookImage, User.userFirstname, User.id_user,
                                         Book.bookDescription) \
                                        .join(Library, Library.id_book == Book.id_book, isouter=True) \
                                        .join(User, User.id_user == Library.id_user, isouter=True)\
                                        .filter_by(id_user = id_user).all()

        data = []
        for book in book:
            for item in book_library:
                if item.id_book == book.id_book:
                    data.append({
                        "id_book": book.id_book,
                        "bookName": book.bookName,
                        "bookImage": book.bookImage,
                        "bookAuthor": book.userFirstname,
                        "bookDescription": book.bookDescription
                    })
        return data

    except Exception as e:
        print(e)
        return None


