from . import db


class User(db.Model):
    id_user = db.Column(db.Integer, primary_key=True, autoincrement=True)
    userEmail = db.Column(db.String())
    userPassword = db.Column(db.String())
    role = db.Column(db.Integer())
    userFirstname = db.Column(db.String())
    userCity = db.Column(db.String())
    userAge = db.Column(db.Integer())
    userDayofbirth = db.Column(db.String())
    userImage1 = db.Column(db.String())
    userImage2 = db.Column(db.String())
    userAndress = db.Column(db.String())
    userPhone = db.Column(db.String())
    userDescription = db.Column(db.String())
    user_book = db.relationship("Book")
    user_library = db.relationship("Library")
    user_comment = db.relationship("Comment")

    def __init__(self, userEmail, userPassword, role, userFirstname, userCity, userAge, userDayofbirth, userImage1, userImage2, userAndress, userPhone, userDescription):
        self.userEmail = userEmail
        self.userPassword = userPassword
        self.role = role
        self.userFirstname = userFirstname
        self.userCity = userCity
        self.userAge = userAge
        self.userDayofbirth = userDayofbirth
        self.userImage1 = userImage1
        self.userImage2 = userImage2
        self.userAndress = userAndress
        self.userPhone = userPhone
        self.userDescription = userDescription


class Category(db.Model):
    id_category = db.Column(db.Integer, primary_key=True, autoincrement=True)
    categoryName = db.Column(db.String())
    categoryImage = db.Column(db.String())
    category_category_book = db.relationship("Category_Book")

    def __init__(self, categoryName, categoryImage ):
        self.categoryName = categoryName
        self.categoryImage = categoryImage


class Book(db.Model):
    id_book = db.Column(db.Integer, primary_key=True, autoincrement=True)
    bookName = db.Column(db.String())
    bookImage = db.Column(db.String())
    bookAuthor = db.Column(db.Integer(), db.ForeignKey('user.id_user'))
    bookDescription = db.Column(db.String())
    bookStatus = db.Column(db.String())
    bookNumberLove = db.Column(db.Integer())
    book_chapter = db.relationship("Chapter")
    book_library = db.relationship("Library")
    book_comment = db.relationship("Comment")
    book_category_book = db.relationship("Category_Book")

    def __init__(self, bookName, bookImage, bookAuthor, bookDescription, bookStatus, bookNumberLove):
        self.bookName = bookName
        self.bookImage = bookImage
        self.bookAuthor = bookAuthor
        self.bookDescription = bookDescription
        self.bookStatus = bookStatus
        self.bookNumberLove = bookNumberLove


class Category_Book(db.Model):
    id_category_book = db.Column(db.Integer(), primary_key=True)
    id_category = db.Column(db.Integer(), db.ForeignKey('category.id_category'))
    id_book = db.Column(db.Integer(), db.ForeignKey('book.id_book'))

    def __init__(self, id_category, id_book):
        self.id_category = id_category
        self.id_book = id_book


class Chapter(db.Model):
    id_chapter = db.Column(db.Integer, primary_key=True, autoincrement=True)
    id_book = db.Column(db.Integer(), db.ForeignKey('book.id_book'))
    chapterTitle = db.Column(db.String())
    chapterContent = db.Column(db.String())
    chapterNumberLove = db.Column(db.Integer())
    chapter_image = db.relationship("ImageOfChapter")

    def __init__(self, id_book,chapterTitle, chapterContent, chapterNumberLove ):
        self.chapterTitle = chapterTitle
        self.id_book = id_book
        self.chapterContent = chapterContent
        self.chapterNumberLove = chapterNumberLove


class ImageOfChapter(db.Model):
    id_imageOfChapter = db.Column(db.Integer, primary_key=True, autoincrement=True)
    id_chapter = db.Column(db.Integer(), db.ForeignKey('chapter.id_chapter'))
    image = db.Column(db.String())

    def __init__(self, id_chapter, image):
        self.id_chapter = id_chapter
        self.image = image


class Library(db.Model):
    id_library = db.Column(db.Integer, primary_key=True, autoincrement=True)
    id_user = db.Column(db.Integer(), db.ForeignKey('user.id_user'))
    id_book = db.Column(db.Integer(), db.ForeignKey('book.id_book'))
    libraryStatusread = db.Column(db.String())

    def __init__(self, id_user, id_book, libraryStatusread):
        self.id_user = id_user
        self.id_book = id_book
        self.libraryStatusread = libraryStatusread


class Comment(db.Model):
    id_comment = db.Column(db.Integer, primary_key=True, autoincrement=True)
    id_user = db.Column(db.Integer(), db.ForeignKey('user.id_user'))
    id_book = db.Column(db.Integer(), db.ForeignKey('book.id_book'))
    comment = db.Column(db.String())

    def __init__(self, id_user, id_book, comment):
        self.id_user = id_user
        self.id_book = id_book
        self.comment = comment


# class Subscribe(db.Model):
#     id_subscribe = db.Column(db.Integer, primary_key=True, autoincrement=True)
#     id_user_sub = db.Column(db.Integer(), db.ForeignKey('user.id_user'))
#     id_user_subed = db.Column(db.Integer(), db.ForeignKey('user.id_user'))
#
#     def __init__(self, id_user_sub, id_user_subed):
#         self.id_user_sub = id_user_sub
#         self.id_user_subed = id_user_subed