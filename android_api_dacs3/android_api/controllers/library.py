from android_api_dacs3.android_api.models import Library
from android_api_dacs3.android_api import db


def create_library(id_user, id_book, libraryStatusread):
    try:
        library = Library(id_user, id_book, libraryStatusread)
        db.session.add(library)
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def show_all_library():
    try:
        library = Library.query.all()
        data = []
        for item in library:
            data.append({
                "id_library": item.id_library,
                "id_user": item.id_user,
                "id_book": item.id_book,
                "libraryStatusread": item.libraryStatusread
            })
        return data
    except Exception as e:
        print(e)
        return None


def show_by_id_library(id_library):
    try:
        library = Library.query.filter_by(id_library=id_library)
        data = []
        for item in library:
            data.append({
                "id_library": item.id_library,
                "id_user": item.id_user,
                "id_book": item.id_book,
                "libraryStatusread": item.libraryStatusread
            })
        return data
    except Exception as e:
        print(e)
        return None


def show_by_id_library_id_user(id_book, id_user):
    try:
        library = Library.query.filter_by(id_book=id_book, id_user=id_user)
        data = []
        for item in library:
            data.append({
                "id_library": item.id_library,
                "id_user": item.id_user,
                "id_book": item.id_book,
            })
        return data
    except Exception as e:
        print(e)
        return None


def update_library(id_library, id_user, id_book, libraryStatusread):
    try:
        library = Library.query.filter_by(id_library=id_library).first()
        id_user.libraryName = id_user
        id_book.libraryImage = id_book
        libraryStatusread.libraryImage = libraryStatusread
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def delete_library(id_library):
    try:
        library = Library.query.get(id_library)
        db.session.delete(library)
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False
