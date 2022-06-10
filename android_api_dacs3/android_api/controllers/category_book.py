from android_api.models import Category_Book
from android_api import db
from sqlalchemy import delete

def create_category_book(id_category, id_book):
    try:
        category_book = Category_Book(id_category, id_book)
        db.session.add(category_book)
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def show_all_category_book():
    try:
        category_book = Category_Book.query.all()
        data = []
        for item in category_book:
            data.append({
                "id_category_book": item.id_category_book,
                "id_category": item.id_category,
                "id_book": item.id_book
            })
        return data
    except Exception as e:
        print(e)
        return None


def show_by_id_category_book(id_category_book):
    try:
        category_book = Category_Book.query.filter_by(id_category_book = id_category_book)
        data = []
        for item in category_book:
            data.append({
                "id_category_book": item.id_category_book,
                "id_category": item.id_category,
                "id_book": item.id_book
            })
        return data
    except Exception as e:
        print(e)
        return None


def show_by_id_book(id_book):
    try:
        category_book = Category_Book.query.filter_by(id_book = id_book)
        data = []
        for item in category_book:
            data.append({
                "id_category_book": item.id_category_book,
                "id_category": item.id_category,
                "id_book": item.id_book
            })
        return data
    except Exception as e:
        print(e)
        return None


def update_category_book(id_category_book, id_category, id_book):
    try:
        category_book = Category_Book.query.filter_by(id_category_book = id_category_book).first()
        category_book.id_category = id_category
        category_book.id_book = id_book
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def delete_category_book(id_category, id_book):
    try:
        category_book = Category_Book.query.filter_by(id_category = id_category, id_book = id_book).first()
        db.session.delete(category_book)
        db.session.commit()
        # data = []
        # for item in category_book:
        #     data.append({
        #         "id_category_book": item.id_category_book,
        #         "id_category": item.id_category,
        #         "id_book": item.id_book
        #     })
        return True
    except Exception as e:
        print(e)
        return False
