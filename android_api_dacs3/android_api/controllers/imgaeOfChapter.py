from android_api.models import ImageOfChapter
from android_api import db


def create_imageOfChapter(id_chapter, image):
    try:
        imageOfChapter = ImageOfChapter(id_chapter, image)
        db.session.add(imageOfChapter)
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def show_all_imageOfChapter():
    try:
        imageOfChapter = ImageOfChapter.query.all()
        data = []
        for item in imageOfChapter:
            data.append({
                "id_imageOfChapter": item.id_imageOfChapter,
                "id_chapter": item.id_chapter,
                "image": item.image
            })
        return data
    except Exception as e:
        print(e)
        return None


def show_by_id_imageOfChapter(id_imageOfChapter):
    try:
        imageOfChapter = ImageOfChapter.query.filter_by(id_imageOfChapter = id_imageOfChapter)
        data = []
        for item in imageOfChapter:
            data.append({
                "id_imageOfChapter": item.id_imageOfChapter,
                "id_chapter": item.id_chapter,
                "image": item.image
            })
        return data
    except Exception as e:
        print(e)
        return None


def update_imageOfChapter(id_imageOfChapter, id_chapter, image):
    try:
        imageOfChapter = ImageOfChapter.query.filter_by(id_imageOfChapter = id_imageOfChapter).first()
        imageOfChapter.id_chapter = id_chapter
        imageOfChapter.image = image
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def delete_imageOfChapter(id_imageOfChapter):
    try:
        imageOfChapter = ImageOfChapter.query.get(id_imageOfChapter)
        db.session.delete(imageOfChapter)
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False
