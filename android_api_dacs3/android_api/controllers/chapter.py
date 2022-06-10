from android_api.models import Chapter
from android_api import db


def create_chapter(id_book, chapterContent, chapterNumberLove, chapterTitle):
    try:
        chapter = Chapter(id_book,chapterTitle, chapterContent, chapterNumberLove)
        db.session.add(chapter)
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def show_all_chapter():
    try:
        chapter = Chapter.query.all()
        data = []
        for item in chapter:
            data.append({
                "id_chapter": item.id_chapter,
                "id_book": item.id_book,
                "chapterTitle": item.chapterTitle,
                "chapterContent": item.chapterContent,
                "chapterNumberLove": item.chapterNumberLove,
                "chapter_image": item.chapter_image
            })
        return data
    except Exception as e:
        print(e)
        return None


def show_by_id_chapter(id_chapter):
    try:
        chapter = Chapter.query.filter_by(id_chapter = id_chapter)
        data = []
        for item in chapter:
            data.append({
                "id_chapter": item.id_chapter,
                "id_book": item.id_book,
                "chapterTitle": item.chapterTitle,
                "chapterContent": item.chapterContent,
                "chapterNumberLove": item.chapterNumberLove,
                "chapter_image": item.chapter_image
            })
        return data
    except Exception as e:
        print(e)
        return None


def show_by_id_book(id_book):
    try:
        chapter = Chapter.query.filter_by(id_book = id_book)
        data = []
        for item in chapter:
            data.append({
                "id_chapter": item.id_chapter,
                "id_book": item.id_book,
                "chapterTitle": item.chapterTitle,
                "chapterContent": item.chapterContent,
                "chapterNumberLove": item.chapterNumberLove,
                "chapter_image": item.chapter_image
            })
        return data
    except Exception as e:
        print(e)
        return None


def update_chapter(id_chapter, chapterTitle, chapterContent):
    try:
        chapter = Chapter.query.filter_by(id_chapter = id_chapter).first()
        chapter.chapterTitle = chapterTitle
        chapter.chapterContent = chapterContent
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def delete_chapter(id_chapter):
    try:
        chapter = Chapter.query.get(id_chapter)
        db.session.delete(chapter)
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False
